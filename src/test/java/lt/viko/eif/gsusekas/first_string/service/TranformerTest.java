package lt.viko.eif.gsusekas.first_string.service;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBException;
import lt.viko.eif.gsusekas.first_string.model.Book;
import lt.viko.eif.gsusekas.first_string.model.Library;

class TransformerTest {

    private Transformer transformer;
    private Library library;
    private final String testXmlPath = ".src/test/resources/test-library.xml";

    @BeforeEach
    void setUp() {
        transformer = new Transformer();

        List<Book> books = List.of(
                new Book(1, "Java Fundamentals", "John Carter", 320, 29.99f, true),
                new Book(2, "Spring Boot in Practice", "Anna Brown", 410, 35.50f, true)
        );

        library = new Library(
                1,
                "Central City Library",
                "Vilnius",
                true,
                50000.0f,
                books
        );
    }

    @Test
    void shouldTransformPojoToXml() throws JAXBException {
        transformer.transformToXML(library, testXmlPath);

        File xmlFile = new File(testXmlPath);

        assertTrue(xmlFile.exists());
        assertTrue(xmlFile.length() > 0);

        xmlFile.delete();
    }

    @Test
    void shouldTransformXmlToPojo() throws JAXBException {
        transformer.transformToXML(library, testXmlPath);

        Library result = transformer.transformToPOJO(testXmlPath);

        assertNotNull(result);
        assertEquals(library.getId(), result.getId());
        assertEquals(library.getName(), result.getName());
        assertEquals(library.getCity(), result.getCity());
        assertEquals(library.isOpen(), result.isOpen());
        assertEquals(library.getYearlyBudget(), result.getYearlyBudget());
        assertEquals(library.getBooks().size(), result.getBooks().size());

        new File(testXmlPath).delete();
    }
}