/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * SystemLog.java
 * System Log
 *
 * Created on : 12/10/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References : log4j-api-2.4, log4j-core-2.4
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.logging;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.io.File;
import java.util.Date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import com.uxmalsoft.commons.exception.SystemException;
import com.uxmalsoft.commons.io.Console;
import com.uxmalsoft.commons.utils.FileNameUtils;

/**
 * 
 * Generic System Log
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class SystemLog {

    /*
     * Estos niveles se usan en los Sistemas y deben ser independientes
     * a los niveles de log4j por si se cambia de tecnologia
     */
    public enum LogLevel {
        FATAL, ERROR, WARNING, INFO, DEBUG, TRACE;
    }

    public enum LogType {

        SYSTEM  ("SYSTEM_LOGGER", true), // Errors,Exceptions
        ERROR   ("ERROR_LOGGER" , true), // Events, Notifications
        DEBUG   ("DEBUG_LOGGER" , true), // Engineering Use

        USER    ("USER_LOGGER"  , true), // User activity
        DATABASE("DB_LOGGER"    , true), // Scripts, Queries, Engineering Use
        ALERT   ("ALERT_LOGGER" , true), // Sended Alerts for Users
        MAIL    ("MAIL_LOGGER"  , true), // Sended mails
        SMS     ("SMS_LOGGER"   , true), // Sended sms

        CONSOLE ("stdout"       , true), // stdout
        ;
        
        private final String logName;
        private final boolean enabled;

        private LogType(String name, boolean enabled) {
            this.logName = name;
            this.enabled = enabled;
        }

        public String getLogName() {
            return logName;
        }

        public boolean isEnabled() {
            return enabled;
        }

    }// enum


    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold desc="Attributes">
    public static final String DEFAULT_LOG_CONFIG_FILE = "log4j2.xml";
    public static final String DEFAULT_LOG_CONFIG_PATH = "./conf/" + DEFAULT_LOG_CONFIG_FILE;

    // Log Levels
    private static boolean logException = true;
    private static boolean logError     = true;
    private static boolean logWarning   = true;
    private static boolean logInfo      = true;
    public  static boolean logDebug     = true; // public
    private static boolean logTrace     = true;

    private static boolean logUtilException = false; // Las excepciones de utils no se contemplan

    // Log Types
    private static Logger systemLogger; // Fatal,Error,Warning,Info
    private static Logger errorLogger;  // Fatal,Error
    private static Logger debugLogger;  // Debug

    private static Logger console;      // Console

    private static Logger userLogger;   // Custom
    private static Logger dbLogger;     // Custom
    private static Logger alertLogger;  // Custom
    private static Logger mailLogger;   // Custom
    private static Logger smsLogger;    // Custom
    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    private SystemLog() {
        // Only public static synchronized functions
    }// empty


    /**
     * Inicializa la configuración de Logs
     * 
     * @param configFilePath
     * @param debugEnabled
     * @return
     */
    public static synchronized boolean initialize(String configFilePath, boolean debugEnabled) {
        return initialize(configFilePath, debugEnabled, true);
    }// initialize

    /**
     * Inicializa la configuración de Logs
     * 
     * @param configFilePath
     * @param debugEnabled
     * @param logUtil
     * @return
     */
    public static synchronized boolean initialize(String configFilePath, boolean debugEnabled, boolean logUtil) {
        boolean res = false;
        configFilePath = validateNull(configFilePath);
        configFilePath = (configFilePath.isEmpty()) ? DEFAULT_LOG_CONFIG_PATH : configFilePath;


        // Load flags
        logException = true;
        logError     = true;
        logWarning   = true;
        logInfo      = true;
        logDebug     = debugEnabled;
        logTrace     = true;
        logUtilException = logUtil;

        try {
            // @config
            LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
            File file = new File(FileNameUtils.normalizeFilePath(configFilePath));
            context.setConfigLocation(file.toURI());



            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            Configuration config = ctx.getConfiguration();
            LoggerConfig loggerConfig = config.getLoggerConfig(LogType.SYSTEM.getLogName());
            loggerConfig.setLevel(Level.DEBUG);

            // Logger
            systemLogger = LogManager.getLogger(LogType.SYSTEM.getLogName());
            errorLogger  = LogManager.getLogger(LogType.ERROR.getLogName());
            debugLogger  = LogManager.getLogger(LogType.DEBUG.getLogName());

            console      = LogManager.getLogger(LogType.CONSOLE.getLogName());

            userLogger   = LogManager.getLogger(LogType.USER.getLogName());
            dbLogger     = LogManager.getLogger(LogType.DATABASE.getLogName());
            alertLogger  = LogManager.getLogger(LogType.ALERT.getLogName());
            mailLogger   = LogManager.getLogger(LogType.MAIL.getLogName());
            smsLogger    = LogManager.getLogger(LogType.SMS.getLogName());

            res = true;
            Console.println("System Logs loaded.....", false);


        } catch (Exception ex) {
            Console.println("" + ex, false);
        } finally {

        }
        return res;
    }// config

    // </editor-fold>


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Enable debugMode
     */
    public static void enableDebugMode() {
        logDebug = true;
    }// enableDebugMode

    /**
     * Disable debugMode
     */
    public static void disableDebugMode() {
        logDebug = false;
    }// disableDebugMode

    /**
     * Prepara el Mensaje para escritura en Log
     * 
     * @param msg
     * @param idOperation
     * @param Throwable ex
     * @param includeStackTrace
     * @return
     */
    private static synchronized String prepareMsg(String msg, String idOperation, Throwable ex,
            boolean includeStackTrace) {
        if (idOperation == null)
            idOperation = "";
        if (msg == null)
            msg = "";


        idOperation = "[" + idOperation + "]";
        StringBuilder sb = new StringBuilder();
        sb.append(idOperation);
        if (!msg.isEmpty()) {
            sb.append(" - ");
            sb.append(msg);
        }

        if (ex != null && includeStackTrace) {
            StackTraceElement[] strElements = ex.getStackTrace();
            if (strElements != null) {
                sb.append(Console.NEWLINE_)
                        .append("-----------------------------------------------------------------------------------------------------")
                        .append(Console.NEWLINE_);

                for (StackTraceElement strElement : strElements) {
                    sb.append("   ").append(idOperation);
                    sb.append(" - Exception Method: [").append(strElement.getClassName()).append(":")
                            .append(strElement.getMethodName()).append("]-");
                    sb.append("[").append(strElement.getLineNumber()).append("]").append(Console.NEWLINE_);
                }
                sb.append(
                        "-----------------------------------------------------------------------------------------------------")
                        .append(Console.NEWLINE_);

            }

        } // print Exception StackTrace

        return sb.toString();
    }// prepareMsg

    // </editor-fold>


    // ------------------------------------------------------
    // System Logs (System, Error, Debug)
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="System Logs (System, Error, Debug)">
    // INFO
    public static synchronized void writeInfo(String msg) {
        writeInfo(msg, "");
    }

    public static synchronized void writeInfo(String msg, String idOperation) {
        writeEntry(msg, idOperation, LogLevel.INFO, null);
    }

    // DEBUG
    public static synchronized void writeDebug(String msg) {
        writeDebug(msg, "");
    }

    public static synchronized void writeDebug(String msg, String idOperation) {
        writeEntry(msg, idOperation, LogLevel.DEBUG, null);
    }

    // WARNING
    public static synchronized void writeWarning(String msg) {
        writeWarning(msg, "");
    }

    public static synchronized void writeWarning(String msg, String idOperation) {
        writeEntry(msg, idOperation, LogLevel.WARNING, null);
    }

    // ERROR
    public static synchronized void writeError(String msg) {
        writeError(msg, "");
    }

    public static synchronized void writeError(String msg, String idOperation) {
        writeEntry(msg, idOperation, LogLevel.ERROR, null);
    }

    // EXCEPTION/FATAL
    public static synchronized void writeException(Throwable ex) {
        writeException("", "", ex, false);
    }

    public static synchronized void writeException(Throwable ex, String idOperation) {
        writeException("", idOperation, ex, false);
    }

    public static synchronized void writeUtilsException(Throwable ex) {
        writeException("", "", ex, true);
    }

    // (@private)
    private static synchronized void writeException(String msg, String idOperation, Throwable ex, boolean isFromUtils) {
        if (!logUtilException && isFromUtils)
            return;
        writeEntry(msg, idOperation, LogLevel.FATAL, ex);
    }

    /**
     * Log an entry (@private)
     * 
     * @param msg
     * @param level
     * @param ex Throwable
     */
    private static synchronized void writeEntry(String msg, LogLevel level, Throwable ex) {
        writeEntry(msg, "", level, ex);
    }// writeEntry"


    /**
     * Procesa los mensajes para escribirlos en archivo (@private)
     * Solo reparte los mensajes entre los 3 log del sistema (_System,_Error,_Debug)
     * 
     * @param msg
     * @param idOperation
     * @param level
     * @param ex Throwable
     */
    private static synchronized void writeEntry(String msg, String idOperation, LogLevel level, Throwable ex) {
        switch (level) {
            case FATAL:
                if (logException) {
                    msg = validateNull(msg);

                    if (ex != null && msg.isEmpty()) {
                        if (ex instanceof SystemException)
                            msg = ((SystemException) ex).getErrorMessage();
                        else {
                            msg = ex.getMessage();
                            if (msg == null || msg.isEmpty())
                                msg = ex.toString();
                        }
                    }

                    String m = prepareMsg(msg, idOperation, ex, false); // Ex stackTrace
                    msg = prepareMsg(msg, idOperation, ex, true);

                    writeEntryToFile(m, systemLogger, org.apache.logging.log4j.Level.FATAL);
                    writeEntryToFile(msg, errorLogger, org.apache.logging.log4j.Level.FATAL);
                }
                break;

            case ERROR:
                if (logError) {
                    msg = prepareMsg(msg, idOperation, ex, false);
                    writeEntryToFile(msg, systemLogger, org.apache.logging.log4j.Level.ERROR);
                    writeEntryToFile(msg, errorLogger, org.apache.logging.log4j.Level.ERROR);
                }
                break;

            case WARNING:
                if (logWarning) {
                    msg = prepareMsg(msg, idOperation, ex, false);
                    writeEntryToFile(msg, systemLogger, org.apache.logging.log4j.Level.WARN);
                }
                break;

            case INFO:
                if (logInfo) {
                    msg = prepareMsg(msg, idOperation, ex, false);
                    writeEntryToFile(msg, systemLogger, org.apache.logging.log4j.Level.INFO);
                }
                break;

            case DEBUG:
                if (logDebug) {
                    msg = prepareMsg(msg, idOperation, ex, false);
                    writeEntryToFile(msg, debugLogger, org.apache.logging.log4j.Level.DEBUG);

                }
                break;

            case TRACE:
                if (logTrace) {

                }
                break;

            default:

        }// switch
    }// writeEntry


    /**
     * Agrega un msg en un Logger (archivo) (@private)
     * 
     * @param msg
     * @param logger
     * @param level
     */
    private static synchronized void writeEntryToFile(String msg, Logger logger, org.apache.logging.log4j.Level level) {

        if (logger != null) {
            if (level == org.apache.logging.log4j.Level.FATAL)
                logger.fatal(msg);

            if (level == org.apache.logging.log4j.Level.ERROR)
                logger.error(msg);

            if (level == org.apache.logging.log4j.Level.WARN)
                logger.warn(msg);

            if (level == org.apache.logging.log4j.Level.INFO)
                logger.info(msg);

            if (level == org.apache.logging.log4j.Level.DEBUG)
                logger.debug(msg);

            if (level == org.apache.logging.log4j.Level.TRACE)
                logger.trace(msg);
        } // logger!=null

    }// writeEntryToFile

    // </editor-fold>


    // ------------------------------------------------------
    // Special Logs
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Special Logs">
    // USER
    /**
     * Log Actividad de Usuario
     * 
     * @param user
     * @param msg
     */
    public static synchronized void writeIntoUserLog(String user, String msg) {
        writeIntoUserLog(user, msg, "", "");
    }

    /**
     * Log Actividad de Usuario
     * 
     * @param user
     * @param msg
     * @param idOperation
     * @param module
     */
    public static synchronized void writeIntoUserLog(String user, String msg, String idOperation, String module) {
        if (logInfo && isNotEmpty(msg)) {
            idOperation = validateNull(idOperation);
            user = validateNull(user);

            StringBuilder sb = new StringBuilder();
            sb.append("[").append(user).append("] - ");

            if (!idOperation.isEmpty())
                sb.append("[").append(idOperation).append("] - ");

            sb.append(msg);

            writeEntryToFile(sb.toString(), userLogger, org.apache.logging.log4j.Level.INFO);
        }
    }// writeIntoUserLog


    // DATABASE
    /**
     * Log de scripts/db
     * 
     * @param table Module/Table
     * @param msg
     */
    public static synchronized void writeIntoDatabaseLog(String table, String msg) {
        writeIntoDatabaseLog(table, msg, "");
    }

    /**
     * Log de scripts/db
     * 
     * @param table
     * @param msg
     * @param idOperation
     */
    public static synchronized void writeIntoDatabaseLog(String table, String msg, String idOperation) {
        if (logDebug && isNotEmpty(msg)) {
            idOperation = validateNull(idOperation);
            table = validateNull(table);

            StringBuilder sb = new StringBuilder();
            sb.append("[").append(table).append("] - ");

            if (!idOperation.isEmpty())
                sb.append("[").append(idOperation).append("] - ");

            sb.append(msg);

            writeEntryToFile(sb.toString(), dbLogger, org.apache.logging.log4j.Level.DEBUG);
        }
    }// writeIntoDatabaseLog


    // ALERT
    /**
     * Alerta del Sistema
     * 
     * @param alertLevel
     * @param alertType
     * @param msg
     */
    public static synchronized void writeIntoAlertLog(String alertLevel, String alertType, String msg) {
        writeIntoAlertLog(alertLevel, alertType, msg, "");
    }

    /**
     * Alerta del Sistema
     * 
     * @param alertLevel
     * @param alertType
     * @param msg
     * @param idOperation
     */
    public static synchronized void writeIntoAlertLog(String alertLevel, String alertType, String msg,
            String idOperation) {
        if (logInfo && isNotEmpty(msg)) {
            idOperation = validateNull(idOperation);
            alertLevel = validateNull(alertLevel);
            alertType = validateNull(alertType);


            StringBuilder sb = new StringBuilder();
            sb.append("[").append(alertLevel).append("] - ");

            if (!alertType.isEmpty())
                sb.append("[").append(alertType).append("] - ");

            if (!idOperation.isEmpty())
                sb.append("[").append(idOperation).append("] - ");

            sb.append(msg);

            writeEntryToFile(sb.toString(), alertLogger, org.apache.logging.log4j.Level.INFO);
        }
    }// writeIntoAlertLog


    // MAIL
    /**
     * Mails enviado
     * 
     * @param mails
     * @param msg Tal y como se envio
     */
    public static synchronized void writeIntoMailLog(String[] mails, String msg) {
        writeIntoMailLog(mails, msg, "");
    }

    /**
     * Mails enviado
     * 
     * @param mails
     * @param msg
     * @param idOperation
     */
    public static synchronized void writeIntoMailLog(String[] mails, String msg, String idOperation) {
        if (logInfo && isNotEmpty(msg)) {
            idOperation = validateNull(idOperation);

            StringBuilder sb = new StringBuilder();

            if (!idOperation.isEmpty())
                sb.append("[").append(idOperation).append("] - ");



            if (mails != null && mails.length > 0) {
                sb.append("[");
                for (String mail : mails) {
                    sb.append(mail);
                    sb.append(" ");
                } // fore
                sb.append("]");
                sb.append(Console.NEWLINE_);
            }

            sb.append(msg);

            writeEntryToFile(sb.toString(), mailLogger, org.apache.logging.log4j.Level.INFO);
        }
    }// writeIntoMailLog


    // SMS
    /**
     * SMS enviados
     * 
     * @param phones
     * @param msg Tal y como se envio
     */
    public static synchronized void writeIntoSMSLog(String[] phones, String msg) {
        writeIntoSMSLog(phones, msg, "");
    }

    /**
     * SMS enviados
     * 
     * @param phones
     * @param msg
     * @param idOperation
     */
    public static synchronized void writeIntoSMSLog(String[] phones, String msg, String idOperation) {
        if (logInfo && isNotEmpty(msg)) {
            idOperation = validateNull(idOperation);

            StringBuilder sb = new StringBuilder();

            if (!idOperation.isEmpty())
                sb.append("[").append(idOperation).append("] - ");

            if (phones != null && phones.length > 0) {

                sb.append("[");
                for (String phone : phones) {
                    sb.append(phone);
                    sb.append(" ");
                } // fore
                sb.append("]");
                sb.append(Console.NEWLINE_);
            }

            sb.append(msg);

            writeEntryToFile(sb.toString(), smsLogger, org.apache.logging.log4j.Level.INFO);
        }
    }// writeIntoSMSLog
     // </editor-fold>


    // ------------------------------------------------------
    // Title
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Title">

    /**
     * Coloca el Titulo en un Log
     * 
     * @param msg
     * @param type
     */
    public static synchronized void setLogTitle(String msg, LogType type) {
        StringBuilder sb = new StringBuilder();
        sb.append(Console.SEPARATOR_LINE);
        sb.append("    ").append(msg).append(" : ").append(new Date().toString());
        sb.append(Console.SEPARATOR_LINE);

        msg = sb.toString();
        org.apache.logging.log4j.Level level = org.apache.logging.log4j.Level.INFO;
        Logger logger = null;

        switch (type) {
            case SYSTEM:
                logger = systemLogger;
                break;
            case ERROR:
                logger = errorLogger;
                break;
            case DEBUG:
                logger = debugLogger;
                break;
            case USER:
                logger = userLogger;
                break;
            case DATABASE:
                logger = dbLogger;
                break;
            case ALERT:
                logger = alertLogger;
                break;
            case MAIL:
                logger = mailLogger;
                break;
            case SMS:
                logger = smsLogger;
                break;
            default:

        }// switch

        writeEntryToFile(msg, logger, level);

    }// setLogTitle


    // </editor-fold>

}// class
