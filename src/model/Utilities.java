package model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import global.Constants;

public class Utilities {
	public String generatePassword() {
		String password = "";
		String[] characters = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
				"r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
				"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6",
				"7", "8", "9" };

		for (int i = 0; i < 12; i++) {
			int random = (int) (Math.random() * 62);
			password += characters[random];
		}

		return password;
	}
	
	public void sendEmail(String email, String newPwd) {
		final String fromEmail = Constants.EMAILSENDER; // requires valid gmail id;
		final String password = Constants.PASSWORDSENDER; // correct password for gmail id
		final String toEmail = email; // can be any email id 
		
		Properties props = new Properties();
		props.put("mail.smtp.host", Constants.SMTPHOST); //SMTP Host
		props.put("mail.smtp.socketFactory.port", Constants.SMTPPORT); //SSL Port
		props.put("mail.smtp.socketFactory.class",Constants.SOCKETFACTORYCLASS); //SSL Factory Class
		props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
		props.put("mail.smtp.port", Constants.SMTPHOST); //SMTP Port
		
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		
		String subject = generateSubject();
		String body = generateBody(newPwd);
		EmailUtils emailUtil = new EmailUtils();
		Session session = Session.getDefaultInstance(props, auth);
		emailUtil.sendEmail(session, toEmail,subject, body);
	}
	
	private String generateSubject() {
		return "Password Recovery";
	}
	
	private String generateBody(String newPwd) {
		return "Your new password is: " + newPwd;
	}
}
