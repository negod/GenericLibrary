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

    /**
     * Contructor of the Dto Dto<YourEnum> myDto = new Dto(YourEnum.class);
     *
     * @param inEnum
     */
    public Dto(Class<T> inEnum) {
        super(inEnum);
    }

    public Dto(String errorMessage, String errorCode) {
        super(errorMessage, errorCode);
    }

    /**
     * Gets the value (Casted) Dto.<String>get(Emun.type);
     * Dto.<Boolean>.get(Enum.type); etc...
     *
     * @param <P>
     * @param field
     * @return
     */
    @Override
    public DataGetter get(T field) {
        if (super.fields != null) {
            if (super.fields.get(field) != null) {
                return super.fields.get(field).getValue();
            }
        }
        return new DataGetter(null);
    }

    /**
     * Returns a Value that can be used to check which type the Value has or if
     * it iss null etc....
     *
     * @param <P>
     * @param field
     * @return
     */
    @Override
    public <P> Value<P> getValue(T field) {
        return super.fields.<P>get(field);
    }

    /**
     * Gets all the fields (Keys) in th Dto
     *
     * @return
     */
    @Override
    public Collection<T> getFields() {
        return super.fields.keySet();
    }

    /**
     * Gets all the values in the Dto
     *
     * @return
     */
    @Override
    public Collection<Value> getValues() {
        return super.fields.values();
    }

    /**
     * Gets the name of the enum defining the current Dto
     *
     * @return
     */
    @Override
    public Class<T> getEnumType() {
        return super.fieldType;
    }
}
