package io.everyonecodes.pbl_module_milihe.controller;

import io.everyonecodes.pbl_module_milihe.dto.RecipeDTO;
import io.everyonecodes.pbl_module_milihe.dto.RecipeSuggestionDTO;
import io.everyonecodes.pbl_module_milihe.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST Controller for handling all public, recipe-related API requests.
 * This is the main entry point for the application's user-facing features.
 */
@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Endpoint to get a list of all vegan recipes.
     * @return A list of RecipeDTOs for all vegan recipes.
     */
    @GetMapping("/vegan")
    public List<RecipeDTO> getVeganRecipes() {
        return recipeService.findAllRecipes().stream()
                .filter(RecipeDTO::isVegan)
                .collect(Collectors.toList());
    }

    /**
     * Endpoint to find recipes based on a list of provided ingredients.
     * @param ingredientNames A list of ingredient names provided by the user.
     * @return A sorted list of recipe suggestions.
     */
    @PostMapping("/find")
    public List<RecipeSuggestionDTO> findRecipesByIngredients(@RequestBody List<String> ingredientNames) {
        return recipeService.findRecipesByIngredients(ingredientNames);
    }

    /**
     * Endpoint to retrieve the full details of a single recipe by its ID.
     *
     * @param id The ID of the recipe, passed in the URL path.
     * @return A ResponseEntity containing the RecipeDTO and a 200 OK status,
     *         or an empty body with a 404 Not Found status if the recipe does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        Optional<RecipeDTO> optionalRecipeDTO = recipeService.findRecipeById(id);

        return optionalRecipeDTO
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}