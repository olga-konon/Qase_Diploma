package listeners;

import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.AllureUtils;


import java.util.concurrent.TimeUnit;

@Log4j2
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Starting test {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Finished test {} Duration: {}s\n", result.getName(), getExecutionTime(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("Failed test {} Duration: {}s", result.getName(), getExecutionTime(result));

        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            try {
                AllureUtils.takeScreenshot(driver);
            } catch (Exception e) {
                log.error("Failed to take screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("Skipping test {}", result.getName());
    }

    private long getExecutionTime(ITestResult result) {
        return TimeUnit.MILLISECONDS.toSeconds(result.getEndMillis() - result.getStartMillis());
    }
}
