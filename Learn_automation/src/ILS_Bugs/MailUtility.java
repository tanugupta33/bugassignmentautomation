package ILS_Bugs;

import java.util.Properties;

import javax.mail.Session;

public class MailUtility {
	private static String host 	= "192.168.211.175";
	private static Session session;
	private static Properties properties=new Properties();
	static{
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", "25");		
	}
	// Get the default Session object.
	public static Session getSession(){
		return getMailSession();
	}
	
	private static Session getMailSession(){
		session= Session.getDefaultInstance(properties);
		return session;
	}
}
