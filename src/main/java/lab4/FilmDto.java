package lab4;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FilmDto {
    private String tytul;
    private String gatunek;
    private int czasTrwaniaMinuty;
}
