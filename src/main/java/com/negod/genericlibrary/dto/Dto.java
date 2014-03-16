/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericlibrary.dto;

import java.util.Collection;

/**
 * 
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 * @param <T> 
 */
public class Dto<T extends Enum<T>> extends DataHolder<T> {

    public Dto(Class<T> inEnum) {
        super(inEnum);
    }

    /**
     * Gets the selected field from the Dto Returns null if the Dto does not
     * contain the field
     *
     * @param field
     * @return
     */
    public Value get(T field) {
        return super.fields.get(field);
    }

    /**
     * Gets all the fields (Keys) in th Dto
     *
     * @return
     */
    public Collection<T> getFields() {
        return super.fields.keySet();
    }

    /**
     * Gets all the values in the Dto
     *
     * @return
     */
    public Collection<Value> getValues() {
        return super.fields.values();
    }

    /**
     * Gets the name of the enum defining the current Dto
     *
     * @return
     */
    public Class<T> getEnumType() {
        return super.fieldType;
    }
}
