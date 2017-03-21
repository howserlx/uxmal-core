/*
 * --------------------------------------------------------------------------
 * Copyright (C) UXMALSOFT - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * --------------------------------------------------------------------------
 * XMLUtils.java
 * XML Utilities
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

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.uxmalsoft.commons.logging.SystemLog;

/**
 * 
 * XML Utilities
 * <p></p>
 *
 * @author Francisco Gerardo Hdz.
 * @version 1.0
 * @since
 * @see
 *
 */
public class XMLUtils {

    // ------------------------------------------------------
    // Attributes
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    // </editor-fold>

    // ------------------------------------------------------
    // Constructors
    // ------------------------------------------------------
    private XMLUtils() {
        // Only public static functions
    }// empty


    // ------------------------------------------------------
    // Methods and Functions
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Methods and Functions">


    /**
     * Valida si un archivo es xml
     * 
     * @param xmlFileName String
     * @return
     */
    public static boolean isValidXML(String xmlFileName) {
        boolean res = false;

        try {
            if (xmlFileName != null && !xmlFileName.isEmpty()) {
                File f = new File(xmlFileName);
                res = isValidXML(f);
            } // if

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// isValidXML


    /**
     * Valida si un archivo es xml
     * 
     * @param xmlFile File
     * @return
     */
    public static boolean isValidXML(File xmlFile) {
        boolean res = false;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            if (xmlFile != null && xmlFile.exists() && xmlFile.isFile()) {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(xmlFile);
                res = true;
            }

        } catch (SAXParseException spe) {
            SystemLog.writeUtilsException(spe);
        } catch (SAXException sxe) {
            SystemLog.writeUtilsException(sxe);
        } catch (ParserConfigurationException pce) {
            SystemLog.writeUtilsException(pce);
        } catch (IOException ioe) {
            SystemLog.writeUtilsException(ioe);
        }
        return res;
    }// isValidXML

    // --------------------------------------------------------------- //
    // Functions & Methods
    // --------------------------------------------------------------- //

    /**
     * Parse del XML Normalizado
     * 
     * @param xmlFileName String
     * @return
     */
    public static Document parseXML(String xmlFileName) {
        Document document = null;

        try {
            if (xmlFileName != null && !xmlFileName.isEmpty()) {
                File f = new File(xmlFileName);
                document = parseXML(f);
            } // if

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return document;
    }// parseXML


    /**
     * Parse del XML Normalizado
     * 
     * @param xmlFile File
     * @return Document
     */
    public static Document parseXML(File xmlFile) {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            if (xmlFile != null && xmlFile.exists() && xmlFile.isFile()) {
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.parse(xmlFile);
                document.normalize();
            }

        } catch (SAXParseException spe) {
            SystemLog.writeUtilsException(spe);
        } catch (SAXException sxe) {
            SystemLog.writeUtilsException(sxe);
        } catch (ParserConfigurationException pce) {
            SystemLog.writeUtilsException(pce);
        } catch (IOException ioe) {
            SystemLog.writeUtilsException(ioe);
        }
        return document;
    }// parseXML


    /**
     * Un nodo contiene algun nodo llamado
     * 
     * @param node
     * @param childNames
     * @return
     */
    public static boolean nodeContainsChildren(Node node, String... childNames) {
        boolean res = false;

        try {
            if (node != null && childNames != null) {
                for (String child : childNames) {
                    res = nodeContainsChild(node, child);
                    if (!res) // Si no contiene alguno es falso
                        return false;
                }
                res = true;
            } // !=nulls
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// nodeContainsChildren

    /**
     * Un nodo contiene un nodo llamado
     * 
     * @param node
     * @param childName
     * @return
     */
    public static boolean nodeContainsChild(Node node, String childName) {
        boolean res = false;

        try {
            if (node != null && childName != null && !childName.isEmpty()) {

                res = nodeListContainsChild(node.getChildNodes(), childName);
            } // !=nulls

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// nodeContainsChild


    /**
     * Una lista de nodos contiene un nodo llamado
     * 
     * @param nodeList
     * @param childName
     * @return
     */
    public static boolean nodeListContainsChild(NodeList nodeList, String childName) {
        boolean res = false;

        try {
            if (nodeList != null && childName != null && !childName.isEmpty()) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node n = nodeList.item(i);
                    if (n != null && n.getNodeName().equals(childName))
                        return true;
                }
            } // !=nulls

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// nodeListContainsChild


    /**
     * Un nodo contiene un nodo llamado
     * 
     * @param node
     * @param childName
     * @return
     */
    public static Node getChildNode(Node node, String childName) {
        Node res = null;

        try {
            if (node != null && childName != null && !childName.isEmpty()) {

                res = getChildNode(node.getChildNodes(), childName);
            } // !=nulls

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// getChildNode


    /**
     * Una lista de nodos contiene un nodo llamado
     * 
     * @param nodeList
     * @param childName
     * @return
     */
    public static Node getChildNode(NodeList nodeList, String childName) {
        Node res = null;

        try {
            if (nodeList != null && childName != null && !childName.isEmpty()) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node n = nodeList.item(i);
                    if (n != null && n.getNodeName().equals(childName))
                        return n;
                }
            } // !=nulls

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return res;
    }// getChildNode


    /**
     * Regresa el valor de un Nodo dentro de otro nodo
     * 
     * @param node Node
     * @param nodeName String
     * @return String
     */
    public static String getNodeValue(Node node, String nodeName) {
        String val = "";

        try {
            if (node != null && nodeName != null && !nodeName.isEmpty()) {

                val = getNodeValue(node.getChildNodes(), nodeName);
            } // !=nulls

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return val;
    }// getNodeValue

    /**
     * Regresa el valor de un Nodo dentro de un NodeList
     * 
     * @param nodeList NodeList
     * @param nodeName String
     * @return String
     */
    public static String getNodeValue(NodeList nodeList, String nodeName) {
        String val = "";

        try {
            if (nodeList != null && nodeName != null && !nodeName.isEmpty()) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node n = nodeList.item(i);
                    if (n != null && n.getNodeName().equals(nodeName)) {

                        // Intenta leer valores
                        // ---------------------------------------------------------------------
                        val = n.getNodeValue();

                        if (val == null) {
                            NamedNodeMap map = n.getAttributes();
                            if (map != null && map.getLength() > 0)
                                val = n.getAttributes().getNamedItem("value").getNodeValue();
                        }

                        if (val == null) {
                            Node fc = n.getFirstChild();
                            if (fc != null)
                                val = n.getFirstChild().getNodeValue();
                        }

                        if (val == null)
                            val = n.getTextContent();

                        if (val == null) {
                            Node fc = n.getFirstChild();
                            if (fc != null)
                                val = n.getFirstChild().getTextContent();
                        }
                        // ---------------------------------------------------------------------
                        return val;
                    } // if
                } // for
            } // !=nulls

        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
        return val;
    }// getNodeValue


    /**
     * Coloca un atributo nuevo con su valor a un elemento de un documento xml
     * 
     * @param doc
     * @param element
     * @param attrName
     * @param attrValue
     */
    public static void setAttrToElement(Document doc, Element element, String attrName, String attrValue) {
        try {
            if (doc != null && element != null) {
                Attr attr = doc.createAttribute(attrName);
                attr.setValue(attrValue);
                element.setAttributeNode(attr);
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
    }// setAttrToElement



    /**
     * Coloca un nodo nuevo con su valor a un elemento de un documento xml
     * 
     * @param doc
     * @param element
     * @param nodeName
     * @param nodeValue
     */
    public static void addNodeToElement(Document doc, Element element, String nodeName, String nodeValue) {
        try {
            if (doc != null && element != null) {
                Element node = doc.createElement(nodeName);
                node.setTextContent(nodeValue);
                element.appendChild(node);
            }
        } catch (Exception ex) {
            SystemLog.writeUtilsException(ex);
        }
    }// addNodeToElement

    // </editor-fold>

    // ------------------------------------------------------
    // None
    // ------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="None">

    // </editor-fold>

}// class
