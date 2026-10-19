package lab4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @NonNull
    private String tytul;
    private String gatunek;
    private int czasTrwaniaMinuty;
}
