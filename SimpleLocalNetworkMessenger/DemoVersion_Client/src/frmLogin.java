import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;


public class frmLogin extends JFrame {

	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField txtPass;
	private Client client;
	/**
	 * Launch the application.
	 *//*

	/**
	 * Create the frame.
	 */
	public frmLogin(Client c) {
		client=c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 262, 189);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUser = new JTextField();
		txtUser.setBounds(104, 29, 86, 20);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(104, 60, 86, 20);
		contentPane.add(txtPass);
		txtPass.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(31, 31, 63, 17);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(31, 63, 63, 17);
		contentPane.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.sentLogin(txtUser.getText(), new String(txtPass.getPassword()));
			}
		});
		btnLogin.setBounds(24, 103, 89, 23);
		contentPane.add(btnLogin);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				client.CreateUser(txtUser.getText(), new String(txtPass.getPassword()));
			}
		});
		btnSignUp.setBounds(123, 103, 89, 23);
		contentPane.add(btnSignUp);
		
	}
}
