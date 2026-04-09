package vistas.alumnos;

import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Alumno;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

public class DetalleAlumnoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField field_nombre;
	private JTextField field_apellidos;
	private JLabel label_apellidos;
	private JLabel label_nombre;
	private JLabel label_fechanac;
	private JFormattedTextField field_fechanac;
	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	/**
	 * Create the frame.
	 */
	public DetalleAlumnoPanel() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		label_nombre = new JLabel("Nombre");
		label_nombre.setBounds(10, 104, 127, 27);
		add(label_nombre);
		
		label_apellidos = new JLabel("Apellidos");
		label_apellidos.setBounds(10, 165, 127, 27);
		add(label_apellidos);
		
		label_fechanac = new JLabel("Fecha de Nacimiento:");
		label_fechanac.setBounds(10, 222, 127, 27);
		add(label_fechanac);
		
		field_nombre = new JTextField();
		field_nombre.setBounds(170, 107, 235, 20);
		add(field_nombre);
		
		field_apellidos = new JTextField();
		field_apellidos.setBounds(170, 168, 235, 20);
		add(field_apellidos);
		
		field_fechanac = new JFormattedTextField();
		field_fechanac.setBounds(170, 225, 235, 20);
		add(field_fechanac);

	}
	//CODIGO PROPIO
	private Alumno alumno;
	private boolean editable;

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		field_nombre.setEditable(editable);
		field_apellidos.setEditable(editable);
		field_fechanac.setEditable(editable);
	}
	public void loadData() {
		if(alumno != null) {
			field_nombre.setText(alumno.getNombre());
			field_apellidos.setText(alumno.getApellidos());
			field_fechanac.setText(alumno.getFechadenacimiento().format(format));
		}else {
			field_nombre.setText("");
			field_apellidos.setText("");
			field_fechanac.setText("");
		}
		field_nombre.requestFocus();
	}
	public void saveData() {
		if(alumno == null) {
			alumno = new Alumno();
		}
		alumno.setNombre(field_nombre.getText());
		alumno.setApellidos(field_apellidos.getText());
		alumno.setFechadenacimiento(LocalDate.parse(field_fechanac.getText(),format));
	}
	
	
}
