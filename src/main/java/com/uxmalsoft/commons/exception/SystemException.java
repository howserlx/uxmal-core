/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * SystemException.java
 * SystemException
 *
 * Created on : 01/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.exception;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import com.uxmalsoft.commons.error.ErrorType;
import com.uxmalsoft.commons.error.SystemError;


/**
 * 
 * System Exception
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class SystemException extends Exception {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    private String description  = "";
    private String idOperation  = "";
    private SystemError error   = null;
    private String errorCode    = "";
    private ErrorType errorType;
    private String errorMessage = "";

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SystemException(String description) {
        this(null, description, "");
    }

    public SystemException(String description, String idOperation) {
        this(null, description, idOperation);
    }

    public SystemException(SystemError sError) {
        this(sError, "", "");
    }

    public SystemException(SystemError sError, String idOperation) {
        this(sError, "", idOperation);
    }

    public SystemException(SystemError sError, String description, String idOperation) {

        this.description = description;
        this.idOperation = idOperation;

        if (sError != null) {
            this.error = sError;
            this.errorType = sError.getErrorType();
            this.errorCode = sError.getErrorCode();
            this.errorMessage = sError.getErrorMessage();
        }
    }

    public SystemException() {

    }
    // </editor-fold>


    // ------------------------------------------------------
    // Getters & Setters
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public final String getDescription() {
        return description;
    }

    public final String getIdOperation() {
        return idOperation;
    }

    public final SystemError getError() {
        return error;
    }

    public final String getErrorCode() {
        return errorCode;
    }

    public final ErrorType getErrorType() {
        return errorType;
    }

    public final String getErrorMessage() {
        return errorMessage;
    }
    // </editor-fold>

    // ------------------------------------------------------
    // Methods & Functions
    // ------------------------------------------------------
    /**
     * Obtiene un mensaje error desde un Throwable
     * 
     * @param ex
     * @return
     */
    protected static String getErrorMessage(Throwable ex) {
        String msg = "";
        if (ex != null)
            msg = ex.getMessage();

        msg = (msg == null) ? "" : msg;

        return msg;
    }// getErrorMessage

    
    /**
     * Obtiene un mensaje description desde un Throwable
     * 
     * @param ex
     * @return
     */
    protected static String getDescription(Throwable ex) {
        String s = (ex != null) ? ex.getMessage() : "";
        s = (s == null) ? "" : s;

        if (ex != null && s.isEmpty() && ex.getCause() != null) {
            s = ex.getCause().getMessage();
            s = (s == null) ? "" : s;
        }

        return s;
    }// getDescription

    
    @Override
    public String toString() {
        String res = (isNotEmpty(description)) ? description : validateNull(errorMessage);
        return res;
    }


}// class

