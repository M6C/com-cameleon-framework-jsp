package ressource.jsp.tableau;

/**
 * Package qui permet la creation d'un tableau de valeurs
 * avec un entete et une gestion d'index de pages.
 */

import java.text.SimpleDateFormat;
import java.util.*;
import ressource.jsp.formulaires.*;
import ressource.jsp.libelles.*;
import ressource.jsp.liens.*;
import ressource.FxUtils;

/**
 * Classe permettant de construire un tableau
 * @author ressource
 * @version 1.0
 */

public class Jsp_Tableau{

  public final static int INDICE_NOM = 0;
  public final static int INDICE_PRESENTATION = 1;
  public final static int INDICE_CIBLE = 2;
  public final static int INDICE_TYPE = 3;
  public final static int INDICE_POSITION = 4;
  public final static int INDICE_PARAMETRES_SPEC = 5;

  public Jsp_Tableau() {
  }

  // Séparateur utilisé lors de la construction des noms de champs dans un tableau
  public final static String FLD_SEPARATEUR = "_";

  private String infoBulle = null;

  private String frameName = null;
  private String fnJavascriptPopup = null;
  private String widthPopup = null;
  private String heightPopup = null;
  private String namePopup = null;
  private String paramPopup = null;

  /** valeur locale : sert pou rle format de la date */
  private Locale frmLocale =  new Locale("FRENCH", "FRANCE");
  /** Format des dates */
  private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", frmLocale);

  /**
  le vecteur entete contient des vecteurs ( un vecteur represent une colonne )
  qui chacun renferme l'entête de la colonne et les proprietes de la colonne. <p>
  Vecteur row contient des vecteurs qui representent
  le tableau et ses valeurs dans son entier. e.g :[row(columns)]
  */

 /**
  * Methode qui permet de creer/d'ajouter une entete de tableau
  * @param name   chaine de caracteres contenant le nom de la colonne
  * @param parameters  chaine de caracteres qui contient tous parametres utiles passe en parametre
  * @param  type    chaine de caracteres definissant le type de la colonne
  * @param position  Entier representant la position de la colonne dans le vecteur de donnees
  * @return Le code HTML généré
  */
  public Vector jsp_NewColumnHead(String name,String parameters,String type,int position) {
    Vector head = new Vector();
    Integer pos=new Integer(position);
    head.addElement(name);
    head.addElement(parameters);
    head.addElement("");
    head.addElement(type);
    head.addElement(pos);

    return (head);
  }

  /**
   * Methode qui permet de creer/d'ajouter une entete de tableau
   * @param name   chaine de caracteres contenant le nom de la colonne
   * @param parameters  chaine de caracteres qui contient tous parametres utiles passe en parametre
   * @param  type    chaine de caracteres definissant le type de la colonne
   * @param position  Entier representant la position de la colonne dans le vecteur de donnees
   * @return Le code HTML généré
   */
  public Vector jsp_NewColumnHead(String name,String parameters,String type,int position,String strCible) {
    Vector head = new Vector();
    Integer pos=new Integer(position);
    head.addElement(name);
    head.addElement(parameters);
    head.addElement(strCible);
    head.addElement(type);
    head.addElement(pos);

    return (head);
  }

  /**
   * Retourne le code HTML d'une ligne sous forme de chaine de caractere
   * @param values      Vecteur contenant les valeurs d'une ligne du tableau.
   * @param head        Vecteur contenant l'entete d'une colonne du tableau.
   * @param parameters  chaine de caracteres qui contient tous parametres utiles passe en parametre
   * @param coul        vrai si'il faut afficher la ligne en couleur
   * @return chaine de caracteres contenant le code html d'une ligne de tableau
   */
  public String jsp_AddRow(Vector head,Vector values,String parameter, boolean coul) {
    return jsp_AddRow(head, values, parameter, coul, -1, -1, null);
  }



  /**
   * Retourne le code HTML d'une ligne sous forme de chaine de caractere
   * @param values     Vecteur contenant les valeurs d'une ligne du tableau.
   * @param head       Vecteur contenant l'entete d'une colonne du tableau.
   * @param parameters chaine de caracteres qui contient tous parametres utiles passe en parametre
   * @param coul       vrai si'il faut afficher la ligne en couleur
   * @param colonneRupture   le numéro de la colonne rupture (à ne pas afficher);
   * @return chaine de caracteres contenant le code html d'une ligne de tableau
   */
  private String jsp_AddRow(Vector head, Vector values, String parameter, boolean coul, int colonneRupture, int pColonneIds, Vector pListeColonneIds) {

    StringBuffer res = new StringBuffer();
    if (coul) {
      res.append("<TR class=\"jspTableauBicolor\">");
    } else {
      res.append("<TR class=\"jspTableauColor\">");
    }

    int pos = 0;
    Vector headBox = new Vector();

    //FGA*28/11/2001 : ajout d'un style pour une ligne appartenent à un élément du panier
    if ((pListeColonneIds != null) && (pColonneIds >= 0)) {
      if (pListeColonneIds.contains(values.elementAt(pColonneIds).toString())) {
        res.append("<TR class=\"jspTableauLigneDuPanier\">");
      }
    }

    pos = 0;
    if (colonneRupture > -1) {
      res.append("<TD>&nbsp;</TD>");
    }

    // je commence par rechercher le rang 0
    for (int rang = 0; rang < head.size(); rang++) {
      if (rang != colonneRupture) {
        headBox = (Vector) head.elementAt(rang);
        pos = ((Integer) headBox.elementAt(INDICE_POSITION)).intValue();

        // Pour un montant, il faut aligner à droite
        if (headBox.elementAt(INDICE_TYPE).toString().trim().equals("montant") || headBox.elementAt(INDICE_TYPE).toString().trim().equals("entier")) {
          res.append("<TD align=\"right\" " + jsp_GetParameters((Vector) head.elementAt(rang)) + " >" + jsp_GetHeadType(headBox, values, values.elementAt(pos), head, parameter));
        } else {
          res.append("<TD " + jsp_GetParameters((Vector) head.elementAt(rang)) + " >" + jsp_GetHeadType(headBox, values, values.elementAt(pos), head, parameter));
        }
        res.append("</TD>");
      }
    }

    res.append("</tr>");

    return res.toString();
  }


  /**
   *  Affiche les totaux d'un tableau avec Rupture
   *
   *@param  head            les entetes du tableaux
   *@param  totaux          la liste des totaux des colonens
   *@param  colonneRupture  le numéro de la colonne de la rupture
   *@return                 le code HTML
   */
  public String jsp_AddTotaux(Vector head, Vector totaux, int colonneRupture) {
    Jsp_Libelles jsp = new Jsp_Libelles();
    StringBuffer res = new StringBuffer();

    res.append("<TR class=\"jspTableauTotal\">");

    int pos = 0;

    // une première colonne vide
    // Différence par rapport à un tableau
    if (colonneRupture > -1) {
      res.append("<TD>&nbsp;</TD>");
    }

    // je commence par rechercher le rang 0
    // Pour toutes les colonnes à sommer, j'affiche le total
    for (int rang = 0; rang < head.size(); rang++) {
      if (rang != colonneRupture) {
        if (totaux.elementAt(rang) != null) {
          Vector headBox = (Vector) head.elementAt(rang);
          if (headBox.elementAt(INDICE_TYPE).toString().equals("entier")) {
            res.append("<TD align=\"right\">" + jsp.jsp_Int(totaux.elementAt(rang).toString()) + "</TD>");
            totaux.setElementAt("0", rang);
          } else if (headBox.elementAt(INDICE_TYPE).toString().equals("montant")) {
            res.append("<TD align=\"right\">" + jsp.jsp_Mnt(totaux.elementAt(rang).toString()) + "</TD>");
            totaux.setElementAt("0.0", rang);
          } else {
            res.append("<TD align=\"left\">" + jsp.jsp_Data(totaux.elementAt(rang).toString()) + "</TD>");
            totaux.setElementAt("", rang);
          }
        } else {
          res.append("<TD>&nbsp;</TD>");
        }
      }
    }

    res.append("</TR>");

    return res.toString();
  }


  /**
   * Retourne la valeur d'une cellule sous forme de chaine de caractere
   * @param values  Vecteur contenant les valeurs d'une ligne du tableau
   * @param pos     position de la valeur à récuperer
   * @return chaine de caracteres contenant la valeur de la cellule
   */
  private String jsp_GetValue(Vector values,int pos) {
    return values.elementAt(pos).toString();
  }

  /**
   * Cree le tableau avec rupture
   * @param head   Vecteur contenant l'entete d'une colonne du tableau.
   * @param row    Vecteur contenant les valeurs des lignes
   * @param border  Chaine de caractere representant la bordure du tableau
   * @param nbrPage  Entier qui represente le nombre de page/index
   * @param nbrLigne  Entier qui represente le nombre de lignes/page
   * @param indexPage  Entier qui represente la page a afficher
   * @param couleur Boolean qui represente si le tableau doit etre bicolor (true)
   * @param parameter  Parametre optionnel du tableau
   * @return chaine de caracteres contenant le code html definissant le tableau.
   */
  public String jsp_CreateTableRupture(Vector head,Vector row,String border,int nbrPage,int nbrLigne,String indexPage,boolean couleur,String parameter, int colonneRupture, Vector colonneSomme) {
    return jsp_CreateTableRupture(head,row,border,"center","1","2",nbrPage,nbrLigne,indexPage,couleur,parameter,colonneRupture,colonneSomme,"");
  }

  /**
   * Cree le tableau avec rupture
   * @param head   Vecteur contenant l'entete d'une colonne du tableau.
   * @param row    Vecteur contenant les valeurs des lignes
   * @param border  Chaine de caractere representant la bordure du tableau
   * @param align      Proprété align du tableau
   * @param cellpadding  Proprété cellpadding du tableau
   * @param cellspacing  Proprété cellspacing du tableau
   * @param nbrPage  Entier qui represente le nombre de page/index
   * @param nbrLigne  Entier qui represente le nombre de lignes/page
   * @param indexPage  Entier qui represente la page a afficher
   * @param couleur Boolean qui represente si le tableau doit etre bicolor (true)
   * @param parameter  Parametre optionnel du tableau
   * @return chaine de caracteres contenant le code html definissant le tableau.
   */
  public String jsp_CreateTableRupture(Vector head,Vector row,String border, String align, String cellpadding, String cellspacing,int nbrPage,int nbrLigne,String indexPage,boolean couleur,String parameter, int colonneRupture, Vector colonneSomme) {
    return jsp_CreateTableRupture(head,row,border,align,cellpadding,cellspacing,nbrPage,nbrLigne,indexPage,couleur,parameter,colonneRupture,colonneSomme,"");
  }

  /**
   * Cree le tableau avec rupture
   * @param head   Vecteur contenant l'entete d'une colonne du tableau.
   * @param row    Vecteur contenant les valeurs des lignes
   * @param border  Chaine de caractere representant la bordure du tableau
   * @param nbrPage  Entier qui represente le nombre de page/index
   * @param nbrLigne  Entier qui represente le nombre de lignes/page
   * @param indexPage  Entier qui represente la page a afficher
   * @param couleur Boolean qui represente si le tableau doit etre bicolor (true)
   * @param parameter  Parametre optionnel du tableau
   * @param strChemin  Chemin cible du lien de l'index
   * @return chaine de caracteres contenant le code html definissant le tableau.
   */
  public String jsp_CreateTableRupture(Vector head,Vector row,String border,int nbrPage,int nbrLigne,String indexPage,boolean couleur,String parameter, int colonneRupture, Vector colonneSomme,String strChemin) {
    return jsp_CreateTableRupture(head,row,border,"center","1","2",nbrPage,nbrLigne,indexPage,couleur,parameter,colonneRupture,colonneSomme,strChemin);
  }

  /**
   * Cree le tableau en mettant en couleur les lignes passées en paramètre
   * @param head   Vecteur contenant l'entete d'une colonne du tableau.
   * @param row    Vecteur contenant les valeurs des lignes
   * @param border  Chaine de caractere representant la bordure du tableau
   * @param nbrPage  Entier qui represente le nombre de page/index
   * @param nbrLigne  Entier qui represente le nombre de lignes/page
   * @param indexPage  Entier qui represente la page a afficher
   * @param couleur Boolean qui represente si le tableau doit etre bicolor (true)
   * @param parameter  Parametre optionnel du tableau
   * @param pColonneIds parametre optionnel. indique la colonne référence pour le panier
   * @param Vector pListeColonneIds Liste des ids du panier
   * @param strChemin  Chemin cible du lien de l'index
   * @return chaine de caracteres contenant le code html definissant le tableau.
   */
  public String jsp_CreateTableAvecPanier(Vector head,Vector row,String border, int nbrPage,int nbrLigne,String indexPage,boolean couleur,String parameter, int pColonneIds, Vector pListeColonneIds, String strChemin) {
    return jsp_CreateTableAvecPanier(head, row, border, "center", "1","2", nbrPage, nbrLigne, indexPage, couleur, parameter, pColonneIds, pListeColonneIds, strChemin);
  } // end

  /**
   * Cree le tableau en mettant en couleur les lignes passées en paramètre
   * @param head   Vecteur contenant l'entete d'une colonne du tableau.
   * @param row    Vecteur contenant les valeurs des lignes
   * @param border  Chaine de caractere representant la bordure du tableau
   * @param nbrPage  Entier qui represente le nombre de page/index
   * @param nbrLigne  Entier qui represente le nombre de lignes/page
   * @param indexPage  Entier qui represente la page a afficher
   * @param couleur Boolean qui represente si le tableau doit etre bicolor (true)
   * @param parameter  Parametre optionnel du tableau
   * @param pColonneIds parametre optionnel. indique la colonne référence pour le panier
   * @param Vector pListeColonneIds Liste des ids du panier
   * @param strChemin  Chemin cible du lien de l'index
   * @return chaine de caracteres contenant le code html definissant le tableau.
   */
  public String jsp_CreateTableAvecPanier(Vector head,Vector row,String border, String align, String cellpadding, String cellspacing, int nbrPage,int nbrLigne,String indexPage,boolean couleur, String parameter, int pColonneIds, Vector pListeColonneIds, String strChemin) {
    StringBuffer res = new StringBuffer();
    String color = "";
    boolean coul = false;

    // permet de verifier si l'option couleur est activee
    if (couleur==true) {
      coul = true;
    }
    res.append( "<table cellpadding=\"" + cellpadding + "\" cellspacing=\"" + cellspacing + "\" align=\"" + align + "\" border=\"" + border + "\" >" );

    // Creation de l'entete du tableau
    res.append( jsp_CreateHeadTable(head) );

    // on cree une ligne du tableau selon les specificite passe en parametre dans l'entete (head) avec les valeurs
    // contenues dans le vecteur de vecteur row ( rappel : chaque ligne de row contient un vecteur regroupant
    // les valeurs de toutes les colonnes).

    // Test de la page a afficher
    int index = 1;
    int ligneAAfficher=0;
    if ((indexPage!=null) && (indexPage.compareTo("null")!=0)) {
      if (FxUtils.objectToInt(indexPage)!= 1){
        index = FxUtils.objectToInt(indexPage);
      }
    }

    // j'affiche les lignes comprises entre index*nbrLigne et index*nbrLigne+nbrLigne
    if (index >1) {
      ligneAAfficher=(index-1)*nbrLigne;
    } else {
      ligneAAfficher=0;
    }

    // Creation des lignes du tableau
    for(int i=(ligneAAfficher);i<(ligneAAfficher+nbrLigne);i++) {
      if (row.size()>i) {
        res.append( jsp_AddRow(head,(Vector)row.elementAt(i),parameter,(coul)&&(couleur), -1, pColonneIds, pListeColonneIds) );
        coul = !coul;
      } // end if row.size>i
    }

    // Creation index du tableau
    res.append( "<TR><TD colspan=\"" + head.size() + "\">" );
    res.append( jsp_CreateIndex(row, nbrLigne, nbrPage, index,(head.size()),strChemin) );
    res.append( "</TD></TR></table>" );

    return res.toString();
  } // End


  /**
   * Cree le tableau avec rupture
   * @param head   Vecteur contenant l'entete d'une colonne du tableau.
   * @param row    Vecteur contenant les valeurs des lignes
   * @param border  Chaine de caractere representant la bordure du tableau
   * @param align      Proprété align du tableau
   * @param nbrPage  Entier qui represente le nombre de page/index
   * @param nbrLigne  Entier qui represente le nombre de lignes/page
   * @param indexPage  Entier qui represente la page a afficher
   * @param couleur Boolean qui represente si le tableau doit etre bicolor (true)
   * @param parameter  Parametre optionnel du tableau
   * @param strChemin  Chemin cible du lien de l'index
   * @return chaine de caracteres contenant le code html definissant le tableau.
   */
  public String jsp_CreateTableRupture(Vector head,Vector row,String border, String align, int nbrPage,int nbrLigne,String indexPage,boolean couleur,String parameter, int colonneRupture, Vector colonneSomme,String strChemin) {
    return jsp_CreateTableRupture(head,row,border,align,"1","2",nbrPage,nbrLigne,indexPage,couleur,parameter,colonneRupture,colonneSomme,strChemin);
  }


  /**
   *  Cree le tableau avec rupture
   *
   *@param  head            Vecteur contenant l'entete d'une colonne du
   *      tableau.
   *@param  row             Vecteur contenant les valeurs des lignes
   *@param  border          Chaine de caractere representant la bordure du
   *      tableau
   *@param  align           Proprété align du tableau
   *@param  cellpadding     Proprété cellpadding du tableau
   *@param  cellspacing     Proprété cellspacing du tableau
   *@param  nbrPage         Entier qui represente le nombre de page/index
   *@param  nbrLigne        Entier qui represente le nombre de lignes/page
   *@param  indexPage       Entier qui represente la page a afficher
   *@param  couleur         Boolean qui represente si le tableau doit etre
   *      bicolor (true)
   *@param  parameter       Parametre optionnel du tableau
   *@param  strChemin       Chemin cible du lien de l'index
   *@param  colonneRupture  Description of the Parameter
   *@param  colonneSomme    Description of the Parameter
   *@return                 chaine de caracteres contenant le code html
   *      definissant le tableau.
   */
  public String jsp_CreateTableRupture(Vector head, Vector row, String border, String align, String cellpadding, String cellspacing, int nbrPage, int nbrLigne, String indexPage, boolean couleur, String parameter, int colonneRupture, Vector colonneSomme, String strChemin) {
    StringBuffer res = new StringBuffer();

    boolean coul = false;
    String valeurRupture = "";
    boolean afficherTotal = false;
    Vector vSomme = new Vector();
    StringBuffer strLienActionDebut;
    StringBuffer strLienActionFin;
    String strLienAction;

    // permet de verifier si l'option couleur est activee
    if (couleur == true) {
      coul = true;
    }
    res.append("<table cellpadding=\"" + cellpadding + "\" cellspacing=\"" + cellspacing + "\" align=\"" + align + "\" border=\"" + border + "\" >");

    // on cree une ligne du tableau selon les specificite passe en parametre dans l'entete (head) avec les valeurs
    // contenues dans le vecteur de vecteur row ( rappel : chaque ligne de row contient un vecteur regroupant
    // les valeurs de toutes les colonnes).

    // Test de la page a afficher
    int index = 1;
    int ligneAAfficher = 0;
    if ((indexPage != null) && (indexPage.compareTo("null") != 0)) {
      if (FxUtils.objectToInt(indexPage) != 1) {
        index = FxUtils.objectToInt(indexPage);
      }
    }

    // j'affiche les lignes comprises entre index*nbrLigne et index*nbrLigne+nbrLigne
    if (index > 1) {
      ligneAAfficher = (index - 1) * nbrLigne;
    } else {
      ligneAAfficher = 0;
    }

    // S'l faut afficher au moins une somme
    if (colonneSomme.size() > 0) {
      if (row.size() > ligneAAfficher) {
        for (int k = 0; k < head.size(); k++) {
          vSomme.addElement(null);
        }
        for (int k = 0; k < colonneSomme.size(); k++) {
          vSomme.setElementAt("0.0", Integer.parseInt(colonneSomme.elementAt(k).toString()));
        }
      }
    }

    // Si pas de rupture
    if (colonneRupture < 0) {
      afficherTotal = (colonneSomme.size() > 0);
      // Creation de l'entete du tableau
      res.append(jsp_CreateHeadTable(head, colonneRupture));
    }

    // Creation des lignes du tableau
    for (int i = (ligneAAfficher); i < (ligneAAfficher + nbrLigne); i++) {
      if (row.size() > i) {

        // Si rupture demandée
        if (colonneRupture >= 0) {

          // je regarde s'il y a une RUPTURE
          Vector headBox = (Vector) head.elementAt(colonneRupture);
          int pos = ((Integer) headBox.elementAt(INDICE_POSITION)).intValue();
          // position de la colonne dans les data.
          if (!valeurRupture.equals(((Vector) row.elementAt(i)).elementAt(pos).toString())) {

            // Sauf le première fois.
            if (afficherTotal) {
              res.append(jsp_AddTotaux(head, vSomme, colonneRupture));
            }
            afficherTotal = (colonneSomme.size() > 0);

            strLienActionDebut = new StringBuffer();
            strLienActionFin = new StringBuffer();
            if (headBox.elementAt(INDICE_TYPE).toString().equals("lien")) {
              strLienAction = jsp_GetTarget((Vector) headBox.elementAt(INDICE_PARAMETRES_SPEC), ((Vector) row.elementAt(i)), row, parameter);
              if (!strLienAction.equals("") && strLienAction != null) {
                strLienActionDebut.append("<A href=\"");
                strLienActionDebut.append(strLienAction);
                strLienActionDebut.append("\">");
                strLienActionFin.append("</A>");
              }
            }

            // Creation entête superieur du tableau principal
            res.append("<TR><TD class=\"jspTableauEntete\" colspan=\"" + head.size() + "\">");
            res.append(strLienActionDebut);
            res.append(((Vector) head.elementAt(colonneRupture)).elementAt(0).toString() + "&nbsp;:&nbsp;" + ((Vector) row.elementAt(i)).elementAt(0).toString());
            res.append(strLienActionFin);
            res.append("</TD></TR>");

            // Creation de l'entete du tableau secondaire
            res.append(jsp_CreateHeadTable(head, colonneRupture));

            // Mise à jour de la variable
            valeurRupture = ((Vector) row.elementAt(i)).elementAt(pos).toString();
          }
        }

        // J'affiche la ligne du tableau
        res.append(jsp_AddRow(head, (Vector) row.elementAt(i), parameter, (coul) && (couleur), colonneRupture, -1, null));

        // Je mets à jour les sommes des colonnes à sommer.
        if (colonneSomme.size() > 0) {
          for (int k = 0; k < colonneSomme.size(); k++) {
            Vector vec = (Vector) row.elementAt(i);
            Vector headBox = (Vector) head.elementAt(Integer.parseInt(colonneSomme.elementAt(k).toString()));
            int pos = ((Integer) headBox.elementAt(INDICE_POSITION)).intValue();

            double d = Double.parseDouble(vec.elementAt(pos).toString());
            double d1 = Double.parseDouble(vSomme.elementAt(Integer.parseInt(colonneSomme.elementAt(k).toString())).toString());
            vSomme.setElementAt(String.valueOf(d + d1), Integer.parseInt(colonneSomme.elementAt(k).toString()));
          }
        }

        coul = !coul;
      }
      // end if row.size>i
    }

    if (afficherTotal) {
      res.append(jsp_AddTotaux(head, vSomme, colonneRupture));
    }

    // Creation index du tableau
    res.append("<TR><TD colspan=\"" + head.size() + "\">");
    res.append(jsp_CreateIndex(row, nbrLigne, nbrPage, index, (head.size()), strChemin));
    res.append("</TD></TR></table>");

    return res.toString();
  }// End methode

  /**
   * Cree le tableau
   * @param head       Vecteur contenant l'entete d'une colonne du tableau.
   * @param row        Vecteur contenant les valeurs des lignes
   * @param border     Chaine de caractere representant la bordure du tableau
   * @param nbrPage    Entier qui represente le nombre de page/index
   * @param nbrLigne   Entier qui represente le nombre de lignes/page
   * @param indexPage  Entier qui represente la page a afficher
   * @param couleur    Boolean qui represente si le tableau doit etre bicolor (true)
   * @param parameter  Parametre optionnel du tableau
   * @param align      Proprété align du tableau
   * @param cellpadding  Proprété cellpadding du tableau
   * @param cellspacing  Proprété cellspacing du tableau
   * @param strChemin  Chemin cible du lien de l'index
   * @return chaine de caracteres contenant le code html definissant le tableau.
   */
  public String jsp_CreateTable(Vector head, Vector row, String border, int nbrPage, int nbrLigne, String indexPage, boolean couleur, String parameter, String align, String cellpadding, String cellspacing,String strChemin) {
    StringBuffer res = new StringBuffer();
    String color = "";
    boolean coul = false;

    // permet de verifier si l'option couleur est activee
    if (couleur==true) {
      coul = true;
    }
    res.append( "<table cellpadding=\"" + cellpadding + "\" cellspacing=\"" + cellspacing + "\" align=\"" + align + "\" border=\"" + border + "\" >" );

    // Creation de l'entete du tableau
    res.append( jsp_CreateHeadTable(head) );

    // on cree une ligne du tableau selon les specificite passe en parametre dans l'entete (head) avec les valeurs
    // contenues dans le vecteur de vecteur row ( rappel : chaque ligne de row contient un vecteur regroupant
    // les valeurs de toutes les colonnes).

    // Test de la page a afficher
    int index = 1;
    int ligneAAfficher=0;
    if ((indexPage!=null) && (indexPage.compareTo("null")!=0)) {
      if (FxUtils.objectToInt(indexPage)!= 1){
        index = FxUtils.objectToInt(indexPage);
      }
    }

    // j'affiche les lignes comprises entre index*nbrLigne et index*nbrLigne+nbrLigne
    if (index >1) {
      ligneAAfficher=(index-1)*nbrLigne;
    } else {
      ligneAAfficher=0;
    }

    // Creation des lignes du tableau
    for(int i=(ligneAAfficher);i<(ligneAAfficher+nbrLigne);i++) {
      if (row.size()>i) {
        res.append( jsp_AddRow(head,(Vector)row.elementAt(i),parameter,(coul)&&(couleur)) );
        coul = !coul;
      } // end if row.size>i
    }

    // Creation index du tableau
    res.append( "<TR><TD colspan=\"" + head.size() + "\">" );
    res.append( jsp_CreateIndex(row, nbrLigne, nbrPage, index,(head.size()),strChemin) );
    res.append( "</TD></TR></table>" );

    return res.toString();
  }

  /**
   * Cree le tableau
   * @param head       Vecteur contenant l'entete d'une colonne du tableau.
   * @param row        Vecteur contenant les valeurs des lignes
   * @param border     Chaine de caractere representant la bordure du tableau
   * @param nbrPage    Entier qui represente le nombre de page/index
   * @param nbrLigne   Entier qui represente le nombre de lignes/page
   * @param indexPage  Entier qui represente la page a afficher
   * @param couleur    Boolean qui represente si le tableau doit etre bicolor (true)
   * @param parameter  Parametre optionnel du tableau
   * @param align      Proprété align du tableau
   * @param cellpadding  Proprété cellpadding du tableau
   * @param cellspacing  Proprété cellspacing du tableau
   * @return chaine de caracteres contenant le code html definissant le tableau.
   */
  public String jsp_CreateTable(Vector head, Vector row, String border, int nbrPage, int nbrLigne, String indexPage, boolean couleur, String parameter, String align, String cellpadding, String cellspacing) {
    return jsp_CreateTable(head, row, border, nbrPage, nbrLigne, indexPage, couleur, parameter, align, cellpadding, cellspacing,"");
  }

  /**
   * Cree le tableau
   * @param head       Vecteur contenant l'entete d'une colonne du tableau.
   * @param row        Vecteur contenant les valeurs des lignes
   * @param border     Chaine de caractere representant la bordure du tableau
   * @param nbrPage    Entier qui represente le nombre de page/index
   * @param nbrLigne   Entier qui represente le nombre de lignes/page
   * @param indexPage  Entier qui represente la page a afficher
   * @param couleur    Boolean qui represente si le tableau doit etre bicolor (true)
   * @param parameter  Parametre optionnel du tableau
   * @return chaine de caracteres contenant le code html definissant le tableau.
   */
  public String jsp_CreateTable(Vector head, Vector row, String border, int nbrPage, int nbrLigne, String indexPage, boolean couleur, String parameter) {
    return jsp_CreateTable(head, row, border, nbrPage, nbrLigne, indexPage, couleur, parameter, "center", "1", "2","");
  }

  /**
   * Cree le tableau avec la cible du lien de l'index défini
   * @param head       Vecteur contenant l'entete d'une colonne du tableau.
   * @param row        Vecteur contenant les valeurs des lignes
   * @param border     Chaine de caractere representant la bordure du tableau
   * @param nbrPage    Entier qui represente le nombre de page/index
   * @param nbrLigne   Entier qui represente le nombre de lignes/page
   * @param indexPage  Entier qui represente la page a afficher
   * @param couleur    Boolean qui represente si le tableau doit etre bicolor (true)
   * @param parameter  Parametre optionnel du tableau
   * @param strChemin  Chemin cible du lien de l'index
   * @return chaine de caracteres contenant le code html definissant le tableau.
   */
  public String jsp_CreateTable(Vector head, Vector row, String border, String cellpadding, String cellspacing, int nbrPage, int nbrLigne, String indexPage, boolean couleur, String parameter,String strChemin) {
    return jsp_CreateTable(head, row, border, nbrPage, nbrLigne, indexPage, couleur, parameter, "center", cellpadding, cellspacing,strChemin);
  }

  /**
   * Cree le tableau
   * @param head       Vecteur contenant l'entete d'une colonne du tableau.
   * @param row        Vecteur contenant les valeurs des lignes
   * @param border     Chaine de caractere representant la bordure du tableau
   * @param nbrPage    Entier qui represente le nombre de page/index
   * @param nbrLigne   Entier qui represente le nombre de lignes/page
   * @param indexPage  Entier qui represente la page a afficher
   * @param couleur    Boolean qui represente si le tableau doit etre bicolor (true)
   * @param parameter  Parametre optionnel du tableau
   * @param align      Proprété align du tableau
   * @return chaine de caracteres contenant le code html definissant le tableau.
   */
  public String jsp_CreateTable(Vector head, Vector row, String border, int nbrPage, int nbrLigne, String indexPage, boolean couleur, String parameter, String align) {
    return jsp_CreateTable(head, row, border, nbrPage, nbrLigne, indexPage, couleur, parameter, align, "1", "2","");
  }

  /**
   * Cree l'entete du tableau
   * @param head  Vecteur contenant l'entete d'une colonne du tableau.
   * @return chaine de caracteres contenant le code html definissant l'entete du tableau.
   */
  public String jsp_CreateHeadTable(Vector head) {
    Jsp_Libelles lib = new Jsp_Libelles();
    StringBuffer res = new StringBuffer();
    StringBuffer strLienActionDebut;
    StringBuffer strLienActionFin;
    String strLienAction;

    res.append("<TR>");
    int pos = 0;
    for (int rang=0;rang<head.size();rang++) {
      pos=rang;
      strLienAction=((Vector)head.elementAt(pos)).elementAt(2).toString();
      strLienActionDebut=new StringBuffer();
      strLienActionFin=new StringBuffer();
      if(!strLienAction.equals("") && strLienAction!=null){
        strLienActionDebut.append("<A href=\"");
        strLienActionDebut.append(strLienAction);
        strLienActionDebut.append("\">");
        strLienActionFin.append("</A>");
      }
      if (pos>=0) {
        res.append("<TD ");
        res.append(jsp_GetParameters((Vector)head.elementAt(pos)));
        res.append(" class=\"jspTableauEntete\" >");
        res.append(strLienActionDebut);
        res.append(jsp_GetHeadName((Vector)head.elementAt(pos)));
        res.append(strLienActionFin);
        res.append("</TD>");
      }
    }
    res.append("</TR>");

    return res.toString();
  }


  /**
   * Cree l'entete du tableau
   * @param head   Vecteur contenant l'entete d'une colonne du tableau.
   * @param colonneRupture indice de la colonne de regroupement
   * @return chaine de caracteres contenant le code html definissant l'entete du tableau.
   */
  public String jsp_CreateHeadTable(Vector head, int colonneRupture) {
    Jsp_Libelles lib = new Jsp_Libelles();
    StringBuffer res = new StringBuffer();
    StringBuffer strLienActionDebut;
    StringBuffer strLienActionFin;
    String strLienAction;

    if (colonneRupture >= 0) {
      res.append("<TR><TD>&nbsp;</TD>");
    } else {
      res.append("<TR>");
    }

    int pos = 0;
    for (int rang=0;rang<head.size();rang++) {
      if (rang != colonneRupture) {
        pos=rang;
        strLienAction=((Vector)head.elementAt(pos)).elementAt(2).toString();
        strLienActionDebut=new StringBuffer();
        strLienActionFin=new StringBuffer();
        if(!strLienAction.equals("") && strLienAction!=null){
          strLienActionDebut.append("<A href=\"");
          strLienActionDebut.append(strLienAction);
          strLienActionDebut.append("\">");
          strLienActionFin.append("</A>");
        }
        if (pos>=0) {
          res.append("<TD ");
          res.append(jsp_GetParameters((Vector)head.elementAt(pos)));
          res.append(" class=\"jspTableauEntete\" >");
          res.append(strLienActionDebut);
          res.append(jsp_GetHeadName((Vector)head.elementAt(pos)));
          res.append(strLienActionFin);
          res.append("</TD>");
        }
      }
    }
    res.append("</TR>");

    return res.toString();
  }


  /**
   * Retourne la hauteur de l'entete
   * @param v Vecteur contenant l'entete d'une colonne du tableau
   * @return chaine de caracteres contenant le code html definissant la hauteur de la colonne.
   */
  private String jsp_GetHeadHeigth(Vector v) {
    StringBuffer res = new StringBuffer();

    res.append(v.elementAt(2).toString());
    if (!res.toString().equals("")) {
      res.append(" heigth=\"" + res + "\"");
    }
    return res.toString();
  }


  /**
   * Retourne les parametres de l'entete
   * @param v   Vecteur contenant l'entete d'une colonne du tableau.
   * @return chaine de caracteres contenant les parametres de la colonne.
   */
  protected String jsp_GetParameters(Vector v) {
    return v.elementAt(1).toString();
  }


  /**
   * Retourne la largeur de l'entete
   * @param  v  Vecteur contenant l'entete d'une colonne du tableau.
   * @return chaine de caracteres contenant le code html
   * definissant la largeur de la colonne.
   */
  private String jsp_GetHeadWidth(Vector v) {
    StringBuffer res = new StringBuffer();

    res.append( v.elementAt(1).toString() );
    if (!res.toString().equals("")) {
      res.append(" width=\"" + res + "\"");
    }

    return res.toString();
  }


  /**
   * Retourne le nom d'une entete
   * @param v  Vecteur contenant l'entete d'une colonne du tableau.
   * @return chaine de caracteres contenant le code html definissant le nom de la colonne.
   */
  protected String jsp_GetHeadName(Vector v) {
    return v.elementAt(0).toString();
  }


  /**
   *  Retourne le nom d'une entete
   *
   *@param  head       : Vecteur contenant les entetes d'une colonne du
   *      tableau.
   *@param  values     : Vecteur contenant les valeurs d'une ligne du tableau.
   *@param  value      : Chaine de caractere contenant la valeur de la cellule
   *@param  row        : Vecteur contenant les valeurs des lignes
   *@param  parameter  : Parametre optionnel du tableau
   *@return            chaine de caracteres contenant le code html definissant
   *      le type et les valeurs associees
   */
  public String jsp_GetHeadType(Vector head, Vector values, Object value, Vector row, String parameter) {
    StringBuffer res = new StringBuffer();

    String strType = head.elementAt(INDICE_TYPE).toString().trim();

    //** Modif FGA chez ressource le 04/10/2001
    //** Ne pas afficher null dans la colonne du tableau
    // pour les cellules de types Chaines (toutes sauf liens multiples)
    if (!strType.equals("liensmultiples")) {
      value = (value == null ? "&nbsp;" : value);
    } else {
      value = (value == null ? new Vector() : value);
    }

    // On cherche de quel type est la colonne : lien,champs, etc
    if (strType.equals("text")) {
      res.append(jsp_CreateTypeText(value.toString()));
    } else if (strType.equals("textdecode")) {
      String v = java.net.URLDecoder.decode(value.toString());
      res.append(jsp_CreateTypeText(v));
    } else {

      if (strType.equals("montant")) {
        res.append(jsp_CreateTypeMontant(value.toString()));
        if (head.size() > INDICE_PARAMETRES_SPEC) {
          res.append(" ").append(head.elementAt(INDICE_PARAMETRES_SPEC));
        }
      } else {
        if (strType.equals("lien")) {
          if (this.frameName == null) {
            res.append(jsp_CreateTypeLien(value.toString(), head, values, row, parameter));
          } else {
            res.append(jsp_CreateTypeLien(value.toString(), head, values, row, parameter, this.frameName));
          }
        } else {
          if (strType.equals("liensmultiples")) {
            // pour les liens multiples la valeur est un vecteur et no pas une String
            // risque d'erreur --> On catch les CastExeption
            try {
              if (this.frameName == null) {
                res.append(jsp_CreateTypeLiensMultiples((Vector) value, head, values, row, parameter));
              } else {
                res.append(jsp_CreateTypeLiensMultiples((Vector) value, head, values, row, parameter, this.frameName));
              }
            } catch (ClassCastException e) {
              System.err.println("Erreur : liens multiples");
              System.err.println(e);
            }
          } else if (strType.equals("lienPopUpImage")) {
            res.append( jsp_CreateTypeLienPopUpImage(value.toString(),head,values,row,parameter,head.elementAt(head.size()-4).toString(),head.elementAt(head.size()-3).toString(),head.elementAt(head.size()-2).toString(),head.elementAt(head.size()-1).toString() ) );
          } else if (strType.equals("lienPopUp")) {
            res.append( jsp_CreateTypeLienPopUp(value.toString(),head,values,row,parameter) );
          } else if (strType.equals("lienPopUpDecode")) {
            String v = java.net.URLDecoder.decode(value.toString());
            res.append( jsp_CreateTypeLienPopUp(v,head,values,row,parameter) );
          } else {
            if (strType.equals("image")) {
              res.append(jsp_CreateTypeImage(value.toString(), head, values, row, parameter,
                  head.elementAt(head.size() - 4).toString(),
                  head.elementAt(head.size() - 3).toString(),
                  head.elementAt(head.size() - 2).toString(),
                  head.elementAt(head.size() - 1).toString()));
            } else {
              if (strType.equals("entier")) {
                res.append(jsp_CreateTypeEntier(value.toString()));
                if (head.size() > INDICE_PARAMETRES_SPEC) {
                  res.append(" ").append(head.elementAt(INDICE_PARAMETRES_SPEC));
                }
              } else {
                if (strType.equals("date")) {
                  res.append(jsp_CreateTypeDate(value.toString()));
                } else {
                  if (strType.equals("datexls")) {
                    res.append(jsp_CreateTypeDateXls(value.toString()));
                  } else {
                    if (strType.equals("taux")) {
                      res.append(jsp_CreateTypeTaux(value.toString()));
                    } else {
                      if (strType.equals("input")) {
                        res.append(jsp_CreateTypeInput(value.toString(), head, values));
                      } else {
                        if (strType.equals("textarea")) {
                          res.append(jsp_CreateTypeTextArea(value.toString(), head, values));
                        } else {
                          if (strType.equals("checkbox")) {
                            res.append(jsp_CreateTypeCheckbox(value.toString(), head, values));
                          } else {
                            if (strType.equals("radio")) {
                              res.append(jsp_CreateTypeRadioBouton(value.toString(), head, values));
                            } else {
                              if (strType.equals("combobox")) {
                                res.append(jsp_CreateTypeCombobox(value.toString(), head, values));
                              } else {
                                res.append(jsp_CreateTypeDefault(value.toString()));
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    if (res.toString().equals("")) {
      res.append("&nbsp;");
    }
    return res.toString();
  }

  /**
   * Retourne le code htlm d'un type montant
   * @param v  Chaine de caracteres contenant la valeur d'une cellule du tableau.
   * @return chaine de caracteres contenant le code html definissant
   * un champs de type montant
   */
  protected String jsp_CreateTypeMontant(String v) {
    Jsp_Libelles lib = new Jsp_Libelles();
    return lib.jsp_Mnt(v);
  }

  /**
   * Retourne le code htlm d'un type entier
   * @param v  Chaine de caracteres contenant la valeur d'une cellule du tableau.
   * @return chaine de caracteres contenant le code html definissant un champs de type entier
   */
  protected String jsp_CreateTypeEntier(String v) {
    Jsp_Libelles lib = new Jsp_Libelles();
    return lib.jsp_Data(v);
  }

  /**
   * Retourne le code htlm d'un type date
   * @param v  Chaine de caracteres contenant la valeur d'une cellule du tableau.
   * @return chaine de caracteres contenant le code html definissant un champs de type entier
   */
  protected String jsp_CreateTypeDate(String v) {
    Jsp_Libelles lib = new Jsp_Libelles();
    return lib.jsp_Date(v);
  }

  /**
   * Retourne le code htlm d'un type date au format JJ/MM/AAAA
   * @param v  Chaine de caracteres contenant la valeur d'une date Excel en Nombre de jour.
   * @return chaine de caracteres contenant le code html definissant une date au format JJ/MM/AAAA
   */
  protected String jsp_CreateTypeDateXls(String v) {
    Jsp_Libelles lib = new Jsp_Libelles();
    /**
     * Une date Excel est un Long qui represente le nombre de jour depuis le 01/01/1900
     * Une date Java  est un Long qui represente le nombre de jour depuis le 01/01/1970
     * Formule: ( Nb_de_jour_en_parametre - Nb_de_jour_depuis_01/01/1970 ) * Nb_Millisecondes_par_jour
     */
    Date date = new Date( ( Long.parseLong(v) - 25569 ) * 86400000l );
    return lib.jsp_Date( formatDate.format(date) );
  }

  /**
   * Retourne le code htlm d'un type taux
   * @param v  Chaine de caracteres contenant la valeur d'une cellule du tableau.
   * @return chaine de caracteres contenant le code html definissant un champs de type entier
   */
  protected String jsp_CreateTypeTaux(String v) {
    Jsp_Libelles lib = new Jsp_Libelles();
    return lib.jsp_Taux(v);
  }

  /**
   * Retourne le code htlm d'un type texte
   * @param  v  Chaine de caracteres contenant la valeur d'une cellule du tableau.
   * @return chaine de caracteres contenant le code html definissant un champ de type text
   */
  protected String jsp_CreateTypeText(String v) {
    return v;
  }

  /**
   *  Retourne le code htlm d'un type lien
   *
   *@param  v          Chaine de caracteres contenant la valeur d'une cellule du tableau.
   *@param  head       Vecteur contenant les entetes d'une colonne du tableau.
   *@param  values     Vecteur contenant les valeurs d'une ligne du tableau.
   *@param  row        Vecteur contenant les valeurs des lignes
   *@param  parameter  Parametre optionnel du tableau
   *@return            chaine de caracteres contenant le code html definissant
   *      un champs de type lien
   */
  protected String jsp_CreateTypeLien(String v, Vector head, Vector values, Vector row, String parameter) {
    return jsp_CreateTypeLien(v, head, values, row, parameter, "_top");
  }


  /**
   *  Retourne le code htlm d'un type lien
   *
   *@param  v            Chaine de caracteres contenant la valeur d'une cellule du tableau.
   *@param  head         Vecteur contenant les entetes d'une colonne du tableau.
   *@param  values       Vecteur contenant les valeurs d'une ligne du tableau.
   *@param  row          Vecteur contenant les valeurs des lignes
   *@param  parameter    Parametre optionnel du tableau
   *@param  frameTarget  Description of the Parameter
   *@return              chaine de caracteres contenant le code html
   *      definissant un champs de type lien
   */
  protected String jsp_CreateTypeLien(String v, Vector head, Vector values, Vector row, String parameter, String frameTarget) {
    Jsp_Liens link = new Jsp_Liens();
    return link.jsp_Link(v, jsp_GetTarget((Vector) head.elementAt(INDICE_PARAMETRES_SPEC), values, row, parameter), "", frameTarget);
  }

  /**
   * Retourne le code htlm d'un type lien qui Ouvre une Fenêtre PopUp
   * @param v          Chaine de caracteres contenant la valeur d'une cellule du tableau.
   * @param head       Vecteur contenant les entetes d'une colonne du tableau.
   * @param values     Vecteur contenant les valeurs d'une ligne du tableau.
   * @param row        Vecteur contenant les valeurs des lignes
   * @param parameter  Parametre optionnel du tableau
   * @return chaine de caracteres contenant le code html definissant un champs de type lien PopUp
   * jsp_LinkPopup(String str1, String link, String style,String fnJavascriptPopup,String  namePopup, String widthPopup,String  heightPopup,String  paramPopup) {
   */
  //jsp_CreateTypeLienPopUpImage(value,head,values,row,parameter,head.elementAt(head.size()-4).toString(),head.elementAt(head.size()-3).toString(),head.elementAt(head.size()-2).toString(),head.elementAt(head.size()-1).toString()
  protected String jsp_CreateTypeLienPopUpImage(String v,Vector head,Vector values,Vector row,String parameter, String strImage, String width, String height, String libelle) {
    Jsp_Liens jsp_Liens = new Jsp_Liens();
  /* permet de specifier forcer l'infoBulle de l'image*/
    String code = "";
    if (infoBulle != null) {
      code = jsp_Liens.jsp_LinkIMGPopup(strImage, jsp_GetTarget((Vector)head.elementAt(5),values,row,parameter), infoBulle, width, height,"",
                                        this.fnJavascriptPopup, this.namePopup,this.widthPopup,this.heightPopup, this.paramPopup);
    }
    else {
      code = jsp_Liens.jsp_LinkIMGPopup(strImage, jsp_GetTarget((Vector)head.elementAt(5),values,row,parameter), v, width, height,"",this.fnJavascriptPopup, this.namePopup,this.widthPopup,this.heightPopup, this.paramPopup);
    }

    if (!libelle.equals("")) {
      code += "&nbsp;" + jsp_Liens.jsp_Link(libelle, jsp_GetTarget((Vector)head.elementAt(5),values,row,parameter));
    }

    return code;
  }

  protected String jsp_CreateTypeLienPopUp(String v,Vector head,Vector values,Vector row,String parameter) {
    Jsp_Liens link = new Jsp_Liens();
    if ((this.fnJavascriptPopup!= null) && ( this.namePopup!=null))
      return link.jsp_LinkPopup(v,jsp_GetTarget((Vector)head.elementAt(5),values,row,parameter),"", this.fnJavascriptPopup, this.namePopup,this.widthPopup,this.heightPopup, this.paramPopup);
    else
      return link.jsp_LinkPopup(v,jsp_GetTarget((Vector)head.elementAt(5),values,row,parameter),"");
  }

  /**
   *  Description de la méthode
   *
   *@param  v            Description of the Parameter
   *@param  head         Description of the Parameter
   *@param  values       Contient les valeur d'une ligne du tableau
   *@param  row          Description of the Parameter
   *@param  parameter    Description of the Parameter
   *@param  frameTarget  Description of the Parameter
   *@return              Description of the Return Value
   */
  protected String jsp_CreateTypeLiensMultiples(Vector v, Vector head, Vector values, Vector row, String parameter, String frameTarget) {
    StringBuffer resultat = new StringBuffer();

    System.err.println("jsp_CreateTypeLiensMultiples (2)");
    Jsp_Liens link = new Jsp_Liens();

    for (int i = 0; i < v.size(); i++) {
      if (i > 0) {
        resultat.append("<BR>");
      }
      resultat.append(link.jsp_Link(v.elementAt(i).toString(), jsp_GetTargetsMultiples((Vector) head.elementAt(INDICE_PARAMETRES_SPEC), values, row, parameter, i), "", frameTarget));
      System.err.println("jsp_CreateTypeLiensMultiples " + i + ":" + resultat.toString());
    }

    System.err.println("jsp_CreateTypeLiensMultiples " + resultat.toString());

    return resultat.toString();
  }


  /**
   *  Description de la méthode
   *
   *@param  v          Description of the Parameter
   *@param  head       Description of the Parameter
   *@param  values     Description of the Parameter
   *@param  row        Description of the Parameter
   *@param  parameter  Description of the Parameter
   *@return            Description of the Return Value
   */
  protected String jsp_CreateTypeLiensMultiples(Vector v, Vector head, Vector values, Vector row, String parameter) {
    System.err.println("jsp_CreateTypeLiensMultiples (1)");
    return jsp_CreateTypeLiensMultiples(v, head, values, row, parameter, "_top");
  }

  /**
   * Retourne le code htlm d'un type image
   * @param v Chaine de caracteres contenant la valeur d'une cellule du tableau.
   * @param head Vecteur contenant les entetes d'une colonne du tableau.
   * @param values Vecteur contenant les valeurs d'une ligne du tableau.
   * @param row Vecteur contenant les valeurs des lignes
   * @param parameter Parametre optionnel du tableau
   * @param strImage Nom de l'image
   * @param width  Largeur de l'image
   * @param heigth  Hauteur de l'image
   * @return chaine de caracteres contenant le code html definissant un champs de type image
   */
  protected String jsp_CreateTypeImage(String v,Vector head,Vector values,Vector row,String parameter, String strImage, String width, String height, String libelle) {
    Jsp_Liens jsp_Liens = new Jsp_Liens();
  /* permet de specifier forcer l'infoBulle de l'image*/
    String code = "";
    if (infoBulle != null) {
      code = jsp_Liens.jsp_LinkIMG(strImage, jsp_GetTarget((Vector)head.elementAt(5),values,row,parameter), infoBulle, width, height);
    }
    else {
      code = jsp_Liens.jsp_LinkIMG(strImage, jsp_GetTarget((Vector)head.elementAt(5),values,row,parameter), v, width, height);
    }

    if (!libelle.equals("")) {
      code += "&nbsp;" + jsp_Liens.jsp_Link(libelle, jsp_GetTarget((Vector)head.elementAt(5),values,row,parameter));
    }

    return code;
  }


  /**
   *  AJOUT MR Retourne le code htlm d'un type input
   *
   *@param  strValue  Valeur de la cellule que l'on desire afficher
   *@param  head      Vecteur contenant les entetes d'une colonne du tableau.
   *@param  values    Vecteur contenant les valeurs d'une ligne du tableau.
   *@return           chaine de caracteres contenant le code html definissant
   *      un champs de type input
   */
  protected String jsp_CreateTypeInput(String strValue, Vector head, Vector values) {
    Jsp_Formulaires form = new Jsp_Formulaires();
    String code;
    int numSize;
    int numMaxLength;
    String strOptions;
    String strType = ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(1).toString();
    int numCol = ((Integer) ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(2)).intValue();
    String strName = ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(0) + this.FLD_SEPARATEUR + values.elementAt(numCol);
    if (strType.equals("text")) {
      numSize = Integer.parseInt(((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(3).toString());
      numMaxLength = Integer.parseInt(((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(4).toString());
      strOptions = ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(5).toString();
      code = form.jsp_Ct(strName, strValue, numSize, numMaxLength);
    } else {
      if (strType.equals("date")) {
        code = form.jsp_CtDate(strName, strValue);
      } else {
        if (strType.equals("email")) {
          code = form.jsp_CtEmail(strName, strValue);
        } else {
          if (strType.equals("int")) {
            numSize = Integer.parseInt(((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(3).toString());
            numMaxLength = Integer.parseInt(((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(4).toString());
            code = form.jsp_CtInt(strName, strValue, numSize, numMaxLength);
          } else {
            if (strType.equals("intfixed")) {
              code = form.jsp_CtInt(strName, strValue);
            } else {
              if (strType.equals("mnt")) {
                code = form.jsp_CtMnt(strName, strValue);
              } else {
                if (strType.equals("mntlimited")) {
                  numSize = Integer.parseInt(((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(3).toString());
                  numMaxLength = Integer.parseInt(((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(4).toString());
                  code = form.jsp_CtMntLimited(strName, strValue, numSize, numMaxLength);
                } else {
                  if (strType.equals("mntsmall")) {
                    code = form.jsp_CtMntSmall(strName, strValue);
                  } else {
                    if (strType.equals("periode")) {
                      code = form.jsp_CtPeriode(strName, strValue);
                    } else {
                      if (strType.equals("psw")) {
                        numSize = Integer.parseInt(((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(3).toString());
                        numMaxLength = Integer.parseInt(((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(4).toString());
                        code = form.jsp_CtPsw(strName, strValue, numSize, numMaxLength);
                      } else {
                        if (strType.equals("taux")) {
                          code = form.jsp_CtTaux(strName, strValue);
                        } else {
                          code = form.jsp_Ct(strName, strValue, 10, 10);
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    return code + "\n";
  }


  /**
   *  AJOUT MR Retourne le code htlm d'un type textarea
   *
   *@param  strValue  Valeur de la cellule que l'on desire afficher
   *@param  head      Vecteur contenant les entetes d'une colonne du tableau.
   *@param  values    Vecteur contenant les valeurs d'une ligne du tableau.
   *@return           chaine de caracteres contenant le code html definissant
   *      un champs de type textarea
   */
  protected String jsp_CreateTypeTextArea(String strValue, Vector head, Vector values) {
    Jsp_Formulaires form = new Jsp_Formulaires();
    String code;
    int numCol = ((Integer) ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(1)).intValue();
    String strName = ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(0) + this.FLD_SEPARATEUR + values.elementAt(numCol);
    int numCols = ((Integer) ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(2)).intValue();
    int numRows = ((Integer) ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(3)).intValue();

    code = form.jsp_CtArea(strName, strValue, numCols, numRows);

    return code + "\n";
  }


  /**
   *  Retourne le code htlm d'un type checkbox
   *
   *@param  strValue  Valeur de la cellule que l'on desire afficher, comme
   *      c'est une checkbox la chaine de cractère n'est vrai que si elle est
   *      egale à "true"
   *@param  head      Vecteur contenant les entetes d'une colonne du tableau.
   *@param  values    Vecteur contenant les valeurs d'une ligne du tableau.
   *@return           chaine de caracteres contenant le code html definissant
   *      un champs de type checkbox
   */
  protected String jsp_CreateTypeCheckbox(String strValue, Vector head, Vector values) {
    Jsp_Formulaires form = new Jsp_Formulaires();
    String code;
    String strTypeCB = ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(3).toString();
    if (strTypeCB.equals("modif")) {
      int numCol = ((Integer) ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(1)).intValue();
      String strName;
      if (numCol == Integer.MAX_VALUE) {
        strName = ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(0).toString();
      } else {
        strName = ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(0) + "_" + values.elementAt(numCol);
      }
      if (!(((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(2).toString()).equals("")) {
        int numVal = Integer.parseInt(((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(2).toString());
        code = form.jsp_CB(strName, "", values.elementAt(numVal).toString(), Boolean.valueOf(strValue).booleanValue());
      } else {
        code = form.jsp_CB(strName, "", Boolean.valueOf(strValue).booleanValue());
      }
    } else {
      Jsp_Libelles lib = (Jsp_Libelles) ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(0);
      code = lib.jsp_LibCB("", ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(2).toString(), strValue);
    }

    return code + "\n";
  }


  /**
   *  AJOUT MR Retourne le code htlm d'un type radiobouton
   *
   *@param  strValue  Valeur de la cellule que l'on desire afficher
   *@param  head      Vecteur contenant les entetes d'une colonne du tableau.
   *@param  values    Vecteur contenant les valeurs d'une ligne du tableau.
   *@return           chaine de caracteres contenant le code html definissant
   *      un champs de type radiobouton
   */
  protected String jsp_CreateTypeRadioBouton(String strValue, Vector head, Vector values) {
    Jsp_Formulaires form = new Jsp_Formulaires();
    String code;

    String strName = ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(0).toString();
    int numVal = ((Integer) ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(1)).intValue();
    String options = ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(2).toString();
    boolean blnSelected = strValue.equals(values.elementAt(numVal));
    code = form.jsp_RB(strName, "", values.elementAt(numVal).toString() + "\" " + options, blnSelected);

    return code + "\n";
  }


  /**
   *  AJOUT MR Retourne le code htlm d'un type combobox
   *
   *@param  strValue  Valeur de la cellule que l'on desire afficher
   *@param  head      Vecteur contenant les entetes d'une colonne du tableau.
   *@param  values    Vecteur contenant les valeurs d'une ligne du tableau.
   *@return           chaine de caracteres contenant le code html definissant
   *      un champs de type combobox
   */
  protected String jsp_CreateTypeCombobox(String strValue, Vector head, Vector values) {
    Jsp_Formulaires form = new Jsp_Formulaires();
    String code;
    int numCol = ((Integer) ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(1)).intValue();
    String strName = ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(0) + this.FLD_SEPARATEUR + values.elementAt(numCol);
    Vector vcrData = (Vector) ((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(2);

    if (((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).size() <= 3) {
      //la première ligne ne doit pas être vide
      code = form.jsp_Combobox(strName, vcrData, strValue);
    } else {
      //la première ligne doit être vide
      code = form.jsp_Combobox(((Vector) head.elementAt(INDICE_PARAMETRES_SPEC)).elementAt(3).toString(), strName, vcrData, strValue);
    }
    return code + "\n";
  }


  /**
   *  Retourne le code htlm du type par défaut
   *
   *@param  v  Chaine de caracteres contenant la valeur d'une cellule du
   *      tableau.
   *@return    chaine de caracteres contenant le code html definissant un
   *      champs de type image
   */
  protected String jsp_CreateTypeDefault(String v) {
    return v;
  }


  /**
   *  Permet d'initialiser la page cible d'une image
   *
   *@param  head       Vecteur contenant l'entete du tableau
   *@param  strImage   Chaine de caractere contenant le nom de l'image
   *@param  width      largeur de l'image
   *@param  height     hauteur de l'image
   *@param  libelle    indiquer un lien avec l'image
   *@param  infoBulle  texte de l'info bulle
   *@return            Vecteur contenant les informations sur l'image
   */
  public Vector jsp_SetImage(Vector head, String strImage, int width, int height, String libelle, String infoBulle) {
    return this.jsp_HTML_SetImage(head, strImage, width, height, libelle, infoBulle);
  }


  /**
   *  Permet d'initialiser la page cible d'une image
   *
   *@param  head      Vecteur contenant l'entete du tableau
   *@param  strImage  Chaine de caractere contenant le nom de l'image
   *@param  width     largeur de l'image
   *@param  height    hauteur de l'image
   *@param  libelle   indiquer un lien avec l'image
   *@return           Vecteur contenant les informations sur l'image
   */
  public Vector jsp_SetImage(Vector head, String strImage, int width, int height, String libelle) {
    return this.jsp_HTML_SetImage(head, strImage, width, height, libelle, "");
  }


  /**
   *  Permet d'initialiser la page cible d'une image
   *
   *@param  head      Vecteur contenant l'entete du tableau
   *@param  strImage  Chaine de caractere contenant le nom de l'image
   *@param  width     largeur de l'image
   *@param  height    hauteur de l'image
   *@return           Vecteur contenant les informations sur l'image
   */
  public Vector jsp_SetImage(Vector head, String strImage, int width, int height) {
    return this.jsp_HTML_SetImage(head, strImage, width, height, "", "");
  }


  /**
   *  Modele qui Permet d'initialiser la page cible d'une image
   *
   *@param  head       Vecteur contenant l'entete du tableau
   *@param  strImage   Chaine de caractere contenant le nom de l'image
   *@param  width      largeur de l'image
   *@param  height     hauteur de l'image
   *@param  libelle    indiquer un lien avec l'image
   *@param  infoBulle  texte de l'info bulle
   *@return            Vecteur contenant les informations sur l'image
   */
  private Vector jsp_HTML_SetImage(Vector head, String strImage, int width, int height, String libelle, String infoBulle) {
    head.addElement(strImage);
    head.addElement(String.valueOf(width));
    head.addElement(String.valueOf(height));
    head.addElement(libelle);
    this.setInfoBulle(infoBulle);
    //head.addElement("infoBulle");

    return head;
  }


  /**
   * Initialiser la page cible d'un lien ainsi que les valeurs
   * des colonnes qui lui seront passees en parametre.
   * @param head Vecteur contenant l'entete du tableau.
   * @param page Chaine de caractere contenat le numeor de la page a cible.
   * @param cibles Tableau d'entier contenant les colonnes cibles du tableau.
   * @param nomCibles Tableau de chaine de caractere contenant le nom des cibles.
   * @return Vecteur contenant les cible du liens
   */
  public Vector jsp_SetTarget(Vector head,String page,int[] cibles,String[] nomCibles) {
    return this.jsp_SetTarget(head,page,cibles,nomCibles,null);
  }

  /**
   * Initialiser la page cible d'un lien ainsi que les valeurs
   * des colonnes qui lui seront passees en parametre.
   * @param head Vecteur contenant l'entete du tableau.
   * @param page Chaine de caractere contenat le numeor de la page a cible.
   * @param cibles Tableau d'entier contenant les colonnes cibles du tableau.
   * @param nomCibles Tableau de chaine de caractere contenant le nom des cibles.
   * @frameTarget la frame ciblée par le lien
   * @return Vecteur contenant les cible du liens
   */
  public Vector jsp_SetTarget(Vector head,String page,int[] cibles,String[] nomCibles, String frameTarget) {
    this.frameName = frameTarget;
    Vector res = new Vector();
    res.addElement(page);

    if (cibles.length!=0) {
      for(int i=0;i<cibles.length;i++) {
        res.addElement(FxUtils.intToInteger(cibles[i]));
        res.addElement(nomCibles[i]);
      }
    }

    head.addElement(res);
    res = head;

    return res;
  }

  /**
   * Initialiser la page cible d'un lien popup ainsi que les valeurs
   * des colonnes qui lui seront passees en parametre.
   * @param head Vecteur contenant l'entete du tableau.
   * @param page Chaine de caractere contenat le numeor de la page a cible.
   * @param cibles Tableau d'entier contenant les colonnes cibles du tableau.
   * @param nomCibles Tableau de chaine de caractere contenant le nom des cibles.
   * @frameTarget la frame ciblée par le lien
   * @fnJavascriptPopup : nom de la fn javascript
   * @widthPopup =width de la popup ;
   * @heightPopup = height ;
   * @namePopup = nom;
   * @paramPopup = parametres;
   * @return Vecteur contenant les cible du liens
   */
  public Vector jsp_SetTarget(Vector head,String page,int[] cibles, String[] nomCibles, String frameTarget, String fnJavascriptPopup, String widthPopup,String  heightPopup,String  namePopup,String  paramPopup) {

    this.frameName = frameTarget;
    this.fnJavascriptPopup = fnJavascriptPopup;
    this.widthPopup = widthPopup;
    this.heightPopup = heightPopup;
    this.namePopup = namePopup;
    this.paramPopup = paramPopup;

    Vector res = new Vector();
    res.addElement(page);

    if (cibles.length!=0) {
      for(int i=0;i<cibles.length;i++) {
        res.addElement(FxUtils.intToInteger(cibles[i]));
        res.addElement(nomCibles[i]);
      }
    }

    head.addElement(res);
    res = head;

    return res;
  }

  /**
   * Recupere la page cible d un lien ainsi que les valeurs
   * des colonnes qui lui seront passees en parametre.
   * @param cibles Vecteur contenant les cibles dans le tableau
   * @param values Vecteur contenant les valeurs d'une ligne du tableau.
   * @param row Vecteur contenant les valeurs des lignes
   * @param parameter les paramètres
   * @return Chaine de caracteres contenant les cibles du liens preformate pour l'URL.
   */
  public String jsp_GetTarget(Vector cibles,Vector values,Vector row,String parameter) {
    StringBuffer res = new StringBuffer();

    // recupere le nom de la page cible
    if (parameter.length()>1) {
      String nom = cibles.elementAt(0).toString();
      if (nom.indexOf("?")>=0) {
        res.append( cibles.elementAt(0).toString() + "&" + jsp_FormatParameter(parameter) );
      } else {
        res.append( cibles.elementAt(0).toString() + "?" + jsp_FormatParameter(parameter) );
      }
      if (cibles.size()!=0) {
        res.append( "&" );
      }
    } else{
      String nom=cibles.elementAt(0).toString();
      if (nom.indexOf("?")>=0) {
        res.append( cibles.elementAt(0).toString() );
        res.append( "&" );
      } else {
        res.append( cibles.elementAt(0).toString() );
        res.append( "?" );
      }
    }// end else

    if (cibles.size()!=0) {
      for (int i=1;i<cibles.size();i=i+2) {
        // on recupere le nom
        res.append( cibles.elementAt(i+1).toString() );
        // on recupere la valeur
        int j= FxUtils.objectToInt(cibles.elementAt(i));
        res.append( "=" );
        res.append( java.net.URLEncoder.encode( values.elementAt(j).toString() ) ) ;
        if ( (i+1) < (cibles.size()-1)) {
          res.append( "&" );
        } // end if
      } // end for
    }// end if

    return res.toString();
  }


  /**
   *  Description de la méthode
   *
   *@param  cibles     Vecteur contenant les cibles dans le tableau
   *@param  values     Vecteur contenant les valeurs d'une ligne du tableau.
   *@param  row        Vecteur contenant les valeurs des lignes
   *@param  parameter  les paramètres
   *@param  num        numéero du multi lien
   *@return            Description of the Return Value
   */
  public String jsp_GetTargetsMultiples(Vector cibles, Vector values, Vector row, String parameter, int num) {

    System.err.println("jsp_GetTargetsMultiples");

    StringBuffer res = new StringBuffer();

    System.err.println("jsp_GetTargetsMultiples : recupere le nom de la page cible");
    // recupere le nom de la page cible
    if (parameter.length() > 1) {
      String nom = cibles.elementAt(0).toString();
      if (nom.indexOf("?") >= 0) {
        res.append(cibles.elementAt(0).toString() + "&" + jsp_FormatParameter(parameter));
      } else {
        res.append(cibles.elementAt(0).toString() + "?" + jsp_FormatParameter(parameter));
      }
      if (cibles.size() != 0) {
        res.append("&");
      }
    } else {
      String nom = cibles.elementAt(0).toString();
      if (nom.indexOf("?") >= 0) {
        res.append(cibles.elementAt(0).toString());
        res.append("&");
      } else {
        res.append(cibles.elementAt(0).toString());
        res.append("?");
      }
    }
    // end else

    System.err.println("jsp_GetTargetsMultiples : recupere les parametres");

    if (cibles.size() != 0) {
      for (int i = 1; i < cibles.size(); i = i + 2) {
        // on recupere le nom
        res.append(cibles.elementAt(i + 1).toString());
        // on recupere la valeur
        int j = FxUtils.objectToInt(cibles.elementAt(i));
        System.err.println("jsp_GetTargetsMultiples : recupere les parametres / num = " + num + " i = " + i + " j = " + j);
        res.append("=");
        res.append(java.net.URLEncoder.encode(((Vector) values.elementAt(j)).elementAt(num).toString()));
        if ((i + 1) < (cibles.size() - 1)) {
          res.append("&");
        }
        // end if
      }
      // end for
    }
    // end if
    System.err.println("jsp_GetTargetsMultiples : fin");
    return res.toString();
  }


  /**
   * Initialiser les informations d'une colonne de type input
   * @param head Vecteur contenant l'entete de base du tableau.
   * @param strName nom général de l'input.
   * @param numCol numero de la colonne du tableau qui contient l'element que
   *              l'on concatene avec le nom de l'input afin de pouvoir "faire le tri"
   *              au niveau du serveur lors d'une modification.
   * @param type Type de l'input(text,date,montant,...), ceci a une importance pour les contrôles
   *              Javascript ajoutés.
   * @param size taille de l'input pour toutes les lignes du tableau
   * @param maxlength longeur maximum de la chaine accéptée dans le champ de saisi
   * @param options chaine de caractères des options que l'on ajouter à chaque champ text
   * @return Vecteur contenant les informations relatives à la colonne définie.
   */
  public Vector jsp_SetInput(Vector head,String strName,String strType,int numCol, String strSize,String strMaxlength,String strOptions) {
    Vector res = new Vector();
    res.addElement(strName);
    res.addElement(strType);
    res.addElement(FxUtils.intToInteger(numCol));
    res.addElement(strSize);
    res.addElement(strMaxlength);
    res.addElement(strOptions);
    head.addElement(res);
    res = head;

    return res;
  }

  /** AJOUT MR
   * Initialiser les informations d'une colonne de type textarea
   * @param head Vecteur contenant l'entete de base du tableau.
   * @param strName nom général de l'input.
   * @param numCol numero de la colonne du tableau qui contient l'element que
   *              l'on concatene avec le nom de l'input afin de pouvoir "faire le tri"
   *              au niveau du serveur lors d'une modification.
   * @param numCols nombre de colonnes de la zone de texte
   * @param numRows nombre de lignes de la zone de texte
   * @return Vecteur contenant les informations relatives à la colonne définie.
   */
  public Vector jsp_SetTextArea(Vector head,String strName,int numCol, int numCols, int numRows) {
    Vector res = new Vector();
    res.addElement(strName);
    res.addElement(FxUtils.intToInteger(numCol));
    res.addElement(FxUtils.intToInteger(numCols));
    res.addElement(FxUtils.intToInteger(numRows));
    head.addElement(res);
    res = head;

    return res;
  }


  /**
   *  AJOUT PG Initialiser les informations d'une colonne de type montant (ou
   *  entier)
   *
   *@param  head       Vecteur contenant l'entete de base du tableau.
   *@param  strDevise  symbole de la devise.
   *@return            Description of the Return Value
   */
  public Vector jsp_SetUnite(Vector head, String strDevise) {
    Vector res = new Vector();
    head.addElement(strDevise);
    res = head;

    return res;
  }


  /**
   * Initialiser les informations d'une colonne de type checkbox, ces colonnes sont
   * de type modifiable.
   * @param head Vecteur contenant l'entete de base du tableau.
   * @param strName nom général de l'input.
   * @param numCol numero de la colonne du tableau qui contient l'element que
   *              l'on concatene avec le nom de l'input afin de pouvoir "faire le tri"
   *              au niveau du serveur lors d'une modification.
   * @param numVal numero de la colonne qui contient la valeur de chaque checkbox
   * @return Vecteur contenant les informations relatives à la colonne définie.
   */
  public Vector jsp_SetCheckBox(Vector head,String strName,int numCol, int numVal) {
    Vector res = new Vector();
    res.addElement(strName);
    res.addElement(FxUtils.intToInteger(numCol));
    res.addElement(Integer.toString(numVal));
    res.addElement("modif");
    head.addElement(res);
    res = head;

    return res;
  }

  /**
   * Initialiser les informations d'une colonne de type checkbox, ces colonnes sont
   * de type modifiable.
   * @param head Vecteur contenant l'entete de base du tableau.
   * @param strName nom général de l'input.
   * @param numCol numero de la colonne du tableau qui contient l'element que
   *              l'on concatene avec le nom de l'input afin de pouvoir "faire le tri"
   *              au niveau du serveur lors d'une modification.
   * @param numVal numero de la colonne qui contient la valeur de chaque checkbox
   * @return Vecteur contenant les informations relatives à la colonne définie.
   */
  public Vector jsp_SetCheckBox(Vector head,String strName,int numCol) {
    Vector res = new Vector();
    res.addElement(strName);
    res.addElement(FxUtils.intToInteger(numCol));
    res.addElement("");
    res.addElement("modif");
    head.addElement(res);
    res = head;

    return res;
  }

  /**
   * Initialiser les informations d'une colonne de type checkbox, ces colonnes sont
   * de type modifiable.
   * @param head Vecteur contenant l'entete de base du tableau.
   * @param strName nom général de l'input (aucune concatenation ne sera faite).
   * @param numVal numero de la colonne qui contient la valeur de chaque checkbox
   * @return Vecteur contenant les informations relatives à la colonne définie.
   */
  public Vector jsp_SetCheckBox(Vector head, int numVal,String strName) {
    Vector res = new Vector();
    res.addElement(strName);
    res.addElement(FxUtils.intToInteger(Integer.MAX_VALUE));
    res.addElement(Integer.toString(numVal));
    res.addElement("modif");
    head.addElement(res);
    res = head;

    return res;
  }

  /**
   * Initialiser les informations d'une colonne de type checkbox, ces colonnes sont
   * de type statique.
   * @param head Vecteur contenant l'entete de base du tableau.
   * @return Vecteur contenant les informations relatives à la colonne définie.
   */
  public Vector jsp_SetCheckBox(Jsp_Libelles lib,Vector head, String valueTrue) {
    Vector res = new Vector();
    res.addElement(lib);
    res.addElement(FxUtils.intToInteger(0));
    res.addElement(valueTrue);
    res.addElement("static");
    head.addElement(res);
    res = head;

    return res;
  }

  /** AJOUT MR
   * Initialiser les informations d'une colonne de type radiobouton
   * @param head Vecteur contenant l'entete de base du tableau.
   * @param strName nom général de l'input.
   * @param numVal numero de la colonne qui contient la valeur de chaque radiobouton
   * @return Vecteur contenant les informations relatives à la colonne définie.
   */
  public Vector jsp_SetRadioBouton(Vector head,String strName,int numVal) {
    Vector res = new Vector();
    res.addElement(strName);
    res.addElement(FxUtils.intToInteger(numVal));
    res.addElement("\"");
    head.addElement(res);
    res = head;

    return res;
  }

  /** AJOUT MR
   * Initialiser les informations d'une colonne de type radiobouton
   * @param head Vecteur contenant l'entete de base du tableau.
   * @param strName nom général de l'input.
   * @param numVal numero de la colonne qui contient la valeur de chaque radiobouton
   * @return Vecteur contenant les informations relatives à la colonne définie.
   */
  public Vector jsp_SetRadioBouton(Vector head,String strName,int numVal, String strOptions) {
    Vector res = new Vector();
    res.addElement(strName);
    res.addElement(FxUtils.intToInteger(numVal));
    res.addElement(strOptions);
    head.addElement(res);
    res = head;

    return res;
  }

  /** AJOUT MR
   * Initialiser les informations d'une colonne de type combobox
   * @param head Vecteur contenant l'entete de base du tableau.
   * @param strName nom général de l'input.
   * @param numCol numero de la colonne du tableau qui contient l'element que
   *              l'on concatene avec le nom de l'input afin de pouvoir "faire le tri"
   *              au niveau du serveur lors d'une modification.
   * @param vcrData Vecteur de la liste des elements de la combobox
   * @return Vecteur contenant les informations relatives à la colonne définie.
   */
  public Vector jsp_SetCombobox(Vector head,String strName,int numCol,Vector vcrData) {
    Vector res = new Vector();
    res.addElement(strName);
    res.addElement(FxUtils.intToInteger(numCol));
    res.addElement(vcrData);
    head.addElement(res);
    res = head;

    return res;
  }

  /** AJOUT MR
   * Initialiser les informations d'une colonne de type combobox
   * @param head Vecteur contenant l'entete de base du tableau.
   * @param strLigneVide libelle de la ligne vide de la combobox.
   * @param strName nom général de l'input.
   * @param numCol numero de la colonne du tableau qui contient l'element que
   *              l'on concatene avec le nom de l'input afin de pouvoir "faire le tri"
   *              au niveau du serveur lors d'une modification.
   * @param vcrData Vecteur de la liste des elements de la combobox
   * @return Vecteur contenant les informations relatives à la colonne définie.
   */
  public Vector jsp_SetCombobox(Vector head,String strLigneVide,String strName,int numCol,Vector vcrData) {
    Vector res = new Vector();
    res.addElement(strName);
    res.addElement(FxUtils.intToInteger(numCol));
    res.addElement(vcrData);
    res.addElement(strLigneVide);
    head.addElement(res);
    res = head;

    return res;
  }

  /**
   * Formatte une liste de paramètres
   * @param parameter chaine de caractères contenant les paramètres
   * @return La liste des paramètres formattés
   */
  private String jsp_FormatParameter(String parameter) {
    StringBuffer res = new StringBuffer();
    Vector vecGroup = new Vector ();
    vecGroup = FxUtils.split(parameter,"&");

    for (int i=0;i<vecGroup.size();i++) {
      Vector vecUnit = new Vector();
      vecUnit = FxUtils.split(vecGroup.elementAt(i).toString(),"=");
      try {
        res.append( vecUnit.elementAt(0).toString()+"="+java.net.URLEncoder.encode(vecUnit.elementAt(1).toString()) );
        if (i+1<vecGroup.size()) {
          res.append("&");
        }
      } catch (Exception e) {
        System.err.println("Les parametres du tableau ne sont pas bien contruit ie :\" inex=0&numero=300 \"");
      }
    }

    return res.toString();
  }


  /**
   * Cree l'index du tableau
   * @param row Entier qui represente le nombre de page/index
   * @param nbrLigne Entier qui represente le nombre de lignes/page
   * @param nbrPage Entier qui represente le nombre de pages
   * @param indexDeb Entier qui represente l'index de debut
   * @param colspan Le nombre de colonne que doit inclure l'index
   * @param strChemin chemin de la cible des liens de l'index
   * @return Chaine de caractere contenant les cible du liens preformats pour l'URL.
   */
  protected String jsp_CreateIndex(Vector row,int nbrLigne,int nbrPage,int indexDeb,int nbrColspan,String strChemin) {
    Jsp_Liens link = new Jsp_Liens();
    StringBuffer res = new StringBuffer();
    int pageVisual = indexDeb;
    String chemin="page.jsp";
    String strLiaison="?";

    if ((strChemin!=null) && (!strChemin.equals(""))){
      chemin = strChemin;
    }
    if (strChemin.indexOf(strLiaison)>0){
      strLiaison = "&";
    }
    //int nbrPageTotal=java.lang.Math.round(row.size()/nbrLigne);
    long nbrPageTotal = java.lang.Math.round(((double)row.size())/nbrLigne);
    if ((nbrPageTotal-(double)row.size()/nbrLigne)<0) {
      nbrPageTotal = nbrPageTotal+1;
    }

    if (nbrPageTotal>1) {
      // creation de la table contenant l'index
      res.append( "<table align=\"center\" >" );
      res.append( "<tr>" );
      res.append( "<td align=\"center\">" );

      //permet de n'afficher que des groupes de 'nbrPage' pages on calcule donc la premiere page
      // de l'index
      indexDeb=(((int)((indexDeb-1)/nbrPage))*nbrPage)+1;
      // Verification si l'on doit afficher les fleches de gauche
      if ((indexDeb-nbrPage)>0){
        res.append( link.jsp_Link("<<",chemin + strLiaison + "index="+(indexDeb-nbrPage)+"") );
      }
      // Affichage des indexs
      Jsp_Libelles lib = new  Jsp_Libelles();
      for(int i=indexDeb;i<(indexDeb+nbrPage);i++) {
        if(i<=nbrPageTotal){
          // Pas de lien sur la page qui est en cours d'affichage.
          if (i==pageVisual){
            res.append("&nbsp;" + lib.jsp_Str(" "+i+" "));
          } else {
            res.append("&nbsp;" + link.jsp_Link("" + i + "", chemin + strLiaison + "index=" + i + ""));
          }// fin if
        }//fin else
      } //fin for

      //Pas de ">>" si il n'y a plus de page a afficher
      if ((indexDeb+nbrPage)<=nbrPageTotal) {
        res.append("&nbsp;"+link.jsp_Link(">>",chemin + strLiaison + "index="+(indexDeb+nbrPage)+"") );
      }// fin if

      res.append("</td>");
      res.append("</tr>");
      res.append("</table>");
    } else {
      res.append("&nbsp;");
    }

    return res.toString();
  }


  /** AJOUT MR
   * Assesseur pour la propriete de l'infoBulle de l'image du tableau modification.
   * @param pInfoBulle valeur de l'infoBulle
   * @return void
   */
  public void setInfoBulle(String pInfoBulle) {
    this.infoBulle = pInfoBulle;
  }

  /*
  * Assesseur pour la propriete de l'infoBulle de l'image du tableau modification.
  * @return String valuer d el'infoBulle
  */
  public String getInfoBulle() {
    return this.infoBulle;
  }

}