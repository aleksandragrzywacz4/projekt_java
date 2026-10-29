package pl.cinemahub.cinemahub_jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.cinemahub.cinemahub_jpa.model.Film;
import pl.cinemahub.cinemahub_jpa.model.Sala;
import pl.cinemahub.cinemahub_jpa.repository.FilmRepository;
import pl.cinemahub.cinemahub_jpa.repository.SalaRepository;

@SpringBootApplication
public class CinemahubJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemahubJpaApplication.class, args);
	}

	@Bean
	CommandLineRunner testJpaCrud(
			FilmRepository filmRepository,
			SalaRepository salaRepository) {

		return args -> {
			System.out.println("TEST");

			Film film = new Film(null, "Film", "Sci-Fi", 135, true);
			filmRepository.save(film);

			Sala sala = new Sala(null, "Sala Kino", 80, true);
			salaRepository.save(sala);

			System.out.println("Wszystkie filmy w bazie:");
			filmRepository.findAll().forEach(System.out::println);

			System.out.println("Wszystkie sale w bazie:");
			salaRepository.findAll().forEach(System.out::println);
		};
	}

}
