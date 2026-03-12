package lt.viko.eif.gsusekas.first_string.service;


import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.stereotype.Service;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Service class responsible for XML validation against DTD and XSD.
 */
@Service
public class XmlValidation {

    /**
     * Validates an XML file against its DTD declaration.
     *
     * @param xmlFilePath path to XML file
     * @return true if valid, false otherwise
     */
    public boolean validateAgainstDTD(String xmlFilePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new SimpleErrorHandler());

            builder.parse(new File(xmlFilePath));

            System.out.println("XML is valid against DTD.");
            return true;
        } catch (Exception e) {
            System.out.println("DTD validation failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validates an XML file against an XSD schema.
     *
     * @param xmlFilePath path to XML file
     * @param xsdFilePath path to XSD file
     * @return true if valid, false otherwise
     */
    public boolean validateAgainstXSD(String xmlFilePath, String xsdFilePath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdFilePath));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlFilePath)));

            System.out.println("XML is valid against XSD.");
            return true;
        } catch (SAXException | IOException e) {
            System.out.println("XSD validation failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Simple error handler for DTD validation.
     */
    private static class SimpleErrorHandler implements ErrorHandler {

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            throw exception;
        }
    }
}
