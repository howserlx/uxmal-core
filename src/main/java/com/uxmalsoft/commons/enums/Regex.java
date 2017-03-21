/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * Regex.java
 * Enum de Expresiones Regulares
 *
 * Created on : 14/08/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.enums;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * Enum de Expresiones Regulares
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public enum Regex {

    ALPHA           ("[\\p{Alpha}ÁÉÍÓÚáéíóúñÑ][\\p{Alpha}ÁÉÍÓÚáéíóúñÑ\\s]+","REGEX_DESC_ALPHA","REGEX_FAIL_ALPHA"),
    NUMERIC         ("[\\p{Digit}]+","REGEX_DESC_NUMERIC","REGEX_FAIL_NUMERIC"),
    SPECIAL         ("[^\\p{Alnum}\\s]+","REGEX_DESC_SPECIAL","REGEX_FAIL_SPECIAL"),
    ALPHA_S         ("[^0-9]+","REGEX_DESC_ALPHA_S","REGEX_FAIL_ALPHA_S"),
    ALPHA_NUM       ("[\\p{Alnum}ÁÉÍÓÚáéíóúñÑ\\s]+","REGEX_DESC_ALPHA_NUM","REGEX_FAIL_ALPHA_NUM"),
    ALPHA_NUM_S     ("[\\p{ASCII}\\s]+","REGEX_DESC_ALPHA_NUM_S","REGEX_FAIL_ALPHA_NUM_S"),
    NAME            ("[\\p{Alpha}ÁÉÍÓÚáéíóúñÑ\\s\\.\\,]+","REGEX_DESC_NAME","REGEX_FAIL_NAME"),
    USERNAME        ("^[\\p{Alpha}][\\p{Alnum}_-|\\.|ñ|Ñ]+","REGEX_DESC_USERNAME","REGEX_FAIL_USERNAME"),
    URL             ("^(http:\\/\\/|https:\\/\\/|ftp:\\/\\/|www.)?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+\\.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z][a-z]{1,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$","REGEX_DESC_URL","REGEX_FAIL_URL"),
    PHONE           ("([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})","REGEX_DESC_PHONE","REGEX_FAIL_PHONE"),
    EMAIL           ("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$","REGEX_DESC_EMAIL","REGEX_FAIL_EMAIL"),
    IP              ("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$","REGEX_DESC_IP","REGEX_FAIL_IP"),
    LAT_OR_LONG     ("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)\\s*,\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$","REGEX_DESC_LAT_OR_LONG","REGEX_FAIL_LAT_OR_LONG"),
    LAT_LONG        ("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)","REGEX_DESC_LAT_LONG","REGEX_FAIL_LAT_LONG"),
    PASSWORD_LOW    ("([a-zA-Z0-9]){8,30}$","REGEX_DESC_PASSWORD_LOW","REGEX_FAIL_PASSWORD_LOW"),
    PASSWORD_MEDIUM ("(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,30})$","REGEX_DESC_PASSWORD_MEDIUM","REGEX_FAIL_PASSWORD_MEDIUM"),
    PASSWORD_HIGH   ("^[a-zA-Z0-9\\\\-_\\\\.]*(?=[a-zA-Z0-9\\\\-_\\\\.]{8,30})(?=[a-zA-Z0-9\\\\-_\\\\.]*\\\\d)(?=[a-zA-Z0-9\\\\-_\\\\.]*[a-zA-Z])(?=[a-zA-Z0-9\\\\-_\\\\.]*[\\\\-_\\\\.])[a-zA-Z0-9\\\\-_\\\\.]*$","REGEX_DESC_PASSWORD_HIGH","REGEX_FAIL_PASSWORD_HIGH"),
    ;

    private final String regEx;
    private final String description;
    private final String msgFail; // Mensaje cuando la regex falla

    private Regex(String regEx, String description, String msgFail) {
        this.regEx = regEx;
        this.description = description;
        this.msgFail = msgFail;
    }// constructor


    // ------------------------------------------------------
    // Getters & Setters
    // ------------------------------------------------------
    // <editor-fold desc="Getters & Setters">
    public String getName() {
        return this.name();
    }

    public String getRegEx() {
        return regEx;
    }

    public String getDescription() {
        return description;
    }

    public String getMsgFail() {
        return msgFail;
    }

    // </editor-fold>


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold desc="Methods and Functions">

    public static Regex findByName(String searchName) {

        for (Regex item : Regex.values()) {
            if (item.name().equals(searchName))
                return item;
        } // fore

        return null;
    }// findByName

    public static Map asMap() {
        Map map = new LinkedHashMap();
        for (Regex item : Regex.values()) {
            map.put(item.name(), item.getRegEx());
        }
        return map;
    }// asMap


    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>


}// enum Regex
