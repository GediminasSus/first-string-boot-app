package lt.viko.eif.gsusekas.first_string.service;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import lt.viko.eif.gsusekas.first_string.model.Book;
import lt.viko.eif.gsusekas.first_string.model.Library;
import lt.viko.eif.gsusekas.first_string.network.XmlClient;
import lt.viko.eif.gsusekas.first_string.service.ConsoleOutput;
import lt.viko.eif.gsusekas.first_string.service.Transformer;
import lt.viko.eif.gsusekas.first_string.service.XmlValidation;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XmlValidationServiceTest {

    private XmlValidation xmlValidation;
    private Transformer transformer;
    private Library library;

    private final String validXmlPath = "valid-library.xml";
    private final String invalidXmlPath = "invalid-library.xml";
    private final String xsdPath = "src/main/resources/xml/library.xsd";

    @BeforeEach
    void setUp() {
        xmlValidation = new XmlValidation();
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
    void shouldValidateCorrectXmlAgainstXsd() throws JAXBException {
        transformer.transformToXML(library, validXmlPath);

        boolean isValid = xmlValidation.validateAgainstXSD(validXmlPath, xsdPath);

        assertTrue(isValid);

        new File(validXmlPath).delete();
    }

    @Test
    void shouldRejectInvalidXmlAgainstXsd() throws Exception {
        String invalidXmlContent = """
                <?xml version="1.0" encoding="UTF-8"?>
                <library>
                    <id>1</id>
                    <name>Central City Library</name>
                    <city>Vilnius</city>
                    <open>true</open>
                    <yearlyBudget>INVALID_FLOAT</yearlyBudget>
                    <books>
                        <book>
                            <id>1</id>
                            <title>Java Fundamentals</title>
                            <author>John Carter</author>
                            <pages>320</pages>
                            <price>29.99</price>
                            <available>true</available>
                        </book>
                    </books>
                </library>
                """;

        java.nio.file.Files.writeString(java.nio.file.Path.of(invalidXmlPath), invalidXmlContent);

        boolean isValid = xmlValidation.validateAgainstXSD(invalidXmlPath, xsdPath);

        assertFalse(isValid);

        new File(invalidXmlPath).delete();
    }
}
    

