package ui.team;

import javax.swing.JInternalFrame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import ui.user.LoginWindow;

import java.awt.Color;

public class CreateTeamWindow extends JInternalFrame {
	private JTextField textField_1;
	private JTextField textField;
	
	CreateTeamWindow(){
		setClosable(true);
		getContentPane().setLayout(null);
		
		JButton loginBTN = new JButton("");
		loginBTN.setContentAreaFilled(false);
		loginBTN.setBounds(16, 136, 131, 30);
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
				// e não ocorreram erros até aqui
				// tenta recuperar os dados 
				// do usuário e fazer o login 
				// propriamente dito
				
				try {
					mainWindow.updateUser(dao.findUserByEmail(email));
					JOptionPane.showMessageDialog(LoginWindow.this, "Login realizado com sucesso.");
				} catch (SQLException e1) {
					// Caso ocorra algum erro no banco de dados
					// avisa ao usuário
					JOptionPane.showMessageDialog(LoginWindow.this, "Algo deu errado ao fazer login");
				}finally {
					// limpa os campos de email e senha 
					// para caso a janela seja aberta novamente.
					// Fecha a janela.
					emailField.setText(null);
					passwordField.setText(null);
					dispose();
				}
			}
		});
		
		textField = new JTextField();
		textField.setText((String) null);
		textField.setOpaque(false);
		textField.setForeground(Color.WHITE);
		textField.setColumns(10);
		textField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		textField.setBounds(146, 48, 155, 20);
		getContentPane().add(textField);
		
		textField_1 = new JTextField();
		textField_1.setText((String) null);
		textField_1.setOpaque(false);
		textField_1.setForeground(Color.WHITE);
		textField_1.setColumns(10);
		textField_1.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		textField_1.setBounds(146, 85, 155, 20);
		getContentPane().add(textField_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(CreateTeamWindow.class.getResource("/resources/criarTime.png")));
		lblNewLabel.setBounds(0, 0, 320, 180);
		getContentPane().add(lblNewLabel);
		
		
		
	}
}
