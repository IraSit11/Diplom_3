package data;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecoverPasswordPage {
    private WebDriver driver;

    public By entryLinkOnBottom = By.xpath("//div/p[contains(@class, 'text_color_inactive')]//a[@class='Auth_link__1fOlj' and text()='Войти']");


    public RecoverPasswordPage (WebDriver driver){
        this.driver= driver;
    }
    @Step("Нажатие на кнопку <Войти>")
    public void clickEntryLinkOnBottom () {
        driver.findElement(entryLinkOnBottom).click();
    }

}
