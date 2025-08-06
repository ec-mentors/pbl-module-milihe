package io.everyonecodes.pbl_module_milihe.controller;

import io.everyonecodes.pbl_module_milihe.dto.spoonacular.SpoonacularSearchResponse;
import io.everyonecodes.pbl_module_milihe.jpa.Ingredient;
import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import io.everyonecodes.pbl_module_milihe.jpa.RecipeIngredient;
import io.everyonecodes.pbl_module_milihe.repository.IngredientRepository;
import io.everyonecodes.pbl_module_milihe.repository.RecipeIngredientRepository;
import io.everyonecodes.pbl_module_milihe.repository.RecipeRepository;
import io.everyonecodes.pbl_module_milihe.service.SpoonacularApiService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * A controller with special endpoints for development and testing purposes.
 * This should be disabled or removed in a production environment.
 */
@RestController
@RequestMapping("/api/setup")
public class DataSetupController {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final SpoonacularApiService spoonacularApiService;

    public DataSetupController(RecipeRepository recipeRepository,
                               IngredientRepository ingredientRepository,
                               RecipeIngredientRepository recipeIngredientRepository,
                               SpoonacularApiService spoonacularApiService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.spoonacularApiService = spoonacularApiService;
    }

    /**
     * Endpoint to create a static, predefined set of test data in the database.
     * This is useful for predictable manual and automated testing.
     * NOTE: This is a POST request because it modifies data.
     */
    @PostMapping("/data")
    @Transactional
    public String setupData() {
        recipeIngredientRepository.deleteAll();
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();

        Ingredient tomato = ingredientRepository.save(new Ingredient("Tomato", "tomato.jpg"));
        Ingredient pasta = ingredientRepository.save(new Ingredient("Pasta", "pasta.jpg"));
        Ingredient lentil = ingredientRepository.save(new Ingredient("Lentils", "lentils.jpg"));
        Ingredient onion = ingredientRepository.save(new Ingredient("Onion", "onion.jpg"));

        Recipe pastaRecipe = recipeRepository.save(new Recipe("Classic Pasta", false));
        Recipe lentilSoup = recipeRepository.save(new Recipe("Lentil Soup", true));

        RecipeIngredient pastaLink = new RecipeIngredient(200, "g", pasta, pastaRecipe);
        RecipeIngredient tomatoLink = new RecipeIngredient(400, "g", tomato, pastaRecipe);
        RecipeIngredient lentilLink = new RecipeIngredient(150, "g", lentil, lentilSoup);
        RecipeIngredient onionLink = new RecipeIngredient(1, "piece", onion, lentilSoup);

        recipeIngredientRepository.saveAll(List.of(pastaLink, tomatoLink, lentilLink, onionLink));

        return "Test data has been created successfully!";
    }

    /**
     * A temporary endpoint to test the Spoonacular API client.
     * When visited, it calls the service and returns the resulting DTO.
     * @return The SpoonacularSearchResponse DTO, which gets converted to JSON.
     */
    @GetMapping("/test-spoonacular")
    public SpoonacularSearchResponse testSpoonacular() {
        System.out.println("--- TRIGGERING SPOONACULAR API TEST ---");
        return spoonacularApiService.searchRecipes("pizza");
    }
}