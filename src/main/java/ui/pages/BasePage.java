package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

@Log4j2
public class BasePage {

    protected void waitForVisible(SelenideElement element) {
        log.info("Waiting for element to be visible: {}", element);
        element.shouldBe(visible);
    }
}
