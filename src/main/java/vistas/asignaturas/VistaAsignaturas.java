package vistas.asignaturas;

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
import excepciones.DAOException;
import implementacionesPOSTGRESQL.PostgresDAOManager;
import modelo.Asignatura;

/*public class VistaAsignaturas extends JFrame {

	private static final long serialVersionUID = 1L;

	//Propios
	private DAOManager manager;
	private AsignaturasTableModel model;
	private DetalleAsignaturaPanel detallePanel;

	//Componentes
		private JPanel contentPane;
		private JButton btn_save;
		private JButton btn_update;
		private JButton btn_delete;
		private JButton btn_cancel;
		private JButton btn_insert;
		private JToolBar toolbar;
		private JTable tablaresultados;
		
	public VistaAsignaturas() {		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1022, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		toolbar = new JToolBar();
		contentPane.add(toolbar, BorderLayout.NORTH);
		
		//PANEL LATERAL
		detallePanel = new DetalleAsignaturaPanel();
		detallePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(detallePanel,BorderLayout.EAST);
		detallePanel.setPreferredSize(new Dimension(300, 0));
		detallePanel.setEditable(false);
		
		///-------------------INSERTAR
		btn_insert = new JButton("INSERT");
		btn_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activarBotonesGuardar(true);
				detallePanel.setEditable(true);
				detallePanel.loadData();
			}
		});
		
		toolbar.add(btn_insert);
		///-------------------UPDATEAR
		btn_update = new JButton("UPDATE");
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activarBotonesGuardar(true);
				activarBotonesCRUD(true);
				try {
					activarBotonesGuardar(true);
					detallePanel.setEditable(true);
					detallePanel.setAsignatura(getAsignaturaSeleccionada());
					detallePanel.loadData();
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		btn_update.setEnabled(false);
		toolbar.add(btn_update);
		///-------------------BORRAR
		btn_delete = new JButton("DELETE");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(rootPane, "¿Seguro que quieres borrar este alumno?","Borrar alumno",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					try {
						manager.getAsignaturaDAO().delete(getAsignaturaSeleccionada());
						updateTable();
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					detallePanel.setEnabled(false);
					activarBotonesGuardar(false);
					activarBotonesCRUD(false);
				}
			}
		});
		btn_delete.setEnabled(false);
		toolbar.add(btn_delete);
		///-------------------GUARDAR
		btn_save = new JButton("SAVE");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				detallePanel.saveData();
				Asignatura a = detallePanel.getAsignatura();
				if(a.getId() == null ) {
					try {
						manager.getAsignaturaDAO().insert(a);
					} catch (DAOException e1) {
						new DAOException("Error al insertar",e1);
					}
				}else {
					try {
						manager.getAsignaturaDAO().update(a);
					} catch (DAOException e1) {
						new DAOException("Error al updatear",e1);
					}
				}
				activarBotonesGuardar(false);
				activarBotonesCRUD(false);
				detallePanel.setAsignatura(null);
				detallePanel.setEditable(false);
				detallePanel.loadData();
				try {
					updateTable();
				} catch (DAOException e1) {
					new DAOException("Error al refrescar los resultados",e1);
				}
			}
		});
		btn_save.setEnabled(false);
		toolbar.add(btn_save);
		///-------------------CANCELAR
		btn_cancel = new JButton("CANCEL");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activarBotonesGuardar(false);
				activarBotonesCRUD(false);
				
				detallePanel.setEditable(false);
				detallePanel.setAsignatura(null);
				detallePanel.loadData();
			};
		});
		btn_cancel.setEnabled(false);
		toolbar.add(btn_cancel);
		
		tablaresultados = new JTable();
		contentPane.add(tablaresultados, BorderLayout.CENTER);

	}
	//CONSTRUCTOR PRINCIPAL
	public VistaAsignaturas(DAOManager manager) throws DAOException {
		this();
		this.manager = manager;
		
		this.model = new AsignaturasTableModel(manager.getAsignaturaDAO());
		this.tablaresultados.setModel(model);
		this.model.update();
		
		
		//Activamos en el click del item el panel de edit
		this.tablaresultados.getSelectionModel().addListSelectionListener(e ->{
			boolean seleccionValida = (tablaresultados.getSelectedRow() != -1); 
			activarBotonesCRUD(seleccionValida);
		});
		this.detallePanel.setEditable(false);
		this.detallePanel.setAsignatura(null);
		this.detallePanel.loadData();
	}
	//OBTENER ASIGNATURA SELECCIONADA 
	Asignatura getAsignaturaSeleccionada() throws DAOException {
		Long id = (Long) tablaresultados.getValueAt(tablaresultados.getSelectedRow(), 0);
		return manager.getAsignaturaDAO().selectOne(id);
	}
	
	//METODOS PARA ACTIVAR O DESACTIVAR BOTONES
	private void activarBotonesCRUD (boolean activo) {
		this.btn_delete.setEnabled(activo);
		this.btn_update.setEnabled(activo);
	}
	private void activarBotonesGuardar(boolean activo) {
		this.btn_cancel.setEnabled(activo);
		this.btn_save.setEnabled(activo);
	}
	//METODO PARA RECARGAR LA TABLARESULTADOS
	private void updateTable() throws DAOException {
		this.model.update();
		model.fireTableDataChanged();
	}

	//EJECUTORE
	public static void main(String[] args) throws SQLException {
		DAOManager manager = new PostgresDAOManager();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaAsignaturas frame = new VistaAsignaturas(manager);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}*/

public class VistaAsignaturas extends JFrame {

    private DAOManager manager;
    private AsignaturasTableModel model;
    private DetalleAsignaturaPanel detallePanel;

    private JTable tabla;
    private JButton btnInsert, btnUpdate, btnDelete, btnSave, btnCancel;

    public VistaAsignaturas(DAOManager manager) throws DAOException {
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.manager = manager;

        initUI();
        initModel();
        initEventos();
    }
    
    private void initUI() {
        setTitle("Asignaturas");
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

        // 🔥 PASAMOS EL MANAGER AL PANEL
        detallePanel = new DetalleAsignaturaPanel(manager);
        detallePanel.setPreferredSize(new Dimension(300, 0));
        detallePanel.setEditable(false);
        getContentPane().add(detallePanel, BorderLayout.EAST);
    }
    
    private void initModel() throws DAOException {
        model = new AsignaturasTableModel(manager.getAsignaturaDAO());
        model.update();
        tabla.setModel(model);
    }
    private void initEventos() {

        tabla.getSelectionModel().addListSelectionListener(e -> {
            boolean seleccionado = tabla.getSelectedRow() != -1;

            btnUpdate.setEnabled(seleccionado);
            btnDelete.setEnabled(seleccionado);
        });

        btnInsert.addActionListener(e -> {
            detallePanel.setAsignatura(null);
            detallePanel.loadData();
            detallePanel.setEditable(true);

            btnSave.setEnabled(true);
            btnCancel.setEnabled(true);
        });

        btnUpdate.addActionListener(e -> {
            try {
                Asignatura a = getSeleccionada();
                detallePanel.setAsignatura(a);
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
                Asignatura a = detallePanel.getAsignatura();

                if (a.getId() == null) {
                    manager.getAsignaturaDAO().insert(a);
                } else {
                    manager.getAsignaturaDAO().update(a);
                }

                refresh();

            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                Asignatura a = getSeleccionada();
                manager.getAsignaturaDAO().delete(a);
                refresh();
            } catch (DAOException ex) {
                ex.printStackTrace();
            }
        });

        btnCancel.addActionListener(e -> reset());
    }
    private Asignatura getSeleccionada() throws DAOException {
        int fila = tabla.getSelectedRow();
        Long id = ((Number) tabla.getValueAt(fila, 0)).longValue();
        return manager.getAsignaturaDAO().selectOne(id);
    }

    private void refresh() throws DAOException {
        model.update();
        model.fireTableDataChanged();
        reset();
    }

    private void reset() {
        detallePanel.setAsignatura(null);
        detallePanel.loadData();
        detallePanel.setEditable(false);

        tabla.clearSelection();

        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }
}