package io.everyonecodes.pbl_module_milihe.configuration;

import io.everyonecodes.pbl_module_milihe.dto.spoonacular.SpoonacularExtendedIngredientDTO;
import io.everyonecodes.pbl_module_milihe.dto.spoonacular.SpoonacularRecipeInformationDTO;
import io.everyonecodes.pbl_module_milihe.dto.spoonacular.SpoonacularRecipeResult;
import io.everyonecodes.pbl_module_milihe.dto.spoonacular.SpoonacularSearchResponse;
import io.everyonecodes.pbl_module_milihe.jpa.Ingredient;
import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import io.everyonecodes.pbl_module_milihe.jpa.RecipeIngredient;
import io.everyonecodes.pbl_module_milihe.repository.IngredientRepository;
import io.everyonecodes.pbl_module_milihe.repository.RecipeRepository;
import io.everyonecodes.pbl_module_milihe.service.SpoonacularApiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    CommandLineRunner initializeDatabase(
            RecipeRepository recipeRepository,
            IngredientRepository ingredientRepository,
            SpoonacularApiService spoonacularApiService
    ) {
        return args -> {
            System.out.println("--- [INITIALIZER] Checking if database needs seeding ---");

            if (recipeRepository.count() > 0) {
                System.out.println("[INITIALIZER] Database already has data. Skipping.");
                return;
            }

            System.out.println("[INITIALIZER] Database is empty. Seeding with data from Spoonacular...");

            SpoonacularSearchResponse searchResponse = spoonacularApiService.searchRecipes("avocado");

            if (searchResponse == null || searchResponse.getResults() == null) {
                System.err.println("[INITIALIZER] Failed to fetch recipe list from Spoonacular!");
                return;
            }

            Map<String, Ingredient> savedIngredientsCache = new HashMap<>();

            for (SpoonacularRecipeResult recipeSummary : searchResponse.getResults()) {

                System.out.println("[INITIALIZER] Fetching full details for recipe: " + recipeSummary.getTitle());
                SpoonacularRecipeInformationDTO recipeDetails = spoonacularApiService.getRecipeDetails(recipeSummary.getId());

                if (recipeDetails != null && !recipeRepository.existsBySpoonacularId(recipeDetails.getId())) {

                    Recipe recipeEntity = convertToRecipeEntity(recipeDetails);

                    if (recipeDetails.getExtendedIngredients() != null) {
                        for (SpoonacularExtendedIngredientDTO ingredientDTO : recipeDetails.getExtendedIngredients()) {
                            Ingredient ingredientEntity = findOrCreateIngredient(ingredientDTO, ingredientRepository, savedIngredientsCache);

                            RecipeIngredient link = new RecipeIngredient(
                                    ingredientDTO.getAmount(),
                                    ingredientDTO.getUnit(),
                                    ingredientEntity,
                                    recipeEntity
                            );
                            recipeEntity.getIngredients().add(link);
                        }
                    }

                    recipeRepository.save(recipeEntity);
                    System.out.println("[INITIALIZER] Saved new recipe with full details: " + recipeEntity.getTitle());
                }
            }

            System.out.println("--- [INITIALIZER] Database Seeding Complete ---");
        };
    }

    private Recipe convertToRecipeEntity(SpoonacularRecipeInformationDTO dto) {
        Recipe recipe = new Recipe();
        recipe.setSpoonacularId(dto.getId());
        recipe.setTitle(dto.getTitle());
        recipe.setImage(dto.getImage());
        recipe.setReadyInMinutes(dto.getReadyInMinutes());
        recipe.setServings(dto.getServings());
        recipe.setSummary(dto.getSummary());
        recipe.setStepByStepInstruction(dto.getInstructions());
        recipe.setVegetarian(dto.isVegetarian());
        recipe.setVegan(dto.isVegan());
        recipe.setGlutenFree(dto.isGlutenFree());
        recipe.setDairyFree(dto.isDairyFree());
        recipe.setHealthScore(dto.getHealthScore());
        recipe.setSourceUrl(dto.getSourceUrl());
        return recipe;
    }

    private Ingredient findOrCreateIngredient(SpoonacularExtendedIngredientDTO dto, IngredientRepository repo, Map<String, Ingredient> cache) {
        String nameLower = dto.getName().toLowerCase();
        if (cache.containsKey(nameLower)) {
            return cache.get(nameLower);
        }
        Optional<Ingredient> existing = repo.findByNameIgnoreCase(nameLower);
        if (existing.isPresent()) {
            cache.put(nameLower, existing.get());
            return existing.get();
        }
        Ingredient newIngredient = new Ingredient(dto.getName(), dto.getImage());
        repo.save(newIngredient);
        cache.put(nameLower, newIngredient);
        return newIngredient;
    }
}