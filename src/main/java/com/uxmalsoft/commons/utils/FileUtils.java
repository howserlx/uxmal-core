/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * FileUtils.java
 * Utilidades de Archivos
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.output.FileWriterWithEncoding;

import com.uxmalsoft.commons.conf.Params;
import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Utilidades de Archivos
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class FileUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private FileUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Crea los directorios de un path
     * 
     * @param path (normalizado)
     */
    public static void createDirs(String path) {
        if (isNotEmpty(path)) {
            createDirs(new File(path));
        }
    }// createDirs


    /**
     * Crea los directorios de un file
     * 
     * @param f
     */
    public static void createDirs(File f) {
        if (f != null) {
            if (f.isDirectory())
                f.mkdirs();
            else {
                f = f.getParentFile();
                if (f != null)
                    f.mkdirs();
            }
        }
    }// createDirs

    /**
     * Carga un archivo de Properties
     * 
     * @param pathPropFile
     * @return
     * @throws IOException
     */
    public static Properties loadPropertiesFile(String pathPropFile) throws IOException {
        Properties configFile = new Properties();
        FileInputStream iConfig = null;
        IOException exToThrow = null;

        try {
            iConfig = new FileInputStream(pathPropFile);
            configFile.load(iConfig);
        } catch (IOException ex) {
            exToThrow = ex;
        } finally {
            if (iConfig != null) {
                try {
                    iConfig.close();
                } catch (IOException ex) {
                    SystemLog.writeUtilsException(ex);
                }
            }
        }

        if (exToThrow != null) {
            throw exToThrow;
        }

        return configFile;
    }// loadPropertiesFile


    /**
     * Lee un archivo de texto y lo regresa en lista
     * 
     * @param fileName
     * @return
     */
    public static List<String> readFile_asList(String fileName) {

        return readFile_asList(fileName, Params.DEFAULT_CHARSET, -1);
    }// readFile_asList

    /**
     * Lee un archivo de texto y lo regresa en lista
     * 
     * @param fileName
     * @param charset
     * @return
     */
    public static List<String> readFile_asList(String fileName, Charset charset) {
        return readFile_asList(fileName, charset, -1);
    }// readFile_asList

    /**
     * Lee un archivo de texto y lo regresa en lista,indicando cuantas lineas regresa
     * 
     * @param fileName
     * @param lines
     * @return
     */
    public static List<String> readFile_asList(String fileName, int lines) {
        return readFile_asList(fileName, Params.DEFAULT_CHARSET, lines);
    }// readFile_asList


    /**
     * Lee un archivo de texto y lo regresa en lista, indicando cuantas lineas regresa
     * 
     * @param fileName
     * @param charset
     * @param lines
     * @return
     */
    public static List<String> readFile_asList(String fileName, Charset charset, int lines) {

        List<String> list = new ArrayList<String>();
        BufferedReader br = null;

        try {

            String sCurrentLine;
            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName), charset));


            while ((sCurrentLine = br.readLine()) != null) {
                if (isNotEmpty(sCurrentLine)) {
                    if (lines < 0 || list.size() < lines)
                        list.add(sCurrentLine);
                } // linea

            } // while

        } catch (IOException ex) {
            SystemLog.writeException(ex);
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                SystemLog.writeException(ex);
            }
        }
        return list;
    }// readFile_asList


    /**
     * Lee un archivo de texto y lo regresa en String
     * 
     * @param fileName
     * @return
     */
    public static String readFile_asString(String fileName) {

        return readFile_asString(fileName, Params.DEFAULT_CHARSET);
    }// readFile_asString


    /**
     * Lee un archivo de texto y lo regresa en String
     * 
     * @param fileName
     * @param charset
     * @return
     */
    public static String readFile_asString(String fileName, Charset charset) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;

        try {

            String sCurrentLine;
            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName), charset));


            while ((sCurrentLine = br.readLine()) != null) {
                if (isNotEmpty(sCurrentLine)) {
                    sb.append(sCurrentLine);
                    sb.append(System.getProperty("line.separator"));
                } // linea

            } // while

        } catch (IOException ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                SystemLog.writeUtilsException(ex);
            }
        }
        return sb.toString();
    }// readFile_asString


    /**
     * Escribe una cadena a un archivo
     * 
     * @param fileName
     * @param content
     */
    public static void writeFile(String fileName, String content) {
        writeFile(fileName, content, Params.DEFAULT_CHARSET, false);
    }// writeFile

    /**
     * Escribe una cadena a un archivo
     * 
     * @param fileName
     * @param content
     * @param charset
     */
    public static void writeFile(String fileName, String content, Charset charset) {
        writeFile(fileName, content, charset, false);
    }// writeFile


    /**
     * Escribe una cadena a un archivo
     * 
     * @param fileName
     * @param content
     * @param append
     */
    public static void writeFile(String fileName, String content, boolean append) {
        writeFile(fileName, content, Params.DEFAULT_CHARSET, append);
    }// writeFile


    /**
     * Escribe una cadena a un archivo
     * 
     * @param fileName
     * @param content
     * @param charset
     * @param append
     */
    public static void writeFile(String fileName, String content, Charset charset, boolean append) {
        BufferedWriter bw = null;
        try {

            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriterWithEncoding fw = new FileWriterWithEncoding(file.getAbsoluteFile(), charset, append);
            bw = new BufferedWriter(fw);

            bw.append(content);


        } catch (IOException ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException ex) {
                SystemLog.writeUtilsException(ex);
            }
        }
    }// writeFile

    /**
     * Escribe un byte[] a un archivo
     * 
     * @param fileName
     * @param content
     */
    public static void writeFile(String fileName, byte[] content) {
        writeFile(new File(fileName), content);
    }// writeFile

    /**
     * Escribe un byte[] a un archivo
     * 
     * @param file
     * @param content
     */
    public static void writeFile(File file, byte[] content) {
        FileOutputStream outputStream = null;
        try {

            outputStream = new FileOutputStream(file);
            outputStream.write(content);

        } catch (IOException ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException ex) {
                SystemLog.writeUtilsException(ex);
            }
        }
    }// writeFile


    /**
     * Construye un archivo CSV
     * 
     * @param fileName
     * @param list
     */
    public static void writeFile_asCSV(String fileName, List<List<String>> list) {
        writeFile_asCSV(fileName, list, Params.DEFAULT_CHARSET, false);
    }// writeFile_asCSV

    /**
     * Construye un archivo CSV
     * 
     * @param fileName
     * @param list
     * @param append
     */
    public static void writeFile_asCSV(String fileName, List<List<String>> list, boolean append) {
        writeFile_asCSV(fileName, list, Params.DEFAULT_CHARSET, append);
    }// writeFile_asCSV

    /**
     * Construye un archivo CSV
     * 
     * @param fileName
     * @param list
     * @param charset
     */
    public static void writeFile_asCSV(String fileName, List<List<String>> list, Charset charset) {
        writeFile_asCSV(fileName, list, charset, false);
    }// writeFile_asCSV


    /**
     * Construye un archivo CSV
     * 
     * @param fileName
     * @param list
     * @param charset
     * @param append
     */
    public static void writeFile_asCSV(String fileName, List<List<String>> list, Charset charset, boolean append) {
        BufferedWriter bw = null;
        try {

            if (!fileName.endsWith(".csv"))
                fileName = fileName + ".csv";

            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), append);
            bw = new BufferedWriter(fw);

            for (List<String> list1 : list) {
                int cont = 0;
                for (String str : list1) {
                    if (cont > 0)
                        bw.append(',');

                    bw.append(str.replace(",", ""));
                    cont++;
                }
                bw.append('\n');
            } // fore


        } catch (IOException ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException ex) {
                SystemLog.writeUtilsException(ex);
            }
        }
    }// writeFile_asCSV

    /**
     * Copia un archivo a destino desde in stream
     * 
     * @param fileName
     * @param in
     */
    public static void copyFile(String fileName, InputStream in) {

        OutputStream out = null;

        try {
            out = new FileOutputStream(new File(fileName));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (IOException ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Exception ex) {
                SystemLog.writeUtilsException(ex);
            }
        }
    }// copyFile

    /**
     * Copia un archivo desde src a dest
     * 
     * @param src
     * @param dest
     */
    public static boolean copyFile(String src, String dest) {
        boolean res = false;

        try {
            File srcFile = new File(src);
            File destFile = new File(dest);

            if (srcFile.exists()) {
                org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
                res = true;
            }

        } catch (IOException ex) {
            SystemLog.writeUtilsException(ex);
        } finally {

        }
        return res;
    }// copyFile

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
