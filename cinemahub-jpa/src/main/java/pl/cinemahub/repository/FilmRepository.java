package pl.cinemahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cinemahub.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}