package model;
// Generated 15 ene 2025, 12:00:33 by Hibernate Tools 6.5.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Modulos generated by hbm2java
 */
public class Modulos implements java.io.Serializable {

	private int id;
	private Ciclos ciclos;
	private String nombre;
	private String nombreEus;
	private int horas;
	private Integer curso;
	private Set horarioses = new HashSet(0);

	public Modulos() {
	}

	public Modulos(int id, int horas) {
		this.id = id;
		this.horas = horas;
	}

	public Modulos(int id, Ciclos ciclos, String nombre, String nombreEus, int horas, Integer curso, Set horarioses) {
		this.id = id;
		this.ciclos = ciclos;
		this.nombre = nombre;
		this.nombreEus = nombreEus;
		this.horas = horas;
		this.curso = curso;
		this.horarioses = horarioses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ciclos getCiclos() {
		return this.ciclos;
	}

	public void setCiclos(Ciclos ciclos) {
		this.ciclos = ciclos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreEus() {
		return this.nombreEus;
	}

	public void setNombreEus(String nombreEus) {
		this.nombreEus = nombreEus;
	}

	public int getHoras() {
		return this.horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public Integer getCurso() {
		return this.curso;
	}

	public void setCurso(Integer curso) {
		this.curso = curso;
	}

	public Set getHorarioses() {
		return this.horarioses;
	}

	public void setHorarioses(Set horarioses) {
		this.horarioses = horarioses;
	}

}
