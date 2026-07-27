package tests.ui;

import api.adapters.CaseAdapter;
import api.adapters.ProjectAdapter;
import api.models.cases.CaseRq;
import api.models.cases.CaseRs;
import api.models.project.ProjectRs;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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

    @BeforeMethod
    public void createFixtureProject() {
        projectName = TestDataGenerator.generateProjectName();
        projectCode = TestDataGenerator.generateProjectCode();

        ProjectRs rs = ProjectAdapter.createDefaultProject(projectName, projectCode);
        assertTrue(rs.status);

        createdProjectName = projectName;
    }

    @AfterMethod(alwaysRun = true)
    public void deleteFixtureProject() {
        ProjectAdapter.deleteProject(projectCode);
    }

    @Test(description = "UI-CASE-02 — Verify a case can be created via the form with valid data")
    public void checkCreateCase() {
        String caseTitle = TestDataGenerator.generateCaseTitle();

        loginAsDefaultUser()
                .clickProjectName(projectName)
                .isPageOpened()
                .clickCreateCaseButton()
                .clickManuallyCreateCaseButton()
                .fillInProjectForm(caseTitle)
                .clickSaveCaseButton()
                .isPageOpened()
                .shouldSeeCase(caseTitle)
                .modalShouldHaveText(caseCreatedText);

    }

    @Test(description = "UI-CASE-03 — Verify an existing case can be edited")
    public void editTestCase() {
        String caseTitle = TestDataGenerator.generateCaseTitle();
        CaseRq rq = CaseRq.builder().title(caseTitle).build();
        CaseRs rs = CaseAdapter.createTest(rq, projectCode);
        assertTrue(rs.status);

        loginAsDefaultUser()
                .clickProjectName(projectName)
                .isPageOpened()
                .clickTestSuite()
                .clickTestCase(caseTitle)
                .isPageOpened()
                .clickEditButton()
                .clearProjectForm()
                .fillInProjectForm("QA")
                .clickSaveCaseButton()
                .isPageOpened()
                .shouldSeeCase("QA")
                .modalShouldHaveText(caseEditedText);

    }

    @Test(description = "UI-CASE-04 — Verify a case can be deleted with confirmation")
    public void checkDeleteCase() {
        String caseTitle = TestDataGenerator.generateCaseTitle();
        CaseRq rq = CaseRq.builder().title(caseTitle).build();
        CaseRs rs = CaseAdapter.createTest(rq, projectCode);
        assertTrue(rs.status);

        loginAsDefaultUser()
                .clickProjectName(projectName)
                .isPageOpened()
                .clickTestSuite()
                .selectCase(caseTitle)
                .clickDeleteButton()
                .fillInConfirm()
                .clickDeleteOnFormButton()
                .isPageOpened()
                .shouldNotSeeCase(caseTitle)
                .modalShouldHaveText(caseDeletedText);

    }
}
