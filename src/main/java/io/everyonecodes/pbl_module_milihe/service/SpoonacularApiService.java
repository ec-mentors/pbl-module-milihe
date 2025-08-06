package io.everyonecodes.pbl_module_milihe.service;

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

    public SpoonacularSearchResponse searchRecipes(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/recipes/complexSearch")
                .queryParam("apiKey", apiKey)
                .queryParam("query", query)
                .queryParam("number", 10)
                .toUriString();

        System.out.println("Calling Spoonacular API with URL: " + url);

        try {
            return restTemplate.getForObject(url, SpoonacularSearchResponse.class);
        } catch (Exception e) {
            System.err.println("Error calling Spoonacular API: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}