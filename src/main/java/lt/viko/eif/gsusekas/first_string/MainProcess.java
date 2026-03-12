package lt.viko.eif.gsusekas.first_string;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lt.viko.eif.gsusekas.first_string.model.Book;
import lt.viko.eif.gsusekas.first_string.model.Library;
import lt.viko.eif.gsusekas.first_string.network.XmlClient;
import lt.viko.eif.gsusekas.first_string.network.XmlServer;
import lt.viko.eif.gsusekas.first_string.service.Transformer;
import lt.viko.eif.gsusekas.first_string.service.XmlValidation;

@Component
public class MainProcess implements CommandLineRunner {

    private final Transformer transformer;
    private final XmlValidation xmlValidation;
    private final XmlServer xmlServer;
    private final XmlClient xmlClient;

    public MainProcess(Transformer transformer, XmlValidation xmlValidation, XmlServer xmlServer, XmlClient xmlClient) {
        this.transformer = transformer;
        this.xmlValidation = xmlValidation;
        this.xmlServer = xmlServer;
        this.xmlClient = xmlClient;
    }

    @Override
    public void run(String... args) throws Exception {
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

        Library library = new Library(1,"Central City Library","Vilnius",true,50000.0f,books);

        String xmlPath = "library.xml";
        String receivedXmlPath = "src/main/resources/xml/received/received-library.xml";
        String xsdPath = "src/main/resources/xml/library.xsd";

        transformer.transformToXML(library, xmlPath);

        boolean xsdValid = xmlValidation.validateAgainstXSD(xmlPath, xsdPath);

        if (!xsdValid) {
            System.out.println("Generated XML is invalid. Process stopped.");
            return;
        }

        Thread serverThread = new Thread(() -> xmlServer.sendXmlFile(xmlPath));
        serverThread.start();

        Thread.sleep(1000);

        xmlClient.receiveXmlFile(receivedXmlPath, xsdPath);

        serverThread.join();
    }
}
    

