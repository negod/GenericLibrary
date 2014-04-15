/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericlibrary.xml;

import com.negod.genericlibrary.constants.Constants;
import com.negod.genericlibrary.dto.Dto;
import com.negod.genericlibrary.dto.Value;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;

/**
 * 
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
class XmlClassDocumentCreator {

    private final String CLASSTYPE_NODE = Constants.CLASSTYPE_NODE;
    private final String CLASSTYPE_ATTRIBUTE = Constants.CLASSTYPE_ATTRIBUTE;
    private Map<String, String> classTypes = new HashMap<String, String>();

    public Document getClassDocument(Dto dto) {
        classTypes.clear();
        Element rootnode = new Element(CLASSTYPE_NODE);
        classTypes = extractClassTypes(classTypes, dto);
        addClassTypesToElememt(classTypes, rootnode);
        return new Document(rootnode);
    }

    private Map<String, String> extractClassTypes(Map<String, String> classTypes, Dto dto) {

        classTypes.put(dto.getEnumType().getSimpleName().toLowerCase(), dto.getEnumType().getCanonicalName());

        Collection<Value> values = dto.getValues();
        for (Value value : values) {
            if (value.isDto()) {
                Dto dtoValue = value.getValue().getDto();
                classTypes.put(dtoValue.getEnumType().getSimpleName().toLowerCase(), dtoValue.getEnumType().getCanonicalName());
                extractClassTypes(classTypes, dtoValue);
            } else if (value.isCollection()) {
                Collection dataCollection = (Collection) value.getValue().getCollection();
                for (Object obj : dataCollection) {
                    if (obj instanceof Dto) {
                        Dto dtoValue = (Dto) obj;
                        extractClassTypes(classTypes, dtoValue);
                    }
                }
            }
        }
        return classTypes;
    }

    private void addClassTypesToElememt(Map<String, String> classTypes, Element parentNode) {
        for (Map.Entry<String, String> entry : classTypes.entrySet()) {
            Element childToParentNode = new Element(entry.getKey());
            childToParentNode.setAttribute(CLASSTYPE_ATTRIBUTE, entry.getValue());
            parentNode.addContent(childToParentNode);
        }
    }
}
