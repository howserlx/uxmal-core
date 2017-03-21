/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * ErrorTable.java
 * ErrorTable Enum
 *
 * Created on : 8/09/2014
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
 * ErrorTable Enu
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */

public enum ErrorTable {

    SYSTEM_GENERAL      ("000",ErrorType.SYSTEM, "ERROR_","ERROR_MSG_"),
    DATABASE_GENERAL    ("100",ErrorType.DATABASE, "ERROR_","ERROR_MSG_"),
    PERSISTENCE_GENERAL ("200",ErrorType.PERSISTENCE, "ERROR_","ERROR_MSG_"),
    BUSINESS_GENERAL    ("300",ErrorType.BUSINESS, "ERROR_","ERROR_MSG_"),
    CONTROLLER_GENERAL  ("400",ErrorType.CONTROLLER, "ERROR_","ERROR_MSG_"),
    VIEW_GENERAL        ("500",ErrorType.VIEW, "ERROR_","ERROR_MSG_"),
    
    SECURITY_GENERAL    ("600",ErrorType.SECURITY, "ERROR_","ERROR_MSG_"),
    CRYPTOGRAPHY        ("610",ErrorType.SECURITY, "ERROR_","ERROR_MSG_"),
    FILE_GENERAL        ("700",ErrorType.FILE, "ERROR_","ERROR_MSG_"),
    REPORT_GENERAL      ("800",ErrorType.REPORT, "ERROR_","ERROR_MSG_"),
    ENCRYPT_GENERAL     ("900",ErrorType.ENCRYPT, "ERROR_","ERROR_MSG_"),
    ;

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    private final String errorCode;
    private final ErrorType errorType;
    private final String errorName;
    private final String errorMessage;

    private ErrorTable(String errorCode, ErrorType errorType, String errorName, String errorMessage) {
        this.errorCode = errorCode;
        this.errorType = errorType;
        this.errorName = errorName;
        this.errorMessage = errorMessage;
    }// constructor

    // ------------------------------------------------------
    // Getters & Setters
    // ------------------------------------------------------
    // <editor-fold desc="Getters & Setters">
    public String getErrorCode() {
        return errorCode;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getErrorName() {
        if (errorName.equals("ERROR_"))
            return errorName + this.name();

        return errorName;
    }

    public String getErrorMessage() {
        if (errorMessage.equals("ERROR_MSG_"))
            return errorMessage + this.name();

        return errorMessage;
    }


    // </editor-fold>


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold desc="Methods and Functions">
    /**
     * Find a SystemError by Code
     * 
     * @param code
     * @return
     */
    public static ErrorTable findByCode(String code) {
        for (ErrorTable item : ErrorTable.values()) {
            if (item.getErrorCode().equals(code))
                return item;
        }
        return null;
    }// findByCode
    
    public static Map asMap() {
        Map map = new LinkedHashMap();
        for (ErrorTable item : ErrorTable.values()) {
            map.put(item.getErrorName(), item.getErrorCode());
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
