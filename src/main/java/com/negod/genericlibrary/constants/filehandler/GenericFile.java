/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericlibrary.constants.filehandler;

import com.negod.genericlibrary.constants.Constants;
import com.negod.genericlibrary.xml.XmlFileHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
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

    public static boolean createHiddenFolderIfNotExists(String Folder) {
        File file = new File(Folder);
        if (!file.exists()) {
            try {
                file.mkdir();
                Path path = FileSystems.getDefault().getPath(Constants.XML_TEMPLATE_FOLDER);
                Files.setAttribute(path, Constants.HIDDEN_FILE_ATTRIBUTE, true);
                return true;
            } catch (IOException ex) {
                Logger.getLogger(XmlFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
