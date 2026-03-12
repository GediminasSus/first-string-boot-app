package lt.viko.eif.gsusekas.first_string;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.xml.bind.JAXBException;
import lt.viko.eif.gsusekas.first_string.model.Book;
import lt.viko.eif.gsusekas.first_string.model.Library;
import lt.viko.eif.gsusekas.first_string.service.Transformer;
import lt.viko.eif.gsusekas.first_string.service.XmlValidation;

@Component
public class TestRunner implements CommandLineRunner {

    private final Transformer xmlTransformer;
    private final XmlValidation xmlValidationService;

    public TestRunner(Transformer xmlTransformer, XmlValidation xmlValidationService) {
        this.xmlTransformer = xmlTransformer;
        this.xmlValidationService = xmlValidationService;
    }

    @Override
    public void run(String... args) throws JAXBException {
        List<Book> books = List.of(
                new Book(1, "Java Fundamentals", "John Carter", 320, 29.99f, true),
                new Book(2, "Spring Boot in Practice", "Anna Brown", 410, 35.50f, true),
                new Book(3, "XML Essentials", "Robert White", 280, 21.75f, false),
                new Book(4, "Data Structures", "Emily Stone", 390, 32.40f, true),
                new Book(5, "Algorithms Explained", "Michael Reed", 360, 30.00f, true),
                new Book(6, "Computer Networks", "Laura Green", 450, 39.99f, false),
                new Book(7, "Database Design", "David Black", 300, 27.80f, true),
                new Book(8, "Operating Systems", "Susan King", 500, 42.25f, true),
                new Book(9, "Software Testing", "Chris Adams", 260, 24.60f, false),
                new Book(10, "Artificial Intelligence Basics", "Linda Moore", 340, 36.90f, true)
        );

        Library library = new Library(
                1,
                "Central City Library",
                "Vilnius",
                true,
                50000.0f,
                books
        );

        String xmlPath = "src/main/resources/xml/library.xml";
        String xsdPath = "src/main/resources/xml/library.xsd";

        xmlTransformer.transformToXML(library, xmlPath);

        boolean dtdValid = xmlValidationService.validateAgainstDTD(xmlPath);
        boolean xsdValid = xmlValidationService.validateAgainstXSD(xmlPath, xsdPath);

        if (dtdValid && xsdValid) {
            Library convertedLibrary = xmlTransformer.transformToPOJO(xmlPath);

            System.out.println("\n--- POJO DATA ---");
            System.out.println("Library ID: " + convertedLibrary.getId());
            System.out.println("Library Name: " + convertedLibrary.getName());
            System.out.println("City: " + convertedLibrary.getCity());
            System.out.println("Open: " + convertedLibrary.isOpen());
            System.out.println("Yearly Budget: " + convertedLibrary.getYearlyBudget());

            for (Book book : convertedLibrary.getBooks()) {
                System.out.println("Book ID: " + book.getId()
                        + ", Title: " + book.getTitle()
                        + ", Author: " + book.getAuthor()
                        + ", Pages: " + book.getPages()
                        + ", Price: " + book.getPrice()
                        + ", Available: " + book.isAvailable());
            }
        } else {
            System.out.println("XML validation failed. POJO transformation skipped.");
        }
    }
}
