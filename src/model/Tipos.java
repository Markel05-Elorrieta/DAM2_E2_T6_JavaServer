package model;
// Generated 15 ene 2025, 12:00:33 by Hibernate Tools 6.5.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Tipos generated by hbm2java
 */
public class Tipos implements java.io.Serializable {
	private static final long serialVersionUID = 439916244561573442L;

	private int id;
	private String name;
	private String nameEus;
	private Set userses = new HashSet(0);

	public Tipos() {
	}

	public Tipos(int id) {
		this.id = id;
	}

	public Tipos(int id, String name, String nameEus, Set userses) {
		this.id = id;
		this.name = name;
		this.nameEus = nameEus;
		this.userses = userses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEus() {
		return this.nameEus;
	}

	public void setNameEus(String nameEus) {
		this.nameEus = nameEus;
	}

	public Set getUserses() {
		return this.userses;
	}

	public void setUserses(Set userses) {
		this.userses = userses;
	}

}
