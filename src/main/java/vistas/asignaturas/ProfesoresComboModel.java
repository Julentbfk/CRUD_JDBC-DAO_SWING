package vistas.asignaturas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import dao.ProfesorDAO;
import excepciones.DAOException;
import modelo.Profesor;

public class ProfesoresComboModel extends DefaultComboBoxModel<ProfesorComboView> {

	private ProfesorDAO daoP;
	private List<Profesor> listaProfesores;
	
	public ProfesoresComboModel(ProfesorDAO daoP) {
		this.daoP = daoP;
		this.listaProfesores = new ArrayList<>();
	}
	
	public void update() throws DAOException {
		listaProfesores = daoP.selectAll();
		removeAllElements();
		for(Profesor p : listaProfesores) {
			addElement(new ProfesorComboView(p));
		}
	}
	
}
