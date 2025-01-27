package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import model.Users;
import model.dao.*;

public class DataThread extends Thread {
	private Socket socket;
	private InputStream is;
	private InputStreamReader isr;
	private ObjectOutputStream oos;
	private BufferedReader br;
	private PrintWriter pw;
	private Logger logger;
	
	public DataThread(Socket socket) {
		this.socket = socket;
		try {
	        // Initialize InputStream and OutputStream
	        // Initialize InputStream and OutputStream for plain text
	        this.is = socket.getInputStream();
	        this.isr = new InputStreamReader(is);
	        this.br = new BufferedReader(isr);

	        // Initialize OutputStream for plain text
	        this.pw = new PrintWriter(socket.getOutputStream(), true);

	        // Initialize ObjectOutputStream
	        this.oos = new ObjectOutputStream(socket.getOutputStream());
	        
			logger = Logger.getLogger("DataThread");
			logger.info("DataThread created");
			// logger.setLevel();
			
		} catch (IOException e) {
			// logger.severe("Error creating DataThread");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override	
	public void run() {
		String msg;
	        try {
				msg = br.readLine();
				System.out.println("Message received: " + msg);
				try {
					checkCommand(msg);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					logger.severe("Error checking command");
					e.printStackTrace();
			
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.severe("Error reading message");
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
				Users userJava = usersDao.checkLoginJava(command[1]);
				logger.info("Result -> " + userJava);
				oos.writeObject(userJava);
		        break;
			case "loginAndroid":
				logger.info("Call -> LoginAndroid");
				usersDao = new UsersDao();
				Users userAndroid = usersDao.checkLoginAndroid(command[1]);
				logger.info("Result -> " + userAndroid);
				System.out.println(userAndroid.getArgazkia());
				oos.writeObject(userAndroid);
				break;
			case "changePwd":
				logger.info("Call -> changePwd");
				usersDao = new UsersDao();
				Object existEmail = usersDao.changePwd(command[1]);
				logger.info("Result -> " + existEmail);
				oos.writeObject(existEmail);
				break;
			case "scheduleTeacher":
				logger.info("Call -> scheduleTeacher");
				horariosDao = new HorariosDao();
				Object horariosTeacher = horariosDao.getHorarioByTeacherId(command[1]);
				oos.writeObject(horariosTeacher);
				logger.info("Result -> " + horariosTeacher);
				break;
			case "scheduleStudent":
				logger.info("Call -> scheduleStudent");
                horariosDao = new HorariosDao();
                Object horariosStudent = horariosDao.getHorarioByStudentId(command[1]);
                oos.writeObject(horariosStudent);
                logger.info("Result -> " + horariosStudent);
                break;
			case "getTeachers":
				logger.info("Call -> getTeachers");
				usersDao = new UsersDao();
				Object teachers = usersDao.getTeachers();
				oos.writeObject(teachers);
				logger.info("Result -> " + teachers);
				break;
			case "usersByTeacher":
				logger.info("Call -> usersByTeacher");
				usersDao = new UsersDao();
				Object usersByTeacher = usersDao.getUsersByTeacherId(command[1]);
				oos.writeObject(usersByTeacher);
				logger.info("Result -> " + usersByTeacher);
				break;
			case "usersFiltered":
				logger.info("Call -> usersFiltered");
				usersDao = new UsersDao();
				Object usersFiltered = usersDao.getFilteredUsers(command[1], command[2]);
				oos.writeObject(usersFiltered);
				logger.info("Result -> " + usersFiltered);
				break;
			case "matriculacionesUser":
			    logger.info("Call -> matriculacionesUser");
			    matriculacionesDao = new MatriculacionesDao();
			    Object matriculaciones = matriculacionesDao.getMatriculacionesByUser(command[1]);
			    oos.writeObject(matriculaciones);
			    logger.info("Result -> " + matriculaciones);
			    break;
			case "updateUser":
				logger.info("Call -> updateUser");
				String blob = command[2];
				for (int i = 3; i < command.length; i++) {
					blob += "/" + command[i];
				}
				usersDao = new UsersDao();
				Object updatedUser = usersDao.setUserPicture(command[1], blob);
				logger.info("Result -> " + updatedUser);
				oos.writeObject(updatedUser);
				break;
			case "bilerakByTeacher" :
				logger.info("Call -> bilerakByTeacher");
				reunionesDao = new ReunionesDao();
				Object bilerakByTeacher = reunionesDao.getReunionesByTeacher(command[1]);
				oos.writeObject(bilerakByTeacher);
				logger.info("Result -> " + bilerakByTeacher);
				break;
			case "testString":
				pw.println("Test command");
				break;
			case "testUser":
				usersDao = new UsersDao();
				Users testUser = usersDao.checkLoginJava("m");
				oos.writeObject(testUser);
				break;
			default:
				pw.println("Command not found");
				break;
		}
	}

}