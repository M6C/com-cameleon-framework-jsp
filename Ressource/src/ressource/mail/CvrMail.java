package ressource.mail;

/**
 * Titre :        Utils
 * Description :
 * Copyright :    Copyright (c) 2001
 * Société :      ressource
 * @author
 * @version 1.0
 */

public class CvrMail implements java.io.Serializable {

  /** adresse du serveur de messagerie */
  private String serveurMessagerie = null;

  /** type du contenu */
  private String typeContenu = AMessage.TYPE_CONTENU_HTML;

  /** objet du mail */
  private String objet = null;

  /** envoyeur du mail */
  private String envoyeur = null;

  /** destinataire du mail */
  private String destinataire = null;

  /** destinataire copie du mail */
  private String destinataireCopie = null;


  /**
   * Constructeur
   */
  public CvrMail() {
  }

  /**
   * Constructeur
   * @param pServerMail adresse IP ou nom su serveur de messagerie
   * @param pTypeContenu Type du contenu (HTML ou Texte)
   * @param pObjet Objet du mail
   * @param pEnvoyeur Envoyeur du mail
   * @param pDestinataire Destinataire du mail
   * @param pDestinataireCopie
   */
  public CvrMail(String pServeurMail, String pTypeContenu, String pObjet, String pEnvoyeur,
    String pDestinataire, String pDestinataireCopie) {

    this.serveurMessagerie = pServeurMail;
    this.typeContenu = pTypeContenu;
    this.destinataire = pDestinataire;
    this.destinataireCopie = pDestinataireCopie;
    this.envoyeur = pEnvoyeur;
    this.objet = pObjet;
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


  /**
   * @return String
   */
  public String getObjet() {
    return this.objet;
  }

  /**
   * @param pObjet
   */
  public void setObjet(String pObjet) {
    this.objet = pObjet;
  }

  /**
   * @return String
   */
  public String getEnvoyeur() {
    return this.envoyeur;
  }

  /**
   * @param pEnvoyeur
   */
  public void setEnvoyeur(String pEnvoyeur) {
    this.envoyeur = pEnvoyeur;
  }

  /**
   * @return String
   */
  public String getDestinataire() {
    return this.destinataire;
  }

  /**
   * @param pEnvoyeur
   */
  public void setDestinataire(String pDestinataire) {
    this.destinataire = pDestinataire;
  }

  /**
   * @return String
   */
  public String getDestinataireCopie() {
    return this.destinataireCopie  ;
  }

  /**
   * @param pEnvoyeur
   */
  public void setDestinataireCopie(String pDestinataireCopie) {
    this.destinataireCopie = pDestinataireCopie;
  }

   /**
    *
    */
   public String toString()
   {
    StringBuffer buf = new StringBuffer(1024);

    buf.append("serveurMessagerie = "); buf.append(serveurMessagerie);
    buf.append(" - ");
    buf.append("typeContenu = "); buf.append(typeContenu);
    buf.append(" - ");
    buf.append("destinataire = "); buf.append(destinataire);
    buf.append(" - ");
    buf.append("destinataireCopie = "); buf.append(destinataireCopie);
    buf.append(" - ");
    buf.append("envoyeur = "); buf.append(envoyeur);
    buf.append(" - ");
    buf.append("objet = "); buf.append(objet);
    buf.append(" - ");
/*
    buf.append(super.toString());
    buf.append(" / ");
*/
    return buf.toString();
   }



}