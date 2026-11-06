package pl.cinemahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cinemahub.model.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
}