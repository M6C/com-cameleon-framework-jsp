package ressource.hashtable.menu;

/**
 * Titre :
 * Description :
 * Copyright :    Copyright (c) 2001
 * Société :
 * @author
 * @version 1.0
 */


/**
 * Définit la structure de menu<p>
 * Herite aussi des possibilites de droit de la classe.hashtable.page
 * @author ressource
 * @version 1.0
 */

import java.util.Vector;

public class TreeMenu {
  /** hashTable */
  private java.util.Hashtable hashTableFrere = new java.util.Hashtable();
  private java.util.Hashtable hashTableAction = new java.util.Hashtable();
  private java.util.Hashtable hashTableParent = new java.util.Hashtable();

  /**
   * Constructeur
   */

   public Vector getFreres(String strKey) {
    Object resultat;
    resultat = this.hashTableFrere.get(strKey);
    if (resultat==null)  {
      return new Vector();
    } else {
      return (Vector) resultat;
    }
  }

   public String getAction(String strKey) {
          return (String)this.hashTableAction.get((Object)strKey);
  }

   public String getParent(String strKey) {
    if ((this.hashTableParent.get(strKey)==null)||("".equals((String)this.hashTableParent.get(strKey)))) {
          return null;
      } else {
          return (String) this.hashTableParent.get(strKey);
      }
  }


  public void setFreres(String strKey,  Vector vecPropriete) {
          this.hashTableFrere.put(strKey, vecPropriete);
  }

 public void setParent(String strKey,  String parent) {
          this.hashTableParent.put(strKey, parent);
  }

 public void setAction(String strKey,  String Action) {
          this.hashTableAction.put(strKey, Action);
  }

  public TreeMenu() {
  }
}