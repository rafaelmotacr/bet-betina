package ui.user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import dao.UserDaoPostgres;
import exceptions.PasswordsDontMatchException;
import util.InputManipulation;

public class RegisterWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private UserDaoPostgres dao = new UserDaoPostgres();

	public RegisterWindow(MainWindow mainWindow) {

		super();
		setClosable(true);

		setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0)));
		setTitle("bet-betina v1.21 - Registro");

		setBounds(0, 0, 250, 275);
		getContentPane().setLayout(null);

		JTextField nameField = new JTextField();
		nameField.setBorder(null);
		nameField.setBounds(14, 53, 212, 20);
		getContentPane().add(nameField);
		nameField.setColumns(10);

		JTextField emailField = new JTextField();
		emailField.setBounds(14, 89, 212, 20);
		getContentPane().add(emailField);
		emailField.setColumns(10);

		JButton backBTN = new JButton("");
		backBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		backBTN.setIcon(new ImageIcon(RegisterWindow.class.getResource("/resources/backBTN.png")));
		backBTN.setContentAreaFilled(false);
		backBTN.setBorder(null);
		backBTN.setBounds(14, 194, 30, 30);
		this.getContentPane().add(backBTN);

		JPasswordField confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(14, 163, 210, 20);
		this.getContentPane().add(confirmPasswordField);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(14, 126, 210, 20);
		this.getContentPane().add(passwordField);

		JButton registerBTN = new JButton("");
		registerBTN.setBounds(46, 194, 154, 34);
		this.getContentPane().add(registerBTN);
		registerBTN.setOpaque(false);
		registerBTN.setContentAreaFilled(false);
		registerBTN.setBorderPainted(false);
		registerBTN.setFocusPainted(false);
		registerBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String name = nameField.getText();
				String email = emailField.getText();
				String password = null;
				String encryptedPassword = null;

				try {
					password = InputManipulation.joinPasswords(String.valueOf(passwordField.getPassword()),
							String.valueOf(confirmPasswordField.getPassword()));
				} catch (PasswordsDontMatchException e1) {
					JOptionPane.showMessageDialog(RegisterWindow.this, "As senhas não conferem!");
					return;
				}

				if (!InputManipulation.isMinLengthPassword(password)) {
					JOptionPane.showMessageDialog(RegisterWindow.this, "Senha muito curta. Use ao menos 8 digitos.");
					return;
				}

				if (!InputManipulation.isMinLengthEmail(email)) {
					JOptionPane.showMessageDialog(RegisterWindow.this, "Email muito curto.");
					return;
				}

				if (!InputManipulation.isValidEmail(email)) {
					JOptionPane.showMessageDialog(RegisterWindow.this, "Insira um e-mail válido.");
					return;
				}

				if (!InputManipulation.isValidName(name)) {
					JOptionPane.showMessageDialog(RegisterWindow.this, "O nome precisa ter ao menos 4 letras.");
					return;
				}

				try {
					if (dao.findUserByEmail(email) != null) {
						JOptionPane.showMessageDialog(RegisterWindow.this, "Email já cadastrado no banco de dados!");
						return;
					}
				} catch (SQLException e1) {
					// no coments
				}

				encryptedPassword = InputManipulation.generateHashedPassword(password);

				try {
					dao.insertUser(name, email, encryptedPassword, 2);
					mainWindow.updateUser(dao.findUserByEmail(email));
					mainWindow.updateButtons();
					JOptionPane.showMessageDialog(RegisterWindow.this, "Usuário criado com sucesso.");
					nameField.setText(null);
					emailField.setText(null);
					passwordField.setText(null);
					confirmPasswordField.setText(null);
					dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(RegisterWindow.this, "Erro ao criar usuário.");
				}

				dispose();
			}
		});

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(RegisterWindow.class.getResource("/resources/registerBG.png")));
		lblNewLabel.setBounds(0, 0, 250, 250);
		getContentPane().add(lblNewLabel);

	}
}
