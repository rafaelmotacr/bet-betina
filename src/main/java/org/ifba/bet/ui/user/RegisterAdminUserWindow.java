package org.ifba.bet.ui.user;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.ifba.bet.dao.user.UserDaoPostgres;
import org.ifba.bet.exceptions.PasswordsDontMatchException;
import org.ifba.bet.model.User;
import org.ifba.bet.util.InputManipulation;

public class RegisterAdminUserWindow extends RegisterUserWindow {

	/*
	 * Classe concreta que herda de RegisterWindow e permite a criação de usuários
	 * admnistradores. Pode ser tratada como JInternalFrame e possui todos os
	 * métodos da referida classe. Depende de uma MainWindow para ser instanciada
	 * corretamente e ter bom funcionamento. Não pode ser "executada" isoladamente.
	 */

	// Atributo obrigarório para classes que herdam de JInternalFrame

	private static final long serialVersionUID = 1L;

	// Conexão com o banco de dados
	private UserDaoPostgres dao = new UserDaoPostgres();

	// Recebe um ponteiro/referência para a janela principal como atributo da
	// classe.
	// Isso permite que a RegisterUserWindow atualize o usuário atual da aplicação

	private MainWindow mainWindow;

	public RegisterAdminUserWindow(MainWindow mainWindow) {
		
		super();
		
		this.backgroundLabel.setIcon(new ImageIcon("src/main/resources/registerADMBG.png"));
		this.mainWindow = mainWindow;
		this.setTitle("Bet-Betina v1.23 - ADM: Criar ADM");
	}

	@Override
	public void onRegisterButtonClick(ActionEvent e) {
		String name = this.nameField.getText();
		String email = this.emailField.getText();
		String password = null;
		try {
			password = InputManipulation.joinPasswords(String.valueOf(passwordField.getPassword()),
					String.valueOf(confirmPasswordField.getPassword()));
		} catch (PasswordsDontMatchException e1) {
			JOptionPane.showMessageDialog(this, "As senhas não conferem!");
			return;
		}
		if (!InputManipulation.isMinLengthPassword(password)) {
			JOptionPane.showMessageDialog(this,
					"Senha muito curta. Use ao menos " + InputManipulation.minPasswordLength + " digitos.");
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
			JOptionPane.showMessageDialog(this,
					"O nome precisa ter ao menos " + InputManipulation.minNameLength + " letras.");
			return;
		}

		if (dao.findUserByEmail(email) != null) {
			JOptionPane.showMessageDialog(this, "Email já cadastrado no banco de dados!");
			return;
		}
		// Se todas as validações forem bem sucedidas,
		// tenta criar um novo admnistrador no banco de dados,
		// através do DAO. Caso contrário, nem sequer chega a este ponto.
		// Se houver erro ao criar o admnitrador, avisa ao cliente.
		// Em caso de sucesso ou falha, fecha a janela

		try {
			dao.insertUser(name, email, password, User.ADMIN);
			this.mainWindow.updateUser(dao.findUserByEmail(email));
			JOptionPane.showMessageDialog(RegisterAdminUserWindow.this, "Administrador criado com sucesso.");
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(RegisterAdminUserWindow.this, "Erro ao criar Administrador.");
		}
		// Limpa todos os campos para caso a janela seja aberta novamente posteriormente
		nameField.setText(null);
		emailField.setText(null);
		passwordField.setText(null);
		confirmPasswordField.setText(null);
		dispose();
	}

}
