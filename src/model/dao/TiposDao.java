package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Tipos;
import model.Users;

public class TiposDao {
	public List<Tipos> getTipos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Tipos";
        Query q = session.createQuery(hql);
        java.util.List<Tipos> tipos = q.list();
        for (Tipos tipo : tipos) {
            System.out.println(tipo.toString());
        }
        session.close();
        return tipos;
	}
}
