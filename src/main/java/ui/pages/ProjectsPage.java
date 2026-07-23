package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.dict.Elements;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Log4j2
public class ProjectsPage extends BasePage {

    private static final String REMOVE_BUTTON = "[data-testid='remove']";
    private static final String CONFIRM_BUTTON = "//span[text()='Delete project']";
    private static final String ACTION_MENU = "button[aria-label='Open action menu']";
    private static final String TABLE_TAG = "tr";

    public ProjectsPage isPageOpened() {
        waitForVisible($(byText(Elements.CREATE_NEW_PROJECT_BUTTON)));
        return this;
    }

    @Step("Click 'create new project' button")
    public ProjectPage clickCreateNewProjectButton() {
        log.info("Clicking 'create new project' button");
        $(byText(Elements.CREATE_NEW_PROJECT_BUTTON)).click();
        return new ProjectPage();
    }

    @Step("Verify project is visible: '{projectName}'")
    public ProjectsPage shouldSeeProject(String projectName) {
        log.info("Verifying project is visible: {}", projectName);
        $(byText(projectName)).shouldBe(visible);
        return this;
    }

    @Step("Open project: '{projectName}'")
    public CasesPage clickProjectName(String projectName) {
        log.info("Opening project: {}", projectName);
        $(byText(projectName)).click();
        return new CasesPage();
    }

    @Step("Delete project: '{projectName}'")
    public ProjectsPage deleteProject(String projectName) {
        log.info("Deleting project: {}", projectName);
        $(byText(projectName))
                .ancestor(TABLE_TAG)
                .find(ACTION_MENU)
                .click();

        $(REMOVE_BUTTON).click();
        $x(CONFIRM_BUTTON).click();
        return this;
    }
}
