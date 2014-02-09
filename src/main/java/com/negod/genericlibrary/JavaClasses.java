package com.negod.genericlibrary;

/**
 *
 * @author Joakim
 */
public enum JavaClasses {

    INTEGER("java.lang.Integer"),
    STRING("java.lang.String"),
    DOUBLE("java.lang.Double"),
    LONG("java.lang.Long"),
    ARRAY_LIST("java.util.ArrayList"),
    NO_VALUE("");
    private String className;

    private JavaClasses(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public static JavaClasses getConstant(String value) {
        for (JavaClasses javaClass : JavaClasses.values()) {
            if (javaClass.getClassName().equals(value)) {
                return javaClass;
            }
        }
        return JavaClasses.NO_VALUE;
    }
}
