package ui.pages;

import ui.dict.Elements;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProjectsPage extends BasePage {

    private static final String REMOVE_BUTTON = "[data-testid='remove']";
    private static final String CONFIRM_BUTTON = "//span[text()='Delete project']";
    private static final String ACTION_MENU = "button[aria-label='Open action menu']";
    private static final String TABLE_TAG = "tr";

    public ProjectsPage isPageOpened() {
        waitForVisible($(byText(Elements.CREATE_NEW_PROJECT_BUTTON)));
        return this;
    }

    public ProjectPage clickCreateNewProjectButton() {
        $(byText(Elements.CREATE_NEW_PROJECT_BUTTON)).click();
        return new ProjectPage();
    }

    public ProjectsPage shouldSeeProject(String projectName) {
        $(byText(projectName)).shouldBe(visible);
        return this;
    }

    public CasesPage clickProjectName(String projectName) {
        $(byText(projectName)).click();
        return new CasesPage();
    }

    public ProjectsPage deleteProject(String projectName) {
        $(byText(projectName))
                .ancestor(TABLE_TAG)
                .find(ACTION_MENU)
                .click();

        $(REMOVE_BUTTON).click();
        $x(CONFIRM_BUTTON).click();
        return this;
    }
}
