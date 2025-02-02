package controller;

import model.Reuniones;
import model.Utilities;

public class EmailThread extends Thread{
	private String key;
	private String email;
	private String password;
	private Reuniones reunion;
	
	public EmailThread(String key, String email, String password) {
		this.key = key;
		this.email = email;
		this.password = password;
		start();
	}
	
	public EmailThread(String key, Reuniones reunion) {
		this.key = key;
		this.reunion = reunion;
		start();
	}
	
	@Override
	public void run() {
		Utilities util = new Utilities();
		
		switch (key) {
		case "changepwd":
			util.sendEmail(email, password);
			break;
		case "insertReunion":
			//util.sendEmailToIrakasle(reunion);
			util.sendEmailToIkasle(reunion);
			break;
		default:
			break;
		}
	}
}
