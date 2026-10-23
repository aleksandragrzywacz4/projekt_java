package pl.cinemahub.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.cinemahub.model.Sala;

import java.util.List;

@Repository
public class SalaRepository {

    private final JdbcTemplate jdbcTemplate;

    public SalaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Sala> findAll() {
        String sql = "SELECT * FROM sale";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Sala(
                        rs.getLong("id"),
                        rs.getString("nazwa"),
                        rs.getInt("liczba_miejsc"),
                        rs.getBoolean("aktywna")
                )
        );
    }
}
