package io.everyonecodes.pbl_module_milihe.configuration;

import io.everyonecodes.pbl_module_milihe.jpa.Ingredient;
import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import io.everyonecodes.pbl_module_milihe.jpa.RecipeIngredient;
import io.everyonecodes.pbl_module_milihe.repository.IngredientRepository;
import io.everyonecodes.pbl_module_milihe.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * This class is responsible for seeding the database with initial
 * test data when the application starts.
 * The @Configuration and @Bean annotations ensure it runs automatically.
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeDatabase(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        return args -> {

            if (recipeRepository.count() > 0) {
                System.out.println("--- [INITIALIZER] Database already contains data. Skipping data seeding. ---");
                return;
            }

            System.out.println("--- [INITIALIZER] Seeding Database with Full Details ---");

            Ingredient tomato = ingredientRepository.save(new Ingredient("Tomato", "https://placehold.co/100x100/f35b5b/ffffff?text=Tomato"));
            Ingredient pasta = ingredientRepository.save(new Ingredient("Pasta", "https://placehold.co/100x100/f5cba7/ffffff?text=Pasta"));
            Ingredient onion = ingredientRepository.save(new Ingredient("Onion", "https://placehold.co/100x100/d7bde2/ffffff?text=Onion"));
            Ingredient lentil = ingredientRepository.save(new Ingredient("Lentils", "https://placehold.co/100x100/a9dfbf/ffffff?text=Lentils"));

            Recipe pastaRecipe = new Recipe("Classic Pasta", false);
            pastaRecipe.setImage("https://placehold.co/600x400/f5b041/ffffff?text=Pasta+Dish");
            pastaRecipe.setSummary("A simple and delicious pasta dish, perfect for a quick weeknight meal.");
            pastaRecipe.setStepByStepInstruction(
                    "1. Boil water in a large pot. Add salt and pasta. Cook according to package directions.\n" +
                            "2. While pasta is cooking, sauté chopped onion in a pan with olive oil.\n" +
                            "3. Add canned tomatoes and simmer for 10 minutes. Season with salt and pepper.\n" +
                            "4. Drain pasta and combine with the sauce. Serve immediately."
            );
            pastaRecipe.setVegetarian(true);
            pastaRecipe.setDairyFree(false);
            pastaRecipe.setGlutenFree(false);

            pastaRecipe.getIngredients().add(new RecipeIngredient(200, "g", pasta, pastaRecipe));
            pastaRecipe.getIngredients().add(new RecipeIngredient(400, "g", tomato, pastaRecipe));
            pastaRecipe.getIngredients().add(new RecipeIngredient(1, "pc", onion, pastaRecipe));

            Recipe lentilSoup = new Recipe("Lentil Soup", true);
            lentilSoup.setImage("https://placehold.co/600x400/27ae60/ffffff?text=Lentil+Soup");
            lentilSoup.setSummary("A hearty and healthy vegan soup, packed with protein and fiber.");
            lentilSoup.setStepByStepInstruction(
                    "1. Finely chop the onion.\n" +
                            "2. In a large pot, sauté the onion until soft.\n" +
                            "3. Add the lentils and 4 cups of vegetable broth. Bring to a boil.\n" +
                            "4. Reduce heat and simmer for 25-30 minutes, or until lentils are tender. Season to taste."
            );
            lentilSoup.setVegetarian(true);
            lentilSoup.setDairyFree(true);
            lentilSoup.setGlutenFree(true);

            lentilSoup.getIngredients().add(new RecipeIngredient(150, "g", lentil, lentilSoup));
            lentilSoup.getIngredients().add(new RecipeIngredient(1, "pc", onion, lentilSoup));

            recipeRepository.saveAll(List.of(pastaRecipe, lentilSoup));

            System.out.println("--- [INITIALIZER] Database Seeding Complete ---");
        };
    }
}