package tests.api;

import api.adapters.CaseAdapter;
import api.adapters.ProjectAdapter;
import api.models.cases.CaseErrorRs;
import api.models.cases.CaseRq;
import api.models.cases.CaseRs;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utils.TestDataGenerator;

import static org.testng.Assert.*;

public class CaseAPITest {

    private boolean caseCreated = false;
    String code;
    int id;
    String title;
    String expectedErrorMessageMissingTitle ="The title field is required.";

    @BeforeClass
    public void createFixtureProject() {
        code = TestDataGenerator.generateProjectCode();
        ProjectAdapter.createDefaultProject(TestDataGenerator.generateProjectName(), code);
    }

    @AfterClass(alwaysRun = true)
    public void deleteFixtureProject() {
        ProjectAdapter.deleteProject(code);
    }

    @Test(priority = 1,
            description = "API-CASE-04 — Verify `POST /case/{code}` creates a case with valid data")
    public void createTest() {
        title = "1";
        CaseRq rq = CaseRq.builder()
                .title(title)
                .build();

        CaseRs rs = CaseAdapter.createTest(rq, code);
        assertTrue(rs.status, "Case should be created");
        assertNotNull(rs.result.id, "Case id should not be null");
        id = rs.result.id;

        caseCreated = true;

    }

    @Test(dependsOnMethods = "createTest",
            description = "API-CASE-07 — Verify `GET /case/{code}/{id}` returns data for an existing case")
    public void getTestCaseByIdAndCode() {
        if (!caseCreated) {
            throw new SkipException("No test case was created — nothing to fetch");
        }

        CaseRs rs = CaseAdapter.getCaseByCodeAndID(code, id);
        assertEquals(rs.result.id, id, "Case id should match");
        assertEquals(rs.result.title, title, "Case title should match");
    }

    @Test(priority = 3,
            description = "API-CASE-09 — Verify `PATCH /case/{code}/{id}` updates an existing case with valid data")
    public void updateCaseByIdAndCode() {
        if (!caseCreated) {
            throw new SkipException("No test case was created — nothing to fetch");
        }
        title = "2";
        CaseRq rq = CaseRq.builder()
                .title(title)
                .build();

        CaseRs rs = CaseAdapter.updateCaseByCodeAndID(rq, code, id);
        assertTrue(rs.status, "Case should be updated");


    }

    @Test(dependsOnMethods = "updateCaseByIdAndCode",
            description = "API-CASE-07 — Verify `GET /case/{code}/{id}` returns data for an existing case")
    public void getTestCaseByIdAndCodeAfterUpdate() {
        if (!caseCreated) {
            throw new SkipException("No test case was created — nothing to fetch");
        }

        CaseRs rs = CaseAdapter.getCaseByCodeAndID(code, id);
        assertEquals(rs.result.id, id, "Case id should match");
        assertEquals(rs.result.title, title, "Case title should be updated");
    }

    @Test(priority = 5, description = "API-CASE-05 — Verify `POST /case/{code}` returns an error when the title is empty")
    public void createTestWithEmptyTitle() {
        CaseRq rq = CaseRq.builder()
                .title(" ")
                .build();

        CaseErrorRs rs = CaseAdapter.createCaseExpectingError(rq, code);
        assertEquals(rs.message, expectedErrorMessageMissingTitle, "Title is required");
        assertTrue(rs.errors.get("title").contains("The title field is required."), "Title error should be listed");
    }

    @Test(priority = 4,
            description = "API-CASE-06 — Verify `DELETE /case/{code}/{id}` deletes an existing case")

    public void deleteCaseByCodeAndId() {

        if (!caseCreated) {
            throw new SkipException("No test case was created — nothing to fetch");
        }
        CaseRs rs = CaseAdapter.deleteCaseByCodeAndID(code, id);
        assertTrue(rs.status, "Case should be deleted");
        caseCreated = false;
        assertEquals(rs.result.id, id, "Deleted case id should match");
    }
}
