package ressource.jsp.securite;

/**
 * Package qui contient les methode gérant la sécurité.
 * Ensemble des méthodes servant à gérer la sécurité
 * @author ressource
 * @version 1.0
 **/


public class Jsp_Securite{


	/**
	 * Constructeur
	 */
	public Jsp_Securite() {
	}


	/**
	 * Confirme ou non si l'utilisateur a le droit de voir ce repertoire
	 * @param nonRepertoire  Nom du repertoire a verifier
	 * @param hashDroit      Hashtable contenant l'ensemble des repertoire et de leur droit
	 * @param userDroit      Code droit de l'utilisateur.
	 * @return true si l'utilisateur a les droits sinon false (par defaut true)
	 */
	public boolean confirmeDroit(String nomRepertoire, java.util.Hashtable hashDroit, String userDroit) {
		boolean droit = true;

		if (hashDroit.containsKey(nomRepertoire)) {
			String niveauSecurite = hashDroit.get(nomRepertoire).toString();
			if (userDroit.compareTo(niveauSecurite) < 0) {
				droit = false;
			}
		}

		return droit;
	}

}