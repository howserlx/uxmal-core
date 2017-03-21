/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * PersistenceException.java
 * Persistence Exception
 *
 * Created on : 01/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.exception.persistence;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import com.uxmalsoft.commons.error.ErrorTable;
import com.uxmalsoft.commons.error.SystemError;
import com.uxmalsoft.commons.exception.SystemException;

/**
 * 
 * Persistence Exception
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class PersistenceException extends SystemException {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------


 // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public PersistenceException(String description) {
        super(null, description, "");
    }

    public PersistenceException(String description, String idOperation) {
        super(null, description, idOperation);
    }

    public PersistenceException(SystemError sError) {
        super(sError, "", "");
    }

    public PersistenceException(SystemError sError, String idOperation) {
        super(sError, "", idOperation);
    }

    public PersistenceException(SystemError sError, String description, String idOperation) {
        super(sError, description, idOperation);
    }


    public PersistenceException(Throwable ex) {
        super(SystemError.createSystemError(ErrorTable.DATABASE_GENERAL,
                getDescription(ex) + " " + getErrorMessage(ex)), "", "");
    }

    public PersistenceException(Throwable ex, String description) {
        super(SystemError.createSystemError(ErrorTable.DATABASE_GENERAL,
                getDescription(ex) + " " + getErrorMessage(ex)), description, "");
    }

    public PersistenceException(String idOperation, Throwable ex) {
        super(SystemError.createSystemError(ErrorTable.DATABASE_GENERAL,
                getDescription(ex) + " " + getErrorMessage(ex)), "", idOperation);
    }

    public PersistenceException(Throwable ex, String description, String idOperation) {
        super(SystemError.createSystemError(ErrorTable.DATABASE_GENERAL,
                getDescription(ex) + " " + getErrorMessage(ex)), description, idOperation);
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
        if (ex instanceof SQLException) {
            SQLException sqlEx = (SQLException) ex;
            message = sqlEx.getMessage();

        } else if (ex instanceof JDBCException) {
            JDBCException jdbEx = (JDBCException) ex;
            message = jdbEx.getMessage();
            SQLException sqlEx = jdbEx.getSQLException();

        } else if (ex instanceof HibernateException) {
            HibernateException hibEx = (HibernateException) ex;
            message = hibEx.getMessage();
            
        } else if (ex instanceof javax.persistence.PersistenceException) {
            javax.persistence.PersistenceException pe = 
                    (javax.persistence.PersistenceException) ex;
            
            message = pe.getMessage();
        }  else {
            if (ex != null)
                message = ex.getMessage();
        }

        if (message == null || message.isEmpty())
            message = "DATABASE ERROR";

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
    // </editor-fold>


}// class
