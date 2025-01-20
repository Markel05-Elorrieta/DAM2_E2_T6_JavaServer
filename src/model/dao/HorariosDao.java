package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Horarios;


public class HorariosDao {
	
	public List<Horarios> getHorarios() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Horarios as h "
        		   + "join fetch h.modulos as m "
        		   + "join fetch h.users as u "
        		   + "join fetch u.tipos as t "
        		   + "join fetch m.ciclos as c";
        Query q = session.createQuery(hql);
        java.util.List<Horarios> horarios = q.list();
        for (Horarios horario : horarios) {
            System.out.println(horario.toString());
        }
        session.close();
        return horarios;
    }
	
	public List<Horarios> getHorarioByTeacherId(String id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Horarios as h " 
				   + "join fetch h.modulos as m " 
				   + "join fetch h.users as u "
				   + "join fetch u.tipos as t " 
				   + "join fetch m.ciclos as c " 
				   + "where u.id = :id";
		Query q = session.createQuery(hql);
		q.setParameter("id", id);
		java.util.List<Horarios> horarios = q.list();
		for (Horarios horario : horarios) {
            System.out.println(horario.toString());
        }
		session.close();
		return horarios;
	}
	
	public List<Horarios> getHorarioByStudentId(String id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Horarios as h " 
                   + "join fetch h.modulos as m " 
                   + "join fetch h.users as u "
                   + "join fetch u.tipos as t " 
                   + "join fetch m.ciclos as c " 
                   + "join fetch c.matriculaciones as mat " 
                   + "where mat.id = :id";
        Query q = session.createQuery(hql);
        q.setParameter("id", id);
        java.util.List<Horarios> horarios = q.list();
        for (Horarios horario : horarios) {
            System.out.println(horario.toString());
        }
        session.close();
        return horarios;
	}
}