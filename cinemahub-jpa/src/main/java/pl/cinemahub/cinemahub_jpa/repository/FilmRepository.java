package pl.cinemahub.cinemahub_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cinemahub.cinemahub_jpa.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}