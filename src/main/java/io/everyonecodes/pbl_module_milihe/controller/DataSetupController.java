//package io.everyonecodes.pbl_module_milihe.controller;
//
//import io.everyonecodes.pbl_module_milihe.jpa.Ingredient;
//import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
//import io.everyonecodes.pbl_module_milihe.jpa.RecipeIngredient;
//import io.everyonecodes.pbl_module_milihe.repository.IngredientRepository;
//import io.everyonecodes.pbl_module_milihe.repository.RecipeRepository;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * A controller with a special endpoint for development and testing purposes.
// * This endpoint allows a developer to easily seed the database with a known set of test data.
// * This should be disabled or removed in a production environment.
// */
//@RestController
//@RequestMapping("/api/setup")
//@CrossOrigin(origins = "*")
//public class DataSetupController {
//
//    private final RecipeRepository recipeRepository;
//    private final IngredientRepository ingredientRepository;
//
//    public DataSetupController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
//        this.recipeRepository = recipeRepository;
//        this.ingredientRepository = ingredientRepository;
//    }
//
//    @GetMapping("/data")
//    @Transactional
//    public String setupData() {
//        System.out.println("--- [CONTROLLER] Seeding Database with Full Details ---");
//
//        recipeRepository.deleteAll();
//        ingredientRepository.deleteAll();
//
//        Ingredient tomato = ingredientRepository.save(new Ingredient("Tomato", "https://placehold.co/100x100/f35b5b/ffffff?text=Tomato"));
//        Ingredient pasta = ingredientRepository.save(new Ingredient("Pasta", "https://placehold.co/100x100/f5cba7/ffffff?text=Pasta"));
//        Ingredient onion = ingredientRepository.save(new Ingredient("Onion", "https://placehold.co/100x100/d7bde2/ffffff?text=Onion"));
//        Ingredient lentil = ingredientRepository.save(new Ingredient("Lentils", "https://placehold.co/100x100/a9dfbf/ffffff?text=Lentils"));
//
//        Recipe pastaRecipe = new Recipe("Classic Pasta", false);
//        pastaRecipe.setImage("https://placehold.co/600x400/f5b041/ffffff?text=Pasta+Dish");
//        pastaRecipe.setSummary("A simple and delicious pasta dish.");
//        pastaRecipe.setStepByStepInstruction("1. Boil pasta.\n2. Sauté onion.\n3. Add tomatoes and simmer.\n4. Combine and serve.");
//        pastaRecipe.getIngredients().add(new RecipeIngredient(200, "g", pasta, pastaRecipe));
//        pastaRecipe.getIngredients().add(new RecipeIngredient(400, "g", tomato, pastaRecipe));
//        pastaRecipe.getIngredients().add(new RecipeIngredient(1, "pc", onion, pastaRecipe));
//
//        Recipe lentilSoup = new Recipe("Lentil Soup", true);
//        lentilSoup.setImage("https://placehold.co/600x400/27ae60/ffffff?text=Lentil+Soup");
//        lentilSoup.setSummary("A hearty vegan soup.");
//        lentilSoup.setStepByStepInstruction("1. Sauté onion.\n2. Add lentils and broth.\n3. Simmer until tender.");
//        lentilSoup.getIngredients().add(new RecipeIngredient(150, "g", lentil, lentilSoup));
//        lentilSoup.getIngredients().add(new RecipeIngredient(1, "pc", onion, lentilSoup));
//
//        recipeRepository.saveAll(List.of(pastaRecipe, lentilSoup));
//
//        return "Test data with full details has been created successfully!";
//    }
//}