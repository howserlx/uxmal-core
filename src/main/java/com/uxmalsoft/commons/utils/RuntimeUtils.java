/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * RuntimeUtils.java
 * Runtime Utilities
 *
 * Created on : 01/06/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.utils;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Runtime Utils
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class RuntimeUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private RuntimeUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------

    /**
     * Ejecuta un comando
     * 
     * @param command
     * @return
     */
    public static synchronized String executeCommand(String command) {
        return executeCommand(command, true);
    }// executeCommand

    /**
     * Ejecuta un comando
     * 
     * @param command
     * @param waitFor
     * @return
     */
    public static synchronized String executeCommand(String command, boolean waitFor) {
        return executeCommand(command, waitFor, false);
    }// executeCommand

    /**
     * Ejecuta un comando
     * 
     * @param command
     * @param waitFor
     * @param returnErrorStream
     * @return
     */
    public static synchronized String executeCommand(String command, boolean waitFor, boolean returnErrorStream) {

        StringBuilder output = new StringBuilder();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            if (waitFor)
                p.waitFor();

            BufferedReader reader = null;

            if (returnErrorStream)
                reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            else
                reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = null;
            while ((line = reader.readLine()) != null) {
                if (isNotEmpty(line))
                    output.append(line).append("\n");
            }


        } catch (IOException | InterruptedException ex) {
            SystemLog.writeUtilsException(ex);
        }

        return output.toString();
    }// executeCommand

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
