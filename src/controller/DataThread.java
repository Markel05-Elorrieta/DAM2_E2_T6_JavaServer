package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import model.Users;
import model.dao.*;

public class DataThread extends Thread {
	private Socket socket;
	private InputStreamReader isr;
	private ObjectOutputStream oos;
	private BufferedReader br;
	private PrintWriter pw;
	private Logger logger;
	
	public DataThread(Socket socket) {
		this.socket = socket;
		try {
			
			this.isr = new InputStreamReader(socket.getInputStream());
			this.br = new BufferedReader(isr);
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			this.pw = new PrintWriter(socket.getOutputStream(), true);
			logger = Logger.getLogger("DataThread");
			logger.info("DataThread created");
			
		} catch (IOException e) {
			System.out.println("a cascau q flipas");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override	
	public void run() {
		String msg;
	        try {
				msg = br.readLine();
				try {
					checkCommand(msg);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void checkCommand(String query) throws IOException, ClassNotFoundException {
		String[] command = query.split("/");
		UsersDao usersDao;
		TiposDao tiposDao;
		ReunionesDao reunionesDao;
		CiclosDao ciclosDao;
		ModulosDao modulosDao;
		HorariosDao horariosDao;
		MatriculacionesDao matriculacionesDao;
		
		switch (command[0]) {
			case "loginJava":
				logger.info("Call -> loginJava");
				usersDao = new UsersDao();
				Users userJava = usersDao.checkLoginJava(command[1], command[2]);
				logger.info("Result -> " + userJava);
				oos.writeObject(userJava);
		        break;
			case "loginAndroid":
				System.out.println("Call -> loginAndroid");
				usersDao = new UsersDao();
				Users userAndroid = usersDao.checkLoginAndroid(command[1], command[2]);
				System.out.println("Result -> " + userAndroid);
				oos.writeObject(userAndroid);
				break;
			case "changePwd":
				System.out.println("Call -> changePwd");
				usersDao = new UsersDao();
				Object existEmail = usersDao.changePwd(command[1]);
				System.out.println("Result -> " + existEmail);
				oos.writeObject(existEmail);
				break;
			case "scheduleTeacher":
				System.out.println("Call -> scheduleTeacher");
				horariosDao = new HorariosDao();
				Object horariosTeacher = horariosDao.getHorarioByTeacherId(command[1]);
				oos.writeObject(horariosTeacher);
				System.out.println("Result -> " + horariosTeacher);
				break;
			case "scheduleStudent":
				System.out.println("Call -> scheduleStudent");
                horariosDao = new HorariosDao();
                Object horariosStudent = horariosDao.getHorarioByStudentId(command[1]);
                oos.writeObject(horariosStudent);
                System.out.println("Result -> " + horariosStudent);
                break;
			case "getTeachers":
				System.out.println("Call -> getTeachers");
				usersDao = new UsersDao();
				Object teachers = usersDao.getTeachers();
				oos.writeObject(teachers);
				System.out.println("Result -> " + teachers);
				break;
			case "usersByTeacher":
				System.out.println("Call -> usersByTeacher");
				usersDao = new UsersDao();
				Object usersByTeacher = usersDao.getUsersByTeacherId(command[1]);
				oos.writeObject(usersByTeacher);
				System.out.println("Result -> " + usersByTeacher);
				break;
			case "usersFiltered":
				System.out.println("Call -> usersFiltered");
				usersDao = new UsersDao();
				Object usersFiltered = usersDao.getFilteredUsers(command[1], command[2]);
				oos.writeObject(usersFiltered);
				System.out.println("Result -> " + usersFiltered);
				break;
			case "matriculacionesUser":
			    System.out.println("Call -> matriculacionesUser");
			    matriculacionesDao = new MatriculacionesDao();
			    Object matriculaciones = matriculacionesDao.getMatriculacionesByUser(command[1]);
			    oos.writeObject(matriculaciones);
			    System.out.println("Result -> " + matriculaciones);
			    break;
			case "testString":
				pw.println("Test command");
				break;
			case "testUser":
				usersDao = new UsersDao();
				Users testUser = usersDao.checkLoginJava("maitane@elorrieta-errekamari.com", "1234");
				oos.writeObject(testUser);
				break;
			default:
				pw.println("Command not found");
				break;
		}
	}

}