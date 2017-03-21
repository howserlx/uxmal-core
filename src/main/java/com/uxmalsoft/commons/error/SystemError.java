/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * SystemError.java
 * SystemError
 *
 * Created on : 01/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.error;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

/**
 * 
 * SystemError
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class SystemError {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    private String errorCode;
    private ErrorType errorType;
    private String errorName;
    private String errorMessage;

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    public SystemError(String errorCode, ErrorType errorType, String errorMessage) {
        this(errorCode, errorType, "", errorMessage);
    }// constructor

    public SystemError(String errorCode, ErrorType errorType, String errorName, String errorMessage) {
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

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    // </editor-fold>

    /**
     * Crea un Nuevo SystemError
     * 
     * @param errorCode
     * @param errorType
     * @param errorMessage
     * @return
     */
    public static SystemError createSystemError(String errorCode, ErrorType errorType, String errorMessage) {
        return new SystemError(errorCode, errorType, "", errorMessage);
    }// createSystemError


    /**
     * Crea un Nuevo SystemError en base a un ErrorTable registrado
     * 
     * @param error
     * @return
     */
    public static SystemError createSystemError(ErrorTable error) {
        return createSystemError(error, "");
    }// createSystemError


    /**
     * Crea un Nuevo SystemError en base a un ErrorTable registrado
     * 
     * @param error
     * @param errorMessage
     * @return
     */
    public static SystemError createSystemError(ErrorTable error, String errorMessage) {
        if (error != null) {
            if (errorMessage == null)
                errorMessage = "";
            if (errorMessage.isEmpty())
                errorMessage = error.getErrorMessage();

            SystemError err =
                    new SystemError(error.getErrorCode(), error.getErrorType(), error.getErrorName(), errorMessage);
            return err;
        }

        return null;
    }// createSystemError

    /**
     * Encuentra por codigo
     * 
     * @param code
     * @return
     */
    public static SystemError findByCode(String code) {
        ErrorTable err = ErrorTable.findByCode(code);

        return createSystemError(err);
    }// findByCode

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
