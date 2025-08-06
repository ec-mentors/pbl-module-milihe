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

    private RecipeService recipeService;


    /**
     * This method runs before each test to set up a clean environment.
     */
    @BeforeEach
    void setUp() {

        recipeService = new RecipeService(null) {
            @Override
            public List<RecipeDTO> findAllRecipes() {
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

        List<String> userIngredients = List.of("Tomato", "Pasta", "Onion");

        List<RecipeSuggestionDTO> actualSuggestions = recipeService.findRecipesByIngredients(userIngredients);

        assertEquals(3, actualSuggestions.size());

        RecipeSuggestionDTO perfectMatch = actualSuggestions.get(0);
        assertEquals("Pasta with Tomato", perfectMatch.getTitle());
        assertEquals(0, perfectMatch.getMissingIngredientCount());
        assertEquals(2, perfectMatch.getMatchedIngredientCount());
        assertEquals(List.of(), perfectMatch.getMissingIngredients());
        RecipeSuggestionDTO closeMatch = actualSuggestions.get(1);
        assertEquals("Onion Soup", closeMatch.getTitle());
        assertEquals(1, closeMatch.getMissingIngredientCount());
        assertEquals(1, closeMatch.getMatchedIngredientCount());
        assertEquals(List.of("Broth"), closeMatch.getMissingIngredients());

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

        RecipeDTO pastaRecipe = new RecipeDTO(
                1L, 0, "Pasta with Tomato", 0, 0, false, false, false, false, 0, null, null, null, null,
                List.of(
                        new RecipeIngredientDTO(0, "Tomato", null, 0, null, null),
                        new RecipeIngredientDTO(0, "Pasta", null, 0, null, null)
                )
        );

        RecipeDTO onionSoup = new RecipeDTO(
                2L, 0, "Onion Soup", 0, 0, false, true, false, false, 0, null, null, null, null,
                List.of(
                        new RecipeIngredientDTO(0, "Onion", null, 0, null, null),
                        new RecipeIngredientDTO(0, "Broth", null, 0, null, null)
                )
        );

        RecipeDTO stirFry = new RecipeDTO(
                3L, 0, "Chicken Stir-fry", 0, 0, false, false, false, false, 0, null, null, null, null,
                List.of(
                        new RecipeIngredientDTO(0, "Onion", null, 0, null, null),
                        new RecipeIngredientDTO(0, "Chicken", null, 0, null, null),
                        new RecipeIngredientDTO(0, "Soy Sauce", null, 0, null, null)
                )
        );

        return List.of(stirFry, pastaRecipe, onionSoup);
    }
}