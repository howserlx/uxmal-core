/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * StringUtils_Test.java
 * Test for StringUtils.java
 *
 * Created on : 01/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References : references
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons;

import com.uxmalsoft.commons.io.Console;
import com.uxmalsoft.commons.logging.SystemLog;
import com.uxmalsoft.commons.conf.TestParams;
import com.uxmalsoft.commons.utils.FileUtils;
import com.uxmalsoft.commons.utils.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 
 * Test for StringUtils.java
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class StringUtils_Test {

    //------------------------------------------------------
    // Attributes
    //------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="Attributes">
    
    //</editor-fold>
    
    //------------------------------------------------------
    // Constructors
    //------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    private StringUtils_Test(){
    
    }//empty
    //</editor-fold>
    

    //------------------------------------------------------
    // Test StringUtils
    //------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="Test StringUtils">
    private static void test_convertLetterCase(String ruta,StringUtils.LetterCase letter){
        List<String> list = FileUtils.readFile_asList(ruta);
        
        if(list != null && !list.isEmpty() )
        {
            System.out.println("---- "+letter.name()+" -----\n");
            for (String str : list) {
                System.out.println(str+"->"+StringUtils.convertLetterCase(str,letter,false));
            }
            System.out.println("\n");
        }
    }
    
    private static void test_removeAccents()
    {
        List<String> list = new ArrayList<String>();
        list.add("aáeéiíoóöőuúüű AÁEÉIÍOÓÖŐUÚÜŰnñNÑ");
        list.add("árvíztűrő tükörfúrógépnñNÑ");
        list.add(" afgr yy uiiu    o  \terre aáeéiíoóöőuúüű AÁEÉIÍOÓÖŐUÚÜŰnñNÑ");
        list.add("Uxmal Soft. S.A. de C.V. - l l  l (aáeéiíoóöőuúüű AÁEÉIÍOÓÖŐUÚÜŰnñ) [¿ñOÑo?]");
        list.add(null);
        list.add("");
        //Acentos
        System.out.println("---- Remove Accents -----\n");
        for (String str : list) {
            System.out.println(str+"->"+StringUtils.removeAccents(str,false));
            System.out.println(str+"->"+StringUtils.removeAccents(str));
        }
        System.out.println("\n");
    }
    private static void test_removeWhiteSpaces(){
        List<String> list = new ArrayList<String>();
        list.add(" afgr yy uiiu    o  \terre aáeéiíoóöőuúüű AÁEÉIÍOÓÖŐUÚÜŰnñNÑ");        
        list.add("Uxmal Soft. S.A. de C.V. - l l  l (aáeéiíoóöőuúüű AÁEÉIÍOÓÖŐUÚÜŰnñ) [¿ñOÑo?]");  
        list.add(null);
        list.add("");
        
        System.out.println("---- RemoveWithSpaces -----\n");
        for (String str : list) {
            System.out.println(str+"->"+StringUtils.removeWhiteSpaces(str));
        }
        System.out.println("\n");
    }
    
    private static void test_onlyAlphaNumeric(){
        List<String> list = new ArrayList<String>();
        list.add("Uxmal Soft. S.A. de C.V. - l l  l (aáeéiíoóöőuúüű AÁEÉIÍOÓÖŐUÚÜŰnñ) [¿ñOÑo?]");  
        list.add(null);
        list.add("");
        
        System.out.println("---- OnlyAlphaNumeric -----\n");
        for (String str : list) {
            System.out.println(str+"->"+StringUtils.onlyAlphaNumeric(str));
        }
        System.out.println("\n");
    }
    
    private static void test_generateTextCode(){
        List<String> list = new ArrayList<String>();
        list.add("Uxmal Soft. S.A. de C.V. San Luis Potosí, México ÑOÑO");
        list.add("Uxmal Soft   SA de CV. San Luis PotoSI, mexico ñOñO");
        list.add(null);
        list.add("");
        System.out.println("---- GenerateTextCode -----\n");
        for (String str : list) {
            System.out.println(str+"->"+StringUtils.generateTextCode(str));
        }
        System.out.println("\n");
    }
    
    private static void test_searchOptionalNames(){
        List<String> list = new ArrayList<String>();
        list.add("Gato% Perro% Mapache");
        list.add("Candelaria (Cande)");
        list.add("Vaca [Vacota Loca]");
        list.add("Candelaria (Cande% Burra)");
        list.add("Candelaria%Cande (Vaca% VACOTA)");
        list.add("Candelaria [Cande% Burra] % Perrillo Loco");
        list.add("Candelaria (Cande) (Burra)");
        list.add(null);
        list.add("");
        System.out.println("---- searchOptionalNames -----\n");
        for (String str : list) {
            System.out.println(str+"->"+StringUtils.getOptionalNames(str,null));
            System.out.println(str+"->"+StringUtils.getOptionalNames(str,"%"));
        }
        System.out.println("\n");
    }
    
   
//</editor-fold>
    
    //------------------------------------------------------
    // main
    //------------------------------------------------------
    //<editor-fold defaultstate="open" desc="main">
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
       
       SystemLog.initialize(TestParams.LOG_CONFIG_PATH,true);
       Console.writeInfo(Console.SEPARATOR_LINE);
       Console.writeInfo(" Test de StringUtils - "+new Date().toString(), true);
       Console.writeInfo(Console.SEPARATOR_LINE);
       
       // test_ALTERNATING_LOWER_CASE();
       /*for (StringUtils.LETTER_CASE type : StringUtils.LETTER_CASE.values()) {
            test_convertLetterCase("_res/nombres_20.txt", type);
        }
       */
        
        //Acentos
        //test_removeAccents();
        
        //Espacios blanco
        //test_removeWhiteSpaces();
        
        //Alphanumeric
        //test_onlyAlphaNumeric();
        
        //Codigos basados en texto
        //test_generateTextCode();
        
        //Nombres opcionales
       // test_searchOptionalNames();
        
        String [] arr = StringUtils.asStringArray("gato","perro");
        
        System.out.println(""+arr[0]);
        System.out.println(""+arr[1]);
        
    }//main
    
    //</editor-fold>

}//class
