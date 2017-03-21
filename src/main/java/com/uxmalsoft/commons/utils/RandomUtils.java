/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * RandomUtils.java
 * Random Utils
 *
 * Created on : 12/03/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.utils;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Random Utils
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class RandomUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private RandomUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Regresa un entero aleatorio entre los rangos (incluidos)
     * 
     * @param min
     * @param max
     * @return
     */
    public static int getInteger(long min, long max) {
        return getInteger((int) min, (int) max);
    }// getInteger

    /**
     * Regresa un entero aleatorio entre los rangos (incluidos)
     * 
     * @param min
     * @param max
     * @return
     */
    public static int getInteger(int min, int max) {
        int res = -1;
        try {
            Random random = new Random();
            res = random.nextInt(max - min + 1) + min;
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// getInteger


    /**
     * Obtiene un String de caracteres alfabeticos
     * 
     * @param count
     * @return
     */
    public static String getAlphaString(int count) {
        return getAlphaString(count, StringUtils.LetterCase._NONE);
    }// getAlphaString

    /**
     * Obtiene un String de caracteres alfabeticos
     * 
     * @param count
     * @param letterCase
     * @return
     */
    public static String getAlphaString(int count, StringUtils.LetterCase letterCase) {
        String str = null;
        try {
            str = getRandomString("alpha", count);
            str = StringUtils.convertLetterCase(str, letterCase);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return str;
    }// getAlphaString


    /**
     * Obtiene un String de caracteres numericos
     * 
     * @param count
     * @return
     */
    public static String getNumString(int count) {
        String str = null;
        try {
            str = getRandomString("num", count);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return str;
    }// getNumString



    /**
     * Obtiene un String de caracteres alfabeticos
     * 
     * @param count
     * @return
     */
    public static String getAlphaNumString(int count) {
        return getAlphaNumString(count, StringUtils.LetterCase._NONE);
    }// getAlphaNumString

    /**
     * Obtiene un String de caracteres alfanumericos
     * 
     * @param count
     * @param letterCase
     * @return
     */
    public static String getAlphaNumString(int count, StringUtils.LetterCase letterCase) {
        String str = null;
        try {
            str = getRandomString("alpha_num", count);
            str = StringUtils.convertLetterCase(str, letterCase);
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return str;
    }// getAlphaNumString


    /**
     * Obtiene una String aleatoria (@private)
     * 
     * @param type
     * @param count
     * @return
     */
    private static String getRandomString(String type, int count) {
        String str = null;

        switch (type) {
            case "alpha":
                str = RandomStringUtils.randomAlphabetic(count);
                break;

            case "num":
                str = RandomStringUtils.randomNumeric(count);
                break;

            case "alpha_num":
                str = RandomStringUtils.randomAlphanumeric(count);
                break;
        }// switch

        return str;
    }// getRandomString

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
