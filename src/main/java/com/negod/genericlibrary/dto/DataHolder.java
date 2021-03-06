/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericlibrary.dto;

import java.util.Collection;
import java.util.EnumMap;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 * @param <T>
 */
public abstract class DataHolder<T extends Enum<T>> extends DtoErrorHandler {

    EnumMap<T, Value> fields = null;
    Class<T> fieldType;

    public DataHolder(Class<T> inEnum) {
        fields = new EnumMap<>(inEnum);
        fieldType = inEnum;
    }
    
    public  DataHolder(String message, String code){
        super(message, code);
    }

    /**
     * Sets a field in the Dto
     *
     * @param field
     * @param data
     */
    public void set(T field, Boolean data) {
        if (data != null) {
            fields.put(field, new Value(data, field));
        }
    }

    /**
     * Sets a field in the Dto
     *
     * @param field
     * @param data
     */
    public void set(T field, String data) {
        if (data != null) {
            fields.put(field, new Value(data, field));
        }
    }

    /**
     * Sets a field in the Dto
     *
     * @param field
     * @param data
     */
    public void set(T field, Integer data) {
        if (data != null) {
            fields.put(field, new Value(data, field));
        }
    }

    /**
     * Sets a field in the Dto
     *
     * @param field
     * @param data
     */
    public void set(T field, Double data) {
        if (data != null) {
            fields.put(field, new Value(data, field));
        }
    }

    /**
     * Sets a field in the Dto
     *
     * @param field
     * @param data
     */
    public void set(T field, Long data) {
        if (data != null) {
            fields.put(field, new Value(data, field));
        }
    }

    /**
     * Sets a field in the Dto
     *
     * @param field
     * @param data
     */
    public void set(T field, Collection<Dto> data) {
        if (data != null) {
            fields.put(field, new Value(data, field));
        }
    }

    /**
     * Sets a field in the Dto
     *
     * @param field
     * @param data
     */
    public void set(T field, Dto data) {
        if (data != null) {
            fields.put(field, new Value(data, field));
        }
    }

    public abstract DataGetter get(T field);

    public abstract <P> Value<P> getValue(T field);

    public abstract Collection<T> getFields();

    public abstract Collection<Value> getValues();

    public abstract Class<T> getEnumType();
}
