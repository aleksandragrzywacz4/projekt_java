package pl.cinemahub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.cinemahub.model.Film;
import pl.cinemahub.repository.FilmRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film(1L, "Gladiator II", "Akcja", 148, true);
    }

    // 1. getAllFilms()

    @Test
    @DisplayName("Pobieranie wszystkich filmów")
    void shouldGetAllFilms() {
        when(filmRepository.findAll()).thenReturn(List.of(film));

        List<Film> result = filmService.getAllFilms();

        assertEquals(1, result.size());
        assertSame(film, result.getFirst());
    }

    @Test
    void shouldReturnEmptyList() {
        when(filmRepository.findAll()).thenReturn(List.of());

        assertTrue(filmService.getAllFilms().isEmpty());
    }

    // 2. getFilmById()

    @Test
    void shouldFindFilmById() {
        when(filmRepository.findById(1L))
                .thenReturn(Optional.of(film));

        Optional<Film> result = filmService.getFilmById(1L);

        assertTrue(result.isPresent());
        assertEquals("Gladiator II", result.get().getTytul());
    }

    @Test
    void shouldReturnEmptyWhenFilmNotFound() {
        when(filmRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertTrue(filmService.getFilmById(99L).isEmpty());
    }

    // 3. saveFilm()

    @Test
    void shouldSaveFilm() {
        when(filmRepository.save(film)).thenReturn(film);

        Film result = filmService.saveFilm(film);

        assertSame(film, result);
        verify(filmRepository).save(film);
    }

    // 4. updateFilm()

    @Test
    void shouldUpdateFilm() {
        Film details = new Film(
                null, "Nowy tytuł", "Dramat", 120, false
        );

        when(filmRepository.findById(1L))
                .thenReturn(Optional.of(film));
        when(filmRepository.save(film)).thenReturn(film);

        Film result = filmService.updateFilm(1L, details);

        assertAll(
                () -> assertEquals("Nowy tytuł", result.getTytul()),
                () -> assertEquals("Dramat", result.getGatunek()),
                () -> assertEquals(120, result.getCzasTrwaniaMinuty()),
                () -> assertFalse(result.isAktywny())
        );

        verify(filmRepository).save(film);
    }

    @Test
    void shouldThrowWhenUpdatingMissingFilm() {
        when(filmRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> filmService.updateFilm(99L, film)
        );

        assertEquals(
                "Nie znaleziono filmu o id: 99",
                exception.getMessage()
        );
        verify(filmRepository, never()).save(any());
    }

    // 5. deleteFilm()

    @Test
    void shouldDeleteFilm() {
        filmService.deleteFilm(1L);

        verify(filmRepository).deleteById(1L);
    }

    // 6. countFilms()

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 100})
    void shouldCountFilms(long count) {
        when(filmRepository.count()).thenReturn(count);

        assertEquals(count, filmService.countFilms());
    }

    // 7. filmExists()

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldCheckIfFilmExists(boolean exists) {
        when(filmRepository.existsById(1L))
                .thenReturn(exists);

        assertEquals(exists, filmService.filmExists(1L));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0, -1})
    void shouldRejectInvalidId(Long id) {
        assertFalse(filmService.filmExists(id));

        verifyNoInteractions(filmRepository);
    }

    // 8. getActiveFilms()

    @Test
    void shouldReturnOnlyActiveFilms() {
        Film inactive = new Film(
                2L, "Joker", "Dramat", 138, false
        );

        when(filmRepository.findAll())
                .thenReturn(List.of(film, inactive));

        List<Film> result = filmService.getActiveFilms();

        assertEquals(1, result.size());
        assertTrue(result.getFirst().isAktywny());
    }

    // 9. getFilmsByGenre()

    @Test
    void shouldFindFilmsByGenreIgnoringCase() {
        when(filmRepository.findAll())
                .thenReturn(List.of(film));

        List<Film> result = filmService.getFilmsByGenre(" akcja ");

        assertEquals(1, result.size());
        assertSame(film, result.getFirst());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void shouldRejectInvalidGenre(String genre) {
        assertThrows(
                IllegalArgumentException.class,
                () -> filmService.getFilmsByGenre(genre)
        );

        verifyNoInteractions(filmRepository);
    }

    // 10. getFilmsLongerThan()

    @Test
    void shouldFindFilmsLongerThanGivenTime() {
        Film shortFilm = new Film(
                2L, "Krótki film", "Dramat", 120, true
        );

        when(filmRepository.findAll())
                .thenReturn(List.of(film, shortFilm));

        List<Film> result = filmService.getFilmsLongerThan(120);

        assertEquals(1, result.size());
        assertSame(film, result.getFirst());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100})
    void shouldRejectNegativeDuration(int duration) {
        assertThrows(
                IllegalArgumentException.class,
                () -> filmService.getFilmsLongerThan(duration)
        );

        verifyNoInteractions(filmRepository);
    }
}
