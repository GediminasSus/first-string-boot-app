package lt.viko.eif.gsusekas.first_string.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.springframework.stereotype.Component;

import jakarta.xml.bind.JAXBException;
import lt.viko.eif.gsusekas.first_string.model.Library;
import lt.viko.eif.gsusekas.first_string.service.ConsoleOutput;
import lt.viko.eif.gsusekas.first_string.service.Transformer;
import lt.viko.eif.gsusekas.first_string.service.XmlValidation;
;


//// XmlClient class is responsible for connecting to the server, receiving the XML file, validating it against XSD, transforming it to a POJO, and outputting the result to the console.
@Component
public class XmlClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    private final XmlValidation xmlValidation;
    private final Transformer transformer;
    private final ConsoleOutput consoleOutput;

    public XmlClient(XmlValidation xmlValidationService, Transformer xmlTransformer, ConsoleOutput consoleOutput) {
        this.xmlValidation = xmlValidationService;
        this.transformer = xmlTransformer;
        this.consoleOutput = consoleOutput;
    }

    public void receiveXmlFile(String outputFilePath, String xsdFilePath) {
        try (Socket socket = new Socket(HOST, PORT);
             InputStream inputStream = socket.getInputStream();
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
             BufferedOutputStream bufferedFileOutput = new BufferedOutputStream(fileOutputStream)) {

            System.out.println("Connected to server.");

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                bufferedFileOutput.write(buffer, 0, bytesRead);
            }

            bufferedFileOutput.flush();
            System.out.println("XML file received successfully.");

            boolean xsdValid = xmlValidation.validateAgainstXSD(outputFilePath, xsdFilePath);

            if (xsdValid) {
                Library library = transformer.transformToPOJO(outputFilePath);
                consoleOutput.consoleOutput(library);  
            } else {
                System.out.println("Received XML is invalid against XSD.");
            }

        } catch (IOException | JAXBException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
