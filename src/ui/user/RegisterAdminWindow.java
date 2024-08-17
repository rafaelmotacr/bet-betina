
package ui.user;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import dao.UserDaoPostgres;
import exceptions.PasswordsDontMatchException;
import util.InputManipulation;

public class RegisterAdminWindow extends RegisterWindow {
	
	/* Classe concreta que herda de RegisterWindow e 
	 * permite a criação de usuários admnistradores.
	 * Pode ser tratada como JInternalFrame e possui
	 * todos os métodos da referida classe.
	 * Depende de uma MainWindow para ser instanciada corretamente
	 * e ter bom funcionamento. Não pode ser "executada" isoladamente.
	 */
	
	// Atributo obrigarório para classes que herdam de JInternalFrame
	
	private static final long serialVersionUID = 1L;
	
	// Conexão com o banco de dados
	private UserDaoPostgres dao = new UserDaoPostgres();
	
	// Recebe um ponteiro/referência para a janela principal como atributo da classe.
	// Isso permite que a RegisterUserWindow atualize o usuário atual da aplicação
	
	private MainWindow mainWindow;

	public RegisterAdminWindow(MainWindow mainWindow) {
		super();
		this.mainWindow = mainWindow;
		this.backgroundLabel.setIcon(new ImageIcon(RegisterWindow.class.getResource("/resources/registerBG.png")));
		this.setTitle("bet-betina v1.21 - Criar ADM");
	}
	
	@Override
	public void onRegisterButtonClick(ActionEvent e) {
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
			JOptionPane.showMessageDialog(this, "Algo deu errado ao consultar seu email em nossa base de dados.");
		}
		encryptedPassword = InputManipulation.generateHashedPassword(password);
		
		// Se todas as validações forem bem sucedidas,
		// tenta criar um novo usuário comum no banco de dados, 
		// através do DAO. Caso contrário, nem sequer chega a este ponto.
		// Se houver erro ao criar o usuário, avisa ao cliente.
		// Em caso de sucesso ou falha ao criar o usuário, fecha a janela
		
		try {
			dao.insertUser(name, email, encryptedPassword, 0);
			this.mainWindow.updateUser(dao.findUserByEmail(email));
			JOptionPane.showMessageDialog(RegisterAdminWindow.this, "Administrador criado com sucesso.");
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(RegisterAdminWindow.this, "Erro ao criar Administrador.");
		}
		// Limpa todos os campos para caso a janela seja aberta novamente posteriormente
		nameField.setText(null);
		emailField.setText(null);
		passwordField.setText(null);
		confirmPasswordField.setText(null);
		dispose();
	}	

}
