package tests;

import base.BaseTest;
import com.github.javafaker.Faker;
import data.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import api.UserApi;
import static org.junit.Assert.assertEquals;

public class TestLogin extends BaseTest {

    UserApi userApi = new UserApi();
    private User user;
    private Response response;

    private String email;
    private String password;

    private WebDriver driver ;

    private Faker faker;

    private User generateRandomUser() {
        email = faker.internet().emailAddress();
        password = faker.internet().password(6, 10);
        String name = faker.name().firstName();
        return new User(email, password, name);
    }

    @Before
    public void setUp() {
        driver = BaseTest.getDriver();

        faker = new Faker();
        user = generateRandomUser();
        response = userApi.createUser(user);
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
    public void tearDown(){
        String accessToken = response.then().extract().path("accessToken");

        if(accessToken != null) {
            userApi.deleteUser(accessToken);
        }

    }
}
