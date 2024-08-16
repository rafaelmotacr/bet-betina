package ui.user;

import java.awt.Color;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import dao.UserDaoPostgres;

public class LoginWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private UserDaoPostgres dao = new UserDaoPostgres();

	public LoginWindow(MainWindow mainWindow) {

		super();
		
		// Configurações da janela de perfil
		// -- parent = null

		setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0)));
		setTitle("bet-betina v1.21 - Login");
		setClosable(true);
		setResizable(false);
		setMaximizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(0, 0, 250, 275);
		
		// Botão para voltar para a janela principal
		// -- parent = this
		
		JButton backBTN = new JButton("");
		backBTN.setBounds(20, 184, 30, 30);
		backBTN.setBorder(null);
		backBTN.setContentAreaFilled(false);
		backBTN.setIcon(new ImageIcon(LoginWindow.class.getResource("/resources/backBTN.png")));
		getContentPane().add(backBTN);
		backBTN.addActionListener(e -> {
			dispose();

		});
		
		// Campo para que o usuário digite sua senha 
		// -- parent = this

		JPasswordField passwordField = new JPasswordField();
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		passwordField.setOpaque(false);
		passwordField.setBounds(20, 153, 203, 20);
		getContentPane().add(passwordField);

		// Campo para que o usuário digite seu email
		// -- parent = this
		
		JTextField emailField = new JTextField();
		emailField.setForeground(new Color(255, 255, 255));
		emailField.setOpaque(false);
		emailField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		emailField.setBounds(20, 90, 203, 20);
		getContentPane().add(emailField);
		emailField.setColumns(10);

		// Botão para realizar login
		// Possui transparencia propositalmente
		// para exibir a iamgem abaixo dele
		// -- parent = this
		
		JButton loginBTN = new JButton("");
		loginBTN.setContentAreaFilled(false);
		loginBTN.setBounds(53, 184, 141, 37);
		loginBTN.setOpaque(false);
		loginBTN.setBorderPainted(false);
		loginBTN.setFocusPainted(false);
		getContentPane().add(loginBTN);
		loginBTN.addActionListener(e -> {
			
			// Email e Password capturam os campos para 
			// evitar chamadas redundantes dos 
			// Métodos de getText()
			
			String email = emailField.getText();
			String password = String.valueOf(passwordField.getPassword());

			// Se o email ou a senha estiverem vazios, não tenta realizar login
			
			if (email.length() == 0 || password.length() == 0) {
				JOptionPane.showMessageDialog(LoginWindow.this, "Nenhum dos campos pode estar vazio.");
				return;
			}

			// Se o login não for bem sucedido
			// leia-se, o usuário não for encontrado
			// ou a senha digitada estar errada
			// aborta o processo de login
			
			if (!dao.login(email, password)) {
				JOptionPane.showMessageDialog(LoginWindow.this, "Email ou senha incorretos.");
				return;
			}
			
			// Se o usuário foi encontrado
			// E não ocorreram erros até aqui
			// Tenta recuperar os dados 
			// Do usuário e fazer o login 
			// propriamente dito
			
			try {

				mainWindow.updateUser(dao.findUserByEmail(email));
				JOptionPane.showMessageDialog(LoginWindow.this, "Login realizado com sucesso.");

				// Se não houver erro, limpa os campos de email e senha
				
				emailField.setText(null);
				passwordField.setText(null);

			} catch (SQLException e1) {
				
				// Caso ocorra algum erro no banco de dados
				// Avisa ao usuário
				
				JOptionPane.showMessageDialog(LoginWindow.this, "Algo deu errado ao fazer login");
			}finally {
				dispose();
				
			}
		});

		// Label que exibe a imagem de fundo
		// -- parent = this
		
		JLabel imgPNL = new JLabel("");
		imgPNL.setBounds(0, 0, 250, 250);
		imgPNL.setIcon(new ImageIcon(LoginWindow.class.getResource("/resources/loginBG.png")));
		getContentPane().add(imgPNL);
		
	}
}
