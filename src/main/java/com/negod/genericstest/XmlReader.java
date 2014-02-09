package com.negod.genericstest;

import com.negod.genericstest.constants.Constants;
import java.util.Collection;
import java.util.Map;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

/**
 *
 * @author Joakim Johansson
 */
public class XmlReader {

    ClassDefiner classDefiner = new ClassDefiner();

    public Dto parseFileData(Document document) throws Exception {
        try {
            Element rootNode = document.getRootElement();
            Dto rootDto = getNewDto(rootNode, Constants.rootClassAttribute);
            return getChildNodes(rootDto, rootNode);
        } catch (Exception e) {
            throw new Exception("Error when parsing xml file", e);
        }
    }

    public Dto getChildNodes(Dto rootDto, Element rootNode) throws Exception {
        for (Map.Entry data : rootDto.getFields().entrySet()) {
            Element childNode = rootNode.getChild(data.getKey().toString().toLowerCase());
            switch (classDefiner.getClassType(childNode)) {
                case DOUBLE:
                    rootDto.set(getEnumProperty(rootDto, data.getKey().toString()), classDefiner.getDoubleValue(childNode.getValue()));
                case INTEGER:
                    rootDto.set(getEnumProperty(rootDto, data.getKey().toString()), classDefiner.getIntValue(childNode.getValue()));
                case LONG:
                    rootDto.set(getEnumProperty(rootDto, data.getKey().toString()), classDefiner.getLongValue(childNode.getValue()));
                case STRING:
                    rootDto.set(getEnumProperty(rootDto, data.getKey().toString()), classDefiner.getStringValue(childNode.getValue()));
                case ARRAY_LIST:
                    handleCollections();
                case NO_VALUE:
                    rootDto.set(getEnumProperty(rootDto, data.getKey().toString()), childNode.getValue());
            }
        }
        return rootDto;
    }

    private Collection handleCollections() {
        return null;
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
