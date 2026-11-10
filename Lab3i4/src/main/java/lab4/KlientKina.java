package lab4;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class KlientKina {
    private String imie;
    private String nazwisko;
    private String email;
    private boolean czyPosiadaKarteKlubowa;
}
