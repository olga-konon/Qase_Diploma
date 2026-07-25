package listeners;

import api.adapters.ProjectAdapter;
import api.models.project.ProjectListRs;
import lombok.extern.log4j.Log4j2;
import org.testng.ISuite;
import org.testng.ISuiteListener;

@Log4j2
public class ProjectCleanupSuiteListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        ProjectListRs existing = ProjectAdapter.getAllProjects();
        if (existing.result == null || existing.result.entities == null) {
            return;
        }

        existing.result.entities.forEach(project -> {
            try {
                ProjectAdapter.deleteProject(project.code);
                log.info("Cleaned up leftover project {}", project.code);
            } catch (Exception e) {
                log.error("Failed to clean up leftover project {}: {}", project.code, e.getMessage());
            }
        });
    }
}
