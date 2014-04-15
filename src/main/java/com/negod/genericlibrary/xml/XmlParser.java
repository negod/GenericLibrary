package com.negod.genericlibrary.xml;

import com.negod.genericlibrary.classcaster.CastFromStringUtil;
import com.negod.genericlibrary.classcaster.ClassType;
import com.negod.genericlibrary.dto.Dto;
import com.negod.genericlibrary.constants.XmlType;
import com.negod.genericlibrary.constants.Constants;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
class XmlParser {

    private final String CLASSTYPE_ATTRIBUTE = Constants.CLASSTYPE_ATTRIBUTE;
    Map<String, String> rootClasses = new HashMap<>();

    public Dto parseFileData(Map<XmlType, Document> documents) throws Exception {
        rootClasses.clear();
        try {
            Document doc = documents.get(XmlType.CLASS_DEFINED);
            rootClasses = extractRootClasses(doc);

            Document xmlDoc = documents.get(XmlType.PRETTY_PRINT);
            Dto dto = extractRootDto(xmlDoc);
            extractValuesFromElementToDto(dto, xmlDoc.getRootElement());

            return dto;
        } catch (Exception e) {
            throw new Exception("Error when parsing xml file", e);
        }
    }

    private <T extends Enum<T>> void extractValuesFromElementToDto(Dto rootDto, Element rootElement) {
        Class<T> clazz = rootDto.getEnumType();
        if (clazz.isEnum()) {
            for (T item : EnumSet.allOf(clazz)) {
                Element childElement = rootElement.getChild(item.name().toLowerCase());
                if (childElement != null) {
                    if (isElementRootClass(childElement)) {
                        Dto dto = getDtoByName(item.name().toLowerCase());
                        extractValuesFromElementToDto(dto, rootElement.getChild(item.name().toLowerCase()));
                        rootDto.set(item, dto);
                    } else if (isElementCollection(childElement)) {
                        List<Element> childElements = childElement.getChildren();
                        List<Dto> dtos = handleCollections(childElements);
                        rootDto.set(item, dtos);
                    } else {
                        String value = rootElement.getChildText(item.name().toLowerCase());
                        CastFromStringUtil.setTypedValueInDto(rootDto, item, value);
                    }
                }
            }
        }
    }

    private List<Dto> handleCollections(List<Element> childElements) {
        List<Dto> dtos = new ArrayList<>();
        for (Element element : childElements) {
            if (isElementRootClass(element)) {
                Dto dto = getDtoByName(element.getName());
                extractValuesFromElementToDto(dto, element);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    private Dto getDtoByName(String name) {
        if (rootClasses.containsKey(name)) {
            String rootClassName = rootClasses.get(name);
            try {
                return new Dto((Class<Enum>) Class.forName(rootClassName));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Checks if the name is present as a rootclass in the XmlDocument
     *
     * @param name
     * @return
     */
    private boolean isElementRootClass(Element element) {
        return rootClasses.containsKey(element.getName());
    }

    private boolean isElementCollection(Element element) {
        if (!rootClasses.containsKey(element.getName())) {
            if (element.getChildren().size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extracts the root dto corresponding to the the root element in the xml
     * document
     *
     * @param xmlDoc
     * @return
     */
    private Dto extractRootDto(Document xmlDoc) {
        Element rootElement = xmlDoc.getRootElement();
        String rootClassName = rootClasses.get(rootElement.getName());
        try {
            return new Dto((Class<Enum>) Class.forName(rootClassName));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get the properties from the template file document
     *
     * @param document
     * @return
     */
    private Map<String, String> extractRootClasses(Document document) {
        rootClasses.clear();
        List<Element> elements = document.getRootElement().getChildren();
        for (Element element : elements) {
            rootClasses.put(element.getName(), element.getAttributeValue(CLASSTYPE_ATTRIBUTE));
        }
        return rootClasses;
    }
}
