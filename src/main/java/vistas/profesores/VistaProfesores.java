package vistas.profesores;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import dao.DAOManager;
import dao.ProfesorDAO;
import excepciones.DAOException;
import implementacionesPOSTGRESQL.PostgresDAOManager;
import modelo.Profesor;
import vistas.alumnos.DetalleAlumnoPanel;

//FORMA CON EL DAOMANAGER CON LOGIN CONCRETO
/*public class VistaProfesores extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//Propio
	private DAOManager manager;
	private ProfesoresTableModel model;
	private DetalleProfesorPanel detallePanel;
	private Profesor profesor;
	//Componentes
		private JPanel contentPane;
		private JButton btn_save;
		private JButton btn_update;
		private JButton btn_delete;
		private JButton btn_cancel;
		private JButton btn_insert;
		private JToolBar toolbar;
		private JTable tablaresultados;
		
	public VistaProfesores() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1022, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		toolbar = new JToolBar();
		contentPane.add(toolbar, BorderLayout.NORTH);
		
		detallePanel = new DetalleProfesorPanel();
		detallePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(detallePanel,BorderLayout.EAST);
		detallePanel.setPreferredSize(new Dimension(300, 0));
		detallePanel.setEditable(false);
		
		
		//--------INSERT-------------
		btn_insert = new JButton("INSERT");
		btn_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablaresultados.clearSelection();
				btn_save.setEnabled(true);
				btn_cancel.setEnabled(true);
				detallePanel.setProfesor(null);
				detallePanel.loadData();
				detallePanel.setEditable(true);
				
			}
		});
		toolbar.add(btn_insert);
		//----------UPDATE--------------
		btn_update = new JButton("UPDATE");
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					profesor = getProfesorSeleccionado();
					detallePanel.setProfesor(profesor);
					detallePanel.loadData();
					btn_cancel.setEnabled(true);
					btn_save.setEnabled(true);
				} catch (DAOException e1) {
					new DAOException("Error al cargar los datos del profesor seleccionado",e1);
				}
			}
		});
		btn_update.setEnabled(false);
		toolbar.add(btn_update);
		//----------DELETE--------------
		btn_delete = new JButton("DELETE");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//METODO PARA LANZAR EMERGENTE (PanelPrincipal,PREGUNTA A RESPONDER,TITULO DEL EMERGENTE,TIPO DE OPCION,CONDICION DE QUE SE LANCE LA OPCION)
				if(JOptionPane.showConfirmDialog(rootPane, "¿Seguro que quieres borrar este profe?","Borrar profe",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
	
					try {
						Profesor p = getProfesorSeleccionado();
						manager.getProfesorDAO().delete(p);
						model.updateModel();
						model.fireTableDataChanged();
						btn_delete.setEnabled(false);
						btn_update.setEnabled(false);
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		btn_delete.setEnabled(false);
		toolbar.add(btn_delete);
		//----------SAVE--------------
		btn_save = new JButton("SAVE");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detallePanel.saveData();//Lanzo el metodo que recogera los datos del panel, si es null crea un profe si no lo rellena 
				Profesor p = detallePanel.getProfesor();
				try {
					if(p.getId()==null) {
						manager.getProfesorDAO().insert(p);
					}else {
						manager.getProfesorDAO().update(p);
					}
					detallePanel.setProfesor(null);
					detallePanel.setEditable(false);
					detallePanel.loadData();
					tablaresultados.clearSelection();
					btn_save.setEnabled(false);
					btn_cancel.setEnabled(false);
					actualizarTabla();
				}catch(DAOException ex) {
					new DAOException("ERROR AL INSERTAR O UPDATIAR");
				}
			}
			
		});
		btn_save.setEnabled(false);
		toolbar.add(btn_save);
		//----------CANCEL--------------
		btn_cancel = new JButton("CANCEL");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detallePanel.setProfesor(null);
				detallePanel.loadData();
				detallePanel.setEditable(false);
				btn_update.setEnabled(false);
				btn_save.setEnabled(false);
				btn_cancel.setEnabled(false);
				btn_delete.setEnabled(false);
				detallePanel.requestFocus();//Para borrar la linea del focus anterior cuando el item se deshabilite
				tablaresultados.clearSelection();//Para borrar la seleccion
			}
		});
		btn_cancel.setEnabled(false);
		toolbar.add(btn_cancel);
		
		tablaresultados = new JTable();
		contentPane.add(tablaresultados, BorderLayout.CENTER);

	}

	public VistaProfesores(DAOManager manager) throws DAOException {
		this();
		this.manager = manager;
		
		this.model = new ProfesoresTableModel(manager.getProfesorDAO());
		this.tablaresultados.setModel(model);
		
		tablaresultados.getSelectionModel().addListSelectionListener(e -> {
			boolean seleccionValida = (tablaresultados.getSelectedRow() != -1);
			detallePanel.setEditable(seleccionValida);
			btn_update.setEnabled(seleccionValida);
			btn_delete.setEnabled(seleccionValida);	
		});
		
		actualizarTabla();
	}
	
	
	//Metodo para seleccionar el profesor de la tabla y devolverlo
	private Profesor getProfesorSeleccionado() throws DAOException {
	    int fila = tablaresultados.getSelectedRow();

	    if (fila == -1) {
	        throw new DAOException("No hay fila seleccionada");
	    }

	    Object value = tablaresultados.getValueAt(fila, 0);

	    Long id = ((Number) value).longValue();

	    return manager.getProfesorDAO().selectOne(id);
	}
	
	//Metodo que lanza los cambios de la tabla en vivo
	void actualizarTabla() throws DAOException {
		model.updateModel();
		model.fireTableDataChanged();
	}

	public static void main(String[] args) throws SQLException {
		DAOManager manager = new PostgresDAOManager();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaProfesores frame = new VistaProfesores(manager);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}*/
public class VistaProfesores extends JFrame {

    private DAOManager manager;
    private ProfesoresTableModel model;
    private DetalleProfesorPanel detallePanel;

    private JTable tabla;
    private JButton btnInsert, btnUpdate, btnDelete, btnSave, btnCancel;

    public VistaProfesores(DAOManager manager) throws DAOException {
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.manager = manager;

        initUI();
        initModel();
        initEventos();
    }
    
    private void initUI() {
        setTitle("Profesores");
        setSize(1000, 600);
        getContentPane().setLayout(new BorderLayout());

        JToolBar toolbar = new JToolBar();
        getContentPane().add(toolbar, BorderLayout.NORTH);

        btnInsert = new JButton("INSERT");
        btnUpdate = new JButton("UPDATE");
        btnDelete = new JButton("DELETE");
        btnSave = new JButton("SAVE");
        btnCancel = new JButton("CANCEL");

        toolbar.add(btnInsert);
        toolbar.add(btnUpdate);
        toolbar.add(btnDelete);
        toolbar.add(btnSave);
        toolbar.add(btnCancel);

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);

        tabla = new JTable();
        getContentPane().add(tabla, BorderLayout.CENTER);

        detallePanel = new DetalleProfesorPanel();
        detallePanel.setPreferredSize(new Dimension(300, 0));
        detallePanel.setEditable(false);
        getContentPane().add(detallePanel, BorderLayout.EAST);
    }
    
    private void initModel() throws DAOException {
        model = new ProfesoresTableModel(manager.getProfesorDAO());
        model.updateModel();
        tabla.setModel(model);
    }
    
    private void initEventos() {

        tabla.getSelectionModel().addListSelectionListener(e -> {
            boolean seleccionado = tabla.getSelectedRow() != -1;

            btnUpdate.setEnabled(seleccionado);
            btnDelete.setEnabled(seleccionado);
        });

        btnInsert.addActionListener(e -> {
            detallePanel.setProfesor(null);
            detallePanel.loadData();
            detallePanel.setEditable(true);

            btnSave.setEnabled(true);
            btnCancel.setEnabled(true);
        });

        btnUpdate.addActionListener(e -> {
            try {
                Profesor p = getSeleccionado();
                detallePanel.setProfesor(p);
                detallePanel.loadData();
                detallePanel.setEditable(true);

                btnSave.setEnabled(true);
                btnCancel.setEnabled(true);

            } catch (DAOException ex) {
                ex.printStackTrace();
            }
        });

        btnSave.addActionListener(e -> {
            try {
                detallePanel.saveData();
                Profesor p = detallePanel.getProfesor();

                if (p.getId() == null) {
                    manager.getProfesorDAO().insert(p);
                } else {
                    manager.getProfesorDAO().update(p);
                }

                refresh();

            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                Profesor p = getSeleccionado();
                manager.getProfesorDAO().delete(p);
                refresh();
            } catch (DAOException ex) {
                ex.printStackTrace();
            }
        });

        btnCancel.addActionListener(e -> reset());
    }
    
    private Profesor getSeleccionado() throws DAOException {
        int fila = tabla.getSelectedRow();
        Long id = ((Number) tabla.getValueAt(fila, 0)).longValue();
        return manager.getProfesorDAO().selectOne(id);
    }

    private void refresh() throws DAOException {
        model.updateModel();
        model.fireTableDataChanged();
        reset();
    }

    private void reset() {
        detallePanel.setProfesor(null);
        detallePanel.loadData();
        detallePanel.setEditable(false);

        tabla.clearSelection();

        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }
}

