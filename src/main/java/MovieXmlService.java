
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MovieXmlService {

    // Zapis listy filmów do XML
    public static void saveMovies(List<Movie> movies, Path path) throws Exception {

        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element root = document.createElement("movies");
        document.appendChild(root);

        for (Movie movie : movies) {
            Element movieElement = document.createElement("movie");
            root.appendChild(movieElement);

            addElement(document, movieElement, "id",
                    String.valueOf(movie.getId()));

            addElement(document, movieElement, "title",
                    movie.getTitle());

            addElement(document, movieElement, "genre",
                    movie.getGenre());
        }

        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        try (OutputStream output = Files.newOutputStream(path)) {
            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(output)
            );
        }
    }

    private static void addElement(Document document, Element parent,
                                   String name, String value) {

        Element element = document.createElement(name);
        element.setTextContent(value);
        parent.appendChild(element);
    }


    public static List<Movie> loadMovies(Path path) throws Exception {

        List<Movie> movies = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl",
                true
        );

        Document document;

        try (InputStream input = Files.newInputStream(path)) {
            document = factory.newDocumentBuilder().parse(input);
        }

        NodeList movieNodes = document.getElementsByTagName("movie");

        for (int i = 0; i < movieNodes.getLength(); i++) {

            Element element = (Element) movieNodes.item(i);

            try {
                String idText = getRequiredField(element, "id");
                String title = getRequiredField(element, "title");
                String genre = getRequiredField(element, "genre");

                int id;

                try {
                    id = Integer.parseInt(idText);
                } catch (NumberFormatException e) {
                    throw new InvalidMovieException(
                            "ID filmu musi być liczbą całkowitą."
                    );
                }

                if (id <= 0) {
                    throw new InvalidMovieException(
                            "ID filmu musi być większe od 0."
                    );
                }

                Movie movie = new Movie(id, title, genre);
                movies.add(movie);

            } catch (InvalidMovieException e) {
                System.out.println(
                        "Nie można utworzyć filmu nr " + (i + 1)
                                + ": " + e.getMessage()
                );
            }
        }

        return movies;
    }

    private static String getRequiredField(Element element, String fieldName)
            throws InvalidMovieException {

        NodeList nodes = element.getElementsByTagName(fieldName);

        if (nodes.getLength() == 0) {
            throw new InvalidMovieException(
                    "Brakuje wymaganego pola: " + fieldName
            );
        }

        String value = nodes.item(0).getTextContent().trim();

        if (value.isEmpty()) {
            throw new InvalidMovieException(
                    "Pole " + fieldName + " nie może być puste."
            );
        }

        return value;
    }

}
