package ressource.jsp.formulaires;

/**
 * Package qui contient les fonctions gerant les elements
 * constituant un formulaire, ce package ne peut etre
 * utilisé qu'avec le fichier FCTSCTRL.js
 * Ce fichier contient les fonctions de control sur les champs.
 */

import java.util.*;

/**
	* Liste des formulaires
	* @author ressource
	* @see ressource.jsp.libelles.Jsp_Libelles
	*/

public class Jsp_Formulaires extends ressource.jsp.libelles.Jsp_Libelles {


	/**
	 * Retourne le code HTML d'un champ de type TEXT
	 * @param strName  Nom du controle
	 * @param strTxt  Valeur du champ par defaut
	 * @param numSize  Taille du champ
	 * @param numMaxSize  Nombre maximum de charactères dans le champ
	 * @param pOtions  Options à ajouter (ex : readOnly=True)
	 * @return le code HTML généré
	 */
	public String jsp_Ct(String strName, String strTxt, int numSize, int numMaxSize, String pOptions) {

		// Chaines de caracteres utilisees pour le msg final
		StringBuffer str = new StringBuffer();

		str.append("<input type=\"TEXT\" name=\"" );
		str.append( strName );
		str.append( "\"" );

		if (numSize > 0 ) {
			str.append( " size=\"" );
			str.append( numSize );
			str.append( "\"" );
		}

		if (numMaxSize > 0) {
			str.append( " maxlength=\"" );
			str.append( numMaxSize );
			str.append( "\"" );
		}

		if (strTxt != null) {
			str.append( " value=\"" );
			str.append( strTxt );
			str.append( "\"" );
		}

		if (pOptions != null) {
			str.append( " " );
			str.append( pOptions );
			str.append( " " );
		}

		str.append( ">" );
		return str.toString();
	}


	/**
	 * Retourne le code HTML d'un champ de type TEXT
	 * @param strName  Nom du controle
	 * @param strTxt  Valeur du champ par defaut
	 * @param numSize  Taille du champ
	 * @param numMaxSize  Nombre maximum de charactères dans le champ
	 * @return le code HTML généré
	 */
	public String jsp_Ct(String strName, String strTxt, int numSize, int numMaxSize) {
		return this.jsp_Ct(strName, strTxt, numSize, numMaxSize, null);
	}


  /**
	 * Retourne le code HTML d'un champ de type HIDDEN
	 * @param strName  Nom du controle
	 * @param strTxt  Valeur du champ par defaut
	 * @param numSize  Taille du champ
	 * @param numMaxSize  Nombre maximum de charactères dans le champ
	 * @return le code HTML généré
	 */
	public String jsp_CtHidden(String strName, String strTxt) {

		// Chaines de caracteres utilisees pour le msg final
		StringBuffer str = new StringBuffer();

		str.append("<input type=\"HIDDEN\" name=\"" );
		str.append( strName );
		str.append( "\"" );

		if (strTxt != null) {
			str.append( " value=\"" );
			str.append( strTxt );
			str.append( "\"" );
		}

		str.append( ">" );
		return str.toString();
	}


	/**
	 * Méthode qui retourne le code HTML realisant un champ de type TEXT
	 *  avec un évènement ( e.g: onBlur).
	 *  @param StrName     Nom du controle
	 *  @param StrTxt      Valeur du champ par defaut
	 *  @param numSize     Taille du champ
	 *  @param numMaxSize  Nombre maximum de characteres dans le champ
	 *  @param strEvt      Evenement
	 *  @param StrFct      fonction appelée par l'evenement
	 *  @return le code HTML généré.
	 */
	private String jsp_HTML_Ct_Ctrl( String strName, String strTxt, int numSize, int numMaxSize, String strEvt, String strFct) {
		// Chaines de caracteres utilisees pour le msg final
		StringBuffer str = new StringBuffer();
		str.append( "<input type=\"TEXT\" name=\"" );
		str.append( strName );
		str.append( "\"" );

		if (numSize > 0 ) {
			str.append( " size=\"" );
			str.append( numSize );
			str.append( "\"" );
		}

		if (numMaxSize > 0) {
			str.append( " maxlength=\"");
			str.append( numMaxSize );
			str.append( "\"" );
		}

		if (strTxt != null) {
			str.append( " value=\"" );
			str.append( strTxt );
			str.append( "\"" );
			str.append( strEvt );
			str.append( "=\"" );
			str.append( strFct );
			str.append( "\"" );
		}

		str.append( ">" );

		return str.toString();
	}


	/**
	 * Retourne le code HTML realisant un champ de type password.
	 * @param StrName  Nom du controle
	 * @param StrTxt   Valeur du champ par defaut
	 * @param numSize  Taille du champ
	 * @param numMaxSize  Nombre maximum de characteres dans le champ
	 * @return le code HTML généré
	 */
	public String jsp_CtPsw( String strName,String strTxt,int numSize,int numMaxSize) {
		// Chaines de caracteres utilisees pour le msg final
		StringBuffer str = new StringBuffer();

		str.append( "<input type=\"PASSWORD\" name=\"" );
		str.append( strName );
		str.append( "\"" );

		if (numSize > 0 ) {
			str.append( " size=\"" );
			str.append( numSize );
			str.append( "\"" );
		}

		if (numMaxSize > 0) {
			str.append( " maxlength=\"" );
			str.append( numMaxSize );
			str.append( "\"" );
		}

		if (strTxt != null) {
			str.append( " value=\"" );
			str.append( strTxt );
			str.append( "\"" );
		}

		str.append( ">" );

		return str.toString();
	}

  /**
	 * Méthode qui retourne le code HTML realisant un RadioButton nomme strName.<p>
	 * Ce radio button contient une valeur strVal et un libelle associe strTxt.<p>
	 * Le radio button est selectionné si blnSelected est true
	 * @param strName  Nom du radio bouton
	 * @param strTxt  Libelle du radio bouton
	 * @param strVal  Valeur du radio bouton
	 * @param bnlSelected  radio bouton selectionné ou non
	 * @return Le code HTML généré
	 */
	public String jsp_RB( String strName, String strTxt, String strVal,boolean blnSelected) {

      StringBuffer res = new StringBuffer();

      res.append( "<input type=\"RADIO\" id =\"" );
			res.append( strName );
			res.append( "\" name=\"" );
			res.append( strName );
			res.append( "\" value=\"" );
			res.append( strVal);
			res.append( "\"" );
			if (blnSelected) {
				res.append( " checked" );
			}
      res.append( ">" );
      res.append(jsp_Str(strTxt));

      return res.toString();
	}

	/**
	 * Méthode qui retourne le code HTML realisant une liste de RadioButton nomme StrName.<p>
	 * Chaque radio button contient une valeur StrVal et un libelle associe StrTxt.<p>
	 * Le radio button nomme StrSelected est celui active par defaut
	 * @param strName  Nom du controle
	 * @param dimStrTxt  liste des libelles
	 * @param dimStrVal  Liste des valeurs
	 * @param strSelected  Nom du champ active
	 * @param strSens  sens des radio box ('V' ou 'H')
	 * @return Le code HTML généré
	 */
	public String jsp_RB( String strName, Vector dimStrTxt, Vector dimStrVal,String strSelected,String strSens) {
		if (strSens.equals("H")) {
			return jsp_HTML_RadioList(strName ,dimStrTxt ,dimStrVal ,strSelected);
		} else {
			return jsp_HTML_RadioListBR(strName ,dimStrTxt ,dimStrVal ,strSelected);
		}
	}


	/**
	 * Méthode qui retourne le code HTML realisant une liste de RadioButton horizontal
	 * nommée StrName. <p>Chaque radio button contient une valeur StrVal et un libelle
	 * associe StrTxt. <p>Le radio button nomme strValSel est celui active par defaut <p>
	 * La liste des radios est horizontale.
	 * @param strName   Nom du controle
	 * @param dimStrTxt  liste des libelles sous forme de vecteurs
	 * @param dimStrVal  Liste des valeurs  sous forme de vecteurs
	 * @param strValSel  Nom du champ active valeur du champ selectionné par defaut
	 * @return Le code HTML généré
	 */
	private String jsp_HTML_RadioList(String strName ,Vector dimStrTxt ,Vector dimStrVal ,String strValSel) {
		StringBuffer res = new StringBuffer();

		for (int i=0;i<dimStrTxt.size();i++) {
			res.append( "<input type=\"RADIO\" id =\"" );
			res.append( strName );
			res.append( "\" name=\"" );
			res.append( strName );
			res.append( "\" value=\"" );
			res.append( dimStrVal.elementAt(i).toString() );
			res.append( "\"" );
			if (dimStrVal.elementAt(i).toString().equals(strValSel)) {
				res.append( " checked>" );
			} else {
				res.append( ">" );
			}

			res.append( jsp_Str(dimStrTxt.elementAt(i).toString()) );
		}

		return res.toString();
	}


	/**
	 * Méthode qui retourne le code HTML realisant une liste de RadioButton horizontal
	 * nommée StrName. <p> Chaque radio button contient une valeur StrVal
	 * et un libelle associe StrTxt. <p> Le radio button nomme strValSel est celui
	 * active par defaut. La liste des radios est verticale.
	 * @param strName  Nom du controle
	 * @param dimStrTxt  liste des libelles sous forme de vecteurs
	 * @param dimStrVal  Liste des valeurs  sous forme de vecteurs
	 * @param strValSel  Nom du champ active valeur du champ selectionné par defaut
	 * @return Le code HTML généré
	 */
	private String jsp_HTML_RadioListBR(String strName,Vector dimStrTxt,Vector dimStrVal,String strValSel) {
		StringBuffer res = new StringBuffer();

		for (int i=0;i<dimStrTxt.size();i++) {
			res.append( "<input id =\"" );
			res.append( strName );
			res.append( "\" type=\"RADIO\" name=\"" );
			res.append( strName );
			res.append( "\" value=\"" );
			res.append( dimStrVal.elementAt(i).toString() );
			res.append( "\"" );
			if (dimStrVal.elementAt(i).toString().compareTo(strValSel)==0) {
				res.append( " checked>" );
			} else {
				res.append( ">" );
			}
			res.append( jsp_Str(dimStrTxt.elementAt(i).toString()) );
			res.append( "<BR>" );
		}

		return res.toString();
	}


	/**
	 * Retourne le code HTML affichant une checkbox
	 * @param strName  Nom du controle
	 * @param strTxt  Libelle
	 * @param val vrai si la case est cochée par défaut
	 * @return Le code HTML généré
	 */
	public String jsp_CB(String strName,String strTxt, boolean val) {
		return jsp_HTML_CheckBool(strName,strTxt,"true",val);
	}


	/**
	 * Retourne le code HTML affichant une checkbox
	 * @param strName  Nom du controle
	 * @param strTxt  Libelle
	 * @param value   Valeur de la checkebox
	 * @param val  vrai si la case est cochée par défaut
	 * @return Le code HTML généré
	 */
	public String jsp_CB(String strName,String strTxt,String value, boolean val) {
		return jsp_HTML_CheckBool(strName,strTxt,value,val);
	}

	/**
	 * Retourne le code HTML affichant une checkbox
	 * @param strName  Nom du controle
	 * @param strTxt  Libelle
	 * @param value  Valeur de la checkebox
	 * @param val  vrai si la case est cochée par défaut
	 * @return Le code HTML généré
	 */
	private String jsp_HTML_CheckBool( String strName,String strTxt,String value, boolean val) {
		StringBuffer res = new StringBuffer();

		res.append( "<input type=\"CHECKBOX\" id =\"" );
		res.append( strName );
		res.append( "\" name=\"" );
		res.append( strName );
		res.append( "\" value=\"" );
		res.append( value );
		res.append( "\" " );

		// si le controle doit etre checké
		if (val) {
			res.append( "checked" );
		}

		res.append( " >" );
		res.append( jsp_Str(strTxt) );

		return res.toString();
	}


	/**
	 * Le code HTML d'un controle de type Date
	 * @param strName  nom du controle
	 * @param strNbr  valeur du champ
	 * @return Le code HTML généré
	 */
	public String jsp_CtDate(String strName, String strDate) {
		return jsp_HTML_Ct_Ctrl( strName, strDate, 10, 10, "onBlur", "js_fIsValideDate(this,DEBUG)");
	}

        /**
	 * Le code HTML d'un controle de type Heure
	 * @param strName  nom du controle
	 * @param strNbr  valeur du champ
	 * @return Le code HTML généré
	 */
	public String jsp_CtHeure(String strName, String strHeure) {
		return jsp_HTML_Ct_Ctrl( strName, strHeure, 5, 5, "onBlur", "js_fIsValideHeure(this,DEBUG)");	}

	/**
	 * Retourne le code HTML d'un controle de type Periode.
	 * @param strName  nom du controle
	 * @param strNbr : valeur du champ
	 * @return Le code HTML généré
	 */
	public String jsp_CtPeriode(String strName,String strDate) {
		return jsp_HTML_Ct_Ctrl(strName,strDate,10,7,"onBlur","js_fIsValidePeriode(this,DEBUG)");
	}


	/**
	 * Retourne le code HTML d'un controle de type Montant : initialise par strNbr
	 * @param strName  nom du controle
	 * @param strNbr  valeur du champ
	 * @return Le code HTML généré
	 */
	public String jsp_CtMnt(String strName,String strNbr) {
		return jsp_HTML_Ct_Ctrl(strName, strNbr, 22, 22, "style=\"text-align:right\" onBlur", "js_fVerifIsNum(this)" );
	}


	/**
	 * Retourne un controle de type Montant, initialise par strNbr
	 * avec un champs de petite taille
	 * @param strName  nom du controle
	 * @param strNbr  valeur du champ
	 * @return Le code HTML d'un controle de type Montant
	 */
	public String jsp_CtMntSmall(String strName,String strNbr) {
		return jsp_HTML_Ct_Ctrl(strName, strNbr, 4, 6, "style=\"text-align:right\" onBlur", "js_fVerifIsNum(this)" );
	}


	/**
	 * Retourne un controle de type Montant initialise par strNbr
	 * @param strName  nom du controle
	 * @param strNbr  valeur du champ
	 * @param size  taille du controle
	 * @param max  longueur maximum du controle
	 * @return Le code HTML d'un controle de type Montant
	 */
	public String jsp_CtMntLimited(String strName,String strNbr,int size,int max) {
		return jsp_HTML_Ct_Ctrl( strName,  strNbr, size, max, "style=\"text-align:right\" onBlur", "js_fVerifIsNumMC(this)" );
	}


	/**
	 * Retourne un controle de type Entier initialise par strNbr
	 * @param strName  nom du controle
	 * @param strNbr   valeur du champ
	 * @return Le code HTML d'un controle de type Entier
	 */
	public String jsp_CtInt(String strName,String strNbr) {
		return jsp_HTML_Ct_Ctrl(strName, strNbr, 8, 8, "onBlur", "js_fIsInt(this)" );
	}


	/**
	 * Retourne un controle de type Entier initialise par strNbr
	 * @param strName    nom du controle
	 * @param strNbr     valeur du champ
	 * @param numSize    largeur du controle
	 * @param numMaxSize maxlength du controle
	 * @return Le code HTML d'un controle de type Entier
	 */
	public String jsp_CtInt(String strName,String strNbr, int numSize,int numMaxSize) {
		return jsp_HTML_Ct_Ctrl(strName, strNbr, numSize, numMaxSize, "onBlur", "js_fIsInt(this)" );
	}


	/**
	 * Retourne un controle de type Taux initialise par strNbr
	 * @param strName  nom du controle
	 * @param strNbr : valeur du champ
	 * @return Le code HTML d'un controle de type taux
	 */
	public String jsp_CtTaux(String strName,String strNbr) {
		return jsp_HTML_Ct_Ctrl(strName, strNbr, 5, 5, "onBlur", "js_fIsTaux( this, DEBUG )" )+" %";
	}


	/**
	 * Retourne le code HTML d'un controle de type email
	 * @param strName  nom du controle
	 * @param strEmail valeur du champ
	 * @return Le code HTML du controle de type email
	 */
	public String jsp_CtEmail(String strName,String strEmail) {
		return jsp_HTML_Ct_Ctrl(strName, strEmail, 20, 50, "onBlur", "js_fIsEMail(this,DEBUG )" );
	}


	/**
	 * Retourne le code HTML d'un controle TEXTAREA
	 * @param StrName  Nom du controle
	 * @param strTxt   valeur du champ
	 * @param NumRows  Nombre de lignes de la MLE
	 * @param NumCols  Nombre de colonnes de la MLE
	 * @return la code HTML générant un champ TEXTAREA
	 */
	public String jsp_CtArea(String strName,String strTxt,int numCols,int numRows) {
		return jsp_HTML_CtArea( strName, strTxt, numCols, numRows);
	}


	/**
	 * Retourne un controle de type zone de texte
	 * @param strName  nom du controle
	 * @param strTxt   valeur du champ
	 * @param numCols  largeur du champ
	 * @param numRows  hauteur du champ
	 * @return Le code HTML d'un TextArea
	 */
	private String jsp_HTML_CtArea(String strName, String strTxt, int numCols, int numRows) {
		StringBuffer res = new StringBuffer();

		res.append( "<textarea class=\"jspStr\" wrap id =\"" );
		res.append( strName );
		res.append( "\" name=\"" );
		res.append( strName );
		res.append( "\"" );

		if (numCols>0) {
			res.append( " cols=\"" );
			res.append( numCols );
			res.append( "\"" );
		}
		if (numRows>0){
		   res.append( " rows=\"" );
			 res.append( numRows );
			 res.append( "\"" );
		}
		if (strTxt == null) {
			res.append( "></textarea>" );
		} else {
			res.append( ">" );
			res.append( strTxt );
			res.append( "</textarea>" );
		}

		return res.toString();
	}


	/**
	 * Affiche une liste déroulante avec une première ligne vide.
	 * @param strname  nom du combobox
	 * @param vecData  Vecteur contenant la liste des données.
	 * @param strValSel  indique la valeur à afficher par défaut
	 * @return La ligne de commande HTML
	 */
	public String jsp_Combobox(String strName,Vector vecData,String strValSel) {
		return jsp_HTML_Combobox(strName,1,vecData,strValSel,"", null,false);
	}


	/**
	 * Affiche une liste déroulante avec une ligne supplémentaire
	 * @param strLigneVide  la ligne vide à ajouter.
	 * @param strName  nom du combobox
	 * @param vecData  Vecteur contenant la liste des données.
	 * @param dimStrValue  Vecteur contenant la liste des valeurs
	 * @param  strValSel  la valeur à afficher par défaut
	 * @return La ligne de commande HTML
	 */
	public String jsp_Combobox(String strLigneVide, String strName,Vector vecData,String strValSel) {
		return jsp_HTML_Combobox(strName,1,vecData,strValSel,"", strLigneVide,false);
	}


	/**
	 * Affiche une liste déroulante et lui associe un evenement
	 * @param strName     nom de la listbox
	 * @param dimStrTxt   Vecteur contenant la liste des Libelles
	 * @param dimStrValue Vecteur contenant la liste des valeurs
	 * @param strValSel   indique la valeur à afficher par défaut
	 * @param strCtrl     options facultatives
	 * @return La ligne de commande HTML
	 */
	public String jsp_Combobox(String strName,Vector vecData,String strValSel,String strCtrl) {
		return jsp_HTML_Combobox(strName,1,vecData,strValSel,strCtrl, null,false);
	}

  /**
	 * Affiche une liste déroulante à selection multiple sans de première ligne
	 * @param strName      nom du combobox
	 * @param data         Vecteur contenant la liste des couples de données, le premier element
   *                     du couple est le libellé puis le second est sa valeur.
   * @param vcrValSel    Vecteur contenant les valeurs des elements selectionnés de la liste
   * @param strSize      Nombre de lignes de la liste
	 * @param strCtrl      options facultatives ajoutées dans le tag &lt;SELECT&gt;
	 * @return La ligne de commande HTML
	 */
	public String jsp_MultiCombobox(String strName,Vector data, Vector vcrValSel, String strSize,String strCtrl) {
    return jsp_HTML_MultiCombobox(strName,data,vcrValSel,strSize,strCtrl,null);
  }

  /**
	 * Affiche une liste déroulante à selection multiple
	 * @param strName      nom du combobox
	 * @param data         Vecteur contenant la liste des couples de données, le premier element
   *                     du couple est le libellé puis le second est sa valeur.
   * @param vcrValSel    Vecteur contenant les valeurs des elements selectionnés de la liste
   * @param strSize      Nombre de lignes de la liste
	 * @param strCtrl      options facultatives ajoutées dans le tag &lt;SELECT&gt;
	 * @param strLigneVide valeur de la première ligne de la combobox
	 * @return La ligne de commande HTML
	 */
	private String jsp_HTML_MultiCombobox(String strName,Vector data, Vector vcrValSel, String strSize,String strCtrl, String strLigneVide) {
          StringBuffer res = new StringBuffer();

          res.append( "<select id=\"" );
          res.append( strName );
          res.append( "\" name=\"" );
          res.append( strName );
          res.append( "\" MULTIPLE " );
          res.append( "size=\"" );
          res.append( strSize );
          res.append( "\"" );
          res.append( strCtrl );
          res.append( " >" );

          if (strLigneVide != null) {
            res.append( "<option value=\"\">" );
            res.append( strLigneVide );
            res.append( "</option>" );
          }

          for (int i=0;i<data.size();i++) {
            Vector vec = (Vector)data.elementAt(i);
            if(vec!=null){
              res.append( "<option value=\"" );
              res.append( vec.elementAt(1).toString() );
              res.append("\"");
              if(vcrValSel!=null){
                for(int j=0;j<vcrValSel.size();j++){
                  if (vec.elementAt(1).toString().equals((String)vcrValSel.elementAt(j))) {
                    res.append(" selected");
                  }
                }
              }
            }
            res.append(">");
            res.append( jsp_Str(vec.elementAt(0).toString()) );
            res.append( "</option>" );
          }
          res.append( "</select>" );
          return res.toString();
	}

   /**
	 * Affiche une liste déroulante avec selection multiple et possibilite d'evenements
	 * @param strName      nom du combobox
	 * @param size         Nombre de ligne visible de la list box
	 * @param dimStrTxt    Vecteur contenant la liste des Libelles
	 * @param dimStrValue  Vecteur contenant la liste des valeurs
	 * @param strValSel    indique la valeur à afficher par défaut
	 * @param strCtrl      options facultatives
	 * @param boolMultiple Active ou non la selection multiple pour la listbox
	 * @return La ligne de commande HTML
	 */
	public String jsp_Listbox(String strName ,int size ,Vector vecData ,String strValSel ,String strCtrl ,boolean boolMultiple ) {
		return jsp_HTML_Combobox(strName ,size,vecData ,strValSel ,strCtrl ,null ,boolMultiple );
	}


	/**
	 * Affiche une liste déroulante
	 * @param strName      nom du combobox
	 * @param dimStrTxt    Vecteur contenant la liste des Libelles
	 * @param dimStrValue  Vecteur contenant la liste des valeurs
	 * @param strValSel    indique la valeur à afficher par défaut
	 * @param strCtrl      options facultatives
	 * @param strLigneVide valeur de la premère ligne d ela combobox
	 * @return La ligne de commande HTML
	 */
	private String jsp_HTML_Combobox(String strName ,int size ,Vector data, String strValSel,String strCtrl, String strLigneVide, boolean multiple) {
		StringBuffer res = new StringBuffer();

		res.append( "<select id=\"" );
		res.append( strName );
		res.append( "\" name=\"" );
		res.append( strName );
		res.append( "\" " );
		res.append( "\" size=\"" );
		res.append( size );
		res.append( "\" " );
		res.append( strCtrl );
		if(multiple) {
		  res.append(" multiple");
		}
		res.append( " >" );

		// on verifie si strValSel est null si oui on l'initialise a ""
		if (strValSel == null) {
			strValSel = "";
		}

		if (strLigneVide != null) {
			res.append( "<option value=\"\">" );
			res.append( strLigneVide );
			res.append( "</option>" );
		}

		for (int i=0;i<data.size();i++) {
			Vector vec = (Vector)data.elementAt(i);
			if (vec.elementAt(1).toString().equals(strValSel)) {
				res.append( "<option value=\"" );
				res.append( vec.elementAt(1).toString() );
				res.append( "\" selected>" );
				res.append( jsp_Str(vec.elementAt(0).toString()) );
				res.append( "</option>" );
			} else {
				res.append( "<option value=\"" );
				res.append( vec.elementAt(1).toString() );
				res.append( "\">" );
				res.append( jsp_Str(vec.elementAt(0).toString()) );
				res.append( "</option>" );
			}
		}

		res.append( "</select>" );

		return res.toString();
	}


	/**
	 * Creation d'un bouton submit avec une image
	 * @param strImage  Chemin de l'image.
	 * @param strAlt    Libelle apparaissant sur l'image.
	 * @param width     Largeur de l'image.
	 * @param height    Hauteur de l'image.
	 * @return Code HTML correspondant a un bouton submit avec une image
	 */
	public String jsp_Submit(String strImage,String strAlt,String width,String height) {
		StringBuffer res = new StringBuffer();

		res.append( "<a href=\"javascript:document.forms[0].submit();\"><img src=\"" );
		res.append( strImage );
		res.append( "\" border=\"0\" alt=\"" );
		res.append( strAlt );
		res.append( "\" width=\"" );
		res.append( width );
		res.append( " \" height=\"" );
		res.append( height );
		res.append( "\" border=\"0\"></a>" );

		return res.toString();
	}


	/**
	 * Creation d'un bouton submit avec une image et un nom
	 * @param strImage  Chemin de l'image.
	 * @param strAlt    Libelle apparaissant sur l'image.
	 * @param width     Largeur de l'image.
	 * @param height    Hauteur de l'image.
	 * @param name      Nom du formulaire cible
	 * @return Code HTML correspondant a un bouton submit avec une image
	 */
	public String jsp_NamedSubmit(String strImage,String strAlt,String width,String height,String name) {
		StringBuffer res = new StringBuffer();

		res.append( "<a href=\"javascript:document."+name+".submit();\"><img src=\"" );
		res.append( strImage );
		res.append( "\" border=\"0\" alt=\"" );
		res.append( strAlt );
		res.append( "\" width=\"" );
		res.append( width );
		res.append( " \" height=\"" );
		res.append( height );
		res.append( "\" border=\"0\"></a>" );

		return res.toString();
	}


	/**
	 * Creation d'un bouton submit avec une image et une liste d'options (ex : name)
	 * @param strImage  Chemin de l'image.
	 * @param strAlt  Libelle apparaissant sur l'image.
	 * @param width  Largeur de l'image.
	 * @param height  Hauteur de l'image.
	 * @param options  options facultatives.
	 * @return Code HTML correspondant a un bouton submit avec une image
	 */
	public String jsp_Submit(String strImage,String strAlt,String width,String height,String options) {
		StringBuffer res = new StringBuffer();

		res.append( "<a href=\"javascript:document.forms[0].submit();\"><img src=\"" );
		res.append( strImage );
		res.append( "\" border=\"0\" alt=\"" );
		res.append( strAlt );
		res.append( "\" width=\"" );
		res.append( width );
		res.append( "\" height=\"" );
		res.append( height );
		res.append( "\" border=\"0\" " );
		res.append( options );
		res.append( " ></a>" );

		return  res.toString();
		}


	/**
	 * Creation d'un bouton submit avec une image et une liste d'options (ex : name)
	 * @param strImage Chemin de l'image.
	 * @param strAlt   Libelle apparaissant sur l'image.
	 * @param width    Largeur de l'image.
	 * @param height   Hauteur de l'image.
	 * @param options  options facultatives.
	 * @param action   page action du submit.
	 * @return Code HTML correspondant a un bouton submit avec une image
	 */
	public String jsp_Submit(String strImage,String strAlt,String width,String height,String options, String action) {
		StringBuffer res = new StringBuffer();

		res.append("<a href=\"javascript:document.forms[0].action='" );
		res.append( action );
		res.append( "\';document.forms[0].submit();\"><img src=\"" );
		res.append( strImage );
		res.append( "\" border=\"0\" alt=\"" );
		res.append( strAlt );
		res.append( "\" width=\"" );
		res.append( width );
		res.append( "\" height=\"" );
		res.append( height );
		res.append( "\" border=\"0\" " );
		res.append( options );
		res.append( " ></a>" );

		return res.toString();
	}

	/**
	 * Creation d'un bouton submit avec une image et une liste d'options (ex : name)
	 * @param strImage Chemin de l'image.
	 * @param strAlt   Libelle apparaissant sur l'image.
	 * @param width    Largeur de l'image.
	 * @param height   Hauteur de l'image.
	 * @param options  options facultatives.
	 * @param action   page action du submit.
	 * @param index index du formulaire
	 * @return Code HTML correspondant a un bouton submit avec une image
	 */
	public String jsp_Submit(String strImage,String strAlt,String width,String height,String options, String action, int index) {
		StringBuffer res = new StringBuffer();

		res.append("<a href=\"javascript:document.forms["+index+"].action='" );
		res.append( action );
		res.append( "\';document.forms[0].submit();\"><img src=\"" );
		res.append( strImage );
		res.append( "\" border=\"0\" alt=\"" );
		res.append( strAlt );
		res.append( "\" width=\"" );
		res.append( width );
		res.append( "\" height=\"" );
		res.append( height );
		res.append( "\" border=\"0\" " );
		res.append( options );
		res.append( " ></a>" );

		return res.toString();
	}


  /**
   *  Description de la méthode
   *
   *@param  strImage  Description of the Parameter
   *@param  strAlt    Description of the Parameter
   *@param  width     Description of the Parameter
   *@param  height    Description of the Parameter
   *@param  commande  Description of the Parameter
   *@return           Description of the Return Value
   */
  public String jsp_Bouton(String strImage, String strAlt, String width, String height, String commande) {
    StringBuffer res = new StringBuffer();

    res.append("<a href=\"").append(commande).append("\"><img src=\"");
    res.append(strImage);
    res.append("\" border=\"0\" alt=\"");
    res.append(strAlt);
    res.append("\" width=\"");
    res.append(width);
    res.append("\" height=\"");
    res.append(height);
    res.append("\" border=\"0\" ");
    res.append(" ></a>");

    return res.toString();
  }


	/**
	 * Affiche un controle de type FILE
	 * @param strName   Nom du controle
	 * @param strSize   taille du controle
	 * @return le code HTML généré
	 */
	public String jsp_CtFile(String strName,String strSize) {
		StringBuffer res = new StringBuffer();

		res.append("<input type=\"FILE\" name=\"" );
		res.append( strName );
		res.append( "\" size=\"" );
		res.append( strSize );
		res.append( "\">" );

		return res.toString();
	}


	/**
	 * Affiche un controle de type FILE
	 * @param strName   Nom du controle
	 * @param strSize   taille du controle
	 * @param strEvent  Evenement i.e : onblur
	 * @return le code HTML généré
	 */
	public String jsp_CtFile(String strName,String strSize,String strEvent) {
		StringBuffer res = new StringBuffer();

		res.append("<input type=\"FILE\" name=\"" );
		res.append( strName );
		res.append( "\" size=\"" );
		res.append( strSize  );
		res.append( "\" " );
		res.append( strEvent );
		res.append( " >" );

		return res.toString();
	}

}

