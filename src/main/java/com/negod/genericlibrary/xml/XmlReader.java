package com.negod.genericlibrary.xml;

import com.negod.genericlibrary.dto.Dto;
import com.negod.genericlibrary.constants.XmlType;
import com.negod.genericlibrary.constants.Constants;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

/**
 *
 * @author Joakim Johansson
 */
public class XmlReader {

    Map<String, String> rootClasses = new HashMap<String, String>();

    public Dto parseFileData(Map<XmlType, Document> documents) throws Exception {
        rootClasses.clear();
        try {
            //rootClasses = extractRootclasses(documents.get(XmlType.CLASS_DEFINED));
            Element rootNode = documents.get(XmlType.PRETTY_PRINT).getRootElement();
            Dto rootDto = getNewDto(rootNode, Constants.ROOTCLASS_ATTRIBUTE);
            return null;
        } catch (Exception e) {
            throw new Exception("Error when parsing xml file", e);
        }
    }

    private Enum getEnumProperty(Dto rootDto, String value) throws Exception {
        Class<Enum> data = (Class<Enum>) Class.forName(rootDto.getEnumType().getName());
        if (Enum.class.isAssignableFrom(data)) {
            return Enum.valueOf(data, value);
        } else {
            return null;
        }
    }

    private boolean containsAttribute(Element element, String inAttribute) {
        Attribute attribute = element.getAttribute(inAttribute);
        return attribute == null ? false : true;
    }

    private Dto getNewDto(Element element, String attribute) throws Exception {
        if (containsAttribute(element, attribute)) {
            String rootClass = element.getAttribute(attribute).getValue();
            return new Dto((Class<Enum>) Class.forName(rootClass));
        } else {
            throw new Exception("Element does not contain class attribute!");
        }
    }
}
