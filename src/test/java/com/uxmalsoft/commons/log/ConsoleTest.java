/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * ConsoleTest.java
 * Console Test
 *
 * Created on : 19/04/2015
 * Author(s)  : Francisco Gerardo Hdz.
 * References : 
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.log;

import com.uxmalsoft.commons.io.Console;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * 
 * Console Test
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class ConsoleTest {
    
    public ConsoleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of println method, of class Console.
     */
    @Test
    public void testPrintln_String() {
        System.out.println("println");
        String msg = "Hola tarola";
        Console.println(msg);
        
    }

    /**
     * Test of println method, of class Console.
     */
    @Test
    public void testPrintln_String_boolean() {
        System.out.println("println");
        String msg = "Hola tarola";
        boolean logMsg = false;
        Console.println(msg, logMsg);
        
    }

    /**
     * Test of writeln method, of class Console.
     */
    @Test
    public void testWriteln_String() {
        System.out.println("writeln");
        String msg = "Hola tarola";
        Console.writeln(msg);
        
    }

    /**
     * Test of writeln method, of class Console.
     */
    @Test
    public void testWriteln_String_boolean() {
        System.out.println("writeln");
        String msg = "Hola tarola";
        boolean logMsg = false;
        Console.writeln(msg, logMsg);
        
    }

    /**
     * Test of writeInfo method, of class Console.
     */
    @Test
    public void testWriteInfo_String() {
        System.out.println("writeInfo");
        String msg = "Hola tarola";
        Console.writeInfo(msg);
       
    }

    /**
     * Test of writeInfo method, of class Console.
     */
    @Test
    public void testWriteInfo_String_boolean() {
        System.out.println("writeInfo");
        String msg = "Hola tarola";
        boolean logMsg = false;
        Console.writeInfo(msg, logMsg);
        
    }

    /**
     * Test of writeError method, of class Console.
     */
    @Test
    public void testWriteError_String() {
        System.out.println("writeError");
        String msg = "Hola tarola";
        Console.writeError(msg);
        
    }

    /**
     * Test of writeError method, of class Console.
     */
    @Test
    public void testWriteError_String_boolean() {
        System.out.println("writeError");
        String msg = "Hola tarola";
        boolean logMsg = false;
        Console.writeError(msg, logMsg);
        
    }

    /**
     * Test of writeDebug method, of class Console.
     */
    @Test
    public void testWriteDebug_String() {
        System.out.println("writeDebug");
        String msg = "Hola tarola";
        Console.writeDebug(msg);
        
    }

    /**
     * Test of writeDebug method, of class Console.
     */
    @Test
    public void testWriteDebug_String_boolean() {
        System.out.println("writeDebug");
        String msg = "Hola tarola";
        boolean logMsg = false;
        Console.writeDebug(msg, logMsg);
        
    }
    
}
