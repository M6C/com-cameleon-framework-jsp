package ressource.database;

import java.sql.*;
import framework.trace.*;
import java.util.*;

public class FxDatabase {

  protected FxDatabase(){
  }

  public static Vector executeQUERY(String pDriver, String pUrl, String pUser, String pUserPwd, String pQuery) throws SQLException
  {
    Vector ret = null;
    Statement stmt = null;
    Connection con = null;
    ResultSet res = null;

    try
    {
      Class.forName(pDriver);
      con = DriverManager.getConnection(pUrl, pUser, pUserPwd);
      stmt = con.createStatement();
      // Values are inserted into the table with
      // the next three statements.
      res = stmt.executeQuery(pQuery);
      ret = readResultSet(res);
    }
    catch(ClassNotFoundException e)
    {
      Trace.ERROR(e);
    }
    finally
    {
      // Attempt to clean up by calling the
      // java.sql.ResultSet.close() method.
      if (res!=null)
        res.close();
      // Attempt to clean up by calling the
      // java.sql.Statement.close() method.
      if(stmt!=null)
        stmt.close();
      // This way the connection will be closed even when exceptions are thrown
      // earlier. This is important, because you may have trouble reopening
      // a JDatastore file after leaving a connection to it open.
      if(con!=null)
        con.close();
    }
    return ret;
  }

  public static Vector readResultSet(ResultSet res) throws SQLException
  {
    Vector ret = null;
    while(res.next())
    {
      if(ret==null) ret = new Vector();
      ResultSetMetaData rsmd = res.getMetaData();
      Vector item = new Vector();
      for (int i=1; i<=rsmd.getColumnCount(); i++)
      {
        item.add(res.getObject(i));
      }
      ret.addElement(item);
    }
    return ret;
  }
  public static int executeUPDATE(String pDriver, String pUrl, String pUser, String pUserPwd, String pQuery) throws SQLException
  {
    int ret = 0;
    Statement stmt = null;
    Connection con = null;
    try
    {
      Class.forName(pDriver);
      con = DriverManager.getConnection(pUrl, pUser, pUserPwd);
      stmt = con.createStatement();
      // Values are inserted into the table with
      // the next three statements.
      ret = stmt.executeUpdate(pQuery);
    }
    catch(ClassNotFoundException e)
    {
      Trace.ERROR(e);
    }
    finally
    {
      // Attempt to clean up by calling the
      // java.sql.Statement.close() method.
      if(stmt!=null)
        stmt.close();
      // This way the connection will be closed even when exceptions are thrown
      // earlier. This is important, because you may have trouble reopening
      // a JDatastore file after leaving a connection to it open.
      if(con!=null)
        con.close();
    }
    return ret;
  }
}