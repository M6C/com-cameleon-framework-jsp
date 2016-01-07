package ressource.jsp.multilangue.upload;

import java.io.File;
import java.util.Vector;
import ressource.jsp.directory.Jsp_Directory;
import ressource.jsp.formulaires.Jsp_Formulaires;
import ressource.jsp.libelles.Jsp_Libelles;

/**
 * Titre :        MultiLangue
 * Description :  Gestion du multi langue des images des pages de présentation.
 * Copyright :    Copyright (c) 2001
 * Société :      Groupe ressource
 * @author FGA
 * @version 1.0
 */

public class Jsp_Multilangue {

  /** directory */
  private Jsp_Directory dir = new Jsp_Directory();

  /** formulaire */
  private Jsp_Formulaires form = new Jsp_Formulaires();

  /** libellé */
  private Jsp_Libelles lib = new  Jsp_Libelles();


  /**
   * @param pImageDir Le répertoire des images
   * @return Vector
   */
	public Vector jsp_getCategoriesImages(String pImageDir) {
		Vector res = new Vector();

		File file2 = new File(pImageDir);
		File[] fileTab = file2.listFiles();

    if (fileTab != null) {
      for (int i =0; i<fileTab.length; i++) {
        File fileTemp = fileTab[i];
        if (fileTemp.isDirectory()) {
          Vector vecTemp = new Vector();
          vecTemp.addElement(fileTemp.getName());
          vecTemp.addElement(fileTemp.getName());
          res.addElement(vecTemp);
        }
      }
    }

		return res;
	}


  /**
   * @param pRoot Le chemin des images
   * @param pRootWeb Le chemin des images sur le web
   * @return Vector
   */
	public Vector jsp_getImages(String pRoot, String pRootWeb) {

		StringBuffer res = new StringBuffer();

		File fileImage = new File(pRoot);
		File[] fileTab = fileImage.listFiles();

		for(int i=0;i<fileTab.length;i++) {
			File fileTemp = fileTab[i];

			//on regarde si le fichier est de type gif ou jpeg
			if ((fileTemp.toString().indexOf(".gif")>-1) || (fileTemp.toString().indexOf(".jpg")>-1)) {
				res.append("<tr>");
				// on affiche l'image et a cote le champ upload.
				res.append("<td><img src=\"" + pRootWeb + "/" + fileTemp.getName() + "\" border=\"0\" alt=\"" + fileTemp.getName() + "\"></td>");
				res.append("<td>" + form.jsp_CtFile("" + i, "20", "onBlur=\"check(this,\'" + fileTemp.getName().substring(fileTemp.getName().length()-3) + "\')\"") + "</td>");
				res.append("</tr>");
			}
		}

		Vector vecRes = new Vector();
		vecRes.addElement(res.toString()); // le tableau html
		vecRes.addElement(fileTab); // les chemins des fichiers

		return vecRes;
	}

  }