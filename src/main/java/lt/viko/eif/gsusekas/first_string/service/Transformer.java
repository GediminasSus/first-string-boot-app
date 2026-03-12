package lt.viko.eif.gsusekas.first_string.service;

import java.io.File;

import org.springframework.stereotype.Service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lt.viko.eif.gsusekas.first_string.model.Library;

@Service
public class Transformer {

    /**
     * Transforms a Library POJO into an XML file.
     *
     * @param library the library object to transform
     * @param filePath the target XML file path
     * @throws JAXBException if marshalling fails
     */
    public void transformToXML(Library library, String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Library.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        

        File file = new File(filePath);
        marshaller.marshal(library, file);

        System.out.println("XML file created successfully at: " + file.getAbsolutePath());
        marshaller.marshal(library, System.out);
    }

    /**
     * Transforms an XML file into a Library POJO.
     *
     * @param filePath the XML file path
     * @return Library object created from XML
     * @throws JAXBException if unmarshalling fails
     */
    public Library transformToPOJO(String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Library.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        File file = new File(filePath);
        Library library = (Library) unmarshaller.unmarshal(file);

        System.out.println("XML transformed back to POJO successfully.");
        return library;
    }
}
