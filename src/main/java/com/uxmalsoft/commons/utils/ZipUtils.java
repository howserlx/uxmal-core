/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * ZipUtils.java
 * Zip Utilities
 *
 * Created on : 08/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References : zip4j-1.3.2
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.utils;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;

import java.io.File;

import com.uxmalsoft.commons.logging.SystemLog;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * 
 * Zip Utilities
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class ZipUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private ZipUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Construye un zip de un directorio, si tiene password no vacio le coloca password
     * 
     * @param folderName
     * @param zipFileName
     * @param password
     */
    public static boolean zipFolder(String folderName, String zipFileName, String password) {
        boolean res = false;
        try {
            // Initiate ZipFile object with the path/name of the zip file.
            File ff = new File(zipFileName);
            if (ff.exists()) {
                ff.deleteOnExit();
            }

            ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(zipFileName);

            // Initiate Zip Parameters which define various properties such
            // as compression method, etc.
            ZipParameters parameters = new ZipParameters();

            // set compression method to store compression
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            // Set the compression level
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            if (isNotEmpty(password)) {
                parameters.setEncryptFiles(true);
                parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
                parameters.setPassword(password);
            }
            /**
             * whether the root folder need to zip
             */
            parameters.setIncludeRootFolder(false);
            // Add folder to the zip file

            File fFolder = new File(folderName);
            if (fFolder.isDirectory()) {
                zipFile.addFolder(fFolder, parameters);
            } else {
                fFolder.mkdir();
                zipFile.addFolder(fFolder, parameters);
            }

            res = true;
        } catch (ZipException ex) {
            SystemLog.writeUtilsException(ex);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        } finally {

        }
        return res;
    }// zipFolder

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
