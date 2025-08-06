package io.everyonecodes.pbl_module_milihe.dto.spoonacular;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoonacularRecipeResult {
    private int id; // spoonacularId
    private String title;
    private String image;
}
