package lab4;

import java.io.FilterOutputStream;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        //Zadanie 1 klasy encyjne Film, Seans i Bilet
        Film film1 = new Film("Gladiator II", "Akcja", 148);
        Seans seans1 = new Seans(film1, "Sala IMAX", "19:30");
        Bilet bilet1 = new Bilet(seans1, 5, 12, 35.50);
        System.out.println("Bilet: " + bilet1);


        //Zadanie 2 klasy wg wzorca Budowniczy KlientKina, ZestawPrzekasek, RezerwacjaBiletow
        KlientKina klient = KlientKina.builder()
                .imie("Marcin")
                .nazwisko("Nowak")
                .email("Marcin@mail.pl")
                .czyPosiadaKarteKlubowa(true)
                .build();

        System.out.println("Klient: "+ klient);


        //Zadanie 3 klasy DTO i wycofywanie zmian
        Film filmDoModyfikacji = new Film("Avatar", "Sci-Fi", 162);
        System.out.println("Przed zmiana: " + filmDoModyfikacji);

        FilmDto snapshot = new FilmDto(
                filmDoModyfikacji.getTytul(),
                filmDoModyfikacji.getGatunek(),
                filmDoModyfikacji.getCzasTrwaniaMinuty()
        );

        filmDoModyfikacji.setTytul("Inny tytul");
        filmDoModyfikacji.setGatunek("Romans");
        filmDoModyfikacji.setCzasTrwaniaMinuty(15);
        System.out.println("Po zmianie: " + filmDoModyfikacji);

        filmDoModyfikacji.setTytul(snapshot.getTytul());
        filmDoModyfikacji.setGatunek(snapshot.getGatunek());
        filmDoModyfikacji.setCzasTrwaniaMinuty(snapshot.getCzasTrwaniaMinuty());
        System.out.println("Po przywroceniu: " + filmDoModyfikacji);
    }
}
