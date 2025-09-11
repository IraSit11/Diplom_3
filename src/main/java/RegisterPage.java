import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    private WebDriver driver;



    private By nameField = By.xpath(".//div[contains(@class, 'input') and contains(., 'Имя')]//input[@name='name']");
    private By emailField = By.xpath(".//div[contains(@class, 'input')][label[text()='Email']]//input");
    private By passwordField = By.xpath(".//div[contains(@class, 'input') and contains(@class, 'input_type_password')][label[text()='Пароль']]//input");
    private By registerButton = By.xpath(".//button[contains(@class,'button_button__33qZ0') and contains(text(),'Зарегистрироваться')]");

    public By errorPasswordField = By.xpath("//p[@class='input__error text_type_main-default' and text()='Некорректный пароль']");

    public By entryLinkOnBottom = By.xpath(".//a[contains(@class, 'Auth_link__1fOlj') and contains(@href, '/login') and text()='Войти']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие на поле <Имя>")
    public void clickNameField () {driver.findElement(nameField);}
    @Step ("Заполнение поля <Имя>")
    public void sendName (String name) {driver.findElement(nameField).sendKeys(name);}
    @Step("Нажатие на поле <Email>")
    public void clickEmailField () {driver.findElement(emailField);}
    @Step ("Заполнение поля <Email>")
    public void sendEmail (String email) {driver.findElement(emailField).sendKeys(email);}
    @Step("Нажатие на поле <Пароль>")
    public void clickPasswordField () {driver.findElement(passwordField);}
    @Step ("Заполнение поля <Пароль>")
    public void sendPassword (String password) {driver.findElement(passwordField).sendKeys(password);}

    @Step ("Заполнение формы регистрации")
    public void sendRegisterUserForm(String name, String email, String password) {
        clickNameField();
        sendName(name);
        clickEmailField();
        sendEmail(email);
        clickPasswordField();
        sendPassword(password);
    }

    @Step("Нажатие на кнопку <Зарегистрироваться>")
    public void clickRegisterButton () {
        driver.findElement(registerButton).click();
    }

    @Step("Дождаться отображения ошибки в поле <Пароль>")
    public void waitErrorPasswordFieldIsDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(errorPasswordField));
    }

    @Step("Получить текст ошибки в поле <Пароль>")
   public  String getErrorPasswordField () {
       return driver.findElement(errorPasswordField).getText();
   }

    @Step("Нажать на кнопку <Войти> внизу формы")
   public void clickEntryLinkOnBottom () {
        driver.findElement(entryLinkOnBottom).click();
   }
}
