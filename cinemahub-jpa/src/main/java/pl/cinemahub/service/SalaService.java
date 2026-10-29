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
}