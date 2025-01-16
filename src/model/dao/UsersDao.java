package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Users;

public class UsersDao {

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
	
	public Users checkLoginAndroid(String email, String password) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Users as u "
				    + "join fetch u.tipos as t "
					+ "where u.email = :email and "
					+ "u.password = :password and "
		            + "(u.tipos.name = 'profesor' or u.tipos.name = 'alumno')";
		Query q = session.createQuery(hql);
		q.setParameter("email", email);
		q.setParameter("password", password);
		Users user = (Users) q.uniqueResult();
		session.close();
		return user;
	}
	
	public Users checkLoginJava(String email, String password) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Users as u "
					+ "join fetch u.tipos as t "
					+ "where u.email = :email and "
					+ "u.password = :password and "
					+ "u.tipos.name = 'profesor'";
		Query q = session.createQuery(hql);
		q.setParameter("email", email);
		q.setParameter("password", password);
		Users user = (Users) q.uniqueResult();
		session.close();
		return user;
	}
	
}