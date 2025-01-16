package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Ciclos;

public class CiclosDao {
	
	public List<Ciclos> getCiclos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Ciclos";
		Query q = session.createQuery(hql);
		java.util.List<Ciclos> ciclos = q.list();
		for (Ciclos ciclo : ciclos) {
			System.out.println(ciclo.toString());
		}
		session.close();
		return ciclos;
	}
}
