package implementacionesPOSTGRESQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.AlumnoDAO;
import dao.Conexion;
import excepciones.DAOException;
import modelo.Alumno;

public class AlumnoPostgreDao implements AlumnoDAO {
	
	private Connection con;
	final String INSERT = "INSERT INTO alumnos (nombre, apellidos, fecha_nac) VALUES (?,?,?)";
	final String UPDATE = "UPDATE alumnos SET nombre = ?, apellidos = ?, fecha_nac = ? WHERE id_alumno = ? ";
	final String DELETE = "DELETE FROM alumnos WHERE id_alumno = ?";
	final String SELECTONE ="SELECT id_alumno, nombre, apellidos, fecha_nac FROM alumnos WHERE id_alumno = ?";
	final String SELECTALL ="SELECT id_alumno, nombre, apellidos, fecha_nac FROM alumnos";
	
	public AlumnoPostgreDao(Connection con) {
		this.con = con;
	}

	@Override
	public void insert(Alumno a) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement(INSERT,PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, a.getNombre());
			ps.setString(2, a.getApellidos());
			ps.setDate(3, java.sql.Date.valueOf(a.getFechadenacimiento()));
			if(ps.executeUpdate() == 0) {
				throw new DAOException("No se ha guardado el INSERT probablemente");
			}
			
			//RECUPERAMOS ID DEL ALUMNO
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				a.setId(rs.getLong(1));
			}else {
				throw new DAOException("ERROR AL ASIGNAR ID");
			}
		
		}catch(SQLException e) {
			throw new DAOException("ERROR EN SQL", e);
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
	public void update(Alumno a) throws DAOException {
		
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(UPDATE);
			ps.setString(1,a.getNombre());
			ps.setString(2,a.getApellidos());
			ps.setDate(3, java.sql.Date.valueOf(a.getFechadenacimiento()));
			ps.setLong(4, a.getId());
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
	public void delete(Alumno a) throws DAOException {

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
	public Alumno selectOne(Long pk) throws DAOException{
		
		PreparedStatement ps = null;		
		ResultSet rs = null;
		Alumno al = null;
		try {
			ps = con.prepareStatement(SELECTONE);
			ps.setLong(1, pk);
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
	public List<Alumno> selectAll() throws DAOException {
		List<Alumno> listaAlumnos = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = con.prepareStatement(SELECTALL);
			rs = st.executeQuery();
			
			if(rs.next() == false) {
				throw new DAOException("ERROR EN LA LECTURA DE LOS REGISTROS");
			}else {
				listaAlumnos.add(convertir(rs));
				while(rs.next()) {
					listaAlumnos.add(convertir(rs));
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
		return listaAlumnos;
	}
	
	//METODO PARA SACAR UN ALUMNO DE UN RESULTSET
	
	private Alumno convertir(ResultSet rs) throws SQLException {
		
		String nombre = rs.getString("nombre");
		String apellidos = rs.getString("apellidos");
		Long id = rs.getLong("id_alumno");
		LocalDate fechanacimiento = rs.getDate("fecha_nac").toLocalDate();
		
		return new Alumno(id,nombre,apellidos,fechanacimiento);
		
	}
	
}
