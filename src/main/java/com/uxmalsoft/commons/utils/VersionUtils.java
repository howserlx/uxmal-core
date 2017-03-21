/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * VersionUtils.java
 * Version Utilities
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

import java.util.Comparator;

import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Version Utilities
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class VersionUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">
    private static VersionComparator cmp;

    public enum CompareResult {
        NONE, LESSER, EQUAL, GREATER;
    }
    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private VersionUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">
    /**
     * Es menor v1 que v2
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isLesser(String v1, String v2) {
        boolean res = false;
        try {
            if (compareVersions(v1, v2) == CompareResult.LESSER)
                res = true;
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// isLesser


    /**
     * Es mayor v1 que v2
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isGreater(String v1, String v2) {
        boolean res = false;
        try {
            if (compareVersions(v1, v2) == CompareResult.GREATER)
                res = true;
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// isGreater


    /**
     * Es igual v1 que v2
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isEqual(String v1, String v2) {
        boolean res = false;
        try {
            if (compareVersions(v1, v2) == CompareResult.EQUAL)
                res = true;
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// isEqual


    /**
     * Compara la version v1 respecta a la version v2
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static CompareResult compareVersions(String v1, String v2) {
        CompareResult res = CompareResult.NONE;
        try {
            cmp = new VersionComparator();
            int result = cmp.compare(v1, v2);

            if (result < 0) {
                res = CompareResult.LESSER;
            } else if (result > 0) {
                res = CompareResult.GREATER;
            } else
                res = CompareResult.EQUAL;

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// compareVersions

    /**
     * Compara la version v1 respecta a la version v2
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static int compareVersions_Int(String v1, String v2) {
        int result = -1;
        try {
            cmp = new VersionComparator();
            result = cmp.compare(v1, v2);

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return result;
    }// compareVersions

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class


// <editor-fold defaultstate="collapsed" desc=" VersionComparator ">
class VersionComparator implements Comparator {

    public boolean equals(Object o1, Object o2) {
        return compare(o1, o2) == 0;
    }

    public int compare(Object o1, Object o2) {
        String version1 = (String) o1;
        String version2 = (String) o2;

        VersionTokenizer tokenizer1 = new VersionTokenizer(version1);
        VersionTokenizer tokenizer2 = new VersionTokenizer(version2);

        int number1 = 0, number2 = 0;
        String suffix1 = "", suffix2 = "";

        while (tokenizer1.MoveNext()) {
            if (!tokenizer2.MoveNext()) {
                do {
                    number1 = tokenizer1.getNumber();
                    suffix1 = tokenizer1.getSuffix();
                    if (number1 != 0 || suffix1.length() != 0) {
                        // Version one is longer than number two, and non-zero
                        return 1;
                    }
                } while (tokenizer1.MoveNext());

                // Version one is longer than version two, but zero
                return 0;
            }

            number1 = tokenizer1.getNumber();
            suffix1 = tokenizer1.getSuffix();
            number2 = tokenizer2.getNumber();
            suffix2 = tokenizer2.getSuffix();

            if (number1 < number2) {
                // Number one is less than number two
                return -1;
            }
            if (number1 > number2) {
                // Number one is greater than number two
                return 1;
            }

            boolean empty1 = suffix1.length() == 0;
            boolean empty2 = suffix2.length() == 0;

            if (empty1 && empty2)
                continue; // No suffixes
            if (empty1)
                return 1; // First suffix is empty (1.2 > 1.2b)
            if (empty2)
                return -1; // Second suffix is empty (1.2a < 1.2)

            // Lexical comparison of suffixes
            int result = suffix1.compareTo(suffix2);
            if (result != 0)
                return result;

        }
        if (tokenizer2.MoveNext()) {
            do {
                number2 = tokenizer2.getNumber();
                suffix2 = tokenizer2.getSuffix();
                if (number2 != 0 || suffix2.length() != 0) {
                    // Version one is longer than version two, and non-zero
                    return -1;
                }
            } while (tokenizer2.MoveNext());

            // Version two is longer than version one, but zero
            return 0;
        }
        return 0;
    }
}// class VersionComparator
 // </editor-fold>


// <editor-fold defaultstate="collapsed" desc=" Version Tokenizer ">
class VersionTokenizer {
    private final String _versionString;
    private final int _length;

    private int _position;
    private int _number;
    private String _suffix;
    private boolean _hasValue;

    public int getNumber() {
        return _number;
    }

    public String getSuffix() {
        return _suffix;
    }

    public boolean hasValue() {
        return _hasValue;
    }

    public VersionTokenizer(String versionString) {
        if (versionString == null)
            throw new IllegalArgumentException("versionString is null");

        _versionString = versionString;
        _length = versionString.length();
    }

    public boolean MoveNext() {
        _number = 0;
        _suffix = "";
        _hasValue = false;

        // No more characters
        if (_position >= _length)
            return false;

        _hasValue = true;

        while (_position < _length) {
            char c = _versionString.charAt(_position);
            if (c < '0' || c > '9')
                break;
            _number = _number * 10 + (c - '0');
            _position++;
        }

        int suffixStart = _position;

        while (_position < _length) {
            char c = _versionString.charAt(_position);
            if (c == '.')
                break;
            _position++;
        }

        _suffix = _versionString.substring(suffixStart, _position);

        if (_position < _length)
            _position++;

        return true;
    }
}// class Version Tokenizer
 // </editor-fold>
