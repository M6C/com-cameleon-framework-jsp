package ressource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Old_FxHtml_Ok1 {

  protected Old_FxHtml_Ok1() {
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
      boolean bGuillemet = (boolean)(str.charAt(iDeb) == '"');
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
  public static String indentString(String str)
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
    Hashtable htTag = new Hashtable();

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
   * Return an Indented String from str
   * @param str
   * @return
   */
  public static String old_indentString(String str)
  {
  	String ret = null;
    StringBuffer stb = new StringBuffer("");
    StringBuffer szTag = new StringBuffer("");
    String szIndente = "    ";
    String szRetour = "\r\n";
    int iIndent = 0;
    boolean bTag = false;
    boolean bCommentaire = false;
    boolean bContinue = true;
    char car, c;

    try {
	    for (int i = 0; i < str.length()-1; i++)
	    {
	      bContinue = true;
	      c = str.charAt(i);
	      if ( bCommentaire )
	      {
	          bTag = false;
	          // Teste si c'est la fin d'un commentaire de la forme <!-- bla bla bla -->
	          if (( c=='>' ) && (str.length()>3) && ( str.charAt(i-1)=='-' ) && ( str.charAt(i-2)=='-' ))
	            bCommentaire = false;
	      }
	      else if ( c == '<' )
	      {
	        if ( (str.charAt(i+1)=='/') && (iIndent>0) )
	          iIndent--;
	
	        // Teste si c'est le début d'un commentaire de la forme <!-- bla bla bla -->
	        bCommentaire = (str.charAt(i+1)=='!');
	
	        // Supprime tout les caractères superflu avant l'ouverture d'une balise
	        while((stb.length()>0) && bContinue)
	        {
	          car = stb.charAt(stb.length()-1);
	          switch( car )
	          {
	            case ' ': case '\n': case '\r': case '\t':
	              stb.deleteCharAt(stb.length()-1);
	              break;
	            default:
	              bContinue = false;
	          }
	        }
	
	        stb.append(szRetour);
	        for (int j=0; j<iIndent; j++)
	          stb.append(szIndente);
	
	          bTag = true;
	      }
	      else if ( c == '>' )
	      {
	        if ( (str.indexOf("</"+szTag.toString().toLowerCase(), i)>=0) || (str.indexOf("</"+szTag.toString().toUpperCase(), i)>=0) )
	          iIndent++;
	        bTag = false;
	        szTag = new StringBuffer("");
	        // Passe tous les caractères superflu après la fin d'une balise
	        while((i<str.length()-1) && bContinue)
	        {
	          car = str.charAt(i+1);
	          switch( car )
	          {
	            case ' ': case '\n': case '\r': case '\t':
	              i++;
	              break;
	            default:
	              bContinue = false;
	          }
	        }
	      }
	      else
	      {
	        if ( (i>0) && (!bTag) && (stb.length()>0) && (stb.charAt(stb.length()-1)=='>') )
	        {
	          stb.append(szRetour);
	          for (int j=0; j<iIndent; j++)
	            stb.append(szIndente);
	        }
	        else if ( bTag && (c==' ') )
	          bTag = false;
	        else if ( bTag )
	          szTag.append(c);
	      }
	      switch ( c )
	      {
	        case '\n': case '\r': case '\t':
	          break;
	        case ' ':
	        {
	          switch( str.charAt(i+1) )
	          {
	            case ' ': case '\n': case '\r': case '\t':
	              break;
	            default:
	              stb.append(c);
	          }
	          break;
	        }
	        default:
	          stb.append(c);
	      }
	    }
    }
    finally {
      ret = stb.toString();

      stb = null;
      szTag = null;
      szIndente = null;
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

  	Hashtable hashTag = extractHashTag(str);
    System.out.println("============= extractHashTagCount");
    Enumeration e = hashTag.keys();
    while (e.hasMoreElements()) {
    	System.out.println(hashTag.get(e.nextElement()));
    }

    System.out.println("============= extractHashTagCount");
    Hashtable hashTagCount = extractHashTagCount(hashTag);
    //showHashtableTree (hashTagCount);
    writeHashtableTree ("/", "============= extractHashTagCount", hashTagCount);

    System.out.println("============= sortHashtableTree hashTagCount");
    Collection keyTagCountSort = sortHashtableTree (hashTagCount);
    //showHashtableTree (keyTagCountSort, hashTagCount);
    writeHashtableTree ("/", "============= sortHashtableTree hashTagCount", keyTagCountSort, hashTagCount);

  	System.out.println("============= cleanEmptyTag");
    Hashtable hashTagCountClean = cleanEmptyTag(hashTagCount);
    //showHashtableTree (hashTagCountClean);
    writeHashtableTree ("/", "============= cleanEmptyTag", hashTagCountClean);

    System.out.println("============= sortHashtableTree hashTagCountClean");
    Collection keyTagCountCleanSort = sortHashtableTree (hashTagCountClean);
    //showHashtableTree (keyTagCountCleanSort, hashTagCountClean);
    writeHashtableTree ("/", "============= sortHashtableTree hashTagCountClean", keyTagCountCleanSort, hashTagCountClean);

    return str;
  }

  /**
   * Return an corrected String from str
   * Correction :
   *  - Add missing end tag
   * @param str
   * @return
   */
  public static Hashtable extractHashTagCount(Hashtable hashTag) {
    Hashtable treeTagCount = null;
    if (hashTag!=null && !hashTag.isEmpty()) {
	    // Indique si on est dans un tag de fin
	    boolean bEndTag = false, bEndTagOld = false;
	    boolean bBeginEnd = false;
	    List lTag = (List)hashTag.get("lTag");
	    List lTagName = (List)hashTag.get("lTagName");
	    List lTxt = (List)hashTag.get("lTxt");
	    if (lTag!=null && !lTag.isEmpty()) {
		    Hashtable hashTagCount = new Hashtable();
		    String szTag = null, szTagName = null;
		    int len = lTag.size();
		    int iLevel = 0;
		    String levelCurrent = new Integer(iLevel).toString();
		
		    // Liste des tags pour les quels une fin de tag n'est pas obligatoire
		    List lNoMandatoryEnd = getListTagNoMandatoryEnd();
		
		    boolean bEndNoMandatory = false;
		
		    treeTagCount = new Hashtable();
		    try {
			    for (int i = 0 ; i < len ; i++) {
			    	szTag = (String)lTag.get(i);
			    	szTagName = (String)lTagName.get(i);
			    	// Verifi si on est sur un tag de début et de fin
			    	bBeginEnd = szTag.endsWith("/>");
			    	// Verifi si une fin de tag est obligatoire
			    	bEndNoMandatory = lNoMandatoryEnd.contains(szTagName.toUpperCase());
			    	// Cas où on n'est pas sur un tag de debut et de fin
			    	// et qu'une fin de tag est obligatoire
			    	if (!bBeginEnd && !bEndNoMandatory) {
				    	// Indique si l'ancien tag est un tag de fin
				    	bEndTagOld = bEndTag;
				    	// Verifi si on est sur un tag de fin
				    	bEndTag = szTag.startsWith("</");
			    		// Cas où on est sur un tag de début
			    		if (!bEndTag) {
			    			iLevel = 0;
			    			// Cherche si il y a déjà une table pour ce niveau
			    			hashTagCount = (Hashtable)treeTagCount.get(levelCurrent + "-" + iLevel);
			    			if (hashTagCount!=null) {
				    			// Boucle tant qu'une liste est trouvé pour le niveau
				    			while (hashTagCount!=null) {
				    				// Récupère la liste et incremente le niveau à chaque fois qu'une liste est trouvee
				    				hashTagCount = (Hashtable)treeTagCount.get(levelCurrent + "-" + (++iLevel));
				    			}
			    			}
		    				levelCurrent += "-" + iLevel;
			    			// Cree une nouvelle liste
		    				hashTagCount = new Hashtable();
		    				// Strock la liste dans l'arbre
		    				treeTagCount.put(levelCurrent, hashTagCount);
		  					// Modifi le compteur de tag ouvert (Incremente)
		  		    	incrementHashCount(hashTagCount, szTagName, bEndTag);
			    		}
			    		// Cas où on est sur un tag de fin
			    		else {
			    			// Verifi si le tag precedent etait un tag de fin
			    			if (bEndTagOld)
				    			// Recupère l'ancienne liste 
				    			hashTagCount = (Hashtable)treeTagCount.get(levelCurrent);
				    		// Passe au niveau inferieur
			    			levelCurrent = levelCurrent.substring(0, levelCurrent.lastIndexOf("-"));
			    			if (levelCurrent.indexOf("-")>0) {
			    				iLevel = Integer.parseInt(levelCurrent.substring(levelCurrent.lastIndexOf("-")+1))-1;
			    			}
			    			else {
			    				iLevel = Integer.parseInt(levelCurrent)-1;
			    			}
			    			if (iLevel<0)
			    				iLevel = 0;
		  					// Modifi le compteur de tag ouvert (Decremente)
		  		    	incrementHashCount(hashTagCount, szTagName, bEndTag);
				    	}
			    	}
			    }
		    }
		    finally {
		      lTag = null;
		      lTagName = null;
		      lTxt = null;
		      szTag = szTagName = null;
		
		      System.gc();
		    	System.gc();
		    }
	    }
    }
    return treeTagCount;
  }

  /**
   * Return an corrected String from str
   * Correction :
   *  - Add missing end tag
   * @param str
   * @return
   */
  public static Hashtable old_extractHashTagCount_Ok(Hashtable hashTag) {
  	//String ret = null;
  	//StringBuffer ret = new StringBuffer();
    // Indique si on est dans un tag de fin
    boolean bEndTag = false, bEndTabOld1 = false;
    boolean bBeginEnd = false;
    List lTag = (List)hashTag.get("lTag");
    List lTagName = (List)hashTag.get("lTagName");
    List lTxt = (List)hashTag.get("lTxt");
    Hashtable hashTagCount = new Hashtable();
    Hashtable treeTagCount = new Hashtable();
    String szTag = null, szTagName = null, szTxt = null;
    String szTagOld1 = null, szTagNameOld1 = null;
    int len = lTag.size();
    int iLevel = 0;
    int iCntBegin = 0;
    String levelCurrent = new Integer(iLevel).toString();
    String levelNext = levelCurrent;

    // Liste des tags pour les quels une fin de tag n'est pas obligatoire
    List lNoMandatoryEnd = getListTagNoMandatoryEnd();
    Hashtable hashTagImpliciteEnd = getListTagImpliciteEnd();

    boolean bEndNoMandatory = false;

    try {
	    for (int i = 0 ; i < len ; i++) {
	    	szTag = (String)lTag.get(i);
	    	szTagName = (String)lTagName.get(i);
	    	szTxt = (String)lTxt.get(i);
	    	// Verifi si on est sur un tag de fin
	    	bEndTag = szTag.startsWith("</");
	    	// Verifi si on est sur un tag de début et de fin
	    	bBeginEnd = szTag.endsWith("/>");
	    	// Verifi si une fin de tag est obligatoire
	    	bEndNoMandatory = lNoMandatoryEnd.contains(szTagName.toUpperCase());
	    	// Cas où on n'est pas sur un tag de debut et de fin
	    	// et qu'une fin de tag est obligatoire
	    	if (!bBeginEnd && !bEndNoMandatory) {
		    	// Précédent tag
	    		szTagOld1 = (String)lTag.get(i-1);
		    	// Précédent nom de tag
	    		szTagNameOld1 = (String)lTagName.get(i-1);
		    	// Verifi si le précédent tag est un tag de fin
	    		bEndTabOld1 = (szTagOld1.indexOf("</")==0);
	    		// Cas où on est sur un tag de début
	    		if (!bEndTag) {
		    		// Passe au niveau superieur
	    			levelNext = new Integer(++iLevel).toString();
	    			// Cherche si il y a déjà une table pour ce niveau
	    			hashTagCount = (Hashtable)treeTagCount.get(levelNext);
	    			if (hashTagCount!=null) {
		    			int ii=1;
		    			// Boucle tant qu'une liste est trouvé pour le niveau
		    			while (hashTagCount!=null) {
		    				// Incremente le niveau de 10 à chaque fois qu'une liste est trouvee
		    				//levelCurrent = new Integer(iLevel+(10*ii++)).toString();
		    				levelCurrent = iLevel+"-"+(ii++);
		    				// Récupère la liste
		    				hashTagCount = (Hashtable)treeTagCount.get(levelCurrent);
		    			}
	    			}
	    			else {
	    				// Sauvegarde le niveau courant
	    				levelCurrent = levelNext;
	    			}
	    			// Cree une nouvelle liste
    				hashTagCount = new Hashtable();
    				// Strock la liste dans l'arbre
    				treeTagCount.put(levelCurrent, hashTagCount);
	    			// Cas où le précédent tag est aussi un tag de debut identique au tag en cours
	    			if (!bEndTabOld1 && szTagName.toUpperCase().equals(szTagNameOld1.toUpperCase())) {
	  	    		// Recupère le nombre de tag de debut Ouvert
	  		    	iCntBegin = getHashCountValueBegin(hashTagCount, szTagName);
	    				for (int j=0 ; j<iCntBegin ; j++) {
		    				// Ajout un tag de fin
		    				//ret.append("<!--Malformed correction - Missing end tag--></"+szTagName+">");
		    				// Modifi le compteur de tag ouvert (Decremente)
		    	    	incrementHashCount(hashTagCount, szTagName, true);
	    				}
	    			}
	    			/*
	    			else {
	    				// Modifi le compteur de tag ouvert (Decremente)
	    	    	incrementHashCount(hashTagCount, szTagName, bEndTag);
	    			}
	    			*/
	    		}
	    		// Cas où on est sur un tag de fin
	    		else {
	    			// Cherche si il y déjà une table pour ce niveau
	    			hashTagCount = (Hashtable)treeTagCount.get(levelCurrent);
	    			// Verifi si une liste est trouvé
	    			if (hashTagCount==null) {
		    			// Cree une nouvelle liste
	    				hashTagCount = new Hashtable();
	    				// Strock la liste dans l'harbre
	    				treeTagCount.put(levelCurrent, hashTagCount);
	    			}
	    			// Cas où le précédent tag est aussi un tag de fin identique au tag en cours
	    			if (bEndTabOld1 && szTagName.toUpperCase().equals(szTagNameOld1.toUpperCase())) {
	    				// Ajout un tag de début
	    				//ret.append("<!--Malformed correction - Missing begin tag--><"+szTagName+">");
	    				// Modifi le compteur de tag ouvert (Incremente)
	    	    	incrementHashCount(hashTagCount, szTagName, false);
	    			}
	    			// Sauvegarde le niveau courant
	    			levelNext = new Integer(iLevel).toString();
		    		// Passe au niveau inferieur
	    			levelCurrent = new Integer(--iLevel).toString();
		    	}
					// Modifi le compteur de tag ouvert
		    	// (Incremente ou Decremente en fonction de bEndTag)
		    	incrementHashCount(hashTagCount, szTagName, bEndTag);
	    	}
	    	// Concatène les tags + textes
	    	//ret.append(szTag).append(szTxt);
	    }
    }
    finally {
      lTag = null;
      lTagName = null;
      lTxt = null;
      szTag = szTagName = szTxt = null;
      szTagOld1 = null;

      System.gc();
    	System.gc();
    }
    return treeTagCount;
  }

  private static void addClosingTag(Collection keySort, Hashtable treeTagCount, Hashtable treeTagCountClean) {
  	Hashtable ret = null;
  	if (treeTagCount!=null && treeTagCountClean!=null) {
  		List lstKey = new ArrayList();
  		List lstTag = new ArrayList();
  		List lstCnt = new ArrayList();
  		ret = new Hashtable();
    	Hashtable hashTag = new Hashtable();
  		Object k1=null, k2=null;
  		Hashtable t1=null;
  		Integer cnt=null;
      Iterator itKey1 = keySort.iterator();
      while (itKey1.hasNext()) {
      	k1 = itKey1.next();
      	t1 = (Hashtable)treeTagCount.get(k1);
      	if (t1!=null && !t1.isEmpty()) {
  	      Enumeration enumKey2 = t1.keys();
  	      while (enumKey2.hasMoreElements()) {
  	      	k2 = enumKey2.nextElement();
  	      	cnt = (Integer)t1.get(k2);
  	      	if (cnt.intValue()>0) {
  	      		
  	      	}
  		    }
      	}
      }
  	}
  }

  private static void old_addClosingTag(String tagName, Hashtable treeTagCount, Hashtable hashTagImpliciteEnd, String levelCurrent, StringBuffer ret) {
  	List listTag = new Vector();
  	listTag.add(tagName);
  	// Recupere la liste des tag avec le compteur pour le niveau courant
  	Hashtable hashTagCount = (Hashtable)treeTagCount.get(levelCurrent);
  	// Compteur pour le tag en cours
  	String szTagCount = (String)hashTagCount.get(tagName);
  	int tagCount = Integer.parseInt(szTagCount);
  	String tag = null;
  	// Parcours la liste des tag à traiter
  	// (qui sera alimenté tout au long tu traitement pour eviter de faire du recursif)
  	while(listTag.size()>0) {
  		tag = (String)listTag.get(listTag.size()-1);
  		if (hashTagImpliciteEnd.containsKey(tag)) {
  			// Liste des tag que dois fermer le tag en cours
  			Hashtable hashTag = (Hashtable)hashTagImpliciteEnd.get(tag);
  		}
  		// Supprime de la liste le tag en cours
  		listTag.remove(listTag.size()-1);
  	}
  }

  private static Hashtable cleanEmptyTag(Hashtable treeTagCount) {
  	Hashtable ret = null;
  	if (treeTagCount!=null && !treeTagCount.isEmpty()) {
  		ret = new Hashtable();
    	Hashtable hashTag = null;
  		Object k1=null, k2=null;
  		Object o1=null;
      Enumeration enumKey1 = treeTagCount.keys();
      while (enumKey1.hasMoreElements()) {
      	k1 = enumKey1.nextElement();
      	Hashtable t = (Hashtable)treeTagCount.get(k1);
      	if (t!=null && !t.isEmpty()) {
        	hashTag = new Hashtable();
  	      Enumeration enumKey2 = t.keys();
  	      while (enumKey2.hasMoreElements()) {
  	      	k2 = enumKey2.nextElement();
  	      	o1 = t.get(k2);
  	      	if (o1!=null && Integer.parseInt(o1.toString())!=0) {
  	      		hashTag.put(k2, o1);
  	      	}
  		    }
  	      if (!hashTag.isEmpty()) {
  	      	ret.put(k1, hashTag);
  	      }
      	}
      }
  	}
  	return ret;
  }

  private static void showHashtableTree (Hashtable tree) {
		if (tree!=null) {
			showHashtableTree(tree.keySet(), tree);
		}
  }

  private static void showHashtableTree (Collection key, Hashtable tree) {
		Object k1=null, k2=null;
		if (tree!=null) {
			Iterator itKey1 = key.iterator();
	    while (itKey1.hasNext()) {
	    	k1 = itKey1.next();
	    	Hashtable t = (Hashtable)tree.get(k1);
	    	System.out.print("key:" + k1.toString() + " size:" + t.size());
	    	if (t.size()>0) {
		      Enumeration enumKey2 = t.keys();
		      while (enumKey2.hasMoreElements()) {
			    	k2 = enumKey2.nextElement();
			    	System.out.print(" [" + k2.toString() + "," + t.get(k2) + "]");
			    }
	    	}
	    	System.out.println("");
	    }
		}
  }

  private static void writeHashtableTree (String path, String title, Hashtable tree) {
		if (tree!=null) {
			writeHashtableTree(path, title, tree.keySet(), tree);
		}
  }

  private static void writeHashtableTree (String path, String title, Collection key, Hashtable tree) {
  	if (!path.endsWith("/") && !path.endsWith("\\"))
  		path += "/";
  	try {
    	File file = new File(path+Long.toString(Calendar.getInstance().getTimeInMillis())+".txt");
			FileWriter fw = new FileWriter(file);
			fw.write(title);
    	fw.write("\r\n");
			Object k1=null, k2=null;
			if (key!=null && !key.isEmpty() && tree!=null && !tree.isEmpty()) {
				Iterator itKey1 = key.iterator();
		    while (itKey1.hasNext()) {
		    	k1 = itKey1.next();
		    	Hashtable t = (Hashtable)tree.get(k1);
		    	fw.write("key:" + k1.toString() + " size:" + t.size());
		    	if (t.size()>0) {
			      Enumeration enumKey2 = t.keys();
			      while (enumKey2.hasMoreElements()) {
				    	k2 = enumKey2.nextElement();
				    	fw.write(" [" + k2.toString() + "," + t.get(k2) + "]");
				    }
		    	}
		    	fw.write("\r\n");
		    }
			}
	    fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

  private static Collection sortHashtableTree (Hashtable tree) {
  	Collection ret = null;
		if (tree!=null && !tree.isEmpty()) {
	    // Sort hashtable. 
			Vector v = new Vector ( tree.keySet()  ) ;
	    Comparator comp = new Comparator() {
	
				public int compare(Object o1, Object o2) {
					//return new Integer(o1.toString()).intValue()-new Integer(o2.toString()).intValue();
					int ret = (o1.equals(o2) ? 0 : 1);
					if (ret!=0) {
						ret = 0;
						String s1 = o1.toString();
						String s2 = o2.toString();
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
	    Collections.sort (v,comp) ; 
	    ret = v;
		}
		return ret;
  }

  private static List getListTagNoMandatoryEnd() {

    // Liste des tags pour les quels une fin de tag n'est pas obligatoire
    List ret = new ArrayList();
    ret.add("BR");
    ret.add("IMG");
    ret.add("INPUT");
    ret.add("META");
    ret.add("LINK");
    ret.add("DOCTYPE");
    // Commentaire
    ret.add("--");
    
    return ret;
  }

  private static Hashtable getListTagImpliciteEnd() {

    // Liste des tags avec leur fin de tag implicite
  	Hashtable ret = new Hashtable();
  	List listEndTag = null;
  	String tagName = null;

  	/**
  	 * TABLE
  	 */
  	tagName = "TABLE";
  	listEndTag = new ArrayList();
  	listEndTag.add("TR");
    ret.put(tagName, listEndTag);

  	/**
  	 * TR
  	 */
  	tagName = "TR";
  	listEndTag = new ArrayList();
  	listEndTag.add("TD");
    ret.put(tagName, listEndTag);

  	/**
  	 * TD
  	 */
  	tagName = "TD";
  	listEndTag = new ArrayList();
  	listEndTag.add("SPAN");
  	listEndTag.add("DIV");
  	listEndTag.add("FONT");
  	listEndTag.add("A");
  	listEndTag.add("B");
  	listEndTag.add("APPLET");
  	listEndTag.add("AREA");
  	listEndTag.add("A");
  	listEndTag.add("B");
  	listEndTag.add("CENTER");
  	listEndTag.add("DIV");
  	listEndTag.add("EM");
  	listEndTag.add("FONT");
  	listEndTag.add("FORM");
  	listEndTag.add("H1");
  	listEndTag.add("H2");
  	listEndTag.add("H3");
  	listEndTag.add("H4");
  	listEndTag.add("H5");
  	listEndTag.add("H6");
  	listEndTag.add("HR");
  	listEndTag.add("IMG");
  	listEndTag.add("INPUT");
  	listEndTag.add("I");
  	listEndTag.add("LI");
  	listEndTag.add("MAP");
  	listEndTag.add("OL");
  	listEndTag.add("OPTION");
  	listEndTag.add("PRE");
  	listEndTag.add("P");
  	listEndTag.add("SELECT");
  	listEndTag.add("SMALL");
  	listEndTag.add("STRIKE");
  	listEndTag.add("STRONG");
  	listEndTag.add("TD");
  	listEndTag.add("TEXTAREA");
  	listEndTag.add("TH");
  	listEndTag.add("TR");
  	listEndTag.add("UL");

  	ret.put(tagName, listEndTag);

    return ret;
  }

  private static void incrementHashCount(Hashtable hashCount, String key, boolean endTag) {
  	int iInc = 0, iCnt = 0;
  	if (endTag) {
  		// Recupere l'ancienne valeur
  		//iCnt = getHashCountValueEnd(hashCount, key);
  		iCnt = getHashCountValueBegin(hashCount, key);
  		// Decremente le compteur
  		iInc = -1;
  		// Ajout le caractère de fin de tag
  		//key = "/" + key;
  	}
  	else {
  		// Recupere l'ancienne valeur
  		iCnt = getHashCountValueBegin(hashCount, key);
  		// Incremente le compteur
  		iInc = 1;
  	}

  	hashCount.put(key.toUpperCase(), new Integer(iCnt+iInc));
  }

  private static int getHashCountValueBegin(Hashtable hashCount, String key) {
  	int ret = 0;
  	if (hashCount!=null && key!=null) {
	  	Integer iCnt = (Integer)hashCount.get(key.toUpperCase());
	  	if (iCnt!=null)
	  		ret = iCnt.intValue();
  	}
  	return ret;
  }
  
  private static Hashtable buildTreeHashCount(Hashtable treeTagCount, String level) {
  	Hashtable ret = (Hashtable)treeTagCount.get(level);
		if (ret==null) {
			ret = new Hashtable();
			treeTagCount.put(level, ret);
		}
		return ret;
  }
/*
  private static int getHashCountValueEnd(Hashtable hashCount, String key) {
  	return getHashCountValueBegin(hashCount, "/" + key);
  }
*/
  /**
   * Return an corrected String from str
   * Correction :
   *  - Add missing end tag
   * @param str
   * @return
   */
  public static Hashtable extractHashTag(String str)
  {
  	Hashtable ret = new Hashtable();
    String szTag = "";
    String szTagName = "";
    String szTxt = "";
    // Indique si on est dans un commentaire
    boolean bCommentaire = false;
    // Indique si on est dans un tag
    boolean bTag = false,  bTagName = false;
    // Caracteres lu dans str
    char c = 0;
    // Anciens caracteres lu dans str (cOld1 = -1, cOld2 = -2...)
    char cOld1 = 0, cOld2 = 0;
    // Ancienne variable ajoute dans stb (cStbOld1 = -1, cStbOld2 = -2...)
    int strLength = str.length();
    List lTag = new ArrayList();
    List lTagName = new ArrayList();
    List lTxt = new ArrayList();

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

          // Teste si c'est la fin d'un commentaire de la forme <!-- bla bla bla -->
          if (cOld1=='-' && cOld2=='-' && c=='>') {
  	      	lTagName.add(szTagName.trim());
  	      	lTag.add(szTag.trim());
  	      	lTxt.add(szTxt.trim());
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
	      else {

	      	if ( c == '<' ) {
	          bTag = true;
	          bTagName = true;

		      	if (!szTagName.equals("")) {
	  	      	lTagName.add(szTagName.trim());
	  	      	lTag.add(szTag.trim());
	  	      	lTxt.add(szTxt.trim());
	        	}

		        szTagName = "";
		        szTag = "";
		        szTxt = "";
		      }
	      	else {
		      	if ( cOld1 == '<' ) {
		          szTag += cOld1;
		      	}

		      	if ( bTag ) {
		          szTag += c;
		        }
		        else {
		          szTxt += c;
		        }
	      	}
	        if ( bTagName && c!='<' && c!='>' && c!='/' && c!='!') {
	          szTagName += c;
	        }

		      if ( c == '!' && (cOld1=='<')) {
	        	bCommentaire = true;
	
	        	if (!szTagName.equals("")) {
	  	      	lTagName.add(szTagName.trim());
	  	      	lTag.add(szTag.trim());
	  	      	lTxt.add(szTxt.trim());
	        	}

		        szTagName = "";
		      	szTag = "<!";
		        szTxt = "";
	          bTag = false;
	          bTagName = true;
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
      	lTagName.add(szTagName.trim());
      	lTag.add(szTag.trim());
      	lTxt.add(szTxt.trim());
    	}
    }
    finally {
    	ret.put("lTagName", lTagName);
    	ret.put("lTag", lTag);
    	ret.put("lTxt", lTxt);
/*
      for (int i=0 ; i<lTag.size() ; i++) {
      	System.out.println(""+lTagName.get(i)+" - "+lTag.get(i)+" - "+lTxt.get(i));
      }
*/
      szTag = null;
      szTagName = null;
      szTxt = null;

    	lTag = null;
    	lTagName = null;
    	lTxt = null;

    	System.gc();
    	System.gc();
    }
    return ret;
  }
  
  private static String repeate(String str, int cnt) {
  	String ret = "";
  	for (int i=0 ; i<cnt ; i++)
  		ret += str;
  	return ret;
  }
}