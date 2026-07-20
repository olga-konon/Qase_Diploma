package api.models.cases;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseErrorRs {
    public String message;
    public Map<String, List<String>> errors;
}

