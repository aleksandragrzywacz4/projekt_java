package lab4;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ZestawPrzekasek {
    private String rozmiarPopcornu;
    private String smakPopcornu;
    private String napoj;
    private boolean czyNachosy;
}
