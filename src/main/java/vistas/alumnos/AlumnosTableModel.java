package vistas.alumnos;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import dao.*;
import excepciones.DAOException;
import modelo.Alumno;

public class AlumnosTableModel extends AbstractTableModel{
	
	private AlumnoDAO alumnos;
	private List<Alumno> datos = new ArrayList<>();
	
	public AlumnosTableModel(AlumnoDAO alumnos) {
		this.alumnos = alumnos;
	}
	
	public void updateModel() throws DAOException {
		datos = alumnos.selectAll();
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column) {
		case 0: return "ID";
		case 1: return "Nombre";
		case 2: return "Apellidos";
		case 3: return "Fecha Nacimiento";
		default: return"";
		}
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}
	@Override
	public int getRowCount() {
		
		return datos.size();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Alumno preguntado = datos.get(rowIndex);
		switch(columnIndex) {
		case 0: return preguntado.getId();
		case 1: return preguntado.getNombre();
		case 2: return preguntado.getApellidos();
		case 3: 
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			 return preguntado.getFechadenacimiento().format(formatter);
		default: return "";
		}
		
		
	}

	
}
