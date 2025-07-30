package io.everyonecodes.pbl_module_milihe.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;

@Entity
@Table(name = "ingredient")
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_seq")
    @SequenceGenerator(name = "ingredient_seq", sequenceName = "ingredient_id_seq", allocationSize = 1)
    private Long id;

    private int spoonacularId;
    private String name;
    private String image;

    public Ingredient(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Ingredient(String name, String image, int spoonacularId) {
        this(name, image);
        this.spoonacularId = spoonacularId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((Ingredient) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}