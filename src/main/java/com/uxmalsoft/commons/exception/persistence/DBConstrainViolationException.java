/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * DBConstrainViolationException.java
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

import com.uxmalsoft.commons.error.SystemError;

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
public class DBConstrainViolationException extends PersistenceException {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------


    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public DBConstrainViolationException(String description) {
        super((SystemError) null, description, "");
    }

    public DBConstrainViolationException(String description, String idOperation) {
        super((SystemError) null, description, idOperation);
    }

    public DBConstrainViolationException(SystemError sError) {
        super(sError, "", "");
    }

    public DBConstrainViolationException(SystemError sError, String idOperation) {
        super(sError, "", idOperation);
    }

    public DBConstrainViolationException(SystemError sError, String description, String idOperation) {
        super(sError, description, idOperation);
    }


    public DBConstrainViolationException(Throwable ex) {
        super(ex, "", "");
    }

    public DBConstrainViolationException(Throwable ex, String description) {
        super(ex, description, "");
    }

    public DBConstrainViolationException(String idOperation, Throwable ex) {
        super(ex, "", idOperation);
    }

    public DBConstrainViolationException(Throwable ex, String description, String idOperation) {
        super(ex, description, idOperation);
    }

    // </editor-fold>


}// class
