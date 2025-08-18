package io.everyonecodes.pbl_module_milihe.dto.spoonacular;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * DTO to represent the full, detailed information for a single recipe
 * as returned by Spoonacular's "Get Recipe Information" endpoint.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoonacularRecipeInformationDTO {
    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String image;
    private String summary;
    private String instructions;
    private List<SpoonacularExtendedIngredientDTO> extendedIngredients;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private int healthScore;
    private String sourceUrl;
}