package io.everyonecodes.pbl_module_milihe.dto.spoonacular;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoonacularSearchResponse {
    private List<SpoonacularRecipeResult> results;
    private int totalResults;
}