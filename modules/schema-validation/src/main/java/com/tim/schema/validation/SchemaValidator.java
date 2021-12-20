package com.tim.schema.validation;

import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * desc: TODO
 *
 * @author Kroos.luo
 * @since 2020-07-29 15:58
 */
public class SchemaValidator {

    public static void main(String[] args) throws IOException, SAXException {
        SchemaValidator.validateX();
    }

    public static void validate() throws IOException, SAXException {
        URL schemaFile = new URL("https://images-na.ssl-images-amazon.com/images/G/01/rainier/help/xsd/release_1_9/Item.xsd");
        Source xmlFile = new StreamSource(new File("/Users/anker/Documents/codeRep/coding-life/modules/schema-validation/src/main/resources/feed.xml"));
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            System.out.println(xmlFile.getSystemId() + " is valid");
        } catch (SAXException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
        } catch (IOException e) {}
    }

    public static void validateX() {
        File docFile = new File("/Users/anker/Documents/codeRep/coding-life/modules/schema-validation/src/main/resources/feed.xml");
        try {
            DOMParser parser = new DOMParser();
            parser.setFeature("http://xml.org/sax/features/validation", true);
            parser.setProperty(
                    "https://images-na.ssl-images-amazon.com/images/G/01/rainier/help/xsd/release_1_9",
                    "Item.xsd");
            parser.parse("/Users/anker/Documents/codeRep/coding-life/modules/schema-validation/src/main/resources/feed.xml");
        } catch (Exception e) {
            System.out.print("Problem parsing the file.");
        }
    }
}
