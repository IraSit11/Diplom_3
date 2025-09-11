import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PageMain {

    private WebDriver driver;
    public static final String URL = "https://stellarburgers.nomoreparties.site/";

    public PageMain (WebDriver driver) {
        this.driver = driver;
    }

    private By accountButton = By.xpath(".//p[contains(@class, 'AppHeader_header__linkText__3q_va') and contains(@class, 'ml-2') and text()='Личный Кабинет']");
    private By loginButton =By.xpath(".//button[contains(@class,'button_button__33qZ0') and contains(text(),'Войти в аккаунт')]");
    private By constructor = By.xpath(".//p[contains(@class, 'AppHeader_header__linkText__3q_va') and contains(@class, 'ml-2') and text()='Конструктор']");
    private By bunsTab = By.xpath(".//div/span[text()='Булки']");
    private By saucesTab = By.xpath(".//div/span[text()='Соусы']");
    private By fillingsTab = By.xpath(".//div/span[text()='Начинки']");
    private By selectedTab = By.xpath("//div[contains(@class,'tab_tab_type_current__2BEPc')]");


    @Step ("Нажатие на кнопку <Личный кабинет> в шапке страницы")
    public void clickAccountButton (){
        driver.findElement(accountButton).click();
    }

    @Step ("Нажатие на кнопку <Войти в аккаунт>")
    public void clickLoginButton () {
        driver.findElement(loginButton).click();
    }

    @Step ("Дождаться загрузки страницы")
    public void waitPageMainIsDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(PageMain.URL));
    }

    @Step ("Нажатие на кнопку <Конструктор>")
    public void clickConstructor (){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(constructor));
         driver.findElement(constructor).click();
    }

    @Step ("Нажатие на вкладку <Булки>")
    public void clickBunsTab (){
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(bunsTab));
        driver.findElement(bunsTab).click();
    }

    @Step ("Нажатие на вкладку <Соусы>")
    public void clickSaucesTab (){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(saucesTab));
        driver.findElement(saucesTab).click();
    }

    @Step ("Нажатие на вкладку <Начинки>")
    public void clickFillingsTab (){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(fillingsTab));
                driver.findElement(fillingsTab).click();
    }

    @Step ("Получить текст активной вкладки")
    public String textSelectedTab(String expectedText) {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.textToBe(selectedTab, expectedText));
        return driver.findElement(selectedTab).getText();
    }



}
