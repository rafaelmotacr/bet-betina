
package org.ifba.bet.ui.user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public abstract class RegisterUserWindow extends JInternalFrame {

	/*
	 * Classe abstrata que carrega elementos e características em comun entre as
	 * janelas de registro de usuário e registro de admnistrador. Possui apenas um
	 * método abstrato (onRegisterButtonClick) que é implementado por suas classes
	 * filhas. Não foi planejada para ser instanciada, e nem deve, por ser uma
	 * classe abstratata.
	 */

	// Atributo obrigatório para janelas que herdam de jInternalFrame

	private static final long serialVersionUID = 1L;

	// Atributos da classe que precisam ser públicos
	// A fim de que sejam herdaddos por outras classes

	public JTextField nameField;
	public JTextField emailField;
	public JPasswordField confirmPasswordField;
	public JPasswordField passwordField;
	public JButton registerBTN;
	public JLabel backgroundLabel;

	public RegisterUserWindow() {

		super();

		// Configurações da janela de registro base

		setClosable(true);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0)));
		setBounds(0, 0, 250, 275);
		getContentPane().setLayout(null);

		// Campo para definir o nome do usuário
		// é transparente e possui uma fina borda
		// para destacá-lo
		// -- parent = this

		nameField = new JTextField();
		nameField.setForeground(new Color(255, 255, 255));
		nameField.setOpaque(false);
		nameField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		nameField.setBounds(14, 53, 212, 20);
		getContentPane().add(nameField);

		// Campo para definir o email do usuário
		// é transparente e possui uma fina borda
		// para destacá-lo
		// -- parent = this

		emailField = new JTextField();
		emailField.setForeground(new Color(255, 255, 255));
		emailField.setOpaque(false);
		emailField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		emailField.setBounds(14, 89, 212, 20);
		getContentPane().add(emailField);

		// Botão que permite ao usuário voltar
		// para a janela anterior
		// -- parent = this

		JButton backBTN = new JButton("");
		backBTN.setIcon(new ImageIcon("src/main/resources/backBTN.png"));
		backBTN.setContentAreaFilled(false);
		backBTN.setBorder(null);
		backBTN.setBounds(14, 194, 30, 30);
		getContentPane().add(backBTN);

		// Fecha a janela de registro e
		// efetivamente retorna para a
		// janela anterior

		backBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// Campo para digitar a senha do usuário
		// é transparente e possui uma fina borda
		// para destacá-lo
		// -- parent = this

		passwordField = new JPasswordField();
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setOpaque(false);
		passwordField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		passwordField.setBounds(14, 126, 210, 20);
		getContentPane().add(passwordField);

		// Campo para confirmar a senha do usuário
		// é transparente e possui uma fina borda
		// para destacá-lo
		// -- parent = this

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setForeground(new Color(255, 255, 255));
		confirmPasswordField.setOpaque(false);
		confirmPasswordField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		confirmPasswordField.setBounds(14, 163, 210, 20);
		getContentPane().add(confirmPasswordField);

		// Botão dedicado a salvar os dados do usuário
		// e efetivamente cadastrá-lo
		// -- parent = this

		registerBTN = new JButton("");
		registerBTN.setBounds(46, 194, 154, 34);
		registerBTN.setOpaque(false);
		registerBTN.setContentAreaFilled(false);
		registerBTN.setBorderPainted(false);
		registerBTN.setFocusPainted(false);
		getContentPane().add(registerBTN);

		// Adiciona como actionListener o método abstrato da classe
		// que deve ser implementado nas classes que herdam dela

		registerBTN.addActionListener(this::onRegisterButtonClick);

		// Label que deve, nas classes filhas, ser preenchida com a
		// imagem de fundo adequada (ADM ou USer)
		// -- parent = statementPNL

		backgroundLabel = new JLabel("");
		backgroundLabel.setBounds(0, 0, 250, 250);
		getContentPane().add(backgroundLabel);
	}

	// Método abstrado a ser herdado por classes filhas

	public abstract void onRegisterButtonClick(ActionEvent e);

}
