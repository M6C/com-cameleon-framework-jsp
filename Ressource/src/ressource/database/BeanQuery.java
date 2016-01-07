package ressource.database;

import java.util.*;
import framework.trace.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class BeanQuery {

  public final static int CST_TYPE_SELECT = 1;
  public final static int CST_TYPE_UPDATE = 2;
  public final static int CST_TYPE_INSERT = 3;

  public static final String CST_SELECT = " SELECT ";
  public static final String CST_FROM = " FROM ";
  public static final String CST_WHERE = " WHERE ";
  public static final String CST_AND = " AND ";
  public static final String CST_OR = " OR ";
  public static final String CST_FIELD_SEPARATOR = ", ";
  public static final String CST_EGAL = " = ";
  public static final String CST_DIFFERENT = " <> ";
  public static final String CST_SUPERIEUR = " > ";
  public static final String CST_INFERIEUR = " < ";
  public static final String CST_SUPERIEUR_EQUAL = " >= ";
  public static final String CST_INFERIEUR_EQUAL = " <= ";
  public static final String CST_IS = " IS ";
  public static final String CST_IS_NOT = " IS NOT ";
  public static final String CST_NULL = " NULL ";
  public static final String CST_IS_NULL = " IS NULL ";
  public static final String CST_IS_NOT_NULL = " IS NOT NULL ";

  protected StringBuffer sbQuery = new StringBuffer();

  protected int iType = 0;

  protected Vector vField = new Vector();
  protected Vector vTable = new Vector();
  protected Vector vWhere = new Vector();

  public BeanQuery(int pType) {
    iType = pType;
  }

  public void addField(String szField)
  {
    vField.addElement(formatField(szField));
  }
  public void addField(Vector pField)
  {
    for( int i=0 ; i<pField.size() ; i++ )
      addField(pField.elementAt(i).toString());
  }
  public void addTable(String szTable)
  {
    vTable.addElement(szTable);
  }
  public void addTable(Vector pTable)
  {
    for( int i=0 ; i<pTable.size() ; i++ )
      vTable.addElement(pTable.elementAt(i));
  }
  public void addWhere(String szField, String szCondition)
  {
    addWhere(szField, szCondition, "");
  }
  public void addWhere(String szField, String szCondition, String szValue)
  {
    addWhere(szField, szCondition, szValue, CST_AND);
  }
  public void addWhere(String szField, String szCondition, String szValue, String szAND_OR)
  {
    StringBuffer szWhere = new StringBuffer();
    if ( vWhere.size()>0 )
      szWhere.append(szAND_OR);
    szWhere.append(szField);
    szWhere.append(szCondition);
    szWhere.append(szValue);
    vWhere.addElement(szWhere.toString());
  }

  public String toString()
  {
    if (iType == this.CST_TYPE_SELECT)
    {
      makeQuerySelect();
    }
    Trace.DEBUG(this, sbQuery.toString());
    return sbQuery.toString();
  }

  public static String formatAlias(String szField)
  {
    return szField.toString().replace('.', '_');
  }

  protected String formatField(String szField)
  {
    StringBuffer ret = new StringBuffer(szField);
    ret.append(" ");
    ret.append(formatAlias(szField));
    return ret.toString();
  }

  protected void makeQuerySelect()
  {
    int  li_nbTable = vTable.size();
    int  li_nbColumn = vField.size();
    int  li_nbCondition = vWhere.size();

    int i;

    //SELECT
    if(li_nbColumn > 1)
    {
      sbQuery.append(this.CST_SELECT);
      for(i=0;i<li_nbColumn;i++)
      {
        sbQuery.append(vField.elementAt(i));
        if(i<li_nbColumn - 1)
          sbQuery.append(this.CST_FIELD_SEPARATOR);
      }
    }
    else
    {
      sbQuery.append(this.CST_SELECT);
      sbQuery.append(vField.elementAt(0));
    }

    //FROM
    if(li_nbTable > 1)
    {
      sbQuery.append(this.CST_FROM);
      for(i=0;i<li_nbTable;i++)
      {
        sbQuery.append(vTable.elementAt(i));
        if(i<li_nbTable - 1)
          sbQuery.append(this.CST_FIELD_SEPARATOR);
      }
    }
    else
    {
      sbQuery.append(this.CST_FROM);
      sbQuery.append(vTable.elementAt(0));
    }

    // WHERE
    if(li_nbCondition > 1) {
      sbQuery.append(this.CST_WHERE);
      for(i=0;i<li_nbCondition;i++)
      {
        sbQuery.append(vWhere.elementAt(i));
/*
        if(i<li_nbCondition - 1)
          sbQuery.append(this.CST_AND);
*/
      }
    }
    else if(vWhere.elementAt(0) != "")
    {
      sbQuery.append(this.CST_WHERE);
      sbQuery.append(vWhere.elementAt(0));
    }
  }
}