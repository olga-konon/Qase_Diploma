package tests.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Config;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ui.pages.*;

import java.util.HashMap;

public class BaseTest {

    public LoginPage loginPage;
    public ProjectPage projectPage;
    public ProjectsPage projectsPage;
    public CreateCasePage createCasePage;
    public CasesPage casesPage;
    public ViewCasePage viewCasePage;
    public EditCasePage editCasePage;

    protected String createdProjectName;
    protected String createdProjectCode;
    protected boolean projectCreated;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browserParam) {
        String browser = System.getProperty("browser", browserParam);
        Configuration.browser = browser;
        Configuration.baseUrl = Config.getBaseUrl();

        Configuration.timeout = 20000;
        Configuration.clickViaJs = true;

        Configuration.savePageSource = true;
        Configuration.screenshots = true;
        Configuration.reportsFolder = "target/selenide-results/reports";

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .savePageSource(true)
                        .screenshots(true));

        boolean headless = "true".equals(System.getProperty("headless", "true"));

        switch (browser) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                firefoxOptions.addArguments("--no-sandbox");
                firefoxOptions.addArguments("--disable-dev-shm-usage");
                firefoxOptions.addArguments("--disable-gpu");
                Configuration.browserCapabilities = firefoxOptions;
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--disable-gpu");
                Configuration.browserCapabilities = edgeOptions;
                break;
            default: // chrome
                ChromeOptions chromeOptions = new ChromeOptions();

                HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.addArguments("--incognito");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-infobars");
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--disable-http2");

                Configuration.browserCapabilities = chromeOptions;
        }
        loginPage = new LoginPage();
        projectPage = new ProjectPage();
        projectsPage = new ProjectsPage();
        createCasePage = new CreateCasePage();
        casesPage = new CasesPage();
        viewCasePage = new ViewCasePage();
        editCasePage= new EditCasePage();

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
//        if (projectCreated && createdProjectName != null) {
//            projectsPage.isPageOpened()
//                    .deleteProject(createdProjectName);
//        }
        Selenide.closeWebDriver();
    }
}
