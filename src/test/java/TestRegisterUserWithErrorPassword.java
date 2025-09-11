import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestRegisterUserWithErrorPassword extends BaseTest {

    UserApi userApi = new UserApi();

    private WebDriver driver ;
    private final String browser;

    private final String name;
    private final String email;
    private final String password;



    public TestRegisterUserWithErrorPassword (String browser, String name, String email,String password) {
        this.browser = browser;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Before
    public void setBrowser () throws InterruptedException {

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("yandex")) {
            WebDriverManager.chromedriver().driverVersion("138.0.7163.0").setup();
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Users\\Ira\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
            driver = new ChromeDriver(options);
        }
        Thread.sleep(2000);

    }

    @Parameterized.Parameters (name = "Браузер: {0}, Имя: {1}, Email: {2}, Пароль: {3}")
    public static Object[][] getRegisterDetails() {
        return new Object[][]{

                {"chrome", "Петр", "petrovPetr13_12@yandex.ru", "12"},
                {"yandex", "Петр", "petrovPetr13_12@yandex.ru", "12"},
                {"chrome", "Петр", "petrovPetr13_12@yandex.ru", "1234"},
                {"yandex", "Петр", "petrovPetr13_12@yandex.ru", "1234"},
                {"chrome", "Петр", "petrovPetr13_12@yandex.ru", "12345"},
                {"yandex", "Петр", "petrovPetr13_12@yandex.ru", "12345"},
        };
    }


    @Test
    @DisplayName("Регистрация пользователя с паролем менее 6 символов")
    @Description("При нажатии заполнении формы регистрации и нажатии на кнопку <Зарегистрироваться>, отображается ошибка в поле <Пароль>")
    public void testRegisterUser () {
        driver.get(PageMain.URL);
        PageMain pageMain = new PageMain(driver);
        pageMain.waitPageMainIsDisplayed();
        pageMain.clickAccountButton();


        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.sendRegisterUserForm(name,email,password);
        registerPage.clickRegisterButton();
        registerPage.waitErrorPasswordFieldIsDisplayed();
        String textError = registerPage.getErrorPasswordField();
        assertEquals("Некорректный пароль", textError);

    }

    @After
    public void tearDown() throws InterruptedException {
        User authUser = new User();
        authUser.setEmail(email);
        authUser.setPassword(password);
        Response response = userApi.authUser(authUser);
        String accessToken = response.then().extract().path("accessToken");

        if(accessToken != null) {
            userApi.deleteUser(accessToken);
        }
        driver.quit();
        Thread.sleep(1000);
    }
}
