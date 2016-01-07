package ressource.mail;

public class AMessageException extends Exception {

  /**
  * Constante correspondant à une erreur générale
  */
  public final static int TYPE_MESSAGE = 0;

  /**
  * Constante correspondant à une erreur de construction d'adresse emetteur
  */
  public final static int TYPE_ADRESSE_FROM = 1;

  /**
  * Constante correspondant à une erreur de construction d'adresse de destinataires
  */
  public final static int TYPE_ADRESSE_TO = 2;

  /**
  * Constante correspondant à une erreur de construction d'adresse de destinataires en copie
  */
  public final static int TYPE_ADRESSE_CC = 3;

  /**
  * Constante correspondant à une erreur de construction d'adresse de destinataires en copie cachee
  */
  public final static int TYPE_ADRESSE_BCC = 4;

  /**
  * Constante correspondant à une erreur de construction du sujet du message
  */
  public final static int TYPE_SUJET = 5;

  /**
  * Constante correspondant à une erreur de construction du contenu du message
  */
  public final static int TYPE_CONTENU = 6;

  /**
  * Constante correspondant à une erreur d'envoi du message
  */
  public final static int TYPE_ENVOI = 7;

  /**
  * Constante correspondant à une erreur de construction du contenu Html
  */
  public final static int TYPE_FICHIER_ATTACHE = 8;

  /**
  * Constante correspondant à une erreur de construction de la date d'envoi
  */
  public final static int TYPE_DATE_ENVOI = 9;

  public final static int TYPE_CONSTRUCTION_MAIL = 10;


  /**
  * Type de l'erreur
  */
  protected int type_erreur;

  /**
  * Constructeur par défaut
  */
  public AMessageException() {
         super("Problème de construction ou d'envoi d'un message SMTP");
  }

  /**
  * Constructeur avec le type est l'origine de l'erreur
  * @param type Type de l'erreur (voir constantes)
  */
  public AMessageException(int type) {
         this();
         this.type_erreur = type;
  }

  /**
  * Récupération du type de l'erreur
  * @return type Type de l'erreur (voir constantes)
  */
  public int getType() {
    return type_erreur;
  }

  /**
  * Mise à jour du type de l'erreur
  * @param type Type de l'erreur (voir constantes)
  */
  public void setType(int newType) {
    type_erreur = newType;
  }





}
