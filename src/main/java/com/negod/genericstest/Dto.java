/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericstest;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joakim
 */
public class Dto {

    Map<String, Object> fields = new HashMap<String, Object>();
    Class<? extends Enum> enumType;

    public <T extends Enum> Dto(Class<T> inEnum) {
        if (inEnum.isEnum()) {
            enumType = inEnum;
            for (Object field : inEnum.getEnumConstants()) {
                fields.put(field.toString(), null);
            }
        } else {
            try {
                throw new Exception("Dto must be set with an ENUM!");
            } catch (Exception ex) {
                Logger.getLogger(Dto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Sets a field in the Dto
     *
     * @param field
     * @param data
     */
    public <S extends Enum, T> void set(Enum field, T data) {
        if (data != null) {
            fields.put(field.name(), data);
        }
    }

    /**
     * Gets the selected field from the Dto Returns null if the Dto does not
     * contain the field
     *
     * @param field
     * @return
     */
    public <S extends Enum, T> T get(S field) {
        return (T) fields.get(field.name());
    }

    /**
     * Gets all the fields in th Dto
     *
     * @return
     */
    public Map<String, Object> getFields() {
        return fields;
    }

    /**
     * Gets the name of the enum defining the current Dto
     *
     * @return
     */
    public Class getEnumType() {
        return enumType;
    }
}
