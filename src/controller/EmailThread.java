package controller;

import model.Utilities;

public class EmailThread extends Thread{
	private String email;
	private String password;
	
	public EmailThread(String email, String password) {
		this.email = email;
		this.password = password;
		start();
	}
	
	@Override
	public void run() {
		Utilities util = new Utilities();
		util.sendEmail(email, password);
	}
}
