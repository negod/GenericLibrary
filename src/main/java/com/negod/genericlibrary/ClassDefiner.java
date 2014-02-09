/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericlibrary;

import com.negod.genericlibrary.constants.Constants;
import org.jdom.Attribute;
import org.jdom.Element;

/**
 *
 * @author Joakim
 */
public class ClassDefiner {

    public JavaClasses getClassType(Element element) throws Exception {
        if (containsAttribute(element, Constants.classAttribute)) {
            return JavaClasses.getConstant(element.getAttribute(Constants.classAttribute).getValue());
        } else if (containsAttribute(element, Constants.rootClassAttribute)) {
            return JavaClasses.getConstant(element.getAttribute(Constants.rootClassAttribute).getValue());
        } else if (containsAttribute(element, Constants.collectionAttribute)) {
            return JavaClasses.getConstant(element.getAttribute(Constants.collectionAttribute).getValue());
        } else {
            return JavaClasses.NO_VALUE;
        }
    }

    public Integer getIntValue(String data) {
        return new Integer(data);
    }

    public Double getDoubleValue(String data) {
        return new Double(data);
    }

    public Long getLongValue(String data) {
        return new Long(data);
    }

    public String getStringValue(String data) {
        return data;
    }

    private boolean containsAttribute(Element element, String inAttribute) {
        Attribute attribute = element.getAttribute(inAttribute);
        return attribute == null ? false : true;
    }
}
