package io.everyonecodes.pbl_module_milihe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {

    private Long id;
    private int spoonacularId;
    private String title;
    private int readyInMinutes;
    private int servings;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private int healthScore;
    private String summary;
    private String stepByStepInstruction;
    private String image;
}
