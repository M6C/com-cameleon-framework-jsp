package ressource;


/***************************************************************************
*             Package qui contient des outils utilisable                   *
*                        par d'autre packages                              *
***************************************************************************/

import java.util.*;
import java.text.*;
import java.math.*;
import java.util.Enumeration;

/**
 * Classe contenant toutes les méthodes très utilisées
 * @author ressource
 */

public class FxUtils {

  public static String FORMAT_ENTIER = "### ###";

	/**
	 * transforme un "int" en Integer
	 * @param nbr  nombre a convertir
	 * @return Un Integer
	 */
	public static Integer intToInteger(int nbr) {
		return ((Integer.valueOf(String.valueOf(nbr))));
	}


	/**
	 * Transforme tout objet en un entier de type int
	 * @param ob  Objet a transformer
	 * @return Resultat de la transformation
	 */
	public static int objectToInt(Object ob) {
		String str = new String();
		str = ob.toString();
		return ((Integer.valueOf(str)).intValue());
	}


	/**
	 * Retourne un vecteur contenant les differentes parties delimitées
	 * par le separateu (sans le separateur)
	 * @param str  Chaine a découper
	 * @param sep  séparateur
	 * @return vecteur de chaines de caractères
	 */
	public static Vector split(String str,String sep) {
		Vector res=new Vector();
		int index;

		String str2=new String(str);
		while (str2.indexOf(sep) != -1) {
			index=str2.indexOf(sep);
			res.addElement(str2.substring(0,index));
			str2=str2.substring(index+sep.length(),str2.length());
		}

		if (str2.length()!=str.length()) {
			res.addElement(str2);
			return res;
		} else {
			res.addElement(str);
			return res;
		}
	}


	/**
	 * Enleve les objets nulls du vecteur et reduit la taille en consequence du vecteur
	 * @param v  Vecteur de file contenant les repertoires freres non tries
	 * @return Le vecteur modifié
	 */
	public static Vector trim(Vector v) {
		Vector res = new Vector();
		int i = v.indexOf(null);
		res=v;
		if (i !=-1) {
			res.setSize(i);
		}
		return res;
	}


	/**
	 * Renvoie la liste des parametres du resuest et leurs valeurs pour insertion dans une URL
	 * @param entree  request
	 * @param sorie  neant
	 * @return le code genere et a ajouter dans l'"URL" + "?"
	 */
/*
	public static String listeParametres(javax.servlet.http.HttpServletRequest req) {

		StringBuffer strResult = new StringBuffer();
		boolean bool = true;

		Enumeration enum = req.getParameterNames();
		while (enum.hasMoreElements() ) {
			if (!bool) {
				strResult.append("&");
			} else {
				bool = false;
		  }
			StringBuffer str = new StringBuffer( enum.nextElement().toString() );
			strResult.append( str.toString() + "=");
			strResult.append( java.net.URLEncoder.encode(req.getParameter(str.toString())));
		}

		return strResult.toString();
	}
*/

  /**
   * Remplace une chaine de caractere par une autre contenu dans une hashtable entre les caracteres "tag" de debut et de fin
   * @param cells chaine original.
   * @param hashLangue HashTable qui contient les chaines qui doivent etre remplace et leur remplacant i.e. : [[clef1,remplacant1],[clef2,remplacant2]]
   * @param caractereDebut chaine representant le tag de début.
   * @param caractereFin chaine representant le tag de fin.
   * @return La chaine original avec les mots clef qui ont ete remplace par leur remplaçant contenu par hashLangue
  */
  public String remplaceChaine(String cells,java.util.Hashtable hashLangue,String caractereDebut,String caractereFin) {
    String obj = " ";
    int indexDebut = 0;
    int indexFin = cells.indexOf(caractereFin);
    String key =null;
    if (indexFin>0) {
      java.lang.StringBuffer cellBuff = new java.lang.StringBuffer(cells);
      do {
        indexDebut = (cells.substring(0,indexFin)).lastIndexOf(caractereDebut);
        key = cells.substring(indexDebut+1,indexFin);
          if(hashLangue.containsKey(key)) {
            obj = (String)hashLangue.get(key);
            cellBuff.replace(indexDebut+1,indexFin,obj);
            cells = cellBuff.toString();
            indexFin = cells.indexOf(obj)+obj.length();
          }
        indexFin = cells.indexOf(caractereFin,indexFin+caractereFin.length());
      } while((indexFin > 0));
    }

    return cells;
  }

	/**
   * Change les ',' en '.' pour pouvoir utiliser les constructeurs java des types Double, etc ...
   *
	 * @param sNbAReformater String
	 * @return String
	 */
	public static String formateNombreJava(String sNbAReformater)
  {
    return ( sNbAReformater==null ? null : sNbAReformater.replace(',', '.') );
	}

	/**
   * Change les '.' en ',' pour l'affichage dans les contrôles javascript
   *
	 * @param sNbAReformater String
	 * @return String
	 */
	public static String reformateNombreJS(String sNbAReformater)
  {
    return ( sNbAReformater==null ? null : sNbAReformater.replace('.', ',') );
	}


  /**
	 * arrondi un nombre au format monnaie.
	 * @param montant  Valeur à arrondir.
	 * @return valeur arrondie.
	 */
	public static double arrondiMonnaie(double montant) {
		return ((double)Math.floor(montant*100))/100.0;
	}

	/**
   * Change les ' ' ' en ' \' ' pour le passage des paramètres des popups vers la fenêtre principale
   *
	 * @param sChaine String
	 * @return String
	 */
	public static String reformateParamStringJS(String sChaine)
  {

   if (sChaine==null)
    {return null;}
   else
    {
      StringBuffer sbNom = new StringBuffer( sChaine );
      for (int j=0; j<sbNom.length(); j++)
       {
        if (sbNom.charAt(j) == '\'')
         {
          if (j>1)
           {
            if (sbNom.charAt(j-1) != '\\' )
             {
              sbNom.insert(j++, '\\');
             }
           }
          else
           {
            sbNom.insert(j++, '\\');
           }
         }
       }
      return sbNom.toString();
    }
	}

	/**
     *  Description de la méthode
     *
     *@param  pDate    Description of the Parameter
     *@param  pFormat  Description of the Parameter
     *@return          Description of the Return Value
     */
    //public static String convertDateToString(java.sql.Date pDate, String pFormat) {
    public static String convertDateToString(java.util.Date pDate, String pFormat) {
        String strDate = "";
        SimpleDateFormat formatDate = new SimpleDateFormat(pFormat);
        strDate = formatDate.format(pDate);
        return strDate;
    }


    /**
     *  Description de la méthode
     *  Convertit un vecteur (de vecteur)* d'objet
     *  en vecteur (de vecteur)* de chaine
     *
     *@param  donnees  Description of the Parameter
     *@return          Description of the Return Value
     */

    public static Vector convertVectorObjectToVectorString(Vector donnees) {
        Vector donneesFormatees = new Vector();
        for (int i = 0; i < donnees.size(); i++) {
            Object o = donnees.elementAt(i);
            if (o instanceof Vector) {
                donneesFormatees.addElement(convertVectorObjectToVectorString((Vector)o));
            } else {
                donneesFormatees.addElement(o.toString());
            }
        }
        return donneesFormatees;
    }


    /**
     *  Description de la méthode
     *
     *@param  entier  Description of the Parameter
     *@param  format  Description of the Parameter
     *@return         Description of the Return Value
     */
    public static String formateIntToString(long entier, String format) {
        String strInt = "";
        DecimalFormat formatteur = new DecimalFormat(format);
        strInt = formatteur.format(entier);
        return strInt;
    }


    /**
     *  Description de la méthode
     *
     *@param  entier  Description of the Parameter
     *@return         Description of the Return Value
     */
    public static String formateIntToString(long entier) {
        return formateIntToString(entier, FORMAT_ENTIER);
    }


    /**
     *  Description de la méthode
     *
     *@param  entier  Description of the Parameter
     *@param  format  Description of the Parameter
     *@return         Description of the Return Value
     */
    public static String formateIntToString(Long entier, String format) {
        return (entier == null) ? "" : formateIntToString(entier.longValue(), format);
    }


    /**
     *  Description de la méthode
     *
     *@param  entier  Description of the Parameter
     *@return         Description of the Return Value
     */
    public static String formateIntToString(Long entier) {
        return formateIntToString(entier, FORMAT_ENTIER);
    }


    /**
     *  Description de la méthode
     *
     *@param  entier  Description of the Parameter
     *@return         Description of the Return Value
     */
    public static String formateIntToString(String entier) {
        StringBuffer buffer = new StringBuffer();

        if (entier != null) {
            int longueur = entier.length();

            int iDebut = 0;
            int iFin;

            int increment;

            while (iDebut < longueur) {
                increment = (longueur - iDebut) % 3;
                if (increment == 0) {
                    increment = 3;
                }

                buffer.append(entier.substring(iDebut, iDebut + increment));
                iDebut += increment;
                if (iDebut < longueur) {
                    buffer.append(" ");
                }
            }
        }
        return buffer.toString();
    }


    /**
     *  Description de la méthode
     *
     *@param  siret  Description of the Parameter
     *@return        Description of the Return Value
     */
    public static String formateSiren(String siret) {
        if (siret == null) {
            return "";
        } else if (siret.length() < 9) {
            return formateIntToString(siret);
        } else {
            return formateIntToString(siret.substring(0, 9));
        }
    }


    /**
     *  Convertit en un objet Java java.sql.Date une chaîne de caractères
     *  d'après un format. Cette chaîne de caractères peut être la valeur d'un
     *  champ INPUT text d'un formulaire HTML.
     *
     *@param  sDate   Date sous forme de String
     *@param  format  Description of the Parameter
     *@return         objet java.sql.Date
     *@prama          format Format sous laquelle se présente la date
     */
    public static java.sql.Date getDateFromJS(String sDate, String format) {
        if (sDate != null) {
            SimpleDateFormat formatDate = new SimpleDateFormat(format);
            java.util.Date dt = null;
            try {
                dt = formatDate.parse(sDate);
            } catch (ParseException pe) {
            }
            if (dt != null) {
                return new java.sql.Date(dt.getTime());
            }
        }

        return null;
    }


    /**
     *  Retourne l attribut dateFromJS de Utils (class)
     *
     *@param  sDate  Description of the Parameter
     *@return        La valeur dateFromJS
     */
    public static java.sql.Date getDateFromJS(String sDate) {
        return getDateFromJS(sDate, "dd/MM/yyyy");
    }


    /**
     *  Récupère une chaîne de caractères représentant un nombre décimal
     *  arrondi.
     *
     *@param  nb           - Nombre à arrondir
     *@param  nbDecimales  - Nombre de décimales
     *@return              String
     */
    public static String getNbArrondi(Double nb, int nbDecimales) {
        String strNb = "";
        if (nb != null) {
            BigDecimal bd = new BigDecimal(nb.toString());
            strNb = bd.setScale(nbDecimales, BigDecimal.ROUND_HALF_UP).toString();
            strNb = reformateNombreJS(strNb);
        }
        return strNb;
    }


    /**
     *  Retourne l attribut doubleFromJS de Utils (class)
     *
     *@param  strDouble  Description of the Parameter
     *@return            La valeur doubleFromJS
     */
    public static Double getDoubleFromJS(String strDouble) {
        if ((strDouble != null) && (!strDouble.equals(""))) {
            return new Double(formateNombreJava(strDouble));
        }
        return null;
    }


    /**
     *  Retourne l attribut integerFromJS de Utils (class)
     *
     *@param  strInteger  Description of the Parameter
     *@return             La valeur integerFromJS
     */
    public static Integer getIntegerFromJS(String strInteger) {
        if ((strInteger != null) && (!strInteger.equals(""))) {
            return new Integer(strInteger);
        }
        return null;
    }


    /**
     *  Retourne l attribut longFromJS de Utils (class)
     *
     *@param  strLong  Description of the Parameter
     *@return          La valeur longFromJS
     */
    public static Long getLongFromJS(String strLong) {
        if ((strLong != null) && (!strLong.equals(""))) {
            return new Long(strLong);
        }
        return null;
    }


    /**
     *  Retourne l attribut floatFromJS de Utils (class)
     *
     *@param  strFloat  Description of the Parameter
     *@return           La valeur floatFromJS
     */
    public static Float getFloatFromJS(String strFloat) {
        if ((strFloat != null) && (!strFloat.equals(""))) {
            return new Float(strFloat);
        }
        return null;
    }


    /**
     *  Retourne l attribut booleanFromJS de Utils (class)
     *
     *@param  strBoolean  Description of the Parameter
     *@return             La valeur booleanFromJS
     */
    public static Boolean getBooleanFromJS(String strBoolean) {
        return new Boolean(strBoolean);
    }

}
