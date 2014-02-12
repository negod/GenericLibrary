package com.negod.genericlibrary;

import com.negod.genericlibrary.constants.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            JavaClasses classes = classDefiner.getClassType(childNode);
            switch (classes) {
                case DOUBLE:
                    addContentToDto(rootDto, data, childNode);
                    break;
                case INTEGER:
                    rootDto.set(getEnumProperty(rootDto, data.getKey().toString()), classDefiner.getIntValue(childNode.getValue()));
                    break;
                case LONG:
                    rootDto.set(getEnumProperty(rootDto, data.getKey().toString()), classDefiner.getLongValue(childNode.getValue()));
                    break;
                case STRING:
                    rootDto.set(getEnumProperty(rootDto, data.getKey().toString()), classDefiner.getStringValue(childNode.getValue()));
                    break;
                case ARRAY_LIST:
                    rootDto.set(getEnumProperty(rootDto, data.getKey().toString()), handleCollections(childNode, data.getKey().toString().toLowerCase()));
                    break;
                case ROOT_CLASS:
                    rootDto.set(getEnumProperty(rootDto, data.getKey().toString()), getChildNodes(getNewDto((Element) childNode, Constants.rootClassAttribute), childNode));
                case NO_VALUE:
                    break;
            }
        }
        return rootDto;
    }

    private Collection handleCollections(Element childNode, String childNodes) {

        String fixedString = childNode.getName().substring(0, childNode.getName().length() - 1);
        List elements = childNode.getChildren(fixedString);

        List<Dto> listData = new ArrayList<Dto>();

        for (Object element : elements) {
            try {
                Dto newDto = getChildNodes(getNewDto((Element) element, Constants.rootClassAttribute), (Element) element);
                listData.add(newDto);
            } catch (Exception ex) {
                Logger.getLogger(XmlReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listData;
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

    private void addContentToDto(Dto rootDto, Map.Entry data, Element childNode) throws Exception {
        Enum property = getEnumProperty(rootDto, data.getKey().toString());
        rootDto.set(property, classDefiner.getDoubleValue(childNode.getValue()));
    }
}
