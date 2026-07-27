package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class BasePage {

    private static final String AIDEN_MODAL_CLOSE =
            "dialog[data-sentry-component='ModalDialog']:has(img[alt='Explore AIDEN']) button:has(svg[data-icon='xmark'])";

    protected void waitForVisible(SelenideElement element) {
        closeAidenModalIfPresent();
        log.info("Waiting for element to be visible: {}", element);
        element.shouldBe(visible);
    }

    @Step("Close AIDEN promo modal if present")
    public void closeAidenModalIfPresent() {
        if ($(AIDEN_MODAL_CLOSE).exists()) {
            log.info("Closing AIDEN promo modal");
            $(AIDEN_MODAL_CLOSE).click();
        }
    }
}
