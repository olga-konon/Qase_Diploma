package tests.ui;

import helpers.Config;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tests.base.BaseTest;
import utils.TestDataGenerator;

import static com.codeborne.selenide.Selenide.*;

@Listeners(listeners.TestListener.class)
public class ProjectTest extends BaseTest {

    @Test(description = "UI-PRJ-01 — Verify a project can be created via the form with valid data")
    public void checkCreateProject() {
        String projectName = TestDataGenerator.generateProjectName();
        String projectCode = TestDataGenerator.generateProjectCode();

        loginPage.open()
                .login(Config.getUser(), Config.getPassword())
                .isPageOpened()
                .clickCreateNewProjectButton()
                .isPageOpened()
                .fillInProjectForm(projectName, projectCode)
                .clickCreateProjectButton();
        open("/projects");

        createdProjectName = projectName;
        projectCreated = true;

        projectsPage.isPageOpened()
                .shouldSeeProject(projectName);
    }

    @Test(description = "UI-PRJ-02 — Verify project creation is blocked when a required field is empty")
    public void shouldNotCreateProjectNameIsEmpty() {
        String projectCode = TestDataGenerator.generateProjectCode();

        loginPage.open()
                .login(Config.getUser(), Config.getPassword())
                .isPageOpened()
                .clickCreateNewProjectButton()
                .isPageOpened()
                .fillInProjectForm("", projectCode);

        projectPage.clickCreateProjectButton();
        projectPage.isPageOpened();
    }

    @Test(description = "UI-PRJ-03 — Verify project creation is blocked when the project code already exists")
    public void shouldNotCreateProjectCodeExists() {
        String projectName = TestDataGenerator.generateProjectName();
        String projectCode = TestDataGenerator.generateProjectCode();

        loginPage.open()
                .login(Config.getUser(), Config.getPassword())
                .isPageOpened()
                .clickCreateNewProjectButton()
                .isPageOpened()
                .fillInProjectForm(projectName, projectCode)
                .clickCreateProjectButton();
        open("/projects");

        createdProjectCode = projectCode;
        projectCreated = true;

        projectsPage.isPageOpened()
                .clickCreateNewProjectButton()
                .isPageOpened()
                .fillInProjectForm(TestDataGenerator.generateProjectName(), projectCode);

        projectPage.clickCreateProjectButton();
        projectPage.isPageOpened();
    }
}
