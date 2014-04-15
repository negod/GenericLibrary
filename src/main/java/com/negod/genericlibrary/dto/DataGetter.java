/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericlibrary.dto;

import java.util.Collection;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
public class DataGetter {

    Object value;

    public DataGetter(Object value) {
        this.value = value;
    }

    public boolean isNotNull() {
        if (value == null) {
            return false;
        } else {
            return true;
        }
    }

    public String getString() {
        try {
            return String.valueOf(value);
        } catch (Exception e) {
            return "";
        }
    }

    public Integer getInteger() {
        try {
            return Integer.getInteger(convertToString(value));
        } catch (Exception e) {
            return null;
        }
    }

    public Double getDouble() {
        try {
            return Double.valueOf(convertToString(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Long getLong() {
        try {
            return Long.valueOf(convertToString(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Boolean getBoolean() {
        try {
            return Boolean.valueOf(convertToString(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Collection getCollection() {
        try {
            if (value instanceof Collection) {
                return (Collection) value;
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Dto getDto() {
        try {
            if (value instanceof Dto) {
                return (Dto) value;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private String convertToString(Object value) {
        try {
            return String.valueOf(value);
        } catch (Exception e) {
            return "";
        }
    }

}
