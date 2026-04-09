package implementacionesPOSTGRESQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.Conexion;
import dao.MatriculaDAO;
import excepciones.DAOException;
import modelo.Alumno;
import modelo.Asignatura;
import modelo.Matricula;
import modelo.Matricula.IdMatricula;

public class MatriculaPostgreDao implements MatriculaDAO{
	private Connection con;
	final String INSERT = "INSERT INTO matriculas (alumno, asignatura, fecha, nota) VALUES (?,?,?,?)";
	final String UPDATE = "UPDATE matriculas SET alumno = ?, asignatura = ?, fecha= ? , nota = ? WHERE alumno = ? AND asignatura = ?";
	final String DELETE = "DELETE FROM matriculas WHERE alumno = ?";
	final String SELECTALL ="SELECT alumno, asignatura, fecha, nota FROM matriculas";
	final String SELECTONE =SELECTALL+" WHERE alumno = ? AND asignatura = ? AND fecha = ?";
	final String SELECTALUMNO = SELECTALL+ " WHERE alumno = ?";
	final String SELECTASIGNATURA = SELECTALL+ " WHERE asignatura = ?";
	final String SELECTYEAR = SELECTALL+ " WHERE fecha = ?";

	
	public MatriculaPostgreDao(Connection con) {
		this.con = con;
	}
	
	@Override
	public void insert(Matricula a) throws DAOException {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(INSERT);
			ps.setLong(1, a.getId().getAlumno());
			ps.setLong(2, a.getId().getAsignatura());
			ps.setInt(3, a.getId().getYear());
			ps.setInt(4, a.getNota());
			
			if(a.getNota() != null) {
				ps.setInt(4, a.getNota());
			}else {
				ps.setNull(4, Types.INTEGER);
			}
			
			if(ps.executeUpdate() == 0) {
				throw new DAOException("ESTO NO HA INSERTADO NA");
			}
			
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL",e);
			
		}finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					throw new DAOException("ERROR EN SQL",e);
				}
			}
		}
		
		
	}
	@Override
	public void update(Matricula a) throws DAOException {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(UPDATE);
			ps.setLong(1, a.getId().getAlumno());
			ps.setLong(2, a.getId().getAsignatura());
			ps.setInt(3, a.getId().getYear());
			ps.setInt(4, a.getNota());
			ps.setLong(5, a.getId().getAlumno());
			ps.setLong(6, a.getId().getAsignatura());
			
			if(ps.executeUpdate() == 0) {
				throw new DAOException("Esto es posible que no haya updateu");
			}		
			
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL", e);
			
		}finally {
			if(ps != null) {			
				try {
					ps.close();
				}catch(SQLException e ) {
					throw new DAOException("ERROR EN SQL ",e);
				}
			}
		}
		
		
	}

	@Override
	public void delete(Matricula a) throws DAOException {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(DELETE);
			ps.setLong(1, a.getId().getAlumno());
			if(ps.executeUpdate() == 0) {
				throw new DAOException("Esto es posible que no haya deleteao");
			}
			
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL", e);
			
		}finally {
			if(ps != null) {			
				try {
					ps.close();
				}catch(SQLException e ) {
					throw new DAOException("ERROR EN SQL ",e);
				}
			}
		}
		
	}

	@Override
	public Matricula selectOne(IdMatricula pk) throws DAOException {
		PreparedStatement ps = null;		
		ResultSet rs = null;
		Matricula al = null;
		try {
			ps = con.prepareStatement(SELECTONE);
			ps.setLong(1,pk.getAlumno());
			ps.setLong(2,pk.getAsignatura());
			ps.setLong(3, pk.getYear());
			rs = ps.executeQuery();
			if(rs.next() == false) {
				throw new DAOException("No se encontro ese registro");
			}
			al = convertir(rs);
			
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL", e);
			
		}finally {
			if(ps != null && rs != null) {
				try {
					rs.close();
					ps.close();
				} catch (SQLException e) {
					throw new DAOException("ERROR EN SQL", e);
				}
			}
		}
		return al;
	}
	@Override
	public List<Matricula> selectAll() throws DAOException {
		List<Matricula> listaMatriculas = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = con.prepareStatement(SELECTALL);
			rs = st.executeQuery();
			
			if(rs.next() == false) {
				throw new DAOException("ERROR EN LA LECTURA DE LOS REGISTROS");
			}else {
				listaMatriculas.add(convertir(rs));
				while(rs.next()) {
					listaMatriculas.add(convertir(rs));
				}
			}
			
			
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL ", e);
			
		}finally {
			if(rs!=null && st !=null) {
				try {
					rs.close();
					st.close();
				} catch (SQLException e) {
					throw new DAOException("ERROR EN SQL", e);
				}
			}
		}
		return listaMatriculas;
	}
	@Override
	public List<Matricula> selectMatriculaAlumno(long alumno) throws DAOException{
		PreparedStatement ps = null;		
		ResultSet rs = null;
		List<Matricula> listaMatriculas = new ArrayList<>();
		try {
			ps = con.prepareStatement(SELECTALUMNO);
			ps.setLong(1,alumno);
			rs = ps.executeQuery();
			if(rs.next() == false) {
				throw new DAOException("No se encontro ese registro");
			}else {
				listaMatriculas.add(convertir(rs));
				while(rs.next()) {
					listaMatriculas.add(convertir(rs));
				}
			}
	
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL", e);
			
		}finally {
			if(ps != null && rs != null) {
				try {
					rs.close();
					ps.close();
				} catch (SQLException e) {
					throw new DAOException("ERROR EN SQL", e);
				}
			}
		}
		return listaMatriculas;
	}
	@Override
	public List<Matricula> selectMatriculaAsignatura(long asignatura) throws DAOException{
		PreparedStatement ps = null;		
		ResultSet rs = null;
		List<Matricula> listaMatriculas = new ArrayList<>();
		try {
			ps = con.prepareStatement(SELECTASIGNATURA);
			ps.setLong(1,asignatura);
			rs = ps.executeQuery();
			if(rs.next() == false) {
				throw new DAOException("No se encontro ese registro");
			}else {
				listaMatriculas.add(convertir(rs));
				while(rs.next()) {
					listaMatriculas.add(convertir(rs));
				}
			}
	
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL", e);
			
		}finally {
			if(ps != null && rs != null) {
				try {
					rs.close();
					ps.close();
				} catch (SQLException e) {
					throw new DAOException("ERROR EN SQL", e);
				}
			}
		}
		return listaMatriculas;
	}
	@Override
	public List<Matricula> selectMatriculaYear(int year) throws DAOException{
		PreparedStatement ps = null;		
		ResultSet rs = null;
		List<Matricula> listaMatriculas = new ArrayList<>();
		try {
			ps = con.prepareStatement(SELECTYEAR);
			ps.setLong(1,year);
			rs = ps.executeQuery();
			if(rs.next() == false) {
				throw new DAOException("No se encontro ese registro");
			}else {
				listaMatriculas.add(convertir(rs));
				while(rs.next()) {
					listaMatriculas.add(convertir(rs));
				}
			}
	
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL", e);
			
		}finally {
			if(ps != null && rs != null) {
				try {
					rs.close();
					ps.close();
				} catch (SQLException e) {
					throw new DAOException("ERROR EN SQL", e);
				}
			}
		}
		return listaMatriculas;
	}
	private Matricula convertir(ResultSet rs) throws SQLException {
		
		long alumno = rs.getLong("alumno");
		long asignatura = rs.getLong("asignatura");
		int fecha = rs.getInt("fecha");
		
		Integer nota = rs.getInt("nota");//Si no hay nota getInt le mete un 0 automaticamente y eso estaria mal asi que tenemos que revisarlo
		if(rs.wasNull()) nota = null;//con wasNull chequeo que el dato obtenido no sea null y si es asi le metemos un null
		
		Matricula matri = new Matricula(alumno,asignatura,fecha);
		matri.setNota(nota);
		return matri;
		
	}
	
}
