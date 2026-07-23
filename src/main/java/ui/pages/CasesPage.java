package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.dict.Elements;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class CasesPage extends BasePage {

    private final String CASE_DELETE_CHECKBOX = "div[@data-suite-body-id]";
    private final String DELETE_BUTTON = "button[aria-label='Delete']";
    private final String CONFIRM_INPUT = "input[name='confirm']";
    private final String DELETE_ON_FORM_BUTTON = "button[type='submit']";
    private final String CONFIRMATION_MODAL = "div[role='alert']";


    public CasesPage isPageOpened() {
        waitForVisible($(byText(Elements.CREATE_CASE_BUTTON)));
        return this;
    }

    @Step("Click create case button")
    public CreateCasePage clickCreateCaseButton() {
        log.info("Clicking create case button");
        $(byText(Elements.CREATE_CASE_BUTTON)).click();
        return new CreateCasePage();
    }

    @Step("Click test suite")
    public CasesPage clickTestSuite() {
        log.info("Clicking test suite");
        $(byText(Elements.CLICK_TEST_SUITE)).click();
        return this;
    }

    @Step("Open case: '{caseName}'")
    public ViewCasePage clickTestCase(String caseName) {
        log.info("Opening case: {}", caseName);
        $(byText(caseName)).click();
        return new ViewCasePage();
    }

    @Step("Select case: '{caseName}'")
    public CasesPage selectCase(String caseName) {
        log.info("Selecting case: {}", caseName);
        $(byText(caseName))
                .closest(CASE_DELETE_CHECKBOX)
                .$("label")
                .shouldBe(visible)
                .click();
        return this;
    }

    @Step("Click delete button")
    public CasesPage clickDeleteButton() {
        log.info("Clicking delete button");
        $(DELETE_BUTTON).click();
        return this;
    }

    @Step("Fill in delete confirmation")
    public CasesPage fillInConfirm() {
        log.info("Filling in delete confirmation");
        $(CONFIRM_INPUT).setValue(Elements.CONFIRM);
        return this;
    }

    @Step("Click delete-on-form button")
    public CasesPage clickDeleteOnFormButton() {
        log.info("Clicking delete-on-form button");
        $(DELETE_ON_FORM_BUTTON).click();
        return this;
    }

    @Step("Verify case is visible: '{caseName}'")
    public CasesPage shouldSeeCase(String caseName) {
        log.info("Verifying case is visible: {}", caseName);
        $(byText(caseName)).shouldBe(visible);
        return this;
    }

    @Step("Verify case is not visible: '{caseName}'")
    public CasesPage shouldNotSeeCase(String caseName) {
        log.info("Verifying case is not visible: {}", caseName);
        $(byText(caseName)).shouldNotBe(visible);
        return this;
    }

    @Step("Verify confirmation modal text: '{expectedText}'")
    public void modalShouldHaveText(String expectedText) {
        log.info("Verifying confirmation modal text: {}", expectedText);
        $(CONFIRMATION_MODAL)
                .shouldBe(visible)
                .shouldHave(text(expectedText));
    }

 }
