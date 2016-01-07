package ressource;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class FxString {

  protected FxString() {
  }

  /**
   * Replace txt1 by txt2 into str
   * @param str
   * @param txt1
   * @param txt2
   * @return
   */
  public static String replaceString( String str, String txt1, String txt2 )
  {
    int index = -1;
    int iTxtLen = txt1.length();
    StringBuffer ret = new StringBuffer(str);
    while( (index=ret.toString().indexOf(txt1))>=0 )
    {
      ret.delete(index, index+iTxtLen);
      ret.insert(index, txt2);
    }
    return ret.toString();
  }

  /**
   * Replace in str by replace from baliseDeb to baliseFin
   * @param str
   * @param baliseDeb
   * @param baliseFin
   * @param replace
   * @return
   */
  public static String replaceString(String str, String baliseDeb, String baliseFin, String replace)
  {
    if ( str.length() > 0 )
    {
      boolean bContinue = true;
      String strTmp = str.toUpperCase();
      String baliseDebTmp = baliseDeb.toUpperCase();
      String baliseFinTmp = baliseFin.toUpperCase();
      int iDeb = 0;
      int iFin = 0;
      while(bContinue)
      {
        iDeb = strTmp.indexOf(baliseDebTmp, iDeb);
        iFin = strTmp.indexOf(baliseFinTmp, iDeb);
        // Cherche la dernière occurance de la balise de fin
        // Même si il y a d'autres balises du même type à l'interieur
        // exemple: <SCRIPT><SCRIPT>blablabla...</SCRIPT></SCRIPT>
        int iTmp = strTmp.indexOf(baliseDebTmp, iDeb+baliseDeb.length());
        while( (iTmp>-1) && (iTmp<iFin) )
        {
          iFin = strTmp.indexOf(baliseFinTmp, iFin+baliseFinTmp.length());
          iTmp = strTmp.indexOf(baliseDebTmp, iTmp+baliseDebTmp.length());
        }
        bContinue = ( (iDeb>-1) && (iFin>-1) );
        if ( bContinue )
        {
          str = str.substring(0, iDeb).concat(replace).concat(str.substring(iFin+baliseFinTmp.length()));
          strTmp = strTmp.substring(0, iDeb).concat(replace).concat(strTmp.substring(iFin+baliseFinTmp.length()));
        }
      }
    }
    return str;
  }

  /**
   * Delete in str from baliseDeb to baliseFin
   * @param str
   * @param baliseDeb
   * @param baliseFin
   * @return
   */
  public static String deleteString(String str, String baliseDeb, String baliseFin)
  {
	  return replaceString(str, baliseDeb, baliseFin, "");
  }

  /**
   * Return a vector with all substring delimited by a separator
   * @param str
   * @param separator
   * @return
   */
  public static Vector getVectorFromString(String str, String separator)
  {
    Vector ret = new Vector();
/*
    StringTokenizer strTock = new StringTokenizer(str, separator);
    while( strTock.hasMoreTokens() )
      ret.addElement(strTock.nextToken().trim());
*/
    if ( (str!=null) && (!"".equals(str.trim())) )
    {
      int index = str.indexOf(separator);
      if( index>=0 )
      {
        while( index>=0 )
        {
          int indexEnd=str.indexOf(separator, index+separator.length());
          if ( indexEnd>=0 )
            ret.addElement(str.substring(index+separator.length(), indexEnd));
          else
            ret.addElement(str.substring(index+separator.length()));
          index=indexEnd;
        }
      }
      else
        ret.addElement(str);
    }
    return ret;
  }
}