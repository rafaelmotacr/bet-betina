
package ui.user;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import dao.UserDaoPostgres;
import exceptions.PasswordsDontMatchException;
import util.InputManipulation;

public class RegisterUserWindow extends RegisterWindow {
	
	private static final long serialVersionUID = 1L;
	private UserDaoPostgres dao = new UserDaoPostgres();
	private MainWindow mainWindow;

	public RegisterUserWindow(MainWindow mainWindow) {
		super();
		this.mainWindow  = mainWindow;
		this.backgroundLabel.setIcon(new ImageIcon(RegisterUserWindow.class.getResource("/resources/registerBG.png")));
		this.setTitle("bet-betina v1.21 - Registro");
		this.registerBTN.addActionListener(this::onRegisterButtonClick);
		
	}
	
	@Override
	public  void onRegisterButtonClick(ActionEvent e) {

		String name = nameField.getText();
		String email = emailField.getText();
		String password = null;
		String encryptedPassword = null;

		try {
			password = InputManipulation.joinPasswords(String.valueOf(passwordField.getPassword()),
					String.valueOf(confirmPasswordField.getPassword()));
		} catch (PasswordsDontMatchException e1) {
			JOptionPane.showMessageDialog(this, "As senhas não conferem!");
			return;
		}

		if (!InputManipulation.isMinLengthPassword(password)) {
			JOptionPane.showMessageDialog(this, "Senha muito curta. Use ao menos 8 digitos.");
			return;
		}

		if (!InputManipulation.isMinLengthEmail(email)) {
			JOptionPane.showMessageDialog(this, "Email muito curto.");
			return;
		}

		if (!InputManipulation.isValidEmail(email)) {
			JOptionPane.showMessageDialog(this, "Insira um e-mail válido.");
			return;
		}

		if (!InputManipulation.isValidName(name)) {
			JOptionPane.showMessageDialog(this, "O nome precisa ter ao menos 4 letras.");
			return;
		}

		try {
			if (dao.findUserByEmail(email) != null) {
				JOptionPane.showMessageDialog(this, "Email já cadastrado no banco de dados!");
				return;
			}
		} catch (SQLException e1) {
			// no coments
		}

		encryptedPassword = InputManipulation.generateHashedPassword(password);

		try {
			dao.insertUser(name, email, encryptedPassword, 0);
			this.mainWindow.updateUser(dao.findUserByEmail(email));
			JOptionPane.showMessageDialog(RegisterUserWindow.this, "Usuário criado com sucesso.");
			nameField.setText(null);
			emailField.setText(null);
			passwordField.setText(null);
			confirmPasswordField.setText(null);
			dispose();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(RegisterUserWindow.this, "Erro ao cadastrar Usuário.");
		}

		dispose();
	}	

}
