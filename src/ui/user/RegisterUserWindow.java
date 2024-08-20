
package ui.user;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import dao.user.UserDaoPostgres;
import exceptions.PasswordsDontMatchException;
import util.InputManipulation;

public class RegisterUserWindow extends RegisterWindow {
	
	/* Classe concreta que herda de RegisterWindow e 
	 * permite a criação de usuários comuns.
	 * Pode ser tratada como JInternalFrame e possui
	 * todos os métodos da referida classe.
	 * Depende de uma MainWindow para ser instanciada corretamente
	 * e ter bom funcionamento. Não pode ser "executada" isoladamente.
	 */
	
	// Atributo obrigarório para classes que extendem JInternalFrame
	
	private static final long serialVersionUID = 1L;
	
	// Conexão com o banco de dados
	
	private UserDaoPostgres dao = new UserDaoPostgres();
	
	// Recebe um ponteiro/referência para a janela principal como atributo da classe.
	// Isso permite que a RegisterUserWindow atualize o usuário atual da aplicação
	
	private MainWindow mainWindow;

	public RegisterUserWindow(MainWindow mainWindow) {
		super();
		this.backgroundLabel.setIcon(new ImageIcon(RegisterUserWindow.class.getResource("/resources/registerUserBG.png")));
		this.mainWindow  = mainWindow;
		this.setTitle("Bet-Betina v1.23 - Registro");
	}
	
	@Override
	public  void onRegisterButtonClick(ActionEvent e) {
		String name = this.nameField.getText();
		String email = this.emailField.getText();
		String password = null;
		String encryptedPassword = null;
		try {
			password = InputManipulation.joinPasswords(String.valueOf(passwordField.getPassword()),String.valueOf(confirmPasswordField.getPassword()));
		} catch (PasswordsDontMatchException e1) {
			JOptionPane.showMessageDialog(this, "As senhas não conferem!");
			return;
		}
		if (!InputManipulation.isMinLengthPassword(password)) {
			JOptionPane.showMessageDialog(this, "Senha muito curta. Use ao menos " + InputManipulation.minPasswordLength + " digitos.");
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
			JOptionPane.showMessageDialog(this, "O nome precisa ter ao menos " +  InputManipulation.minNameLength +" letras.");
			return;
		}
		try {
			if (dao.findUserByEmail(email) != null) {
				JOptionPane.showMessageDialog(this, "Email já cadastrado no banco de dados!");
				return;
			}
		} catch (HeadlessException | SQLException e1) {
			
		}

		encryptedPassword = InputManipulation.generateHashedPassword(password);
		
		// Se todas as validações forem bem sucedidas,
		// tenta criar um novo usuário comum no banco de dados, 
		// através do DAO. Caso contrário, nem sequer chega a este ponto.
		// Se houver erro ao criar o usuário, avisa ao cliente.
		// Em caso de sucesso ou falha , fecha a janela
		
		try {
			dao.insertUser(name, email, encryptedPassword, 0);
			this.mainWindow.updateUser(dao.findUserByEmail(email));
			JOptionPane.showMessageDialog(RegisterUserWindow.this, "Usuário criado com sucesso.");
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(RegisterUserWindow.this, "Erro ao cadastrar Usuário.");
		}
		// Limpa todos os campos para caso a janela seja aberta novamente posteriormente
		nameField.setText(null);
		emailField.setText(null);
		passwordField.setText(null);
		confirmPasswordField.setText(null);
		dispose();
	}	
}
