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
public class TestLogin extends BaseTest{

    UserApi userApi = new UserApi();
    private Response response;

    private WebDriver driver ;
    private final String browser;


    private final String email;
    private final String password;

    public TestLogin (String browser, String email, String password) {
        this.browser = browser;
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

        User user = new User(email,password,"Петр");
        response = userApi.createUser(user);
    }

    @Parameterized.Parameters (name = "Браузер: {0}, Email: {1}, Пароль: {2}")
    public static Object[][] getLoginDetails() {
        return new Object[][]{
                {"chrome", "petrovPetr13_12@yandex.ru", "666666"},
                {"yandex", "petrovPetr13_12@yandex.ru", "666666"},
        };
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет» в шапке главной страницы")
    @Description("При нажатии на кнопку появляется форма аутентификации, после заполнения которой и нажатии на кнопку <Войти>, снова отображается главная страница")

    public void testLoginViaAccountButton () {
        driver.get(PageMain.URL);
        PageMain pageMain = new PageMain(driver);
        pageMain.waitPageMainIsDisplayed();
        pageMain.clickAccountButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.sendLoginForm(email,password);
        loginPage.clickEntryButton();

        pageMain.waitPageMainIsDisplayed();

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = PageMain.URL;
        assertEquals(expectedUrl, currentUrl);

    }

    @Test
    @DisplayName("Вход через кнопку «Войти в аккаунт» на главной странице")
    @Description("При нажатии на кнопку появляется форма аутентификации, после заполнения которой и нажатии на кнопку <Войти>, снова отображается главная страница")

    public void testLoginButton () {
        driver.get(PageMain.URL);
        PageMain pageMain = new PageMain(driver);
        pageMain.waitPageMainIsDisplayed();
        pageMain.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.sendLoginForm(email,password);
        loginPage.clickEntryButton();

        pageMain.waitPageMainIsDisplayed();

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = PageMain.URL;
        assertEquals(expectedUrl, currentUrl);

    }

    @Test
    @DisplayName("Вход по кнопке «Войти», которая располагается внизу формы регистрации")
    @Description("При нажатии на кнопку появляется форма аутентификации, после заполнения которой и нажатии на кнопку <Войти>, отображается главная страница")
    public void testLoginViaRegisterLink () {
        driver.get(PageMain.URL);
        PageMain pageMain = new PageMain(driver);
        pageMain.waitPageMainIsDisplayed();
        pageMain.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickEntryLinkOnBottom();

        loginPage.sendLoginForm(email,password);
        loginPage.clickEntryButton();

        pageMain.waitPageMainIsDisplayed();

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = PageMain.URL;
        assertEquals(expectedUrl, currentUrl);

    }

    @Test
    @DisplayName("Вход по кнопке «Войти», которая располагается внизу формы восстановления пароля")
    @Description("При нажатии на кнопку появляется форма аутентификации, после заполнения которой и нажатии на кнопку <Войти>, отображается главная страница")
    public void testLoginViaRecoverLink () {
        driver.get(PageMain.URL);
        PageMain pageMain = new PageMain(driver);
        pageMain.waitPageMainIsDisplayed();
        pageMain.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRecoverPasswordLink();

        RecoverPasswordPage recoverPasswordPage = new RecoverPasswordPage(driver);
        recoverPasswordPage.clickEntryLinkOnBottom();

        loginPage.sendLoginForm(email,password);
        loginPage.clickEntryButton();

        pageMain.waitPageMainIsDisplayed();

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = PageMain.URL;
        assertEquals(expectedUrl, currentUrl);

    }


    @After
    public void tearDown() throws InterruptedException {
        String accessToken = response.then().extract().path("accessToken");

        if(accessToken != null) {
            userApi.deleteUser(accessToken);
        }
        driver.quit();
        Thread.sleep(1000);
    }
}
