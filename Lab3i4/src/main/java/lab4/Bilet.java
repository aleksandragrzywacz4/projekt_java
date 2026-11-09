package lab4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bilet {
    @NonNull
    private Seans seans;
    private int numerRzedu;
    private int numerMiejsca;
    private double cena;
}
