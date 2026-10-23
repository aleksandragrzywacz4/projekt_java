package pl.cinemahub.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.cinemahub.model.Film;

import java.util.List;

@Repository
public class FilmRepository {

    private final JdbcTemplate jdbcTemplate;

    public FilmRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Film> findAll() {
        String sql = "SELECT * FROM filmy";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Film(
                        rs.getLong("id"),
                        rs.getString("tytul"),
                        rs.getString("gatunek"),
                        rs.getInt("czas_trwania_minuty"),
                        rs.getBoolean("aktywny")
                )
        );
    }
}
