package com.negod.genericlibrary.xml;

import com.negod.genericlibrary.constants.XmlType;
import com.negod.genericlibrary.dto.Dto;
import com.negod.genericlibrary.constants.Constants;
import com.negod.genericlibrary.constants.filehandler.GenericFile;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
public class XmlFileHandler {

    XmlCreator parser = new XmlCreator();
    XmlParser reader = new XmlParser();

    public XmlFileHandler() {
        GenericFile.createHiddenFolderIfNotExists(Constants.XML_TEMPLATE_FOLDER);
    }

    /**
     * Parses the Xml file to a new Dto
     *
     * @param fileName The xml file
     * @return
     */
    public Dto getXmlFileAsDto(String fileName) {
        fileName = addXmlEndingToFile(fileName);
        if (GenericFile.fileExists(fileName)) {
            try {
                Map<XmlType, Document> documents = new EnumMap<>(XmlType.class);
                Document prettyPrintDoc = getDocumentFromFile(fileName);
                Document classDefinedDoc = getDocumentFromFile(buildTemplateFileame(fileName));
                documents.put(XmlType.PRETTY_PRINT, prettyPrintDoc);
                documents.put(XmlType.CLASS_DEFINED, classDefinedDoc);
                return reader.parseFileData(documents);
            } catch (Exception ex) {
                Logger.getLogger(XmlFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Create a new Xml file based on the Dto tha acts a holder for all the xml
     * data
     *
     * @param dto The data for the xml file
     * @param fileName The filename för the xml file
     */
    public void createXml(Dto dto, String fileName) {
        try {
            fileName = addXmlEndingToFile(fileName);
            GenericFile.createFolderIfNotExists(fileName);
            Map<XmlType, Document> documents = parser.createXmlDocument(dto);
            createXmlFiles(documents, fileName);
        } catch (Exception ex) {
            Logger.getLogger(XmlFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String addXmlEndingToFile(String fileName) {
        return fileName + Constants.XML_FILE_EXTENSION;
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

    private void createXmlFiles(Map<XmlType, Document> documents, String fileName) throws Exception {
        try {
            createXmlFile(documents.get(XmlType.CLASS_DEFINED), buildTemplateFileame(fileName));
            createXmlFile(documents.get(XmlType.PRETTY_PRINT), fileName);
        } catch (IOException io) {
            throw new Exception("Cannot create XmlFile " + fileName, io);
        } catch (Exception e) {
            throw new Exception("Cannot create XmlFile " + fileName, e);
        }
    }

    private String buildTemplateFileame(String fileName) {
        return Constants.XML_TEMPLATE_FOLDER + fileName
                + Constants.XML_TEMPLATE_FILE_EXTENSION
                + Constants.XML_FILE_EXTENSION;
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
