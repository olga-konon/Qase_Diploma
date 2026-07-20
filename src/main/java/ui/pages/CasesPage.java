package ui.pages;

import ui.dict.Elements;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CasesPage  extends BasePage {


    public CasesPage isPageOpened() {
        waitForVisible($(byText(Elements.CREATE_CASE_BUTTON)));
        return this;
    }

    public CasePage clickCreateCaseButton() {
        $(byText(Elements.CREATE_CASE_BUTTON)).click();
        return new CasePage();
    }
}
