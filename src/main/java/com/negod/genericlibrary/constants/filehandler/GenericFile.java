/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericlibrary.constants.filehandler;

import java.io.File;

/**
 *
 * @author Joakim
 */
public class GenericFile {

    /**
     * Checks if the file exists
     *
     * @param fileName Path to the file to check
     * @return
     */
    public static boolean fileExists(String fileName) {
        return new File(fileName).exists();
    }
}
