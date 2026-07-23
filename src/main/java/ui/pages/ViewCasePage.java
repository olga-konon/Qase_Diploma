package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class ViewCasePage extends BasePage{

    private final String EDIT_BUTTON = "button[aria-label='Edit']";

    @Step("Click edit button")
    public EditCasePage clickEditButton() {
        log.info("Clicking edit button");
        $(EDIT_BUTTON).click();
        return new EditCasePage();
    }
}
