package ressource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import ressource.bean.BeanTag;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class FxHtml {

  protected FxHtml() {
  }

  /**
   * Delete all tag in
   * @param str
   * @param listTag
   * @return
   */
  public static String deleteTag(String str, Vector listTag)
  {
    StringBuffer ret = new StringBuffer("");
    for (int i = 0; i < str.length()-1; i++)
    {
      boolean bAppend = true;
      char c = str.charAt(i);
      if ( c == '<' )
      {
        String szTag = getNextTagName(str, i);
        bAppend = !FxVector.isStringInVector(szTag, listTag);
      }
      if ( bAppend )
        ret.append(c);
      else
        i = str.indexOf('>', i);
    }
    return ret.toString();
  }

  /**
   * Return the name of next tag in str from iIndex
   * @param str
   * @param iIndex
   * @return
   */
  public static String getNextTagName( String str, int iIndex )
  {
    StringBuffer ret = new StringBuffer("");
    int iStart = iIndex;
    // Recherche le début d'un tag
    while ( ( iStart < str.length() ) && ( str.charAt(iStart)!='<' ) ) iStart++;
    // recupere le nom du tag
    if ( str.charAt(iStart)=='<' )
    {
      boolean bContinue = true;
      for( int i=iStart+1 ; (i<str.length()) && bContinue ; i++ )
      {
        char c=str.charAt(i);
        switch(c)
        {
          case ' ': case '\n': case '\r': case '\t':
            bContinue = (ret.length()==0);
            break;
          case '>':
            bContinue = false;
            break;
          default:
            ret.append(c);
        }
      }
    }
    return ret.toString().trim();
  }

  public static String getNextText(String str, String strFrom, int iIndex )
  {
    StringBuffer ret = new StringBuffer();
    int iDeb=str.indexOf(strFrom, iIndex);
    if ( iDeb > -1 )
    {
      iDeb += strFrom.length();
      boolean bGuillemet = (str.charAt(iDeb) == '"');
      boolean bLoop = true;
      if ( bGuillemet ) iDeb++;
      while( bLoop )
      {
        char c = str.charAt(iDeb++);
        switch( c )
        {
          case ' ':
            if ( !bGuillemet ) bLoop = false;
            break;
          case '>': case '"':
            bLoop = false;
            break;
        }
        if ( bLoop ) ret.append(c);
      }
    }
    return ret.toString();
  }

  /**
   * Return an Indented String from str
   * @param str
   * @return
   */
  public static String indentString(String str) {

  	boolean bIndent = true; 
  	List lBean = extractHashTag(str);
  	System.out.println(lBean);

  	initLevel(lBean);

    //str = toHtmlAll("toHtmlAll chaineBeanTagAll", lBean, bIndent);
    str = toHtmlAll(null, lBean, bIndent);

    return str;
  }

  /**
   * Return an Indented String from str
   * @param str
   * @return
   */
  public static String old_indentString(String str)
  {
  	String ret = null;
    StringBuffer stb = new StringBuffer("");
    StringBuffer szTag = new StringBuffer("");
    String szIndent = "    ";
    int iIndentLength = szIndent.length();
    String szRetour = "\r\n";
    int iRetourLength = szRetour.length();
    int iIndent = 0;
    // Indique si on est dans un commentaire
    boolean bCommentaire = false;
    // Indique si on est dans un tag
    boolean bTag = false;
    // Indique si on est dans un tag de fin
    boolean bEndTab = false;
    // Caracteres lu dans str
    char c = 0;
    // Anciens caracteres lu dans str (cOld1 = -1, cOld2 = -2...)
    char cOld1 = 0, cOld2 = 0;
    // Ancienne variable ajoute dans stb (cStbOld1 = -1, cStbOld2 = -2...)
    char cStbOld1 = 0;
    int strLength = str.length();
    int stbLength = 0;

    try {
	    for (int i = 0; i < strLength-1; i++) {
	      cOld2 = cOld1;
	      cOld1 = c;
	      c = str.charAt(i);
	      if ( bCommentaire ) {
	          bTag = false;
	          bEndTab = false;
	          // Teste si c'est la fin d'un commentaire de la forme <!-- bla bla bla -->
	          if ((strLength>3) && ( cOld1=='-' ) && ( cOld2=='-' ) && ( c=='>' ))
	            bCommentaire = false;
	      }
	      else if ( c == '!' ) {
	      	bCommentaire = (cOld1=='<');
	      }
	      else if ( c == '/' ) {
	      	if ((cOld1=='<') && (iIndent>0)) {
	          iIndent--;
	          bEndTab = true;
	      	}
	      }
	      else if ( c == '<' ) {
	          bTag = true;
	          bEndTab = false;
	      }
	      else if ( c == '>' ) {
	      	if(!bEndTab) {
		        if ( (str.indexOf("</"+szTag.toString().toLowerCase(), i)>=0) || (str.indexOf("</"+szTag.toString().toUpperCase(), i)>=0) )
		          iIndent++;
	      	}
	        bTag = false;
	        szTag = new StringBuffer("");
	      }
	      else {
	        if ( (i>0) && (!bTag) && (stbLength>0) && (cStbOld1=='>') ) {
	          stb.append(szRetour);
            stbLength+=(iRetourLength);
	          for (int j=0; j<iIndent; j++)
	            stb.append(szIndent);
            stbLength+=(iIndentLength*iIndent);
	        }
	        else if ( bTag && (c==' ') )
	          bTag = false;
	        else if ( bTag )
	          szTag.append(c);
	      }
	      switch ( c ) {
	        case '\n': case '\r': case '\t':
	          break;
	        case ' ':
	        {
	          switch(cOld1) {
	            case ' ': case '>': case '\n': case '\r': case '\t':
	              break;
	            default: {
	  	        	cStbOld1 = c;
	              stb.append(c);
	              stbLength++;
	            }
	          }
	          break;
	        }
	        case '<':
	        	break;
	        default: {
	        	if (cOld1=='<') {
	        		stb.append(szRetour);
              stbLength+=(iRetourLength);
	  	        for (int j=0; j<iIndent; j++)
	  	          stb.append(szIndent);
              stbLength+=(iIndentLength*iIndent);
		          stb.append(cOld1);
              stbLength++;
	        	}
	        	stb.append(c);
            stbLength++;
	        }
	      }
	    }
    }
    finally {
      ret = stb.toString();

      stb = null;
      szTag = null;
      szIndent = null;
      szRetour = null;

    	System.gc();
    	System.gc();
    }
    return ret;
  }

  /**
   * Return an corrected String from str
   * Correction :
   *  - Add missing end tag
   * @param str
   * @return
   */
  public static String correctMalformed(String str) {
  	return correctMalformed(str, false);
  }

  /**
   * Return an corrected String from str
   * Correction :
   *  - Add missing end tag
   * @param str
   * @return
   */
  public static String correctMalformed(String str, boolean indent) {

  	boolean bWrite = false; 
  	List lBean = extractHashTag(str);
  	System.out.println(lBean);
  	if (bWrite) writeHashtableTree ("/", "extractHashTag lBean", lBean);
  	
  	correctUnlessEnd(lBean);
  	if (bWrite) writeHashtableTree ("/", "correctUnlessEnd lBean", lBean);

  	initLevel(lBean);
  	if (bWrite) writeHashtableTree ("/", "initLevel lBean", lBean);

  	correctLevelEnd(lBean);
  	if (bWrite) writeHashtableTree ("/", "correctLevelTagEnd lBean", lBean);

  	correctLevelBegin(lBean);
  	if (bWrite) writeHashtableTree ("/", "correctLevelBegin lBean", lBean);

  	if (bWrite) writeHashtableCorrectAuto("/", "CorrectedAndAutomatic lBean", lBean);

  	initLevel(lBean);
  	if (bWrite) writeHashtableTree ("/", "initLevel 2 lBean", lBean);

  	if (bWrite) {
	  	List lBeanClean = cleanEmptyTag(lBean);
	  	Collections.sort(lBeanClean);
	  	writeHashtableTree ("/", "sortHashtableTreeBean lBeanSortClean", lBeanClean);
  	}

  	initLevel(lBean);
  	if (bWrite) writeHashtableTree ("/", "initLevel 3 lBean", lBean);

  	if (bWrite) {
	  	List lBeanClean2 = cleanEmptyTag(lBean);
	  	Collections.sort(lBeanClean2);
	  	writeHashtableTree ("/", "sortHashtableTreeBean lBeanSortClean2", lBeanClean2);
  	}

  	if (bWrite) writeHashtableHtmlAll("/", "writeHashtableHtmlAll chaineBeanTagAll", lBean, indent);

  	if (bWrite) cleanWrited(lBean);

    str = toHtmlAll("toHtmlAll chaineBeanTagAll", lBean, indent);

    return str;
  }

  /**
   * Return an corrected String from str
   * Correction :
   *  - Add missing end tag
   * @param str
   * @return
   */
  private static void initLevel(List lBean) {
  	initLevel(lBean, "0", 0);
  }

  /**
   * Return an corrected String from str
   * Correction :
   *  - Add missing end tag
   * @param str
   * @return
   */
  private static void initLevel(List lBean, String levelCurrent, int index) {
    if (lBean!=null && !lBean.isEmpty()) {
	    // Indique si on est dans un tag de fin
	    boolean bEndTag = false;
	    boolean bBeginEnd = false;
	    BeanTag beanTag = null;
	    if (lBean!=null && !lBean.isEmpty()) {
		    int len = lBean.size();
		    int iLevel = 0;
		    String szLevelCurrent = levelCurrent;
  			if (szLevelCurrent.indexOf("-")>0) {
  				iLevel = Integer.parseInt(szLevelCurrent.substring(szLevelCurrent.lastIndexOf("-")+1));
  			}
  			else {
  				iLevel = Integer.parseInt(szLevelCurrent);
  			}
		    boolean bEndNoMandatory = false;
		    List lLevel = new ArrayList();
		    Hashtable hLevelCnt = new Hashtable();
		    Integer iCnt = null;

		    try {
		    	// Alimente les listes pour les bean anterieurs
			    for (int i = 0 ; i < index ; i++) {
			    	beanTag = (BeanTag)lBean.get(i);
			    	// Verifi si on est sur un tag de début et de fin
			    	bBeginEnd = beanTag.getBeginEnd().booleanValue();
			    	// Verifi si une fin de tag est obligatoire
			    	bEndNoMandatory = !beanTag.isMandatoryEnd();
			    	if (!bBeginEnd && !bEndNoMandatory) {
				    	// Verifi si on est sur un tag de fin
				    	bEndTag = beanTag.getEnd().booleanValue();
			    		// Cas où on est sur un tag de début
			    		if (!bEndTag) {
			    			lLevel.add(beanTag.getLevel());
			    		}
	
				    	iCnt = (Integer)hLevelCnt.get(beanTag.getLevel());
				    	if (iCnt==null)
				    		iCnt = new Integer(0);
				    	hLevelCnt.put(beanTag.getLevel(), new Integer(iCnt.intValue()+1));
			    	}
			    }
			    for (int i = index ; i < len ; i++) {
			    	beanTag = (BeanTag)lBean.get(i);
			    	// Verifi si on est sur un tag de début et de fin
			    	bBeginEnd = beanTag.getBeginEnd().booleanValue();
			    	// Verifi si une fin de tag est obligatoire
			    	bEndNoMandatory = !beanTag.isMandatoryEnd();
			    	// Cas où on n'est pas sur un tag de debut et de fin
			    	// et qu'une fin de tag est obligatoire
			    	if (!bBeginEnd && !bEndNoMandatory) {
				    	// Verifi si on est sur un tag de fin
				    	bEndTag = beanTag.getEnd().booleanValue();
			    		// Cas où on est sur un tag de début
			    		if (!bEndTag) {
			    			iLevel = 0;
		    				String szLevel = szLevelCurrent + "-" + iLevel;
		    				while(lLevel.contains(szLevel)) {
		    					szLevel = szLevelCurrent + "-" + (++iLevel);
		    				}
		    				szLevelCurrent = szLevel;
					    	lLevel.add(szLevelCurrent);
			    		}
			    	}
			    	if (bBeginEnd || bEndNoMandatory) {
			    		String szL = szLevelCurrent + "-0";
				    	// Compte  le nombre d'élements par level
				    	iCnt = (Integer)hLevelCnt.get(szL);
				    	if (iCnt==null) {
				    		iCnt = new Integer(0);
				    	}
				    	iCnt = new Integer(iCnt.intValue()+1);
				    	hLevelCnt.put(szL, iCnt);
	
							// Initialise le niveau du bean
				    	beanTag.setLevel(szL);
				    	// Initialise l'ordre
				    	beanTag.setOrder(iCnt);
			    	} else {
				    	// Compte  le nombre d'élements par level
				    	iCnt = (Integer)hLevelCnt.get(szLevelCurrent);
				    	if (iCnt==null) {
				    		iCnt = new Integer(0);
				    	}
				    	iCnt = new Integer(iCnt.intValue()+1);
				    	hLevelCnt.put(szLevelCurrent, iCnt);
	
							// Initialise le niveau du bean
				    	beanTag.setLevel(szLevelCurrent);
				    	// Initialise l'ordre
				    	beanTag.setOrder(iCnt);
	
			    		// Cas où on est sur un tag de fin
				    	if (!bBeginEnd && !bEndNoMandatory && bEndTag) {
				    		// Passe au niveau inferieur
			    			if (szLevelCurrent.indexOf("-")>0) {
			    				szLevelCurrent = szLevelCurrent.substring(0, szLevelCurrent.lastIndexOf("-"));
				    			if (szLevelCurrent.indexOf("-")>0) {
				    				iLevel = Integer.parseInt(szLevelCurrent.substring(szLevelCurrent.lastIndexOf("-")+1))-1;
				    			}
				    			else {
				    				iLevel = Integer.parseInt(szLevelCurrent)-1;
				    			}
			    			}
			    			else {
			    				iLevel = Integer.parseInt(szLevelCurrent)-1;
			    			}
			    			if (iLevel<0)
			    				iLevel = 0;
				    	}
			    	}
			    }
		    }
		    finally {
			  	//writeHashtableTree ("/", "============= initLevel lBean levelCurrent:" + levelCurrent + " index:" + index, lBean);

			  	lBean = null;
		
		      System.gc();
		    	System.gc();
		    }
	    }
    }
  }

  /**
   * Verifi si tous les tag de début on bien un tag de fin au même niveau
   * Créé un nouveau tag de fin<br>
   * Correction :
   *  <ul>Add missing end tag</ul>
   * @param str
   * @return
   */
  private static void correctLevelBegin(List lBean) {
    // Indique si on est dans un tag de fin
    if (lBean!=null && !lBean.isEmpty()) {
    	int count = 0, current = 0;
	    boolean bEndTag = false;
	    boolean bBeginEnd = false;
	    boolean bEndNoMandatory = false;
	    boolean bComment = false;
	    BeanTag beanTag = null;

	    try {
	    	Iterator itBean = lBean.iterator();
	    	while (itBean.hasNext()) {
	    		// Avance jusqu'au bean courant
	    		for ( ; count < current ; count++) {
	    			itBean.next();
	    		}
		    //for (int i = 0 ; i < len ; i++) {
		    	//beanTag = (BeanTag)lBean.get(i);
		    	beanTag = (BeanTag)itBean.next();
	    		// Verifi si on est sur un tag de fin
		    	bEndTag = beanTag.getEnd().booleanValue();
		    	// Verifi si on est sur un tag de début et de fin
		    	bBeginEnd = beanTag.getBeginEnd().booleanValue();
		    	// Verifi si une fin de tag est obligatoire
		    	bEndNoMandatory = !beanTag.isMandatoryEnd();
		    	// Verifi si c'est un commentaire
		    	bComment = beanTag.isComment();
		    	// Cas où :
		    	// - on est sur un tag de debut
		    	// - on n'est pas sur un tag de debut et de fin,
		    	// - on n'est pas sur un tag de commentaire,
		    	// - une fin de tag est obligatoire,
		    	// - le nombre de tag ouvert est positif
		    	if (!bEndTag && !bBeginEnd &&
		    			!bComment && 
		    			!bEndNoMandatory && beanTag.getCount().intValue()>0) {
		    		// Indique si le tag automatique de fin a été ajouté
		  	    boolean bAdded = false;
		    		// Parcour la liste à la recherche d'un tag qui peut le fermer
		  	    int len = lBean.size();
		  	    for(int i=(count+1) ; i<len ; i++) {
				    	BeanTag tag = (BeanTag)lBean.get(i);
				    	// Verifi si c'est un commentaire
				    	bComment = tag.isComment();
				    	// Verifi si le tag doit ferme le tag en erreur 
				    	if (!bComment  && tag.isClosingTag(beanTag)) {
				    		addAutomaticEndTagAt(lBean, beanTag, i);
			    			// Recréé l'iteration
			    			itBean = lBean.iterator();
			    			// Réinitialise le compteur
			    			count = 0;
			    			// Indique que le tag automatique de fin a été ajouté
			    			bAdded = true;
			    			break;
				    	}
		  	    }
		  	    // Verifi si un tag automatique de fin à été ajouté 
		  	    if (!bAdded) {
			  	    // Ajout un tag automatique de fin 
		  	    	addAutomaticEndTagAt(lBean, beanTag, count+1);
		    			// Recréé l'iteration
		    			itBean = lBean.iterator();
		    			// Réinitialise le compteur
		    			count = 0;
		  	    }
		    	}
		    	count++;
		    	current++;
		    }
	    }
	    finally {
	      lBean = null;
	
	      System.gc();
	    	System.gc();
	    }
    }
  }
  
  private static void addAutomaticEndTagAt(List lBean, BeanTag beanTag, int i) {
		BeanTag beanTagEnd = beanTag.createEnd();
		// Indique que le tag à été ajouté automatiquement
		beanTagEnd.setAutomatic(Boolean.TRUE);
		// Ajout l'élement de fin de tag à la liste
		lBean.add(i, beanTagEnd);
		// Décrémente le nombre de tag ouvert
		beanTag.setCount(new Integer(beanTag.getCount().intValue()-1));
		// Decremente Les levels qui suivent
		decreaseLevel(lBean, ++i);
  }

  /**
   * Verifi si tous les tag de fin on bien un tag de début au même niveau
   * Si non, recherche le précédent tag ouvert<br>
   * Correction :
   *  <ul>Add missing end tag</ul><br>
   * @param str
   * @return
   */
  private static void correctLevelEnd(List lBean) {
    // Indique si on est dans un tag de fin
    boolean bEndTag = false;
    boolean bBeginEnd = false;
    BeanTag beanTag = null;
    if (lBean!=null && !lBean.isEmpty()) {
	    int len = lBean.size();
	    boolean bEndNoMandatory = false;
	    Hashtable hBeanOld = new Hashtable();
	    Hashtable lBeanOld = new Hashtable();

	    try {
		    for (int i = 0 ; i < len ; i++) {
		    	beanTag = (BeanTag)lBean.get(i);
		    	// Verifi si on est sur un tag de début et de fin
		    	bBeginEnd = beanTag.getBeginEnd().booleanValue();
		    	// Verifi si une fin de tag est obligatoire
		    	bEndNoMandatory = !beanTag.isMandatoryEnd();
		    	// Cas où on n'est pas sur un tag de debut et de fin
		    	// et qu'une fin de tag est obligatoire
		    	if (!bBeginEnd && !bEndNoMandatory) {
		    		// Verifi si on est sur un tag de fin
			    	bEndTag = beanTag.getEnd().booleanValue();
		    		lBeanOld = (Hashtable)hBeanOld.get(beanTag.getLevel());
		    		// Cas où on est sur un tag de debut
		    		if (!bEndTag) {
			    		// Sauvegarde les anciens tag avec comme clé le level
			    		if (lBeanOld==null) {
			    			lBeanOld = new Hashtable();
			    		}
			    		lBeanOld.put(beanTag.getName(), beanTag);
			    		hBeanOld.put(beanTag.getLevel(), lBeanOld);
			    		beanTag.setCount(new Integer(1));
			    	}
		    		else {
			    		// Cas où on est sur un tag de fin
		    			if (correctTagLevelEnd(hBeanOld, beanTag)) {
		    				// Réinitialise les level suivant
		    				initLevel(lBean, beanTag.getLevel(), i);
		    				/*
		    				int cnt = i;
		    				BeanTag b = null;
		    				while (cnt<lBean.size() && (b==null)) {
		    					b = (BeanTag)lBean.get(cnt);
		    					if (b.isEnd())
		    						b = null;
		    					cnt++;
		    				}
	    					if (b!=null) {
			    				// Réinitialise les level suivant
			    				initLevel(lBean, b.getLevel(), cnt);
	    					}
	    					*/
		    			}
		    		}
		    	}
		    }
	    }
	    finally {
	      lBean = null;
	
	      System.gc();
	    	System.gc();
	    }
    }
  }

  /**
   * Supprime les tag de fin inutile
   * @param lBean Liste de BeanTag
   * @return
   */
  private static void correctUnlessEnd(List lBean) {
    // Indique si on est dans un tag de fin
    boolean bEndTag = false;
    boolean bBeginEnd = false;
    BeanTag beanTag = null;
    if (lBean!=null && !lBean.isEmpty()) {
	    int len = lBean.size();
	    boolean bEndNoMandatory = false;
	  	Hashtable hCount = new Hashtable();

	    try {
		    for (int i = 0 ; i < len ; i++) {
		    	beanTag = (BeanTag)lBean.get(i);
		    	// Verifi si on est sur un tag de début et de fin
		    	bBeginEnd = beanTag.getBeginEnd().booleanValue();
		    	// Verifi si une fin de tag est obligatoire
		    	bEndNoMandatory = !beanTag.isMandatoryEnd();
	    		// Verifi si on est sur un tag de fin
		    	bEndTag = beanTag.getEnd().booleanValue();
		    	// Cas où on est sur un tag de fin
		    	if (!bBeginEnd && !bEndNoMandatory) {
		    		Integer cnt = (Integer)hCount.get(beanTag.getName());
		    		int iCnt = (cnt==null ? 0 : cnt.intValue());
		    		if (!bEndTag) {
		    			iCnt++;
		    		} else {
			    		if (iCnt==0) {
			    			// Supprime l'élément
					    	lBean.remove(i);
		    				// Décrémente les compteurs
					    	i--;
					    	len--;
			    		}
			    		else {
			    			iCnt--;
			    		}
		    		}
		    		if (iCnt>0)
		    			hCount.put(beanTag.getName(), new Integer(iCnt));
		    	}
		    }
	    }
	    finally {
	      lBean = null;
	
	      System.gc();
	    	System.gc();
	    }
			// Réinitialise les level suivant
			initLevel(lBean);
    }
  }

  /**
   * Retourne une hashtable avec 
   *  - comme clé le niveau
   *  - comme donnée une autre hashtable avec :
   *          - comme clé le nom du tag
   *          - comme donnée les BeanTag de début
   * @param lBean List des tag
   * @return
   */
  private static Hashtable makeHashtableBegin(List lBean) {
  	return makeHashtable(lBean, Boolean.FALSE);
  }

  /**
   * Retourne une hashtable avec 
   *  - comme clé le niveau
   *  - comme donnée une autre hashtable avec :
   *          - comme clé le nom du tag
   *          - comme donnée les BeanTag de fin
   * @param lBean List des tag
   * @return
   */
  private static Hashtable makeHashtableEnd(List lBean) {
  	return makeHashtable(lBean, Boolean.TRUE);
  }

  /**
   * Retourne une hashtable avec 
   *  - comme clé le niveau
   *  - comme donnée une autre hashtable avec :
   *          - comme clé le nom du tag
   *          - comme donnée les BeanTag
   * @param lBean List des tag
   * @param bEnd Filtre sur :
   *   - les tag de fin si égal = Boolean.TRUE
   *   - les tag de début si égal = Boolean.FALSE
   *   - ne fait pas de filtre si null
   * @return
   */
  private static Hashtable makeHashtable(List lBean, Boolean bEnd) {
    Hashtable ret = null;
    // Indique si on est dans un tag de fin
    if (lBean!=null && !lBean.isEmpty()) {
	    ret = new Hashtable();
	    int len = lBean.size();
	    Hashtable lBeanOld = null;
	    BeanTag beanTag = null;

	    try {
		    for (int i = 0 ; i < len ; i++) {
		    	beanTag = (BeanTag)lBean.get(i);
		    	// Ajout dans la hashtable que si
		    	// - On ne trie pas sur les tag de fin ou de début seul
		    	// - Ou si les tag correspondent au filtre sur les tag de fin ou de début seul
		    	if (bEnd==null ||
		    			bEnd.equals(beanTag.getEnd())) {
						lBeanOld = (Hashtable)ret.get(beanTag.getLevel());
							// Sauvegarde les anciens tag avec comme clé le level
						if (lBeanOld==null) {
							lBeanOld = new Hashtable();
						}
						lBeanOld.put(beanTag.getName(), beanTag);
						ret.put(beanTag.getLevel(), lBeanOld);
		    	}
		    }
	    }
	    finally {
	      lBean = null;
	
	      System.gc();
	    	System.gc();
	    }
    }
    return ret;
  }

  private static boolean correctTagLevelEnd(Hashtable hBeanOld, BeanTag beanTag) {
		// Indique si il y a eu une correction en retour de methode
  	boolean ret = false;
  	BeanTag tag = null;
  	Hashtable lBeanOld = (Hashtable)hBeanOld.get(beanTag.getLevel());
		// Verifi si il y a un tag de début au même level
		if (lBeanOld!=null) {
			tag = (BeanTag)lBeanOld.get(beanTag.getName());
			// Verifi si : 
			//  - on a trouvé un tag du même nom
			//  - on a trouvé un tag de début
			//  - le compteur de tag de début est bien positif
			if (tag!=null) {
				if (!tag.getEnd().booleanValue() &&
						tag.getCount().intValue()>0) {
					// Decremente le nombre de tag de debut
					tag.setCount(new Integer(tag.getCount().intValue()-1));
				}
				else {
					tag = null;
				}
			}
		}

		if (tag==null) {
  		boolean bLoop = true;
  		String levelCurrent = beanTag.getLevel();
  		// Verifi si un tag de début à été trouvé
  		while (bLoop) {
  			// Recupère la dernière valeur du level
  			if (levelCurrent.indexOf("-")<=0) {
    			// Met fin à la boucle
  				bLoop = false;
  			}
    		// Passe au niveau inferieur
  			levelCurrent = decreaseLevel(levelCurrent);
  			//Verifi si on a trouvé une liste de tag pour le level
    		lBeanOld = (Hashtable)hBeanOld.get(levelCurrent);
    		if (lBeanOld!=null) {
    			//Verifi si on a trouvé un tag du même nom
    			tag = (BeanTag)lBeanOld.get(beanTag.getName());
  				if (tag!=null) {
    				// Verifi si : 
    				//  - on a trouvé un tag de début
    				//  - le compteur de tag de début est bien positif
    				if (!tag.isEnd() && tag.getCount().intValue()>0) {
    					// Corrige le level pour le ramener au level du tag de début
		    			beanTag.correctLevel(tag.getLevel());
    					// Corrige l'ordre pour le mettre en dernier
		    			beanTag.setOrder(new Integer(lBeanOld.size()));
		    			// Décrémente le compteur du tag de début
		    			tag.setCount(new Integer(tag.getCount().intValue()-1));
		    			// Met fin à la boucle
		    			bLoop = false;
		    			// Indique qu'il y a eu une correction en retour de methode
		    			ret = true;
    				}
    				else {
    					tag = null;
    				}
  				}
    		}
  		}
		}
		return ret;
  }

  private static void decreaseLevel(List lBean, int fromIndex) {
    // Indique si on est dans un tag de fin
    if (lBean!=null && !lBean.isEmpty()) {
	    int len = lBean.size();
	    BeanTag beanTag = null;
	    String level = null;

	    try {
		    for (int i = fromIndex ; i < len ; i++) {
		    	beanTag = (BeanTag)lBean.get(i);
		    	if (level==null)
		    		level = beanTag.getLevel();
		    	else {
		    		/**
		    		 * Mets fin à la décrémentation si on est arrivé à un
		    		 * niveau inferieur ou si on est revenu au niveau courant
		    		 */
		    		int levelCnt = level.split("-").length;
		    		int tagCnt = beanTag.getLevel().split("-").length;
		    		if (tagCnt<=levelCnt)
		    			break;
		    	}
		    	if (!beanTag.isAutomatic()) {
		    		beanTag.setLevel(decreaseLevel(beanTag.getLevel()));
		    	}
		    }
	    }
	    finally {
	    	//writeHashtableTree ("/", "============= decreaseLevel lBean fromIndex:" + fromIndex, lBean);
	      lBean = null;
	    }
    }
  }

  private static String decreaseLevel(String levelCurrent) {
  	String ret = "0";
  	int iLevel = 0;
		if (levelCurrent.indexOf("-")>0) {
			iLevel = Integer.parseInt(levelCurrent.substring(levelCurrent.lastIndexOf("-")+1))-1;
			if (iLevel<0)
    		// Passe au niveau inferieur
				ret = levelCurrent.substring(0, levelCurrent.lastIndexOf("-"));
			else
    		// Passe au niveau inferieur
				ret = levelCurrent.substring(0, levelCurrent.lastIndexOf("-")) + "-" + iLevel;
		}
		else {
			iLevel = Integer.parseInt(levelCurrent)-1;
			if (iLevel<0)
				iLevel = 0;
			ret = new Integer(iLevel).toString();
		}
		return ret;
  }

  private static List cleanEmptyTag(List lBean) {
  	List ret = null;
  	if (lBean!=null && !lBean.isEmpty()) {
  		ret = new ArrayList();
  		BeanTag beanTag=null;
	    int len = lBean.size();

	    for (int i = 0 ; i < len ; i++) {
	    	beanTag = (BeanTag)lBean.get(i);
	      	if (beanTag!=null && beanTag.getCount().intValue()!=0) {
	      		ret.add(beanTag);
	      	}
    	}
  	}
  	return ret;
  }

  private static void cleanWrited(Collection bean) {
		BeanTag b=null;
		if (bean!=null && !bean.isEmpty()) {
			Iterator itBean = bean.iterator();
	    while (itBean.hasNext()) {
	    	b = (BeanTag)itBean.next();
	    	b.setWrited(Boolean.FALSE);
	    }
		}
  }

  private static void writeHashtableTree (String path, String title, Collection bean) {
  	if (!path.endsWith("/") && !path.endsWith("\\"))
  		path += (path.indexOf("\\")>0 ? "\\" : "/");
  	try {
    	File file = getFile(title, path);
    	//title = "============= " + title;
      System.out.println(file.getName());// + " " + title);
			FileWriter fw = new FileWriter(file);
			fw.write(title);
    	fw.write("\r\n");
			BeanTag b=null;
			if (bean!=null && !bean.isEmpty()) {
				Iterator itBean = bean.iterator();
		    while (itBean.hasNext()) {
		    	b = (BeanTag)itBean.next();
		    	fw.write(b.getId()+":key:" + b.getLevel() + " " + b.toString());
		    	fw.write("\r\n");
		    }
			}
	    fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
  }

  private static void writeHashtableHtmlAll(String path, String title, Collection bean, boolean indent) {
  	if (!path.endsWith("/") && !path.endsWith("\\"))
  		path += "/";
  	try {
    	File file = getFile(title, path, "html");
    	//title = "============= " + title;
      System.out.println(file.getName());// + " " + title);
			FileWriter fw = new FileWriter(file);
			hashtableHtmlAll(fw, title, bean, indent);
		} catch (IOException e) {
			e.printStackTrace();
		}
  }

  public static String toHtmlAll(String title, Collection bean, boolean indent) {
  		StringWriter sw = new StringWriter();
			hashtableHtmlAll(sw, title, bean, indent);
			return sw.toString();
  }

  public static String toHtmlAll(String title, Iterator bean, boolean indent) {
  		StringWriter sw = new StringWriter();
  		iteratorHtmlAll(sw, title, bean, indent);
			return sw.toString();
  }

  private static void hashtableHtmlAll(Writer w, String title, Collection bean, boolean indent) {
		if (bean!=null && !bean.isEmpty()) {
			iteratorHtmlAll(w, title, bean.iterator(), indent);
		}
  }

  private static void iteratorHtmlAll(Writer w, String title, Iterator bean, boolean indent) {
  	try {
			if (bean!=null) {
	  		if (title!=null && title.length()>0) {
					w.write(title);
		    	w.write("\r\n");
	  		}
				BeanTag b=null;
		    while (bean.hasNext()) {
		    	b = (BeanTag)bean.next();
		    	b.writeHtmlAll(w, indent);
		    }
			}
	    w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
  }

  private static void writeHashtableCorrectAuto(String path, String title, List lBean) {
  	Hashtable hBeanBegin = makeHashtableBegin(lBean);
  	Hashtable hBeanEnd = makeHashtableEnd(lBean);
  	List list = new ArrayList();
  	addCorrectAuto(list, hBeanBegin, hBeanEnd);
  	addCorrectAuto(list, hBeanEnd, hBeanBegin);
  	sortHashtableTreeBean (list);
  	writeHashtableTree (path, title, list);
  }
  
  private static void addCorrectAuto(List lBean, Hashtable hBean1, Hashtable hBean2) {
  	// Iteration sur les Level
  	Iterator itL1 = hBean1.keySet().iterator();
  	while (itL1.hasNext()) {
  		String level = (String)itL1.next();
  		Hashtable h1 = (Hashtable)hBean1.get(level);
    	// Iteration sur les Name
    	Iterator itN1 = h1.keySet().iterator();
    	while (itN1.hasNext()) {
    		String name = (String)itN1.next();
    		BeanTag b1 = (BeanTag)h1.get(name);
    		if (b1.isAutomatic() || b1.isCorrected()) {
    			Hashtable h2 = (Hashtable)hBean2.get(level);
    			if (h2!=null) {
    				BeanTag b2 = (BeanTag)h2.get(name);
      			if (b2!=null) {
      				if (b1.isEnd()) {
      					lBean.add(b2);
      					lBean.add(b1);
      				}
      			}
    			}
    		}
    	}
  	}
  }

  private static void sortHashtableTreeBean (List lBean) {
		if (lBean!=null && !lBean.isEmpty()) {
	    // Sort hashtable. 
	    Comparator comp = new Comparator() {
	
				public int compare(Object o1, Object o2) {
					int ret = 0;
					if (o1 instanceof BeanTag)
						ret = (((BeanTag)o1).equalsLevel((BeanTag)o2) ? 0 : 1);
					else
						ret = (o1.equals(o2) ? 0 : 1);
					if (ret!=0) {
						ret = 0;
						String s1 = o1.toString();
						String s2 = o2.toString();
						if (o1 instanceof BeanTag) {
							s1 = ((BeanTag)o1).getLevel();
							s2 = ((BeanTag)o2).getLevel();
						}
						String[] l1 = s1.split("-");
						String[] l2 = s2.split("-");
						int i1 = 0, i2 = 0;
						int len = (l1.length<l2.length) ? l1.length : l2.length;
						for (int i=0 ; i<len && ret==0 ; i++) {
							i1 = Integer.parseInt(l1[i]);
							i2 = Integer.parseInt(l2[i]);
							ret = (i1==i2 ? 0 : i1-i2);
						}
						if (ret==0)
							ret = l1.length-l2.length;
					}
					return ret;
				}
	    	
	    };
	    Collections.sort(lBean,comp) ; 
		}
  }

  /**
   * Return a List of BeanTag element from str text
   * @param str
   * @return List of BeanTag element
   */
  public static List extractHashTag(String str)
  {
    String szTag = "";
    String szTagName = "";
    String szTxt = "";
    // Indique si on est dans un commentaire
    boolean bCommentaire = false;
    // Indique si on est dans un tag
    boolean bTag = false,  bTagName = false;
    // Indique si on est dans un text Data
    boolean bTxtData = false;
    // Caracteres lu dans str
    char c = 0;
    // Anciens caracteres lu dans str (cOld1 = -1, cOld2 = -2...)
    char cOld1 = 0, cOld2 = 0;
    // Ancienne variable ajoute dans stb (cStbOld1 = -1, cStbOld2 = -2...)
    int strLength = str.length();
    List ret = new ArrayList();

    try {
	    for (int i = 0; i < strLength; i++) {
	      cOld2 = cOld1;
	      cOld1 = c;
	      c = str.charAt(i);
	      if ( bCommentaire ) {

	      	if (bTag)
	      		szTag += c;
	      	else
	      		szTxt += c;

	      	if (bTagName)
	      		szTagName += c;

          // Teste si c'est la fin d'un commentaire de la forme A- bla bla bla -->
          if (cOld1=='-' && cOld2=='-' && c=='>') {
  	      	ret.add(new BeanTag(Integer.toString(ret.size()+1), szTagName, szTag, szTxt));
		        szTagName = "";
		        szTag = "";
		        szTxt = "";
            bCommentaire = false;
            bTagName = false;
            bTag = false;
          }
          // Teste si c'est un DocType
          else if (szTagName.toUpperCase().equals("DOCTYPE")) {
            bCommentaire = false;
	          bTagName = false;
	          bTag = true;
	          szTag += szTagName;
            szTxt = "";
          }
          // Teste si le début de la balise de debut
          else if (bTagName && szTagName.equals("--")) {
          	bTagName = false;
	          bTag = true;
	          szTag += szTagName;
            szTxt = "";
          }
	      }
	      else if ( bTxtData ) {
	      	szTxt += c;
	      	if (c == '>' && cOld1 == ']' && cOld2 == ']') {
	      		bTxtData = false;
	      	}
	      }
	      else {
	      	if ( cOld1 == '<' ) {
	      		if (("abcdefghijklmnopqrstuvwxyz".indexOf(c)<0) &&
	      				("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(c)<0) &&
	      				(c!='/')) {
	      			bTag = false;
	      		}
	      		else {
			        if (szTxt.length()>0)
			        	// Supprime le dernier caractère ('<') du text car c'est un début de tag 
			        	szTxt = szTxt.substring(0, szTxt.length()-1);

		          bTag = true;
		          bTagName = true;

			      	if (!szTagName.equals("")) {
		  	      	ret.add(new BeanTag(Integer.toString(ret.size()+1), szTagName, szTag, szTxt));
		        	}

			        szTagName = "";
			        szTag = "";
			        szTxt = "";

	      			szTag += cOld1;
	      		}
	      	}

	      	if ( bTag ) {
	          szTag += c;
	        }
	        else {
	          szTxt += c;
	        }

	      	if ( bTagName && c!='<' && c!='>' && c!='/' && c!='!') {
	      		szTagName += c;
	      	}

		      if ( c == '-' && (cOld1=='!') && (cOld2=='<')) {
	        	bCommentaire = true;
	        	/*
	        	if (!szTagName.equals("")) {
	  	      	ret.add(new BeanTag(Integer.toString(ret.size())+1, szTagName, szTag, szTxt));
	        	}
	        	*/
		        szTagName = "-";//"";
		      	szTag = "<!-";
		        szTxt = "";
	          bTag = false;
	          bTagName = true;
		      }
		      else if ( c == '[') {
		      	bTxtData = szTxt.endsWith("<![CDATA[");
		      }
		      else if ( c == '>' ) {
		      	bTag = false;
		      	bTagName = false;
		      }
		      else if ( c == ' ' ) {
	          bTagName = false;
		      }
	      }
	    }
    	if (!szTagName.equals("")) {
      	ret.add(new BeanTag(Integer.toString(ret.size())+1, szTagName, szTag, szTxt));
    	}
    }
    finally {

      szTag = null;
      szTagName = null;
      szTxt = null;

    	System.gc();
    	System.gc();
    }
    return ret;
  }

  public static String cleanHtml(String str)
  {
    Vector vctList = new Vector();
    vctList.add("font");
    vctList.add("/font");
    vctList.add("b");
    vctList.add("/b");
    str = FxHtml.deleteTag(str, vctList);
    str = FxString.replaceString(str, "       ", "");
    str = FxString.replaceString(str, "      ", "");
    str = FxString.replaceString(str, "     ", "");
    str = FxString.replaceString(str, "    ", "");
    str = FxString.replaceString(str, "   ", "");
    str = FxString.replaceString(str, "  ", "");
    str = FxString.replaceString(str, "> ", ">");
    str = FxString.replaceString(str, " <", "<");
    str = FxString.replaceString(str, "\n", "");
    str = FxString.replaceString(str, "\r", "");
    str = FxString.replaceString(str, "\t", "");
    str = FxString.replaceString(str, "&nbsp;", " ");
    str = FxString.replaceString(str, "&#160;", " ");

    System.gc();

    return str;
  }

  private static File getFile(String name, String path) {
  	return getFile(name, path, "txt");
  }

  private static File getFile(String name, String path, String extention) {
  	File ret = new File(path+Long.toString(Calendar.getInstance().getTimeInMillis())+"-"+name+"."+extention);
  	while(ret.exists()) {
  		try {
  			Thread.sleep(1000); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ret = new File(path+Long.toString(Calendar.getInstance().getTimeInMillis())+"-"+name+"."+extention);
  	}
  	return ret;
  }
}