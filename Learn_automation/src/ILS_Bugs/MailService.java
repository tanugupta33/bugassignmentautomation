package ILS_Bugs;

import java.util.Date;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailService {

	private static final String topHTML="<!DOCTYPE html> <html>    <head> "
			+ "<title>Closure Count</title>"
			+ "<style type=\"text/css\"> table, th, td {border: 1px solid black;padding: 5px;}table {border-collapse: collapse;} </style>"
			+ " </head>     "
			+ "<body> 		 "
			+ "<p>Hi Team,</p>         "
			+ "<p>PFB the bug assignment for ILS bugs.</p>";
	private static final String bottonHTML="<p>Regards,</br>Tanu</p> </body> </html>";
	
	private static void composeAndSendMail(String[] to, String cc[], String from, String[] tables, String locale) throws AddressException, MessagingException{

		String mailMessage = topHTML;
		for(String table:tables){
			mailMessage=mailMessage+table;
			mailMessage=mailMessage+"<br><br>";
		}
		mailMessage=mailMessage+bottonHTML;
		Multipart multipart = new MimeMultipart();
		Session session = MailUtility.getSession();
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		for (int i = 0; i < to.length; i++) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
		}
		for (int i = 0; i < cc.length; i++) {
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
		}
		message.setSubject("ANZ Bug Assignment for Today " + new Date());

		BodyPart htmlBodyPart = new MimeBodyPart();
		htmlBodyPart.setContent(mailMessage, "text/html");
		multipart.addBodyPart(htmlBodyPart);
		message.setContent(multipart);
		Transport.send(message);
		System.out.println("++ File mailed ++ ");

	}
	
	public static void composeAndSendEMail(String[] to, String cc[], String from, String[] tables, String locale) throws AddressException, MessagingException{
		composeAndSendMail(to, cc, from, tables, locale);
	}
	}
