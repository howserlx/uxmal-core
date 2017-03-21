/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * SpreadsheetCell.java
 * SpreadsheetCell
 *
 * Created on : 15/07/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.spreadsheet;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import com.uxmalsoft.commons.enums.JavaObject;
import com.uxmalsoft.commons.logging.SystemLog;
import com.uxmalsoft.commons.utils.SpreadsheetUtils;

/**
 * 
 * SpreadsheetCell
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class SpreadsheetCell {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    private int sheet;
    private String cellRef;
    private int row;
    private int column;

    private Object cellValue;
    private JavaObject valueType;

    private CellStyle cellStyle;
    private IndexedColors fontColor;
    private IndexedColors backgroundColor;
    private short align;

    private IndexedColors borderColor;
    private short borderType;

    // </editor-fold>


    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SpreadsheetCell() {
        this.sheet = -1;
        this.cellRef = "";
        this.row = -1;
        this.column = -1;
        this.cellValue = null;
        this.valueType = JavaObject.STRING;
        this.cellStyle = null;
        this.fontColor = null; // null para respetar colores originales
        this.backgroundColor = null; // null para respetar colores originales
        this.borderColor = null; // null para respetar colores originales
        this.borderType = CellStyle.BORDER_NONE;

        this.align = CellStyle.ALIGN_LEFT;
    }// empty

    public SpreadsheetCell(int sheet, int row, int column, Object cellValue, JavaObject valueType) {
        this();
        this.sheet = sheet;
        this.row = row;
        this.column = column;
        this.cellValue = cellValue;
        this.valueType = valueType;
    }// constructor


    public SpreadsheetCell(int sheet, String cellRef, Object cellValue, JavaObject valueType) {
        this();
        cellRef = validateNull(cellRef).trim().toUpperCase();

        this.sheet = sheet;
        this.cellRef = cellRef;
        this.cellValue = cellValue;
        this.valueType = valueType;
    }// constructor


    // </editor-fold>


    // ------------------------------------------------------
    // Getters & Setters
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public int getSheet() {
        return sheet;
    }

    public void setSheet(int sheet) {
        this.sheet = sheet;
    }

    public String getCellRef() {
        return cellRef;
    }

    public void setCellRef(String cellRef) {
        this.cellRef = cellRef;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Object getCellValue() {
        return cellValue;
    }

    public void setCellValue(Object cellValue) {
        this.cellValue = cellValue;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public JavaObject getValueType() {
        return valueType;
    }

    public void setValueType(JavaObject valueType) {
        this.valueType = valueType;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public IndexedColors getFontColor() {
        return fontColor;
    }

    public void setFontColor(IndexedColors fontColor) {
        this.fontColor = fontColor;
    }

    public IndexedColors getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(IndexedColors backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public short getAlign() {
        return align;
    }

    public void setAlign(short align) {
        this.align = align;
    }

    public IndexedColors getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(IndexedColors borderColor) {
        this.borderColor = borderColor;
    }

    public short getBorderType() {
        return borderType;
    }

    public void setBorderType(short borderType) {
        this.borderType = borderType;
    }


    // </editor-fold>


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------

    /**
     * Coloca los parametros de estilo
     * 
     * @param fontColor
     * @param backgroundColor
     * @param borderColor
     * @param borderType
     * @param align
     */
    public void setStyleParams(IndexedColors fontColor, IndexedColors backgroundColor, IndexedColors borderColor,
            short borderType, short align) {

        if (fontColor != null)
            this.fontColor = fontColor;

        if (backgroundColor != null)
            this.backgroundColor = backgroundColor;

        if (borderColor != null)
            this.borderColor = borderColor; // null para respetar colores originales

        if (borderType >= 0)
            this.borderType = borderType;

        if (align >= 0)
            this.align = align;

    }// setStyleParams



    /**
     * Construye CellStyle
     * 
     * @param wb
     * @return
     */
    public CellStyle buildCellStyle(Workbook wb) {
        CellStyle cs = null;
        boolean change = false;
        try {
            Cell c = SpreadsheetUtils.findCellByReference(wb, this);
            cs = wb.createCellStyle();
            cs.cloneStyleFrom(c.getCellStyle());
            cs.setAlignment(align);

            if (fontColor != null) {
                short fontIdx = cs.getFontIndex();
                Font font = SpreadsheetUtils.cloneFont(wb, wb.getFontAt(fontIdx));
                font.setColor(fontColor.getIndex());
                cs.setFont(font);
                change = true;
            }

            if (backgroundColor != null) {
                cs.setFillForegroundColor(backgroundColor.getIndex());
                cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
                change = true;
            }

            if (borderType != CellStyle.BORDER_NONE && borderColor != null) {
                Sheet s = wb.getSheetAt(sheet);

                CellRangeAddress range = SpreadsheetUtils.findMergedRange(s, c);
                if (range != null) {
                    RegionUtil.setBorderBottom(borderType, range, s, wb);
                    RegionUtil.setBorderTop(borderType, range, s, wb);
                    RegionUtil.setBorderLeft(borderType, range, s, wb);
                    RegionUtil.setBorderRight(borderType, range, s, wb);

                    RegionUtil.setBottomBorderColor(borderColor.getIndex(), range, s, wb);
                    RegionUtil.setTopBorderColor(borderColor.getIndex(), range, s, wb);
                    RegionUtil.setLeftBorderColor(borderColor.getIndex(), range, s, wb);
                    RegionUtil.setRightBorderColor(borderColor.getIndex(), range, s, wb);
                }
                
                cs.setBorderLeft(borderType);
                cs.setBorderRight(borderType);
                cs.setBorderTop(borderType);
                cs.setBorderBottom(borderType);

                cs.setLeftBorderColor(borderColor.getIndex());
                cs.setRightBorderColor(borderColor.getIndex());
                cs.setTopBorderColor(borderColor.getIndex());
                cs.setBottomBorderColor(borderColor.getIndex());
            }


        } catch (Exception ex) {
            SystemLog.writeException(ex);
        }
        return (change) ? cs : null;
    }// buildCellStyle



    // </editor-fold>


    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
