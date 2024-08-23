package org.ifba.bet.ui.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.user.UserDaoPostgres;
import org.ifba.bet.model.User;

public class ConfirmDeleteUserPanel extends JInternalFrame {

	/*
	 * Classe concreta que herda de JInternalFrame e tem como objetivo ser uma tela
	 * de confirmação de exclusão do perfil de um usuário "x". Conecta-se ao banco
	 * de dados e pode fazer alterações. Depende de uma MainWindow E de uma
	 * ProfileWindow para ser instanciada corretamente e ter bom funcionamento. Não
	 * pode ser "executada" isoladamente.
	 */

	// Atributo obrigatório para janelas que herdam de jInternalFrame

	private static final long serialVersionUID = 1L;

	// Conexão com banco de dados

	private UserDaoPostgres dao = new UserDaoPostgres();

	// JLabel declarada aqui para er acessível
	// aos métodos da classe

	private JLabel confirmationTextLabel = new JLabel();

	// Ponteiro para o usuário a ser deletado

	private User currentUser = null;

	// Ponteiro para a janela de perfil
	// para casos onde seja necessário
	// fechá-la

	private ProfileWindow profileWindow = null;

	// Ponteiro/referência para a janela principal
	// para que seja possível deslogar
	// o usuário quando seu perfil
	// for apagado

	private MainWindow mainWindow = null;

	public ConfirmDeleteUserPanel() {

		super();

		// Configurações da janela de perfil

		setTitle("Confirmação de Exclusão");
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBounds(0, 0, 212, 280);
		setFocusable(false);
		setClosable(true);
		getContentPane().setLayout(null);

		// Botão de confirmação de exclusão
		// -- parent = this

		JButton confirmDeleteBTN = new JButton("Sim");
		confirmDeleteBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		confirmDeleteBTN.setContentAreaFilled(false);
		confirmDeleteBTN.setBounds(8, 196, 90, 23);
		getContentPane().add(confirmDeleteBTN);

		// Tenta apagar o usuário exibe
		// uma mensagem de acordo com
		// o resultado da exclusão

		confirmDeleteBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dao.deletUser(currentUser);
					mainWindow.updateUser(null); // Define o usuário atual como nulo
					JOptionPane.showMessageDialog(ConfirmDeleteUserPanel.this,
							"Usuário (a) deletado (a) com sucesso. Fechando janela.");
					dispose();
					profileWindow.dispose(); // Fecha a janela de perfil para impedir o acesso a dados defasados
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(ConfirmDeleteUserPanel.this,
							"Não foi possível deletar o seu usuário (a).");
					dispose();
				}
			}
		});

		// Botão de cancelamento de exclusão
		// -- parent = this

		JButton cancelDeleteBTN = new JButton("Esquece");
		cancelDeleteBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		cancelDeleteBTN.setContentAreaFilled(false);
		cancelDeleteBTN.setBounds(102, 196, 90, 23);
		getContentPane().add(cancelDeleteBTN);

		// Fecha a janela e não apaga o usuário

		cancelDeleteBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// Label que exibe uma imagem de perfil genérica para o usuário
		// -- parent = this

		JLabel profilePictureLabel = new JLabel("");
		profilePictureLabel.setIcon(new ImageIcon("src/main/resources/user.png"));
		profilePictureLabel.setBounds(73, 11, 50, 50);
		getContentPane().add(profilePictureLabel);

		// Label que exibe uma linha preta, nada demais
		// -- parent = this

		JPanel blackLine = new JPanel();
		blackLine.setForeground(Color.BLACK);
		blackLine.setBackground(Color.BLACK);
		blackLine.setBounds(37, 72, 119, 3);
		getContentPane().add(blackLine);

		// Label que exibe um aviso final ao usuário
		// -- parent = this

		confirmationTextLabel.setVerticalAlignment(SwingConstants.TOP);
		confirmationTextLabel.setBounds(8, 86, 178, 99);
		confirmationTextLabel.setFont(new Font("Georgia", Font.PLAIN, 12));
		getContentPane().add(confirmationTextLabel);
	}

	// Personaliza a janela de acordo com o usuário recebido
	// e define a referência para a janela principal
	// indispensável para o funcionamento adequado da classe

	public void turnOn(MainWindow mainWindow, ProfileWindow profileWindow) {
		this.profileWindow = profileWindow;
		this.mainWindow = mainWindow;
		currentUser = mainWindow.getCurrentUser();
		confirmationTextLabel.setText("<html>Deseja realmente apagar para \r\n<br> sempre o "
				.concat(currentUser.getAccessLevel() == 0 ? "usuario (a)" : "admnistrador") + "<strong> "
				+ currentUser.getName() + "</strong>?\r\n<br>(Para sempre é um tempão!)</html>");
	}
}
