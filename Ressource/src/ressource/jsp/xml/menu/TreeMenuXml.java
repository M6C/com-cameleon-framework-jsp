package ressource.jsp.xml.menu;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Vector;
import java.io.File;
import ressource.hashtable.menu.TreeMenu;
/**
 * Titre :
 * Description :
 * Copyright :    Copyright (c) 2001
 * Société :
 * @author
 * @version 1.0
 */

public class TreeMenuXml {
  /** le document créé après chargement du fichier XML */
  private Document document = null;

  /** Structure pour contenir la structure de rep */
  private TreeMenu menu;

  /** Clefs du fichiers XML*/
  private final String CST_ITEM_REPERTOIRE = "repertoire";
  private final String CST_ITEM_CIBLE = "cible";
  private final String CST_ITEM_NAME = "name";
//DRO

  public TreeMenu getTreeMenu() {
          return this.menu;
  }



  public TreeMenuXml() {
  }

  private void loadXMLDocument(String pFileName) throws Exception {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

                this.document = docBuilder.parse(new File(pFileName));
                this.document.getDocumentElement().normalize();

        }

  public void setInitialiser(String pFileMenu) throws Exception {
    // On charge une page
    this.loadXMLDocument(pFileMenu);
    this.chargerDonneesTreeMenu();

 }

 private Vector GetNoeudsFreres (Node app) throws Exception {
      Vector vect = null;

      System.out.println("GetNoeudsFreres Debut");
      if (app== null) {
        return vect;
      }

      System.out.println("GetNoeudsFreres app.name:"+app.getNodeName());

      Node parent = app.getParentNode();
      NodeList l_node = parent.getChildNodes();


      if ((l_node==null) || (l_node.getLength()==0 ))
        return vect;

      System.out.println("GetNoeudsFreres (l_node!=null) || (l_node.getLength()!=0 )");
      vect = new Vector();

      for (int i = 0; i < l_node.getLength(); i++) {
        if  (l_node.item(i) ==null) {
          System.out.println("**** GetNoeudsFreres l_node.item(i) =null " + i + l_node.item(i).getNodeName());
        }
        Node ncourant = l_node.item(i);

        System.out.println("GetNoeudsFreres ncourant.name:" + ncourant.getNodeName());

        if  ((ncourant!=null)&&(ncourant.getAttributes()!= null)) {
          Node noeud = ncourant.getAttributes().getNamedItem(CST_ITEM_NAME);
          System.out.println("GetNoeudsFreres noeud:" + noeud);
          if (noeud!= null){
            System.out.println("GetNoeudsFreres noeud.name:" + noeud.getNodeName());
            vect.addElement(ncourant.getAttributes().getNamedItem(CST_ITEM_NAME).getNodeValue());
          }
        }
      }

      if (vect.isEmpty()){
        return null;
      }

      System.out.println("GetNoeudsFreres Fin");

      return vect;
 }

   private void chargerDonneesTreeMenu() throws Exception {
      Vector Resultat ;
      String rep_name="";
      String action="";
      Vector vect;

      //DEBUG
      System.out.println("chargerDonneesTreeMenu debut");

      this.menu = new TreeMenu();

      NodeList app = this.document.getDocumentElement().getElementsByTagName( CST_ITEM_REPERTOIRE );
      int nbPage = app.getLength();

      for (int i=0;i<app.getLength();i++) {
        rep_name = app.item(i).getAttributes().getNamedItem(CST_ITEM_NAME).getNodeValue();
        action = app.item(i).getAttributes().getNamedItem(CST_ITEM_CIBLE).getNodeValue();

        //DEBUG
        System.out.println("chargerDonneesTreeMenu rep_name:"+((rep_name==null) ? "null" : rep_name));
        System.out.println("chargerDonneesTreeMenu action:"+((action==null) ? "null" : action));

        Resultat = this.GetNoeudsFreres(app.item(i));
        if ((rep_name!=null) && (rep_name!="")&&(Resultat!=null)){
          menu.setFreres(rep_name, Resultat);
          }

        if (app.item(i).getParentNode()!=  null) {
          //DEBUG
          System.out.println("chargerDonneesTreeMenu app.item(i).getParentNode()!=  null");

          if ((app.item(i).getParentNode().getAttributes()!=null)&&(app.item(i).getParentNode().getAttributes().getNamedItem(CST_ITEM_NAME)!=null)) {
            //DEBUG
            System.out.println("chargerDonneesTreeMenu app.item(i).getParentNode()!=  null 1");
            menu.setParent(rep_name, app.item(i).getParentNode().getAttributes().getNamedItem(CST_ITEM_NAME).getNodeValue());
          } else {
            //DEBUG
            System.out.println("chargerDonneesTreeMenu app.item(i).getParentNode()!=  null 2");
            menu.setParent(rep_name, "");
          }
        } else {
          //DEBUG
          System.out.println("chargerDonneesTreeMenu app.item(i).getParentNode()==  null");
          menu.setParent(rep_name, "");
        }
        menu.setAction(rep_name,action);
      }

      //DEBUG
      System.out.println("chargerDonneesTreeMenu fin");
}

}