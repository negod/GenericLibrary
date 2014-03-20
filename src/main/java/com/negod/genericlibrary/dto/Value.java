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
public class Value<T> {

    private final T value;
    private final Enum field;

    public Value(T value, Enum field) {
        this.field = field;
        this.value = value;
    }

    public String getFieldName() {
        return field.name();
    }

    public T getValue() {
        return value;
    }

    public Class getClassType() {
        return value.getClass();
    }

    public String getFullClassName() {
        return value.getClass().getCanonicalName();
    }

    public boolean isNull() {
        return value == null;
    }

    public boolean isNotNull() {
        return value != null;
    }

    public boolean isInteger() {
        return getValue() instanceof Integer;
    }

    public boolean isString() {
        return getValue() instanceof String;
    }

    public boolean isBoolean() {
        return getValue() instanceof Boolean;
    }

    public boolean isCollection() {
        return getValue() instanceof Collection;
    }

    public boolean isDto() {
        return getValue() instanceof Dto;
    }

    @Override
    public String toString() {
        if (!isNull()) {
            return value.toString();
        } else {
            return "";
        }
    }
}
