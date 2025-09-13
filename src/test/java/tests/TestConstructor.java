package tests;

import base.BaseTest;
import data.PageMain;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;



import static org.junit.Assert.assertEquals;

public class TestConstructor extends BaseTest {

    private WebDriver driver;


    @Before
    public void setUp() {
        driver = BaseTest.getDriver();
    }


    @Test
    @DisplayName("Проверка перехода к разделу <Булки>")
    @Description("При нажатии на вкладку <Начинки>, а затем на вкладку <Булки>, происходит переход к разделу <Булки>")
    public void testTabBuns () {

        driver.get(PageMain.URL);

        PageMain pageMain = new PageMain(driver);
        pageMain.waitPageMainIsDisplayed();
        pageMain.clickConstructor();
        pageMain.clickFillingsTab();
        pageMain.clickBunsTab();

        String expectedText = "Булки";
        String actualText = pageMain.textSelectedTab(expectedText);

        assertEquals(expectedText, actualText);

    }

    @Test
    @DisplayName("Проверка перехода к разделу <Соусы>")
    @Description("При нажатии на вкладку <Соусы>, происходит переход к разделу <Соусы>")
    public void testTabSauces () {
        driver.get(PageMain.URL);
        PageMain pageMain = new PageMain(driver);
        pageMain.waitPageMainIsDisplayed();
        pageMain.clickConstructor();

        pageMain.clickSaucesTab();
        String expectedText = "Соусы";
        String actualText = pageMain.textSelectedTab(expectedText);

        assertEquals(expectedText, actualText);

    }

    @Test
    @DisplayName("Проверка перехода к разделу <Соусы>")
    @Description("При нажатии на вкладку <Начинки>, происходит переход к разделу <Начинки>")
    public void testTabFillings () {

        driver.get(PageMain.URL);
        PageMain pageMain = new PageMain(driver);
        pageMain.waitPageMainIsDisplayed();

        pageMain.clickConstructor();
        pageMain.clickFillingsTab();
        String expectedText = "Начинки";
        String actualText = pageMain.textSelectedTab(expectedText);

        assertEquals(expectedText, actualText);

    }

}
