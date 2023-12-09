package sky.pro.Animals;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition
@EnableScheduling
public class PetAstanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetAstanaApplication.class, args);
	}
}
