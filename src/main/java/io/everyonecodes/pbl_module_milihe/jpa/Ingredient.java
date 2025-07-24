package io.everyonecodes.pbl_module_milihe.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JPA Entity for an Ingredient. Represents a single ingredient.
 * It now includes a bidirectional @OneToMany relationship to RecipeIngredient.
 */
@Entity
@Table(name = "ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int spoonacularId;
    private String name;
    private String image;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredients.add(recipeIngredient);
        recipeIngredient.setIngredient(this);
        if (this.id != null && recipeIngredient.getRecipe() != null && recipeIngredient.getRecipe().getId() != null) {
            recipeIngredient.setId(new RecipeIngredientId(recipeIngredient.getRecipe().getId(), this.id));
        }
    }

    public void removeRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredients.remove(recipeIngredient);
        recipeIngredient.setIngredient(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
