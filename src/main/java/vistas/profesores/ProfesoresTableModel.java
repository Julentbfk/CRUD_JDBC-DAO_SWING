package vistas.profesores;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.ProfesorDAO;
import excepciones.DAOException;
import modelo.Profesor;

public class ProfesoresTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	private ProfesorDAO p;
	private List<Profesor> listaProfesores = new ArrayList<>();
	
	public ProfesoresTableModel(ProfesorDAO p) {
		this.p = p;
	}
	//CARGAMOS LA LISTA DE PROFESORES 
	public void updateModel() throws DAOException {
		listaProfesores = p.selectAll();
	}
	
	//DEFINIMOS EL NUMERO DE COLUMNAS DE LA TABLA
	@Override
	public int getColumnCount() {
		return 3;
	}
	//DEFINIMOS EL NUMERO DE TUPLAS DE LA TABLA
	@Override
	public int getRowCount() {
		return listaProfesores.size();
	}
	//DEFINIMOS EL FORMATO DE IMPRESION DICIENDOLE QUE VALOR VA EN CADA COLUMNA
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Profesor preguntado = listaProfesores.get(rowIndex);
		switch(columnIndex) {
		case 0: return preguntado.getId();
		case 1: return preguntado.getNombre();
		case 2: return preguntado.getApellidos();
		default: return "";
		}
	}
	//DEFINIMOS EL FORMATO DE IMPRESION DICIENDOLE EL TITULO DE CADA COLUMNA
	@Override
	public String getColumnName(int column) {
		switch(column) {
		case 0: return "ID";
		case 1: return "Nombre";
		case 2: return "Apellidos";
		default: return"";
		}
	}
}
