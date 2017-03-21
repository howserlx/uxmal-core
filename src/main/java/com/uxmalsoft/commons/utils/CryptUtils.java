/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * CryptUtils.java
 * Cryptography Utils
 *
 * Created on : 29/05/2015
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.uxmalsoft.commons.exception.CryptException;
import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Cryptography Utils
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class CryptUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    private   static final String ALGORITHM        = "AES";
    protected static final String DEFAULT_ENCODING = "UTF-8";
    protected static final int    IV_SIZE      = 16;
    protected static final int    MAX_KEY_SIZE = 256;
    
    public enum KeyLength {
        K16, K24, K32;
    }

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    protected CryptUtils() {
        // Only public static functions
    }// empty

    // ------------------------------------------------------
    // Key
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Key">
    /**
     * Valida el tamaño de una llave
     * 
     * @param length
     * @return
     */
    public static boolean isValidKeyLength(int length) {
        boolean res = false;

        if (length == 16 || length == 24 || length == 32)
            res = true;

        return res;
    }// isValidKeyLength

    /**
     * Tiene instalada la JCE Unlimited Policy
     * 
     * @return
     * @throws CryptException
     */
    public static boolean hasJCEUnlimitedPolicy() throws CryptException {
        boolean res = false;
        try {
            int maxKeyLen = Cipher.getMaxAllowedKeyLength("AES");
            res = (maxKeyLen > MAX_KEY_SIZE);

        } catch (NoSuchAlgorithmException ex) {
            SystemLog.writeUtilsException(ex);
            throw new CryptException(ex);
        }

        return res;
    }// hasJCEUnlimitedPolicy

    /**
     * Normalize Key
     * 
     * @param key
     * @return
     * @throws CryptException
     */
    public static String normalizeKey(String key) throws CryptException {
        String str = key;
        if (isNotEmpty(key)) {
            try {
                int maxKeyLen = Cipher.getMaxAllowedKeyLength("AES");
                maxKeyLen = (maxKeyLen > MAX_KEY_SIZE) ? MAX_KEY_SIZE : maxKeyLen;
                key = StringUtils.fillString(key, maxKeyLen / 8, '0');
            } catch (NoSuchAlgorithmException ex) {
                SystemLog.writeUtilsException(ex);
                throw new CryptException(ex);
            }

        } else {
            throw new CryptException("Key is Empty");
        }
        return key;
    }// normalizeKey


    /**
     * Normalize Key
     * 
     * @param key
     * @param length
     * @return
     * @throws CryptException
     */
    public static String normalizeKey(String key, KeyLength length) throws CryptException {
        String str = key;
        if (isNotEmpty(key)) {
            try {
                int l = 16;
                switch (length) {
                    case K16:
                        l = 16;
                        break;
                    case K24:
                        l = 24;
                        break;
                    case K32:
                        l = 32;
                        break;
                }
                key = StringUtils.fillString(key, l, '0');
            } catch (Exception ex) {
                SystemLog.writeUtilsException(ex);
                throw new CryptException(ex);
            }

        } else {
            throw new CryptException("Key is Empty");
        }
        return key;
    }// normalizeKey

    // </editor-fold>


    // ------------------------------------------------------
    // Encrypt & Decrypt Data
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Encrypt & Decrypt Data">

    /**
     * Encrypt DATA
     * 
     * @param data
     * @param key
     * @return String
     * @throws CryptException
     */
    public static String encryptData(String data, String key) throws CryptException {
        byte[] bdata = encryptData_asBytes(data, key);
        String encryptedData = ConversionUtils.bytesToHex(bdata);

        return encryptedData;
    }// encryptData

    /**
     * Encrypt DATA
     * 
     * @param data
     * @param key
     * @return byte[]
     * @throws CryptException
     */
    public static byte[] encryptData_asBytes(String data, String key) throws CryptException {

        byte[] bdata = StringUtils.getBytes(data, DEFAULT_ENCODING);
        byte[] bkey = StringUtils.getBytes(key, DEFAULT_ENCODING);

        byte[] encryptedData = encryptData_asBytes(bdata, bkey);

        return encryptedData;
    }// encryptData_asBytes


    /**
     * Encrypt DATA
     * 
     * @param bdata
     * @param key
     * @return byte[]
     * @throws CryptException
     */
    public static byte[] encryptData_asBytes(byte[] bdata, String key) throws CryptException {

        byte[] bkey = StringUtils.getBytes(key, DEFAULT_ENCODING);

        byte[] encryptedData = encryptData_asBytes(bdata, bkey);

        return encryptedData;
    }// encryptData_asBytes

    /**
     * Encrypt DATA
     * 
     * @param bdata
     * @param bkey
     * @return
     * @throws CryptException
     */
    public static byte[] encryptData_asBytes(byte[] bdata, byte[] bkey) throws CryptException {
        byte[] encryptedData = {};
        try {

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey k = getSecretKey(bkey);
            cipher.init(Cipher.ENCRYPT_MODE, k);

            encryptedData = cipher.doFinal(bdata);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException ex) {
            SystemLog.writeUtilsException(ex);
            throw new CryptException(ex);
        }
        return encryptedData;
    }// encryptData_asBytes



    /**
     * Decrypt DATA
     * 
     * @param data
     * @param key
     * @return String
     * @throws CryptException
     */
    public static String decryptData(String data, String key) throws CryptException {
        String str = null;

        try {
            byte[] bdata = ConversionUtils.hexToBytes(data);
            byte[] bkey = StringUtils.getBytes(key, DEFAULT_ENCODING);

            byte[] decryptedData = decryptData_asBytes(bdata, bkey);
            str = new String(decryptedData, DEFAULT_ENCODING);

        } catch (CryptException ex) {
            throw new CryptException(ex);
        } catch (UnsupportedEncodingException ex) {
            SystemLog.writeUtilsException(ex);
            throw new CryptException(ex);
        }
        return str;
    }// decryptData_asBytes

    /**
     * Decrypt DATA
     * 
     * @param bdata
     * @param key
     * @return String
     * @throws CryptException
     */
    public static String decryptData(byte[] bdata, String key) throws CryptException {
        String str = null;
        try {
            byte[] bkey = StringUtils.getBytes(key, DEFAULT_ENCODING);

            byte[] decryptedData = decryptData_asBytes(bdata, bkey);
            str = new String(decryptedData, DEFAULT_ENCODING);

        } catch (CryptException ex) {
            throw new CryptException(ex);
        } catch (UnsupportedEncodingException ex) {
            SystemLog.writeUtilsException(ex);
            throw new CryptException(ex);
        }
        return str;
    }// decryptData_asBytes


    /**
     * Decrypt DATA
     * 
     * @param bdata
     * @param bkey
     * @return
     * @throws CryptException
     */
    public static byte[] decryptData_asBytes(byte[] bdata, byte[] bkey) throws CryptException {
        byte[] decryptedData = {};

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey k = getSecretKey(bkey);

            cipher.init(Cipher.DECRYPT_MODE, k);
            decryptedData = cipher.doFinal(bdata);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException ex) {
            SystemLog.writeUtilsException(ex);
            throw new CryptException(ex);
        }
        return decryptedData;
    }// decryptData_asBytes


    // </editor-fold>


    // ------------------------------------------------------
    // Encrypt & Decrypt File
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Encrypt & Decrypt File">

    /**
     * Encrypt File
     * 
     * @param fileName
     * @param key
     * @throws CryptException
     */
    public static void encryptFile(String fileName, String key) throws CryptException {
        encryptFile(fileName, fileName, key);
    }// encryptFile

    /**
     * Encrypt File
     * 
     * @param inputFile
     * @param outputFile
     * @param key
     * @throws CryptException
     */
    public static void encryptFile(String inputFile, String outputFile, String key) throws CryptException {
        encryptFile(new File(inputFile), new File(outputFile), key);
    }// encryptFile

    /**
     * Encrypt File
     * 
     * @param inputFile
     * @param outputFile
     * @param key
     * @throws CryptException
     */
    public static void encryptFile(File inputFile, File outputFile, String key) throws CryptException {
        byte[] encryptedData = {};
        FileInputStream inputStream = null;
        try {
            byte[] bkey = StringUtils.getBytes(key, DEFAULT_ENCODING);
            // byte[] bdata = {};

            // File
            inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            // Cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey k = getSecretKey(bkey);

            cipher.init(Cipher.ENCRYPT_MODE, k);
            encryptedData = cipher.doFinal(inputBytes);

            FileUtils.writeFile(outputFile, encryptedData);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException ex) {
            SystemLog.writeUtilsException(ex);
            throw new CryptException(ex);
        } catch (FileNotFoundException ex) {
            SystemLog.writeUtilsException(ex);
        } catch (IOException ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ex) {
                SystemLog.writeUtilsException(ex);
            }
        }
    }// encryptFile


    /**
     * Decrypt File
     * 
     * @param fileName
     * @param key
     * @throws CryptException
     */
    public static void decryptFile(String fileName, String key) throws CryptException {
        decryptFile(fileName, fileName, key);
    }// decryptFile

    /**
     * Decrypt File
     * 
     * @param inputFile
     * @param outputFile
     * @param key
     * @throws CryptException
     */
    public static void decryptFile(String inputFile, String outputFile, String key) throws CryptException {
        decryptFile(new File(inputFile), new File(outputFile), key);
    }// decryptFile

    /**
     * Decrypt File
     * 
     * @param inputFile
     * @param outputFile
     * @param key
     * @throws CryptException
     */
    public static void decryptFile(File inputFile, File outputFile, String key) throws CryptException {
        byte[] decryptedData = {};
        FileInputStream inputStream = null;

        try {
            byte[] bkey = StringUtils.getBytes(key, DEFAULT_ENCODING);

            // File
            inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            // Cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey k = getSecretKey(bkey);

            cipher.init(Cipher.DECRYPT_MODE, k);
            decryptedData = cipher.doFinal(inputBytes);

            FileUtils.writeFile(outputFile, decryptedData);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException ex) {
            SystemLog.writeUtilsException(ex);
            throw new CryptException(ex);
        } catch (FileNotFoundException ex) {
            SystemLog.writeUtilsException(ex);
        } catch (IOException ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ex) {
                SystemLog.writeUtilsException(ex);
            }
        }

    }// decryptFile

    /**
     * Read Encrypted File
     * 
     * @param fileName
     * @param key
     * @return
     * @throws CryptException
     */
    public static String readEncryptedFile(String fileName, String key) throws CryptException {
        return readEncryptedFile(new File(fileName), key);
    }// readEncryptedFile

    /**
     * Read Encrypted File
     * 
     * @param inputFile
     * @param key
     * @return
     * @throws CryptException
     */
    public static String readEncryptedFile(File inputFile, String key) throws CryptException {
        byte[] decryptedData = {};
        FileInputStream inputStream = null;
        String str = null;

        try {
            byte[] bkey = StringUtils.getBytes(key, DEFAULT_ENCODING);

            // File
            inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            // Cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey k = getSecretKey(bkey);

            cipher.init(Cipher.DECRYPT_MODE, k);
            decryptedData = cipher.doFinal(inputBytes);
            str = new String(decryptedData, DEFAULT_ENCODING);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException ex) {
            SystemLog.writeUtilsException(ex);
            throw new CryptException(ex);
        } catch (FileNotFoundException ex) {
            SystemLog.writeUtilsException(ex);
        } catch (IOException ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ex) {
                SystemLog.writeUtilsException(ex);
            }
        }

        return str;
    }// readEncryptedFile


    // </editor-fold>


    // ------------------------------------------------------
    // Encrypt & Decrypt Directory
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Encrypt & Decrypt File">

    /**
     * Encrypt Files in Directory
     * 
     * @param dirName
     * @param key
     * @throws CryptException
     */
    public static void encryptDirectory(String dirName, String key) throws CryptException {
        encryptDirectory(dirName, dirName, key);
    }// encryptDirectory

    /**
     * Encrypt Files in Directory
     * 
     * @param inputDir
     * @param outputDir
     * @param key
     * @throws CryptException
     */
    public static void encryptDirectory(String inputDir, String outputDir, String key) throws CryptException {
        encryptDirectory(new File(inputDir), new File(outputDir), key);
    }// encryptFile

    /**
     * Encrypt Files in Directory
     * 
     * @param inputDir
     * @param outputDir
     * @param key
     * @throws CryptException
     */
    public static void encryptDirectory(File inputDir, File outputDir, String key) throws CryptException {
        try {
            if (inputDir.isDirectory() && outputDir.isDirectory()) {
                FileUtils.createDirs(outputDir);

                for (File f : inputDir.listFiles()) {
                    try {
                        if (f.isFile()) {
                            // Output path
                            String output = outputDir + File.separator + f.getName();
                            output = FileNameUtils.normalizeFilePath(output);

                            encryptFile(f, new File(output), key);

                        } // is File
                    } catch (CryptException ex) {
                        // noLogEx
                    }
                } // fore
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
            throw new CryptException(ex);
        }
    }// encryptDirectory



    /**
     * Decrypt Files in Directory
     * 
     * @param dirName
     * @param key
     * @throws CryptException
     */
    public static void decryptDirectory(String dirName, String key) throws CryptException {
        decryptDirectory(dirName, dirName, key);
    }// decryptDirectory

    /**
     * Decrypt Files in Directory
     * 
     * @param inputDir
     * @param outputDir
     * @param key
     * @throws CryptException
     */
    public static void decryptDirectory(String inputDir, String outputDir, String key) throws CryptException {
        decryptDirectory(new File(inputDir), new File(outputDir), key);
    }// decryptFile

    /**
     * Decrypt Files in Directory
     * 
     * @param inputDir
     * @param outputDir
     * @param key
     * @throws CryptException
     */
    public static void decryptDirectory(File inputDir, File outputDir, String key) throws CryptException {
        try {
            if (inputDir.isDirectory() && outputDir.isDirectory()) {
                FileUtils.createDirs(outputDir);

                for (File f : inputDir.listFiles()) {
                    try {
                        if (f.isFile()) {
                            // Output path
                            String output = outputDir + File.separator + f.getName();
                            output = FileNameUtils.normalizeFilePath(output);

                            decryptFile(f, new File(output), key);

                        } // is File
                    } catch (CryptException ex) {
                        // noLogEx
                    }
                } // fore
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
            throw new CryptException(ex);
        }
    }// decryptDirectory


    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="secure">

    /**
     * Regresa una SecretKey
     * 
     * @param bkey
     * @return
     */
    protected static synchronized SecretKey getSecretKey(byte[] bkey) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bkey, "AES");
        return secretKeySpec;
    }// getSecretKey


    // </editor-fold>

}// class
