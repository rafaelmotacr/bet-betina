package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.UserDaoPostgres;
import java.awt.Font;

public class RegisterWindow {

	JFrame frame;
	private JTextField nameField;
	private JPasswordField passwordField;
	private JTextField emailField;
	private MainWindow mainWindow;
	private UserDaoPostgres dao = new UserDaoPostgres();
	private JButton backBTN;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterWindow window = new RegisterWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegisterWindow() {
		initialize();
	}
	
	public RegisterWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 259, 286);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		backBTN = new JButton("");
		backBTN.setIcon(new ImageIcon(RegisterWindow.class.getResource("/resources/back_btn_register.png")));
		backBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 5));
		backBTN.setBounds(10, 11, 33, 23);
		frame.getContentPane().add(backBTN);
		backBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		
		
		JButton registerBTN = new JButton("");
		registerBTN.setBounds(58, 195, 127, 41);
		frame.getContentPane().add(registerBTN);
		registerBTN.setOpaque(false);
		registerBTN.setContentAreaFilled(false);
		registerBTN.setBorderPainted(false);
		registerBTN.setFocusPainted(false);
		registerBTN.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				try {
					String nome = nameField.getText();
					String email = emailField.getText() ;
					String senha = passwordField.getText();
					dao.insertUser(nome, email, senha);
					mainWindow.updateUser(dao.findUserByLoguin(email, senha));
					mainWindow.updateButtons();
					JOptionPane.showMessageDialog(frame, "Usuário criado com sucesso.");
					frame.dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame,"Erro ao criar usuário.");
				}
				frame.dispose();
			}
		});
		
		
		
		emailField = new JTextField();
		emailField.setBounds(10, 115, 223, 20);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 153, 223, 20);
		frame.getContentPane().add(passwordField);
		
		nameField = new JTextField();
		nameField.setBounds(10, 73, 223, 20);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(RegisterWindow.class.getResource("/resources/registerBg.png")));
		lblNewLabel.setBounds(0, 0, 250, 250);
		frame.getContentPane().add(lblNewLabel);
	}
}
