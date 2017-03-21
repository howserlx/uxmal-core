/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * SpreadsheetUtils.java
 * Spreadsheet Utilities
 *
 * Created on : 14/07/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References : poi-3.12, poi-ooxml-3.12, poi-ooxml-schemas-3.12,xmlbeans-2.6.0
 * stax-api-1.0.1
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.utils;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import com.uxmalsoft.commons.logging.SystemLog;
import com.uxmalsoft.commons.spreadsheet.SpreadsheetCell;

/**
 * 
 * Spreadsheet Utils
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class SpreadsheetUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private SpreadsheetUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Abre un Spreadsheet
     * 
     * @param file
     * @return
     */
    public static Workbook openSpreadsheet(File file) {
        Workbook wb = null;
        FileInputStream inputStream = null;
        try {
            if (file != null && file.exists()) {
                inputStream = new FileInputStream(file);
                wb = WorkbookFactory.create(inputStream);
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception ex) {
                SystemLog.writeUtilsException(ex);
            }
        }

        return wb;
    }// openSpreadsheet

    /**
     * Guarda un Spreadsheet
     * 
     * @param wb
     * @param file
     */
    public static void saveSpreadsheet(Workbook wb, File file) {
        FileOutputStream outputStream = null;
        try {
            if (file != null && wb != null) {

                // Guarda
                outputStream = new FileOutputStream(file);
                wb.write(outputStream);
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception ex) {
                SystemLog.writeUtilsException(ex);
            }

        }
    }// saveSpreadsheetUtils


    /**
     * Lee los datos de un spreadsheet
     * 
     * @param file
     * @param sheet
     * @param firstColumn
     * @param lastColumn
     * @param validateColumn Columna a verificar (no vacia) para que la row se inserte en los datos
     * @return
     */
    public static List<Object[]> readData_fromCells(File file, Integer sheet, Integer firstColumn, Integer lastColumn,
            Integer validateColumn) {
        return readData_fromCells(file, sheet, firstColumn, lastColumn, -1, -1, validateColumn);
    }// readData_fromCells

    /**
     * Lee los datos de un spreadsheet
     * 
     * @param file
     * @param sheet
     * @param firstColumn
     * @param lastColumn
     * @param firstRow
     * @param lastRow
     * @param validateColumn Columna a verificar (no vacia) para que la row se inserte en los datos
     * @return List<Object[]>
     */
    public static List<Object[]> readData_fromCells(File file, Integer sheet, Integer firstColumn, Integer lastColumn,
            Integer firstRow, Integer lastRow, Integer validateColumn) {
        List<Object[]> list = new ArrayList<>();

        try {
            Workbook wb = openSpreadsheet(file);
            Sheet sh = wb.getSheetAt(sheet);

            list = readData_fromCells(sh, firstColumn, lastColumn, firstRow, lastRow, validateColumn);

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }

        return list;
    }// readData


    /**
     * Lee los datos de una sheet
     * 
     * @param sheet
     * @param firstColumn
     * @param lastColumn
     * @param validateColumn Columna a verificar (no vacia) para que la row se inserte en los datos
     * @return
     */
    public static List<Object[]> readData_fromCells(Sheet sheet, Integer firstColumn, Integer lastColumn,
            Integer validateColumn) {
        return readData_fromCells(sheet, firstColumn, lastColumn, -1, -1, validateColumn);
    }// readData_fromCells

    /**
     * Lee los datos de una sheet
     * 
     * @param sheet
     * @param firstColumn columna inicial
     * @param lastColumn columna final
     * @param firstRow -1 indica no fila inicial
     * @param lastRow -1 indica no fila final
     * @param validateColumn Columna a verificar (no vacia) para que la row se inserte en los datos
     * @return
     */
    public static List<Object[]> readData_fromCells(Sheet sheet, Integer firstColumn, Integer lastColumn,
            Integer firstRow, Integer lastRow, Integer validateColumn) {
        List<Object[]> list = new ArrayList<>();
        firstColumn = (firstColumn == null || firstColumn < 0) ? 0 : firstColumn;
        lastColumn  = (lastColumn  == null || lastColumn  < 0) ? 0 : lastColumn;

        if (validateColumn == null)
            validateColumn = -1;

        try {
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                try {
                    Row row = (Row) rows.next();

                    if (includeRow(row, firstRow, lastRow)) {
                        // Iterator cells = row.cellIterator();

                        List data = new ArrayList();
                        boolean excludeRow = false;

                        for (int i = firstColumn; i < lastColumn; i++) {
                            Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
                            if (cell == null) {
                                if (validateColumn.equals(i)) {
                                    excludeRow = true;
                                }
                            }

                            data.add(getCellValue(cell));
                        } // for

                        if (!excludeRow) {
                            list.add(data.toArray());
                        }

                    } // includeRow
                } catch (Exception ex) {
                    SystemLog.writeException(ex);
                }
            } // while row

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }

        return list;
    }// readData


    /**
     * Una row esta en rango
     * 
     * @param row
     * @param first
     * @param last
     * @return
     */
    private static boolean includeRow(Row row, Integer first, Integer last) {
        if (first == null)
            first = -1;
        if (last == null)
            last = -1;


        boolean great = false;
        boolean less = false;

        if (row != null) {

            great = (first < 0 || row.getRowNum() >= first);
            less = (last < 0 || row.getRowNum() <= last);

        }
        return great && less;
    }// includeRow


    /**
     * Una columna esta en rango
     * 
     * @param row
     * @param first
     * @param last
     * @return
     */
    private static boolean includeColumn(Cell cell, Integer first, Integer last) {
        if (first == null)
            first = -1;
        if (last == null)
            last = -1;


        boolean great = false;
        boolean less = false;

        if (cell != null) {

            great = (first < 0 || cell.getColumnIndex() >= first);
            less  = (last  < 0 || cell.getColumnIndex() <= last);

        }
        return great && less;
    }// includeColumn

    /**
     * Actualiza los valores de las Celdas
     * 
     * @param wb
     * @param list
     * @param changeStyle
     */
    public static void updateCellValues(Workbook wb, List<SpreadsheetCell> list, boolean changeStyle) {
        try {
            if (wb != null && list != null) {

                for (SpreadsheetCell item : list) {
                    try {
                        int indexSheet = item.getSheet();

                        if (indexSheet >= 0 && indexSheet < wb.getNumberOfSheets()) {
                            Cell cell = findCellByReference(wb, item);
                            updateCellValue(wb, cell, item, changeStyle);
                        } // sheet exists

                    } catch (Exception ex) {
                        SystemLog.writeUtilsException(ex);
                    }
                } // fore

            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
    }// updateCellValues


    /**
     * Procesa el valor de una sola celda
     * 
     * @param wb
     * @param cell
     * @param item
     * @param changeStyle
     */
    public static void updateCellValue(Workbook wb, Cell cell, SpreadsheetCell item, boolean changeStyle) {
        try {
            switch (item.getValueType()) {
                case BOOLEAN:
                    Boolean b = ConversionUtils.asBoolean(item.getCellValue());
                    cell.setCellValue(b);
                    break;

                case STRING:
                    String s = ConversionUtils.asString(item.getCellValue());
                    cell.setCellValue(s);
                    break;

                case SHORT:
                case INTEGER:
                case LONG:
                case FLOAT:
                case DOUBLE:
                    Double n = ConversionUtils.asDouble(item.getCellValue());
                    cell.setCellValue(n);
                    break;

                case DATE:
                    Date d = ConversionUtils.asDate(item.getCellValue());
                    cell.setCellValue(d);
                    break;

                case TIME:
                    break;
                default:

            }// switch

            if (changeStyle) {
                if (item.getCellStyle() != null) {
                    cell.setCellStyle(item.getCellStyle());
                } else {
                    CellStyle cs = item.buildCellStyle(wb);
                    if (cs != null)
                        cell.setCellStyle(cs);
                }

            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }

    }// updateCellValue



    /**
     * Obtiene el Valor de una Celda
     * 
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object cellValue = null;

        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;

                case Cell.CELL_TYPE_FORMULA:
                    cellValue = cell.getCellFormula();
                    break;

                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue().toString();
                    } else {
                        cellValue = Double.toString(cell.getNumericCellValue());
                    }
                    break;

                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;

                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = Boolean.toString(cell.getBooleanCellValue());
                    break;

            }// switch
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return cellValue;
    }// getCellValue



    /**
     * Inserta una imagen a un Spreadsheet
     * 
     * @param wb
     * @param image
     * @param cell
     * @param width numberOfColumns
     * @param height numberOfRows
     */
    public static void insertImage(Workbook wb, File image, SpreadsheetCell cell, int width, int height) {
        InputStream inputStream = null;
        try {
            if (wb != null && cell != null) {
                Sheet sheet = wb.getSheetAt(cell.getSheet());

                inputStream = new FileInputStream(image);
                // Get the contents of an InputStream as a byte[].
                byte[] bytes = IOUtils.toByteArray(inputStream);

                // Adds a picture to the workbook
                int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);


                // Returns an object that handles instantiating concrete classes
                CreationHelper helper = wb.getCreationHelper();

                // Creates the top-level drawing patriarch.
                Drawing drawing = sheet.createDrawingPatriarch();

                // Create an anchor that is attached to the worksheet
                ClientAnchor anchor = helper.createClientAnchor();

                anchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE);

                // set top-left corner for the image
                SpreadsheetUtils.findCellByReference(wb, cell);
                anchor.setCol1(cell.getColumn());
                anchor.setRow1(cell.getRow());

                if (width > 0)
                    anchor.setCol2(cell.getColumn() + width);

                if (height > 0)
                    anchor.setRow2(cell.getRow() + height);

                // anchor.setCol2(pictureIdx);

                // Creates a picture
                Picture pict = drawing.createPicture(anchor, pictureIdx);

            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception ex) {
                SystemLog.writeUtilsException(ex);
            }
        }
    }// insertImage


    /**
     * Encuentra la region combinada de un Cell
     * 
     * @param s
     * @param cell
     * @return
     */
    public static CellRangeAddress findMergedRange(Sheet s, Cell cell) {
        int noOfMR = s.getNumMergedRegions();

        for (int i = 0; i < noOfMR; ++i) {
            CellRangeAddress mergeRange = s.getMergedRegion(i);
            if (mergeRange.isInRange(cell.getRowIndex(), cell.getColumnIndex())) {
                return mergeRange;
            }
        } // for
        return null;
    }// findMergedRange


    /**
     * Encuentra una Cell por su referencia
     * recalcula row and column
     * 
     * @param wb
     * @param cell
     * @return
     */
    public static Cell findCellByReference(Workbook wb, SpreadsheetCell cell) {
        Cell c = null;

        try {
            Sheet s = wb.getSheetAt(cell.getSheet());

            CellReference ref = new CellReference(cell.getCellRef());
            Row r = s.getRow(ref.getRow());
            if (r != null) {
                c = r.getCell(ref.getCol());
                cell.setRow(c.getRowIndex());
                cell.setColumn(c.getColumnIndex());
            }

        } catch (Exception ex) {
            SystemLog.writeException(ex);
        }

        return c;
    }// findCellByReference


    /**
     * Clona una Font
     * 
     * @param wb
     * @param font
     * @return
     */
    public static Font cloneFont(Workbook wb, Font font) {
        Font clone = null;
        try {
            clone = wb.createFont();

            if (font != null) {
                clone.setFontHeightInPoints(font.getFontHeightInPoints());
                clone.setFontName(font.getFontName());
                clone.setColor(font.getColor());
                clone.setBoldweight(font.getBoldweight());
            }
        } catch (Exception ex) {
            SystemLog.writeException(ex);
        }
        return clone;
    }// cloneFont


    // </editor-fold>



    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
