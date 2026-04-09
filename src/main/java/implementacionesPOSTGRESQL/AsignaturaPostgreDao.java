package implementacionesPOSTGRESQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.AsignaturaDAO;
import dao.Conexion;
import excepciones.DAOException;
import modelo.Asignatura;
import modelo.Profesor;

public class AsignaturaPostgreDao implements AsignaturaDAO{
	private Connection con;
	final String INSERT = "INSERT INTO asignaturas (nombre, profesor) VALUES (?,?)";
	final String UPDATE = "UPDATE asignaturas SET nombre = ?, profesor = ? WHERE id_asignatura = ? ";
	final String DELETE = "DELETE FROM asignaturas WHERE id_asignatura = ?";
	final String SELECTONE ="SELECT id_asignatura, nombre, profesor FROM asignaturas WHERE id_asignatura = ?";
	final String SELECTALL ="SELECT id_asignatura, nombre, profesor FROM asignaturas";
	
	public AsignaturaPostgreDao(Connection con) {
		this.con = con;
	}
	
	@Override
	public void insert(Asignatura a) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(INSERT,PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, a.getNombre());
			ps.setLong(2,a.getIdProfesor());
			
			if(ps.executeUpdate() == 0) {
				throw new DAOException("ESTO NO HA INSERTADO NA");
			}
			//RECUPERAMOS ID DEL ALUMNO
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				a.setId(rs.getLong(1));
			}else {
				throw new DAOException("ERROR AL ASIGNAR ID");
			}
			
			
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL",e);
			
		}finally {
			if(ps != null && rs != null){
				try {
					rs.close();
					ps.close();
				} catch (SQLException e) {
					throw new DAOException("ERROR EN SQL",e);
				}
			}
		}
		
	}

	@Override
	public List<Asignatura> selectAll() throws DAOException {
		List<Asignatura> asignaturas = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SELECTALL);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Asignatura p = new Asignatura(rs.getLong("id_asignatura"), rs.getString("nombre"),rs.getLong("profesor"));
				asignaturas.add(p);
			}
			
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL",e);
			
		}finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				throw new DAOException("ERROR EN SQL",e);
			}
		}
		
		return asignaturas;

	}

	@Override
	public void update(Asignatura a) throws DAOException {
		PreparedStatement ps  = null;
		try {
			ps = con.prepareStatement(UPDATE);
			ps.setString(1,a.getNombre());
			ps.setLong(2,a.getIdProfesor());
			ps.setLong(3, a.getId());
			if(ps.executeUpdate() == 0) {
				throw new DAOException("Esto es posible que no haya updateu");
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
	public void delete(Asignatura a) throws DAOException {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(DELETE);
			ps.setLong(1, a.getId());
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
	public Asignatura selectOne(Long pk) throws DAOException {
		PreparedStatement ps = null;		
		ResultSet rs = null;
		Asignatura pr = null;
		try {
			ps = con.prepareStatement(SELECTONE);
			ps.setLong(1, pk);
			rs = ps.executeQuery();
			if(rs.next() == false) {
				throw new DAOException("No se encontro ese registro");
			}
			pr = new Asignatura(rs.getLong("id_asignatura"),rs.getString("nombre"),rs.getLong("profesor"));
			
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
		return pr;
	}
	

}
