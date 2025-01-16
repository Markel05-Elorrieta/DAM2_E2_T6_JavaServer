package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Modulos;


public class ModulosDao {
	
	public List<Modulos> getModulos(){
		Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Modulos as m "
        		   + "join fetch m.ciclos";
        Query q = session.createQuery(hql);
        java.util.List<Modulos> modulos = q.list();
        for (Modulos modulo : modulos) {
            System.out.println(modulo.toString());
        }
        session.close();
        return modulos;
    }
}
