package io.everyonecodes.pbl_module_milihe.service;

import io.everyonecodes.pbl_module_milihe.dto.RecipeDTO;
import io.everyonecodes.pbl_module_milihe.dto.RecipeIngredientDTO;
import io.everyonecodes.pbl_module_milihe.jpa.Ingredient;
import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import io.everyonecodes.pbl_module_milihe.jpa.RecipeIngredient;
import io.everyonecodes.pbl_module_milihe.repository.IngredientRepository;
import io.everyonecodes.pbl_module_milihe.repository.RecipeIngredientRepository;
import io.everyonecodes.pbl_module_milihe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing Recipe entities and their associated DTOs.
 * This class encapsulates the business logic related to recipes,
 * orchestrates interactions with repositories, and handles the conversion
 * between JPA entities and DTOs for external consumption.
 */
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    /**
     * Constructor for RecipeService.
     * Spring automatically injects the required repository instances.
     *
     * @param recipeRepository           The repository for Recipe entities.
     * @param ingredientRepository       The repository for Ingredient entities.
     * @param recipeIngredientRepository The repository for RecipeIngredient entities.
     */
    public RecipeService(RecipeRepository recipeRepository,
                         IngredientRepository ingredientRepository,
                         RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    /**
     * Saves a new Recipe entity to the database.
     * This method directly saves the JPA entity. In more complex scenarios,
     * you might take a RecipeDTO and convert it to a Recipe entity here.
     *
     * @param recipe The Recipe entity to save.
     * @return The saved Recipe entity, potentially with an updated ID.
     */
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    /**
     * Retrieves all Recipe entities from the database and converts them into RecipeDTOs.
     * This method demonstrates fetching entities and then transforming them for the client.
     *
     * @return A list of RecipeDTOs.
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
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        return recipeOptional.map(this::toRecipeDTO);
    }

    /**
     * Converts a Recipe JPA entity into a RecipeDTO.
     * This method is responsible for mapping fields and, importantly,
     * building the list of RecipeIngredientDTOs by fetching associated ingredient details.
     *
     * @param recipe The Recipe JPA entity to convert.
     * @return The corresponding RecipeDTO.
     */
    private RecipeDTO toRecipeDTO(Recipe recipe) {

        List<RecipeIngredientDTO> recipeIngredientDTOs = recipe.getRecipeIngredients().stream()
                .map(this::toRecipeIngredientDTO)
                .collect(Collectors.toList());

        return new RecipeDTO(
                recipe.getId(),
                recipe.getSpoonacularId(),
                recipe.getTitle(),
                recipe.getReadyInMinutes(),
                recipe.getServings(),
                recipe.isVegetarian(),
                recipe.isVegan(),
                recipe.isGlutenFree(),
                recipe.isDairyFree(),
                recipe.getHealthScore(),
                recipe.getSummary(),
                recipe.getStepByStepInstruction(),
                recipe.getImage(),
                recipe.getSourceUrl(),
                recipeIngredientDTOs
        );
    }

    /**
     * Converts a RecipeIngredient JPA entity into a RecipeIngredientDTO.
     * This method fetches the corresponding Ingredient details to enrich the DTO.
     * This directly addresses Task 3's requirement to combine data.
     *
     * @param recipeIngredient The RecipeIngredient JPA entity to convert.
     * @return The corresponding RecipeIngredientDTO.
     */
    private RecipeIngredientDTO toRecipeIngredientDTO(RecipeIngredient recipeIngredient) {

        Ingredient ingredient = recipeIngredient.getIngredient();
        return new RecipeIngredientDTO(
                ingredient != null ? ingredient.getSpoonacularId() : 0,
                ingredient != null ? ingredient.getName() : "Unknown Ingredient",
                recipeIngredient.getOriginalString(),
                recipeIngredient.getAmount(),
                recipeIngredient.getUnit(),
                ingredient != null ? ingredient.getImage() : null
        );
    }
}
