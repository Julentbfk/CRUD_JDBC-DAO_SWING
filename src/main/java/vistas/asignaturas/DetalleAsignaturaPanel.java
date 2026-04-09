package vistas.asignaturas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dao.DAOManager;
import excepciones.DAOException;
import implementacionesPOSTGRESQL.PostgresDAOManager;
import modelo.Asignatura;
import modelo.Profesor;

import java.sql.SQLException;

import javax.swing.JComboBox;

public class DetalleAsignaturaPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField field_asignatura;
	private JComboBox<ProfesorComboView> combo_profesor;
	private ProfesoresComboModel modelProfesores;
	private DAOManager manager;
	private Asignatura asignatura;
	private boolean editable;
	
	public void setAsignatura(Asignatura asignatura	) {
		this.asignatura = asignatura;
	}
	public Asignatura getAsignatura() {
		return asignatura;
	}
	
	
	public void setEditable(boolean editable) {
		this.editable = editable;
		this.field_asignatura.setEditable(editable);
		this.combo_profesor.setEditable(editable);
	}
	public boolean isEditable() {
		return editable;
	}
	
	public void loadData() {
	
		if(asignatura == null) {
		asignatura = new Asignatura("",null);
		}
		field_asignatura.setText(asignatura.getNombre());

		//Recuperamos al profesor
		for (int i = 0; i < combo_profesor.getItemCount(); i++) {
		    ProfesorComboView item = combo_profesor.getItemAt(i);

		    if (item.getProfesor().getId().equals(asignatura.getIdProfesor())) {
		        combo_profesor.setSelectedIndex(i);
		        break;
		    }
		}
	
		field_asignatura.requestFocus();
	}
	public void saveData() {
		if(asignatura == null) {
			asignatura = new Asignatura("",null);
		}
		asignatura.setNombre(field_asignatura.getText());
		ProfesorComboView view = (ProfesorComboView) combo_profesor.getSelectedItem();
		Profesor p = view.getProfesor();
	    if (p != null) {
	        asignatura.setIdProfesor(p.getId());
	    }
	}
	
	/*public DetalleAsignaturaPanel() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Asignatura");
		lblNewLabel.setBounds(10, 11, 118, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Profesor");
		lblNewLabel_1.setBounds(10, 53, 118, 14);
		add(lblNewLabel_1);
		
		field_asignatura = new JTextField();
		field_asignatura.setBounds(72, 8, 235, 20);
		add(field_asignatura);
		field_asignatura.setColumns(10);
		
		try {
			manager = new PostgresDAOManager();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelProfesores = new ProfesoresComboModel(manager.getProfesorDAO());
		combo_profesor = new JComboBox<>();
		combo_profesor.setModel(modelProfesores);
		try {
			modelProfesores.update();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		combo_profesor.setBounds(72, 49, 235, 22);
		add(combo_profesor);

	}*/
	public DetalleAsignaturaPanel(DAOManager manager) {
	    this.manager = manager;

	    setLayout(null);

	    JLabel lblNombre = new JLabel("Asignatura");
	    lblNombre.setBounds(10, 10, 100, 20);
	    add(lblNombre);

	    field_asignatura = new JTextField();
	    field_asignatura.setBounds(120, 10, 200, 20);
	    add(field_asignatura);

	    JLabel lblProfesor = new JLabel("Profesor");
	    lblProfesor.setBounds(10, 50, 100, 20);
	    add(lblProfesor);

	    combo_profesor = new JComboBox<>();
	    combo_profesor.setBounds(120, 50, 200, 20);
	    add(combo_profesor);

	    modelProfesores = new ProfesoresComboModel(manager.getProfesorDAO());
	    combo_profesor.setModel(modelProfesores);

	    try {
	        modelProfesores.update();
	    } catch (DAOException e) {
	        e.printStackTrace();
	    }
	}
}
