/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericlibrary;

/**
 *
 * @author Joakim
 */
public enum Phone implements GenericDto {

    HOME, MOBILE;

    public String getFieldName() {
        return name();
    }

    public String getXmlRootNode() {
        return "Phone";
    }
}
