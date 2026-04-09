package implementacionesPOSTGRESQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.Conexion;
import dao.ProfesorDAO;
import excepciones.DAOException;
import modelo.Alumno;
import modelo.Profesor;

public class ProfesorPostgreDao implements ProfesorDAO{
	private Connection con; 
	
	final String INSERT = "INSERT INTO profesores (nombre, apellidos ) VALUES (?,?)";
	final String UPDATE = "UPDATE profesores SET nombre = ?, apellidos = ? WHERE id_profesor= ? ";
	final String DELETE = "DELETE FROM profesores WHERE id_profesor = ?";
	final String SELECTONE ="SELECT id_profesor, nombre, apellidos FROM profesores WHERE id_profesor = ?";
	final String SELECTALL ="SELECT id_profesor, nombre, apellidos FROM profesores";
	
	public ProfesorPostgreDao(Connection con) {
		this.con = con;
	}

	@Override
	public void insert(Profesor a) throws DAOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(INSERT,PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, a.getNombre());
			ps.setString(2,a.getApellidos());
			
			if(ps.executeUpdate() == 0) {
				throw new DAOException("ESTO NO HA INSERTADO NA");
			}
			
			//RECUPERAMOS ID DEL PROFESOR
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				a.setId(rs.getLong(1));
			}else {
				throw new DAOException("ERROR AL ASIGNAR ID");
			}
			
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL",e);
			
		}finally {
			if(ps != null && rs != null) {
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
	public List<Profesor> selectAll() throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Profesor> profesoresListados = new ArrayList<>();
		
		try {
			ps = con.prepareStatement(SELECTALL);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Profesor p = new Profesor(rs.getLong("id_profesor"), rs.getString("nombre"),rs.getString("apellidos"));
				profesoresListados.add(p);
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
		
		return profesoresListados;
	}

	@Override
	public void update(Profesor a) throws DAOException {
		PreparedStatement ps  = null;
		try {
			ps = con.prepareStatement(UPDATE);
			ps.setString(1,a.getNombre());
			ps.setString(2,a.getApellidos());
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
	public void delete(Profesor a) throws DAOException {
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
	public Profesor selectOne(Long pk) throws DAOException {
		PreparedStatement ps = null;		
		ResultSet rs = null;
		Profesor pr = null;
		try {
			ps = con.prepareStatement(SELECTONE);
			ps.setLong(1, pk);
			rs = ps.executeQuery();
			if(rs.next() == false) {
				throw new DAOException("No se encontro ese registro");
			}
			pr = new Profesor(rs.getLong("id_profesor"),rs.getString("nombre"),rs.getString("apellidos"));
			
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
	

	
	/*
	public static void main(String[] args) throws SQLException,DAOException {
		Connection con = Conexion.conectar();
		ProfesorDAO profe = new ProfesorPostgreDao(con);
		
		
		//INSERT
		Profesor p = new Profesor("Cristo","Redentor");
		profe.insert(p);
		
		//UPDATE
		profe.update(new Profesor(7L,"Pazuzu","Codorniu"));
		
		//SELECTALL
		List<Profesor> listaProfesores = profe.selectAll();
		for(Profesor pr: listaProfesores) {
			System.out.println(pr.toString());
		}
		
		//SELECTONE
		System.out.println(profe.selectOne(new Long(1)));
		
		//DELETE
		Profesor p = profe.selectOne(8L);
		profe.delete(p);
	}
	*/

}
