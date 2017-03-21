/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * Database.java
 * JDBC Database
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


/**
 * 
 * Database Params
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class Database {

    // ------------------------------------------------------
    // DBM
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="DBM">

    public enum DBM {
        POSTGRESQL ("PostgreSQL" ,"5432"  ,"org.postgresql.Driver"                       ,"jdbc:postgresql://"         ,""),
        MYSQL      ("MySQL"      ,"3306"  ,"com.mysql.jdbc.Driver"                       ,"jdbc:mysql://"              ,""),
        SQLSERVER  ("SQL Server" ,"1433"  ,"com.microsoft.jdbc.sqlserver.SQLServerDriver","jdbc:microsoft:sqlserver://",""),
        ORACLE     ("Oracle"     ,"1521"  ,"oracle.jdbc.OracleDriver"                    ,"jdbc:oracle:thin:@"         ,"xe"),
        ;
        
        private final String name;
        private final String defaultPort;
        private final String driver;
        private final String connString;
        private final String defaultService;

        public String getName() {
            return name;
        }

        public String getDefaultPort() {
            return defaultPort;
        }

        public String getDriver() {
            return driver;
        }

        public String getConnString() {
            return connString;
        }

        public String getDefaultService() {
            return defaultService;
        }

        private DBM(String name, String defaultPort, String driver, String connString, String defaultService) {
            this.name = name;
            this.defaultPort = defaultPort;
            this.driver = driver;
            this.connString = connString;
            this.defaultService = defaultService;
        }// constructor

    }// enum

    // </editor-fold>


    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>


    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public Database() {

    }// empty
     // </editor-fold>


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Construuye una Cadena de Conexion de acuerdo al tipo
     * 
     * @param dbm
     * @param host
     * @param port
     * @param dbName
     * @return
     */
    public static String getConnString(Database.DBM dbm, String host, String port, String dbName) {
        return getConnString(dbm, host, port, dbName, null);
    }// getConnString


    /**
     * Construuye una Cadena de Conexion de acuerdo al tipo
     * 
     * @param dbm
     * @param host
     * @param port
     * @param dbName
     * @param service
     * @return
     */
    public static String getConnString(Database.DBM dbm, String host, String port, String dbName, String service) {
        String connStr = "";

        if (dbm != null && host != null) {

            if ((port == null) || port.isEmpty())
                port = dbm.getDefaultPort();

            if ((service == null) || service.isEmpty())
                service = dbm.getDefaultService();

            switch (dbm) {
                case POSTGRESQL:// jdbc:postgresql://myhost:5432/database
                    connStr = dbm.getConnString() + host + ":" + port + "/" + dbName;
                    break;

                case MYSQL: // jdbc:mysql://myhost:3306/database
                    connStr = dbm.getConnString() + host + ":" + port + "/" + dbName;
                    break;

                case SQLSERVER: // jdbc:microsoft:sqlserver://myhost:1433;DatabaseName=database
                    connStr = dbm.getConnString() + host + ":" + port + ";DatabaseName=" + dbName;
                    break;

                case ORACLE: // jdbc:oracle:thin:@myhost:1521:database
                    connStr = dbm.getConnString() + host + ":" + port + ":" + service;
                    break;

                default:

            }// switch

        } // !null

        return connStr;
    }// getConnString



    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
