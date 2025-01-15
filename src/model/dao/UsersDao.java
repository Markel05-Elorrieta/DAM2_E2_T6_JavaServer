package model.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Users;

public class UsersDao {
	
	public Users getUserById(String stID) {
		int userID = Integer.parseInt(stID);
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Users as u where u.id = :id";
		Query q = session.createQuery(hql);
		q.setParameter("id", userID);
		Users user = (Users) q.uniqueResult();
		System.out.println(user.toString());
		session.close();
		return user;
	}
	
	public void getUsers() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Users";
        Query q = session.createQuery(hql);
        java.util.List<Users> users = q.list();
        for (Users user : users) {
            System.out.println(user.toString());
        }
        session.close();
	}
}