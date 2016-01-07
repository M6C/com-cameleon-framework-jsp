package ressource.hashtable.menu;

/**
 * Définit les propriete d'un menu (Affichage,chemin de l'image, cible par defaut,etc)<p>
 * Herite aussi des possibilites de droit de la classe.hashtable.page
 * @author ressource
 * @version 1.0
 */

import java.util.Vector;

public class Menu {

	/** hashTable */
	private java.util.Hashtable hashTable = new java.util.Hashtable();

	/**
	 * Constructeur
	 */
  public Menu() {
  }


	/**
	 * Retourne le vecteur de propriete de l'objet Menu_hashTable
	 * de la menu correspondant.
	 * @param key  Nom du repertoire qui sert de clef.
	 * @return Vecteur contenant toutes les propriete de la menu dans l'ordre
	 * suivant : <p>
	 *   0 : Niveau de profondeur du menu <p>
	 *   1 : Libelle de la menu <p>
	 *   2 : Ordre d'affichage dans le quel il doit apparaitre <p>
	 *   3 : Chemin de la cible par defaut <p>
	 *   4 : Chemin de l'image du menu
	 *   5 : Droit du menu
	 */
	 private Vector getProprietes(String strKey) {
		return (Vector)hashTable.get(strKey);
	}


	/**
	 * Initialise le vecteur de propriete de l'objet menu_hashTable
	 * de la menu correspondant.
	 * @param key  Nom du repertoire qui sert de clef.
	 * @param vecpropriete  Vecteur contenant toutes les propriete de la menu
	 * dans l'ordre suivant : <p>
	 *    0 : Niveau de profondeur du menu <p>
	 *    1 : Libelle de la menu <p>
	 *    2 : Ordre d'affichage dans le quel il doit apparaitre <p>
	 *    3 : Chemin de la cible par defaut <p>
	 *    4 : Chemin de l'image du menu <p>
	 *    5 : Droit du menu
	 */
	private void setProprietes(String strKey,  Vector vecPropriete) {
		this.hashTable.put(strKey, vecPropriete);
	}


	/**
	 * Retourne le libelle du menu <p>
	 * position 1 dans le vecteur de propriete.
	 * @param key  Nom du repertoire qui sert de clef.
	 * @return Le libelle du menu
	 */
	public String getLibelle(String strKey) {
			return this.getProprietes(strKey).elementAt(1).toString();
	}


	/**
	 * Initialise le libelle du menu <p>
	 * position 1 dans le vecteur de propriete.
	 * @param key  Nom du repertoire qui sert de clef.
	 * @param param  Valeur a initilise,si null c'est initialise a ""
	 * @throws ressource.hashtable.exceptions.Exceptions Exception sur les propriétés de la page
	 */
	public void setLibelle(String strKey, String param) throws ressource.hashtable.exceptions.Exceptions {
		checkKey(strKey);
		if (param !=null) {
			Vector vecTemp = this.getProprietes(strKey);
			vecTemp.setElementAt(param,1);
			this.setProprietes(strKey, vecTemp);
		}
		else {
			Vector vecTemp = this.getProprietes(strKey);
			vecTemp.setElementAt("", 1);
			this.setProprietes(strKey, vecTemp);
		}
	}

	/**
	 * Initialise le niveau de profondeurs de la menu <p>
	 * position 0 dans le vecteur de propiete.
	 * @param key    Nom du repertoire qui sert de clef.
	 * @param param  Valeur a initilise,si null c'est initialise a ""
	 * @throws ressource.hashtable.exceptions.Exceptions Exception sur les propriétés de la page
	 */
	public void setNiveau(String strKey, String param) throws ressource.hashtable.exceptions.Exceptions {
		checkKey(strKey);
		if (param !=null) {
			Vector vecTemp = this.getProprietes(strKey);
			vecTemp.setElementAt(param, 0);
			this.setProprietes(strKey, vecTemp);
		}
		else {
			Vector vecTemp = this.getProprietes(strKey);
			vecTemp.setElementAt("", 0);
			this.setProprietes(strKey, vecTemp);
		}
	}


	/**
	 * Retourne le niveau de profondeurs de la menu <p>
	 * position 0 dans le vecteur de propriete.
	 * @param key  Nom du repertoire qui sert de clef.
	 * @return Le niveau de la page/menu
	 */
	public String getNiveau(String strKey) {
		return this.getProprietes(strKey).elementAt(0).toString();
	}


	/**
	 * Initialise l'ordre d'affichage du menu <p>
	 * position 2 dans le vecteur de propriete.
	 * @param key    Nom du repertoire qui sert de clef.
	 * @param param  la Valeur a initilisé,si null c'est initialise a ""
	 * @throws ressource.hashtable.exceptions.Exceptions Exception sur les propriétés de la page
	 */
	public void setOrdreAffichage(String strKey, String param)  throws ressource.hashtable.exceptions.Exceptions{
		checkKey(strKey);
		if (param != null) {
			Vector vecTemp = this.getProprietes(strKey);
			vecTemp.setElementAt(param,2);
			this.setProprietes(strKey, vecTemp);
		}
		else {
			Vector vecTemp = this.getProprietes(strKey);
			vecTemp.setElementAt(param,2);
			this.setProprietes(strKey, vecTemp);
		}
	}


	/**
	 * Retourne l'ordre d'affichage du menu <p>
	 * position 2 dans le vecteur de propriete.
	 * @param key  Nom du repertoire qui sert de clef.
	 * @return L' ordre d'apparition du menu
	 */
	public String getOrdreAffichage(String strKey) {
		return this.getProprietes(strKey).elementAt(2).toString();
	}


	/**
	 * Initialise le chemin de la cible par defaut de ce menu <p>
	 * position 3 dans le vecteur de propriete.
	 * @param key  Nom du repertoire qui sert de clef.
	 * @param param Valeur a initilise,si null c'est initialise a "" (Pas de secu)
	 * @throws ressource.hashtable.exceptions.Exceptions Exception sur les propriétés de la page
	 */
	public void setCible(String strKey,String param) throws ressource.hashtable.exceptions.Exceptions {
		checkKey(strKey);
		if (param !=null) {
			Vector vecTemp = this.getProprietes(strKey);
			vecTemp.setElementAt(param,3);
			this.setProprietes(strKey, vecTemp);
		}
		else {
			Vector vecTemp = this.getProprietes(strKey);
			vecTemp.setElementAt("",3);
			this.setProprietes(strKey, vecTemp);
		}
	}


	/**
	 * Retourne le chemin de la cible par defaut du menu <p>
	 * position 3 dans le vecteur de propriete.
	 * @param key  Nom du repertoire qui sert de clef.
	 * @return Le chemin de l'image du menu ("" si pas de chemin)
	 */
	public String getCible(String strKey) {
		return this.getProprietes(strKey).elementAt(3).toString();
	}


	/**
	 * Initialise le chemin de l'image pour un menu <p>
	 * position 4 dans le vecteur de propriete.
	 * @param key  Nom du repertoire qui sert de clef.
	 * @param param  Valeur a initilise,si null c'est initialise a "no image"
	 * (Pas de secu)
	 * @throws ressource.hashtable.exceptions.Exceptions Exception sur les propriétés de la page
	 */
	public void setCheminImage(String strKey,String param) throws ressource.hashtable.exceptions.Exceptions  {
		checkKey(strKey);
		if (param !=null) {
			Vector vecTemp = this.getProprietes(strKey);
			vecTemp.setElementAt(param,4);
			this.setProprietes(strKey, vecTemp);
		}
		else {
			throw new ressource.hashtable.exceptions.ExceptionsNull(strKey);
		}
	}


	/**
	 * Retourne le chemin de l'image du menu <p>
	 * position 4 dans le vecteur de propriete.
	 * @param key  Nom du repertoire qui sert de clef.
	 * @return Le chemin de l'image du menu ("no image" si pas de chemin)
	 */
	public String getCheminImage(String strKey) {
			return this.getProprietes(strKey).elementAt(4).toString();
	}


	/**
	 * Ajoute une entree a l'objet, et initialise chaque propriete a une valeur
	 * par defaut, dans l'ordre suivant : <p>
	 *   0 : Niveau de profondeur du menu/page <p>
	 *   1 : Libelle de la page/menu <p>
	 *   2 : Ordre d'affichage dans le quel il doit apparaitre <p>
	 *   3 : Chemin de la cible par defaut <p>
	 *   4 : Chemin de l'image du menu <p>
	 *   5 : Droit<p>
	 * @param key  Nom du repertoire qui sert de clef.
	 */
	 public void addElement(String strKey) {
			Vector proprietes = new Vector();
			proprietes.addElement("0");
			proprietes.addElement("");
			proprietes.addElement("");
			proprietes.addElement("");
			proprietes.addElement("");
			proprietes.addElement("0");
			this.hashTable.put(strKey, proprietes);
	}


	/**
	 * verifie si la clef est nulle ou nom
	 * @param key  Nom du repertoire qui sert de clef.
	 * @throws ressource.hashtable.exceptions.Exceptions Exception sur les propriétés de la page
	 */
	private void checkKey(String strKey) throws ressource.hashtable.exceptions.Exceptions {
	  if (strKey == null) {
	    throw new ressource.hashtable.exceptions.ExceptionsKey();
		}
	}


	/**
	 * Initialise le niveau de droit de la page/menu
	 * @param key    Nom du repertoire qui sert de clef.
	 * @param param  Valeur a initilise,si null c'est initialise a "0"
	 *
	 * FGA*14/08/2001 : ajout d'une confition pour ajout des droits
	 * FGA*14/08/2001 : param <> chaine vide !
	 */
	public void setDroit(String strKey, String param) throws ressource.hashtable.exceptions.Exceptions {
		checkKey(strKey);
		if (param != null) {
				if (!param.equals("")) {
					Vector vecTemp = this.getProprietes(strKey);
					vecTemp.setElementAt(param, 5);
					this.setProprietes(strKey, vecTemp);
				}
		}
		else {
			throw new ressource.hashtable.exceptions.ExceptionsNull(strKey);
		}
	}


	/**
	 * Retourne le niveau de securite du menu
	 * @param key  Nom du repertoire qui sert de clef.
	 * @return  Le niveau de securite du menu
	 */
	public String getDroit(String strKey) {
		return this.getProprietes(strKey).elementAt(5).toString();
	}


	/**
	 * Retourne si la clef existe ou non
	 * @param key  Nom du repertoire qui sert de clef.
	 * @return True si la clef est dans la table
	 */
	public boolean isExist(String strKey) {
		return this.hashTable.containsKey(strKey);
	}

}