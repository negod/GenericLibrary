/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericlibrary.xml;

import com.negod.genericlibrary.dto.Dto;
import com.negod.genericlibrary.dto.Value;
import java.util.Collection;
import org.jdom.Document;
import org.jdom.Element;

/**
 * 
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
class XmlDocumentCreator {

    public Document getXmlDocument(Dto dto) {
        Element rootnode = new Element(dto.getEnumType().getSimpleName().toLowerCase());
        addDataToElement(dto, rootnode);
        return new Document(rootnode);
    }

    /**
     * Add all data from the Dto to the root element. This methods adds the data
     * recursively to the rootnode
     *
     * @param dto
     * @param parentNode
     */
    private void addDataToElement(Dto dto, Element parentNode) {
        Element childToParentNode;
        Collection<Value> values = dto.getValues();
        for (Value value : values) {

            childToParentNode = new Element(value.getFieldName().toLowerCase());
            if (value.isDto()) {

                Dto dtoData = value.getValue().getDto();
                addDataToElement(dtoData, childToParentNode);

            } else if (value.isCollection()) {

                Collection dataCollection = (Collection) value.getValue();
                Element childToChildToParentNode;

                for (Object obj : dataCollection) {
                    if (obj instanceof Dto) {
                        Dto dtoValue = (Dto) obj;
                        childToChildToParentNode = new Element(dtoValue.getEnumType().getSimpleName().toLowerCase());
                        addDataToElement(dtoValue, childToChildToParentNode);
                        childToParentNode.addContent(childToChildToParentNode);
                    }
                }

            } else {
                childToParentNode.setText(value.toString());
            }
            parentNode.addContent(childToParentNode);
        }
    }
}
