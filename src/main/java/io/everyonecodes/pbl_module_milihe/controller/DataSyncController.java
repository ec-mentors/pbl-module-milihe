//package io.everyonecodes.pbl_module_milihe.controller;
//
//import io.everyonecodes.pbl_module_milihe.repository.IngredientRepository;
//import io.everyonecodes.pbl_module_milihe.repository.RecipeRepository;
//import io.everyonecodes.pbl_module_milihe.service.DataSyncService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/sync")
//public class DataSyncController {
//
//    private final DataSyncService dataSyncService;
//    private final RecipeRepository recipeRepository;
//    private final IngredientRepository ingredientRepository;
//
//    public DataSyncController(DataSyncService dataSyncService, RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
//        this.dataSyncService = dataSyncService;
//        this.recipeRepository = recipeRepository;
//        this.ingredientRepository = ingredientRepository;
//    }
//
//    /**
//     * A manual trigger endpoint to start the data synchronization process.
//     * @return A success message.
//     */
//    @PostMapping("/recipes")
//    public String triggerSync() {
//        recipeRepository.deleteAll();
//        ingredientRepository.deleteAll();
//
//        dataSyncService.syncRecipes();
//        return "Spoonacular data sync initiated successfully!";
//    }
//}
