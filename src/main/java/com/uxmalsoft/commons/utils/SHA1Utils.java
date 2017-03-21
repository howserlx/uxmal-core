/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * SHA1Utils.java
 * Hash 1 Utilities
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
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Utilidades de Secure Hash Algorithm 1
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class SHA1Utils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private SHA1Utils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Obtiene el SHA 1
     * 
     * @param data
     * @return String (40 caracteres)
     */
    public static String getSHA(String data) {
        return getData(data, "SHA-1");
    }// getSHA


    /**
     * Obtiene el SHA de una cadena (private)
     * 
     * @param data
     * @param algorithm
     * @return
     */
    private static String getData(String data, String algorithm) {
        if (data == null)
            return null;
        MessageDigest md;
        String res = "";
        try {
            md = MessageDigest.getInstance(algorithm);
            md.update(data.getBytes("UTF-8"));

            byte[] hash = md.digest();

            res = ConversionUtils.bytesToHex(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            SystemLog.writeUtilsException(ex);
            res = null;
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
            res = null;
        }
        return res;
    }// getData


    /**
     * Obtiene el SHA 1 de un archivo
     * 
     * @param path
     * @return String (40 caracteres)
     */
    public static String getSHA_fromFile(String path) {
        return getData_fromFile(path, "SHA-1");
    }// getSHA_fromFile


    /**
     * Obtiene el SHA de un archivo (private)
     * 
     * @param data
     * @param algorithm
     * @return
     */
    private static String getData_fromFile(String path, String algorithm) {
        if (path == null)
            return null;
        if (!(new File(path).exists()))
            return null; // Si no existe

        MessageDigest md;
        String res = "";
        try {
            FileInputStream fis = new FileInputStream(path);
            md = MessageDigest.getInstance(algorithm);

            byte[] dataBytes = new byte[1024];

            int nread = 0;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            } ;

            byte[] hash = md.digest();
            res = ConversionUtils.bytesToHex(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            SystemLog.writeUtilsException(ex);
            res = null;
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
            res = null;
        }
        return res;
    }// getData_fromFile


    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
