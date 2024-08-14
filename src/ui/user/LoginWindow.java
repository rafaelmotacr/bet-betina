package ui.user;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import dao.UserDaoPostgres;
import javax.swing.border.LineBorder;

public class LoginWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private UserDaoPostgres dao = new UserDaoPostgres();

	public LoginWindow(MainWindow mainWindow) {

		super();

		setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0)));
		setTitle("bet-betina v1.21 - Login");
		setClosable(true);
		setResizable(false);
		setMaximizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(0, 0, 250, 275);

		JButton backBTN = new JButton("");
		backBTN.setBounds(20, 184, 30, 30);
		backBTN.setBorder(null);
		backBTN.setContentAreaFilled(false);
		backBTN.setIcon(new ImageIcon(LoginWindow.class.getResource("/resources/backBTN.png")));
		getContentPane().add(backBTN);
		backBTN.addActionListener(e -> {
			dispose();

		});

		JPasswordField passwordField = new JPasswordField();
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		passwordField.setOpaque(false);
		passwordField.setBounds(20, 153, 203, 20);
		getContentPane().add(passwordField);

		JTextField emailField = new JTextField();
		emailField.setForeground(new Color(255, 255, 255));
		emailField.setOpaque(false);
		emailField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		emailField.setBounds(20, 90, 203, 20);
		getContentPane().add(emailField);
		emailField.setColumns(10);

		JButton loginBTN = new JButton("");
		loginBTN.setContentAreaFilled(false);
		loginBTN.setBounds(53, 184, 141, 37);
		loginBTN.setOpaque(false);
		loginBTN.setBorderPainted(false);
		loginBTN.setFocusPainted(false);
		getContentPane().add(loginBTN);
		loginBTN.addActionListener(e -> {
			String email = emailField.getText();
			String password = String.valueOf(passwordField.getPassword());

			if (email.length() == 0 || password.length() == 0) {
				JOptionPane.showMessageDialog(LoginWindow.this, "Nenhum dos campos pode estar vazio.");
				return;
			}

			if (!dao.login(email, password)) {
				JOptionPane.showMessageDialog(LoginWindow.this, "Login ou senha incorretos.");
				return;
			}

			try {

				mainWindow.updateUser(dao.findUserByEmail(email));
				JOptionPane.showMessageDialog(LoginWindow.this, "Login realizado com sucesso.");
				mainWindow.updateButtons();
				emailField.setText(null);
				passwordField.setText(null);
				LoginWindow.this.dispose();

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(LoginWindow.this, "Algo deu errado ao fazer login");
			}
		});

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 250, 250);
		lblNewLabel.setIcon(new ImageIcon(LoginWindow.class.getResource("/resources/loginBG.png")));
		getContentPane().add(lblNewLabel);
	}

}
