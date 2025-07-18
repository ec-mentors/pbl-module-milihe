package io.everyonecodes.pbl_module_milihe.respository;

import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    }
