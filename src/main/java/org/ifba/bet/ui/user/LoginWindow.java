package org.ifba.bet.ui.user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import org.ifba.bet.dao.user.UserDaoPostgres;
import org.ifba.bet.model.User;

public class LoginWindow extends JInternalFrame {

	// Atributo obrigarório para classes que herdam de JInternalFrame

	private static final long serialVersionUID = 1L;
	private static final String ADMIN_EMAIL = "admin";
	private static final String ADMIN_PASSWORD = "admin";

	// Conexão com o banco de dados

	private UserDaoPostgres userDao = new UserDaoPostgres();
	// Recebe um ponteiro/referência para a janela principal como parâmetro.
	// Isso permite que a LoginWiwndow atualize o usuário atual da aplicação

	public LoginWindow(MainWindow mainWindow) {

		super();

		// Configurações da janela de perfil
		// -- parent = null

		setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0)));
		setTitle("Bet-Betina v1.23 - Login");
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
		backBTN.setIcon(new ImageIcon("src/main/resources/backBTN.png"));
		getContentPane().add(backBTN);
		backBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
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
		// possui transparencia propositalmente
		// para exibir a iamgem abaixo dele
		// -- parent = this

		JButton loginBTN = new JButton("");
		loginBTN.setContentAreaFilled(false);
		loginBTN.setBounds(53, 184, 141, 37);
		loginBTN.setOpaque(false);
		loginBTN.setBorderPainted(false);
		loginBTN.setFocusPainted(false);
		getContentPane().add(loginBTN);
		loginBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Email e Password capturam os campos para
				// evitar chamadas redundantes dos
				// métodos de getText()
				String email = emailField.getText();
				String password = String.valueOf(passwordField.getPassword());
				User testUser = null;
				
				if (email.equalsIgnoreCase(ADMIN_EMAIL) && password.equalsIgnoreCase(ADMIN_PASSWORD)) {
						testUser = userDao.findUserByEmail(ADMIN_EMAIL);
						if (testUser == null) {
							try {
								userDao.insertUser("admin", ADMIN_EMAIL, ADMIN_PASSWORD, User.ADMIN);
								mainWindow.updateUser(userDao.findUserByEmail(ADMIN_EMAIL));
								JOptionPane.showMessageDialog(LoginWindow.this, "Usuário root criado com sucesso.");
							} catch (SQLException e1) {
								JOptionPane.showMessageDialog(LoginWindow.this, "Erro ao logar como root");
								e1.printStackTrace();
							}
							emailField.setText(null);
							passwordField.setText(null);
							dispose();
							return;
						}
				}

				// Se o email ou a senha estiverem vazios, não tenta realizar login

				if (email.length() == 0 || password.length() == 0) {
					JOptionPane.showMessageDialog(LoginWindow.this, "Nenhum dos campos pode estar vazio.");
					return;
				}

				// Se o login não for bem sucedido
				// leia-se, o usuário não for encontrado
				// ou a senha digitada estar errada
				// aborta o processo de login

				if (!userDao.login(email, password)) {
					JOptionPane.showMessageDialog(LoginWindow.this, "Email ou senha incorretos.");
					return;
				}

				// Se o usuário foi encontrado
				// e não ocorreram erros até aqui
				// tenta recuperar os dados
				// do usuário e fazer o login
				// propriamente dito

				mainWindow.updateUser(userDao.findUserByEmail(email));
				JOptionPane.showMessageDialog(LoginWindow.this, "Login realizado com sucesso.");
				// limpa os campos de email e senha
				// para caso a janela seja aberta novamente.
				// Fecha a janela.
				emailField.setText(null);
				passwordField.setText(null);
				dispose();
				}
			
		});

		// Label que exibe a imagem de fundo
		// -- parent = this

		JLabel imgPNL = new JLabel("");
		imgPNL.setBounds(0, 0, 250, 250);
		imgPNL.setIcon(new ImageIcon("src/main/resources/loginBG.png"));
		getContentPane().add(imgPNL);

	}
}
