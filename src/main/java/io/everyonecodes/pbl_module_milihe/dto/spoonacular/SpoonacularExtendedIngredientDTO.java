package io.everyonecodes.pbl_module_milihe.dto.spoonacular;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO to represent a single, detailed ingredient within the
 * SpoonacularRecipeInformationDTO's 'extendedIngredients' list.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoonacularExtendedIngredientDTO {
    private int id;
    private String name;
    private double amount;
    private String unit;
    private String original;
    private String image;
}