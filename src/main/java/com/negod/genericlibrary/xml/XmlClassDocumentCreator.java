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
 * @author Joakim
 */
public class XmlClassDocumentCreator {

    private final String CLASS_ATTRIBUTE = Constants.CLASSTYPE_NODE;
    private Map<String, String> classTypes = new HashMap<String, String>();

    public Document getClassDocument(Dto dto) {
        classTypes.clear();
        Element rootnode = new Element(CLASS_ATTRIBUTE);
        classTypes = extractClassTypes(classTypes, dto);
        addClassTypesToElememt(classTypes, rootnode);
        return new Document(rootnode);
    }

    private Map<String, String> extractClassTypes(Map<String, String> classTypes, Dto dto) {

        classTypes.put(dto.getEnumType().getSimpleName().toLowerCase(), dto.getEnumType().getCanonicalName());

        Collection<Value> values = dto.getValues();
        for (Value value : values) {
            if (value.isDto()) {
                Dto dtoValue = (Dto) value.getValue();
                classTypes.put(dtoValue.getEnumType().getSimpleName().toLowerCase(), dtoValue.getEnumType().getCanonicalName());
                extractClassTypes(classTypes, dtoValue);
            } else if (value.isCollection()) {
                Collection dataCollection = (Collection) value.getValue();
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
            childToParentNode.setAttribute(CLASS_ATTRIBUTE, entry.getValue());
            parentNode.addContent(childToParentNode);
        }
    }
}
