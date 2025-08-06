package io.everyonecodes.pbl_module_milihe.service;

import io.everyonecodes.pbl_module_milihe.dto.RecipeDTO;
import io.everyonecodes.pbl_module_milihe.dto.RecipeIngredientDTO;
import io.everyonecodes.pbl_module_milihe.dto.RecipeSuggestionDTO;
import io.everyonecodes.pbl_module_milihe.jpa.Ingredient;
import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import io.everyonecodes.pbl_module_milihe.jpa.RecipeIngredient;
import io.everyonecodes.pbl_module_milihe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Comparator;

/**
 * Service class for managing Recipe entities and their associated DTOs.
 */
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * Retrieves all recipes from the database and converts them into a list of RecipeDTOs.
     * This operation eagerly fetches all associated ingredients to ensure efficiency.
     *
     * @return A list of RecipeDTOs representing all recipes.
     */
    public List<RecipeDTO> findAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAllWithIngredients();
        return recipes.stream()
                .map(this::toRecipeDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single Recipe entity by its ID and converts it into a RecipeDTO.
     *
     * @param id The ID of the recipe to retrieve.
     * @return An Optional containing the RecipeDTO if found, or an empty Optional if not.
     */
    public Optional<RecipeDTO> findRecipeById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findByIdWithIngredients(id);
        return recipeOptional.map(this::toRecipeDTO);
    }

    /**
     * Converts a Recipe entity into its corresponding RecipeDTO.
     * This method handles the mapping of all fields and the conversion of the
     * associated ingredients list.
     *
     * @param recipe The Recipe entity to convert.
     * @return The fully populated RecipeDTO.
     */
    private RecipeDTO toRecipeDTO(Recipe recipe) {
        List<RecipeIngredientDTO> recipeIngredientDTOs = recipe.getIngredients().stream()
                .map(this::toRecipeIngredientDTO)
                .collect(Collectors.toList());


        return new RecipeDTO(
                recipe.getId(),
                0,
                recipe.getTitle(),
                0,
                0,
                false,
                recipe.isVegan(),
                false,
                false,
                0,
                null,
                null,
                null,
                null,
                recipeIngredientDTOs
        );
    }

    /**
     * Converts a RecipeIngredient entity into its corresponding RecipeIngredientDTO.
     * This method creates a "flattened" DTO that includes details from the
     * associated Ingredient entity, such as its name and image.
     *
     * @param recipeIngredient The RecipeIngredient entity to convert.
     * @return The corresponding RecipeIngredientDTO.
     */
    private RecipeIngredientDTO toRecipeIngredientDTO(RecipeIngredient recipeIngredient) {
        Ingredient ingredient = recipeIngredient.getIngredient();
        return new RecipeIngredientDTO(
                0,
                ingredient != null ? ingredient.getName() : "Unknown Ingredient",
                null,
                recipeIngredient.getAmount(),
                recipeIngredient.getUnit(),
                ingredient != null ? ingredient.getImage() : null
        );
    }

    public List<RecipeSuggestionDTO> findRecipesByIngredients(List<String> userIngredients) {

        List<RecipeDTO> allRecipes = findAllRecipes();
        List<RecipeSuggestionDTO> suggestions = new ArrayList<>();

        for (RecipeDTO recipe : allRecipes) {
            List<String> requiredIngredients = recipe.getExtendedIngredients().stream()
                    .map(RecipeIngredientDTO::getName)
                    .collect(Collectors.toList());

            List<String> missingIngredients = new ArrayList<>();
            for (String required : requiredIngredients) {
                if (!userIngredients.contains(required)) {
                    missingIngredients.add(required);
                }
            }

            int requiredCount = requiredIngredients.size();
            int missingCount = missingIngredients.size();
            int matchedCount = requiredCount - missingCount;

            RecipeSuggestionDTO suggestion = new RecipeSuggestionDTO(
                    recipe.getId(),
                    recipe.getSpoonacularId(),
                    recipe.getTitle(),
                    recipe.getImage(),
                    matchedCount,
                    missingCount,
                    missingIngredients,
                    recipe.isVegetarian(),
                    recipe.isVegan(),
                    recipe.isGlutenFree()
            );

            suggestions.add(suggestion);
        }

        suggestions.sort(Comparator.comparingInt(RecipeSuggestionDTO::getMissingIngredientCount));

        return suggestions;
    }


}