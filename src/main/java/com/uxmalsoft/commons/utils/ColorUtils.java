/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * ColorUtils.java
 * Color Utilities
 *
 * Created on : 20/07/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References : references
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.utils;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Utilidades de Color
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */

public class ColorUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private ColorUtils() {

    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Valida y normaliza la cadena recibida
     * 
     * @param str
     * @return
     */
    public static String normalizeColorString(String str) {
        return normalizeColorString(str, true);
    }// normalizeColorString

    /**
     * Valida y normaliza la cadena recibida
     * 
     * @param str
     * @param includePrefix
     * @return
     */
    public static String normalizeColorString(String str, boolean includePrefix) {
        String validated = "";
        char[] divide = new char[3];
        char[] aux = new char[6];
        boolean band = false;

        // validamos si la cadena contiene "#"
        if (str.contains("#"))
            str = str.substring(1);

        // validamos la cadena par caracteres no aceptados
        aux = str.toCharArray();
        for (char c : aux) {
            if ((c >= 48 && c <= 57) || (c >= 65 && c <= 70) || (c >= 97 && c <= 102))// busca caracteres invalidos
                band = true;
            else
                return null;
        }
        // normalizamos la cadena
        switch (str.length()) {
            case 1:
                str.getChars(0, 1, divide, 0);
                for (int i = 0; i < 6; i++)
                    validated += divide[0] + "";
                break;

            case 2:
                str.getChars(0, 1, divide, 0);
                str.getChars(1, 2, divide, 1);
                for (int i = 0; i < 3; i++)
                    validated += divide[0] + "";
                for (int i = 0; i < 3; i++)
                    validated += divide[1] + "";
                break;

            case 3:
                str.getChars(0, 1, divide, 0);
                str.getChars(1, 2, divide, 1);
                str.getChars(2, 3, divide, 2);
                for (int i = 0; i < 2; i++)
                    validated += divide[0] + "";
                for (int i = 0; i < 2; i++)
                    validated += divide[1] + "";
                for (int i = 0; i < 2; i++)
                    validated += divide[2] + "";
                break;

            case 6:
                validated = str;
                break;

            default:
                if (str.length() > 6) {
                    validated = StringUtils.fillString(str, 6, '0');
                } else
                    validated = null;
                break;
        }

        if (validated != null)
            validated = (includePrefix) ? "#" : "" + validated;

        return validated;
    }// normalizeColorString

    /**
     * Recibe un Color y lo convierte en su representacion de color en hexadecimal
     * 
     * @param color
     * @return
     */
    public static String colorToHex(Color color) {
        return colorToHex(color, true);
    }// colorToHex

    /**
     * Recibe un Color y lo convierte en su representacion de color en hexadecimal
     * 
     * @param color
     * @param includePrefix
     * @return
     */
    public static String colorToHex(Color color, boolean includePrefix) {
        String colorHexa = "";
        String red = "";
        String green = "";
        String blue = "";
        int intRed = 0;
        int intGreen = 0;
        int intBlue = 0;

        // obtenemos los valores enteros RGB
        intRed = color.getRed();
        intGreen = color.getGreen();
        intBlue = color.getBlue();
        // convertimos los valores enteros a hexadecimal
        red = Integer.toHexString((int) intRed);
        if (red.equals("0"))
            red = "00";
        green = Integer.toHexString((int) intGreen);
        if (green.equals("0"))
            green = "00";
        blue = Integer.toHexString((int) intBlue);
        if (blue.equals("0"))
            blue = "00";

        colorHexa = (includePrefix) ? "#" : "" + red + green + blue;

        return colorHexa;
    }// colorToHex

    /**
     * Convierte una cadena hexadecimal a un Color
     * 
     * @param str
     * @param defaultColor
     * @return
     */
    public static Color hexToColor(String str, Color defaultColor) {
        Color c = defaultColor;

        try {
            if (isNotEmpty(str)) {
                int[] cc = getColorComponents(str);
                c = new Color(cc[0], cc[1], cc[2]);
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
            c = defaultColor;
        }
        return c;
    }// hexToColor

    /**
     * Obtiene los componentes rgb de un Color en hexadecimal
     * 
     * @param str
     * @return
     */
    public static int[] getColorComponents(String str) {
        int cc[] = {-1, -1, -1};
        try {
            if (isNotEmpty(str)) {
                str = normalizeColorString(str, false);
                if (str.length() == 6) {
                    cc[0] = ConversionUtils.hexNumberToInt(str.substring(0, 2));
                    cc[1] = ConversionUtils.hexNumberToInt(str.substring(2, 4));
                    cc[2] = ConversionUtils.hexNumberToInt(str.substring(4));
                }

            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return cc;
    }// getColorComponents


    /**
     * Genera un Color mas claro o mas oscuro segun el porcentaje recibido
     * 
     * @param color
     * @param percent
     * @return
     */
    public static String darkerOrBrighter(String color, float percent) {
        return darkerOrBrighter(color, percent, 'n');
    }// darkerOrBrighter

    /**
     * Genera un Color mas claro o mas oscuro segun el porcentaje recibido
     * 
     * @param color
     * @param percent
     * @param c, 'a' para aclarar y 'o' para oscurecer
     * @return
     */
    public static String darkerOrBrighter(String color, float percent, char c) {
        String red = "";
        String green = "";
        String blue = "";
        String newColor = "";
        float intRed = 0;
        float intGreen = 0;
        float intBlue = 0;
        float darkness;
        int umbral = 765 / 2;

        try {
            // se valida y normaliza la cadena
            color = normalizeColorString(color);
            // convertimos el porcentaje recibido en una escala de 0 a 255
            percent = (percent * 255) / 100;
            // partimos la cadena de color en sus tres componentes
            red = color.substring(0, 2);
            green = color.substring(2, 4);
            blue = color.substring(4, 6);

            // convertimos las cadenas en hexadecimal a entero
            intRed = Integer.parseInt(red, 16);
            intGreen = Integer.parseInt(green, 16);
            intBlue = Integer.parseInt(blue, 16);
            // calculamos la oscuridad del color entrante
            darkness = intRed + intGreen + intBlue;
            // validamos si el color es claro u oscuro
            if ((darkness >= umbral) || (c == 'o')) {
                // claro, lo oscurece
                intRed = (intRed - percent < 00) ? 00 : intRed - percent;
                intGreen = (intGreen - percent < 00) ? 00 : intGreen - percent;
                intBlue = (intBlue - percent < 00) ? 00 : intBlue - percent;
            }
            if ((darkness <= umbral) || (c == 'a')) {
                // oscuro lo aclara
                intRed = (intRed + percent > 255) ? 255 : intRed + percent;
                intGreen = (intGreen + percent > 255) ? 255 : intGreen + percent;
                intBlue = (intBlue + percent > 255) ? 255 : intBlue + percent;
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        // convertimos los valores enteros a hexadecimal
        red = Integer.toHexString((int) intRed);
        if (red.equals("0"))
            red = "00";
        green = Integer.toHexString((int) intGreen);
        if (green.equals("0"))
            green = "00";
        blue = Integer.toHexString((int) intBlue);
        if (blue.equals("0"))
            blue = "00";
        // formamos la cadena del nuevo color
        newColor = "#" + red + blue + green;
        return newColor;
    }// darkerOrBrighter


    /**
     * Se utiliza función darkerOrBrighter con un porcentaje fijo de 60
     * 
     * @param color
     * @return
     */
    public static String contrastingColor(String color) {
        return darkerOrBrighter(color, 60);
    }// contrastingColor

    /**
     * Regresa una lista de Strings con colores en hexadecimal
     * 
     * @param color
     * @param nColors
     * @return
     */


    public static List<String> gamaColor(String color, int nColors) {
        List<String> gama = new ArrayList();
        int par = 0;
        int impar = 0;
        int cambio = 50 / nColors;

        // determinar si el numero es par o impar
        if (nColors % 2 == 0) {
            // par
            par = (nColors / 2);
            impar = (nColors / 2);
        } else {
            // impar
            par = (nColors / 2) + 1;
            impar = nColors - par;
        }
        // valida cadena de color
        color = normalizeColorString(color);
        gama.add(color);
        // genera gamas oscuras
        for (int i = 0; i < impar; i++) {
            // se genera el color y se agrega a la lista
            gama.add(darkerOrBrighter(color, cambio, 'o'));
            cambio += cambio;
        }
        // genera gamas claras
        for (int j = 0; j < par; j++) {
            // se genera el color y se agrega a la lista
            gama.add(darkerOrBrighter(color, cambio, 'a'));
            cambio += cambio;
        }
        return gama;
    }// gamaColor


    /**
     * Regresa un color en hexadecimal obtenido del pixel en la coordenada especificada
     * 
     * @param path
     * @param x
     * @param y
     * @return
     */
    public static String getColorPixel(String path, int x, int y) {
        String color = "";
        Image img = null;
        BufferedImage buffImg = null;
        File imgTemp = null;

        try {
            // Devuelve el fichero seleccionado
            imgTemp = new File(path);
            img = new ImageIcon(path).getImage();
            buffImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            buffImg = ImageIO.read(imgTemp);
            // obtiene el vaor rgb del pixel en la coordenada x,y
            Color mycolor = new Color(buffImg.getRGB(x, y));
            color = colorToHex(mycolor);
        } catch (IOException ex) {
            SystemLog.writeException(ex);
        } finally {
            if (buffImg != null)
                buffImg.flush();
        }
        return color;
    }// getColorPixel


    /**
     * Convierte un color de 24 bits en uno de 8 bits
     * 
     * @param color
     * @return
     */
    public static Color rgb_as8bit(Color color) {

        Color eightBitColor;
        // obtenemos los componentes RGB del color y les aplicamos a conversión
        int red = (color.getRed() * 6 / 256) * 36;
        int green = (color.getGreen() * 6 / 256) * 6;
        int blue = (color.getBlue() * 6 / 256);
        // creamos un nuevo color con los componentes RGB generados
        eightBitColor = new Color(red, green, blue);
        return eightBitColor;
    }// rgb_as8bit


    /**
     * Genera una cuadricula con los colores especificados en la lista recibida
     * 
     * @param list
     * @param colums
     * @return
     */
    private static String genColorTable(List<Color> list, int colums) {
        String arHtml = "";
        String finalStr = "";
        String rgb = "";
        String colorHexa = "";
        String cellSize = "30px";

        for (int i = 0; i <= colums; i++) {
            // por cada elemento de la lista de colores
            for (Color col : list) {
                arHtml = "<div style=\"width: " + cellSize + ";" + "height: " + cellSize + ";" + "background-color: ";
                // obtenemos el color en hexadecimal
                colorHexa = colorToHex(col);
                arHtml += colorHexa + "\">";
                // obtenemos el RGB del color y lo pasamos a cadena
                rgb = Integer.toString(col.getRed()) + "-" + Integer.toString(col.getGreen()) + "-"
                        + Integer.toString(col.getBlue());
                arHtml += rgb + "< /div>";
                finalStr += arHtml;
                arHtml = "";
            }
            finalStr += "<br/>";
        }
        return finalStr;
    }// genColorTable


    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
