package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.DAOManager;
import excepciones.DAOException;
import vistas.alumnos.VistaAlumnos;
import vistas.asignaturas.VistaAsignaturas;
import vistas.profesores.VistaProfesores;

import javax.swing.JButton;

public class VistaMainmenu extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//Componentes
	private JPanel contentPane;
	private JButton btn_asignaturas,btn_profesores,btn_alumnos,btn_matriculas;
	
	//Propios
	DAOManager manager;

	public VistaMainmenu(DAOManager manager) {
		setTitle("Panel Principal");
		this.manager = manager;
		initUI();
		initEventos();
	}
	
	private void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 672, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btn_asignaturas = new JButton("ASIGNATURAS");
		btn_asignaturas.setBounds(28, 43, 286, 154);
		contentPane.add(btn_asignaturas);
		
		btn_alumnos = new JButton("ALUMNOS");
		btn_alumnos.setBounds(340, 43, 286, 154);
		contentPane.add(btn_alumnos);
		
		btn_matriculas = new JButton("soon....");
		btn_matriculas.setEnabled(false);
		btn_matriculas.setBounds(340, 238, 286, 154);
		contentPane.add(btn_matriculas);
		
		btn_profesores = new JButton("PROFESORES");
		btn_profesores.setBounds(28, 238, 286, 154);
		contentPane.add(btn_profesores);

	}
	
	private void initEventos() {
		
		 btn_asignaturas.addActionListener(e -> {
			 try {
				VistaAsignaturas vista= new VistaAsignaturas(manager);
				vista.setLocationRelativeTo(this);
				vista.setVisible(true);
			 } catch (DAOException e1) {
				new DAOException("Error al cargar la vista",e1);
			 }
	     });
		 btn_alumnos.addActionListener(e -> {
			 try {
				VistaAlumnos vista= new VistaAlumnos(manager);
				vista.setLocationRelativeTo(this);
				vista.setVisible(true);
			 } catch (DAOException e1) {
				new DAOException("Error al cargar la vista",e1);
			 }
	     });
		 btn_profesores.addActionListener(e -> {
			 try {
				VistaProfesores vista= new VistaProfesores(manager);
				vista.setLocationRelativeTo(this);
				vista.setVisible(true);
			 } catch (DAOException e1) {
				new DAOException("Error al cargar la vista",e1);
			 }
	     });
		 btn_matriculas.addActionListener(e -> {
			 //COMING SOON....
	     });
		
	}
	
}
