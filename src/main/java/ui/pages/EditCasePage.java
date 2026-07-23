package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import ui.dict.Elements;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class EditCasePage extends BasePage{
    private final String TITLE_FIELD = "input[name='title']";

    @Step("Clear case title field")
    public EditCasePage clearProjectForm() {
        log.info("Clearing case title field");
        $(TITLE_FIELD).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        return this;
    }

    @Step("Fill in project form: '{title}'")
    public EditCasePage fillInProjectForm(String title) {
        log.info("Updating case title to: {}", title);
        $(TITLE_FIELD).setValue(title);
        return this;
    }

    @Step("Click save case button")
    public CasesPage clickSaveCaseButton() {
        log.info("Clicking save case button");
        $(byText(Elements.SAVE_BUTTON)).click();
        return new CasesPage();
    }
}
