/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * TimeUtils.java
 * Time/Date Utilities
 *
 * Created on : 08/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.utils;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import java.util.Calendar;
import java.util.Date;

import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Utilidades de Tiempo
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class TimeUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    public static final long SECONDS_MILLI = 1000;
    public static final long MINUTES_MILLI = SECONDS_MILLI * 60;
    public static final long HOURS_MILLI   = MINUTES_MILLI * 60;
    public static final long DAYS_MILLI    = HOURS_MILLI * 24;
    
    public final static String DEFAULT_DATE_FORMAT      = "dd/MM/yyyy";
    public final static String DEFAULT_TIME_FORMAT      = "HH:mm:ss";
    public final static String DEFAULT_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    
    public final static String SQL_DATE_FORMAT = "yyyyMMdd";
    public final static String SQL_TIME_FORMAT = "yyyyMMdd HH:mm:ss";

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private TimeUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Determina el tiempo transcurrido (days,hours,minutes,secs) entre 2 fechas
     * 
     * @param startDate
     * @param endDate
     * @return String con tiempo transcurrido
     */
    public static String getElapsedDaysTime_hhMMss(Date startDate, Date endDate) {
        return getElapsedDaysTime_hhMMss(startDate, endDate, false);
    }// getElapsedTime_hhMMss

    /**
     * Determina el tiempo transcurrido (days,hours,minutes,secs,ms) entre 2 fechas
     * 
     * @param startDate
     * @param endDate
     * @param includeMs incluye milisegundos?
     * @return String con tiempo transcurrido
     */
    public static String getElapsedDaysTime_hhMMss(Date startDate, Date endDate, boolean includeMs) {
        return getElapsedTime_ddhhMMss(startDate, endDate, true, includeMs, ":");
    }// getElapsedTime_hhMMss

    /**
     * Determina el tiempo transcurrido (hours,minutes,secs) entre 2 fechas
     * 
     * @param startDate
     * @param endDate
     * @return String con tiempo transcurrido
     */
    public static String getElapsedTime_hhMMss(Date startDate, Date endDate) {
        return getElapsedTime_hhMMss(startDate, endDate, false);
    }// getElapsedTime_hhMMss

    /**
     * Determina el tiempo transcurrido (hours,minutes,secs,ms) entre 2 fechas
     * 
     * @param startDate
     * @param endDate
     * @param includeMs incluye milisegundos?
     * @return String con tiempo transcurrido
     */
    public static String getElapsedTime_hhMMss(Date startDate, Date endDate, boolean includeMs) {
        return getElapsedTime_ddhhMMss(startDate, endDate, false, includeMs, ":");
    }// getElapsedTime_hhMMss


    /**
     * Determina el tiempo transcurrido (days,hours,minutes,secs,ms) entre 2 fechas
     * 
     * @param startDate
     * @param endDate
     * @param insertDays incluye dias?
     * @param includeMs incluye milisegundos?
     * @param separator Caracter separador
     * @return String con tiempo transcurrido
     */
    public static String getElapsedTime_ddhhMMss(Date startDate, Date endDate, boolean insertDays, boolean includeMs,
            String separator) {
        StringBuilder sb = new StringBuilder();

        separator = (separator == null) ? ":" : separator;
        separator = (separator.isEmpty()) ? ":" : separator;

        try {
            int[] arr = getElapsedTime(startDate, endDate);

            if (arr.length >= 5) {
                if (insertDays)
                    sb.append(arr[0]).append(separator); // dias

                sb.append(StringUtils.fillString("" + arr[1], 2, '0')).append(separator); // hr
                sb.append(StringUtils.fillString("" + arr[2], 2, '0')).append(separator); // min
                sb.append(StringUtils.fillString("" + arr[3], 2, '0')); // seg

                if (includeMs)
                    sb.append(".").append(StringUtils.fillString("" + arr[4], 3, '0')); // ms
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return sb.toString();
    }// getElapsedTime_ddhhMMss


    /**
     * Descripcion de Tiempo transcurrido
     * 
     * @param startDate
     * @param endDate
     * @return String con descripcion de Tiempo transcurrido para print
     */
    public static String getElapsedTime_description(Date startDate, Date endDate) {
        StringBuilder sb = new StringBuilder();

        try {
            sb.append("\n  Start:").append(startDate.toString()).append("\n");
            sb.append(" Finish:").append(endDate.toString()).append("\n");
            sb.append("----------------------------").append("\n");

            int[] arr = getElapsedTime(startDate, endDate);
            if (arr.length >= 5) {

                sb.append("        Days:").append(arr[0]).append("\n"); // dias
                sb.append("       Hours:").append(StringUtils.fillString("" + arr[1], 2, '0')).append("\n"); // hr
                sb.append("     Minutes:").append(StringUtils.fillString("" + arr[2], 2, '0')).append("\n"); // min
                sb.append("     Seconds:").append(StringUtils.fillString("" + arr[3], 2, '0')).append("\n"); // seg
                sb.append("Milliseconds:").append(StringUtils.fillString("" + arr[4], 3, '0')).append("\n"); // ms
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return sb.toString();
    }// getElapsedTime_description

    /**
     * Determina el tiempo transcurrido (days,hours,minutes,secs, ms) entre 2 fechas
     * 
     * @param startDate
     * @param endDate
     * @return int[]
     */
    public static int[] getElapsedTime(Date startDate, Date endDate) {

        int[] arr = new int[5];

        try {
            // milliseconds
            long difference = endDate.getTime() - startDate.getTime();

            long elapsedDays = difference / DAYS_MILLI;
            difference = difference % DAYS_MILLI;

            long elapsedHours = difference / HOURS_MILLI;
            difference = difference % HOURS_MILLI;

            long elapsedMinutes = difference / MINUTES_MILLI;
            difference = difference % MINUTES_MILLI;

            long elapsedSeconds = difference / SECONDS_MILLI;
            difference = difference % SECONDS_MILLI;

            arr[0] = (int) elapsedDays;
            arr[1] = (int) elapsedHours;
            arr[2] = (int) elapsedMinutes;
            arr[3] = (int) elapsedSeconds;
            arr[4] = (int) difference;
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return arr;
    }// getElapsedTime

    /**
     * Determina la edad en años
     * 
     * @param birthDate
     * @return int
     */
    public static int determineAgeInYears(Date birthDate) {
        int years = 0;
        int months = 0;
        int days = 0;

        try {
            // create calendar object for birth day
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTimeInMillis(birthDate.getTime());

            // create calendar object for current day
            long currentTime = System.currentTimeMillis();
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(currentTime);

            // Get difference between years
            years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            int currMonth = now.get(Calendar.MONTH) + 1;
            int birthMonth = birthDay.get(Calendar.MONTH) + 1;

            // Get difference between months
            months = currMonth - birthMonth;
            // if month difference is in negative then reduce years by one and calculate the number of months.
            if (months < 0) {
                years--;
                months = 12 - birthMonth + currMonth;
                if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                    months--;
            } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                years--;
                months = 11;
            }
            // Calculate the days
            if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
                days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
            else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                int today = now.get(Calendar.DAY_OF_MONTH);
                now.add(Calendar.MONTH, -1);
                days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
            } else {
                days = 0;
                if (months == 12) {
                    years++;
                    months = 0;
                }
            }
            // Create new Age object
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
            years = 0;
        }
        return years;
    }// determineAgeInYears


    /**
     * Convierte una Cadena a una Fecha
     * 
     * @param str
     * @return Date
     */
    public static Date stringToDate(String str) {
        return stringToDate(str, DEFAULT_DATE_TIME_FORMAT);
    }// stringToDate


    /**
     * Convierte una Cadena a una Fecha
     * 
     * @param str
     * @param format
     * @return Date
     */
    public static Date stringToDate(String str, String format) {
        return ConversionUtils.stringToDate(str, format);
    }// convertStringToDate


    /**
     * 
     * @param format
     * @return
     */
    public static String getNow_asString(String format) {
        return formatDate(new Date(), format);
    }// getNow_asString


    /**
     * Regresa un String con el formato de una Fecha
     * 
     * @param calendar Calendar
     * @return String
     */
    public static String formatDate(Calendar calendar) {
        return ConversionUtils.formatDate(calendar, DEFAULT_DATE_FORMAT);
    }// formatDate


    /**
     * Regresa un String con el formato de una Fecha
     * 
     * @param calendar Calendar
     * @param format String
     * @return String
     */
    public static String formatDate(Calendar calendar, String format) {
        return ConversionUtils.formatDate(calendar, format);
    }// formatDate


    /**
     * Regresa un String con el formato de una Fecha
     * 
     * @param date Date
     * @return String
     */
    public static String formatDate(Date date) {
        return ConversionUtils.formatDate(date, DEFAULT_DATE_FORMAT);
    }// formatDate

    /**
     * Regresa un String con el formato de una Fecha
     * 
     * @param date Date
     * @param format String
     * @return String
     */
    public static String formatDate(Date date, String format) {
        return ConversionUtils.formatDate(date, format);
    }// formatDate

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">
    /**
     * Description
     * 
     * @param param1
     * @param param2
     */
    public void aMethod(Object param1, Object param2) {
        try {

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
    }// aMethod

    /**
     * Description
     * 
     * @param param1
     * @param param2
     * @return
     */
    public boolean aReturnMethod(Object param1, Object param2) {
        boolean res = false;
        try {
            // TO DO
            // res sets to ´false' value is a good programming practice
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// aReturnMethod
     // </editor-fold>

}// class
