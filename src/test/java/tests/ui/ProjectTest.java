package tests.ui;

import lombok.extern.log4j.Log4j2;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tests.base.BaseTest;
import utils.TestDataGenerator;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

@Log4j2
@Listeners(listeners.TestListener.class)
public class ProjectTest extends BaseTest {

    private final List<String> createdProjectNames = new ArrayList<>();

    @Test(description = "UI-PRJ-01 — Verify a project can be created via the form with valid data")
    public void checkCreateProject() {
        String projectName = TestDataGenerator.generateProjectName();
        String projectCode = TestDataGenerator.generateProjectCode();

        loginAsDefaultUser()
                .clickCreateNewProjectButton()
                .isPageOpened()
                .fillInProjectForm(projectName, projectCode)
                .clickCreateProjectButton();
        open("/projects");

        createdProjectName = projectName;
        createdProjectNames.add(projectName);

        projectsPage.isPageOpened()
                .shouldSeeProject(projectName);
    }

    @Test(description = "UI-PRJ-02 — Verify project creation is blocked when a required field is empty")
    public void shouldNotCreateProjectNameIsEmpty() {
        String projectCode = TestDataGenerator.generateProjectCode();

        loginAsDefaultUser()
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

        loginAsDefaultUser()
                .clickCreateNewProjectButton()
                .isPageOpened()
                .fillInProjectForm(projectName, projectCode)
                .clickCreateProjectButton();
        open("/projects");

        createdProjectNames.add(projectName);

        projectsPage.isPageOpened()
                .clickCreateNewProjectButton()
                .isPageOpened()
                .fillInProjectForm(TestDataGenerator.generateProjectName(), projectCode);

        projectPage.clickCreateProjectButton();
        projectPage.isPageOpened();
    }

    @AfterClass(alwaysRun = true)
    public void cleanUpCreatedProjects() {
        if (createdProjectNames.isEmpty()) {
            return;
        }
        loginAsDefaultUser();

        for (String projectName : createdProjectNames) {
            open("/projects");
            projectsPage.deleteProject(projectName);
        }

        closeWebDriver();

    }
}
