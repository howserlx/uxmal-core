/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * JavaObject.java
 * Enum Objeto de Java
 *
 * Created on : 07/07/2015
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
 * Enum Objeto de Java
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public enum JavaObject {

    BOOLEAN    ( 1,"Boolean"),
    STRING     ( 2,"String"),
    SHORT      ( 3,"Short"),
    INTEGER    ( 4,"Integer"),
    LONG       ( 5,"Long"),
    FLOAT      ( 6,"Float"),
    DOUBLE     ( 7,"Double"),
    DATE       ( 8,"Date"),
    TIME       ( 9,"Time"),
    ;

    private final int id;
    private final String label;

    private JavaObject(int id, String label) {
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
    public static JavaObject findById(int searchId) {
        for (JavaObject item : JavaObject.values()) {
            if (item.getId() == searchId)
                return item;
        }
        return null;
    }// findById


    public static JavaObject findByName(String searchName) {
        for (JavaObject item : JavaObject.values()) {
            if (item.getLabel().equals(searchName))
                return item;
        }
        return null;
    }// findByName

    public static Map asMap() {
        Map map = new LinkedHashMap();
        for (JavaObject item : JavaObject.values()) {
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
