package io.everyonecodes.pbl_module_milihe.controller;

import io.everyonecodes.pbl_module_milihe.jpa.Ingredient;
import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import io.everyonecodes.pbl_module_milihe.jpa.RecipeIngredient;
import io.everyonecodes.pbl_module_milihe.repository.IngredientRepository;
import io.everyonecodes.pbl_module_milihe.repository.RecipeRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/setup")
public class DataSetupController {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public DataSetupController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/data")
    @Transactional
    public String setupData() {
        Ingredient tomato = ingredientRepository.save(new Ingredient("Tomato", "tomato.jpg"));
        Ingredient pasta = ingredientRepository.save(new Ingredient("Pasta", "pasta.jpg"));
        Ingredient lentil = ingredientRepository.save(new Ingredient("Lentils", "lentils.jpg"));
        Ingredient onion = ingredientRepository.save(new Ingredient("Onion", "onion.jpg"));

        Recipe pastaRecipe = new Recipe();
        pastaRecipe.setTitle("Classic Pasta");
        pastaRecipe.setSummary("A simple pasta dish.");
        pastaRecipe.setVegan(false);
        pastaRecipe.setVegetarian(true);
        pastaRecipe.setDairyFree(false);
        pastaRecipe.setGlutenFree(false);
        pastaRecipe.setReadyInMinutes(20);
        pastaRecipe.setServings(2);
        pastaRecipe.setHealthScore(75);

        pastaRecipe.getIngredients().add(new RecipeIngredient(200, "g", pasta, pastaRecipe));
        pastaRecipe.getIngredients().add(new RecipeIngredient(400, "g", tomato, pastaRecipe));

        Recipe lentilSoup = new Recipe();
        lentilSoup.setTitle("Lentil Soup");
        lentilSoup.setSummary("A hearty vegan soup.");
        lentilSoup.setVegan(true);
        lentilSoup.setVegetarian(true);
        lentilSoup.setDairyFree(true);
        lentilSoup.setGlutenFree(true);
        lentilSoup.setReadyInMinutes(45);
        lentilSoup.setServings(4);
        lentilSoup.setHealthScore(90);

        lentilSoup.getIngredients().add(new RecipeIngredient(150, "g", lentil, lentilSoup));
        lentilSoup.getIngredients().add(new RecipeIngredient(1, "piece", onion, lentilSoup));

        recipeRepository.saveAll(List.of(pastaRecipe, lentilSoup));

        return "Test data has been created successfully!";
    }
}