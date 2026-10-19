package lab4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seans {
    @NonNull
    private Film film;

    @NonNull
    private String sala;

    @NonNull
    private String godzinaEmisji;
}
