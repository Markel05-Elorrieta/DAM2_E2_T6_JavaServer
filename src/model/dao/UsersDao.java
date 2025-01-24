package model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import controller.EmailThread;
import model.MBCrypt;
import model.Users;
import model.Utilities;

public class UsersDao {
	
	private static List<Users> userList = null;

	public List<Users> getUsers() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Users as u "
        		+ "join fetch u.tipos as t";
        Query q = session.createQuery(hql);
        java.util.List<Users> users = q.list();
        for (Users user : users) {
            System.out.println(user.toString());
        }
        session.close();
        return users;
	}
	
	public Users checkLoginAndroid(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Users as u "
				    + "join fetch u.tipos as t "
					+ "where u.email = :email and "
		            + "(u.tipos.name = 'profesor' or u.tipos.name = 'alumno')";
		Query q = session.createQuery(hql);
		q.setParameter("email", email);
		Users user = (Users) q.uniqueResult();
		session.close();
		return user;
	}
	
	public Users checkLoginJava(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Users as u "
					+ "join fetch u.tipos as t "
					+ "where u.email = :email and "
					+ "u.tipos.name = 'profesor'";
		Query q = session.createQuery(hql);
		q.setParameter("email", email);
		Users user = (Users) q.uniqueResult();
		session.close();
		return user;
	}
	
	public String changePwd(String email) {
		Boolean isUser = this.checkEmail(email);
		if (!isUser) {
			return "0";
		}else {
			Utilities util = new Utilities();
			String newPwd = util.generatePassword();
			Session session = HibernateUtil.getSessionFactory().openSession();
			String hql = "update Users set password = :newPwd where email = :email";
			Query q = session.createQuery(hql);
			q.setParameter("newPwd", newPwd);
			q.setParameter("email", email);
			session.beginTransaction();
			q.executeUpdate();
			session.getTransaction().commit();
			session.close();
			EmailThread emailThread = new EmailThread(email, newPwd);
			return "1";
		}
	}
	
	private boolean checkEmail(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Users as u " 
				   + "where u.email = :email"
				   + " and (u.tipos.name = 'profesor' or u.tipos.name = 'alumno')";
		Query q = session.createQuery(hql);
		q.setParameter("email", email);
	
		if (q.uniqueResult() == null) {
			session.close();
			return false;
		}
		session.close();
		return true;
	}
	
	public List<Users> getTeachers() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Users as u " 
				   + "join fetch u.tipos as t " 
			       + "where t.name = 'profesor'";
		Query q = session.createQuery(hql);
		List<Users> teachers = q.list();
		session.close();
		return teachers;
	}
	
	public List<Users> getUsersByTeacherId(String id){
		Session session = HibernateUtil.getSessionFactory().openSession();
	    String hql = "from Users as u " +
	                "join fetch u.tipos as t " +
	                "join fetch u.matriculacioneses as m " +
	                "join fetch m.ciclos as c " +
	                "join fetch c.moduloses as mo " +
	                "join fetch mo.horarioses as h " +
	                "where h.users.id = :id";
	    Query q = session.createQuery(hql);
	    q.setParameter("id", Integer.parseInt(id));
	    List<Users> users = q.list();
	    session.close();
	    userList = users;
	    return users;
	}
	
	public List<Users> getFilteredUsers(String ciclo, String curso){
		Session session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("userList: " + userList);
		System.out.println("ciclo: " + ciclo);
		System.out.println("curso: " + curso);
		String hql = "from Users as u " + 
					 "join fetch u.tipos as t " + 
					 "join fetch u.matriculacioneses as m " +
					 "join fetch m.id as mi " +
					 "join fetch m.ciclos as c " +
					 "join fetch c.moduloses as mo " + 
					 "join fetch mo.horarioses as h ";
		
		if (!ciclo.equals("0") && !curso.equals("0")) {
			hql += " where c.nombre = :ciclo and mi.curso = :curso";
		} else if (!ciclo.equals("0")) {
			hql += " where c.nombre = :ciclo";
        } else if (!curso.equals("0")) {
            hql += "where mi.curso = :curso";
        }
		
		Query q = session.createQuery(hql);
		
		if (!ciclo.equals("0") && !curso.equals("0")) {
			q.setParameter("ciclo", ciclo);
			q.setParameter("curso", curso);
		} else if (!ciclo.equals("0")) {
			q.setParameter("ciclo", ciclo);
		} else if (!curso.equals("0")) {
			q.setParameter("curso", curso);
		}
		
		List<Users> users = q.list();
		session.close();
		
		List<Users> ret = compareLists(users, userList);
	    return ret;
    }
	
	private List<Users> compareLists(List<Users> list1, List<Users> list2) {
		List<Users> ret = new ArrayList<Users>();
		for (Users user : list1) {
			for (Users user2 : list2) {
				if (user.getId() == user2.getId()) {
					ret.add(user);
				}
			}
		}
		return ret;
	}
	
	
	
	
}