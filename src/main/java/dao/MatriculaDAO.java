package dao;

import java.util.List;

import excepciones.DAOException;
import modelo.Matricula;

public interface MatriculaDAO extends DAO<Matricula,Matricula.IdMatricula>{
	
	public List<Matricula> selectMatriculaAlumno(long alumno) throws DAOException;
	public List<Matricula> selectMatriculaAsignatura(long asignatura) throws DAOException;
	public List<Matricula> selectMatriculaYear(int year) throws DAOException;
	
}
