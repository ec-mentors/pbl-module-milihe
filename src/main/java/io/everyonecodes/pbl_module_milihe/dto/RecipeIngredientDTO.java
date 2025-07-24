package io.everyonecodes.pbl_module_milihe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for transferring RecipeIngredient data, combining details
 * from both RecipeIngredient and Ingredient entities for frontend display.
 * The 'id' field has been removed as the JPA entity uses a composite key
 * and does not expose a simple Long ID for this join entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDTO {
    // private Long id;
    private int spoonacularId;
    private String name;
    private String originalString;
    private double amount;
    private String unit;
    private String image;
}
