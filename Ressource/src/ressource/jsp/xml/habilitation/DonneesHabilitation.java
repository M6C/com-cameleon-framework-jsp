package ressource.jsp.xml.habilitation;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import ressource.jsp.securite.droit.ListeActionsUser;
import java.util.Vector;


/**
 * Titre : Librairies
 * Description : Lecture des habilitations dans un fichier XML.
 * Copyright :    Copyright (c) 2001
 * Société : Groupe ressource
 * @author FGA
 * @version 1.0
 */

public class DonneesHabilitation {

	/** le document créé après chargement du fichier XML */
	private Document document = null;

  /** la liste des droits lue */
  private ListeActionsUser listeActionsUser = null;

  /** une liste de profil du user */
  private Vector listeProfilUser = new Vector();

	/**
	 * Liste des clefs lues dans le fichier XML.<p>
	 * On boucle sur "action".
	 */
	private final String CST_ITEM_ACTION = "action";
	private final String CST_ITEM_ACTION_ID = "id";
	private final String CST_ITEM_ACTION_NOM = "name";
	private final String CST_ITEM_PROFIL = "profil";
	private final String CST_ITEM_PROFIL_ID = "id";
	private final String CST_ITEM_PROFIL_NOM = "name";


  /**
   * Constructeur par défaut
   */
  public DonneesHabilitation() {
  }


	/**
	 * Initialise l'instance créé à partir fu fichier XML donné.
	 * @param pFileMenu nom du fichier XML à traiter.
	 * @param pProfilUser le profil du user.
	 * @throws Exception Liste des erreurs possibles (fichier introuvable...)
	 * @return Le nombre d'éléments lus.
	 */
	public void setInitialiser(String pFileHabilitation, String pProfilUser) throws Exception {

		// On initialise le profil du user
    Vector pProfil = new Vector();
    pProfil.addElement(pProfilUser);
    setListeProfilUser(pProfil);

  	// On charge les habilitations pour le profil user.
		this.loadXMLDocument(pFileHabilitation);
		this.chargerDonneesHabilitation();
	}


	/**
	 * Initialise l'instance créé à partir fu fichier XML donné.
	 * @param pFileMenu nom du fichier XML à traiter.
	 * @param pListeProfilUser les profils du user.
	 * @throws Exception Liste des erreurs possibles (fichier introuvable...)
	 * @return Le nombre d'éléments lus.
	 */
	public void setInitialiser(String pFileHabilitation, Vector pListeProfilUser) throws Exception {

		// On initialise le profil du user
    setListeProfilUser(pListeProfilUser);

  	// On charge les habilitations pour le profil user.
		this.loadXMLDocument(pFileHabilitation);
		this.chargerDonneesHabilitation();
	}


	/**
	 * Chargement du fichier XML
	 * @param pFileName nom du fichier XML à charger.
	 * @throws Exception Liste des erreurs possibles (fichier introuvable...)
	 */
	private void loadXMLDocument(String pFileName) throws Exception {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

		this.document = docBuilder.parse(new File(pFileName));
		this.document.getDocumentElement().normalize();
	}


	/**
	 * Teste l'état d'un noeud. Si le noeud est null alors retourne chaîne vide.<p>
	 * Sa value sinon.<p>
	 * @param pNode le noeud.
	 * @return la valeur de la value
	 */
	private String valueNode(Node pNode) {

		if (pNode == null) {
			return "";
		}

		if (pNode.getFirstChild() == null) {
			return "";
		}

		return pNode.getFirstChild().getNodeValue();
	}


  /**
   * Retourne la liste des actions possibles pour le user.
   * @return ListeActionsUser liste des actions possibles pour le user.
   */
  public ListeActionsUser getListeActionUser() {
    return this.listeActionsUser;
  }


	/**
	 * Recherche un noeud avec un nom donné.
	 * @param pNode Le noeud dans lequel il faut chercher.
	 * @param pName Le nom du noeud à chercher
	 * @return le noeud trouvé
	 */
	private Node findNote(Node pNode, String pName) {
	  if (pNode.getNodeName().equals(pName)) {
			return pNode;
	  }

		if (pNode.hasChildNodes()) {
			NodeList childs = pNode.getChildNodes();
			int length = childs.getLength();
			for (int i=0; i< length;i++) {
				Node found = this.findNote(childs.item(i), pName);
				if (found != null) {
					return found;
				}
			}
		}

		return null;
	}


	/**
	 * Charge les données à partir du fichier habilitation XML.<p>
	 * construit un objet du type ListeActionsUser.
	 * @throws Exception Liste des erreurs possibles
	 */
	private void chargerDonneesHabilitation() throws Exception {

		NodeList app = this.document.getDocumentElement().getElementsByTagName( CST_ITEM_ACTION );
		this.listeActionsUser  = new ListeActionsUser();

    // Je boucle sur toutes les actions
		for (int i=0;i<app.getLength();i++) {

		  // Je prend les attributs : nom
			NamedNodeMap nd = app.item(i).getAttributes();
			String strNomAction = "";
      // Je boucle sur les attributs pour recuperer le nom
		  for (int k=0;k<nd.getLength();k++) {
				if (nd.item(k).getNodeName().equals(this.CST_ITEM_ACTION_NOM)) {
					strNomAction = nd.item(k).getNodeValue();
				}
		  }

      // Je recupere la liste des profils d emon action courante
			NodeList nlProfil = app.item(i).getChildNodes();

      // Je boucle sur liste des profils
      boolean trouve = false;
      int j = 0;
      while ((j<nlProfil.getLength())&&(!trouve)) {
        // Si mon noeud est un profil
        if (nlProfil.item(j).getNodeName().equals(this.CST_ITEM_PROFIL)) {
      		NamedNodeMap nnp = nlProfil.item(j).getAttributes();

          // Je regarde dans les attributs l'ID
          for (int c=0; c<nnp.getLength(); c++) {
            if (nnp.item(c).getNodeName().equals(this.CST_ITEM_PROFIL_ID)) {

              // Si la valeur du profil est dans la liste des profils du user
              if (getListeProfilUser().contains(new Long(nnp.item(c).getNodeValue()))) {
                this.listeActionsUser.ajouterAction( strNomAction );
                trouve = true;
              }

            }
          }
        }
        j++; // boucle sur les profils
      }
		}
	}


  /**
   * Retourne les profils du user
   * @return les profils du user
   */
  public Vector getListeProfilUser() {
    return this.listeProfilUser;
  }


  /**
   * Initialise les profils du user
   * @param pListeProfilUser les profils du user
   */
  public void setListeProfilUser(Vector pListeProfilUser) {
    this.listeProfilUser = pListeProfilUser;
  }

}