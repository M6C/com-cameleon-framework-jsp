package ressource.jsp.liens;

/**
 * Package qui contient les méthodes créant differents types
 * de liens
 */

/**
	* Liste des fonctions pour les liens.
	* @author ressource
	* @version 1.0
	*
	* @see ressource.jsp.formulaires.Jsp_Formulaires
	*/

public class Jsp_Liens extends ressource.jsp.libelles.Jsp_Libelles {

	/**
	 * Constructeur
	 */
	public Jsp_Liens() {
	}


  /**
	 * Retourne le code HTML d'un lien vers une autre page
	 * en utilisant le gif "fleche_dte.gif".
	 * @param str   Libelle du lien
	 * @param link  adresse URL du lien
	 * @return Le lien vers une autre page
	 */
	public String jsp_Link(String str1, String link) {
		StringBuffer str = new StringBuffer();

		if (link == null) {
		  link = "";
		}
		if (str1 == null) {
		  str1 = "";
		}
		str.append( "<a href=\"" );
		str.append( link );
		str.append( "\" target=\"_top\">" );
		str.append( jsp_Str(str1) );
		str.append( "</a>" );

		return str.toString();
	}

	/**
	 * Retourne le code HTML d'un lien vers une autre page utilisant la class
	 * style_lien
	 * @param str   Libelle du lien
	 * @param link  adresse URL du lien
	 * @return Le lien vers une autre page
	 */
	public String jsp_CMS_Link(String str1, String link) {
		StringBuffer str = new StringBuffer();

		if (link == null) {
		  link = "";
		}
		if (str1 == null) {
		  str1 = "";
		}
		str.append( "<a href=\"" );
		str.append( link );
		str.append( "\" target=\"_top\" class=\"style_lien\">" );
		str.append( str1 );
		str.append( "</a>" );

		return str.toString();
	}

		/**
	 * Retourne le code HTML d'un lien vers une autre page utilisant la class
	 * style_menu
	 * @param str   Libelle du lien
	 * @param link  adresse URL du lien
	 * @return Le lien vers une autre page
	 */
	public String jsp_CMS_Menu(String str1, String link) {
		StringBuffer str = new StringBuffer();

		if (link == null) {
		  link = "";
		}
		if (str1 == null) {
		  str1 = "";
		}
		str.append( "<a href=\"" );
		str.append( link );
		str.append( "\" target=\"_top\" class=\"style_menu\">" );
		str.append( str1 );
		str.append( "</a>" );

		return str.toString();
	}


	 /**
	 * Retourne le code HTML d'un lien vers une autre page
	 * @param str   Libelle du lien
	 * @param link  adresse URL du lien
	 * @param style style du lien
	 * @return Le lien vers une autre page
	 */
	public String jsp_Link(String str1, String link, String style) {
		return this.jsp_Link(str1, link, style, null);
	}


  /**
	 * Retourne le code HTML d'un lien vers une page Popup
	 * Il faut importer la librairie contenant la fonction JS OuvrirGrille
	 * @param str Libelle du lien
	 * @param link adresse URL du lien
	 * @param style style utilisé pour les liens
	 * @return Le lien vers une autre page
         * OuvreFen(url,name,width, height, timeOut, parameters, menu)
	 */
	public String jsp_LinkPopup(String str1, String link, String style,String fnJavascriptPopup,String  namePopup, String widthPopup,String  heightPopup,String  paramPopup) {
		return this.jsp_Link(str1,"javascript:"+fnJavascriptPopup+"('" + link + "','"+namePopup+"','"+widthPopup+"','"+heightPopup+"',null,'"+paramPopup+"')", style, "");
	}


        /**
	 * Retourne le code HTML d'un lien vers une page Popup à partir d'une image
	 * Il faut importer la librairie contenant la fonction JS OuvrirGrille
	 * @param str Libelle du lien
	 * @param link adresse URL du lien
	 * @param style style utilisé pour les liens
	 * @return Le lien vers une autre page
         * OuvreFen(url,name,width, height, timeOut, parameters, menu)
	 */

	public String jsp_LinkIMGPopup(String img,String link,String str1, String width, String heigth,String target,String fnJavascriptPopup,String  namePopup, String widthPopup,String  heightPopup,String  paramPopup) {
		StringBuffer str = new StringBuffer();
                String lienPopup=null;

		if (img == null) {
		  img = "";
		}
		if (link == null) {
		  link = "";
		}
		if (str1 == null) {
		  str1 = "";
		}
		if (width == null) {
		  width = "";
		}
		if (heigth == null) {
		  heigth = "";
		}

		str.append( "<a target=\"" + target + "\" href=\"" );
		str.append( "javascript:"+fnJavascriptPopup+"('" + link + "','"+namePopup+"','"+widthPopup+"','"+heightPopup+"',null,'"+paramPopup+"')" );
		str.append( "\"><img src=\"" );
                str.append( getJspCPthLibImg() );
		str.append( img );
		str.append( "\" border=\"0\" alt=\"" );
		str.append( str1 );
		str.append( "\" width=\"" );
		str.append( width );
		str.append( "\" heigth=\"" );
		str.append( heigth );
		str.append( "\" ></a>" );

    return str.toString();
  }
	public String jsp_LinkPopup(String str1, String link, String style) {
		return this.jsp_Link(str1,"javascript:OuvreGrille('" + link + "')", style, "");
	}


  /**
	 * Retourne le code HTML d'un lien vers une autre page
	 * en utilisant le gif "fleche_dte.gif".
	 * @param str Libelle du lien
	 * @param link adresse URL du lien
	 * @param style style utilisé pour les liens
	 * @param target frame cible du lien
	 * @return Le lien vers une autre page
	 */
	public String jsp_Link(String str1, String link, String style, String target) {
		StringBuffer str = new StringBuffer();

		if (link == null) {
		  link = "";
		}
		if (str1 == null) {
		  str1 = "";
		}
		if (target == null) {
		  target = "_top";
		}
		if (style == null) {
		  style = "";
		}

		str.append( "<a" );
		if (!style.equals("")) {
			str.append( " class=\"" + style + "\"" );
		}

		str.append( " href=\"" );
		str.append( link );
		str.append( "\"" );

		if (!target.equals("")) {
			str.append( " target=\"" + target + "\"");
		}

		str.append( ">" );
		str.append( str1 );
		str.append( "</a>" );

		return str.toString();
	}

	/**
	 * Retourne le code HTML d'un lien Image vers une autre page
	 * @param img    Image à afficher
	 * @param link   adresse URL du lien
	 * @param str    Libelle de l'image
	 * @param width  Largeur de l'image
	 * @param height Hauteur de l'image
	 * @return Le lien vers ue autre page via une image
	 */
	private String jsp_HTML_LinkIMG(String img,String link,String str1, String width, String heigth,String target) {
		StringBuffer str = new StringBuffer();

		if (img == null) {
		  img = "";
		}
		if (link == null) {
		  link = "";
		}
		if (str1 == null) {
		  str1 = "";
		}
		if (width == null) {
		  width = "";
		}
		if (heigth == null) {
		  heigth = "";
		}

		str.append( "<a target=\"" + target + "\" href=\"" );
		str.append( link );
		str.append( "\"><img src=\"" );
                str.append( getJspCPthLibImg() );
		str.append( img );
		str.append( "\" border=\"0\" alt=\"" );
		str.append( str1 );
		str.append( "\" width=\"" );
		str.append( width );
		str.append( "\" heigth=\"" );
		str.append( heigth );
		str.append( "\" ></a>" );

    return str.toString();
  }



  /**
	 * Retourne le code HTML d'un lien Image vers une autre page
	 * @param img    Image à afficher
	 * @param link   adresse URL du lien
	 * @param str    Libelle de l'image
	 * @param width  Largeur de l'image
	 * @param height Hauteur de l'image
	 * @return Le lien vers ue autre page via une image
	 */
	public String jsp_LinkIMG(String img,String link,String str1, String width, String heigth) {
		StringBuffer str = new StringBuffer();

		if (img == null) {
		  img = "";
		}
		if (link == null) {
		  link = "";
		}
		if (str1 == null) {
		  str1 = "";
		}
		if (width == null) {
		  width = "";
		}
		if (heigth == null) {
		  heigth = "";
		}

		str.append( "<a target=\"_top\" href=\"" );
		str.append( link );
		str.append( "\"><img src=\"" );
                str.append( getJspCPthLibImg() );
		str.append( img );
		str.append( "\" border=\"0\" alt=\"" );
		str.append( str1 );
		str.append( "\" width=\"" );
		str.append( width );
		str.append( "\" heigth=\"" );
		str.append( heigth );
		str.append( "\" ></a>" );

    return str.toString();
  }


  /**
	 * Retourne le code HTML d'une ancre
	 * @param str1   Libelle du lien. Le # en déjà inclu dans le lien.
	 * @param link   adresse URL du lien
	 * @return Le code HTML généré
	 */
	public String jsp_Ancre(String str1, String link) {
		StringBuffer str = new StringBuffer();
		if (str1 == null) {
		  str1 = "";
		}
		if (link == null) {
			link = "";
		}

		str.append( "<a href=\"#" );
		str.append( link );
		str.append( "\" TARGET=\"_self\">" );
		str.append( jsp_Str(str1) );
		str.append( "</a>" );

		return str.toString();
	}


  /**
	 * Retourne le code HTML d'un lien retour vers la page précédente en utilisant un gif "fleche_gch.gif" 5(taille 15*15)
	 * @param str   Libelle du lien
	 * @param link  adresse URL du lien
	 * @return Le lien vers la page precedente
	 **/
	public String jsp_Back(String str1, String link) {
		StringBuffer str = new StringBuffer();

		if (str1 == null) {
		  str1 = "";
		}
		if (link == null) {
		  link = "";
		}

		StringBuffer href = new StringBuffer();
		href.append( "<a target =\"_top\" href=\"" );
		href.append( link );
		href.append( "\">" );

		str.append( href.toString() );
		str.append( "<img src=\"" );
		str.append( this.getJspCPthLibImg() );
		str.append( "fleche_gch.gif\" width=\"15\" height=\"15\" alt=\"Retour\" border=\"0\"></a>&nbsp;" );
		str.append( href.toString() );
		str.append( jsp_Str(str1) );
		str.append( "</a>" );

		return str.toString();
  }

/**
	 * Retourne le code HTML d'un lien retour vers la page précédente en utilisant un gif "fleche_gch.gif" 5(taille 15*15)
	 * @param str   Libelle du lien
	 * @param link  adresse URL du lien
	 * @return Le lien vers la page precedente
	 **/
	public String jsp_Back(String str1, String link, String width, String height) {
		StringBuffer str = new StringBuffer();

		if (str1 == null) {
		  str1 = "";
		}
		if (link == null) {
		  link = "";
		}

		StringBuffer href = new StringBuffer();
		href.append( "<a target =\"_top\" href=\"" );
		href.append( link );
		href.append( "\">" );

		str.append( href.toString() );
		str.append( "<img src=\"" );
		str.append( this.getJspCPthLibImg() );
		str.append( "fleche_gch.gif\" width=\"");
		str.append( width );
		str.append( "\" height=\"");
		str.append( height );
		str.append("\" alt=\"Retour\" border=\"0\"></a>&nbsp;" );
		str.append( href.toString() );
		str.append( jsp_Back(str1) );
		str.append( "</a>" );

		return str.toString();
  }


  /**
	 * Retourne le code HTML d'un lien retour vers la page suivante
	 * en utilisant un gif "fleche_drt.gif".
	 * @param str   Libelle du lien
	 * @param link  adresse URL du lien
	 * @return Le lien vers la page suivante
	 */
	public String jsp_Next(String str1,String link ) {
		StringBuffer str = new StringBuffer();

		if (str1 == null) {
		  str1 = "";
		}
		if (link == null) {
		  link = "";
		}

		StringBuffer href = new StringBuffer();
		href.append( "<a target =\"_top\" href=\"" );
		href.append( link );
		href.append( "\">" );

		str.append( href.toString() );
		str.append( "<img src=\"" );
		str.append( this.getJspCPthLibImg() );
		str.append( "fleche_drt.gif\" width=\"15\" height=\"15\" alt=\"Suivant\" border=\"0\"></a>&nbsp;" );
		str.append( href.toString() );
		str.append( jsp_Str(str1) );
		str.append( "</a>" );

		return str.toString();
  }

	/**
	 * Retourne le code HTML d'un lien retour vers la page suivante
	 * en utilisant un gif "fleche_drt.gif".
	 * @param str   Libelle du lien
	 * @param link  adresse URL du lien
	 * @return Le lien vers la page suivante
	 */
	public String jsp_Next(String str1,String link, String width, String height) {
		StringBuffer str = new StringBuffer();

		if (str1 == null) {
		  str1 = "";
		}
		if (link == null) {
		  link = "";
		}

		StringBuffer href = new StringBuffer();
		href.append( "<a target =\"_top\" href=\"" );
		href.append( link );
		href.append( "\">" );
		str.append( href.toString() );
		str.append( "<img src=\"" );
		str.append( this.getJspCPthLibImg() );
		str.append( "fleche_drt.gif\" width=\"");
		str.append( width );
		str.append( "\" height=\"");
		str.append( height );
		str.append( "\" alt=\"Suivant\" border=\"0\"></a>&nbsp;" );
		str.append( href.toString() );
		str.append( jsp_Str(str1) );
		str.append( "</a>" );

		return str.toString();
  }

	/**
	 * Retourne le code HTML d'un lien retour vers l'historique
	 * en utilisant un gif "historique.gif".
	 * @param str   Libelle du lien
	 * @param link  adresse URL du lien
	 * @return Le lien vers la page historique
	 */
	public String jsp_Histo(String str,String link) {
		StringBuffer res = new StringBuffer();

		StringBuffer res1 = new StringBuffer();
		res1.append( "<a href=" );
		res1.append( link );
		res1.append( ">" );

		res.append("<img src=\"");
		res.append( this.getJspCPthLibImg() );
		res.append( "historique.gif\" width=\"28\" height=\"25\" border=\"0\" alt=\"Historique\" align=\"top\"></a>&nbsp;" );
		res.append( res1.toString() );
		res.append( jsp_Str(str) );
		res.append( "</a>" );

		return res.toString();
	}


	/**
	 * lien avec un roll over sur l'image
	 * @param img1          image1
	 * @param lib           Libelle du lien
	 * @param link          lien pointe
	 * @param img2          image2
	 * @param imgLongueur   Longueur des images
	 * @param imgHauteur    Hauteur des images
	 * @param target        Frame visée par le lien
	 * @return Le code HTML généré
	 */
	private String jsp_HTML_LinkRoll(String img1,String lib,String link,String img2,String name,String imgLongueur,String imgHauteur,String target) {
		StringBuffer str = new StringBuffer();

		if (img1 == null) {
			img1 = "";
		}
		if (lib == null) {
			lib = "";
		}
		if (link == null) {
			link = "";
		}
		if (img2 == null) {
			img2 = "";
		}
		if (name == null) {
			name = "";
		}
		if (imgLongueur == null) {
			imgLongueur = "";
		}
		if (imgHauteur == null) {
			imgHauteur = "";
		}
		if (target == null) {
			target = "_top";
		}


		str.append( "<a target=\"" );
		str.append( target );
		str.append( "\" href=\"" );
		str.append( link );
		str.append( "\" onmouseover=\"" );
		str.append( name );
		str.append( ".src=\'" );
		str.append( img2 );
		str.append( "\'\" onmouseout=\"" );
		str.append( name );
		str.append( ".src=\'");
		str.append( img1 );
		str.append( "\'\"><img src=\"" );
		str.append( img1 );
		str.append( "\" border=\"0\" alt=\"" );
		str.append( lib );
		str.append( "\" name=\"" );
		str.append( name );
		str.append( "\" width=\"" );
		str.append( imgLongueur );
		str.append( "\" heigth=\"" );
		str.append( imgHauteur );
		str.append( "\" ></a>" );

    return str.toString();
  }


	/**
	 * lien avec un roll over sur l'image
	 * @param img1   image1
	 * @param lib    Libelle du lien
	 * @param link   lien pointe
	 * @param img2   image2
	 * @param imgLongueur   Longueur des images
	 * @param imgHauteur    Hauteur des images
	 * @return Le code HTML généré
	 */
	public String jsp_LinkRoll(String img1,String lib,String link,String img2,String name,String imgLongueur,String imgHauteur) {
		String str = new String();
		str = jsp_HTML_LinkRoll(img1,lib,link,img2,name,imgLongueur,imgHauteur,"_top");
    return str;
  }

	/**
	 * lien avec un roll over sur l'image
	 * @param img1          Chemin de l'image 1
	 * @param lib           Libelle du lien
	 * @param link          lien pointe
	 * @param img2          Chemin de l'image 2
	 * @param imgLongueur   Longueur des images
	 * @param imgHauteur    Hauteur des images
	 * @param target        Frame visée par le lien
	 * @return Le code HTML généré
	 */
	public String jsp_LinkRoll(String img1,String lib,String link,String img2,String name,String imgLongueur,String imgHauteur,String target) {
		String str = new String();
		str = jsp_HTML_LinkRoll(img1,lib,link,img2,name,imgLongueur,imgHauteur,target);
    return str;
  }

}