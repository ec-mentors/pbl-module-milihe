package io.everyonecodes.pbl_module_milihe.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * A lightweight DTO for recipe suggestions, using Lombok to reduce boilerplate.
 * The @Data annotation provides getters, setters, toString(), equals(), and hashCode().
 * The @NoArgsConstructor and @AllArgsConstructor provide the respective constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSuggestionDTO {
    private Long id;
    private int spoonacularId;
    private String title;
    private String image;
    private int matchedIngredientCount;
    private int missingIngredientCount;
    private List<String> missingIngredients;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
}