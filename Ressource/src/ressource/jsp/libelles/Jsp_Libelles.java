package ressource.jsp.libelles;

/**
 * Package qui contient les méthodes qui formattent le
 * texte des différents libellés <p>
 * ce package marche avec une feuille de style.
 */

import java.util.*;
import java.text.DecimalFormat;
//import ressource.jsp.libelles;

/**
	* Fonctions de création des libellés.
	* @author ressource
	* @version 1.0
	*/

public class Jsp_Libelles  {


	/** Répertoire où est emplacé toutes les images */
	private String jsp_CPthLibImg = "/images/";


  /**
	 * Affiche un gif transparent sur width pixel
	 * @param width  Largeur en pixel
	 * @param heigth Hauteur en pixel
	 * @return Le code HTML de l'image transparente
	 */
	public String jsp_Dot(int width, int heigth) {
		StringBuffer str = new StringBuffer();

		str.append( "<img src=\"" );
		str.append( jsp_CPthLibImg );
		str.append( "DotClear.gif\" width=\"" );
		str.append( width );
		str.append( "\" height=\"" );
		str.append( heigth );
		str.append( "\" border=\"0\">" );

		return str.toString();
	}


  /**
	 * Retourne le code HTML d'un libelle strTxt dans un style donné
	 * @param strTxt    Valeur du libelle
	 * @param classFont classe de style à utiliser pour afficher la font
	 * @return Le code HTML d'un libelle
	 */
	private String jsp_HTML_Str(String strTxt, String classFont) {
		StringBuffer str = new StringBuffer();
		if (strTxt == null) {
			strTxt = "";
		}

		str.append( "<font class=\"" );
		str.append( classFont );
		str.append( "\">" );
		str.append( strTxt );
		str.append( "</font>" );

		return  str.toString();
	}


  /**
	 * Retourne le code HTML d'un sous-titre contenu dans str
	 * @param Str  Valeur du sous titre à afficher
	 * @return Le code HTML d'un sous titre
	 */
	public String jsp_SSTitre(String str) {
		return jsp_HTML_Str(str, "jspSSTitre");
	}


	/**
	 * Retourne le code HTML de l'entête contenu dans str
	 * @param str  Valeur du libelle à afficher
	 * @return Le code HTML de l'entête
	 */
	public String jsp_Entete(String str) {
		return jsp_HTML_Str(str, "jspEntete");
	}


  /**
	 * Retourne le code HTML d'un titre contenu dans str
	 * @param str  Valeur du libelle du titre
	 * @return Le code HTML du titre
	 */
	public String jsp_Titre(String str) {
		return jsp_HTML_Str(str, "jspTitre");
	}


  /**
	 * Retourne le code HTML d'un libelle contenu dans str
	 * @param str  Valeur du libelle
	 * @return Le code HTML d'un libelle
	 */
	public String jsp_Str(String str) {
		return jsp_HTML_Str(str ,"jspStr");
	}


  /**
	 * Retourne le code HTML d'un message d'information contenu dans str
	 * @param str  Valeur du libelle
	 * @return Le code HTML d'un libelle
	 */
	public String jsp_MessageInfo(String str) {
		return jsp_HTML_Str(str ,"jspMessageInformation");
	}


  /**
	 * Renvoie code html correspondant au type (bold ou italic) dans un tableau
	 * de chaines de caracteres a deux elements.
	 * @param ttypes : Valeur du type
	 * @return Le code HTML d'un libelle
	 */
	private String[] jsp_HTML_Type(String ttypes) {
		String[] res = new String[2];
		if (ttypes == null) {
			ttypes = "";
		}

		res[0] = " ";
		res[1] = " ";

		if (!ttypes.equals("")) {
			if (ttypes.equals("BOLD")) {
				res[0] = "<b>";
				res[1] = "</b>";
			} else {
				res[0] = "<i>";
				res[1] = "</i>";
			}
		}

		return res;
	}

        /**
	 * Retourne le code HTML d'un libelle contenu dans la fonction liens.jsp_Back
	 * @param str  Valeur du libelle
	 * @return Le code HTML d'un libelle
	 */
	public String jsp_Back(String str) {
		return jsp_HTML_Str(str ,"jspBack");
	}

	/**
	 * Retourne le code HTML d'un libellé dans le style Menu
	 * @param str  Valeur du libelle
	 * @return Le code HTML d'un libelle du style Menu
	 */
	public String jsp_strMenu(String str) {
		return jsp_HTML_Str(str,"jspStrMenu");
	}


	/**
	 * Retourne le code HTML d'un libelle au format Donnee
	 *  contenu dans str
	 * @param Str  Valeur de la donnee
	 * @return Le code HTML d'une donnne
	 */
	public String jsp_DataMenu(String str ){
		return(jsp_HTML_Str( str,"jspDataMenu"));
	}


	/**
	 * Retourne le code HTML d'un libelle au format Donnee
	 *  contenu dans str
	 * @param str  Valeur de la donnee
	 * @return Le code HTML d'une donnne
	 */
	public String jsp_Data(String str) {
		return jsp_HTML_Str( str,"jspdata");
	}


  /**
	 * Retourne le code HTML d'un libelle au format Date
	 * contenu dans StrDate
	 * @param strDate  Valeur du Date
	 * @return Le code HTML d'une Date
	 */
	public String jsp_Date(String strDate) {
		return jsp_Data(strDate);
	}


  /**
	 * Retourne le code HTML d'un libelle au format Montant
	 * contenu dans strNbr
	 * @param strNbr  Valeur du montant
	 * @return Le code HTML d'un Montant
	 */
	public String jsp_Mnt(String strNbr) {
		StringBuffer strMnt = new StringBuffer();
		if (strNbr == null) {
			strNbr = "";
		}

		if (!strNbr.equals("")) {
			strMnt.append( jsp_NbrToMnt(strNbr) );
		}

		return jsp_Data( strMnt.toString() );
	}


  /**
	 * Retourne le code HTML d'un libelle au format Montant
	 * contenu dans StrNbr
	 * @param strNbr   Valeur du montant
	 * @param decimale nombre de décimales
	 * @return Le code HTML d'un Montant
	 */
	public String jsp_MntLimited(String strNbr,int decimale) {
		String strMnt = "";
		if (strNbr == null) {
			strNbr = "";
		}

		if (!strNbr.equals("")) {
			strMnt = round(strNbr, decimale);
			strMnt = jsp_NbrToMnt(strMnt);
		}

		return jsp_Data(strMnt);
	}


  /**
   *  Retourne le code HTML d'un libelle au format Entier contenu dans StrNbr
   *
   *@param  strNbr  Valeur du montant
   *@return         Le code HTML d'un Montant
   */
  public String jsp_Int(String strNbr) {
      String strMnt = new String();
      DecimalFormat df = new DecimalFormat("#,##0;-#,##0");

      if ((strNbr != null) && !strNbr.equals("")) {
          strMnt = df.format(Double.parseDouble(strNbr));
      }

      return jsp_Data(strMnt);
  }


	/**
	 * Retourne un arrondi de la valeur passe en paramètre (str),
	 * avec un nombre de decimales choisi (decimale)
	 * @param str   Chaine a découper
	 * @param sep   séparateur
	 * @return chaine de caractères
	 */
	public String round(String str1, int decimal) {
		str1.replace(',','.');

		// conversion d 'une String en un float par l'intermediaire d'un Float.
		// car la methode "round" n'est valable que pour les floats
		double a = Double.parseDouble(str1);

		for (int i=0; i<decimal; i++) {
			a = a*10;
		}
		String nb = String.valueOf( (int)Math.round(a) );
		if ((nb.length()-decimal) > 0) {
			nb = nb.substring(0, nb.length()-decimal) + "." + nb.substring(nb.length()-decimal, nb.length());
		}
		if ((nb.length()-decimal) == 0) {
			nb = "0." + nb.substring(nb.length()-decimal, nb.length());
		}

		return nb;
	}


	/**
	 * Retourne le code HTML d'un libelle au format Taux contenu dans StrTx
	 * @param strTxt  Valeur du Taux
	 * @return Le code HTML d'un Taux
	 */
	public String jsp_Taux(String strTxt) {
		return jsp_Data(strTxt + " %");
	}


	/**
	 * Descriptif : Converti une chaine de caractere StrNbr
	 *  contenant un nombre au format Montant (9 999 999).
	 *  @param Str  chaine a convertir
	 *  @return Le nombre au format montant
	 */
	private String jsp_NbrToMnt(String strNbr) {
		return jsp_TNbr_NbrToMnt(strNbr, " " );
	}


	/**
	 * Converti une chaine de caractere StrNbr
	 * contenant un nombre au format Montant avec un séparateur donné.
	 * @param strNombre  Le nombre à formatter
	 * @param strSep     Séparateur à utiliser pour formatter le nombre en montant.
	 * @return Le nombre au format montant
	 */
	private String jsp_TNbr_NbrToMnt(String strNombre,String strSep) {
		if (strNombre == null) {
			strNombre = "";
		}

		strNombre = strNombre.replace(',','.');
		strNombre = this.round(strNombre, 2);

		StringTokenizer str = new StringTokenizer(strNombre, ".");
		String strEntier = "0";
		String strDecimal = "";
		if (str.hasMoreElements()) {
			strEntier = str.nextToken();
		}
		if (str.hasMoreElements()) {
			strDecimal = str.nextToken();
		}
		int index = strEntier.length();

		// J'ajoute des séparateurs des milliers
		while ((index-3) > 0) {
			index = index-3;
			strEntier = strEntier.substring(0,index) + strSep + strEntier.substring(index,strEntier.length());
		}

		// J'ajoute des 0 à la fin
		while (strDecimal.length() < 2) {
			strDecimal += "0";
		}

		return strEntier + "," + strDecimal;
	}


	/**
	 * Convertit les <br> des textes destines
	 * à entrer dans un textarea par la code ASCII des retour chariot.
	 * @param texte   chaine a convertir
	 * @return La chaine convertie
	 */
	public String jsp_BR_To_CHR(String texte) {
		StringBuffer res = new StringBuffer();
		if (texte == null) {
			texte = "";
		}

		if (!texte.equals("")) {
			StringTokenizer vec = new StringTokenizer(texte,"<br>");
		  while (vec.hasMoreElements()) {
				res.append( vec.nextElement().toString() );
				res.append( "\n" );
			}
		}

		return res.toString();
	}


	/**
	 * Convertit les code ASCII des retour chariot par des <br>
	 * @param texte  chaine a convertir
	 * @return La chaine convertie
	 */
	public String jsp_CHR_To_BR(String texte) {
		StringBuffer res = new StringBuffer();
		if (texte == null) {
			texte = "";
		}

		if (!texte.equals("")) {
			StringTokenizer vec = new StringTokenizer(texte,"\n");
		  while (vec.hasMoreElements()) {
				res.append( vec.nextElement().toString() );
				res.append( "<br>" );
			}
		}

		return res.toString();
	}


	/**
	 * Tire un trait sur NbCol colonnes
	 * @param nbCol  nombre de colonne
	 * @return Le code HTML du tiret
	 */
	public String jsp_HR(int nbCol) {
		StringBuffer str = new StringBuffer();

		str.append( "<TR><TD colspan=\"" );
		str.append( nbCol );
		str.append( "\" class=\"jsp_CColTiret\"><img src=\"" );
		str.append( this.getJspCPthLibImg() );
		str.append( "\"DotClear.gif\" height=\"1\" border=\"0\"></TD></TR>" );

		return str.toString();
	}


	/**
	 * Saut de ligne d'une hauteur donné sur x colonnes
	 * @param nbCol   nombre de colonne
	 * @param height  hauteur du saut
	 * @return Le code HTML du saut
	 */
	public String jsp_BR(int nbCol, int height) {
		StringBuffer str = new StringBuffer();

		str.append( "<TR><TD colspan=\"" );
		str.append( nbCol );
		str.append( "\"><img src=\"" );
		str.append( this.getJspCPthLibImg() );
		str.append( "DotClear.gif\" height=\"" );
		str.append( height );
		str.append( "\" border=\"0\"></TD></TR>" );

		return str.toString();
	}

  /**
	 * CheckBox "statique" utilisant des images
	 * @param text libellé de la case à cocher
   * @param valTrue  chaine de caractère représentant la bonne valeur
	 * @param value  valeur de la case à cocher, si value=valTrue alors la case est cochée
	 * @return Le code HTML de la case à cocher
	 */
	public String jsp_LibCB(String text, String valTrue, String value) {
		StringBuffer str = new StringBuffer();

		str.append( "<img src=\"" );
		str.append( this.getJspCPthLibImg() );
		if (value.equals(valTrue)){
      str.append( "cb_on.gif" );
    }else{
      str.append( "cb_off.gif" );
    }
		str.append( "\">&nbsp;&nbsp;" );
    str.append(jsp_Str(text));
		return str.toString();
	}

        /**
	 * CheckBox "statique" utilisant des images
	 * @param text libellé de la case à cocher
         * @param valTrue  chaine de caractère représentant la bonne valeur
	 * @param value  valeur de la case à cocher, si value=valTrue alors la case est cochée
	 * @return Le code HTML de la case à cocher
	 */
        public String jsp_IMG(String name) {
          StringBuffer str = new StringBuffer();

          str.append( "<img src=\"" );
          str.append( this.getJspCPthLibImg() );
          str.append( name );
          str.append( "\">" );
          return str.toString();
        }

	/**
	 * Retourne la propriété jsp_CPthLibImg
	 * @return La propriété jsp_CPthLibImg
	 */
	public String getJspCPthLibImg() {
		return this.jsp_CPthLibImg;
	}


	/**
	 * Initialise la propriété jsp_CPthLibImg
	 * @param param   La propriété jsp_CPthLibImg
	 */
	public void setJspCPthLibImg(String param) {
		this.jsp_CPthLibImg = param;
	}

}

