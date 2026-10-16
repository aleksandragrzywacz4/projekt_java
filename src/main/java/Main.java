import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args){
        List<String> elementyKina = Arrays.asList(
                "Film: Gladiator II (Seans 2D, Gatunek: Akcja)",
                "Film: W głowie się nie mieści 2 (Seans 3D, Gatunek Animacja)",
                "Film: Dune: Część druga (Seans 3D, Gatunek: Sci-Fi)",
                "Bilet normalny - Rząd 5, Miejsce 12 (Status: Opłacony)",
                "Bilet ulgowy - Rząd 3, Miejsce 4 (Status: Zarezerwowany)",
                "Film: Joker: Folie à deux (Seans 2D, Gatunek: Dramat)",
                "Bilet VIP - Rząd 1, Miejsce 8 (Status: Opłacony)"
        );

        //1. filtrowanie strumienia
        System.out.println("Zadanie 1");
        List<String> przefiltrowane = elementyKina.stream().filter(element -> element.contains("3D") || element.contains("Opłacony")).collect(Collectors.toList());
        przefiltrowane.forEach(System.out::println);


        //2. elementy powtarzajace sie
        System.out.println("Zadanie 2");
        List<String> statusyBiletow = Arrays.asList("Opłacony", "Zarezerwowany", "Opłacony", "Anulowany", "Opłacony", "Zarezerwowany");

        Map<String, Long> powtorzeniaStatusow = statusyBiletow.stream().collect(Collectors.groupingBy(status -> status, Collectors.counting()));
        powtorzeniaStatusow.forEach((status, ile) -> System.out.println("Status '" + status + "': wystepuje " + ile + " razy"));


        //3. mapowanie na strumieniu
        System.out.println("Zadanie 3");
        Stream<String> strumienFilmow = elementyKina.stream().filter(e -> e.startsWith("Film: "))
                .map(e -> {
                    int start = e.indexOf("Film: ") + 6;
                    int end = e.indexOf(" (");
                    return e.substring(start, end).trim();
                });

        List<String> listaTytulow = strumienFilmow.collect(Collectors.toList());
        listaTytulow.forEach(tytul -> System.out.println("Zmapowane: " + tytul));


        //4. Zapis i odczyt z pliku
        System.out.println("Zadanie 4");

        String nazwaPliku = "repertuar.txt";

        try{
            File plik = new File(nazwaPliku);
            FileWriter fileWriter = new FileWriter(plik, false);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("Repertuar kina");
            for (String tytul : listaTytulow){
                printWriter.println(tytul);
            }
            printWriter.close();
            System.out.println("Pomyslnie zapisano plik");
        }catch (IOException e){
            System.err.println("Błąd zapisu: " + e.getMessage());
        }

        try{
            Path sciezka = Paths.get(nazwaPliku);
            List<String> odczytaneLinie = Files.readAllLines(sciezka);

            System.out.println("Odczytana zawartosc pliku: ");
            odczytaneLinie.forEach(linia -> System.out.println(linia));
        }catch (IOException e){
            System.err.println("Błąd zapisu: " + e.getMessage());
        }

        //5. Filtrowanie i sortowanie z pliku
        try{
            Path sciezka = Paths.get(nazwaPliku);
            List<String> posortowaneFilmy = Files.lines(sciezka)
            .filter(linia -> !linia.startsWith("---"))
                    .sorted()
                    .collect(Collectors.toList());

            System.out.println("Filmu po filtrowaniu i sortowaniu: ");
            posortowaneFilmy.forEach(film -> System.out.println(" * " + film));
        } catch (IOException e){
            System.err.println("Błąd: " + e.getMessage());
        }

        // LAB 3.1 - Zapis i odczyt XML
        System.out.println("\nLAB 3.1 - XML");

        List<Movie> movies = Arrays.asList(
                new Movie(1, "Gladiator II", "Akcja"),
                new Movie(2, "W głowie się nie mieści 2", "Animacja"),
                new Movie(3, "Dune: Część druga", "Sci-Fi"),
                new Movie(4, "Joker: Folie à deux", "Dramat")
        );

        Path xmlPath = Paths.get("movies.xml");

        try {
            // Zapis listy do XML
            MovieXmlService.saveMovies(movies, xmlPath);
            System.out.println("Zapisano filmy do XML.");

            // Wyświetlenie zawartości XML
            System.out.println("\nZawartość pliku XML:");
            System.out.println(Files.readString(xmlPath));

            // Odczyt XML do nowej listy
            List<Movie> loadedMovies = MovieXmlService.loadMovies(xmlPath);

            System.out.println("Filmy odczytane z XML:");
            loadedMovies.forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Błąd XML: " + e.getMessage());
        }

    }
}
