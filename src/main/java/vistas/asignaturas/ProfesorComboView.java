package vistas.asignaturas;

import java.util.Objects;

import modelo.Profesor;

public class ProfesorComboView {
	private Profesor profesor;

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public ProfesorComboView(Profesor profesor) {
		this.profesor = profesor;
	}

	@Override
	public int hashCode() {
		return profesor.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		return profesor.equals(obj);
	}

	@Override
	public String toString() {
		return profesor.getNombre() + " "+ profesor.getApellidos() + " " + profesor.getId();
	}
	
	
}
