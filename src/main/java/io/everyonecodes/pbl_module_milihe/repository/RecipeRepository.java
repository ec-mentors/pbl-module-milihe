package io.everyonecodes.pbl_module_milihe.repository;

import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.recipeIngredients ri LEFT JOIN FETCH ri.ingredient")
    List<Recipe> findAllWithIngredients();
}
