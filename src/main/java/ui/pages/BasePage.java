package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;

public class BasePage {

    protected void waitForVisible(SelenideElement element) {
        element.shouldBe(visible);
    }
}
