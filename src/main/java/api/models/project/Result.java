package api.models.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("title")
    @Expose
    public String title;
}
