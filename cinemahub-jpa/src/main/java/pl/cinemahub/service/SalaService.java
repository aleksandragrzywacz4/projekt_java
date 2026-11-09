package pl.cinemahub.service;

import org.springframework.stereotype.Service;
import pl.cinemahub.model.Sala;
import pl.cinemahub.repository.SalaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<Sala> getAllSale() {
        return salaRepository.findAll();
    }

    public Optional<Sala> getSalaById(Long id) {
        return salaRepository.findById(id);
    }

    public Sala saveSala(Sala sala) {
        return salaRepository.save(sala);
    }

    public Sala updateSala(Long id, Sala salaDetails) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono sali o id: " + id));

        sala.setNazwa(salaDetails.getNazwa());
        sala.setLiczbaMiejsc(salaDetails.getLiczbaMiejsc());
        sala.setAktywna(salaDetails.isAktywna());

        return salaRepository.save(sala);
    }

    public void deleteSala(Long id) {
        salaRepository.deleteById(id);
    }


    public long countSale() {
        return salaRepository.count();
    }

    public boolean salaExists(Long id) {
        if (id == null || id <= 0) {
            return false;
        }

        return salaRepository.existsById(id);
    }

    public List<Sala> getActiveSale() {
        return salaRepository.findAll().stream()
                .filter(Sala::isAktywna)
                .toList();
    }

    public List<Sala> getSaleWithMinimumSeats(int minMiejsc) {
        if (minMiejsc < 0) {
            throw new IllegalArgumentException("Liczba miejsc nie może być ujemna");
        }

        return salaRepository.findAll().stream()
                .filter(sala -> sala.getLiczbaMiejsc() >= minMiejsc)
                .toList();
    }

    public List<Sala> getSaleByName(String nazwa) {
        if (nazwa == null || nazwa.isBlank()) {
            throw new IllegalArgumentException("Nazwa nie może być pusta");
        }

        return salaRepository.findAll().stream()
                .filter(sala -> sala.getNazwa() != null)
                .filter(sala -> sala.getNazwa().equalsIgnoreCase(nazwa.trim()))
                .toList();
    }

}