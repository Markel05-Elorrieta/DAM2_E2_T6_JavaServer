// default package
// Generated 15 ene 2025, 9:25:34 by Hibernate Tools 6.5.1.Final
package model;
/**
 * Horarios generated by hbm2java
 */
public class Horarios implements java.io.Serializable {

	private HorariosId id;
	private Users users;
	private Modulos modulos;

	public Horarios() {
	}

	public Horarios(HorariosId id, Users users, Modulos modulos) {
		this.id = id;
		this.users = users;
		this.modulos = modulos;
	}

	public HorariosId getId() {
		return this.id;
	}

	public void setId(HorariosId id) {
		this.id = id;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Modulos getModulos() {
		return this.modulos;
	}

	public void setModulos(Modulos modulos) {
		this.modulos = modulos;
	}

}