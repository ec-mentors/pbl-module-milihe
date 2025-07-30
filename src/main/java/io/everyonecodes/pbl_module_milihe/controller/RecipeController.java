package io.everyonecodes.pbl_module_milihe.controller;

import io.everyonecodes.pbl_module_milihe.dto.RecipeDTO;
import io.everyonecodes.pbl_module_milihe.service.RecipeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for handling recipe-related API requests.
 * This controller only interacts with the Service layer.
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Endpoint to get a list of all vegan recipes.
     * This demonstrates a simple filtering logic.
     * @return A list of RecipeDTOs for all vegan recipes.
     */
    @GetMapping("/vegan")
    public List<RecipeDTO> getVeganRecipes() {
        List<RecipeDTO> allRecipes = recipeService.findAllRecipes();

        return allRecipes.stream()
                .filter(RecipeDTO::isVegan)
                .collect(Collectors.toList());
    }
}