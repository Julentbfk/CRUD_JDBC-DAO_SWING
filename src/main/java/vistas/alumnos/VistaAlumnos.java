package vistas.alumnos;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.*;
import excepciones.DAOException;
import implementacionesPOSTGRESQL.PostgresDAOManager;
import modelo.Alumno;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;

/*

public class VistaAlumnos extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	//PROPIOS
	private DAOManager manager;
	private AlumnosTableModel model;
	private DetalleAlumnoPanel detallePanel;

	//Componentes
	private JPanel contentPane;
	private JButton btn_save;
	private JButton btn_update;
	private JButton btn_delete;
	private JButton btn_cancel;
	private JButton btn_insert;
	private JToolBar toolbar;
	private JTable tablaresultados;
	
	public VistaAlumnos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1022, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		toolbar = new JToolBar();
		contentPane.add(toolbar, BorderLayout.NORTH);
		
		detallePanel = new DetalleAlumnoPanel();
		detallePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(detallePanel,BorderLayout.EAST);
		detallePanel.setPreferredSize(new Dimension(300, 0));
		detallePanel.setEditable(false);
		
		btn_insert = new JButton("INSERT");
		btn_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				detallePanel.setAlumno(null);
				detallePanel.loadData();
				detallePanel.setEditable(true);
				btn_cancel.setEnabled(true);
				btn_save.setEnabled(true);
				
			}
		});
		
		toolbar.add(btn_insert);
		
		btn_update = new JButton("UPDATE");
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Alumno alumno = getAlumnoSeleccionado();
					detallePanel.setAlumno(alumno);
					detallePanel.setEditable(true);
					detallePanel.loadData();
					
					btn_save.setEnabled(true);
					btn_cancel.setEnabled(true);
				}catch(DAOException ex) {
					Logger.getLogger(VistaAlumnos.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		
		btn_update.setEnabled(false);
		toolbar.add(btn_update);
		
		btn_delete = new JButton("DELETE");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(rootPane, "¿Seguro que quieres borrar este alumno?","Borrar alumno",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					try {
						Alumno a = getAlumnoSeleccionado();
						manager.getAlumnoDAO().delete(a);
						model.updateModel();
						model.fireTableDataChanged();
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_delete.setEnabled(false);
		toolbar.add(btn_delete);
		
		btn_save = new JButton("SAVE");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detallePanel.saveData();
				Alumno alu = detallePanel.getAlumno();
				try {	
					if(alu.getId() ==null) {
						manager.getAlumnoDAO().insert(alu);
					}else {
						manager.getAlumnoDAO().update(alu);
					}
					detallePanel.setAlumno(null);
					detallePanel.setEditable(false);
					detallePanel.loadData();
					tablaresultados.clearSelection();
					btn_save.setEnabled(false);
					btn_cancel.setEnabled(false);
					
					model.updateModel();//Recargamos el modelo con los datos que hemos cargados
					model.fireTableDataChanged();
					
					
				}catch(DAOException ex) {
					new DAOException("ERROR AL INSERTAR O UPDATEAR",ex);
				}
			}
		});
		btn_save.setEnabled(false);
		toolbar.add(btn_save);
		
		btn_cancel = new JButton("CANCEL");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				detallePanel.setAlumno(null);
				detallePanel.loadData();	
				detallePanel.setEditable(false);
				
				btn_cancel.setEnabled(false);
				btn_update.setEnabled(false);
				btn_delete.setEnabled(false);
				btn_save.setEnabled(false);
				detallePanel.requestFocus();//Para borrar la linea del focus anterior
			}
		});
		btn_cancel.setEnabled(false);
		toolbar.add(btn_cancel);
		
		tablaresultados = new JTable();
		contentPane.add(tablaresultados, BorderLayout.CENTER);

	}
	 
	
	public VistaAlumnos(DAOManager manager) throws DAOException {
		this();//Este llama al vacio y le pasa todo el content
		this.manager = manager;

		this.model = new AlumnosTableModel(manager.getAlumnoDAO());//Cargamos el modelo con los datos de los alumnos
		this.model.updateModel();//Recargamos el modelo con los datos que hemos cargados
		this.tablaresultados.setModel(model);//Cargamos la tabla con la informacion de nuestro modelo
		this.tablaresultados.getSelectionModel().addListSelectionListener(e ->{
			boolean seleccionValida = (tablaresultados.getSelectedRow() != -1); 
			detallePanel.setEditable(seleccionValida);
			btn_update.setEnabled(seleccionValida);	
			btn_delete.setEnabled(seleccionValida);
		});
	}
	
	//Metodo para que al seleccionar un alumno me lo pinte en el formulario detalle
	private Alumno getAlumnoSeleccionado() throws DAOException {
		
		Long id = (Long)tablaresultados.getValueAt(tablaresultados.getSelectedRow(), 0);
		
		return manager.getAlumnoDAO().selectOne(id);
	}

	
	
	//EJECUTOR
	public static void main(String[] args) throws SQLException {
		
		DAOManager manager = new PostgresDAOManager();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaAlumnos frame = new VistaAlumnos(manager);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}*/




public class VistaAlumnos extends JFrame {

    private DAOManager manager;
    private AlumnosTableModel model;
    private DetalleAlumnoPanel detallePanel;

    private JTable tablaresultados;
    private JButton btn_save, btn_update, btn_delete, btn_cancel, btn_insert;

    
    private void initUI() {
        setBounds(100, 100, 1022, 587);
        getContentPane().setLayout(new BorderLayout());

        JToolBar toolbar = new JToolBar();
        getContentPane().add(toolbar, BorderLayout.NORTH);

        detallePanel = new DetalleAlumnoPanel();
        detallePanel.setPreferredSize(new Dimension(300, 0));
        detallePanel.setEditable(false);
        getContentPane().add(detallePanel, BorderLayout.EAST);

        tablaresultados = new JTable();
        getContentPane().add(tablaresultados, BorderLayout.CENTER);

        btn_insert = new JButton("INSERT");
        btn_update = new JButton("UPDATE");
        btn_delete = new JButton("DELETE");
        btn_save = new JButton("SAVE");
        btn_cancel = new JButton("CANCEL");

        toolbar.add(btn_insert);
        toolbar.add(btn_update);
        toolbar.add(btn_delete);
        toolbar.add(btn_save);
        toolbar.add(btn_cancel);

        btn_update.setEnabled(false);
        btn_delete.setEnabled(false);
        btn_save.setEnabled(false);
        btn_cancel.setEnabled(false);
    }
    
    private void initModel() throws DAOException {
        model = new AlumnosTableModel(manager.getAlumnoDAO());
        model.updateModel();
        tablaresultados.setModel(model);
    }

    private void initEventos() {

        tablaresultados.getSelectionModel().addListSelectionListener(e -> {
            boolean seleccionValida = (tablaresultados.getSelectedRow() != -1);
            detallePanel.setEditable(seleccionValida);
            btn_update.setEnabled(seleccionValida);
            btn_delete.setEnabled(seleccionValida);
        });

        btn_insert.addActionListener(e -> {
            detallePanel.setAlumno(null);
            detallePanel.loadData();
            detallePanel.setEditable(true);
            btn_save.setEnabled(true);
            btn_cancel.setEnabled(true);
        });

        btn_save.addActionListener(e -> {
            try {
                detallePanel.saveData();
                Alumno alu = detallePanel.getAlumno();

                if (alu.getId() == null) {
                    manager.getAlumnoDAO().insert(alu);
                } else {
                    manager.getAlumnoDAO().update(alu);
                }

                refresh();

            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        btn_update.addActionListener(e -> {
            try {
                Alumno alumno = getAlumnoSeleccionado();
                detallePanel.setAlumno(alumno);
                detallePanel.loadData();
                detallePanel.setEditable(true);

                btn_save.setEnabled(true);
                btn_cancel.setEnabled(true);

            } catch (DAOException ex) {
                ex.printStackTrace();
            }
        });

        btn_delete.addActionListener(e -> {
            try {
                Alumno a = getAlumnoSeleccionado();
                manager.getAlumnoDAO().delete(a);
                refresh();
            } catch (DAOException ex) {
                ex.printStackTrace();
            }
        });

        btn_cancel.addActionListener(e -> reset());
    }
    private Alumno getAlumnoSeleccionado() throws DAOException {
        int fila = tablaresultados.getSelectedRow();
        Long id = ((Number) tablaresultados.getValueAt(fila, 0)).longValue();
        return manager.getAlumnoDAO().selectOne(id);
    }

    private void refresh() throws DAOException {
        model.updateModel();
        model.fireTableDataChanged();

        reset();
    }

    private void reset() {
        detallePanel.setAlumno(null);
        detallePanel.loadData();
        detallePanel.setEditable(false);

        tablaresultados.clearSelection();

        btn_save.setEnabled(false);
        btn_cancel.setEnabled(false);
        btn_update.setEnabled(false);
        btn_delete.setEnabled(false);
    }
    
    public VistaAlumnos(DAOManager manager) throws DAOException {
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.manager = manager;

        initUI();
        initModel();
        initEventos();
    }
    
}
