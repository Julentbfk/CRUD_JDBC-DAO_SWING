import java.sql.SQLException;

import javax.swing.JOptionPane;

import excepciones.DAOException;
import implementacionesPOSTGRESQL.PostgresDAOManager;
import vistas.LoginDialog;
import vistas.VistaMainmenu;
import vistas.alumnos.VistaAlumnos;

public class EscuelaMain {
	
	public static void main(String[] args) throws DAOException{

		LoginDialog dialog = new LoginDialog();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

		if(!dialog.getAceptado()){
			System.out.println("HA CANCELADO");
			System.exit(0);
		}
		
		try {
			PostgresDAOManager manager = new PostgresDAOManager(dialog.getUsuario(),dialog.getPassword());
			System.out.println("HA ACEPTADO");
			VistaMainmenu menu = new VistaMainmenu(manager);
			menu.setLocationRelativeTo(null);
			menu.setVisible(true);
			
		} catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
		}
		
	}
}
