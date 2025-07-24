package io.everyonecodes.pbl_module_milihe.service;

import io.everyonecodes.pbl_module_milihe.dto.IngredientDTO;
import io.everyonecodes.pbl_module_milihe.jpa.Ingredient;
import io.everyonecodes.pbl_module_milihe.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing Ingredient entities and their associated DTOs.
 * This class encapsulates the business logic related to ingredients,
 * orchestrates interactions with the IngredientRepository, and handles the conversion
 * between Ingredient JPA entities and IngredientDTOs.
 */
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    /**
     * Constructor for IngredientService.
     * Spring automatically injects the required repository instance.
     * @param ingredientRepository The repository for Ingredient entities.
     */
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Saves a new Ingredient entity to the database.
     * @param ingredient The Ingredient entity to save.
     * @return The saved Ingredient entity, potentially with an updated ID.
     */
    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    /**
     * Retrieves all Ingredient entities from the database and converts them into IngredientDTOs.
     * @return A list of IngredientDTOs.
     */
    public List<IngredientDTO> findAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream()
                .map(this::toIngredientDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single Ingredient entity by its ID and converts it into an IngredientDTO.
     * @param id The ID of the ingredient to retrieve.
     * @return An Optional containing the IngredientDTO if found, or an empty Optional if not.
     */
    public Optional<IngredientDTO> findIngredientById(Long id) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);
        return ingredientOptional.map(this::toIngredientDTO);
    }

    /**
     * Converts an Ingredient JPA entity into an IngredientDTO.
     * This method is responsible for mapping fields from the entity to the DTO.
     * @param ingredient The Ingredient JPA entity to convert.
     * @return The corresponding IngredientDTO.
     */
    private IngredientDTO toIngredientDTO(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getSpoonacularId(),
                ingredient.getName(),
                ingredient.getImage()
        );
    }

    /**
     * Converts an IngredientDTO to an Ingredient JPA entity.
     * Note: ID might be null for new entities.
     * This method now correctly matches the constructor of the Ingredient JPA entity
     * by providing an empty ArrayList for the 'recipeIngredients' list.
     * @param dto The IngredientDTO.
     * @return The corresponding Ingredient entity.
     */
    public Ingredient fromIngredientDTO(IngredientDTO dto) {
        return new Ingredient(
                dto.getId(),
                dto.getSpoonacularId(),
                dto.getName(),
                dto.getImage(),
                new ArrayList<>()
        );
    }
}
