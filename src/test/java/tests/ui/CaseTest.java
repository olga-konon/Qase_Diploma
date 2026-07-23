package tests.ui;

import api.adapters.ProjectAdapter;
import api.models.project.ProjectRq;
import api.models.project.ProjectRs;
import helpers.Config;
import org.testng.annotations.Test;
import tests.base.BaseTest;
import utils.TestDataGenerator;

import static org.testng.Assert.assertTrue;

public class CaseTest extends BaseTest {

    String caseCreatedText = "Test case was created successfully!";
    String caseEditedText = "Test case was edited successfully!";
    String caseDeletedText = "Deletion of 1 test case started";

    String projectName;
    String projectCode;
    String caseTitle;

    @Test(priority = 1, description = "UI-CASE-02 — Verify a case can be created via the form with valid data")
    public void checkCreateCase() {

        projectName = TestDataGenerator.generateProjectName();
        projectCode = TestDataGenerator.generateProjectCode();
        caseTitle = TestDataGenerator.generateCaseTitle();

        ProjectRq rq = ProjectRq.builder()
                .title(projectName)
                .code(projectCode)
                .description("test")
                .access("all")
                .group("test")
                .build();

        ProjectRs rs = ProjectAdapter.createProject(rq);
        assertTrue(rs.status);

        createdProjectName = projectName;
        projectCreated = true;

        loginPage.open()
                .login(Config.getUser(), Config.getPassword())
                .isPageOpened()
                .clickProjectName(projectName)
                .isPageOpened()
                .clickCreateCaseButton()
                .fillInProjectForm(caseTitle)
                .clickSaveCaseButton()
                .isPageOpened()
                .shouldSeeCase(caseTitle)
                .modalShouldHaveText(caseCreatedText);

    }

    @Test(dependsOnMethods = "checkCreateCase",
            description = "Verify an existing case can be edited")
    public void editTestCase() {
        loginPage.open()
                .login(Config.getUser(), Config.getPassword())
                .isPageOpened()
                .clickProjectName(createdProjectName)
                .isPageOpened()
                .clickTestSuite()
                .clickTestCase(caseTitle)
                .clickEditButton()
                .clearProjectForm()
                .fillInProjectForm("QA")
                .clickSaveCaseButton()
                .isPageOpened()
                .shouldSeeCase("QA")
                .modalShouldHaveText(caseEditedText);

    }

    @Test(dependsOnMethods = "editTestCase",
            description = "UI-CASE-04 — Verify a case can be deleted with confirmation")
    public void checkDeleteCase() {
        loginPage.open()
                .login(Config.getUser(), Config.getPassword())
                .clickProjectName(createdProjectName)
                .isPageOpened()
                .clickTestSuite()
                .selectCase("QA")
                .clickDeleteButton()
                .fillInConfirm()
                .clickDeleteOnFormButton()
                .isPageOpened()
                .shouldNotSeeCase("QA")
                .modalShouldHaveText(caseDeletedText);

    }
}
