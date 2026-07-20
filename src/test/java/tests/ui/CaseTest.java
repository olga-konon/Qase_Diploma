package tests.ui;

import api.adapters.ProjectAdapter;
import api.models.project.ProjectRq;
import api.models.project.ProjectRs;
import helpers.Config;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.api.ProjectAPITest;
import tests.baseTests.BaseTest;
import utils.TestDataGenerator;

import static org.testng.Assert.assertTrue;


public class CaseTest extends BaseTest {


    @Test(description = "UI-CASE-02 — Verify a case can be created via the form with valid data")
    public void checkCreateCase() {

        String projectName = TestDataGenerator.generateProjectName();
        String projectCode = TestDataGenerator.generateProjectCode();

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

        // move to datagenerator
        String caseTitle = "Case " + System.currentTimeMillis();

        loginPage.open()
                .login(Config.getUser(), Config.getPassword())
                .isPageOpened()
                .clickProjectName(projectName)
                .isPageOpened()
                .clickCreateCaseButton()
                .fillInProjectForm(caseTitle)
                .clickSaveCaseButton();

        // add assertion

    }

    @Test(description = "Verify an existing case can be edited")
    public void editTestCase() {

    }

}
