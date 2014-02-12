/*
 *
 */
package com.negod.genericlibrary;

import com.negod.genericlibrary.constants.Constants;
import java.util.Collection;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;

/**
 *
 * @author Joakim Johansson
 */
class XmlCreator {

    ClassDefiner definer = new ClassDefiner();

    public Document createXmlDocument(Dto dto) {
        return new Document(addChildnodes(dto));
    }

    /**
     * This method is used recursively to add childnodes to the rootnode. the
     * rootnode is created based on the Dto that binds all data toghether.
     *
     * @param dto The fields and data to be added in the childnode.
     * @return The childnode element
     */
    Element addChildnodes(Dto dto) {
        Element currentRootNode = new Element(dto.getEnumType().getSimpleName().toLowerCase());
        currentRootNode.setAttribute(Constants.rootClassAttribute, dto.getEnumType().getName());
        for (Map.Entry data : dto.getFields().entrySet()) {
            Element childToCurrentRootNode = new Element(data.getKey().toString().toLowerCase());
            ElementContentType content = ElementContentType.OTHER;

            if (data.getValue() != null) {
                content = addContentToChildNode(data, currentRootNode, childToCurrentRootNode);
            }

            if (!content.equals(ElementContentType.DTO)) {
                currentRootNode.addContent(childToCurrentRootNode);
            }

        }
        return currentRootNode;
    }

    /**
     * Adds content to the current rootnode by adding childnodes poopulated with
     * data. If the data to populate the childnode is a collection, another
     * method takes care of that (HandleCollections). If the data is a Dto, this
     * method calls back to recursive function addChildnodes
     *
     * @param data The data to add to the childnode
     * @param currentRootNode The parent node to the childnode that is to be
     * populated
     * @param childToCurrentRootNode The childnode that will be populated with
     * data and added to the current rootNode v
     */
    ElementContentType addContentToChildNode(Map.Entry data, Element currentRootNode, Element childToCurrentRootNode) {
        if (data.getValue() instanceof Dto) {
            currentRootNode.addContent(addChildnodes((Dto) data.getValue()));
            return ElementContentType.DTO;
        } else if (data.getValue() instanceof Collection) {
            HandleCollections(data, childToCurrentRootNode);
            return ElementContentType.COLLECTION;
        } else {
            childToCurrentRootNode.addContent(data.getValue().toString());
            childToCurrentRootNode.setAttribute(Constants.classAttribute, data.getValue().getClass().getName());
            return ElementContentType.OTHER;
        }
    }

    /**
     * This method is used if a field in the Dto is a collection Loop through
     * the collection and add data to the current node
     *
     * @param data
     * @param childToCurrentRootNode
     */
    void HandleCollections(Map.Entry data, Element childToCurrentRootNode) {
        childToCurrentRootNode.setAttribute(Constants.collectionAttribute, data.getValue().getClass().getName());
        for (Object collectionData : (Collection) data.getValue()) {
            if (collectionData != null) {
                addContentToNode(collectionData, childToCurrentRootNode);
            }
        }
    }

    /**
     * Adds textual content to an element. If the data is a Dto, this method
     * calls back to recursive function addChildnodes, otherwise it just adds
     * the textual content.
     *
     * @param collectionData
     * @param childToCurrentRootNode
     */
    void addContentToNode(Object collectionData, Element childToCurrentRootNode) {
        if (collectionData instanceof Dto) {
            childToCurrentRootNode.addContent(addChildnodes((Dto) collectionData));
        } else {
            childToCurrentRootNode.addContent(collectionData.toString());
            childToCurrentRootNode.setAttribute(Constants.classAttribute, collectionData.getClass().getSimpleName());
        }
    }
}
