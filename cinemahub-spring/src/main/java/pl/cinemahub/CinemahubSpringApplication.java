
package pl.cinemahub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.cinemahub.repository.FilmRepository;
import pl.cinemahub.repository.SalaRepository;

@SpringBootApplication
public class CinemahubSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemahubSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner testRepositories(
			FilmRepository filmRepository,
			SalaRepository salaRepository) {

		return args -> {
			System.out.println("Filmy w bazie:");
			filmRepository.findAll().forEach(System.out::println);

			System.out.println("\nSale w bazie:");
			salaRepository.findAll().forEach(System.out::println);
		};
	}
}
