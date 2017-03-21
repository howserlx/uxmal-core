/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * FileNameUtils.java
 * Utilidades de Nombres de Archivos
 *
 * Created on : 08/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.utils;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.io.File;

/**
 * 
 * Utilidades de nombres de archivos
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class FileNameUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">
    public enum NamePosition {
        START, CONTAINS, ENDS, EQUALS;
    }

    private static final String DIR_SEPARATOR = File.separator;
    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private FileNameUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Normaliza el path de un directorio
     * 
     * @param dirPath
     * @return
     */
    public static String normalizeDirectoryPath(String dirPath) {
        dirPath = validateNull(dirPath).trim();
        return org.apache.commons.io.FilenameUtils.normalize(dirPath);
    }// normalizeDirectoryPath

    /**
     * Normaliza el path de un archivo
     * 
     * @param filePath
     * @return
     */
    public static String normalizeFilePath(String filePath) {
        filePath = validateNull(filePath).trim();
        return org.apache.commons.io.FilenameUtils.normalizeNoEndSeparator(filePath);
    }// normalizeFilePath


    /**
     * Obtiene solamente el nombre del archivo/ultimo directorio
     * 
     * @param filePath
     * @return
     */
    public static String getOnlyName(String filePath) {
        filePath = filePath.trim();
        return org.apache.commons.io.FilenameUtils.getBaseName(filePath);
    }// getOnlyName

    /**
     * Obtiene solamente la extension del archivo
     * 
     * @param filePath
     * @return
     */
    public static String getOnlyExtension(String filePath) {
        filePath = filePath.trim();
        return org.apache.commons.io.FilenameUtils.getExtension(filePath);
    }// getOnlyExtension


    /**
     * Obtiene el directorio superior proximo a f
     * 
     * @param f
     * @return
     */
    public static File getDirectory(File f) {
        File dir = null;
        if (f != null) {
            if (f.isDirectory()) {
                dir = f;
                //
            } else {
                dir = getDirectory(f.getParentFile());
            }
        }
        return dir;
    }// getDirectory

    /**
     * Encuentra una archivo llamado fileName(solo nombre)
     * 
     * @param dir
     * @param fileName
     * @param pos
     * @return
     */
    public static File findFileByName(String dir, String fileName, NamePosition pos) {
        if (pos == null)
            pos = NamePosition.CONTAINS;
        File f = null;

        if (dir != null) {
            File d = new File(dir);
            if (d.isDirectory()) {

                for (File file : d.listFiles()) {
                    switch (pos) {
                        case START:
                            if (getOnlyName(file.getName()).startsWith(fileName))
                                return file;
                            break;

                        case CONTAINS:
                            if (getOnlyName(file.getName()).contains(fileName))
                                return file;
                            break;

                        case ENDS:
                            if (getOnlyName(file.getName()).endsWith(fileName))
                                return file;
                            break;

                        case EQUALS:
                            if (getOnlyName(file.getName()).equals(fileName))
                                return file;
                            break;

                    }// switch
                } // fore
            }
        }

        return f;

    }// findFileByName

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
