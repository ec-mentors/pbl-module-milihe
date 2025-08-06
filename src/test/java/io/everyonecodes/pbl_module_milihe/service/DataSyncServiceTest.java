package io.everyonecodes.pbl_module_milihe.service;

import io.everyonecodes.pbl_module_milihe.dto.spoonacular.SpoonacularRecipeResult;
import io.everyonecodes.pbl_module_milihe.dto.spoonacular.SpoonacularSearchResponse;
import io.everyonecodes.pbl_module_milihe.jpa.Recipe;
import io.everyonecodes.pbl_module_milihe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for the DataSyncService.
 * This test verifies the business logic in isolation by using mocks
 * for all external dependencies (API service and repository).
 */
public class DataSyncServiceTest {

    private DataSyncService dataSyncService;

    private SpoonacularApiService mockSpoonacularApiService;
    private RecipeRepository mockRecipeRepository;

    @BeforeEach
    void setUp() {
        mockSpoonacularApiService = mock(SpoonacularApiService.class);
        mockRecipeRepository = mock(RecipeRepository.class);

        dataSyncService = new DataSyncService(mockSpoonacularApiService, mockRecipeRepository);
    }

    @Test
    void syncRecipes_savesNewRecipes_andSkipsExistingOnes() {

        var newRecipeResult = new SpoonacularRecipeResult();
        newRecipeResult.setId(101);
        newRecipeResult.setTitle("New Test Pasta");

        var existingRecipeResult = new SpoonacularRecipeResult();
        existingRecipeResult.setId(102);
        existingRecipeResult.setTitle("Existing Test Soup");

        var response = new SpoonacularSearchResponse();
        response.setResults(List.of(newRecipeResult, existingRecipeResult));


        when(mockSpoonacularApiService.searchRecipes("italian")).thenReturn(response);

        when(mockRecipeRepository.existsBySpoonacularId(101)).thenReturn(false);

        when(mockRecipeRepository.existsBySpoonacularId(102)).thenReturn(true);



        dataSyncService.syncRecipes();



        verify(mockSpoonacularApiService, times(1)).searchRecipes("italian");

        verify(mockRecipeRepository, times(1)).existsBySpoonacularId(101);
        verify(mockRecipeRepository, times(1)).existsBySpoonacularId(102);

        verify(mockRecipeRepository, times(1)).save(any(Recipe.class));

    }
}
