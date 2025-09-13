package tests;

import base.BaseTest;
import com.github.javafaker.Faker;
import data.LoginPage;
import data.PageMain;
import data.RegisterPage;
import data.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import api.UserApi;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestRegisterUserWithErrorPassword extends BaseTest {

    UserApi userApi = new UserApi();

    private WebDriver driver;

    @Parameterized.Parameter(0)
    public String name;

    @Parameterized.Parameter(1)
    public String email;

    @Parameterized.Parameter(2)
    public String password;

    @Before
    public void setUp() {
        driver = BaseTest.getDriver();
    }


    @Parameterized.Parameters (name = "Имя: {0}, Email: {1}, Пароль: {2}")
    public static Object[][] getRegisterDetails() {
        Faker faker = new Faker();

        return new Object[][]{
                {faker.name().firstName(), faker.internet().emailAddress(), faker.internet().password(2,3)},
                {faker.name().firstName(), faker.internet().emailAddress(), faker.internet().password(4,5)},
                {faker.name().firstName(), faker.internet().emailAddress(), faker.internet().password(5,6)},

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
    public void tearDown()  {
        User authUser = new User();
        authUser.setEmail(email);
        authUser.setPassword(password);
        Response response = userApi.authUser(authUser);
        String accessToken = response.then().extract().path("accessToken");

        if(accessToken != null) {
            userApi.deleteUser(accessToken);
        }

    }
}
