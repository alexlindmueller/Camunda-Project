package scis.liefart;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.*;
import java.net.InetAddress;
import java.util.Properties;
import java.util.Date;

import javax.mail.*;

import javax.mail.internet.*;

import com.sun.mail.smtp.*;


public class untauglich implements JavaDelegate {

  private final static Logger LOGGER = Logger.getLogger("exportmsg");

  public void execute(DelegateExecution execution) throws Exception {
	  String thema = (String) execution.getVariable("thema");
	  String betreffa = (String) execution.getVariable("betreffa");
	  String inhalta = (String) execution.getVariable("inhalta");
	 
	  
	  
      LOGGER.info("Ablehnung gesendet");
      
      
      String sender = "scistischlerei@gmx.de";
      String password = "hans1234";
      String receiver = (String) execution.getVariable("maila");

      final Properties properties = new Properties();

      properties.put("mail.transport.protocol", "smtp");
      properties.put("mail.smtp.host", "mail.gmx.net");
      properties.put("mail.smtp.port", "587");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.user", sender);
      properties.put("mail.smtp.password", password);
      properties.put("mail.smtp.starttls.enable", "true");

      Session mailSession = Session.getInstance(properties, new Authenticator()
      {
          @Override
          protected PasswordAuthentication getPasswordAuthentication()
          {
              return new PasswordAuthentication(properties.getProperty("mail.smtp.user"),
                      properties.getProperty("mail.smtp.password"));
          }
      });

      Message message = new MimeMessage(mailSession);
      InternetAddress addressTo = new InternetAddress(receiver);
      message.setRecipient(Message.RecipientType.TO, addressTo);
      message.setFrom(new InternetAddress(sender));
      message.setSubject("" + betreffa);
      message.setContent(""+ inhalta, "text/plain");
      Transport.send(message);
      
      
      
	  //execution.setVariable("thema", thema + thema);
  }
 
}