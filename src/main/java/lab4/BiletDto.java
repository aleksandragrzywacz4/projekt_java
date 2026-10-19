package lab4;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BiletDto {
    private Seans seans;
    private int numerRzedu;
    private int numerMiejsca;
    private double cena;
}
