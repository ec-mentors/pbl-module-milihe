package io.everyonecodes.pbl_module_milihe.repository;

import io.everyonecodes.pbl_module_milihe.jpa.RecipeIngredient;
import io.everyonecodes.pbl_module_milihe.jpa.RecipeIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {
    List<RecipeIngredient> findByRecipe_Id(Long recipeId);
}
