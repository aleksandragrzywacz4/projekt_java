package pl.cinemahub.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class GodzinyScraper {

    private static final String URL = "https://cs.pollub.pl/news/";

    record Ogloszenie(LocalDate data, String tytul, String link) {
    }

    public static void main(String[] args) throws IOException {
        List<Ogloszenie> ogloszenia = new ArrayList<>();

        for (int strona = 1; strona <= 20; strona++) {
            String adres = strona == 1
                    ? URL
                    : URL + "page/" + strona + "/";

            Document doc = Jsoup.connect(adres)
                    .timeout(10000)
                    .get();

            var wpisy = doc.select("article");

            if (wpisy.isEmpty()) {
                break;
            }

            for (Element wpis : wpisy) {
                Element link = wpis.selectFirst("h2 a[href], h3 a[href]");

                if (link == null) {
                    continue;
                }

                String adresWpisu = link.absUrl("href");

                Document artykul = Jsoup.connect(adresWpisu)
                        .timeout(10000)
                        .get();

                String tresc = artykul.text()
                        .toLowerCase(Locale.ROOT);

                if (!tresc.contains("godziny dziekańskie")
                        && !tresc.contains("godziny rektorskie")
                        && !tresc.contains("godzin dziekańskich")
                        && !tresc.contains("godzin rektorskich")) {
                    continue;
                }

                Element dataElement = artykul.selectFirst("time[datetime]");

                if (dataElement == null) {
                    continue;
                }

                LocalDate data = LocalDate.parse(
                        dataElement.attr("datetime").substring(0, 10)
                );

                ogloszenia.add(new Ogloszenie(
                        data,
                        link.text(),
                        adresWpisu
                ));
            }
        }

        ogloszenia.sort(Comparator.comparing(Ogloszenie::data));

        System.out.println("=== GODZINY DZIEKAŃSKIE I REKTORSKIE ===");

        if (ogloszenia.isEmpty()) {
            System.out.println("Nie znaleziono ogłoszeń w sprawdzonych aktualnościach.");
        }

        for (Ogloszenie ogloszenie : ogloszenia) {
            System.out.println(
                    ogloszenie.data() + " - " + ogloszenie.tytul()
            );
            System.out.println(ogloszenie.link());
        }
    }
}
