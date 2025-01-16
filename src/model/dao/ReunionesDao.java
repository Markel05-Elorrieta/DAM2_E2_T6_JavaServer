package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Reuniones;

public class ReunionesDao {
	public List<Reuniones> getReuniones() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Reuniones";
        Query q = session.createQuery(hql);
        java.util.List<Reuniones> reuniones = q.list();
        for (Reuniones reunion : reuniones) {
            System.out.println(reunion.toString());
        }
        session.close();
        return reuniones;
	}
}
