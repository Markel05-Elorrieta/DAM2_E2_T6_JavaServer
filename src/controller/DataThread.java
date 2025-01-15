package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Users;
import model.dao.*;

public class DataThread extends Thread {
	private Socket socket;
	private InputStreamReader isr;
	private ObjectOutputStream oos;
	private BufferedReader br;
	
	public DataThread(Socket socket) {
		this.socket = socket;
		try {
			this.isr = new InputStreamReader(socket.getInputStream());
			this.br = new BufferedReader(isr);
			this.oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override	
	public void run() {
		String msg;
		Object[] obj;
	        try {
				msg = br.readLine();
					/*
				    switch(msg) {
                    case "1":
                        oos.writeObject(new Object[]{"Hello","uno"});
                        break;
                    case "2":
                        oos.writeObject(new Object[]{"Bye","dos"});
                        break;
                    case "3":
                        oos.writeObject(new Object[]{"aaa","tres"});
                        break;
                    default:
                        System.out.println("Command not found");
                        break;
				    }
				    
					System.out.println("Closing communication");
					this.isr.close();
					this.br.close();
					this.oos.close();
					socket.close();
                    break;
                    */
					checkCommand(msg);
					
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void checkCommand(String query) throws IOException {
		String[] command = query.split("/");
		
		switch (command[0]) {
		case "user":
			UsersDao usersDao = new UsersDao();
			oos.writeObject(usersDao.getUserById(command[1]));
			System.out.println("User sent");
			break;
		default:
			UsersDao usersDaoDefault = new UsersDao();
			usersDaoDefault.getUsers();
			System.out.println("Users sent");
			break;
		}
	}

}