package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.dict.Elements;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class ProjectPage extends BasePage {

    private final String PROJECT_NAME_FIELD = "#project-name";
    private final String PROJECT_CODE_FIELD = "#project-code";

    public ProjectPage isPageOpened() {
        waitForVisible($(byText(Elements.CREATE_NEW_PROJECT)));
        return this;
    }

    @Step("Fill in project form: '{projectName}', '{projectCode}'")
    public ProjectPage fillInProjectForm(String projectName, String projectCode) {
        log.info("Filling in project form: name='{}', code='{}'", projectName, projectCode);
        $(PROJECT_NAME_FIELD).setValue(projectName);
        $(PROJECT_CODE_FIELD).setValue(projectCode);
        return this;
    }

    @Step("Click create project button")
    public ProjectsPage clickCreateProjectButton() {
        log.info("Clicking create project button");
        $(byText(Elements.CREATE_NEW_PROJECT)).click();
        return new ProjectsPage();
    }
}
