package io.everyonecodes.pbl_module_milihe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDTO {

    private Long id;
    private int spoonacularId;
    private String name;
    private String originalString; // Renamed from 'original' to avoid conflicts if 'original' is a keyword
    private double amount;
    private String unit;
    private String image; // will need to reconstruct the full URL using Spoonacular's base image path. e.g., "baby-beets.jpg"
}
