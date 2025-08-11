package io.everyonecodes.pbl_module_milihe.service;

import io.everyonecodes.pbl_module_milihe.dto.RecipeDTO;
import io.everyonecodes.pbl_module_milihe.dto.RecipeIngredientDTO;
import io.everyonecodes.pbl_module_milihe.dto.RecipeSuggestionDTO;
import io.everyonecodes.pbl_module_milihe.jpa.Ingredient;
import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import io.everyonecodes.pbl_module_milihe.jpa.RecipeIngredient;
import io.everyonecodes.pbl_module_milihe.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<RecipeDTO> findAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAllWithIngredients();
        return recipes.stream()
                .map(this::toRecipeDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<RecipeDTO> findRecipeById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findByIdWithIngredients(id);
        return recipeOptional.map(this::toRecipeDTO);
    }

    @Transactional(readOnly = true)
    public List<RecipeSuggestionDTO> findRecipesByIngredients(List<String> userIngredients) {
        List<String> userIngredientsLower = userIngredients.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        List<Recipe> allRecipes = recipeRepository.findAllWithIngredients();
        List<RecipeSuggestionDTO> suggestions = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            List<String> requiredIngredients = recipe.getIngredients().stream()
                    .map(ri -> ri.getIngredient().getName())
                    .collect(Collectors.toList());

            List<String> missingIngredients = new ArrayList<>();
            for (String required : requiredIngredients) {
                if (!userIngredientsLower.contains(required.toLowerCase())) {
                    missingIngredients.add(required);
                }
            }

            int requiredCount = requiredIngredients.size();
            int missingCount = missingIngredients.size();
            int matchedCount = requiredCount - missingCount;

            if (matchedCount > 0) {
                suggestions.add(new RecipeSuggestionDTO(
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
                ));
            }
        }

        suggestions.sort(Comparator.comparingInt(RecipeSuggestionDTO::getMissingIngredientCount));
        return suggestions;
    }

    private RecipeDTO toRecipeDTO(Recipe recipe) {
        List<RecipeIngredientDTO> recipeIngredientDTOs = recipe.getIngredients().stream()
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

    private RecipeIngredientDTO toRecipeIngredientDTO(RecipeIngredient recipeIngredient) {
        Ingredient ingredient = recipeIngredient.getIngredient();
        if (ingredient == null) {
            return new RecipeIngredientDTO(0, "Error: Ingredient not found", null, 0, "", null);
        }
        return new RecipeIngredientDTO(
                ingredient.getSpoonacularId(),
                ingredient.getName(),
                null,
                recipeIngredient.getAmount(),
                recipeIngredient.getUnit(),
                ingredient.getImage()
        );
    }
}