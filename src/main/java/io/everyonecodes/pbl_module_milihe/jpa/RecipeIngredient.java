package io.everyonecodes.pbl_module_milihe.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * JPA Entity for RecipeIngredient.
 * Represents the many-to-many relationship between Recipe and Ingredient,
 * with additional attributes like amount and unit.
 * It uses a composite primary key defined by RecipeIngredientId.
 */
@Entity
@Table(name = "recipe_ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredient {
    @EmbeddedId
    private RecipeIngredientId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
    private double amount;
    private String unit;
    private String originalString;

    public RecipeIngredient(Recipe recipe, Ingredient ingredient, double amount, String unit, String originalString) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
        this.unit = unit;
        this.originalString = originalString;
        this.id = new RecipeIngredientId(recipe.getId(), ingredient.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}