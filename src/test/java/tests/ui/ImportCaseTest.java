package tests.ui;

import api.adapters.ProjectAdapter;
import api.models.project.ProjectRs;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.base.BaseTest;
import utils.TestDataGenerator;

import static org.testng.Assert.assertTrue;

public class ImportCaseTest extends BaseTest {

    String projectName;
    String projectCode;

    String caseImportedText = "case were successfully imported!";
    String caseInvalidFileText = "Data is invalid.";

    @BeforeMethod
    public void createFixtureProject() {
        projectName = TestDataGenerator.generateProjectName();
        projectCode = TestDataGenerator.generateProjectCode();

        ProjectRs rs = ProjectAdapter.createDefaultProject(projectName, projectCode);
        assertTrue(rs.status);

        createdProjectName = projectName;
    }

//    @AfterMethod(alwaysRun = true)
//    public void deleteFixtureProject() {
//        ProjectAdapter.deleteProject(projectCode);
//    }

    @Test(description = "UI-CASE-BULK-01 — Verify cases can be imported from file into a project")
    public void checkImportCase() {

        loginAsDefaultUser()
                .clickProjectName(projectName)
                .isPageOpened()
                .clickActionMenu()
                .isPageOpened()
                .clickImportDataButton()
                .isPageOpened()
                .uploadFile("test.json")
                .clickImportCaseButton()
                .isPageOpened()
                .clickTestSuite()
                .shouldSeeCase("TEST123")
                .modalShouldHaveText(caseImportedText);

    }

    @Test(description = "UI-CASE-BULK-04 — Verify importing invalid file (wrong type or bad row) is rejected ")
    public void checkImportCaseWithInvalidFile() {

        loginAsDefaultUser()
                .clickProjectName(projectName)
                .isPageOpened()
                .clickActionMenu()
                .isPageOpened()
                .clickImportDataButton()
                .isPageOpened()
                .uploadFile("invalid_file.json")
                .clickImportCaseButtonWithInvalidFile()
                .isPageOpened();

        casesPage.modalShouldHaveText(caseInvalidFileText);

    }

    @Test(description = "UI-CASE-BULK-02 — Verify multiple cases can be selected and bulk-updated for a shared field such as status or priority")
    public void checkBulkEdit() {
        loginAsDefaultUser()
                .clickProjectName(projectName)
                .isPageOpened()
                .clickActionMenu()
                .isPageOpened()
                .clickImportDataButton()
                .isPageOpened()
                .uploadFile("import_3cases.json")
                .clickImportCaseButton()
                .isPageOpened()
                //update
                .clickActionMenu()
                .isPageOpened()
                .clickImportDataButton()
                .isPageOpened()
                .uploadFile("import_3cases_updated.json")
                .clickCheckBox()
                .clickImportCaseButton();

        // add validation

    }

    @Test(description = "UI-CASE-BULK-03 — Verify multiple cases can be selected and bulk-deleted")
    public void checkBulkDelete() {
        loginAsDefaultUser()
                .clickProjectName(projectName)
                .isPageOpened()
                .clickActionMenu()
                .isPageOpened()
                .clickImportDataButton()
                .isPageOpened()
                .uploadFile("import_3cases.json")
                .clickImportCaseButton()
                .isPageOpened()
                .clickTestSuite();

        casesPage.closeAidenModalIfPresent();
        casesPage.selectAll()
                .clickDeleteButton()
                .fillInConfirm()
                .clickDeleteOnFormButton();

        // assertions

    }
}