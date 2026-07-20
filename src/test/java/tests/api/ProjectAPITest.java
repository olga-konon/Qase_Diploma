package tests.api;

import api.adapters.ProjectAdapter;
import api.models.project.ProjectErrorRs;
import api.models.project.ProjectRq;
import api.models.project.ProjectRs;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ProjectAPITest {

    private final String CODE = "QA";
    private boolean projectCreated = false;

    @Test(priority = 1,
            description = "API-PRJ-07 — Verify `POST /project` creates a project with valid data")
    public void createProject() {
        ProjectRq rq = ProjectRq.builder()
                .title("QA34")
                .code(CODE)
                .description("test")
                .access("all")
                .group("test")
                .build();

        ProjectRs rs = ProjectAdapter.createProject(rq);
        assertTrue(rs.status);
        assertEquals(rs.result.code, "QA");
        projectCreated = true;
    }

    @Test(priority = 2,
            description = "API-PRJ-05 — Verify `GET /project/{code}` returns data for an existing project")
    public void getProjectByCode() {
        if (!projectCreated) {
            throw new SkipException("No project was created — nothing to fetch");
        }
        ProjectRs rs = ProjectAdapter.getProjectByCode(CODE);
        assertTrue(rs.status);
        assertEquals(rs.result.code, CODE);
        assertEquals(rs.result.title, "QA34");

    }

    @Test(description = "API-PRJ-08 — Verify `POST /project` returns an error when a required field is missing",
            priority = 3)
    public void createProjectWithEmptyField() {

        ProjectRq rq = ProjectRq.builder()
                .title("")
                .code(CODE)
                .description("test")
                .access("all")
                .group("test")
                .build();

        ProjectRs rs = ProjectAdapter.createProjectExpectingError(rq);

        assertFalse(rs.status);
        assertEquals(rs.errorMessage, "Data is invalid.");
    }


    @Test(priority = 4)
    public void deleteProjectByCode() {
        if (!projectCreated) {
            throw new SkipException("No project was created — nothing to fetch");
        }

        ProjectRs rs = ProjectAdapter.deleteProject(CODE);
        assertTrue(rs.status);
        projectCreated = false;

    }

    @Test(priority = 5, description = "API-PRJ-06 — Verify `GET /project/{code}` returns an error for a non-existent project code")
    public void getProjectByNonExistingCode() {
        String nonExistingCode = "NOPE404";
        ProjectRs rs = ProjectAdapter.getProjectByNonExistingCode(nonExistingCode);
        assertFalse(rs.status);
    }

    @Test(priority = 6,
            description = "API-AUTH-01 — Verify requests with a missing or invalid token are rejected")
    public void getProjectByCodeWithInValidToken() {

        ProjectErrorRs rs = ProjectAdapter.getProjectByCodeWithNotValidToken(CODE);
        assertEquals(rs.error, "Unauthenticated.");


    }

}
