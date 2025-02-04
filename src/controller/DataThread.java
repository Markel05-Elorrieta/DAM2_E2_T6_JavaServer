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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import model.Ikastetxeak;
import model.Reuniones;
import model.Users;
import model.Utilities;
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
			socket.setSendBufferSize(1024*1024);
	        this.is = socket.getInputStream();
	        this.isr = new InputStreamReader(is);
	        this.br = new BufferedReader(isr);

	        // Initialize OutputStream for plain text
	        this.pw = new PrintWriter(socket.getOutputStream(), true);

	        // Initialize ObjectOutputStream
	        this.oos = new ObjectOutputStream(socket.getOutputStream());
	        
			logger = Logger.getLogger("DataThread");
			logger.severe("DataThread created");
			logger.setLevel(Level.SEVERE);
			
		} catch (IOException e) {
			logger.severe("Error creating DataThread" + e.getMessage());
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
				logger.severe("Error reading message" + e.getMessage());
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
		IkastetxeakDao ikastetxeakDao;
		
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
			case "teachersByUser":
				logger.info("Call -> teachersByUser");
				usersDao = new UsersDao();
				Object teachersByUser = usersDao.getTeachersByUserId(command[1]);
				oos.writeObject(teachersByUser);
				logger.info("Result -> " + teachersByUser);
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
			case "bilerakByStudent" :
				logger.severe("Call -> bilerakByStudent");
				reunionesDao = new ReunionesDao();
				Object bilerakByStudent = reunionesDao.getReunionesByStudent(command[1]);
				oos.writeObject(bilerakByStudent);
				logger.severe("Result -> " + bilerakByStudent);
				break;
			case "getIkastetxeak":
				logger.info("Call -> getIkastetxeak");
				ikastetxeakDao = new IkastetxeakDao();
				List<Ikastetxeak> ikastetxeak = ikastetxeakDao.getIkastetxeak();
				oos.writeObject(ikastetxeak);
				logger.info("Result -> " + ikastetxeak);
				break;
			case "getIkastetxe":
				logger.info("Call -> getIkastetxe");
                ikastetxeakDao = new IkastetxeakDao();
                Ikastetxeak ikastetxe = ikastetxeakDao.getIkastetxeaByCCEN(command[1]);
                oos.writeObject(ikastetxe);
                logger.info("Result -> " + ikastetxe);
                break;
			case "newBilera":
				logger.info("Call -> newBilera");
				reunionesDao = new ReunionesDao();
				/*
				 * command[1] -> teacherId 
				 * command[2] -> alumnoId 
				 * command[3] -> estado
				 * command[4] -> IkastetxeId
				 * command[5] -> Title
				 * command[6] -> asunto
				 * command[7] -> aula
				 * command[8] -> fecha
				 */
				usersDao = new UsersDao();
				Utilities utilities = new Utilities();
				
				Reuniones r = new Reuniones(
								  usersDao.getUserById(command[1]),
								  usersDao.getUserById(command[2]),
								  command[3],
								  null,
								  command[4],
								  command[5],
								  command[6],
								  command[7],
								  utilities.stringToTimestamp(command[8])
								);
				
				Reuniones newBilera = reunionesDao.insertReunion(r);
				EmailThread emailThread = new EmailThread("insertReunion", newBilera);
				oos.writeObject(newBilera);
				logger.info("Result -> " + newBilera);
				break;
			case "acceptBilera":
				logger.info("Call -> acceptBilera");
				reunionesDao = new ReunionesDao();
				Reuniones updateReunion1 = reunionesDao.acceptReunion(command[1]);
				EmailThread emailThread1 = new EmailThread("insertReunion", updateReunion1);
				logger.info("Result -> " + "Bilera id " + command[1] + " accepted");
				break;
			case "declineBilera":
				logger.info("Call -> declineBilera");
				reunionesDao = new ReunionesDao();
				Reuniones updateReunion2 = reunionesDao.declineReunion(command[1]);
				EmailThread emailThread2 = new EmailThread("insertReunion", updateReunion2);
				logger.info("Result -> " + "Bilera id " + command[1] + " declined");
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