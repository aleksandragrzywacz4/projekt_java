package pl.cinemahub.cinemahub_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cinemahub.cinemahub_jpa.model.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
}