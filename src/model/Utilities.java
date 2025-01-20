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
		String ret = "<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Password Recovery</title>\r\n"
				+ "    <style>\r\n"
				+ "        body {\r\n"
				+ "            font-family: Arial, sans-serif;\r\n"
				+ "            background-color: \r\n"
				+ "#f4f4f4;\r\n"
				+ "            margin: 0;\r\n"
				+ "            padding: 0;\r\n"
				+ "        }\r\n"
				+ "        .container {\r\n"
				+ "            max-width: 600px;\r\n"
				+ "            margin: 50px auto;\r\n"
				+ "            background-color: \r\n"
				+ "#fff;\r\n"
				+ "            padding: 20px;\r\n"
				+ "            box-shadow: 0 0 10px \r\n"
				+ "rgba(0, 0, 0, 0.1);\r\n"
				+ "            border-radius: 8px;\r\n"
				+ "        }\r\n"
				+ "        h1 {\r\n"
				+ "            color: \r\n"
				+ "#333;\r\n"
				+ "        }\r\n"
				+ "        p {\r\n"
				+ "            color: \r\n"
				+ "#666;\r\n"
				+ "        }\r\n"
				+ "        .button {\r\n"
				+ "            display: inline-block;\r\n"
				+ "            padding: 10px 20px;\r\n"
				+ "            margin-top: 20px;\r\n"
				+ "            background-color: \r\n"
				+ "#007BFF;\r\n"
				+ "            color: \r\n"
				+ "#fff;\r\n"
				+ "            text-decoration: none;\r\n"
				+ "            border-radius: 5px;\r\n"
				+ "        }\r\n"
				+ "        .button:hover {\r\n"
				+ "            background-color: \r\n"
				+ "#0056b3;\r\n"
				+ "        }\r\n"
				+ "    </style>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "    <div class=\"container\">\r\n"
				+ "        <h1>Password Recovery</h1>\r\n"
				+ "        <p>Hello,</p>\r\n"
				+ "        <p>We received a request to reset your password. Your new password is:</p>\r\n"
				+ "        <p><strong>" + newPwd + "</strong></p>\r\n"
				+ "        <p>If you did not request a password reset, please ignore this email or contact support if you have questions.</p>\r\n"
				+ "        <p>Thank you,</p>\r\n"
				+ "        <p>JEM Software</p>\r\n"
				+ "    </div>\r\n"
				+ "</body>";
		return ret;
	}
}
