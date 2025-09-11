import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
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
public class TestConstructor  {

    private WebDriver driver;
    private final String browser;


    public TestConstructor(String browser) {
        this.browser = browser;
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

        Thread.sleep(1000);
    }

    @Parameterized.Parameters (name = "Браузер: {0}")
    public static Object[][] choiceBrowser() {
        return new Object[][]{
                {"chrome"},
                {"yandex"},
        };
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


    @After
    public void tearDown() throws InterruptedException {
        driver.quit();
        Thread.sleep(1500);

    }


}
