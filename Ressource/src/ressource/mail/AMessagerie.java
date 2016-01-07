package ressource.mail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


/**
* Classe permettant l'envoi de messages simples
* via un serveur de messagerie electronique
*
* @author BUOT S.
* @since  jdk 1.1
* @see AMessage
*/
public class AMessagerie {

  protected Session session;
  protected Transport transport;

  /**
  * Constructeur nécessitant l'adresse du serveur SMTP
  * @param  serveur L'adresse du serveur de messagerie SMTP.
  */
  public AMessagerie(String serveur) {
    Properties proprietes = new Properties();
    proprietes.put("mail.smtp.host", serveur);
    session = Session.getDefaultInstance(proprietes, null);
  }

  /**
  * Invocation de l'envoi d'un message auprès du serveur de messagerie.
  * @param  aMessage Message à envoyer
  */
  public void envoiMessage(AMessage aMessage) throws AMessageException {
    try {

      Message message = aMessage.getMimeMessage(session);
      Transport.send(message);

    } catch (MessagingException exp) {
      throw new AMessageException( AMessageException.TYPE_ENVOI );
    }
  }

}



