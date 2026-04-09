package implementacionesPOSTGRESQL;

import java.sql.*;
import java.util.List;

import dao.*;
import excepciones.DAOException;
import modelo.*;

public class PostgresDAOManager implements DAOManager{
	

	private Connection con;
	public PostgresDAOManager(String user, String pass) throws SQLException {
		con = Conexion.conectar(user,pass);
	}
	
	private AlumnoDAO alumnos = null;
	private ProfesorDAO profesores = null;
	private MatriculaDAO matriculas = null;
	private AsignaturaDAO asignaturas = null;
	
	@Override
	public AlumnoDAO getAlumnoDAO() {
		if(alumnos == null) {
			alumnos = new AlumnoPostgreDao(con);
		}
		return alumnos;
	}

	@Override
	public AsignaturaDAO getAsignaturaDAO() {
		if(asignaturas == null) {
			asignaturas = new AsignaturaPostgreDao(con);
		}
		return asignaturas;
	}

	@Override
	public MatriculaDAO getMatriculaDAO() {
		if(matriculas == null) {
			matriculas = new MatriculaPostgreDao(con);
			
		}
		return matriculas;
	}

	@Override
	public ProfesorDAO getProfesorDAO() {
		if(profesores == null) {
			profesores = new ProfesorPostgreDao(con);
		}
		return profesores;
	}
	
}
