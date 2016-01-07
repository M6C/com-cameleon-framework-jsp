package ressource.jsp.directory;

/**
 * Package qui contient les fonctions gerant les fichiers
 * et les répertoires du projet.
 */

import java.io.*;
import java.util.*;

/**
	* Liste des fonctions gérant les répertoires et fichiers
	* @author ressource
	*/

public class Jsp_Directory {

	/**
	 * Retourne les repertoires principaux du répertoire racine
	 * @param file3  Fichier qui appelle la fonction jsp_CreateMenu.
	 * @param root  Repertoire racine.
	 * @return Un vecteur contenant les répertoires
	 */
	public Vector jsp_getMenuTitre (File file3,String root) {
		Vector res2 = new Vector();
		String name = (file3.getParentFile()).getName();
		boolean j = true;
		while (j) {
			file3 = file3.getParentFile();
			name = (file3.getParentFile()).getName();
			if (name.compareTo(root)==0){
		  	res2.addElement(file3);
		  	file3 = file3.getParentFile();
		  	File[] temp2 = file3.listFiles();
		  	for (int i=0;i<temp2.length;i++) {
					if (temp2[i].isDirectory() == true) {
				  	res2.addElement(temp2[i]);
					}
				}
		  	j = false;
			}
		}

		return res2;
	}


	/**
	 * Retourne les repertoire frere au fichier "file" <p>
	 * le fichier doit être un file et non pas un directory
	 * @param file  Fichier qui contient le source
	 * @return Un vecteur contenant la liste des répertoires.
	 */
	public Vector jsp_GetMenu(File file) {
		Vector res = new Vector();
		file = file.getParentFile().getParentFile();
		File[] temp = file.listFiles();

		if (temp.length!=0) {
			for (int i=0;i<temp.length;i++) {
				// si on trouve "^" dans le nom du repertoire on garde le repertoire
				if ((temp[i].isDirectory()==true) && ((temp[i].getName()).indexOf("^")>=0)){
					res.addElement(temp[i]);
				}
			}
		}
		return res;
	}


	/**
	 * Retourne les repertoire frere au fichier "file". Le fichier doit etre un
	 * directory
	 * @param file  Fichier qui contient le source
	 * @return Vecteur contenant les repertoire frere au repertoire contenant le file
	 */
	public Vector jsp_GetMenuFils(File file) {
		Vector res = new Vector();
		try {
			file = file.getParentFile();
			file = file.getParentFile();
			File[] temp = file.listFiles();
			if (temp.length!=0) {
				for (int i=0;i<temp.length;i++) {
					// on recupere les repertoire freres qui contienent "^"
					if ((temp[i].isDirectory() == true) && ((temp[i].getName()).indexOf("^")>=0)){
						Vector fils = new Vector();
						// pour chaque repertoire frere valide on recupere ses fils.
						fils = jsp_GetFils(temp[i]);
						fils.insertElementAt(temp[i],0);
						res.addElement(fils);
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Erreur dans la fonction getMenuFils(File file)");
		}
		return res;
	}


	/**
	 * Retourne les repertoire frere au fichier "file". Le fichier doit etre
	 * un directory
	 * @param file  Fichier qui contient le source
	 * @param intLevel  Niveau
	 * @return Vecteur contenant les répertoires frères au repertoire contenant le file
	 */
	public Vector jsp_GetMenuFils(File file,int intLevel) {
		Vector res = new Vector();
		try {
			for(int i=0;i<(intLevel+1);i++){
				file = file.getParentFile();
			}
			File[] temp = file.listFiles();
			if (temp.length!=0) {
				for (int i=0;i<temp.length;i++) {
					// on recupere les repertoire freres qui contienent "^"
					if ((temp[i].isDirectory() == true) && ((temp[i].getName()).indexOf("^")>=0)){
						Vector fils = new Vector();
						// pour chaque repertoire frere valide on recupere ses fils.
						fils = jsp_GetFils(temp[i]);
						fils.insertElementAt(temp[i],0);
						res.addElement(fils);
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Erreur dans la fonction getMenuFils(File file,int intLevel)");
		}
		return res;
	}


	/**
	 * Retourne les sous repertoires au fichier "file".<p>
	 * Donne les sous repertoire du (file) repertoire passe en parametre.
	 * @param file  Fichier qui contient le source
	 * @return Vecteur contenant les sous repertoires fils au repertoire contenant le file
	 */
	public Vector jsp_GetFils(File file) {
		Vector res = new Vector();
		File[] filsList = file.listFiles();

		for (int i=0;i<filsList.length;i++) {
			if (filsList[i].isDirectory() == true) {
				res.addElement(filsList[i]);
			}
		}
		return res;
	}


	/**
	 * Recherche le fichier se trouvant sur le rootName
	 * @param file  fichier sur lequel on se trouve
	 * @param  strRootName répertoire de destination
	 * @return Le file de destination correspondant à strRootName
	 */
	public File getRootFile(File file,String strRootName) {
		File res = file;
		do {
			res = res.getParentFile();
		}
		while((res.getName().compareTo(strRootName)) != 0);

		return res;
	}

}

