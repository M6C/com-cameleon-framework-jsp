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

public class FxVector {

  protected FxVector() {
  }

  /**
   * Return true if str is in vct
   * @param str
   * @param vct
   * @return
   */
  public static boolean isStringInVector(String str, Vector vct)
  {
    boolean ret = false;
    for ( int i=0 ; (i<vct.size()) && (!ret) ; i++ )
    {
      String item = (String)vct.elementAt(i);
      ret = item.toUpperCase().equals(str.toUpperCase());
    }
    return ret;
  }
}