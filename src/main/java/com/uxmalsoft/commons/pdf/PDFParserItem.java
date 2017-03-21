/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * PDFParserItem.java
 * PDFParserItem
 *
 * Created on : 4/08/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.pdf;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.util.ArrayList;
import java.util.List;

import com.uxmalsoft.commons.logging.SystemLog;
import com.uxmalsoft.commons.utils.ConversionUtils;

/**
 * 
 * PDFParserItem
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */

public class PDFParserItem {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">
    private static final String FILE_TYPE = "PDF_PARSER";
    private static final int FILE_COLUMNS = 12;

    public enum ItemType {
        TEXT, NUMERIC, ALPHA, ALPHANUM, DIGIT, DATE, TIME;

        
        public static ItemType findByName(String searchName) {
            for (ItemType item : ItemType.values()) {
                if (item.name().equals(searchName))
                    return item;
            }
            return TEXT;
        }// findByName
    }// enum


    public enum ItemRange {
        NORMAL, WORD;

        public static ItemRange findByName(String searchName) {
            for (ItemRange item : ItemRange.values()) {
                if (item.name().equals(searchName))
                    return item;
            }
            return NORMAL;
        }// findByName
    }// enum


    public enum ParseResult {
        NOT_PARSED,         // No ha iniciado la busqueda
        FOUND,              // Se encontraron datos
        POSSIBLE_FOUND,     // Se encontraron posiblemente los datos (no.serie en vez de No. Serie)
        FOUND_AND_POSSIBLE, // Se encontraron datos y otros datos posibles (no.serie en vez de No. Serie)
        NOT_FOUND,          // No se encontraron datos
        PARSE_ERROR;        // Error en Parser

    }// enum


    // Procesamiento
    private String    action;   // accion a realizar al encontrar la posicion
    private ItemRange range;    // leer por caracter, palabra por palabra
    private ItemType  objType;  // tipo de objeto
    private String    length;   // longitud aproximada
    private Integer   numOc;    // numero maximo de ocurrencias

    // Find
    // n+ , n-, n, n-m, first, last
    private String page;   // pagina aproximada
    private String row;    // renglon aproximado
    private String column; // columna o palabra de renglon aproximada

    // (r|p):regex:position
    private String  beforeKey; // palabra o regex anterior al objeto buscado
    private String  afterKey;  // palabra o regex posterior al objeto buscado
    private Boolean regex;     // las llaves representan una regex


    // Resultado
    private List<String> listFound;
    private List<String> listPossible;
    private ParseResult  result;

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public PDFParserItem() {
        listFound = new ArrayList<>();
        listPossible = new ArrayList<>();
        result = ParseResult.NOT_PARSED;
    }// empty

    public PDFParserItem(String action, ItemRange range, ItemType objType, String length, Integer numOc, String page,
            String row, String column, String beforeKey, String afterKey, Boolean regex) {
        this();
        this.action    = action;
        this.range     = range;
        this.objType   = objType;
        this.length    = length;
        this.numOc     = numOc;
        this.page      = page;
        this.row       = row;
        this.column    = column;
        this.beforeKey = beforeKey;
        this.afterKey  = afterKey;
        this.regex     = regex;
    }// constructor

    // </editor-fold>


    // ------------------------------------------------------
    // Getters & Setters
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ItemRange getRange() {
        return range;
    }

    public void setRange(ItemRange range) {
        this.range = range;
    }

    public ItemType getObjType() {
        return objType;
    }

    public void setObjType(ItemType objType) {
        this.objType = objType;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Integer getNumOc() {
        return numOc;
    }

    public void setNumOc(Integer numOc) {
        this.numOc = numOc;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getBeforeKey() {
        return beforeKey;
    }

    public void setBeforeKey(String beforeKey) {
        this.beforeKey = beforeKey;
    }

    public String getAfterKey() {
        return afterKey;
    }

    public void setAfterKey(String afterKey) {
        this.afterKey = afterKey;
    }

    public Boolean getRegex() {
        return regex;
    }

    public void setRegex(Boolean regex) {
        this.regex = regex;
    }

    public List<String> getListFound() {
        return listFound;
    }

    public void setListFound(List<String> listFound) {
        this.listFound = listFound;
    }

    public List<String> getListPossible() {
        return listPossible;
    }

    public void setListPossible(List<String> listPossible) {
        this.listPossible = listPossible;
    }

    public ParseResult getResult() {
        return result;
    }

    public void setResult(ParseResult result) {
        this.result = result;
    }

    // </editor-fold>


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">
    /**
     * Construye una lista de items desde un Archivo
     * 
     * @param fileType
     * @param data
     * @return
     */
    public static List<PDFParserItem> buildList_fromFile(String fileType, List<String[]> data) {
        List<PDFParserItem> list = new ArrayList<>();
        try {
            if (fileType.equals(FILE_TYPE)) {

                for (String[] arr : data) {
                    if (arr.length == FILE_COLUMNS) {

                        String action    = arr[0];
                        ItemRange range  = ItemRange.findByName(arr[1]);
                        ItemType objType = ItemType.findByName(arr[2]);
                        String length    = arr[3];
                        Integer numOc    = ConversionUtils.asInteger(arr[4]);

                        String page   = arr[5];
                        String row    = arr[6];
                        String column = arr[7];

                        String beforeKey = arr[8];
                        String afterKey  = arr[9];
                        Boolean regex    = ConversionUtils.asBoolean(arr[10]);

                        PDFParserItem item = new PDFParserItem(action, range, objType, length, numOc, page, row, column,
                                beforeKey, afterKey, regex);
                        list.add(item);

                    } // valid columns
                } // fore

            } // valid file
        } catch (Exception ex) {
            SystemLog.writeException(ex);
        }
        return list;
    } // buildList_fromFile


    // </editor-fold>


    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">
    // </editor-fold>

}// class
