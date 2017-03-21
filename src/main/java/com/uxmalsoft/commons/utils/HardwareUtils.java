/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * HardwareUtils.java
 * Hardware Utilities
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * Hardware Utilities
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class HardwareUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Operating System Enum">
    public enum OperatingSystem {
        LINUX   ("nix,nux,aix" ,"Linux"   ,"SerialNumber:,sudo hdparm -I /dev/sd? | grep 'Serial'",",sudo dmidecode -s baseboard-serial-number"),
        MAC     ("mac"         ,"MacOS"   ,"",""),
        SOLARIS ("sunos"       ,"Solaris" ,"",""),
        UNIX    ("nix,nux,aix" ,"Unix"    ,"",""),
        WINDOWS ("win"         ,"Windows" ,"SerialNumber=,wmic diskdrive get serialnumber /format:list","SerialNumber=,wmic baseboard get serialnumber /format:list"),
        ;

        private final String id;
        private final String name;
        private final String diskIdCommand;
        private final String hwdIdCommand;

        private OperatingSystem(String id, String name, String diskIdCommand, String hwdIdCommand) {
            this.id = id;
            this.name = name;
            this.diskIdCommand = diskIdCommand;
            this.hwdIdCommand = hwdIdCommand;
        }// constructor


        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDiskIdCommand() {
            return diskIdCommand;
        }

        public String getHwdIdCommand() {
            return hwdIdCommand;
        }

        public static OperatingSystem findById(String searchId) {
            searchId = validateNull(searchId).toLowerCase();

            for (OperatingSystem item : OperatingSystem.values()) {
                for (String str : item.getId().split(",")) {
                    if (searchId.contains(str))
                        return item;
                } // fore id
            } // fore os
            return null;
        }// findById

        public static Map asMap() {
            Map map = new LinkedHashMap();
            for (OperatingSystem item : OperatingSystem.values()) {
                map.put(item.getName(), item.getId());
            }
            return map;
        }// asMap

    }// enum
     // </editor-fold>

    private static final String URL_PUBLIC_IP_ADDRESS      = "http://checkip.amazonaws.com";
    private static final String URL_GEOLOCATION_IP_ADDRESS = "http://ip-api.com/json/{0}";

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private HardwareUtils() {
        // Only public static functions

    }// empty


    // ------------------------------------------------------
    // Network
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Network">

    /**
     * IP Address
     * 
     * @return
     */
    public static List<String> getIPAddress() {
        return getIPAddress(false, true);
    }// getIPAddress

    /**
     * IP Address
     * 
     * @param showIpV6
     * @param showCurrentConn
     * @return
     */
    public static List<String> getIPAddress(boolean showIpV6, boolean showCurrentConn) {
        List list = new ArrayList<>();
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();

                if (n.isUp()) {
                    Enumeration ee = n.getInetAddresses();
                    while (ee.hasMoreElements()) {

                        Object obj = ee.nextElement();
                        if (obj instanceof Inet6Address && !showIpV6)
                            continue;

                        InetAddress i = (InetAddress) obj;

                        if (showCurrentConn)
                            if (i.isLoopbackAddress() && !i.isSiteLocalAddress())
                                continue;

                        list.add(i.getHostAddress());

                    } // while
                } // up
            } // while


        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return list;
    }// getIPAddress


    /**
     * Public IPAddress
     * 
     * @return
     */
    public static String getPublicIPAddress() {
        String ip = null;
        BufferedReader in = null;
        try {
            URL serviceURL = new URL(URL_PUBLIC_IP_ADDRESS);
            in = new BufferedReader(new InputStreamReader(
                    serviceURL.openStream()));

            ip = in.readLine();
        } catch (MalformedURLException ex) {
            SystemLog.writeUtilsException(ex);
        } catch (IOException ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception ex) {
                    SystemLog.writeUtilsException(ex);
                }
        }
        return ip;
    }// getPublicIPAddress


    /**
     * Geolocation by IPAddress
     * 
     * @return
     */
    public static HashMap getGeolocationIPAddress() {
        return getGeolocationIPAddress(getPublicIPAddress());
    }// getGeolocationIPAddress


    /**
     * Geolocation by IPAddress
     * 
     * @param ipAddress
     * @return
     */
    public static HashMap getGeolocationIPAddress(String ipAddress) {
        HashMap<String, Object> result = null;
        BufferedReader in = null;
        try {
            URL serviceURL = new URL(StringUtils.prepareMsg(URL_GEOLOCATION_IP_ADDRESS, ipAddress)); // http://ip-api.com/docs/api:json
            in = new BufferedReader(new InputStreamReader(
                    serviceURL.openStream()));

            String json = in.readLine();
            result = new ObjectMapper().readValue(json, HashMap.class);

        } catch (MalformedURLException ex) {
            SystemLog.writeUtilsException(ex);
        } catch (IOException ex) {
            SystemLog.writeUtilsException(ex);
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception ex) {
                    SystemLog.writeUtilsException(ex);
                }
        }
        return result;
    }// getGeolocationIPAddress


    /**
     * MAC Address
     * 
     * @param format
     * @param encrypted
     * @return
     */
    public static String getMACAddress(boolean format, boolean encrypted) {
        String res = "";
        StringBuilder sb = new StringBuilder();
        try {
            byte[] mac = getMACAddress();
            String formatChar = (format) ? "-" : "";

            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? formatChar : ""));
            } // for

            res = sb.toString();

            if (encrypted)
                res = SHA256Utils.getSHA(res);

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// getMACAddress


    /**
     * MAC Address
     * 
     * @return
     */
    public static byte[] getMACAddress() {
        byte[] mac = {};

        try {

            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            mac = network.getHardwareAddress();


        } catch (UnknownHostException | SocketException ex) {
            SystemLog.writeUtilsException(ex);
        }
        return mac;
    }// getMACAddress


    // </editor-fold>

    // ------------------------------------------------------
    // Operating System
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Operating System">

    /**
     * OS is Linux
     * 
     * @return
     */
    public static boolean isLinux() {
        return (getOS_asEnum().equals(OperatingSystem.LINUX));
    }// isLinux

    /**
     * OS is MacOS
     * 
     * @return
     */
    public static boolean isMacOS() {
        return (getOS_asEnum().equals(OperatingSystem.MAC));
    }// isMacOS

    /**
     * OS is Solaris
     * 
     * @return
     */
    public static boolean isSolaris() {
        return (getOS_asEnum().equals(OperatingSystem.SOLARIS));
    }// isSolaris

    /**
     * OS is Unix
     * 
     * @return
     */
    public static boolean isUnix() {
        return (getOS_asEnum().equals(OperatingSystem.UNIX));
    }// isUnix

    /**
     * OS is Windows
     * 
     * @return
     */
    public static boolean isWindows() {
        return (getOS_asEnum().equals(OperatingSystem.WINDOWS));
    }// isWindows



    /**
     * Get Operating System as Enum
     * 
     * @return
     */
    public static OperatingSystem getOS_asEnum() {
        OperatingSystem os = null;
        try {
            os = OperatingSystem.findById(getOS());
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return os;
    }// getOS_asEnum

    /**
     * Get Operating System
     * 
     * @return
     */
    public static String getOS() {
        String str = "";
        try {
            str = System.getProperty("os.name");
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return str;
    }// getOS

    /**
     * Get Operating System Version
     * 
     * @return
     */
    public static String getOSVersion() {
        String str = "";
        try {
            str = System.getProperty("os.version");
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return str;
    }// getOSVersion

    // </editor-fold>


    // ------------------------------------------------------
    // Id
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Id">

    /**
     * BaseBoard Id
     * 
     * @return
     */
    public static String getBaseBoardId() {
        return getBaseBoardId(false);
    }// getBaseBoardId

    /**
     * BaseBoard Id
     * 
     * @param encrypted
     * @return
     */
    public static String getBaseBoardId(boolean encrypted) {
        String id = null;
        try {
            OperatingSystem os = getOS_asEnum();
            if (os != null) {
                String command = os.getHwdIdCommand();
                String[] arr = command.split(",");
                if (arr.length == 2) {
                    id = RuntimeUtils.executeCommand(arr[1]);
                    id = StringUtils.removeWhiteSpaces(id.toUpperCase());
                    id = id.replace(arr[0].toUpperCase(), "");

                    if (encrypted)
                        id = SHA256Utils.getSHA(id);
                }
            } // !null
        } catch (Exception ex) {
            id = null;
            SystemLog.writeUtilsException(ex);
        }
        return id;
    }// getBaseBoardId

    /**
     * DiskDriveId Id
     * 
     * @return
     */
    public static String getDiskDriveId() {
        return getDiskDriveId(false);
    }// getDiskDriveId


    /**
     * DiskDriveId Id
     * 
     * @param encrypted
     * @return
     */
    public static String getDiskDriveId(boolean encrypted) {
        String id = null;
        try {
            OperatingSystem os = getOS_asEnum();
            if (os != null) {
                String command = os.getDiskIdCommand();
                String[] arr = command.split(",");
                if (arr.length == 2) {
                    id = RuntimeUtils.executeCommand(arr[1]);
                    id = StringUtils.removeWhiteSpaces(id.toUpperCase());
                    id = id.replace(arr[0].toUpperCase(), "");

                    if (encrypted)
                        id = SHA256Utils.getSHA(id);
                }
            } // !null
        } catch (Exception ex) {
            id = null;
            SystemLog.writeUtilsException(ex);
        }
        return id;
    }// getDiskDriveId


    // </editor-fold>

}// class
