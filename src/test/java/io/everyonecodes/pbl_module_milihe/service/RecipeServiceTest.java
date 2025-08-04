package io.everyonecodes.pbl_module_milihe.service;

import io.everyonecodes.pbl_module_milihe.dto.RecipeDTO;
import io.everyonecodes.pbl_module_milihe.dto.RecipeIngredientDTO;
import io.everyonecodes.pbl_module_milihe.dto.RecipeSuggestionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the RecipeService class.
 * This class tests the business logic in isolation from the database.
 */
class RecipeServiceTest {

    // The class we are testing
    private RecipeService recipeService;

    // The RecipeRepository is not used in the method we are testing,
    // as it calls another method in the same class (findAllRecipes).
    // We will control the data by overriding that method.

    /**
     * This method runs before each test to set up a clean environment.
     */
    @BeforeEach
    void setUp() {
        // We instantiate our service. Because the method we want to test
        // calls another public method in the same class (findAllRecipes),
        // we will override findAllRecipes here to provide controlled test data.
        // This is a simple way to test the logic of one method in isolation.
        recipeService = new RecipeService(null) { // Passing null as we won't use the real repository.
            @Override
            public List<RecipeDTO> findAllRecipes() {
                // This override ensures that when our test calls findAllRecipes(),
                // it gets this specific, controlled list of fake data.
                return createTestRecipeData();
            }
        };
    }

    /**
     * This test verifies the core recipe search logic.
     * It checks if the service correctly calculates missing ingredients
     * and sorts the results with the best matches first.
     */
    @Test
    void findRecipesByIngredients_shouldCalculateAndSortCorrectly() {
        // --- ARRANGE ---
        // Define the ingredients the user has.
        List<String> userIngredients = List.of("Tomato", "Pasta", "Onion");

        // The test data is provided by the overridden findAllRecipes() method.

        // --- ACT ---
        // Call the method we are testing.
        List<RecipeSuggestionDTO> actualSuggestions = recipeService.findRecipesByIngredients(userIngredients);

        // --- ASSERT ---
        // Verify that the results are calculated and sorted as expected.

        // We expect 3 suggestions in total.
        assertEquals(3, actualSuggestions.size());

        // The FIRST result should be "Pasta with Tomato" because it has 0 missing ingredients.
        RecipeSuggestionDTO perfectMatch = actualSuggestions.get(0);
        assertEquals("Pasta with Tomato", perfectMatch.getTitle());
        assertEquals(0, perfectMatch.getMissingIngredientCount());
        assertEquals(2, perfectMatch.getMatchedIngredientCount());
        assertEquals(List.of(), perfectMatch.getMissingIngredients()); // Empty list of missing ingredients.

        // The SECOND result should be "Onion Soup" because it has 1 missing ingredient.
        RecipeSuggestionDTO closeMatch = actualSuggestions.get(1);
        assertEquals("Onion Soup", closeMatch.getTitle());
        assertEquals(1, closeMatch.getMissingIngredientCount());
        assertEquals(1, closeMatch.getMatchedIngredientCount());
        assertEquals(List.of("Broth"), closeMatch.getMissingIngredients());

        // The THIRD result should be "Chicken Stir-fry" because it has 2 missing ingredients.
        RecipeSuggestionDTO badMatch = actualSuggestions.get(2);
        assertEquals("Chicken Stir-fry", badMatch.getTitle());
        assertEquals(2, badMatch.getMissingIngredientCount());
        assertEquals(1, badMatch.getMatchedIngredientCount());
        assertEquals(List.of("Chicken", "Soy Sauce"), badMatch.getMissingIngredients());
    }

    /**
     * A helper method to create a list of fake recipes for our test.
     * The list is deliberately unsorted to ensure our sorting logic is tested.
     *
     * @return A list of RecipeDTOs for testing.
     */
    private List<RecipeDTO> createTestRecipeData() {
        // Recipe 1: Perfect match (requires 2 ingredients, user has both)
        RecipeDTO pastaRecipe = new RecipeDTO(
                1L, 0, "Pasta with Tomato", 0, 0, false, false, false, false, 0, null, null, null, null,
                List.of(
                        new RecipeIngredientDTO(0, "Tomato", null, 0, null, null),
                        new RecipeIngredientDTO(0, "Pasta", null, 0, null, null)
                )
        );

        // Recipe 2: Close match (requires 2 ingredients, user has 1, is missing 1)
        RecipeDTO onionSoup = new RecipeDTO(
                2L, 0, "Onion Soup", 0, 0, false, true, false, false, 0, null, null, null, null,
                List.of(
                        new RecipeIngredientDTO(0, "Onion", null, 0, null, null),
                        new RecipeIngredientDTO(0, "Broth", null, 0, null, null) // This one is missing
                )
        );

        // Recipe 3: Bad match (requires 3 ingredients, user has 1, is missing 2)
        RecipeDTO stirFry = new RecipeDTO(
                3L, 0, "Chicken Stir-fry", 0, 0, false, false, false, false, 0, null, null, null, null,
                List.of(
                        new RecipeIngredientDTO(0, "Onion", null, 0, null, null),
                        new RecipeIngredientDTO(0, "Chicken", null, 0, null, null),   // Missing
                        new RecipeIngredientDTO(0, "Soy Sauce", null, 0, null, null) // Missing
                )
        );

        // Return the list in an unsorted order to properly test the sorting logic.
        return List.of(stirFry, pastaRecipe, onionSoup);
    }
}