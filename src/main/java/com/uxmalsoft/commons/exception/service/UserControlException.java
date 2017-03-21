/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * UserControlException.java
 * UserControl Exception
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

/**
 * 
 * UserControl Exception
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class UserControlException extends ServiceException {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------


    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public UserControlException(String description) {
        super(null, description, "");
    }

    public UserControlException(String description, String idOperation) {
        super(null, description, idOperation);
    }

    public UserControlException(SystemError sError) {
        super(sError, "", "");
    }

    public UserControlException(SystemError sError, String idOperation) {
        super(sError, "", idOperation);
    }

    public UserControlException(SystemError sError, String description, String idOperation) {
        super(sError, description, idOperation);
    }

    public UserControlException() {

    }
    // </editor-fold>


}// class
