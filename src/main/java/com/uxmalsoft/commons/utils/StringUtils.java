/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * StringUtils.java
 * String Utilities
 *
 * Created on : 08/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Utilidades de Cadenas
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class StringUtils {

    public enum LetterCase { // (Ej. hola MunDo del mañana)
        _NONE,                  //Sin cambios
        UPPER_CASE,             //Mayusculas (HOLA MUNDO DEL MAÑANA)
        LOWER_CASE,             //minusculas (hola mundo del mañana)
        SENTENCE_CASE,          //Primer letra de la primer palabra en Mayusculas,no cambiar otras letras (Hola MunDo Del Mañana)
        SENTENCE_LOWER_CASE,    //Primer letra de la primer palabra en Mayusculas,las demas en minusculas (Hola Mundo Del Mañana)
        CAPITALIZED_CASE,       //Primer letra de cada palabra en Mayusculas
        CAPITALIZED_NAME_CASE,  //Primer letra de cada palabra en Mayusculas (excepto pronombres y artículos que van en minusculas)
        CAMEL_CASE,             //Primer letra en minuscula, cada palabra siguiente con la primera letra en Mayuscula (eliminar espacios)
        DOMAIN_NAME_CASE,       //Minusculas, espacios y _ reemplazados por . (Ej. hola.mundo.del.mañana)
        SNAKE_CASE,             //Reemplazar espacios con _, no cambiar Mayusculas ni minusculas
        SNAKE_UPPER_CASE,       //Reemplazar espacios con _, texto a Mayusculas
        SNAKE_LOWER_CASE,       //Reemplazar espacios con _, texto a minusculas
        ALTERNATING_UPPER_CASE, //Alternar Mayuscula de cada palabra, minuscula, empezando con Mayuscula
        ALTERNATING_LOWER_CASE, //Alternar minuscula de cada palabra, Mayuscula, empezando con minuscula
        ;
    }// enum

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">
    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private StringUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Validations
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Validations">
    /**
     * Valida si un String es null o esta vacio
     * 
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.isEmpty());
    }// isNullOrEmpty

    /**
     * Valida si un String no es null y no esta vacio
     * 
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return (str != null && !str.isEmpty());
    }// isNotEmpty

    /**
     * Omite cadenas nulas
     * 
     * @param text
     * @return
     */
    public static String validateNull(String text) {
        return validateNull(text, true);
    }// validateNull

    /**
     * Omite cadenas nulas
     * 
     * @param text
     * @param trim String
     * @return
     */
    public static String validateNull(String text, boolean trim) {
        if (trim)
            return (text == null) ? "" : text.trim();
        else
            return (text == null) ? "" : text;
    }// validateNull

    // </editor-fold>


    // ------------------------------------------------------
    // LETTER CASE
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="LETTER CASE">

    /**
     * Procesa el texto de acuerdo al algoritmo
     * 
     * @param text
     * @param letter_case
     * @return
     */
    public static String convertLetterCase(String text, LetterCase letter_case) {
        return convertLetterCase(text, letter_case, true);
    }// convertLetterCase

    /**
     * Procesa el texto de acuerdo al algoritmo
     * 
     * @param text
     * @param letter_case
     * @param trim
     * @return
     */
    public static String convertLetterCase(String text, LetterCase letter_case, boolean trim) {
        if (text == null)
            return null;

        String res = text;

        if (trim)
            res = res.trim();


        if (letter_case != null) {
            switch (letter_case) {
                case UPPER_CASE:
                    res = res.toUpperCase();
                    break;

                case LOWER_CASE:
                    res = res.toLowerCase();
                    break;

                case SENTENCE_CASE:
                    if (res.length() > 1) {
                        res = res.substring(0, 1).toUpperCase() + res.substring(1);
                    } else
                        res = res.toUpperCase();
                    break;
                case SENTENCE_LOWER_CASE:
                    res = res.toLowerCase();
                    if (res.length() > 1) {
                        res = res.substring(0, 1).toUpperCase() + res.substring(1);
                    } else
                        res = res.toUpperCase();
                    break;

                case CAPITALIZED_CASE:
                    res = _CAPITALIZED_TEXT(text, false);
                    break;

                case CAPITALIZED_NAME_CASE:
                    res = _CAPITALIZED_TEXT(text, true);
                    break;

                case CAMEL_CASE:
                    res = removeWhiteSpaces(_CAPITALIZED_TEXT(text, false));
                    if (res.length() > 1) {
                        res = res.substring(0, 1).toLowerCase() + res.substring(1);
                    } else
                        res = res.toLowerCase();

                    break;

                case DOMAIN_NAME_CASE:
                    res = res.replaceAll("\\s+", ".");
                    res = res.replaceAll("_", ".");
                    res = removeAccents(res).toLowerCase();
                    break;

                case SNAKE_CASE:
                    res = res.replaceAll("\\s+", "_");
                    break;

                case SNAKE_UPPER_CASE:
                    res = res.replaceAll("\\s+", "_");
                    res = res.toUpperCase();
                    break;

                case SNAKE_LOWER_CASE:
                    res = res.replaceAll("\\s+", "_");
                    res = res.toLowerCase();
                    break;

                case ALTERNATING_UPPER_CASE:
                    res = _ALTERNATING_CASE(text, false);
                    break;

                case ALTERNATING_LOWER_CASE:
                    res = _ALTERNATING_CASE(text, true);
                    break;

                default:


            }// switch
        }
        return res;
    }// convertLetterCase


    /**
     * Determina si es un articulo (español)
     * 
     * @return
     */
    public static Map<String, String> getArticle() {
        // articulos: el, la, los, las, un, una, unos, unas
        // de, del, a
        Map<String, String> _map = new HashMap<>();
        _map.put("el"  , "el");
        _map.put("la"  , "la");
        _map.put("los" , "los");
        _map.put("las" , "las");
        _map.put("un"  , "un");
        _map.put("una" , "una");
        _map.put("uno" , "unos");
        _map.put("unas", "una");
        _map.put("de"  , "de");
        _map.put("del" , "del");
        _map.put("a"   , "a");
        _map.put("y"   , "y");
        _map.put("o"   , "o");
        _map.put("en"  , "en");
        _map.put("que" , "que");

        return _map;
    }// getArticle


    /**
     * _CAPITALIZED_TEXT (@private)
     * 
     * @param text
     * @param capitalized_name_Case
     * @return
     */
    private static String _CAPITALIZED_TEXT(String text, boolean capitalized_name_Case) {
        if (text == null)
            return null;

        String[] palabras;
        StringBuilder textoFormateado;
        Map<String, String> articulos = getArticle();

        palabras = text.split("\\s");
        textoFormateado = new StringBuilder();
        boolean primerPalabra = false;
        for (String palabra : palabras) {
            if (palabra.length() > 0) {
                if (capitalized_name_Case && primerPalabra && articulos.containsValue(palabra.trim().toLowerCase())) {
                    // Es un articulo
                    textoFormateado.append(palabra.toLowerCase().concat(" "));
                } else {
                    primerPalabra = true;
                    textoFormateado.append(palabra.substring(0, 1).toUpperCase()
                            .concat(palabra.substring(1, palabra.length())
                                    .toLowerCase())
                            .concat(" "));
                }
            }
        }
        return textoFormateado.toString();
    }// _CAPITALIZED_TEXT


    /**
     * _ALTERNATING_CASE (@private)
     * 
     * @param text
     * @param alternating_lower_case
     * @return
     */
    private static String _ALTERNATING_CASE(String text, boolean alternating_lower_case) {
        if (text == null)
            return null;

        String[] palabras;
        StringBuilder textoFormateado;

        palabras = text.split("\\s");
        textoFormateado = new StringBuilder();
        boolean primerPalabra = false;
        for (String palabra : palabras) {
            if (palabra.length() > 0) {
                textoFormateado.append(_ALTERNATING_CASE_WORD(palabra, alternating_lower_case, false).concat(" "));
            }
        }
        return textoFormateado.toString();
    }// _ALTERNATING_CASE


    /**
     * _ALTERNATING_CASE_WORD (@private)
     * 
     * @param text
     * @param alternating_lower_case
     * @param _trim
     * @return
     */
    private static String _ALTERNATING_CASE_WORD(String text, boolean alternating_lower_case, boolean _trim) {
        if (text == null)
            return null;

        String palabras;
        StringBuilder textoFormateado;

        if (_trim)
            palabras = text.trim();
        else
            palabras = text;

        textoFormateado = new StringBuilder();
        boolean primerPalabra = false, to_Lower = alternating_lower_case;
        for (char letra : palabras.toCharArray()) {
            if (Character.isLetter(letra)) {
                if (to_Lower) {
                    textoFormateado.append(Character.toLowerCase(letra));
                    to_Lower = !to_Lower;
                } else {
                    textoFormateado.append(Character.toUpperCase(letra));
                    to_Lower = !to_Lower;
                }
            }
        }
        return textoFormateado.toString();
    }// _ALTERNATING_CASE_WORD

    // </editor-fold>


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Obtiene el arreglo de Bytes de una cadena
     * 
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        return ConversionUtils.getBytes_fromString(str);
    }// getBytes

    /**
     * Obtiene el arreglo de Bytes de una cadena
     * 
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] getBytes(String str, String encoding) {
        return ConversionUtils.getBytes_fromString(str, encoding);
    }// getBytes

    /**
     * Regresa una String[]
     * 
     * @param arr
     * @return
     */
    public static String[] asStringArray(String... arr) {
        return arr;
    }// asStringArray


    public static String prepareMsg(String text, Object... arr) {
        List<Object> list = Arrays.asList(arr);
        List<String> listStr = new ArrayList<>();

        for (Object obj : list) {
            if (obj != null)
                listStr.add(obj.toString());
        }

        String[] strArr = new String[listStr.size()];
        listStr.toArray(strArr);
        return prepareMsg(text, strArr);
    }// prepareMsg

    /**
     * Prepara mensajes para reemplazar cadenas dentro del mensaje
     * traduccion de mensajes principalmente {n}
     * 
     * @param text
     * @param arr
     * @return
     */
    public static String prepareMsg(String text, String... arr) {
        String res = (text != null) ? text : "";

        if (arr != null && arr.length > 0) {
            try {
                for (int i = 0; i < arr.length; i++) {
                    String regEx = "\\{" + (i) + "\\}";
                    if (arr[i] != null && !arr[i].isEmpty())
                        res = res.replaceAll(regEx, arr[i]);
                } // for
            } catch (Exception ex) {
                SystemLog.writeUtilsException(ex);
            }
        } else {
            String regEx = "\\{" + "[0-9]+" + "\\}";
            res = res.replaceAll(regEx, "");
        }
        return res;
    }// prepareMsg


    /**
     * Remover acentos
     * 
     * @param text
     * @return
     */
    public static String removeAccents(String text) {
        return removeAccents(text, true);
    }// removeAccents

    /**
     * Remover acentos
     * 
     * @param text
     * @param latin (incluye ñ)
     * @return
     */
    public static String removeAccents(String text, boolean latin) {
        if (text == null)
            return null;

        if (latin) {
            text = text.replaceAll("ñ", "{gn}");
            text = text.replaceAll("Ñ", "{GN}");
        }

        String str = Normalizer.normalize(text, Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        if (latin) {
            str = str.replaceAll("\\{gn\\}", "ñ");
            str = str.replaceAll("\\{GN\\}", "Ñ");
        }
        return str;
    }// removeAccents



    /**
     * Remueve espacios en blanco
     * 
     * @param text
     * @return
     */
    public static String removeWhiteSpaces(String text) {
        return text == null ? null : text.replaceAll("\\s", "");
    }// removeWhiteSpaces


    /**
     * Remueve todo menos letras (incluyendo acentos), numeros y espacios
     * 
     * @param text
     * @return
     */
    public static String onlyAlphaNumeric(String text) {
        return text == null ? null : text.replaceAll("[^a-zA-Z0-9 áéíóú ÁÉÍÓÚñÑ]", "");
    }// onlyAlphaNumeric


    /**
     * Genera un codigo basado en texto, para comparar nombres y determinar
     * si la variacion de 2 nombres coinciden
     * 
     * @param text
     * @return
     */
    public static String generateTextCode(String text) {
        if (text == null)
            return null;

        String str = text.toUpperCase();
        str = removeAccents(str);
        str = removeWhiteSpaces(str);
        str = onlyAlphaNumeric(str);
        return str;
    }// generateTextCode


    /**
     * Obtiene una subcadena entre 2 caracteres
     * 
     * @param text
     * @param delA
     * @param delB
     * @return
     */
    public static String getSubstringBetween(String text, char delA, char delB) {
        String res = "";
        List<String> list = getSubstringsBetween(text, delA, delB);
        if (list != null && list.size() == 1)
            res = list.get(0);
        return res;
    }// getSubstringBetween


    /**
     * Obtiene una lista de subcadenas entre 2 caracteres
     * 
     * @param text
     * @param delA
     * @param delB
     * @return
     */
    public static List<String> getSubstringsBetween(String text, char delA, char delB) {
        List<String> list = new ArrayList<>();

        try {
            if (text != null && text.length() > 0 && text.contains("" + delA) && text.contains("" + delB)) {

                int index = 0; // Indice
                char c = ' '; // Caracter actual
                StringBuilder sb = new StringBuilder();
                boolean firstDelFound = false; // Primer delimitador encontrado
                boolean secondDelFound = false; // Segundo delimitador encontrado

                while (index < text.length()) {
                    c = text.charAt(index);

                    // Primer delimitador
                    if (c == delA && !firstDelFound) {
                        firstDelFound = true;
                        index++;
                        if (index < text.length())
                            c = text.charAt(index);
                        else
                            break;
                    }

                    // Construye cadena
                    if (firstDelFound && !secondDelFound) {
                        if (c != delB)
                            sb.append(c);
                    }

                    // Segundo delimitador
                    if (c == delB && firstDelFound && !secondDelFound) {
                        secondDelFound = true;
                        c = '\0';
                    }
                    // Ciclo completo
                    if (firstDelFound && secondDelFound) {
                        firstDelFound = false;
                        secondDelFound = false;

                        if (sb.length() > 0)
                            list.add(sb.toString());

                        sb = new StringBuilder();
                    }

                    index++;
                } // while
            } // if!=null && contiene delimitadores

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return list;
    }// getSubstringsBetween



    /**
     * Busca nombres opcionales en una cadena y los separa
     * nombres entre [] o ()
     * y despues con un cierto separados
     * 
     * @param text
     * @param separator
     * @return
     */
    public static List<String> getOptionalNames(String text, String separator) {
        return getOptionalNames(text, separator, null);
    }// getOptionalNames


    /**
     * Busca nombres opcionales en una cadena y los separa
     * nombres entre [] o ()
     * y despues con un cierto separados
     * 
     * @param text
     * @param separator
     * @param letter_case Formato de Informacion
     * @return
     */
    public static List<String> getOptionalNames(String text, String separator, StringUtils.LetterCase letter_case) {
        if (text == null)
            return null;

        List<String> list = new ArrayList<>();

        // Unificar [] y ()
        text = text.replaceAll("\\[", "(");
        text = text.replaceAll("\\]", ")");


        // Nombre opcional entre ()
        List<String> substrings = getSubstringsBetween(text, '(', ')');

        // Elimina de cadena original
        for (String string : substrings) {
            text = text.replaceAll("\\(" + string + "\\)", "");
        }

        // Nombre opcional separado por simbolo?
        if (separator != null) {
            String[] arr = text.split(separator);
            for (String string : arr) {
                list.add(StringUtils.convertLetterCase(string, letter_case));
            } // fore

            if (!substrings.isEmpty()) {
                for (String string : substrings) {
                    String[] arr2 = string.split(separator);
                    for (String str : arr2) {
                        list.add(StringUtils.convertLetterCase(str, letter_case));
                    } // fore
                } // fore
            }
        } else {
            list.add(StringUtils.convertLetterCase(text, letter_case));
            for (String string : substrings) {
                list.add(StringUtils.convertLetterCase(string, letter_case));
            }
        }
        return list;
    }// getOptionalNames



    /**
     * Rellena una cadena al tamaño indicado por la izquierda con espacios
     * 
     * @param text
     * @param size
     * @return
     */
    public static String fillString(String text, int size) {
        return fillString(text, size, ' ', true);
    }// fillString


    /**
     * Rellena una cadena al tamaño indicado con el caracter dado por la izquierda
     * 
     * @param text
     * @param size
     * @param fillCharacter
     * @return
     */
    public static String fillString(String text, int size, char fillCharacter) {
        return fillString(text, size, fillCharacter, true);
    }// fillString


    /**
     * Rellena una cadena al tamaño indicado con el caracter dado
     * 
     * @param text Texto a rellenar
     * @param size Tamaño
     * @param fillCharacter Caracter de relleno
     * @param left Relleno por la izquierda
     * @return
     */
    public static String fillString(String text, int size, char fillCharacter, boolean left) {
        text = validateNull(text);
        size = (size < 0) ? 0 : size;

        if (text.length() == size) {
            return text;
        }

        if (text.length() > size) {
            return text.substring(0, size);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size - text.length(); i++) {
            sb.append(fillCharacter);
        }

        text = (left) ? sb.toString() + text : text + sb.toString();
        return text;
    }// fillString



    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>


}// class
