package lab4;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Builder
@Getter
@ToString
public class RezerwacjaBiletow {
    private String idRezerwacji;
    private KlientKina klient;
    private List<Bilet> bilety;
    private ZestawPrzekasek przekaski;
}
