package tests.baseTests;

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
import ui.pages.LoginPage;
import ui.pages.ProjectPage;
import ui.pages.ProjectsPage;

import java.util.HashMap;

public class BaseTest {

    public LoginPage loginPage;
    public ProjectPage projectPage;
    public ProjectsPage projectsPage;

    protected String createdProjectName;
    protected String createdProjectCode;
    protected boolean projectCreated;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browserParam) {
        String browser = System.getProperty("browser", browserParam);
        Configuration.browser = browser;
        Configuration.baseUrl = Config.getBaseUrl();

        Configuration.timeout = 10000;
        Configuration.clickViaJs = true;

        Configuration.savePageSource = true;
        Configuration.screenshots = true;
        Configuration.reportsFolder = "target/selenide-results/reports";

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .savePageSource(true)
                        .screenshots(true));

        switch (browser) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                Configuration.browserCapabilities = firefoxOptions;
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--inprivate", "--disable-notifications");
                Configuration.browserCapabilities = edgeOptions;
                break;
            default: // chrome
                ChromeOptions chromeOptions = new ChromeOptions();
                HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.addArguments("--incognito", "--disable-notifications",
                        "--disable-popup-blocking", "--disable-infobars", "--no-sandbox");
                if (System.getenv("BUILD_NUMBER") != null) {
                    chromeOptions.addArguments("--headless=new");
                }
                Configuration.browserCapabilities = chromeOptions;
        }
        loginPage = new LoginPage();
        projectPage = new ProjectPage();
        projectsPage = new ProjectsPage();

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (projectCreated && createdProjectName != null) {
            projectsPage.isPageOpened()
                    .deleteProject(createdProjectName);
        }
        Selenide.closeWebDriver();
    }
}
