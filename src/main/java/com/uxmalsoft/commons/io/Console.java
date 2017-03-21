/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * Console.java
 * Console
 *
 * Created on : 09/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.io;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Console
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class Console {


    // Constants
    public static final String NEWLINE_ = "" + ((char) 13) + ((char) 10);
    public static final String SEPARATOR_LINE = 
                  NEWLINE_ + 
                  "==================================================================================================================================="
                  + NEWLINE_;
    public static final String SINGLE_LINE =
                  "=====================================================================================";


    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private Console() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    public static void println(String msg) {
        println(msg, true);
    }

    public static void println(String msg, boolean logMsg) {
        writeIntoConsole(msg);
        if (logMsg)
            SystemLog.writeInfo(msg);
    }


    public static void writeln(String msg) {
        writeln(msg, true);
    }

    public static void writeln(String msg, boolean logMsg) {
        writeIntoConsole(msg);
        if (logMsg)
            SystemLog.writeInfo(msg);
    }


    public static void writeInfo(String msg) {
        writeInfo(msg, true);
    }

    public static void writeInfo(String msg, boolean logMsg) {
        writeIntoConsole(msg);
        if (logMsg)
            SystemLog.writeInfo(msg);
    }


    public static void writeError(String msg) {
        writeError(msg, true);
    }

    public static void writeError(String msg, boolean logMsg) {
        writeIntoConsole(msg);
        if (logMsg)
            SystemLog.writeError(msg);
    }


    public static void writeDebug(String msg) {
        writeDebug(msg, true);
    }

    public static void writeDebug(String msg, boolean logMsg) {
        if (SystemLog.logDebug) {
            writeIntoConsole(msg);
            if (logMsg)
                SystemLog.writeDebug(msg);
        }
    }

    public static synchronized void writeIntoConsole(String msg) {
        System.out.println(msg);
    }// writeIntoConsole


}// class
