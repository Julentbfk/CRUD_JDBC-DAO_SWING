package modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Alumno {
	private Long id = null;
	private String nombre;
	private String apellidos;
	private LocalDate fechadenacimiento;
	
	public Alumno(String nombre, String apellidos, LocalDate fechadenacimiento) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechadenacimiento = fechadenacimiento;
	}
	public Alumno(Long id,String nombre, String apellidos, LocalDate fechadenacimiento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechadenacimiento = fechadenacimiento;
	}
	
	public Alumno() {
		this.nombre = "";
		this.apellidos = "";
		this.fechadenacimiento = LocalDate.MAX;

	}
	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", fechadenacimiento="
				+ fechadenacimiento + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(apellidos, fechadenacimiento, id, nombre);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(fechadenacimiento, other.fechadenacimiento)
				&& Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public LocalDate getFechadenacimiento() {
		return fechadenacimiento;
	}
	public void setFechadenacimiento(LocalDate fechadenacimiento) {
		this.fechadenacimiento = fechadenacimiento;
	}
}
