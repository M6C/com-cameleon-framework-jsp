package ressource.database;

import ressource.*;
import framework.trace.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class FxQuery {

  public final static String CST_EGUALE = "=";
  public final static String CST_EGUALE_SUP = "=>";
  public final static String CST_EGUALE_INF = "<=";
  public final static String CST_DIFFERENT = "<>";
  public final static String CST_SUPERIEUR = ">";
  public final static String CST_INFERIEUR = "<";

  protected FxQuery() {
  }

  public static String makeSelect(String table, String column, String columnWhere, String valueWhere){
    String ret = new String();

    String szGuil = isText(valueWhere) ? "'" : "";

    ret = "SELECT " + column;
    ret += " FROM " + table;
    ret += " WHERE " + columnWhere + CST_EGUALE + szGuil + valueWhere + szGuil;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeSelect(String table, String column, String condition){
    String ret = new String();

    ret = "SELECT " + column;
    ret += " FROM " + table;
    ret += " WHERE " + condition;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeSelect(String table, String column){
    String ret = new String();

    ret = "SELECT " + column;
    ret += " FROM " + table;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeSelect(Vector table, Vector column, Vector condition){
    int  li_nbTable = table.size();
    int  li_nbColumn = column.size();
    int  li_nbCondition = condition.size();
    String  ret  = new String();
    String ls_select = new String();
    String ls_from  = new String();
    String ls_where = new String();

    int i;

    if(li_nbColumn > 1) {
      ls_select = "SELECT ";
      for(i=0;i<li_nbColumn;i++){
        ls_select += column.elementAt(i);
        if(i<li_nbColumn - 1) ls_select += ", ";
      }
      } else ls_select = "SELECT " + column.elementAt(0);

    if(li_nbTable > 1) {
      ls_from = " FROM ";
      for(i=0;i<li_nbTable;i++){
        ls_from += table.elementAt(i);
        if(i<li_nbTable - 1) ls_from += ", ";
      }
      } else ls_from = " FROM " + table.elementAt(0);

    if(li_nbCondition > 1) {
      ls_where = " WHERE ";
      for(i=0;i<li_nbCondition;i++){
        ls_where += condition.elementAt(i);
        if(i<li_nbCondition - 1) ls_where += " AND ";
      }
      } else if(condition.elementAt(0) != "") ls_where = " WHERE " + condition.elementAt(0);

    ret = ls_select + ls_from + ls_where;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeSelect(String table, Vector columnSelect, Vector columnWhere, Vector valueWhere)
  {
    int  li_nbColumnSelect = columnSelect.size();
    int  li_nbColumnWhere = columnWhere.size();
    int  li_nbValueWhere  = valueWhere.size();
    String  ret      = new String();
    String ls_select = new String();
    String ls_from   = new String();
    String ls_where  = new String();
    String szGuil    = new String();

    int i=0, count=0;

    if(li_nbColumnSelect > 1)
    {
      ls_select = "SELECT ";
      for(i=0;i<li_nbColumnSelect;i++)
      {
        ls_select += (String)columnSelect.elementAt(i);
        if(i<li_nbColumnSelect - 1) ls_select += ", ";
      }
    }
    else
      ls_select = "SELECT " + (String)columnSelect.elementAt(0);

    ls_from = " FROM " + table;

    if(li_nbValueWhere > 1)
    {
      ls_where = " WHERE ";
      for(i=0, count=0;i<li_nbValueWhere;i++)
      {
        if ( (valueWhere.elementAt(i)!=null) && (!"".equals(((String)valueWhere.elementAt(i)).trim())) )
        {
          if(count>0)
            ls_where += " AND ";
          szGuil = isText((String)valueWhere.elementAt(i)) ? "'" : "";
          ls_where += columnWhere.elementAt(i).toString() + CST_EGUALE + szGuil + valueWhere.elementAt(i).toString() + szGuil;
          count++;
        }
      }
    }
    else if(!"".equals(valueWhere.elementAt(0).toString().trim()))
    {
      szGuil = isText((String)valueWhere.elementAt(0)) ? "'" : "";
      ls_where = " WHERE " + columnWhere.elementAt(0).toString() + CST_EGUALE + szGuil + valueWhere.elementAt(0).toString() + szGuil;
    }

    ret = ls_select + ls_from + ls_where;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeSelect(String table, Vector columnSelect, String columnWhere, String valueWhere)
  {
    int  li_nbColumnSelect = columnSelect.size();
    String  ret      = new String();
    String ls_select = new String();
    String ls_from   = new String();
    String ls_where  = new String();
    String szGuil    = new String();

    int i=0, count=0;

    if(li_nbColumnSelect > 1)
    {
      ls_select = "SELECT ";
      for(i=0;i<li_nbColumnSelect;i++)
      {
        ls_select += (String)columnSelect.elementAt(i);
        if(i<li_nbColumnSelect - 1) ls_select += ", ";
      }
    }
    else
      ls_select = "SELECT " + (String)columnSelect.elementAt(0);

    ls_from = " FROM " + table;

    szGuil = isText(valueWhere) ? "'" : "";
    ls_where = " WHERE " + columnWhere + CST_EGUALE + szGuil + valueWhere + szGuil;

    ret = ls_select + ls_from + ls_where;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeSelect(String table, String columnSelect, Vector columnWhere, Vector valueWhere)
  {
    int  li_nbColumnWhere = columnWhere.size();
    int  li_nbValueWhere  = valueWhere.size();
    String  ret      = new String();
    String ls_select = new String();
    String ls_from   = new String();
    String ls_where  = new String();
    String szGuil    = new String();

    int i=0, count=0;

    ls_select = "SELECT " + columnSelect;

    ls_from = " FROM " + table;

    if(li_nbValueWhere > 1)
    {
      ls_where = " WHERE ";
      for(i=0, count=0;i<li_nbValueWhere;i++)
      {
        if ( (valueWhere.elementAt(i)!=null) && (!"".equals(((String)valueWhere.elementAt(i)).trim())) )
        {
          if(count>0)
            ls_where += " AND ";
          szGuil = isText((String)valueWhere.elementAt(i)) ? "'" : "";
          ls_where += columnWhere.elementAt(i).toString() + CST_EGUALE + szGuil + valueWhere.elementAt(i).toString() + szGuil;
          count++;
        }
      }
    }
    else if(!"".equals(valueWhere.elementAt(0).toString().trim()))
    {
      szGuil = isText((String)valueWhere.elementAt(0)) ? "'" : "";
      ls_where = " WHERE " + szGuil + valueWhere.elementAt(0).toString() + szGuil;
    }

    ret = ls_select + ls_from + ls_where;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeSelect(Vector table, Vector column, String condition){
    int  li_nbTable = table.size();
    int  li_nbColumn = column.size();
    String  ret  = new String();
    String ls_select = new String();
    String ls_from  = new String();
    String ls_where = new String();

    int i;

    if(li_nbColumn > 1) {
      ls_select = "SELECT ";
      for(i=0;i<li_nbColumn;i++){
        ls_select += column.elementAt(i);
        if(i<(li_nbColumn - 1)) ls_select += ", ";
      }
      } else ls_select = "SELECT " + column.elementAt(0);

    if(li_nbTable > 1)
    {
      ls_from = " FROM ";
      for(i=0;i<li_nbTable;i++)
      {
        ls_from += table.elementAt(i);
        if(i<(li_nbTable - 1))
          ls_from += ", ";
      }
    }
    else
      ls_from = " FROM " + table.elementAt(0);

    ls_where = " WHERE " + condition;

    ret = ls_select + ls_from + ls_where;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeSelect(String table, Vector column){
    int  li_nbColumn = column.size();
    String  ret  = new String();
    String ls_select = new String();
    String ls_from  = new String();
    String ls_where = new String();

    int i;

    if(li_nbColumn > 1) {
      ls_select = "SELECT ";
      for(i=0;i<li_nbColumn;i++){
        ls_select += column.elementAt(i);
        if(i<(li_nbColumn - 1)) ls_select += ", ";
      }
      } else ls_select = "SELECT " + column.elementAt(0);

    ls_from = " FROM " + table;

    ret = ls_select + ls_from;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeUpdate(String table, String column){
    String  ret = new String();

    ret = "UPDATE " + column;
    ret += " SET "  + table;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeUpdate(String table, String column, String condition){
    String ret = new String();

    ret = "UPDATE " + column;
    ret += " SET "  + table;
    ret += " WHERE " + condition;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeUpdate(String table, Vector column, String condition){
    String  ret  = new String();
    int  li_nbColumn = column.size();

    int i;

    ret = "UPDATE " + table;

    if(li_nbColumn > 1)
    {
      ret += " SET ";
      for(i=0;i<li_nbColumn;i++)
      {
        ret += column.elementAt(i);
        if(i<li_nbColumn- 1) ret += ", ";
      }
    }
    else
      ret += " SET " + column.elementAt(0);

    ret += " WHERE " + condition;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeUpdate(Vector table, Vector column, Vector condition){
    int  li_nbTable = table.size();
    int  li_nbColumn = column.size();
    int  li_nbCondition = condition.size();
    String  ret  = new String();
    String ls_update = new String();
    String ls_set  = new String();
    String ls_where = new String();

    int i;

    if(li_nbTable > 1)
    {
      ls_update = "UPDATE ";
      for(i=0;i<li_nbTable;i++)
      {
        Object item = table.elementAt(i);
        ls_update += item.toString();
        if(i<li_nbTable - 1)
          ls_update += ", ";
      }
    }
    else
      ls_update = "UPDATE " + table.elementAt(0);

    if(li_nbColumn > 1) {
      ls_set = " SET ";
      for(i=0;i<li_nbColumn;i++){
        ls_set += column.elementAt(i);
        if(i<li_nbColumn- 1) ls_set += ", ";
      }
      } else ls_set = " SET " + column.elementAt(0);

    if(li_nbCondition > 1) {
      ls_where = " WHERE ";
      for(i=0;i<li_nbCondition;i++)
      {
        ls_where += condition.elementAt(i);
        if(i<li_nbCondition - 1)
          ls_where += " AND ";
      }
    }
    else if(condition.elementAt(0) != "")
      ls_where = " WHERE " + condition.elementAt(0);

    ret = ls_update + ls_set + ls_where;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeInsert(String tables, String columns, String values){
    boolean lb_values  = false;
    String  ret    = new String();
    String  ls_insert = "INSERT INTO " + tables;
    String  ls_cols  = " (" + columns + ") ";
    String  ls_values = " VALUES (";

    values = FxDate.DateSQL(values);
    lb_values = isText(values);
    if(lb_values)
      ls_values += "'";

    ls_values += values;

    if(lb_values)
      ls_values += "'";

    ls_values += ")";

    ret = ls_insert + ls_cols + ls_values;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeInsert(String tables, Vector columns, Vector values){
    int  li_nbColumns = columns.size();
    int  li_nbValues = values.size();
    String  ret  = new String();
    String ls_insert = new String();
    String ls_cols  = new String();
    String ls_values = new String();
    boolean lb_values = false;

    int i=0;

    ls_insert = "INSERT INTO " + tables;

    ls_cols = " (";
    for(i=0;i<li_nbColumns;i++)
    {
      ls_cols += columns.elementAt(i);
      if(i<li_nbColumns - 1)
        ls_cols += ", ";
    }
    ls_cols += ")";

    ls_values = " VALUES (";
    for(i=0;i<li_nbValues;i++)
    {
      Object item = values.elementAt(i);
      if ( item!=null )
      {
        values.setElementAt(FxDate.DateSQL(item.toString()), i);
        lb_values = isText(item.toString());
        if(lb_values)
          ls_values += "'";
        ls_values += item.toString();
        if(lb_values)
        {
          if(i<(li_nbValues-1))
            ls_values += "',";
          else
            ls_values += "'";
        }
        else if(i<(li_nbValues-1))
          ls_values += ",";
      }
    }
    ls_values += " )";

    ret = ls_insert + ls_cols + ls_values;

    Trace.DEBUG(ret);

    return ret;
  }

  public static String makeInsert(Vector tables, Vector columns, Vector values){
    int  li_nbTables = tables.size();
    int  li_nbColumns = columns.size();
    int  li_nbValues = values.size();
    String  ret  = new String();
    String ls_insert = new String();
    String ls_cols  = new String();
    String ls_values = new String();
    boolean lb_values;

    int i=0;

    ls_insert = "INSERT INTO ";
    for(i=0;i<li_nbTables;i++){
      ls_insert += tables.elementAt(i);
      if(i<li_nbTables - 1) ls_insert += ", ";
    }

    ls_cols = " (";
    for(i=0;i<li_nbColumns;i++){
      ls_cols += columns.elementAt(i);
      if(i<li_nbColumns - 1) ls_cols += ",";
    }ls_cols += ") ";

    ls_values = " VALUES (";
    for(i=0;i<li_nbValues;i++)
    {
      values.setElementAt(FxDate.DateSQL((String)values.elementAt(i)), i);
      lb_values = isText((String)values.elementAt(i));
      if(lb_values) ls_values += "'";
      ls_values += values.elementAt(i);
      if(lb_values)
      {
        if(i<li_nbValues - 1)
          ls_values += "',";
        else
          ls_values += "'";
      }
      else if(i<li_nbValues - 1)
        ls_values += ",";
    }
    ls_values += ") ";

    ret = ls_insert + ls_cols + ls_values;

    Trace.DEBUG(ret);

    return ret;
  }

  protected static boolean isText(String p_str){
    try
    {
      return new Float(p_str).isNaN();
    }
    catch (NumberFormatException e)
    {
      return true;
    }
  }
}