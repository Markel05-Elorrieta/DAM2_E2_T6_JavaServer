package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Horarios;
import model.Matriculaciones;

public class MatriculacionesDao {
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
	
	public List<Matriculaciones> getMatriculaciones() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Matriculaciones as m "
        		   + "join fetch m.users as u "
        		   + "join fetch u.tipos as t "
        		   + "join fetch m.ciclos as c ";
        Query q = session.createQuery(hql);
        java.util.List<Matriculaciones> matriculaciones = q.list();
        for (Matriculaciones matriculacion : matriculaciones) {
            System.out.println(matriculacion.toString());
        }
        session.close();
        return matriculaciones;
	}
}
