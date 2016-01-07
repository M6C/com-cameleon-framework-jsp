package ressource.hashtable.exceptions;

/**
 * Definit les propriete d'une page (Niveu securite,Visible ou non,ordre Affichage,etc)
 * @author ressource
 * @version 1.0
 */

public class ExceptionsNull extends ressource.hashtable.exceptions.Exceptions {

	/** message */
	private String message ="";

	/**
	 * Constructeur
	 */
  public ExceptionsNull() {
  }


	/**
	 * Constructeur
	 * @param param  message d'erreur
	 */
	public ExceptionsNull(String param) {
		this.message = " Erreur dans le package ressource.hashtable.Menu \n Le chemin de l'image du menu " + param +" est \"null\" ";
	}


	/**
	 * Libellé du message erreur.
	 * @return la chaine de caractères contenant le message erreur.
	 */
	public String toString() {
		return this.message;
	}

}