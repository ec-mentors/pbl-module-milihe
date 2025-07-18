package io.everyonecodes.pbl_module_milihe.repository;

import io.everyonecodes.pbl_module_milihe.jpa.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
