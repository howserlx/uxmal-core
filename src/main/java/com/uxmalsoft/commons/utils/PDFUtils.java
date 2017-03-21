/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * PDFUtils.java
 * Utilidades de PDF
 *
 * Created on : 14/04/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References : pdfbox-1.8.9 (fontbox-1.8.9 ,jempbox-1.8.9)
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.utils;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.RandomAccess;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDCcitt;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.PDFTextStripper;

import com.uxmalsoft.commons.logging.SystemLog;
import com.uxmalsoft.commons.pdf.PDFParserItem;

/**
 * 
 * Utilidades de PDF
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class PDFUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private PDFUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Crea un PDF a partir de imagenes
     * 
     * @param fileName
     * @param images
     * @throws IOException
     * @throws COSVisitorException
     * 
     *         Hay que reparar tiff
     */
    public static void createPDF_fromImages(String fileName, List<String> images)
            throws IOException, COSVisitorException {
        try {
            PDDocument document = new PDDocument();
            FileUtils.createDirs(fileName);

            if (isNotEmpty(fileName) && images != null && !images.isEmpty()) {
                if (!fileName.endsWith(".pdf"))
                    fileName = fileName + ".pdf";

                for (String image : images) {
                    try {
                        InputStream in = new FileInputStream(image);
                        PDPage page = null;

                        // Size (rectangle)
                        if (image.toLowerCase().endsWith(".tif") ||
                                image.toLowerCase().endsWith(".tiff")) {
                            // Tiff no se le puede obtener width/height
                            page = new PDPage();

                        } else {
                            BufferedImage bimg = ImageIO.read(in);
                            float width = bimg.getWidth();
                            float height = bimg.getHeight();

                            page = new PDPage(new PDRectangle(width, height));
                        }

                        // New Page
                        document.addPage(page);

                        PDXObjectImage pdImage;
                        if (image.toLowerCase().endsWith(".jpg")) {
                            pdImage = new PDJpeg(document, new FileInputStream(image));

                        } else if (image.toLowerCase().endsWith(".tif") ||
                                image.toLowerCase().endsWith(".tiff")) {
                            // pdImage = CCITTFactory.createFromFile(document, new File(image));
                            RandomAccess ra = new RandomAccessFile(new File(image), "r");
                            pdImage = new PDCcitt(document, ra);

                        } else if (image.toLowerCase().endsWith(".gif") ||
                                image.toLowerCase().endsWith(".bmp") ||
                                image.toLowerCase().endsWith(".png")) {
                            BufferedImage bim = ImageIO.read(new File(image));
                            pdImage = new PDPixelMap(document, bim);
                        } else {
                            throw new IOException("Image type not supported: " + image);
                        }


                        PDPageContentStream contentStream = new PDPageContentStream(document, page);
                        contentStream.drawImage(pdImage, 0, 0);
                        contentStream.close();
                        in.close();
                    } catch (Exception ex) {
                        SystemLog.writeUtilsException(ex);
                    }
                } // fore

                document.save(fileName);
                document.close();
            } // if
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
    }// createPDF_fromImages



    /**
     * Convierte un PDF a archivos de Texto
     * 
     * @param pdfFile
     * @param txtFile
     */
    public static void pdfToTextFile(String pdfFile, String txtFile) {
        pdfToTextFile(pdfFile, txtFile, false, null);
    }// pdfToTextFile

    /**
     * Convierte un PDF a archivos de Texto
     * 
     * @param pdfFile
     * @param txtFile
     * @param dividePages
     * @param pageSeparator
     */
    public static void pdfToTextFile(String pdfFile, String txtFile, boolean dividePages, String pageSeparator) {
        try {
            List<String> list = pdfToText(pdfFile, pageSeparator);
            int page = 1;
            String ext = "." + FileNameUtils.getOnlyExtension(txtFile);
            txtFile = txtFile.replace(ext, "");

            if (dividePages) {
                for (String str : list) {
                    String fileName = txtFile + "_" + page + ext;

                    FileUtils.writeFile(fileName, str);
                    page++;
                } // fore
            } else {
                StringBuilder sb = new StringBuilder();
                for (String str : list) {
                    sb.append(str);
                }
                String fileName = txtFile + ext;
                FileUtils.writeFile(fileName, sb.toString());

            } // else

        } catch (Exception e) {
            SystemLog.writeUtilsException(e);
        }
    }// pdfToTextFile


    /**
     * Convierte un PDF a Lista de Strings (una por pagina)
     * 
     * @param pdfFile
     * @return List String one per page
     */
    public static List<String> pdfToText(String pdfFile) {
        return pdfToText(pdfFile, null);
    }// pdfToText

    /**
     * Convierte un PDF a Lista de Strings (una por pagina)
     * 
     * @param pdfFile
     * @param pageSeparator
     * @return
     */
    public static List<String> pdfToText(String pdfFile, String pageSeparator) {
        PDFParser parser;
        List<String> parsedText = new ArrayList<>();
        PDFTextStripper pdfStripper = null;

        File file = new File(pdfFile);

        if (!file.isFile()) {
            return null;
        }

        try {
            parser = new PDFParser(new FileInputStream(file));
        } catch (IOException e) {
            return null;
        }

        try {

            parser.parse();

            pdfStripper = new PDFTextStripper();

            PDDocument document = PDDocument.load(pdfFile);
            List<PDPage> list = document.getDocumentCatalog().getAllPages();

            int pageNumber = 1;
            for (PDPage page : list) {
                PDDocument pdDoc = null;
                try {
                    pdDoc = new PDDocument();
                    pdDoc.addPage(page);
                    String str = pdfStripper.getText(pdDoc);
                    if (isNotEmpty(pageSeparator))
                        str = str + "\n" + pageSeparator + "\n";

                    parsedText.add(str);

                } catch (Exception ex) {
                    SystemLog.writeUtilsException(ex);

                } finally {
                    pageNumber++;
                    try {
                        if (pdDoc != null)
                            pdDoc.close();
                    } catch (Exception e) {
                        SystemLog.writeUtilsException(e);
                    }
                } // finally

            } // fore

        } catch (Exception e) {
            SystemLog.writeUtilsException(e);
        }
        return parsedText;
    }// pdfToText

    // </editor-fold>


    // ------------------------------------------------------
    // Parser
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Parser">

    /**
     * Realiza un parser a un PDF para encontrar datos
     * 
     * @param pdfFile
     * @param list
     * @param depthSearch Busqueda normal, con caseSensitive=false y con accentSensitive=false
     * @param caseSensitive Sensible a mayusculas minusculas
     * @param accentSensitive Sensible a acentos
     * @param deleteWhiteSpaces Fusionar palabras
     * @param deleteWhiteRows Eliminar Renglones vacios
     */
    public static void parsePDF(String pdfFile, List<PDFParserItem> list, boolean depthSearch,
            boolean caseSensitive, boolean accentSensitive,
            boolean deleteWhiteSpaces, boolean deleteWhiteRows) {

        try {


        } catch (Exception ex) {
            SystemLog.writeException(ex);
        }

    }// parsePDF


    // </editor-fold>


    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
