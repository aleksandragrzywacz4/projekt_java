package pl.cinemahub.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.cinemahub.model.Sala;

import java.util.List;
import java.util.Optional;

@Repository
public class SalaRepository {

    private final JdbcTemplate jdbcTemplate;

    public SalaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Sala> salaRowMapper = (rs, rowNum) ->
            new Sala(
                    rs.getLong("id"),
                    rs.getString("nazwa"),
                    rs.getInt("liczba_miejsc"),
                    rs.getBoolean("aktywna")
            );

    // CREATE
    public int add(Sala sala) {
        String sql = """
                INSERT INTO sale (nazwa, liczba_miejsc, aktywna)
                VALUES (?, ?, ?)
                """;

        return jdbcTemplate.update(sql,
                sala.getNazwa(),
                sala.getLiczbaMiejsc(),
                sala.isAktywna());
    }

    // READ
    public List<Sala> findAll() {
        String sql = "SELECT * FROM sale";
        return jdbcTemplate.query(sql, salaRowMapper);
    }

    // READ po ID
    public Optional<Sala> findById(Long id) {
        String sql = "SELECT * FROM sale WHERE id = ?";

        return jdbcTemplate.query(sql, salaRowMapper, id)
                .stream()
                .findFirst();
    }

    // UPDATE
    public int update(Sala sala) {
        String sql = """
                UPDATE sale
                SET nazwa = ?, liczba_miejsc = ?, aktywna = ?
                WHERE id = ?
                """;

        return jdbcTemplate.update(sql,
                sala.getNazwa(),
                sala.getLiczbaMiejsc(),
                sala.isAktywna(),
                sala.getId());
    }

    // DELETE aktywny na false
    public int softDelete(Long id) {
        String sql = "UPDATE sale SET aktywna = FALSE WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // DELETE rekordu
    public int hardDelete(Long id) {
        String sql = "DELETE FROM sale WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
