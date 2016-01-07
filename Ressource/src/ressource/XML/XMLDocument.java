package ressource.XML;

import java.util.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class XMLDocument {

  protected String version = "\"1.0\"";
  protected StringBuffer document = new StringBuffer();
  protected String begin = "<body>\r\n";
  protected String end = "</body>\r\n";
  protected String extension = ".xml";
  private String encode;

  public XMLDocument() {
	  this.createBebin();
  }

  public XMLDocument(Hashtable attribut) {
	  this("body", attribut, true);
  }

  public XMLDocument(String pMainTag) {
	  this(pMainTag, true);
  }

  public XMLDocument(String pMainTag, boolean addXmlVersion) {
    this(pMainTag, null, addXmlVersion);
  }
  public XMLDocument(String pMainTag, Hashtable attribut, boolean addXmlVersion) {
	this.setBegin(buildTagBegin(pMainTag, attribut));
    this.setEnd("</"+pMainTag+">\r\n");
    this.createBebin(addXmlVersion);
  }

public void close()
  {
    this.createEnd();
  }
  public void createBebin()
  {
	  createBebin(true);
  }
  public void createBebin(boolean addXmlVersion)
  {
	  if (addXmlVersion) {
	    this.add("<?xml version=");
	    this.add(getVersion());
	    this.add("?>\r\n");
	  }
    this.add(this.getBegin());
  }
  public void createEnd()
  {
    this.getDocument().append(this.getEnd());
  }
  public void add(String szName, Vector lstData)
  {
    for( int i=0 ; i<lstData.size() ; i++ )
    {
      String name = (lstData.size()>1) ? szName.concat(Integer.toString(i)) : szName;
      String item = (String)lstData.elementAt(i);
      this.add(szName, item);
    }
  }
  public void add(String szName, String szData)
  {
    this.addBegin(szName);
    this.addData(szData);
    this.addEnd(szName);
  }
  public int insertAfter(String szFind, String szName, String szData)
  {
    int iFind = this.getDocument().toString().indexOf(szFind);
    if( iFind>=0 )
    {
      iFind += szFind.length();
      iFind = this.insertBegin(iFind, szName);
      iFind = this.insertData(iFind, szData);
      iFind = this.insertEnd(iFind, szName);
    }
    return iFind;
  }
  public int insertBefore(String szFind, String szName, String szData)
  {
    int iFind = this.getDocument().toString().indexOf(szFind);
    if( iFind>=0 )
    {
      iFind = this.insertBegin(iFind, szName);
      iFind = this.insertData(iFind, szData);
      iFind = this.insertEnd(iFind, szName);
    }
    return iFind;
  }
  public void addBegin(String szName)
  {
    this.add("<");
    this.add(szName);
    this.add(">");
  }
  public void addBegin(String szName, Hashtable attribut)
  {
    this.add(buildTagBegin(szName, attribut));
  }

  protected String buildTagBegin(String szName, Hashtable attribut) {
	  StringBuffer ret = new StringBuffer();
	  ret.append("<");
	  ret.append(szName);
	  ret.append(buildAttribut(attribut));
	  ret.append(">\r\n");
	  return ret.toString();
  }
  protected String buildAttribut(Hashtable attribut) {
	StringBuffer ret = new StringBuffer();
	if (attribut!=null) {
		String key;
		Enumeration eEnum = attribut.keys();
	    while (eEnum.hasMoreElements()) {
	    	key = (String)eEnum.nextElement();
	    	ret.append(" ").append(key);
	    	ret.append("=\"").append(attribut.get(key).toString()).append("\"");
	    }
	}
	return ret.toString();
  }
  public void addEnd(String szName)
  {
    this.add("</");
    this.add(szName);
    this.add(">\r\n");
  }
  public void addData(String szData)
  {
  	if (encode!=null && !encode.equals("")) {
  		try {
				this.add(java.net.URLEncoder.encode(szData, encode));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	  }
	  else
		  this.add(szData);
  }
  public void add(String str)
  {
    this.getDocument().append(str);
  }
  public int insertBegin(int iIndex, String szName)
  {
    this.insert(iIndex, "<");     iIndex++;
    this.insert(iIndex, szName);  iIndex += szName.length();
    this.insert(iIndex, ">");     iIndex++;
    return iIndex;
  }
  public int insertEnd(int iIndex, String szName)
  {
    this.insert(iIndex, "</");    iIndex+=2;
    this.insert(iIndex, szName);  iIndex += szName.length();
    this.insert(iIndex, ">\r\n"); iIndex+=3;
    return iIndex;
  }
  public int insertData(int iIndex, String szData)
  {
    String data = java.net.URLEncoder.encode(szData);
    return this.insert(iIndex, data);
  }
  public int insert(int iIndex, String szData)
  {
    this.getDocument().insert(iIndex, szData);
    return iIndex+szData.length();
  }
  public int insertBeginBefore(String szFind, String szName)
  {
    int iIndex = this.getDocument().toString().indexOf(szFind);
    if( iIndex>=0 )
    {
    this.insert(iIndex, "<");     iIndex++;
    this.insert(iIndex, szName);  iIndex += szName.length();
    this.insert(iIndex, ">");     iIndex++;
    }
    return iIndex;
  }
  public int insertEndBefore(String szFind, String szName)
  {
    int iIndex = this.getDocument().toString().indexOf(szFind);
    if( iIndex>=0 )
    {
      this.insert(iIndex, "</");    iIndex+=2;
      this.insert(iIndex, szName);  iIndex += szName.length();
      this.insert(iIndex, ">\r\n"); iIndex+=3;
    }
    return iIndex;
  }
  public int insertDataBefore(String szFind, String szData)
  {
    int iIndex = this.getDocument().toString().indexOf(szFind);
    if( iIndex>=0 )
      iIndex=this.insertData(iIndex, szData);
    return iIndex;
  }
  public int insertBeginAfter(String szFind, String szName)
  {
    int iIndex = this.getDocument().toString().indexOf(szFind);
    if( iIndex>=0 )
    {
      iIndex += szFind.length();
      this.insert(iIndex, "<");     iIndex++;
      this.insert(iIndex, szName);  iIndex += szName.length();
      this.insert(iIndex, ">");     iIndex++;
    }
    return iIndex;
  }
  public int insertEndAfter(String szFind, String szName)
  {
    int iIndex = this.getDocument().toString().indexOf(szFind);
    if( iIndex>=0 )
    {
      iIndex += szFind.length();
      this.insert(iIndex, "</");    iIndex+=2;
      this.insert(iIndex, szName);  iIndex += szName.length();
      this.insert(iIndex, ">\r\n"); iIndex+=3;
    }
    return iIndex;
  }
  public int insertDataAfter(String szFind, String szData)
  {
    int iIndex = this.getDocument().toString().indexOf(szFind);
    if( iIndex>=0 )
    {
      iIndex += szFind.length();
      iIndex=this.insertData(iIndex, szData);
    }
    return iIndex;
  }
  public void write(String szFileName, boolean bClose)
  {
    if ( bClose ) this.close();
    this.write(szFileName);
  }
  public void write(String szFileName)
  {
    // Ajoute l'extension .XML si il faut
    if ( !szFileName.substring(szFileName.length()-extension.length()).equalsIgnoreCase(extension) ) szFileName = szFileName + extension;
    try
    {
      FileWriter fl = new FileWriter(szFileName);
      fl.write(this.getDocument().toString().toCharArray());
      fl.close();
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }
  public boolean isContendTag(String pTagName)
  {
    return (findTag(pTagName)>=0);
  }
  public int findTag(String pTagName)
  {
    int ret = -1;

    ret = this.getDocument().toString().indexOf("<"+pTagName+">");
    if ( ret<0 )
      ret = this.getDocument().toString().indexOf("<"+pTagName+" ");

    return ret;
  }
	@Override
	public String toString() {
		return this.getDocument().toString();
	}
protected StringBuffer getDocument() {
    return document;
  }
  protected String getVersion() {
    return version;
  }
  protected void setDocument(StringBuffer document) {
    this.document = document;
  }
  protected void setVersion(String version) {
    this.version = version;
  }
  protected String getBegin() {
    return begin;
  }
  protected void setBegin(String begin) {
    this.begin = begin;
  }
  protected String getEnd() {
    return end;
  }
  protected void setEnd(String end) {
    this.end = end;
  }
  protected String getExtension() {
    return extension;
  }
  protected void setExtension(String extension) {
    this.extension = extension;
  }
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
}