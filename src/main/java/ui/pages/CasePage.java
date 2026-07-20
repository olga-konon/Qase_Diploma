package ui.pages;

import io.qameta.allure.Step;
import ui.dict.Elements;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CasePage extends BasePage {

    private final String TITLE_FIELD = "input[name='title']";


    // open page

    // is page opened
    public CasePage isPageOpened() {
        waitForVisible($(byText(Elements.SAVE_BUTTON)));
        return this;
    }

    @Step("Fill in project form: '{title}'")
    public CasePage fillInProjectForm(String title) {
        $(TITLE_FIELD).setValue(title);
        return this;
    }

    public CasePage isSaveButtonEnabled() {
        $(byText(Elements.SAVE_BUTTON)).shouldBe(enabled);
        return this;
    }

    public CasesPage clickSaveCaseButton() {
        $(byText(Elements.SAVE_BUTTON)).click();
        return new CasesPage();
    }


}

