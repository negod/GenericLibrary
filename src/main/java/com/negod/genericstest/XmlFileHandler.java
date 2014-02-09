package com.negod.genericstest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Joakim Johansson
 */
public class XmlFileHandler {

    XmlCreator xmlcreator = new XmlCreator();
    XmlReader xmlreader = new XmlReader();

    /**
     * Parses the Xml file to a new Dto
     *
     * @param fileName The xml file
     * @return
     * @throws Exception
     */
    public Dto parseXmlToDto(String fileName) throws Exception {
        if (fileExists(fileName)) {
            return xmlreader.parseFileData(getDocumentFromFile(fileName));
        } else {
            throw new Exception("File " + fileName + " does not exist!");
        }
    }

    /**
     * Create a new Xml file based on the Dto tha acts a holder for all the xml
     * data
     *
     * @param dto The data for the xml file
     * @param fileName The filename för the xml file
     * @throws Exception
     */
    public void createXml(Dto dto, String fileName) throws Exception {
        try {
            createXmlFile(xmlcreator.createXmlDocument(dto), fileName);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    /**
     * Checks if the file exists
     *
     * @param fileName Path to the file to check
     * @return
     */
    private boolean fileExists(String fileName) {
        return new File(fileName).exists();
    }

    /**
     * Builds a Document from the xml file
     *
     * @param fileName The path to the xml file
     * @return The document that was built from the xml file
     * @throws Exception
     */
    private Document getDocumentFromFile(String fileName) throws Exception {
        try {
            SAXBuilder builder = new SAXBuilder();
            File xmlFile = new File(fileName);
            return (Document) builder.build(xmlFile);
        } catch (JDOMException ex) {
            throw new Exception("Cannot create xmlDocument from " + fileName);
        } catch (IOException ex) {
            throw new Exception("Cannot create xmlDocument from " + fileName);
        } catch (Exception e) {
            throw new Exception("Cannot create xmlDocument from " + fileName);
        }
    }

    /**
     * Creates an xml file based on a xml document
     *
     * @param document The xml file converted to a Document
     * @param fileName Path to the xml file
     * @throws Exception
     */
    private void createXmlFile(Document document, String fileName) throws Exception {
        try {
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(fileName));
        } catch (IOException io) {
            throw new Exception("Cannot create XmlFile " + fileName, io);
        } catch (Exception e) {
            throw new Exception("Cannot create XmlFile " + fileName, e);
        }
    }
}
