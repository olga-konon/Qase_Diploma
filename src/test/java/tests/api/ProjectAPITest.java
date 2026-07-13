package tests.api;

import api.adapters.ProjectAdapter;
import api.models.project.ProjectRq;
import api.models.project.ProjectRs;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ProjectAPITest {

    private final String CODE = "QA";

    @Test(description = "API-PRJ-07 — Verify `POST /project` creates a project with valid data")
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
    }

    @AfterMethod
    public void deleteProject() {
        ProjectAdapter.deleteProject(CODE);
    }
}
