package ressource.jsp.menu;


import ressource.jsp.directory.Jsp_Directory;
import ressource.jsp.liens.Jsp_Liens;
import ressource.hashtable.menu.Menu;
import ressource.hashtable.menu.TreeMenu;
import ressource.FxUtils;
import java.util.Vector;
import java.io.File;
import ressource.jsp.securite.droit.ListeActionsUser;

/**
 * Construction du menu dynamique
 * @author ressource
 * @version 1.0
 *
 * FGA*14/08/2001 : Modification pour prendre en compte les droits du user
 * FGA*14/08/2001 : Prise en compte d'un objet contenant les droits ListeActionsUser
 * FGA*14/08/2001 : Nettoyage du code.
 *
 * @see ressource.hashtable.menu.Menu
 */


public class Jsp_Menu {

	/** Disposition possible des menu */
	public final static String MENU_HORIZONTAL = "H";
	public final static String MENU_VERTICAL = "V";

	/** Largeur des images du menu */
	private String largeur = "";

	/** Hauteur des images du menu */
	private String hauteur = "";

	/** Etat vertical ou horizontal du menu */
	private String sens = MENU_HORIZONTAL;

	/** Gestion des liens */
	private Jsp_Liens link = new Jsp_Liens();

	/** droit du user */
	ListeActionsUser listeActionsUser = null;

	/** profil du user */
	String profilUser = null;


	/**
	 * Constructeur par défaut
	 */
	public void Jsp_Menu() {
	}


	/**
	 * Initialise la largeur des images du menu.
	 * @param pLargeur  La largeur des images.
	 */
	public void setLargeur (String pLargeur) {
		if (pLargeur != null ) {
			this.largeur = pLargeur;
		}
	}


	/**
	 * Retourne la largeur des images du menu.
	 * @return La largeur des images.
	 */
	public String getLargeur () {
		return this.largeur ;
	}


	/**
	 * Initialise la hauteur des images du menu.
	 * @param pHauteur  La hauteur des images.
	 */
	public void setHauteur (String pHauteur) {
		if (pHauteur != null) {
			this.hauteur = pHauteur;
		}
	}


	/**
	 * Retourne la hauteur des images du menu.
	 * @return La hauteur des images.
	 */
	public String getHauteur () {
		return this.hauteur ;
	}


	/**
	 * Initialise le sens du menu.
	 * @param strSens  Le sens menu.
	 */
	public void setSens (String pSens) {
		if (pSens != null ) {
			if (pSens.equals(MENU_VERTICAL)||pSens.equals(MENU_HORIZONTAL)) {
				this.sens = pSens;
			}
		}
	}


	/**
	 * Retourne le sens du menu. <p>
	 *    MENU_HORIZONTAL : pour horizontal. <p>
	 *    MENU_VERTICAL : pour vertical. <p>
	 * @return Le sens du menu.
	 */
	public String getSens() {
		return this.sens ;
	}


	/**
	 * cree un menu qui ne change jamais, quelque soit la profondeur
	 * a la quelle on se trouve par rapport au root specifie.
	 * @param fichier fichier source (actuellement lu)
	 * @param Menu Liste des infos sur le menu
	 * @param userDroit droit du user
	 * @param niveau Niveau du menu souhaité
	 * @param hauteur Hauteur du menu
	 * @param largeur Largeur du menu
	 * @param Sens Sens du menu
	 * @return Code html pour creer menu dans un tableau avec le sens desire.
	 */
	public String jsp_CreerMenu(String fichier, Menu menu, String userDroit, String niveau, String hauteur, String largeur, String sens) {
		this.setSens(sens);
		this.setHauteur(hauteur);
		this.setLargeur(largeur);
		return this.jsp_CreerMenu(fichier, menu, userDroit, niveau);
	}
	public String jsp_CreerMenuXML(String fichier, Menu menu, TreeMenu tmenu, String userDroit, String niveau, String hauteur, String largeur, String sens) {
		this.setSens(sens);
		this.setHauteur(hauteur);
		this.setLargeur(largeur);
		return this.jsp_CreerMenuXML(fichier, menu,tmenu, userDroit, niveau );
	}


	/**
	 * cree un menu qui ne change jamais, quelque soit la profondeur
	 * a la quelle on se trouve par rapport au root specifie.
	 * @param fichier fichier source (actuellement lu)
	 * @param Menu Liste des infos sur le menu
	 * @param ListeActionsUser liste des actions possibles du user
	 * @param niveau Niveau du menu souhaité
	 * @param hauteur Hauteur du menu
	 * @param largeur Largeur du menu
	 * @param Sens Sens du menu
	 * @return Code html pour creer menu dans un tableau avec le sens desire.
	 */
	public String jsp_CreerMenu(String fichier, Menu menu, ListeActionsUser listeActionsUser, String niveau, String hauteur, String largeur, String sens) {
		this.setSens(sens);
		this.setHauteur(hauteur);
		this.setLargeur(largeur);
		return this.jsp_CreerMenu(fichier, menu, listeActionsUser, niveau);
	}

	public String jsp_CreerMenuXML(String fichier, Menu menu, TreeMenu tmenu, ListeActionsUser listeActionsUser, String niveau, String hauteur, String largeur, String sens) {
		this.setSens(sens);
		this.setHauteur(hauteur);
		this.setLargeur(largeur);
		return this.jsp_CreerMenuXML(fichier, menu, tmenu, listeActionsUser, niveau);
	}

	/**
	 * cree un menu qui ne change jamais, quelque soit la profondeur
	 * a la quelle on se trouve par rapport au root specifie.
	 * @param fichier fichier source (actuellement lu)
	 * @param Menu Liste des infos sur le menu
	 * @param pUserDroit droit du user
	 * @param niveau Niveau du menu souhaité
	 * @return Code html pour creer menu dans un tableau avec le sens desire.
	 */
	public String jsp_CreerMenu(String fichier, Menu menu, String pUserDroit, String niveau) {
		this.setProfilUser( pUserDroit );
		return this.creerMenu(fichier, menu, niveau);
	}
	public String jsp_CreerMenuXML(String fichier, Menu menu, TreeMenu tmenu, String pUserDroit, String niveau) {
		this.setProfilUser( pUserDroit );
		return this.creerMenuXML(fichier, menu, tmenu, niveau);
	}


	/**
	 * cree un menu qui ne change jamais, quelque soit la profondeur
	 * a la quelle on se trouve par rapport au root specifie.
	 * @param fichier fichier source (actuellement lu)
	 * @param Menu Liste des infos sur le menu
	 * @param ListeActionsUser liste des actions possibles du user
	 * @param niveau Niveau du menu souhaité
	 * @return Code html pour creer menu dans un tableau avec le sens desire.
	 */
	public String jsp_CreerMenu(String fichier, Menu menu, ListeActionsUser pListeActionsUser, String niveau) {
		this.setListeActionsUser( pListeActionsUser );
		return this.creerMenu(fichier, menu, niveau);
	}

	public String jsp_CreerMenuXML(String fichier, Menu menu, TreeMenu tmenu,ListeActionsUser pListeActionsUser, String niveau) {
		this.setListeActionsUser( pListeActionsUser );
		return this.creerMenuXML(fichier, menu, tmenu, niveau);
	}

	/**
	 * cree un menu qui ne change jamais, quelque soit la profondeur
	 *  à laquelle on se trouve par rapport au root spécifié.
	 * @param fichier fichier source (actuellement lu)
	 * @param Menu Liste des infos sur le menu
	 * @param niveau Niveau du menu souhaité
	 * @return Code html pour creer menu dans un tableau avec le sens desire.
	 */
	private String creerMenuXML(String uri_page, Menu menu, TreeMenu treemenu, String niveau) {

		// buffer de retour
		StringBuffer res = new StringBuffer();
                String parent  = null;
                parent = this.rechercheNiveauXML(uri_page, niveau, menu,treemenu);

		if (parent != null) {

			// on recupere les freres
			Vector vecFreres =  treemenu.getFreres(parent);

			// on filtre par le niveau
			vecFreres = this.filtreXML(menu, niveau, vecFreres);

			// on filtre et ordonne par les droits et l'ordre d'affichage
			vecFreres = this.jsp_OrderMenuXML(vecFreres, menu);

			// on cree le menu en HTML
			if (vecFreres.size() != 0) {
				res.append( this.jsp_ConstruireMenuXML(vecFreres, parent, menu, this.getSens()) );
			}
		} // FGA

		return res.toString();
	}

	private String creerMenu(String fichier, Menu menu, String niveau) {

		// buffer de retour
		StringBuffer res = new StringBuffer();

		// fichier correspondant au fichier courant
		File file = new File(fichier);

		// recherche du repertoire du niveau desire
		file = this.rechercheNiveau(file, niveau, menu);

		// FGA*13/08/2001 : Ajout d'un test
		// On ne fait si file est null
		// En +, ca évite d'avoir une exception
		if (file != null) {

			// on recupere les freres
			Vector vecFreres = this.getFreres(file);

			// on filtre par le niveau
			vecFreres = this.filtre(menu, niveau, vecFreres);

			// on filtre et ordonne par les droits et l'ordre d'affichage
			vecFreres = this.jsp_OrderMenu(vecFreres, menu);

			// on cree le menu en HTML
			if (vecFreres.size() != 0) {
				res.append( this.jsp_ConstruireMenu(vecFreres, file, menu, this.getSens()) );
			}
		} // FGA

		return res.toString();
	}

		/**
	 * cree un menu qui ne change jamais, quelque soit la profondeur
	 *  à laquelle on se trouve par rapport au root spécifié.
	 * @param fichier fichier source (actuellement lu)
	 * @param Menu Liste des infos sur le menu
	 * @param niveau Niveau du menu souhaité
	 * @return Code html pour creer menu dans un tableau avec le sens desire.
	 */
	/*private String jsp_CMS_creerMenu(String fichier, Menu menu, String niveau) {

		// buffer de retour
		StringBuffer res = new StringBuffer();

		// fichier correspondant au fichier courant
		File file = new File(fichier);

		// recherche du repertoire du niveau desire
		file = this.rechercheNiveau(file, niveau, menu);

		// FGA*13/08/2001 : Ajout d'un test
		// On ne fait si file est null
		// En +, ca évite d'avoir une exception
		if (file != null) {

			// on recupere les freres
			Vector vecFreres = this.getFreres(file);

			// on filtre par le niveau
			vecFreres = this.filtre(menu, niveau, vecFreres);

			// on filtre et ordonne par les droits et l'ordre d'affichage
			vecFreres = this.jsp_OrderMenu(vecFreres, menu);

			// on cree le menu en HTML
			if (vecFreres.size() != 0) {
				if (this.getSens().toUpperCase().equals("V")) {
				  //res.append(this.jsp_CMS_VerticalMenu(vecFreres,file,"",menu));
				}
				else {
				  res.append(this.jsp_CMS_HorizontalMenu(vecFreres,file,"",menu));
				}
			}
		} // FGA

		return res.toString();
	}*/


	/**
	 * Code html du menu verticale avec la langue
	 * @param v          Vecteur de file contenant les repertoires freres non tries
	 * @param file       fichier actuellement lu.
	 * @param strLangue  la langue utilisée.
	 * @param hashTable  Objet (Dictionnaire) contenant l'ensemble des menu et leur propriete
	 * @param sens      sens du menu à afficher.
	 * @return Le code HTML généré
	 */
	private String jsp_ConstruireMenu(Vector v, File file, Menu hashTable, String sens) {

		StringBuffer res = new StringBuffer();

		// creation de l'entete du tableau
		res.append("<table nowrap cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >");
		if (sens.equals(MENU_HORIZONTAL)) {
			res.append("<tr>");
		}

		// je passe en revu tout le vecteur
		for (int i=0;i<v.size();i++) {

			// si l'element est non null
			if (v.elementAt(i) != null) {

				// on recupere le nom du repertoire courant
				String nomFichierCourant = file.getName();

				// on recupere le nom du repertoire(menu) que l'on va creer
				String nomFichier = (String)v.elementAt(i);

				// on verifie si les images existes si au moins une des deux n'existe pas on cree un lien de type texte
				boolean imgExist = imageExist( hashTable.getCheminImage(nomFichier) );

				// Si le menu est vertical
				if (sens.equals(MENU_VERTICAL)) {
		  		res.append("<tr>");
				}

				// on verifie si ce n'est pas le menu actif
				if (!nomFichier.equals(nomFichierCourant)) {

					// on verifie si les images existes si au moins une des deux n'existe pas on cree un lien de type texte
					if (imgExist) {
						res.append("<td>");
						res.append( link.jsp_LinkRoll(hashTable.getCheminImage(nomFichier) + "_off.gif", hashTable.getLibelle(nomFichier), hashTable.getCible(nomFichier), hashTable.getCheminImage(nomFichier) + "_on.gif", ((String)v.elementAt(i)), this.getLargeur(), this.getHauteur()) );
						res.append("</td>");
					} else {
						// on cree un lien texte
						res.append("<td>&nbsp;");
						res.append( link.jsp_Link(hashTable.getLibelle(nomFichier), hashTable.getCible(nomFichier), "jspMenuInActif"));
						res.append("&nbsp;</td>");
					}

				} else {
						// Quand c'est le menu actif.
						// si l'image exite alors on cree un bouton  sinon
                                                // avec un lien
						if (imgExist) {
                                                        res.append("<td>");
                                                        res.append( link.jsp_LinkRoll(hashTable.getCheminImage(nomFichier) + "_on.gif", hashTable.getLibelle(nomFichier), hashTable.getCible(nomFichier), hashTable.getCheminImage(nomFichier) + "_on.gif", ((String)v.elementAt(i)), this.getLargeur(), this.getHauteur()) );
                                                        res.append("</td>");
						} else {
							// on affiche simplement le lien en une autre couleur garce à une classe de style jspMenuActif
							res.append("<td>&nbsp;");
							res.append( link.jsp_Link(hashTable.getLibelle(nomFichier), hashTable.getCible(nomFichier),"jspMenuActif"));
							res.append("&nbsp;</td>");
						}
				}
				// on saute de ligne à chauqe fois pour un menu vertival
				if (sens.equals(MENU_VERTICAL)) {
					res.append("</tr>");
				}
			}
		} // for

		// Tout sur la même ligne
		if (sens.equals(MENU_HORIZONTAL)) {
			res.append("</tr>");
		}
		res.append("</table>");

		return res.toString();
	}

	private String jsp_ConstruireMenuXML(Vector v, String file_name, Menu hashTable, String sens) {

		StringBuffer res = new StringBuffer();

		// creation de l'entete du tableau
		res.append("<table nowrap cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >");
		if (sens.equals(MENU_HORIZONTAL)) {
			res.append("<tr>");
		}

		// je passe en revu tout le vecteur
		for (int i=0;i<v.size();i++) {

			// si l'element est non null
			if (v.elementAt(i) != null) {

				// on recupere le nom du repertoire courant
				String nomFichierCourant = file_name;

				// on recupere le nom du repertoire(menu) que l'on va creer
				String nomFichier = (String)v.elementAt(i);

				// on verifie si les images existes si au moins une des deux n'existe pas on cree un lien de type texte
				boolean imgExist = imageExist( hashTable.getCheminImage(nomFichier) );

				// Si le menu est vertical
				if (sens.equals(MENU_VERTICAL)) {
		  		res.append("<tr>");
				}

				// on verifie si ce n'est pas le menu actif
				if (!nomFichier.equals(nomFichierCourant)) {

					// on verifie si les images existes si au moins une des deux n'existe pas on cree un lien de type texte
					if (imgExist) {
						res.append("<td>");
						res.append( link.jsp_LinkRoll(hashTable.getCheminImage(nomFichier) + "_off.gif", hashTable.getLibelle(nomFichier), hashTable.getCible(nomFichier), hashTable.getCheminImage(nomFichier) + "_on.gif", ((String)v.elementAt(i)), this.getLargeur(), this.getHauteur()) );
						res.append("</td>");
					} else {
						// on cree un lien texte
						res.append("<td>&nbsp;");
						res.append( link.jsp_Link(hashTable.getLibelle(nomFichier), hashTable.getCible(nomFichier), "jspMenuInActif"));
						res.append("&nbsp;</td>");
					}

				} else {
						// Quand c'est le menu actif.
						// si l'image exite alors on cree un bouton  sinon
						if (imgExist) {
							res.append("<td><img src=\"");
							res.append( hashTable.getCheminImage(nomFichier) );
							res.append("_on.gif\" width=\"" );
							res.append( this.getLargeur() );
							res.append( "\" heigth=\"" );
							res.append( this.getHauteur() );
							res.append( "\"></td>");
						} else {
							// on affiche simplement le lien en une autre couleur garce à une classe de style jspMenuActif
							res.append("<td>&nbsp;");
							res.append( link.jsp_Link(hashTable.getLibelle(nomFichier), hashTable.getCible(nomFichier),"jspMenuActif"));
							res.append("&nbsp;</td>");
						}
				}
				// on saute de ligne à chauqe fois pour un menu vertival
				if (sens.equals(MENU_VERTICAL)) {
					res.append("</tr>");
				}
			}
		} // for

		// Tout sur la même ligne
		if (sens.equals(MENU_HORIZONTAL)) {
			res.append("</tr>");
		}
		res.append("</table>");

		return res.toString();
	}


	/**
	 * Code html du menu verticale avec la langue
	 * @param v          Vecteur de file contenant les repertoires freres non tries
	 * @param file       fichier actuellement lu.
	 * @param strLangue  la langue utilisée.
	 * @param hashTable  Objet (Dictionnaire) contenant l'ensemble des menu et leur propriete
	 * @param sens      sens du menu à afficher.
	 * @return Le code HTML généré
	 */
	private String jsp_CMS_ConstruireMenu(Vector v, File file, Menu hashTable, String sens) {

		StringBuffer res = new StringBuffer();

		// creation de l'entete du tableau
		res.append("<table nowrap cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >");
		if (sens.equals(MENU_HORIZONTAL)) {
			res.append("<tr>");
		}

		// je passe en revu tout le vecteur
		for (int i=0;i<v.size();i++) {

			// si l'element est non null
			if (v.elementAt(i) != null) {

				// on recupere le nom du repertoire courant
				String nomFichierCourant = file.getName();

				// on recupere le nom du repertoire(menu) que l'on va creer
				String nomFichier = (String)v.elementAt(i);

				// on verifie si les images existes si au moins une des deux n'existe pas on cree un lien de type texte
				boolean imgExist = imageExist( hashTable.getCheminImage(nomFichier) );

				// Si le menu est vertical
				if (sens.equals(MENU_VERTICAL)) {
		  		res.append("<tr>");
				}

				// on verifie si ce n'est pas le menu actif
				if (!nomFichier.equals(nomFichierCourant)) {

					// on verifie si les images existes si au moins une des deux n'existe pas on cree un lien de type texte
					if (imgExist) {
						res.append("<td>");
						res.append( link.jsp_LinkRoll(hashTable.getCheminImage(nomFichier) + "_off.gif", hashTable.getLibelle(nomFichier), hashTable.getCible(nomFichier), hashTable.getCheminImage(nomFichier) + "_on.gif", ((String)v.elementAt(i)), this.getLargeur(), this.getHauteur()) );
						res.append("</td>");
					} else {
						// on cree un lien texte
						res.append("<td>&nbsp;");
						res.append( link.jsp_Link(hashTable.getLibelle(nomFichier), hashTable.getCible(nomFichier)) );
						res.append("&nbsp;</td>");
					}

				} else {
						// Quand c'est le menu actif.
						// si l'image exite alors on cree un bouton  sinon
						if (imgExist) {
							res.append("<td><img src=\"");
							res.append( hashTable.getCheminImage(nomFichier) );
							res.append("_on.gif\" width=\"" );
							res.append( this.getLargeur() );
							res.append( "\" heigth=\"" );
							res.append( this.getHauteur() );
							res.append( "\"></td>");
						} else {
							// on affiche simplement le libelle
							res.append("<td>&nbsp;");
							res.append( hashTable.getLibelle(nomFichier) );
							res.append("&nbsp;</td>");
						}
				}
				// on saute de ligne à chauqe fois pour un menu vertival
				if (sens.equals(MENU_VERTICAL)) {
					res.append("</tr>");
				}
			}
		} // for

		// Tout sur la même ligne
		if (sens.equals(MENU_HORIZONTAL)) {
			res.append("</tr>");
		}
		res.append("</table>");

		return res.toString();
	}


	/**
	 * Trie le vecteur en l'ordonnant par ordre croissant
	 * @param v Vecteur de file contenant les repertoires freres non tries.
	 * @param Menu Liste des infos sur le menu.
	 * @return Le vecteur trié
	 */
	private Vector jsp_OrderMenu(Vector v, Menu menu) {
		Vector res = new Vector();
		res.setSize(v.size());

		for(int i=0;i<v.size();i++) {
			String fileName = ((File)(v.elementAt(i))).getName();

		  // On regarde si le user a les droits pour le répertoire.
		  boolean droit = true;
			if ((this.listeActionsUser == null)&&(this.profilUser != null)) {
			  droit = confirmeDroit(fileName, menu, this.profilUser);
			}
			if ((this.profilUser == null)&&(this.listeActionsUser != null)) {
				droit = confirmeDroit(fileName, this.listeActionsUser);
			}

			if (droit) {
				if (Integer.parseInt(menu.getOrdreAffichage(fileName))>=res.size()) {
					res.setSize(Integer.parseInt(menu.getOrdreAffichage(fileName))+1);
				}
				res.setElementAt(fileName,Integer.parseInt(menu.getOrdreAffichage(fileName)));
			}
		}

		return res;
	}
	private Vector jsp_OrderMenuXML(Vector v, Menu menu) {
		Vector res = new Vector();
		res.setSize(v.size());

		for(int i=0;i<v.size();i++) {
			String fileName = (String)v.elementAt(i);

		  // On regarde si le user a les droits pour le répertoire.
		  boolean droit = true;
			if ((this.listeActionsUser == null)&&(this.profilUser != null)) {
			  droit = confirmeDroit(fileName, menu, this.profilUser);
			}
			if ((this.profilUser == null)&&(this.listeActionsUser != null)) {
				droit = confirmeDroit(fileName, this.listeActionsUser);
			}

			if (droit) {
				if (Integer.parseInt(menu.getOrdreAffichage(fileName))>=res.size()) {
					res.setSize(Integer.parseInt(menu.getOrdreAffichage(fileName))+1);
				}
				res.setElementAt(fileName,Integer.parseInt(menu.getOrdreAffichage(fileName)));
			}
		}

		return res;
	}


	/**
	 * Confirme ou non si l'utilisateur a le droit de voir ce repertoire
	 * @param pNomRepertoire Nom du repertoire a verifier
	 * @param pHashDroit Hashtable contenant l'ensemble des repertoire et de leur droit
	 * @param pUserDroit Code droit de l'utilisateur.
	 * @return true si l'utilisateur a les droits sinon false (par defaut true)
	 */
	private boolean confirmeDroit(String pNomRepertoire, Menu pHashDroit, String pUserDroit) {
		boolean res = false;
		if (pHashDroit.isExist(pNomRepertoire)) {
			if (Integer.parseInt(pHashDroit.getDroit(pNomRepertoire)) <= Integer.parseInt(pUserDroit)) {
				res = true;
			}
		}
		return res;
	}


	/**
	 * Confirme ou non si l'utilisateur a le droit de voir ce repertoire
	 * @param pNomRepertoire Nom du repertoire a verifier
	 * @param pListeActionsUser Liste des actions autorisées du user
	 * @return true si l'utilisateur a les droits sinon false (par defaut true)
	 */
	private boolean confirmeDroit(String pNomRepertoire, ListeActionsUser pListeActionsUser) {
		return pListeActionsUser.contientAction(pNomRepertoire);
	}


	/**
	 * Retourne le premier repertoire trouve du niveau passe en parametre.
	 * @param pFichier  Fichier courant
	 * @param pMenu     Hashtable contenant l'ensemble des repertoire et de leur droit
	 * @param pNiveau   niveau recherché
	 * @return repertoire trouve
	 */
	private String rechercheNiveauXML(String pNomFichier, String pNiveau, Menu pMenu, TreeMenu pTreeMenu) {
		boolean continuer = true;

		while (continuer) {


		  // FGA*13/08/2001 : Ajout contrôle pour éviter exception
			if ((pMenu != null)&&(pNomFichier != null)&&(pNomFichier!="")) {
				if (pMenu.isExist(pNomFichier)) {
					if (Integer.parseInt(pMenu.getNiveau(pNomFichier)) == Integer.parseInt(pNiveau)) {
						continuer = false;
                                        }
				}

			} else {
				// FGA*13/08/2001
				continuer = false;
			}

                  if (continuer==true)
                    pNomFichier = pTreeMenu.getParent(pNomFichier);

                }

		return pNomFichier;
	}

	private File rechercheNiveau(File pFichier, String pNiveau, Menu pMenu) {
		boolean continuer = true;

		while (continuer) {

			pFichier = pFichier.getParentFile();

		  // FGA*13/08/2001 : Ajout contrôle pour éviter exception
			if ((pMenu != null)&&(pFichier != null)) {

				if (pMenu.isExist(pFichier.getName())) {
					if (Integer.parseInt(pMenu.getNiveau(pFichier.getName())) == Integer.parseInt(pNiveau)) {
						continuer = false;
					}
				}

			} else {
				// FGA*13/08/2001
				continuer = false;
			}

		}

		return pFichier;
	}


	/**
	 * Retourne la liste des frères du fichier passé en parametre
	 * @param pFichier Fichier de base
	 * @return Liste des repertoires trouvé.
	 */
	private Vector getFreres(File pFichier) {

		File[] filsList = pFichier.getParentFile().listFiles();
		Vector res = new Vector();

		for (int i=0;i<filsList.length;i++) {
			if (filsList[i].isDirectory() == true) {
				res.addElement(filsList[i]);
			}
		}

		return res;
	}


	/**
	 * Recupere les repertoire qui sont des menu et du bon niveau
	 * @param pMenu  Ensemble des menus.
	 * @param pNiveau Niveau souhaite de menu.
	 * @param pListeRepertoire Liste des répertoires autorisés.
	 * @return Les répertoires filtrés
	 */
	private  Vector filtreXML(Menu pMenu, String pNiveau, Vector pListeRepertoire) {
		Vector res = new Vector();

		for (int i=0;i<pListeRepertoire.size();i++) {
			String strTemp = (String) pListeRepertoire.elementAt(i);

			if (pMenu.isExist(strTemp)) {
				if (Integer.parseInt(pMenu.getNiveau(strTemp)) == Integer.parseInt(pNiveau)) {
					res.addElement((String) pListeRepertoire.elementAt(i));
				}
			}
		}

		return res;
	}


	private  Vector filtre(Menu pMenu, String pNiveau, Vector pListeRepertoire) {
		Vector res = new Vector();

		for (int i=0;i<pListeRepertoire.size();i++) {
			String strTemp = ((File)pListeRepertoire.elementAt(i)).getName();

			if (pMenu.isExist(strTemp)) {
				if (Integer.parseInt(pMenu.getNiveau(strTemp)) == Integer.parseInt(pNiveau)) {
					res.addElement((File)pListeRepertoire.elementAt(i));
				}
			}
		}

		return res;
	}





	/**
	 * Vérifie si l'image existe.
	 * @param pCheminImage Chemin de l'image à vérifier
	 * @return True si l'image existe
	 */
	private boolean imageExist(String pCheminImage) {
		if (pCheminImage != null) {
			return (!pCheminImage.equals(""));
		} else {
			return false;
		}
	}


	/**
	 * Initialise la propriété profilUser
	 * @param pProfilUser le profil du user
	 */
	public void setProfilUser(String pProfilUser) {
		if (pProfilUser != null) {
			this.profilUser = pProfilUser;
		}
	}

	/**
	 * Initialise la liste des actions possibles du user
	 * @param pListeActionsUser Liste des actions du user
	 */
	public void setListeActionsUser(ListeActionsUser pListeActionsUser) {
		if (pListeActionsUser != null) {
			this.listeActionsUser = pListeActionsUser;
		}
	}

}