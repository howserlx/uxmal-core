/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * CollectionUtils.java
 * Collection Utilities
 *
 * Created on : 02/07/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References :
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.utils;

import static com.uxmalsoft.commons.utils.StringUtils.isNotEmpty;
import static com.uxmalsoft.commons.utils.StringUtils.validateNull;

import org.apache.commons.lang3.ArrayUtils;

import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Utilidades de Colecciones
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */

public class CollectionUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private CollectionUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">

    /**
     * Convierte un Array a un tamaño fijo y rellena los espacios faltantes con value
     * por la izquierda
     * 
     * @param arr
     * @param size
     * @param value
     * @return
     */
    public static Object[] normalizeArray_left(Object[] arr, int size, Object value) {
        ArrayUtils.reverse(arr);
        arr = normalizeArray(arr, size, value);
        ArrayUtils.reverse(arr);

        return arr;
    }// normalizeArray_left

    /**
     * Convierte un Array a un tamaño fijo y rellena los espacios faltantes con value
     * 
     * @param arr
     * @param size
     * @param value
     * @return
     */
    public static Object[] normalizeArray(Object[] arr, int size, Object value) {
        if (size < 1)
            size = 1;

        Object[] res = new Object[size];
        try {
            for (int i = 0; i < res.length; i++) {
                if (i < arr.length)
                    res[i] = arr[i];
                else
                    res[i] = value;
            } // for
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }

        return res;
    }// normalizeArray


    /**
     * Fusiona 2 arreglos
     * 
     * @param arr1
     * @param arr2
     * @return
     */
    public static Object[] mergeArrays(Object[] arr1, Object[] arr2) {
        return ArrayUtils.addAll(arr1, arr2);
    }// mergeArrays

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
