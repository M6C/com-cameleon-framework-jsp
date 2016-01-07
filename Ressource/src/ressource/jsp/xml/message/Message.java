package ressource.jsp.xml.message;

/**
  * Permet la gestion des messages d'erreurs de l'application
  * @version 1.0
  * @author ressource
  */

import java.util.Vector;
import java.io.File;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Message {

	/** langue des messages */
	private String language = "";

	/** Contient un ensemble de vecteur (id, libellé) */
	public Vector listeMessage = new Vector();

	/** Nombre de message enregistré */
	private int nbMessage = 0;

	/** Message par défaut si le numéro de message n'existe pas */
	protected final static String MESSAGE_NON_TROUVE = "message non trouve.";

	/** le document créé après chargement du fichier XML */
	private Document document = null;

	private final String CST_ITEM_MESSAGE = "message";
	private final String CST_ITEM_ID = "id";

	/**
	 * Constructeur par défaut
	 */
	public Message() {
	}


	/**
	 * Initialiser la liste des messages à partir du fichier passé en paramètre
	 * @param nomFichier  Le nom du fichier contenant la liste des messages
	 * @throws FileNotFoundException  Le fichier n'a pas été trouvé
	 * @throws IOException  Problème d'écriture/lecture avec le fichier
	 * @return Le nombre de message enregistré
	 */
	public int initialiser(String nomFichier, String langue) throws FileNotFoundException, IOException, Exception {

		this.language = langue;
		this.loadXMLDocument(nomFichier);

		// Document menu = don.loadXMLDocument();
		NodeList app = this.document.getDocumentElement().getElementsByTagName( CST_ITEM_MESSAGE );

		for (int i=0;i<app.getLength();i++) {

		  // Je prend les attributs : le répertoire
			NamedNodeMap nd = app.item(i).getAttributes();
			String id = "";
		  for (int k=0;k<nd.getLength();k++) {
				if (nd.item(k).getNodeName().equals(this.CST_ITEM_ID)) {
					id = nd.item(k).getNodeValue();
				}
		  }

		  // On récupère le libellé correspondant à langue utilisée à l'initialisation.
			if (app.item(i).hasChildNodes()) {
				Node nodeLibelle = this.findNote(app.item(i), this.language);
				this.ajouterMessage(id, this.valueNode(nodeLibelle));
			}
		}

		return this.nbMessage;
	}


	/**
	 * Retourner un message d'un numéro donné et en
	 * passant des paramètres au message
	 * @param id  Le numéro du message désiré
	 * @param vec  La liste des paramètres à mettre dans le message
	 * @return Le message trouvé et incluant la liste des paramètres
	 */
	public String getMessage(String id, java.util.Vector vec) {
		return construireMessage(id,vec);
	}


	/**
	 * Retourner un message d'un numéro donné
	 * @param id  le numéro du message à retourner
	 * @return le message trouvé
	 */
	public String getMessage(String id) {
		return construireMessage(id, new java.util.Vector());
	}


	/**
	 * Créer le message à partir d'un numéro et de la liste des paramètres
	 * @param id  Le numéro du message désiré
	 * @param vec  La liste des paramètres à mettre dans le message
	 * @return le message trouvé et incluant la liste des paramètres
	 */
	private String construireMessage(String id, java.util.Vector vec) {
		int loop = 0;
		StringBuffer message = new StringBuffer();

		while ((loop < listeMessage.size())&&(!((Vector)listeMessage.elementAt(loop)).elementAt(0).toString().equals(id))) {
			loop++;
		}

		if (loop < listeMessage.size()) {
			message.append( ((Vector)listeMessage.elementAt(loop)).elementAt(1).toString() );
			for (int k=1;k<=vec.size();k++) {
				int i = message.toString().indexOf("%" + k );
				if (i>-1) {
					message.replace(i, i + String.valueOf(k).length()+1 , vec.elementAt(k-1).toString());
				}
			}
		} else {
			message.append( this.MESSAGE_NON_TROUVE );
		}

		return message.toString();
	}


	/**
	 * Ajouter un message à la liste des messages
	 * @Param	strId  Le numéro du message
	 * @Param libelle  Le libellé du message
	 */
	private void ajouterMessage(String strId, String libelle) {
		Vector v = new Vector();
		v.addElement(strId);
		v.addElement(libelle);
		listeMessage.addElement(v);

		this.nbMessage++;
	}


	/**
	 * Réinitialiser la liste des messages
	 */
	private void viderMessage() {
		for (int k=0;k<this.listeMessage.size();k++) {
			this.listeMessage.removeAllElements();
		}

		this.nbMessage = 0;
	}


	/**
	 * Teste l'état d'un noeud. Si le noeud est null alors retourne chaîne vide.<p>
	 * Sa value sinon.<p>
	 * @return la valeur de la value
	 */
	private String valueNode(Node node) {

		if (node == null) {
			return MESSAGE_NON_TROUVE;
		}

		if (node.getFirstChild() == null) {
			return MESSAGE_NON_TROUVE;
		}

		return node.getFirstChild().getNodeValue();
	}


	/**
	 * Recherche un noeud avec un nom donné.
	 * @param node Le noeud dans lequel il faut chercher.
	 * @param name Le nom du noeud à chercher
	 * @return le noeud trouvé
	 */
	private Node findNote(Node node, String name) {
	  if (node.getNodeName().equals(name)) {
			return node;
	  }

		if (node.hasChildNodes()) {
			NodeList childs = node.getChildNodes();
			int length = childs.getLength();
			for (int i=0; i< length;i++) {
				Node found = this.findNote(childs.item(i), name);
				if (found != null) {
					return found;
				}
			}
		}

		return null;
	}


	/**
	 * Chargement du fichier XML
	 * @param fileName nom du fichier XML à charger.
	 * @throws Exception Liste des erreurs possibles (fichier introuvable...)
	 */
	private void loadXMLDocument(String fileName) throws Exception {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

		this.document = docBuilder.parse(new File(fileName));
		this.document.getDocumentElement().normalize();
	}


	/**
	 * Retourne la langue de l'instance créée
	 * @return String la langue
	 */
	public String getlanguage() {
		return this.language;
	}

}