package tests.api;

import api.adapters.CaseAdapter;
import api.models.cases.CaseErrorRs;
import api.models.cases.CaseRq;
import api.models.cases.CaseRs;

import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class CaseAPITest {

    private boolean caseCreated = false;
    // add project creating method
    String code = "QA";
    int id;
    String title;

    @Test(priority = 1,
            description = "API-CASE-04 — Verify `POST /case/{code}` creates a case with valid data")
    public void createTest() {
        title = "1";
        CaseRq rq = CaseRq.builder()
                .title(title)
                .build();

        CaseRs rs = CaseAdapter.createTest(rq, code);
        assertTrue(rs.status);
        assertNotNull(rs.result.id);
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
        assertEquals(rs.result.id, id);
        assertEquals(rs.result.title, title);


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
        assertTrue(rs.status);


    }

    @Test(dependsOnMethods = "updateCaseByIdAndCode",
            description = "API-CASE-07 — Verify `GET /case/{code}/{id}` returns data for an existing case")
    public void getTestCaseByIdAndCodeAfterUpdate() {
        if (!caseCreated) {
            throw new SkipException("No test case was created — nothing to fetch");
        }

        CaseRs rs = CaseAdapter.getCaseByCodeAndID(code, id);
        assertEquals(rs.result.id, id);
        assertEquals(rs.result.title, title);


    }

    @Test(priority = 5, description = "API-CASE-05 — Verify `POST /case/{code}` returns an error when the title is empty")
    public void createTestWithEmptyTitle() {
        CaseRq rq = CaseRq.builder()
                .title(" ")
                .build();

        CaseErrorRs rs = CaseAdapter.createCaseExpectingError(rq, code);
        assertEquals(rs.message, "The title field is required.");
        assertTrue(rs.errors.get("title").contains("The title field is required."));
    }

    @Test(priority = 4,
            description = "API-CASE-06 — Verify `DELETE /case/{code}/{id}` deletes an existing case")

    public void deleteCaseByCodeAndId() {

        if (!caseCreated) {
            throw new SkipException("No test case was created — nothing to fetch");
        }
        CaseRs rs = CaseAdapter.deleteCaseByCodeAndID(code, id);
        assertTrue(rs.status);
        caseCreated = false;
        assertEquals(rs.result.id, id);
    }
}
