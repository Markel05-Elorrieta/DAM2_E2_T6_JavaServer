package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(23456);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Starting server Waiting for connections...");
		do {
			Socket socket;
			try {
				socket = serverSocket.accept();
				System.out.println("Connection established!");
				System.out.println("Connected IP -> " + socket.getInetAddress());
				System.out.println("Connected Port -> " + socket.getPort());
				DataThread jasoHaria = new DataThread(socket);
				jasoHaria.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while (true);
	}
}
