/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * SystemLog_Test.java
 * Test for SystemLog.java
 *
 * Created on : 01/09/2014
 * Author(s)  : Francisco Gerardo Hdz.
 * References : 
 *
 * [nextEntry]
 * --------------------------------------------------------------------------
 */

package com.uxmalsoft.commons.log;

import com.uxmalsoft.commons.logging.SystemLog;
import com.uxmalsoft.commons.conf.TestParams;
/**
 * 
 * Test for SystemLog.java
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class SystemLog_Test {

    //------------------------------------------------------
    // Attributes
    //------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="Attributes">
    
    //</editor-fold>
    
    //------------------------------------------------------
    // Constructors
    //------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    private SystemLog_Test(){
    
    }//empty
    //</editor-fold>
    
    
    //------------------------------------------------------
    // Methods and Functions
    //------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="Methods and Functions">

    public static void createException(String idOperation,boolean isFromUtils ){
        try {
            if(isFromUtils){
                int a = 3;
                int b = 1-1;

                int c = a/b;
            }else{
                StringBuilder sb = null;
                sb.append('w');
            }
            
        } catch (ArithmeticException e) {
            SystemLog.writeException(e);
            SystemLog.writeException(e,idOperation);
           
            
        } catch(NullPointerException e){
            SystemLog.writeUtilsException(e);
        }
    }//aMethod


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
        
        for(int i=0;i<1;i++){
            String idOp = "AH1123581321";

            SystemLog.writeInfo(null,null);
            SystemLog.writeInfo("Este es un Mensaje INFO");
            SystemLog.writeInfo("Este es un Mensaje INFO 1",idOp);

            SystemLog.writeDebug("Este es un Mensaje DEBUG");
            SystemLog.writeDebug("Este es un Mensaje DEBUG 1",idOp);

            SystemLog.writeWarning("Este es un Mensaje WARNING");
            SystemLog.writeWarning("Este es un Mensaje WARNING 1",idOp);

            SystemLog.writeError("Este es un Mensaje ERROR");
            SystemLog.writeError("Este es un Mensaje ERROR 1",idOp);

            createException(idOp, false);
            createException(idOp, true);


            SystemLog.writeIntoUserLog("user1", "El usuario1 ha iniciado sesión");
            SystemLog.writeIntoUserLog("user1", "El usuario1 ha iniciado sesión", idOp, "Login");

            SystemLog.writeIntoDatabaseLog("Countries_mx", "SELECT * FROM Countries_mx");
            SystemLog.writeIntoDatabaseLog("Countries_mx", "SELECT * FROM Countries_mx", idOp);

            SystemLog.writeIntoAlertLog("Alarma", "LOGIN_FAIL", "El usuario1 intento hacer Login fuera de Horario");
            SystemLog.writeIntoAlertLog("Alarma", "LOGIN_FAIL", "El usuario1 intento hacer Login fuera de Horario",idOp);

            String[] phones = {"4440000000","4441111111","4441234567"};
            String[] mails  = {"root@test.com","admin@test.com","user@test.com"};

            SystemLog.writeIntoMailLog(mails, "La contraseña ha sido cambiada");
            SystemLog.writeIntoMailLog(mails, "La contraseña ha sido cambiada",idOp);

            SystemLog.writeIntoSMSLog(phones, "Se ha registrado una compra de $100");
            SystemLog.writeIntoSMSLog(phones, "Se ha registrado una compra de $100",idOp);
            
            
            String a = "/(";
            String b = "o ";
            String c = "Y";
            String d = " o";
            String e = ")\\";
            
            System.out.println(a+b+c+d+e);

        }//for
    }//main
    
    //</editor-fold>

}//class
