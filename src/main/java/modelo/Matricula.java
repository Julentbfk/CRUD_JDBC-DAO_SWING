package modelo;

import java.util.Objects;

public class Matricula {
	
	public class IdMatricula{
		private long alumno;
		
		private long asignatura;
		private int year;
		
		public IdMatricula(long alumno,long asignatura,int year) {
			this.alumno = alumno;
			this.asignatura = asignatura;
			this.year = year;
		}
		public long getAlumno() {
			return alumno;
		}
		public void setAlumno(long alumno) {
			this.alumno = alumno;
		}
		public long getAsignatura() {
			return asignatura;
		}
		public void setAsignatura(long asignatura) {
			this.asignatura = asignatura;
		}
		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}
		private Matricula getEnclosingInstance() {
			return Matricula.this;
		}
		
		@Override
		public String toString() {
			return "IdMatricula [alumno=" + alumno + ", asignatura=" + asignatura + ", year=" + year + "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(alumno, asignatura, year);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			IdMatricula other = (IdMatricula) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return alumno == other.alumno && asignatura == other.asignatura && year == other.year;
		}
	}
	
	private IdMatricula id;
	private Integer nota = null;
	
	public Matricula(long alumno,long asignatura, int year) {
		this.id = new IdMatricula(alumno,asignatura,year);
	}
	
	public Integer getNota() {
		return nota;
	}
	public void setNota(Integer nota) {
		this.nota = nota;
	}
	public IdMatricula getId() {
		return id;
	}
	public void setId(IdMatricula id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Matricula [id=" + id + ", nota=" + nota + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, nota);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matricula other = (Matricula) obj;
		return Objects.equals(id, other.id) && Objects.equals(nota, other.nota);
	}
	
	
}
