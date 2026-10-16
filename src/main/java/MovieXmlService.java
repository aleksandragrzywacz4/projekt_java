
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

    // Odczyt filmów z XML do listy obiektów
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

            int id = Integer.parseInt(
                    element.getElementsByTagName("id")
                            .item(0).getTextContent()
            );

            String title = element.getElementsByTagName("title")
                    .item(0).getTextContent();

            String genre = element.getElementsByTagName("genre")
                    .item(0).getTextContent();

            Movie movie = new Movie(id, title, genre);
            movies.add(movie);
        }

        return movies;
    }
}
