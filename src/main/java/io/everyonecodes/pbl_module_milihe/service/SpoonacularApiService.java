package io.everyonecodes.pbl_module_milihe.service;

import io.everyonecodes.pbl_module_milihe.dto.spoonacular.SpoonacularRecipeInformationDTO;
import io.everyonecodes.pbl_module_milihe.dto.spoonacular.SpoonacularSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SpoonacularApiService {

    private final RestTemplate restTemplate;

    @Value("${spoonacular.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://api.spoonacular.com";

    public SpoonacularApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Searches for a list of recipes from Spoonacular using a given query.
     */
    public SpoonacularSearchResponse searchRecipes(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/recipes/complexSearch")
                .queryParam("apiKey", apiKey)
                .queryParam("query", query)
                .queryParam("number", 10)
                .toUriString();

        System.out.println("Calling Spoonacular for recipe search: " + url);
        try {
            return restTemplate.getForObject(url, SpoonacularSearchResponse.class);
        } catch (Exception e) {
            System.err.println("Error calling Spoonacular API (search): " + e.getMessage());
            return null;
        }
    }

    /**
     * Fetches the full, detailed information for a single recipe by its Spoonacular ID.
     */
    public SpoonacularRecipeInformationDTO getRecipeDetails(int spoonacularId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/recipes/{id}/information")
                .queryParam("apiKey", apiKey)
                .buildAndExpand(spoonacularId)
                .toUriString();

        System.out.println("Calling Spoonacular for recipe details: " + url);
        try {
            return restTemplate.getForObject(url, SpoonacularRecipeInformationDTO.class);
        } catch (Exception e) {
            System.err.println("Error calling Spoonacular API (details): " + e.getMessage());
            return null;
        }
    }
}