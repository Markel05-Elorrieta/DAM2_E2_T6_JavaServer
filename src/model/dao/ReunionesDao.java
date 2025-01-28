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
	
	public List<Reuniones> getReunionesByTeacher(String id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Reuniones as r " +
		             "join fetch r.usersByProfesorId as up " +
		             "join fetch r.usersByAlumnoId as ua " +
		             "join fetch up.tipos as tp " +
		             "join fetch ua.tipos as ta " +
		             "where up.id = :id";	
		Query q = session.createQuery(hql);
		q.setParameter("id", Integer.parseInt(id));
		List<Reuniones> reuniones = q.list();
		session.close();
		return reuniones;
	}
	
	public List<Reuniones> getReunionesByStudent(String id){
		Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Reuniones as r " +
                     "join fetch r.usersByProfesorId as up " +
                     "join fetch r.usersByAlumnoId as ua " +
                     "join fetch up.tipos as tp " +
                     "join fetch ua.tipos as ta " +
                     "where ua.id = :id";	
        Query q = session.createQuery(hql);
        q.setParameter("id", Integer.parseInt(id));
        List<Reuniones> reuniones = q.list();
        session.close();
        return reuniones;
	}
}
