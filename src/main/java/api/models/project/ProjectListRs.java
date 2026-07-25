package api.models.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectListRs {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("result")
    @Expose
    public ListResult result;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ListResult {
        @SerializedName("entities")
        @Expose
        public List<Result> entities;
    }
}
