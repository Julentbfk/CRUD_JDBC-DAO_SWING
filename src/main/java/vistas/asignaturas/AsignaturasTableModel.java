package vistas.asignaturas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.AsignaturaDAO;
import excepciones.DAOException;
import modelo.Asignatura;

public class AsignaturasTableModel extends AbstractTableModel{
	
	private AsignaturaDAO dao;
	private List<Asignatura> listaAsignaturas;
	
	public AsignaturasTableModel(AsignaturaDAO dao) {
		this.dao = dao;
		this.listaAsignaturas = new ArrayList<>();
	}
	
	public void update() throws DAOException{
		listaAsignaturas = dao.selectAll();
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}
	@Override
	public String getColumnName(int column) {
		if(column==0) {
			return "Identificador";
		}else {
			return "Nombre de la asignatura";
		}
	}

	@Override
	public int getRowCount() {
		return listaAsignaturas.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Asignatura a = listaAsignaturas.get(rowIndex);
		if(columnIndex == 0) {
			return a.getId();
		}else {
			return a.getNombre();
		}
	}
	
}
