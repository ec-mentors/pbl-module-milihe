package io.everyonecodes.pbl_module_milihe;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RecipeIngredient {
    private int recipeId;
    private int ingredientId;
    private double amount;
    private String unit;
}
