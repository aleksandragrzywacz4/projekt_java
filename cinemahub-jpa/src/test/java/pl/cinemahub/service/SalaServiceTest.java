package pl.cinemahub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.cinemahub.model.Sala;
import pl.cinemahub.repository.SalaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private SalaService salaService;

    private Sala sala;

    @BeforeEach
    void setUp() {
        sala = new Sala(1L, "Sala IMAX", 120, true);
    }

    // 1. getAllSale()

    @Test
    @DisplayName("Pobieranie wszystkich sal")
    void shouldGetAllSale() {
        when(salaRepository.findAll()).thenReturn(List.of(sala));

        List<Sala> result = salaService.getAllSale();

        assertEquals(1, result.size());
        assertSame(sala, result.getFirst());
    }

    @Test
    void shouldReturnEmptyList() {
        when(salaRepository.findAll()).thenReturn(List.of());

        assertTrue(salaService.getAllSale().isEmpty());
    }

    // 2. getSalaById()

    @Test
    void shouldFindSalaById() {
        when(salaRepository.findById(1L))
                .thenReturn(Optional.of(sala));

        Optional<Sala> result = salaService.getSalaById(1L);

        assertTrue(result.isPresent());
        assertEquals("Sala IMAX", result.get().getNazwa());
    }

    @Test
    void shouldReturnEmptyWhenSalaNotFound() {
        when(salaRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertTrue(salaService.getSalaById(99L).isEmpty());
    }

    // 3. saveSala()

    @Test
    void shouldSaveSala() {
        when(salaRepository.save(sala)).thenReturn(sala);

        Sala result = salaService.saveSala(sala);

        assertSame(sala, result);
        verify(salaRepository).save(sala);
    }

    // 4. updateSala()

    @Test
    void shouldUpdateSala() {
        Sala details = new Sala(
                null, "Sala VIP", 50, false
        );

        when(salaRepository.findById(1L))
                .thenReturn(Optional.of(sala));
        when(salaRepository.save(sala)).thenReturn(sala);

        Sala result = salaService.updateSala(1L, details);

        assertAll(
                () -> assertEquals("Sala VIP", result.getNazwa()),
                () -> assertEquals(50, result.getLiczbaMiejsc()),
                () -> assertFalse(result.isAktywna())
        );

        verify(salaRepository).save(sala);
    }

    @Test
    void shouldThrowWhenUpdatingMissingSala() {
        when(salaRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> salaService.updateSala(99L, sala)
        );

        assertEquals(
                "Nie znaleziono sali o id: 99",
                exception.getMessage()
        );

        verify(salaRepository, never()).save(any());
    }

    // 5. deleteSala()

    @Test
    void shouldDeleteSala() {
        salaService.deleteSala(1L);

        verify(salaRepository).deleteById(1L);
    }

    // 6. countSale()

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 100})
    void shouldCountSale(long count) {
        when(salaRepository.count()).thenReturn(count);

        assertEquals(count, salaService.countSale());
    }

    // 7. salaExists()

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldCheckIfSalaExists(boolean exists) {
        when(salaRepository.existsById(1L))
                .thenReturn(exists);

        assertEquals(exists, salaService.salaExists(1L));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0, -1})
    void shouldRejectInvalidId(Long id) {
        assertFalse(salaService.salaExists(id));

        verifyNoInteractions(salaRepository);
    }

    // 8. getActiveSale()

    @Test
    void shouldReturnOnlyActiveSale() {
        Sala inactive = new Sala(
                2L, "Sala VIP", 50, false
        );

        when(salaRepository.findAll())
                .thenReturn(List.of(sala, inactive));

        List<Sala> result = salaService.getActiveSale();

        assertEquals(1, result.size());
        assertTrue(result.getFirst().isAktywna());
    }

    // 9. getSaleWithMinimumSeats()

    @Test
    void shouldFindSaleWithMinimumSeats() {
        Sala smallSala = new Sala(
                2L, "Mała sala", 49, true
        );

        Sala exactSala = new Sala(
                3L, "Sala 50", 50, true
        );

        when(salaRepository.findAll())
                .thenReturn(List.of(sala, smallSala, exactSala));

        List<Sala> result =
                salaService.getSaleWithMinimumSeats(50);

        assertEquals(2, result.size());
        assertTrue(result.contains(sala));
        assertTrue(result.contains(exactSala));
        assertFalse(result.contains(smallSala));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100})
    void shouldRejectNegativeMinimumSeats(int minSeats) {
        assertThrows(
                IllegalArgumentException.class,
                () -> salaService.getSaleWithMinimumSeats(minSeats)
        );

        verifyNoInteractions(salaRepository);
    }

    @ParameterizedTest
    @CsvSource({
            "0, true",
            "120, true",
            "121, false"
    })
    void shouldCheckSeatBoundaries(int minSeats, boolean expected) {
        when(salaRepository.findAll()).thenReturn(List.of(sala));

        boolean result = !salaService
                .getSaleWithMinimumSeats(minSeats).isEmpty();

        assertEquals(expected, result);
    }

    // 10. getSaleByName()

    @Test
    void shouldFindSalaByNameIgnoringCase() {
        when(salaRepository.findAll())
                .thenReturn(List.of(sala));

        List<Sala> result = salaService.getSaleByName(" sala imax ");

        assertEquals(1, result.size());
        assertSame(sala, result.getFirst());
    }

    @Test
    void shouldReturnEmptyForUnknownName() {
        when(salaRepository.findAll())
                .thenReturn(List.of(sala));

        assertTrue(salaService.getSaleByName("Nieznana").isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void shouldRejectInvalidName(String name) {
        assertThrows(
                IllegalArgumentException.class,
                () -> salaService.getSaleByName(name)
        );

        verifyNoInteractions(salaRepository);
    }
}
