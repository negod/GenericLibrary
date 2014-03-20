/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericlibrary.classcaster;

import com.negod.genericlibrary.dto.Dto;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
public class CastFromStringUtil {

    public static void setTypedValueInDto(Dto dto, Enum enumField, String value) {
        ClassType classType = decideClass(value);
        switch (classType) {
            case BOOLEAN:
                dto.set(enumField, getBoolean(value));
                break;
            case DOUBLE:
                dto.set(enumField, getDouble(value));
                break;
            case INTEGER:
                dto.set(enumField, getInteger(value));
            case LONG:
                dto.set(enumField, getLong(value));
                break;
            case STRING:
                dto.set(enumField, value);
                break;
            default:
                throw new UnsupportedOperationException("ClassType not supported yet");
        }
    }

    private static ClassType decideClass(String value) {
        if (isBoolean(value)) {
            return ClassType.BOOLEAN;
        } else if (isDouble(value)) {
            return ClassType.DOUBLE;
        } else if (isInteger(value)) {
            return ClassType.INTEGER;
        } else if (isLong(value)) {
            return ClassType.LONG;
        }
        return ClassType.STRING;
    }

    private static boolean isBoolean(String value) {
        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return true;
        }
        return false;
    }

    private static Boolean getBoolean(String value) {
        return Boolean.valueOf(value);
    }

    private static boolean isDouble(String value) {
        try {
            Double doubleValue = new Double(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static Double getDouble(String value) {
        return new Double(value);
    }

    private static boolean isInteger(String value) {
        try {
            Integer doubleValue = new Integer(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static Integer getInteger(String value) {
        return new Integer(value);
    }

    private static boolean isLong(String value) {
        try {
            Long doubleValue = new Long(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static Long getLong(String value) {
        return new Long(value);
    }
}
