/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * DBConnection.java
 * Crea conexion JDBC de Base de Datos
 *
 * Created on : 09/10/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.db;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import com.uxmalsoft.commons.exception.persistence.PersistenceException;
import com.uxmalsoft.commons.io.Console;
import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Crea conexion jbdc de Base de Datos
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class DBConnection {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public DBConnection() {

    }// empty
     // </editor-fold>

    // ------------------------------------------------------
    // Getters & Setters
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    // </editor-fold>

    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Construye una conexion jbdc
     * 
     * @param dbm
     * @param host
     * @param port
     * @param dbName
     * @param user
     * @param pass
     * @return
     */
    public static Connection getConnection(Database.DBM dbm, String host, String port, String dbName, String user,
            String pass) {
        try {
            if (dbm != null && host != null && dbName != null && user != null && pass != null) {
                Properties prop = new Properties();
                if (!user.isEmpty())
                    prop.setProperty("user", user);
                if (!pass.isEmpty())
                    prop.setProperty("password", pass);

                host = Database.getConnString(dbm, host, port, dbName);

                Class.forName(dbm.getDriver()).newInstance();
                Connection conn = DriverManager.getConnection(host, prop);

                Console.writeln("Estableciendo conexión " + dbm.getName() + " host:" + host + "user:" + user, false);
                return conn;
            } // !null
        } catch (Exception ex) {
            SystemLog.writeException(ex);
        }
        return null;
    }// getConnection


    /**
     * Construye una conexion jbdc
     * 
     * @param dbm
     * @param host
     * @param port
     * @param dbName
     * @param service
     * @param user
     * @param pass
     * @return
     */
    public static Connection getConnection(Database.DBM dbm, String host, String port, String dbName, String service,
            String user,
            String pass) {
        try {
            if (dbm != null && host != null && dbName != null && user != null && pass != null) {
                Properties prop = new Properties();
                if (!user.isEmpty())
                    prop.setProperty("user", user);
                if (!pass.isEmpty())
                    prop.setProperty("password", pass);

                host = Database.getConnString(dbm, host, port, dbName, service);

                Class.forName(dbm.getDriver()).newInstance();
                Connection conn = DriverManager.getConnection(host, prop);

                Console.writeln("Estableciendo conexión " + dbm.getName() + " host:" + host + "user:" + user, false);
                return conn;
            } // !null
        } catch (Exception ex) {
            SystemLog.writeException(ex);
        }
        return null;
    }// getConnection


    /**
     * Crea un Statement de Database
     * 
     * @param conn
     * @return
     * @throws PersistenceException
     */
    public static Statement createStatement(Connection conn) throws PersistenceException {
        Statement st = null;
        try {
            st = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return st;
        } catch (Exception ex) {
            throw new PersistenceException("Error Creating Statement");
        }
    }// createStatement


    /**
     * Crea un Statement de Database
     * 
     * @param conn
     * @param resultSetType
     * @param concurrency
     * @return
     * @throws PersistenceException
     */
    public static Statement createStatement(Connection conn, int resultSetType, int concurrency) throws PersistenceException {
        Statement st = null;
        try {
            st = conn.createStatement(resultSetType, concurrency);
            return st;
        } catch (Exception ex) {
            throw new PersistenceException("Error Creating Statement");
        }
    }// createStatement


    /**
     * Cierra Statement
     * 
     * @param st
     */
    public static void closeStatement(Statement st) {
        try {
            st.close();
        } catch (Exception ex) {
            SystemLog.writeException(ex);
        }
    }// closeStatement

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
