
package pl.cinemahub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.cinemahub.model.Film;
import pl.cinemahub.model.Sala;
import pl.cinemahub.repository.FilmRepository;
import pl.cinemahub.repository.SalaRepository;

import java.util.Comparator;

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

	@Bean
	CommandLineRunner testCrud(
			FilmRepository filmRepository,
			SalaRepository salaRepository) {

		return args -> {
			System.out.println("=== CRUD FILMY ===");

			// CREATE
			Film film = new Film(null, "Film testowy CRUD", "Akcja", 120, true);
			filmRepository.add(film);

			// READ ALL
			System.out.println("Wszystkie filmy:");
			filmRepository.findAll().forEach(System.out::println);

			// Odczyt ID dodanego filmu
			Film zapisanyFilm = filmRepository.findAll().stream()
					.filter(f -> f.getTytul().equals("Film testowy CRUD"))
					.max(Comparator.comparing(Film::getId))
					.orElseThrow();

			Long filmId = zapisanyFilm.getId();

			// READ BY ID
			System.out.println("Film po ID: " + filmRepository.findById(filmId));

			// UPDATE
			zapisanyFilm.setTytul("Film po aktualizacji");
			filmRepository.update(zapisanyFilm);
			System.out.println("Po UPDATE: " + filmRepository.findById(filmId));

			// SOFT DELETE
			filmRepository.softDelete(filmId);
			System.out.println("Po SOFT DELETE: " + filmRepository.findById(filmId));

			// HARD DELETE
			filmRepository.hardDelete(filmId);
			System.out.println("Po HARD DELETE: " + filmRepository.findById(filmId));


			System.out.println("\n=== CRUD SALE ===");

			// CREATE
			Sala sala = new Sala(null, "Sala testowa CRUD", 50, true);
			salaRepository.add(sala);

			// READ ALL
			System.out.println("Wszystkie sale:");
			salaRepository.findAll().forEach(System.out::println);

			// Odczyt ID dodanej sali
			Sala zapisanaSala = salaRepository.findAll().stream()
					.filter(s -> s.getNazwa().equals("Sala testowa CRUD"))
					.max(Comparator.comparing(Sala::getId))
					.orElseThrow();

			Long salaId = zapisanaSala.getId();

			// READ BY ID
			System.out.println("Sala po ID: " + salaRepository.findById(salaId));

			// UPDATE
			zapisanaSala.setLiczbaMiejsc(100);
			salaRepository.update(zapisanaSala);
			System.out.println("Po UPDATE: " + salaRepository.findById(salaId));

			// SOFT DELETE
			salaRepository.softDelete(salaId);
			System.out.println("Po SOFT DELETE: " + salaRepository.findById(salaId));

			// HARD DELETE
			salaRepository.hardDelete(salaId);
			System.out.println("Po HARD DELETE: " + salaRepository.findById(salaId));
		};
	}
}
