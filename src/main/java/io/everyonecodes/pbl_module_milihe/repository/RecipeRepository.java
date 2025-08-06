package io.everyonecodes.pbl_module_milihe.repository;

import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.ingredients ri LEFT JOIN FETCH ri.ingredient")
    List<Recipe> findAllWithIngredients();


    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.ingredients ri LEFT JOIN FETCH ri.ingredient WHERE r.id = :id")
    Optional<Recipe> findByIdWithIngredients(@Param("id") Long id);

    /**
     * Checks if a Recipe with the given spoonacularId already exists in the database.
     * This is a very efficient way to check for duplicates as it only returns true/false.
     *
     * @param spoonacularId The ID from the Spoonacular API.
     * @return true if a recipe exists, false otherwise.
     */
    boolean existsBySpoonacularId(int spoonacularId);
}
