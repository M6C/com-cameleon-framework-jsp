package ressource.jsp.tableau;

import java.util.*;

/**
 * Package qui permet la creation d'un tableau de valeurs
 * compatible avec Excel.
 */

public class Jsp_Excel extends ressource.jsp.tableau.Jsp_Tableau {

  public Jsp_Excel() {
  }

  public String jsp_CreateCSV(Vector head, Vector row) {
    StringBuffer res = new StringBuffer();

    for( int i=0 ; i < head.size() ; i++ )
    {
      Vector element = (Vector)head.elementAt(i);
      res.append(this.jsp_GetHeadName(element) + ";");
    }
    res.append( "\n" );

    for( int i=0 ; i < row.size() ; i++ )
    {
      Vector element = (Vector)row.elementAt(i);

      for( int j=0 ; j < element.size() ; j++ )
      {
        res.append( (String)element.elementAt(j) + ";" );
      }
      res.append( "\n" );
    }

    return res.toString();
  }
}