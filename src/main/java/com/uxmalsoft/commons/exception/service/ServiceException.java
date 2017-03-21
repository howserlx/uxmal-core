/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * ServiceException.java
 * Persistence Exception
 *
 * Created on : 01/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.exception.service;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import com.uxmalsoft.commons.error.SystemError;
import com.uxmalsoft.commons.exception.SystemException;

/**
 * 
 * Service Exception
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class ServiceException extends SystemException {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------


    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public ServiceException(String description) {
        super(null, description, "");
    }

    public ServiceException(String description, String idOperation) {
        super(null, description, idOperation);
    }

    public ServiceException(SystemError sError) {
        super(sError, "", "");
    }

    public ServiceException(SystemError sError, String idOperation) {
        super(sError, "", idOperation);
    }

    public ServiceException(SystemError sError, String description, String idOperation) {
        super(sError, description, idOperation);
    }

    public ServiceException() {

    }
    // </editor-fold>


}// class
