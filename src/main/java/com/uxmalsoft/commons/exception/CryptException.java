/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * CryptException.java
 * Cryptography Exception
 *
 * Created on : 01/05/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.exception;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.uxmalsoft.commons.error.ErrorTable;
import com.uxmalsoft.commons.error.SystemError;

/**
 * 
 * Cryptography Exception
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class CryptException extends SystemException {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------


    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public CryptException(String description) {
        super(null, description, "");
    }

    public CryptException(String description, String idOperation) {
        super(null, description, idOperation);
    }

    public CryptException(SystemError sError) {
        super(sError, "", "");
    }

    public CryptException(SystemError sError, String idOperation) {
        super(sError, "", idOperation);
    }

    public CryptException(SystemError sError, String description, String idOperation) {
        super(sError, description, idOperation);
    }


    public CryptException(Throwable ex) {
        super(SystemError.createSystemError(ErrorTable.CRYPTOGRAPHY, getDescription(ex) + " " + getErrorMessage(ex)),
                "", "");
    }

    public CryptException(Throwable ex, String description) {
        super(SystemError.createSystemError(ErrorTable.CRYPTOGRAPHY, getDescription(ex) + " " + getErrorMessage(ex)),
                description, "");
    }

    public CryptException(String idOperation, Throwable ex) {
        super(SystemError.createSystemError(ErrorTable.CRYPTOGRAPHY, getDescription(ex) + " " + getErrorMessage(ex)),
                "", idOperation);
    }

    public CryptException(Throwable ex, String description, String idOperation) {
        super(SystemError.createSystemError(ErrorTable.CRYPTOGRAPHY, getDescription(ex) + " " + getErrorMessage(ex)),
                description, idOperation);
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
        String message = "";

        if (ex instanceof NoSuchAlgorithmException) {
            NoSuchAlgorithmException e = (NoSuchAlgorithmException) ex;
            message = e.getMessage();

        } else if (ex instanceof NoSuchPaddingException) {
            NoSuchPaddingException e = (NoSuchPaddingException) ex;
            message = e.getMessage();

        } else if (ex instanceof InvalidKeyException) {
            InvalidKeyException e = (InvalidKeyException) ex;
            message = e.getMessage();

        } else if (ex instanceof IllegalBlockSizeException) {
            IllegalBlockSizeException e = (IllegalBlockSizeException) ex;
            message = e.getMessage();

        } else if (ex instanceof BadPaddingException) {
            BadPaddingException e = (BadPaddingException) ex;
            message = e.getMessage();

        } else if (ex instanceof InvalidAlgorithmParameterException) {
            InvalidAlgorithmParameterException e = (InvalidAlgorithmParameterException) ex;
            message = e.getMessage();

        } else {
            if (ex != null)
                message = ex.getMessage();
        }

        if (message == null || message.isEmpty())
            message = "CRYPTOGRAPHY ERROR";

        return message;
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
}// class
