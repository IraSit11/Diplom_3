package step;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    private By registerLink = By.xpath(".//a[contains(@class, 'Auth_link__1fOlj') and contains(@href, '/register') and text()='Зарегистрироваться']");
    public By buttonEntry = By.xpath(".//button[contains(@class,'button_button__33qZ0') and contains(text(),'Войти')]");
    private By recoverPasswordLink = By.xpath(".//a[contains(@class, 'Auth_link__1fOlj') and contains(@href, '/forgot-password') and text()='Восстановить пароль']");
    private By emailField = By.xpath(".//div[contains(@class, 'input')][label[text()='Email']]//input");
    private By passwordField = By.xpath(".//div[contains(@class, 'input') and contains(@class, 'input_type_password')][label[text()='Пароль']]//input");
    private By entryButton = By.xpath(".//button[contains(@class,'button_button__33qZ0') and contains(text(),'Войти')]");


    public LoginPage (WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие на ссылку  <Зарегистрироваться> внизу формы ")
    public void clickRegisterLink () {
        driver.findElement(registerLink).click();
    }

    @Step ("Дождаться появления кнопки <Войти>")
    public void waitButtonEntryIsDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(buttonEntry));
    }

    @Step ("Нажатие на ссылку <Восстановить пароль>")
    public void clickRecoverPasswordLink () {
        driver.findElement(recoverPasswordLink).click();
    }

    @Step ("Нажатие на поле <Email>")
    public void clickEmailField () {
        driver.findElement(emailField).click();
    }

    @Step ("Заполнение поля <Email>")
    public void sendEmailField (String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step ("Нажатие на поле <Пароль>")
    public void clickPasswordField () {
        driver.findElement(passwordField).click();
    }

    @Step ("Заполнение поля <Пароль>")
    public void sendPasswordField (String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step ("Нажатие на кнопку <Войти>")
    public void clickEntryButton () {
        driver.findElement(entryButton).click();
    }

    @Step ("Заполнение формы аутентификации")
    public void sendLoginForm (String email,String password) {
        clickEmailField();
        sendEmailField(email);
        clickPasswordField();
        sendPasswordField(password);
    }


}
