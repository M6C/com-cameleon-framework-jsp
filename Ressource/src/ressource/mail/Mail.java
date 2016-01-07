package ressource.mail;

/**
  * CLasse nevoyant des emails.
  * @author ressource
	* @version 1.0
  */


import java.util.StringTokenizer;


public class Mail {

	/** html qui peut être ajouté en début d'un mail */
	public static final String DEBUT_EMAIL_HTML = "<html><head></head><body class=\"clsMail\">";

	/** html qui peut être ajouté en fin d'un mail */
	public static final String FIN_EMAIL_HTML = "</body></html>";

  /** adresse du serveur de messagerie */
  private String serveurMessagerie = null;

  /** type du contenu */
  private String typeContenu = AMessage.TYPE_CONTENU_HTML;

  /**
   * Constructeur
   * @param pServerMail adresse IP ou nom su serveur de messagerie
   * @param pTypeContenu Type du contenu (HTML ou Texte)
   */
  public Mail(String pServeurMail, String pTypeContenu) {
    this.serveurMessagerie = pServeurMail;
    this.typeContenu = pTypeContenu;
  }


	/**
	 * Envoie un mail au format HTML sans destinataire en copie.
	 * @param pEnvoyeur la personne envoyant le mail.
	 * @param pDetinataire liste des destinataires (séparé par des ';').
	 * @param pSujet sujet du mail envoyé.
	 * @param pContenu contenu du mail.
	 * @return resultat de l'envoi du mail <p>
	 *   0 message non envoyé <p>
	 *  -1 message non envoyé <p>
	 *   1 message envoyé
	 */
	public int envoyerMail(String pEnvoyeur, String pDestinataire, String pSujet, String pContenu) throws AMessageException {
		return envoyerMail(pEnvoyeur, pDestinataire, "", pSujet, pContenu);
	}


	/**
	 * Envoie un mail avaec destinataire en copie.
   * @param PEnvoyeur la personne envoyant le mail.
	 * @param pDetinataire liste des destinataires (séparé par des ';').
	 * @param pDestinataireCopie liste des destinataires en copie de l'email (séparé par des ';').
	 * @param PSujet sujet du mail envoyé.
	 * @param pContenu contenu du mail.
	 * @retrun resultat de l'envoi du mail <p>
	 *     0 message non envoyé <p>
	 *    -1 message non envoyé <p>
	 *     1 message envoyé
	 */
	public int envoyerMail(String pEnvoyeur, String pDestinataire, String pDestinataireCopie, String pSujet, String pContenu) throws AMessageException {

	  int retour = 0;

		/*  Decoupage des destinataires de l'email. */
		StringTokenizer decoupage = new StringTokenizer(pDestinataire,";");
		String[] adresses = new String[decoupage.countTokens()];

		int n = decoupage.countTokens();
		for (int i=0 ; i<n ; i++)
     {
			adresses[i] = (String)decoupage.nextElement();
      if (adresses[i]!=null)
       {
        adresses[i] = adresses[i].trim();
        if (adresses[i].length()==0)
         {
          adresses[i] = null;
         }

       }
//System.out.println("adresses["+i+"] = '" + adresses[i] + "'" );
		 }
///*
		try
     {
//*/
			AMessage message = new AMessage(pEnvoyeur, adresses, pSujet, pContenu, getTypeContenu());


			/*  Decoupage des destinataires en copie. */
		  StringTokenizer decoupageCopie = new StringTokenizer(pDestinataireCopie,";");
	  	String[] adressesCopie = new String[decoupageCopie.countTokens()];
			int nCopie = decoupageCopie.countTokens();

      String sDest;
			for (int i=0 ; i<nCopie ; i++)
       {
        sDest = (String)decoupageCopie.nextElement();
        if (sDest!=null)
         {
          sDest = sDest.trim();
          if (sDest.length()>0)
           {
            message.addDestinataire( sDest, AMessage.DESTINATAIRE_COPIE);
           }
         }
		   }

			AMessagerie messagerie = new AMessagerie( getServeurMessagerie() );

			messagerie.envoiMessage(message);

			retour = 1;
///*
		 }
    catch (AMessageException e)
     {
			retour = -1;
			e.printStackTrace();
			throw e;
		 }
//*/
		return retour;
	}


  /**
   * @param pServeurMessagerie
   */
  public void setServeurMessagerie(String pServeurMessagerie) {
    this.serveurMessagerie = pServeurMessagerie;
  };


  /**
   * @return serveurMessagerie
   */
  public String getServeurMessagerie() {
    return this.serveurMessagerie;
  };


  /**
   * @param pTypeContenu
   */
  public void setTypeContenu(String pTypeContenu) {
    this.typeContenu = pTypeContenu;
  }


  /**
   * @return typeContenu
   */
  public String getTypeContenu() {
    return this.typeContenu;
  }

}
