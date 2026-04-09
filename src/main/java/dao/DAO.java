package dao;

import java.util.List;

import excepciones.DAOException;
import modelo.Alumno;

public interface DAO<T,PK> {
	
	void insert(T a) throws DAOException;
	List<T> selectAll() throws DAOException;
	void update(T a) throws DAOException;
	void delete(T a) throws DAOException;
	
	T selectOne(PK pk) throws DAOException;

}
