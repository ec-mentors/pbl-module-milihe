package io.everyonecodes.pbl_module_milihe.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
    private Long id;
    private int spoonacularId;
    private String name;
    private String image;
}
