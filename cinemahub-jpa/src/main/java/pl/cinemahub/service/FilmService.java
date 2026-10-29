package pl.cinemahub.service;

import org.springframework.stereotype.Service;
import pl.cinemahub.model.Film;
import pl.cinemahub.repository.FilmRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Optional<Film> getFilmById(Long id) {
        return filmRepository.findById(id);
    }

    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    public Film updateFilm(Long id, Film filmDetails) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono filmu o id: " + id));

        film.setTytul(filmDetails.getTytul());
        film.setGatunek(filmDetails.getGatunek());
        film.setCzasTrwaniaMinuty(filmDetails.getCzasTrwaniaMinuty());
        film.setAktywny(filmDetails.isAktywny());

        return filmRepository.save(film);
    }

    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }
}
