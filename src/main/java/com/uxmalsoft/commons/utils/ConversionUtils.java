/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * ConversionUtils.java
 * Conversion Utilities
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

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.uxmalsoft.commons.conf.Params;
import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Utilidades de Conversion
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class ConversionUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    public enum BytesUnit {

        KILOBYTES (1,"kB"),
        MEGABYTES (2,"MB"),
        GIGABYTES (3,"GB"),
        TERABYTES (4,"TB"),
        PETABYTES (5,"PB"),
        EXABYTES  (6,"EB"),
        ZETTABYTES(7,"ZB"),
        YOTTABYTES(8,"YB"),
        ;
        
        private final int pos;

        public int getPos() {
            return pos;
        }

        private final String unit;

        public String getUnit() {
            return unit;
        }

        private BytesUnit(int p, String u) {
            this.pos = p;
            this.unit = u;
        }
    }// BYTES_UNIT

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private ConversionUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Bytes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Bytes">

    /**
     * Obtiene el arreglo de Bytes de una cadena y regresa este arreglo
     * 
     * @param str
     * @return byte[]
     */
    public static byte[] getBytes_fromString(String str) {
        return getBytes_fromString(str, Params.DEFAULT_ENCODING);
    }// getBytes

    /**
     * Obtiene el arreglo de Bytes de una cadena y regresa este arreglo
     * 
     * @param str
     * @param encoding
     * @return byte[]
     */
    public static byte[] getBytes_fromString(String str, String encoding) {
        encoding = (isNotEmpty(encoding)) ? encoding : Params.DEFAULT_ENCODING;
        byte[] arr = {};
        try {
            arr = str.getBytes(encoding);
        } catch (UnsupportedEncodingException ex) {
            SystemLog.writeUtilsException(ex);
        }
        return arr;
    }// getBytes_fromString

    /**
     * Convierte cadena hexadecimal a arreglo de bytes
     * 
     * @param hex
     * @return byte[]
     */
    public static byte[] hexToBytes(String hex) {
        byte[] enBytes = new byte[hex.length() / 2];
        for (int i = 0; i < enBytes.length; i++) {
            int index = i * 2;
            String aux = hex.substring(index, index + 2);
            int v = Integer.parseInt(aux, 16);
            enBytes[i] = (byte) (v & 0xff);
        } // for
        return enBytes;
    }// hexToBytes

    /**
     * Convierte un arreglo de bytes a una cadena hexadecimal
     * 
     * @param data
     * @return String
     */
    public static String bytesToHex(byte[] data) {
        StringBuilder hexString = new StringBuilder();
        try {
            for (int i = 0; i < data.length; i++) {
                String hex = Integer.toHexString(0xff & data[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            } // for
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return hexString.toString();
    }// bytesToHex

    /**
     * Convierte bytes a otra unidad de almacenamiento
     * 
     * @param bytes - Bytes a convertir
     * @param unit - Unidad
     * @return Double
     */
    public static Double bytesIn(double bytes, BytesUnit unit) {
        Double res = bytes;

        try {
            for (int i = 0; i < unit.getPos(); i++)
                res = res / 1024;

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
            res = -1.0;
        }
        return res;
    }// bytesIn

    // </editor-fold>


    // ------------------------------------------------------
    // Numbers
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Numbers">

    /**
     * Convierte un numero hexadecimal a entero decimal
     * 
     * @param num String
     * @return int
     */
    public static int hexNumberToInt(String num) {
        return convertNumberFromBase(num, 16);
    }// hexNumberToInt

    /**
     * Convierte un numero octal a entero decimal
     * 
     * @param num String
     * @return int
     */
    public static int octalNumberToInt(String num) {
        return convertNumberFromBase(num, 8);
    }// octalNumberToInt


    /**
     * Convierte un numero binario a entero decimal
     * 
     * @param num String
     * @return int
     */
    public static int binaryNumberToInt(String num) {
        return convertNumberFromBase(num, 2);
    }// binaryNumberToInt


    /**
     * Convierte un entero decimal a numero hexadecimal
     * 
     * @param num int
     * @return String
     */
    public static String intToHexNumber(int num) {
        return convertNumberToBase(num, 16);
    }// intToHexNumber

    /**
     * Convierte un entero decimal a numero octal
     * 
     * @param num int
     * @return String
     */
    public static String intToOctalNumber(int num) {
        return convertNumberToBase(num, 8);
    }// intToOctalNumber

    /**
     * Convierte un entero decimal a numero binario
     * 
     * @param num int
     * @return String
     */
    public static String intToBinaryNumber(int num) {
        return convertNumberToBase(num, 2);
    }// intToBinaryNumber

    /**
     * Convierte un numero de base N(2,8,16,...) a decimal
     * 
     * @param num String numero en base N
     * @param base int base
     * @return int
     */
    public static int convertNumberFromBase(String num, int base) {
        Integer d = null;
        try {
            d = Integer.valueOf(num, base);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return d;
    }// convertNumberFromBase

    /**
     * Convierte un numero decimal a uno numero con base N(2,8,16,...)
     * 
     * @param num int Numero decimal
     * @param base int base
     * @return String
     */
    public static String convertNumberToBase(int num, int base) {
        String n = null;
        try {
            n = Integer.toString(num, base);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return n;
    }// convertNumberToBase

    // </editor-fold>


    // ------------------------------------------------------
    // Date
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Date">

    /**
     * Regresa un String con el formato de una Fecha
     * 
     * @param calendar Calendar
     * @param format String
     * @return String
     */
    public static String formatDate(Calendar calendar, String format) {
        String str = null;

        if (calendar != null)
            str = formatDate(calendar.getTime(), format);
        return str;
    }// formatDate

    /**
     * Regresa un String con el formato de una Fecha
     * 
     * @param date Date
     * @param format String
     * @return String
     */
    public static String formatDate(Date date, String format) {

        String str = null;
        try {
            if (date != null && isNotEmpty(format)) {
                DateFormat df = new SimpleDateFormat(format);
                str = df.format(date);
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return str;
    }// formatDate

    /**
     * Convierte una Cadena a una Fecha
     * 
     * @param str
     * @param format
     * @return Date
     */
    public static Date stringToDate(String str, String format) {
        Date date = null;
        try {
            if (isNotEmpty(str) && isNotEmpty(format)) {
                DateFormat formatter = new SimpleDateFormat(format);
                date = formatter.parse(str);
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return date;
    }// stringToDate

    // </editor-fold>


    // ------------------------------------------------------
    // Parse String
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Parse String">

    /**
     * Intenta convertir String a Boolean
     * 
     * @param str
     * @return Boolean
     */
    public static Boolean parseBoolean(String str) {
        Boolean res = null;
        try {
            if (isNotEmpty(str) && !str.equals("null"))
                res = Boolean.valueOf(str);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// parseBoolean


    /**
     * Intenta convertir String a Short
     * 
     * @param str
     * @return Short
     */
    public static Short parseShort(String str) {
        Short res = null;
        try {
            if (isNotEmpty(str) && !str.equals("null"))
                res = Short.valueOf(str);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// parseShort


    /**
     * Intenta convertir String a Integer
     * 
     * @param str
     * @return Integer
     */
    public static Integer parseInteger(String str) {
        Integer res = null;
        try {
            if (isNotEmpty(str) && !str.equals("null"))
                res = Integer.valueOf(str);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// parseInteger

    /**
     * Intenta convertir String a Long
     * 
     * @param str
     * @return Long
     */
    public static Long parseLong(String str) {
        Long res = null;
        try {
            if (isNotEmpty(str) && !str.equals("null"))
                res = Long.valueOf(str);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// parseLong

    /**
     * Intenta convertir String a Float
     * 
     * @param str
     * @return Float
     */
    public static Float parseFloat(String str) {
        Float res = null;
        try {
            if (isNotEmpty(str) && !str.equals("null"))
                res = Float.valueOf(str);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// parseFloat

    /**
     * Intenta convertir String a Double
     * 
     * @param str
     * @return Double
     */
    public static Double parseDouble(String str) {
        Double res = null;
        try {
            if (isNotEmpty(str) && !str.equals("null"))
                res = Double.valueOf(str);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// parseDouble

    // </editor-fold>


    // ------------------------------------------------------
    // JavaObject
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="JavaObject">

    /**
     * Convierte un Objeto a Boolean
     * 
     * @param obj
     * @return Boolean
     */
    public static Boolean asBoolean(Object obj) {
        Boolean res = null;
        try {
            res = Boolean.valueOf("" + obj);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// asBoolean

    /**
     * Convierte un Objeto a String
     * 
     * @param obj
     * @return String
     */
    public static String asString(Object obj) {
        String res = null;
        try {
            res = String.valueOf(obj);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// asString

    /**
     * Convierte un Objeto a Short
     * 
     * @param obj
     * @return Short
     */
    public static Short asShort(Object obj) {
        Short res = null;
        try {
            res = Short.valueOf("" + obj);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// asShort

    /**
     * Convierte un Objeto a Integer
     * 
     * @param obj
     * @return Integer
     */
    public static Integer asInteger(Object obj) {
        Integer res = null;
        try {
            res = Integer.valueOf("" + obj);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// asInteger

    /**
     * Convierte un Objeto a Long
     * 
     * @param obj
     * @return Long
     */
    public static Long asLong(Object obj) {
        Long res = null;
        try {
            res = Long.valueOf("" + obj);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;

    }// asLong

    /**
     * Convierte un Objeto a Float
     * 
     * @param obj
     * @return Float
     */
    public static Float asFloat(Object obj) {
        Float res = null;
        try {
            res = Float.valueOf("" + obj);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// asFloat

    /**
     * Convierte un Objeto a Double
     * 
     * @param obj
     * @return Double
     */
    public static Double asDouble(Object obj) {
        Double res = null;
        try {
            res = Double.valueOf("" + obj);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// asDouble

    /**
     * Convierte un Objeto a Date
     * 
     * @param obj
     * @return Date
     */
    public static Date asDate(Object obj) {
        Date res = null;
        try {
            String str = String.valueOf("" + obj);
            res = TimeUtils.stringToDate(str);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// asDate

    /**
     * Convierte un Objeto a Time
     * 
     * @param obj
     * @return Date
     */
    public static Date asTime(Object obj) {
        // @todo
        return null;
    }// asTime

    // </editor-fold>



    // ------------------------------------------------------
    // Binary
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Binary">

    /**
     * Convierte una Cadena Hexadecimal en un arreglo de Boolean
     * 71 -> f t t t f f f t
     * 
     * @param str
     * @param length tamaño esperado
     * @return Boolean[]
     */
    public static Boolean[] hexNumberToBooleanArray(String str, Integer length) {
        str = validateNull(str);
        Boolean[] arr = {};

        try {
            if (!str.isEmpty()) {
                Integer dec = hexNumberToInt(str);
                String bin = intToBinaryNumber(dec);

                arr = new Boolean[bin.length()];

                for (int i = 0; i < bin.length(); i++) {
                    arr[i] = ((bin.charAt(i)) == '1');
                } // fori
            }

            if (length != null && length > 0) {
                Object[] arr1 = CollectionUtils.normalizeArray_left(arr, length, false);
                arr = new Boolean[arr1.length];

                for (int i = 0; i < arr1.length; i++) {
                    Boolean b = (Boolean) arr1[i];
                    arr[i] = b;
                }
            }

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }

        return arr;
    }// hexNumberToBooleanArray


    /**
     * Convierte una arreglo de Boolean a Cadena Hexadecimal
     * t t t f f f t -> 71
     * 
     * @param arr
     * @param length tamaño esperado
     * @return String
     */
    public static String booleanArrayToHexNumber(Boolean[] arr, Integer length) {
        String res = null;

        try {
            StringBuilder sb = new StringBuilder();
            for (Boolean b : arr) {
                sb.append((b) ? "1" : "0");
            }

            Integer dec = binaryNumberToInt(sb.toString());
            res = intToHexNumber(dec);

            if (length != null && length > 0) {
                res = StringUtils.fillString(res, length, '0');
            }

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }

        return res;
    }// booleanArrayToHexNumber

    // </editor-fold>


    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
