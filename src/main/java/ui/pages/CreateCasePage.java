package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.dict.Elements;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class CreateCasePage extends BasePage {

    private final String TITLE_FIELD = "input[name='title']";

    public CreateCasePage isPageOpened() {
        waitForVisible($(byText(Elements.SAVE_BUTTON)));
        return this;
    }

    @Step("Fill in project form: '{title}'")
    public CreateCasePage fillInProjectForm(String title) {
        log.info("Filling in case title: {}", title);
        $(TITLE_FIELD).setValue(title);
        return this;
    }

    public CreateCasePage isSaveButtonEnabled() {
        $(byText(Elements.SAVE_BUTTON)).shouldBe(enabled);
        return this;
    }

    @Step("Click save case button")
    public CasesPage clickSaveCaseButton() {
        log.info("Clicking save case button");
        $(byText(Elements.SAVE_BUTTON)).click();
        return new CasesPage();
    }
}
