package vistas.profesores;

import java.time.LocalDate;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelo.Alumno;
import modelo.Profesor;

public class DetalleProfesorPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField field_nombre;
	private JTextField field_apellidos;
	private JLabel label_apellidos;
	private JLabel label_nombre;

	public DetalleProfesorPanel() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		label_nombre = new JLabel("Nombre");
		label_nombre.setBounds(10, 104, 127, 27);
		add(label_nombre);
		
		label_apellidos = new JLabel("Apellidos");
		label_apellidos.setBounds(10, 165, 127, 27);
		add(label_apellidos);
		
		field_nombre = new JTextField();
		field_nombre.setBounds(170, 107, 235, 20);
		add(field_nombre);
		
		field_apellidos = new JTextField();
		field_apellidos.setBounds(170, 168, 235, 20);
		add(field_apellidos);
	
	}
	//Codigo Propio
	private Profesor profesor;
	private boolean editable;

	public Profesor getProfesor() {
		return profesor;
	}
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	public boolean isEditable() {
		return editable;
	}
	
	//DIRA QUE LOS CAMPOS SEAN MODIFICABLES O NO
	public void setEditable(boolean editable) {
		this.editable = editable;
		field_nombre.setEditable(editable);
		field_apellidos.setEditable(editable);	
	}
	//ESCRIBIRA EN LOS CAMPOS LOS DATOS DEL PROFESOR ESCOGIDO Y SI NO ES VALIDO ESCRIBIRA VACIO, USADO PARA LIMPIAR O IMPRIMIR LOS DATOS DEL USUARIO A UPDATEAR
	public void loadData() {
		if(profesor != null) {
			field_nombre.setText(profesor.getNombre());
			field_apellidos.setText(profesor.getApellidos());
		}else {
			field_nombre.setText("");
			field_apellidos.setText("");
		}
		field_nombre.requestFocus();
		
	}
	//RELLENARA UN PROFESOR CON LOS DATOS DE LOS CAMPOS, USADO PARA GUARDAR
	public void saveData() {
		if(profesor == null) {
			profesor = new Profesor();
		}
		profesor.setNombre(field_nombre.getText());
		profesor.setApellidos(field_apellidos.getText());
	}
}
