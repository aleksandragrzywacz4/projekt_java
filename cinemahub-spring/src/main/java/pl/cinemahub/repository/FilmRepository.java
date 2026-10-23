package pl.cinemahub.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.cinemahub.model.Film;

import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository {

    private final JdbcTemplate jdbcTemplate;

    public FilmRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Film> filmRowMapper = (rs, rowNum) ->
            new Film(
                    rs.getLong("id"),
                    rs.getString("tytul"),
                    rs.getString("gatunek"),
                    rs.getInt("czas_trwania_minuty"),
                    rs.getBoolean("aktywny")
            );

    // CREATE
    public int add(Film film) {
        String sql = """
                INSERT INTO filmy (tytul, gatunek, czas_trwania_minuty, aktywny)
                VALUES (?, ?, ?, ?)
                """;

        return jdbcTemplate.update(sql,
                film.getTytul(),
                film.getGatunek(),
                film.getCzasTrwaniaMinuty(),
                film.isAktywny());
    }

    // READ
    public List<Film> findAll() {
        String sql = "SELECT * FROM filmy";
        return jdbcTemplate.query(sql, filmRowMapper);
    }

    // READ po ID
    public Optional<Film> findById(Long id) {
        String sql = "SELECT * FROM filmy WHERE id = ?";

        return jdbcTemplate.query(sql, filmRowMapper, id)
                .stream()
                .findFirst();
    }

    // UPDATE
    public int update(Film film) {
        String sql = """
                UPDATE filmy
                SET tytul = ?, gatunek = ?, czas_trwania_minuty = ?, aktywny = ?
                WHERE id = ?
                """;

        return jdbcTemplate.update(sql,
                film.getTytul(),
                film.getGatunek(),
                film.getCzasTrwaniaMinuty(),
                film.isAktywny(),
                film.getId());
    }

    // DELETE - ustawia aktywny na false
    public int softDelete(Long id) {
        String sql = "UPDATE filmy SET aktywny = FALSE WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // DELETE - usuwanie rekordu z bazy
    public int hardDelete(Long id) {
        String sql = "DELETE FROM filmy WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
