package ressource.XML;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class FxXML {

  public FxXML() {
  }

  /**
   * Construit et Ecrit un document XML
   * @param szFileName
   * @param lstData
   */
  public static void writeXML( String szFileName, XMLDocument xmlDoc )
  {
    xmlDoc.write(szFileName, true);
  }

  /**
   * Construit et Ecrit un document XML
   * @param szFileName
   * @param lstData
   */
  public static void writeXML( String szFileName, Vector lstData )
  {
    XMLDocument xmlDoc = new XMLDocument();
    for( int i=0 ; i<lstData.size() ; i++ )
    {
      Vector item = (Vector)lstData.elementAt(i);
      String szName = (String)item.elementAt(0);
      Vector data = (Vector)item.elementAt(1);
      xmlDoc.add(szName, data);
    }
    xmlDoc.write(szFileName, true);
  }

  /**
   * Construit un document XML
   * @param szFileName
   * @param lstData
   */
  public static void beginXMLElement( XMLDocument xmlDoc, String name, Hashtable attribut )
  {
      xmlDoc.addBegin(name, attribut);
  }

  /**
   * Construit un document XML
   * @param szFileName
   * @param lstData
   */
  public static void closeXMLElement( XMLDocument xmlDoc, String name)
  {
      xmlDoc.addEnd(name);
  }

  /**
   * Construit un document XML
   * @param szFileName
   * @param lstData
   */
  public static void addXMLData( XMLDocument xmlDoc, Vector lstData )
  {
    for( int i=0 ; i<lstData.size() ; i++ )
    {
      Vector item = (Vector)lstData.elementAt(i);
      String szName = (String)item.elementAt(0);
      Vector data = (Vector)item.elementAt(1);
      xmlDoc.add(szName, data);
    }
  }

  /**
   * Construit un document XML
   * @param szFileName
   * @param lstData
   */
  public static void addXMLSubData( XMLDocument xmlDoc, String name, Vector lstData )
  {
	XMLDocument xmlSubDoc = new XMLDocument(name, false);
	xmlSubDoc.setEncode(xmlDoc.getEncode());
    for( int i=0 ; i<lstData.size() ; i++ )
    {
      Vector item = (Vector)lstData.elementAt(i);
      String szName = (String)item.elementAt(0);
      Vector data = (Vector)item.elementAt(1);
      xmlSubDoc.add(szName, data);
    }
    xmlDoc.add(xmlSubDoc.toString());
  }
}