package io.everyonecodes.pbl_module_milihe.service;

import io.everyonecodes.pbl_module_milihe.dto.spoonacular.SpoonacularRecipeResult;
import io.everyonecodes.pbl_module_milihe.dto.spoonacular.SpoonacularSearchResponse;
import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import io.everyonecodes.pbl_module_milihe.repository.RecipeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataSyncService {

    private final SpoonacularApiService spoonacularApiService;
    private final RecipeRepository recipeRepository;

    public DataSyncService(SpoonacularApiService spoonacularApiService, RecipeRepository recipeRepository) {
        this.spoonacularApiService = spoonacularApiService;
        this.recipeRepository = recipeRepository;
    }

    /**
     * Fetches recipes from Spoonacular and saves them to the local database.
     * This method is now scheduled to run automatically based on the cron expression.
     */
    //@Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void syncRecipes() {
        System.out.println("--- [SCHEDULED TASK] Starting Spoonacular Data Sync ---");

        SpoonacularSearchResponse response = spoonacularApiService.searchRecipes("italian");

        if (response == null || response.getResults() == null) {
            System.out.println("[SCHEDULED TASK] Failed to fetch recipes from Spoonacular. Aborting sync.");
            return;
        }

        for (SpoonacularRecipeResult spoonacularRecipe : response.getResults()) {
            boolean recipeExists = recipeRepository.existsBySpoonacularId(spoonacularRecipe.getId());

            if (recipeExists) {
                System.out.println("[SCHEDULED TASK] Recipe '" + spoonacularRecipe.getTitle() + "' already exists. Skipping.");
                continue;
            }

            Recipe recipeEntity = convertToRecipeEntity(spoonacularRecipe);
            recipeRepository.save(recipeEntity);
            System.out.println("[SCHEDULED TASK] Saved new recipe: " + recipeEntity.getTitle());
        }

        System.out.println("--- [SCHEDULED TASK] Data Sync Finished ---");
    }

    /**
     * Helper method to map a Spoonacular DTO to our Recipe entity.
     */
    private Recipe convertToRecipeEntity(SpoonacularRecipeResult dto) {
        Recipe recipe = new Recipe();
        recipe.setSpoonacularId(dto.getId());
        recipe.setTitle(dto.getTitle());
        recipe.setImage(dto.getImage());
        recipe.setReadyInMinutes(0);
        recipe.setServings(0);
        recipe.setVegetarian(false);
        recipe.setVegan(false);
        recipe.setGlutenFree(false);
        recipe.setDairyFree(false);
        recipe.setHealthScore(0);
        return recipe;
    }
}