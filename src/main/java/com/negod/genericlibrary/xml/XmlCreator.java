/*
 *
 */
package com.negod.genericlibrary.xml;

import com.negod.genericlibrary.constants.XmlType;
import com.negod.genericlibrary.dto.Dto;
import static com.negod.genericlibrary.constants.XmlType.CLASS_DEFINED;
import java.util.EnumMap;
import java.util.Map;
import org.jdom.Document;

/**
 *
 * @author Joakim Johansson
 */
class XmlCreator {

    XmlClassDocumentCreator classDocCreator = new XmlClassDocumentCreator();
    XmlDocumentCreator xmlDocCreator = new XmlDocumentCreator();
    Map<XmlType, Document> documents = new EnumMap<XmlType, Document>(XmlType.class);

    public Map<XmlType, Document> createXmlDocument(Dto dto) {
        documents.clear();

        for (XmlType type : XmlType.values()) {
            documents.put(type, createDomDocument(dto, type));
        }

        return documents;
    }

    private Document createDomDocument(Dto dto, XmlType xmlType) {
        switch (xmlType) {
            case CLASS_DEFINED:
                return classDocCreator.getClassDocument(dto);
            case PRETTY_PRINT:
                return xmlDocCreator.getXmlDocument(dto);
            default:
                throw new UnsupportedOperationException("XmlType not supported yet");
        }
    }
}
