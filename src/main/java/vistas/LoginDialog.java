package vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	//Componentes
	private final JPanel contentPanel = new JPanel();
	private JTextField field_usuario;
	private JPasswordField field_password;
	private JButton btn_cancelar;
	JButton btn_aceptar;
	
	//Propios
	private boolean aceptado;
	

	public LoginDialog() {
		setModal(true);
		setBounds(100, 100, 395, 261);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		{
			JLabel lblUsuario = new JLabel("Usuario");
			lblUsuario.setBounds(34, 80, 46, 14);
			contentPanel.add(lblUsuario);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setBounds(34, 129, 77, 14);
			contentPanel.add(lblPassword);
		}
		
		btn_aceptar = new JButton("Aceptar");
		btn_aceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptado = true;
				dispose();
			}
		});
		btn_aceptar.setBounds(22, 180, 89, 23);
		contentPanel.add(btn_aceptar);
		
		btn_cancelar = new JButton("Cancelar");
		btn_cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptado = false;
				dispose();
			}
		});
		btn_cancelar.setBounds(135, 180, 89, 23);
		contentPanel.add(btn_cancelar);
		
		field_usuario = new JTextField();
		field_usuario.setBounds(137, 77, 211, 20);
		contentPanel.add(field_usuario);
		field_usuario.setColumns(10);
		
		field_password = new JPasswordField();
		field_password.setBounds(137, 126, 211, 20);
		contentPanel.add(field_password);
	}
	
	public boolean getAceptado() {
		return aceptado;
	}
	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}
	
	//Metodos para recuperar campos
	public String getUsuario() {
		return field_usuario.getText();
	}
	public String getPassword() {
		return new String(field_password.getPassword());
	}

	//EJECUTOR
	public static void main(String[] args) {
		try {
			LoginDialog dialog = new LoginDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
