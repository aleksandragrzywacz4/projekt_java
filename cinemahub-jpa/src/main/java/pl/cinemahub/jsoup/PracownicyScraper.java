package pl.cinemahub.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PracownicyScraper {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://cs.pollub.pl/staff/")
                .timeout(10000)
                .get();

        Map<String, List<String>> zaklady = new LinkedHashMap<>();
        String aktualnyZaklad = null;

        for (Element element : doc.select("h3, a[href]")) {

            if (element.tagName().equals("h3")) {
                String naglowek = element.text();

                if (naglowek.startsWith("Zakład ")) {
                    aktualnyZaklad = naglowek;
                    zaklady.putIfAbsent(aktualnyZaklad, new ArrayList<>());
                } else if (naglowek.equals("Pracownia techniczna")
                        || naglowek.equals("Sekretariat")) {
                    aktualnyZaklad = null;
                }

                continue;
            }

            if (aktualnyZaklad == null) {
                continue;
            }

            String pracownik = element.text();

            if (pracownik.startsWith("dr ")
                    || pracownik.startsWith("mgr ")
                    || pracownik.startsWith("inż. ")
                    || pracownik.startsWith("prof. ")) {

                List<String> lista = zaklady.get(aktualnyZaklad);

                if (!lista.contains(pracownik)) {
                    lista.add(pracownik);
                }
            }
        }

        Collator collator = Collator.getInstance(
                Locale.forLanguageTag("pl-PL")
        );

        for (Map.Entry<String, List<String>> wpis : zaklady.entrySet()) {
            System.out.println("\n" + wpis.getKey());

            List<String> pracownicy = wpis.getValue();

            pracownicy.sort((a, b) ->
                    collator.compare(nazwisko(a), nazwisko(b))
            );

            pracownicy.forEach(System.out::println);
        }

        System.out.println("\n=== PRACOWNICY ZE STOPNIEM DR LUB DR INŻ. ===");

        for (Map.Entry<String, List<String>> wpis : zaklady.entrySet()) {
            System.out.println("\n" + wpis.getKey());

            wpis.getValue().stream()
                    .filter(pracownik ->
                            pracownik.startsWith("dr inż. ") ||
                                    (pracownik.startsWith("dr ") &&
                                            !pracownik.startsWith("dr hab.")))
                    .forEach(System.out::println);
        }
    }

    private static String nazwisko(String pracownik) {
        String bezDopisku = pracownik.split(",")[0];
        String[] wyrazy = bezDopisku.trim().split("\\s+");
        return wyrazy[wyrazy.length - 1];
    }
}
