package io.everyonecodes.pbl_module_milihe.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ingredient {
    @Id
    @GeneratedValue
    private int id;
    private int spoonacularId;
    private String name;
}
