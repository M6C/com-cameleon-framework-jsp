package ressource.jsp.multilangue;

import java.util.Hashtable;

/**
 * Titre :        MultiLangue
 * Description :  Gestion du multi langue des libelles des pages de présentation.
 * Copyright :    Copyright (c) 2001
 * Société :      Groupe ressource
 * @author FGA
 * @version 1.0
 */

public class HashBloc extends Hashtable {

	/** key réservé */
	public final static String BLOC_TITRE = "1";
	public final static String BLOC_SOUS_TITRE = "2";
	public final static String BLOC_BANDEAU = "3";

	/**
	 * Constructeur par défaut
	 */
  public HashBloc() {
		super();
  }

  /**
   * @return String titre de la page
   */
  public String getTitre() {
    return (String)this.get(BLOC_TITRE);
  }

  /**
   * @return le sous-titre de la page
   */
  public String getSousTitre() {
    return (String)this.get(BLOC_SOUS_TITRE);
  }

  /**
   * @return le bandeau de la page
   */
  public String getBandeau() {
    return (String)this.get(BLOC_BANDEAU);
  }

}