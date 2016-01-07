package ressource.mail;

import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
* Cette classe permet le stockage des informations
* d'un message indépendement de son implémentation
* pour JavaMail, et ne contenant que du texte formaté
*
* @author BUOT S./GEORJON N.
* @version 1.1
* @since  jdk 1.1
* @see AMessagerie
*/
public class AMessage {

  public final static int DESTINATAIRE_NORMAL = 0;
  public final static int DESTINATAIRE_COPIE  = 1;
  public final static int DESTINATAIRE_CACHE  = 2;

  public final static String TYPE_CONTENU_TEXTE = new String("text/plain");
  public final static String TYPE_CONTENU_HTML  = new String("text/html");

  protected String envoyeur;
  protected String sujet;
  protected Vector destinataires = new Vector();
  protected Vector destinataires_copie = new Vector();
  protected Vector destinataires_caches = new Vector();
  protected String contenu;
  protected String typeContenu;
  protected String fileName;

  /**
  * Constructeur par défaut
  */
  public AMessage( ) {
      this.envoyeur = "";
      this.sujet = "";
      this.contenu = "";
      this.typeContenu = new String(AMessage.TYPE_CONTENU_TEXTE);
      this.fileName = null;
  }

  /**
  * Constructeur renseignant l'ensemble des informations
  * @param envoyeur Adresse messagerie de l'envoyeur du message
  * @param destinataires Adresses messagerie des destinataires du message (ou null)
  * @param sujet Sujet du message
  * @param contenu Contenu du message
  */
 public AMessage( String envoyeur,
                  String[] destinataires,
                  String sujet,
                  String contenu)
 {
  this.envoyeur = envoyeur;
  if (destinataires!=null)
   {
    for (int i=0 ; i<destinataires.length ; i++)
     {
//System.out.print("destinataires["+i+"] = '" + destinataires[i] + "'" );
      if (destinataires[i]!=null)
       {
//System.out.println(" (ajouté)" );
        this.destinataires.add(destinataires[i]);
       }
/*
      else
       {
System.out.println(" (non ajouté)" );
       }
*/
     }
    }
  this.sujet = sujet;
  this.contenu = contenu;
  this.typeContenu = new String(AMessage.TYPE_CONTENU_TEXTE);
  this.fileName = null;
 }

	/**
	 *
	* Constructeur renseignant l'ensemble des informations
	* @param envoyeur Adresse messagerie de l'envoyeur du message
	* @param destinataires Adresses messagerie des destinataires du message (ou null)
	* @param sujet Sujet du message
	* @param contenu Contenu du message
	* @param typeContenu Type du contenu du message : Texte ou HTML
	*/
  public AMessage( String envoyeur,
                   String[] destinataires,
                   String sujet,
                   String contenu,
                   String typeContenu) {
       this(envoyeur, destinataires, sujet, contenu);
       this.typeContenu = new String(typeContenu);
  }

	/**
	* Constructeur renseignant l'ensemble des informations
	* @author GEORJON N.
	* @param envoyeur Adresse messagerie de l'envoyeur du message
	* @param destinataires Adresses messagerie des destinataires du message (ou null)
	* @param sujet Sujet du message
	* @param contenu Contenu du message
	* @param typeContenu Type du contenu du message : Texte ou HTML
	* @param fileName Nom du fichier à attacher au mail
	*/
  public AMessage( String envoyeur,
                   String[] destinataires,
                   String sujet,
                   String contenu,
                   String typeContenu,
                   String fileName) {
       this(envoyeur, destinataires, sujet, contenu, typeContenu);
       this.fileName = fileName;
  }

  /**
  * Construction d'un Message de type mime
  * @param  session Session à laquelle le message doit être associé
  */
  public MimeMessage getMimeMessage(Session session) throws AMessageException {

       // ***** Création du message ***********
       MimeMessage message = new MimeMessage(session);
       // ***** Mise à jour de l'emetteur ***********
       try {
           message.setFrom(new InternetAddress(this.envoyeur));
       } catch (MessagingException exp) {
           throw new AMessageException( AMessageException.TYPE_ADRESSE_FROM );
       }

       // ***** Mise à jour des destinataires ***********
       try {
           message.addRecipients( Message.RecipientType.TO ,
                              convertiAdresses(destinataires));
       } catch (MessagingException exp) {
           throw new AMessageException( AMessageException.TYPE_ADRESSE_TO );
       }

       try {
           message.addRecipients( Message.RecipientType.CC ,
                              convertiAdresses(destinataires_copie));
       } catch (MessagingException exp) {
           throw new AMessageException( AMessageException.TYPE_ADRESSE_CC );
       }

       try {
           message.addRecipients( Message.RecipientType.BCC ,
                              convertiAdresses(destinataires_caches));
       } catch (MessagingException exp) {
           throw new AMessageException( AMessageException.TYPE_ADRESSE_BCC );
       }
       // ***** Mise à jour du sujet ***********

       try {
           //message.setSubject(this.sujet);
					 message.setSubject(this.sujet,"ISO-8859-1");

       } catch (MessagingException exp) {
           throw new AMessageException( AMessageException.TYPE_SUJET );
       }
       // ***** Mise à jour de la date ***********

       try {
              message.setSentDate(new Date());


       } catch (MessagingException exp) {
           throw new AMessageException( AMessageException.TYPE_DATE_ENVOI );
       }


       // ***** Mise à jour du contenu ***********

       MimeMultipart mp = new MimeMultipart();
       try
       {   // s'il y a un contenu à mettre dans le body
           if(this.contenu != null)
           {
              MimeBodyPart bp = new MimeBodyPart();
              // s'il s'agit d'un contenu texte
              if (this.typeContenu.equals(AMessage.TYPE_CONTENU_TEXTE))
                 //bp.setContent( this.contenu , "text/plain" );
								 bp.setText( this.contenu , "ISO-8859-1" );

              // s'il s'agit d'un contenu Html
              else if (this.typeContenu.equals(AMessage.TYPE_CONTENU_HTML))
                   bp.setDataHandler(new DataHandler(new ByteArrayDataSource(this.contenu, "text/html")));

              mp.addBodyPart(bp);
           }

       } catch (MessagingException exp) {
           throw new AMessageException( AMessageException.TYPE_CONTENU );
       }

       // ***** Mise à jour fichier attache ***********
       try {
             if (this.fileName != null )
             {
                 MimeBodyPart mbp2 = new MimeBodyPart();
                 FileDataSource fds = new FileDataSource(this.fileName);
                 mbp2.setDataHandler(new DataHandler(fds));
                 mbp2.setFileName(fds.getName());

                  // create the Multipart and its parts to it
                  mp.addBodyPart(mbp2);
             }
       } catch (MessagingException exp) {
           throw new AMessageException( AMessageException.TYPE_FICHIER_ATTACHE );
       }

       try {

        	    message.setContent(mp);

       } catch (MessagingException exp) {
           throw new AMessageException( AMessageException.TYPE_CONSTRUCTION_MAIL );
       }


       // ***** Retour du message construit ***********
       return message;
  }

  /**
  * Mise à jour de l'emetteur du message
  * @param envoyeur Adresse internet de l'emetteur du message
  */
  public void setEnvoyeur(String envoyeur) {
     this.envoyeur = envoyeur;
  }

  /**
  * Renvoi de l'adresse internet l'emetteur du message
  * @return Adresse internet l'emetteur
  */
  public String getEnvoyeur() {
      return this.envoyeur;
  }

  /**
  * Mise à jour du sujet du message
  * @param sujet Objet du message
  */
  public void setSujet(String sujet) {
       this.sujet = sujet;
  }

  /**
  * Renvoi du sujet du message
  * @ return Sujet du message
  */
  public String getSujet() {
       return this.sujet;
  }

  /**
  * Ajout d'un destinataire pour le message
  * @param destinataire Adresse messagerie du destinataire à ajouter
  * @param type Type de destinataire (DESTINATAIRE_NORMAL, DESTINATAIRE_COPIE ou DESTINATAIRE_CACHE)
  */
  public void addDestinataire(String destinataire, int type) {
       switch (type) {
              case DESTINATAIRE_COPIE:
                   destinataires_copie.add(destinataire);
                   break;
              case DESTINATAIRE_CACHE:
                   destinataires_caches.add(destinataire);
                   break;
              default:
                   destinataires.add(destinataire);
                   break;
       }
  }

  /**
  * Mise à jour du contenu pour le message
  * @param contenu Contenu du message (UNICODE)
  */
  public void setContenu(String contenu) {
     this.contenu = contenu;
  }

  /**
  * Lecture du contenu du message
  * @return Contenu du message (UNICODE)
  */
  public String getContenu() {
     return this.contenu;
  }

  private Address[] convertiAdresses(Vector adresses) throws AddressException {
       Address[] adress = new Address[adresses.size()];
       Enumeration eEnum = adresses.elements();
       int i=0;
       while (eEnum.hasMoreElements()) {
         adress[i] = new InternetAddress( (String)eEnum.nextElement() );
         i++;
       }
       return adress;
  }

}
