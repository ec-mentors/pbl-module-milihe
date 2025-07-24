package io.everyonecodes.pbl_module_milihe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean; // Import for @Bean annotation
import org.springframework.web.client.RestTemplate; // Import for RestTemplate

@SpringBootApplication
public class PblModuleMiliheApplication {

	public static void main(String[] args) {
		SpringApplication.run(PblModuleMiliheApplication.class, args);
	}

	/**
	 * Defines a RestTemplate bean.
	 * RestTemplate is used for making synchronous HTTP requests to external APIs (like Spoonacular).
	 * Spring will manage this bean and inject it wherever it's needed (e.g., in SpoonacularApiService).
	 * @return A new instance of RestTemplate.
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
