package api.models.project;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectRq {
    private String title;
    private String code;
    private String description;
    private String access;
    private String group;

}
