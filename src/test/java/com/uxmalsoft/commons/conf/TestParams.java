/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * TestParams.java
 * Test Params
 *
 * Created on : 18/04/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References : 
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.conf;

import com.uxmalsoft.commons.logging.SystemLog;
import com.uxmalsoft.commons.conf.TestParams;
import java.io.File;
/**
 * 
 * Test Params
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class TestParams {

    //------------------------------------------------------
    // Attributes
    //------------------------------------------------------
    
    public static final String TEST_BASE_PATH = "_res";
    public static final String TEST_IMG_PATH = "img";
    public static final String TEST_TXT_PATH = "txt";
    public static final String TEST_PDF_PATH = "pdf";
    public static final String TEST_DOC_PATH = "doc";
    
    public static final String LOG_CONFIG_PATH = "./_config/log4j2.xml";
    
    //------------------------------------------------------
    // Constructors
    //------------------------------------------------------
    private TestParams(){
        //Only public static functions
    }//empty
    
    
    //------------------------------------------------------
    // Methods & Functions
    //------------------------------------------------------
    public static String getTest_basePath(){
        String path = TEST_BASE_PATH+File.separator;
        return path;
    }//getTest_basePath
    
    public static String getTest_imgPath(){
        String path = getTest_basePath()+TEST_IMG_PATH+File.separator;
        return path;
    }//getTest_imgPath
    
    public static String getTest_txtPath(){
        String path = getTest_basePath()+TEST_TXT_PATH+File.separator;
        return path;
    }//getTest_txtPath
    
    public static String getTest_pdfPath(){
        String path = getTest_basePath()+TEST_PDF_PATH+File.separator;
        return path;
    }//getTest_pdfPath
    
    public static String getTest_docPath(){
        String path = getTest_basePath()+TEST_DOC_PATH+File.separator;
        return path;
    }//getTest_docPath
    
}//class
