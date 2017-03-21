/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * ErrorType.java
 * ErrorType Enum
 *
 * Created on : 01/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.error;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 
 * ErrorType Enu
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public enum ErrorType {

    SYSTEM      ( 0, "ERROR_TYPE_SYSTEM"), 
    DATABASE    ( 1, "ERROR_TYPE_DATABASE"), 
    PERSISTENCE ( 2, "ERROR_TYPE_PERSISTENCE"), 
    BUSINESS    ( 3, "ERROR_TYPE_BUSINESS"),
    CONTROLLER  ( 4, "ERROR_TYPE_CONTROLLER"),
    VIEW        ( 5, "ERROR_TYPE_VIEW"),
     
    SECURITY    ( 6, "ERROR_TYPE_SECURITY"), 
    FILE        ( 7, "ERROR_TYPE_FILE"),
    REPORT      ( 8, "ERROR_TYPE_REPORT"),
    ENCRYPT     ( 9, "ERROR_TYPE_ENCRYPT"),
    ;

    private final int id;
    private final String label;

    private ErrorType(int id, String label) {
        this.id = id;
        this.label = label;
    }// constructor

    // ------------------------------------------------------
    // Getters & Setters
    // ------------------------------------------------------
    // <editor-fold desc="Getters & Setters">
    public String getName() {
        return this.name();
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    // </editor-fold>


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold desc="Methods and Functions">
    public static ErrorType findById(int searchId) {
        for (ErrorType item : ErrorType.values()) {
            if (item.getId() == searchId)
                return item;
        }
        return null;
    }// findById

    public static ErrorType findByLabel(String searchLabel) {
        for (ErrorType item : ErrorType.values()) {
            if (item.getLabel().equals(searchLabel))
                return item;
        }
        return null;
    }// findByLabel
    
    public static Map asMap() {
        Map map = new LinkedHashMap();
        for (ErrorType item : ErrorType.values()) {
            map.put(item.getLabel(), item.getId());
        }
        return map;
    }// asMap

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// enum
