package ui.user;

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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import dao.UserDaoPostgres;
import exceptions.PasswordsDontMatchException;
import model.User;
import util.InputManipulation;

public class ProfileWindow extends JInternalFrame {
	
	// Atributo obrigarório para classes que extendem JInternalFrame
	
	private static final long serialVersionUID = 1L; 
	
	// CurrentUser define o usuário atual
	// E serve para buscá-lo no banco de dados
	
	private User currentUser; 

	// Estes componentes da tela são declarados aqui para
	// se tornarem acessíveis aos métodos da classe
	
	private JTextField nameFLD; 
	private JTextField emailFLD; 
	private JLabel nameLBL; 
	private JLabel idLBL;
	private JLabel balanceLBL; 
	
	// Conexão com o banco de dados

	private UserDaoPostgres dao = new UserDaoPostgres(); 
	
	// Ponteiro para a janela principal para comunicação
	
	private MainWindow mainWindow; 

	// Construtor da classe, onde seu design é elaborado
	
	public ProfileWindow() {

		super();
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		setClosable(true);
		setBounds(0, 0, 640, 360);
		getContentPane().setLayout(null);
		
		// InternalFrame de delete 
		// -- parent = frame

		ConfirmDeletePanel confirmDeleteWindow = new ConfirmDeletePanel();
		confirmDeleteWindow.setBounds(214, 11, 212, 280);
		getContentPane().add(confirmDeleteWindow);

		// Painel principal, o do meio 
		// -- parent = frame
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mainPanel.setBounds(214, 11, 212, 280);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		// Label onde o nome do usuário é exibido 
		// -- parent = mainPanel
		
		nameLBL = new JLabel();
		nameLBL.setBounds(12, 77, 202, 14);
		mainPanel.add(nameLBL);
		nameLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		
		// Label onde o saldo atual do usuário é exibido 
		// -- parent = mainPanel
		
		balanceLBL = new JLabel();
		balanceLBL.setBounds(12, 103, 163, 14);
		mainPanel.add(balanceLBL);
		balanceLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		
		// Label que exibe uma imagem de perfil genérica para o usuário
		// -- parent = mainPanel
		
		JLabel profilePictureLBL = new JLabel("");
		profilePictureLBL.setBounds(81, 12, 50, 50);
		mainPanel.add(profilePictureLBL);
		profilePictureLBL.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/user.png")));

		// Label que exibe uma linha preta, nada demais
		// -- parent = mainPanel
		
		JPanel blackLine_1 = new JPanel();
		blackLine_1.setBounds(46, 66, 119, 3);
		mainPanel.add(blackLine_1);
		blackLine_1.setForeground(Color.BLACK);
		blackLine_1.setBackground(Color.BLACK);

		// Label que exibe o total de apostas de um usuário
		// -- parent = dataPanel
		
		JLabel totalBetsLBL = new JLabel();
		totalBetsLBL.setBounds(12, 153, 163, 14);
		mainPanel.add(totalBetsLBL);
		totalBetsLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		// Label que exibe o total de derrotas de um usuário
		// -- parent = dataPanel
		
		JLabel totalLosesLBL = new JLabel();
		totalLosesLBL.setBounds(12, 179, 163, 14);
		mainPanel.add(totalLosesLBL);
		totalLosesLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		// Label que exibe o total de triunfos de um usuário
		// -- parent = dataPanel
		
		JLabel totalWinsLBL = new JLabel();
		totalWinsLBL.setBounds(12, 205, 163, 14);
		mainPanel.add(totalWinsLBL);
		totalWinsLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		// Label que exibe a taxa de acerto de um user
		// -- parent = dataPanel
		
		JLabel victoryRateLBL = new JLabel();
		victoryRateLBL.setBounds(12, 231, 163, 14);
		mainPanel.add(victoryRateLBL);
		victoryRateLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		// Label que exibe o time favorito de um usuário
		// Baseado no time que ele mais apostou ao longo do tempo
		// -- parent = dataPanel
		
		JLabel favoriteTeamLBL = new JLabel();
		favoriteTeamLBL.setBounds(12, 257, 163, 14);
		mainPanel.add(favoriteTeamLBL);
		favoriteTeamLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		
		// Painel que contém os dados do usuário 
		// -- parent = frame

		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		dataPanel.setBounds(10, 11, 194, 280);
		dataPanel.setBackground(new Color(0, 128, 128));
		dataPanel.setLayout(null);
		getContentPane().add(dataPanel);
		
		// Label com o texto "Dados", nada demais 
		// -- parent = dataPanel

		JLabel dataTextPNL = new JLabel("DADOS");
		dataTextPNL.setBounds(61, 11, 72, 28);
		dataTextPNL.setForeground(Color.WHITE);
		dataTextPNL.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		dataPanel.add(dataTextPNL);

		// Label que exibe o id do usuário atual 
		// -- parent = dataPanel
		
		idLBL = new JLabel();
		idLBL.setForeground(Color.WHITE);
		idLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		idLBL.setBounds(11, 50, 46, 14);
		dataPanel.add(idLBL);
		
		// Label com o texto "Nome", nada demais 
		// -- parent = dataPanel
		
		JLabel nameTextPNL = new JLabel("NOME");
		nameTextPNL.setForeground(new Color(255, 255, 255));
		nameTextPNL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		nameTextPNL.setBounds(11, 74, 46, 14);
		dataPanel.add(nameTextPNL);

		// Campo para alterar nome do usuário
		// Inativo por padrão
		// -- parent = dataPanel
		
		nameFLD = new JTextField();
		nameFLD.setBorder(new LineBorder(new Color(0, 0, 0)));
		nameFLD.setEnabled(false);
		nameFLD.setBounds(11, 87, 173, 20);
		dataPanel.add(nameFLD);
		
		// Label com o texto "Nome", nada demais 
		// -- parent = dataPanel
		
		JLabel emailTextPNL = new JLabel("EMAIL");
		emailTextPNL.setForeground(Color.WHITE);
		emailTextPNL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		emailTextPNL.setBounds(11, 118, 46, 14);
		dataPanel.add(emailTextPNL);

		// Campo para alterar email do usuário 
		// Inativo por padrão
		// -- parent = dataPanel
		
		emailFLD = new JTextField();
		emailFLD.setBorder(new LineBorder(new Color(0, 0, 0)));
		emailFLD.setEnabled(false);
		emailFLD.setColumns(10);
		emailFLD.setBounds(11, 131, 173, 20);
		dataPanel.add(emailFLD);
		
		// Label com o texto "SENHA", nada demais
		// Indica visualmente a utilidade dos campos abaixo dela
		// -- parent = dataPanel
		
		JLabel passwordTextLBL = new JLabel("SENHA");
		passwordTextLBL.setForeground(Color.WHITE);
		passwordTextLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		passwordTextLBL.setBounds(61, 152, 72, 28);
		dataPanel.add(passwordTextLBL);
		
		// Label com o texto "NOVA SENHA", nada demais 
		// -- parent = dataPanel

		JLabel changePasswordTextLBL = new JLabel("NOVA SENHA");
		changePasswordTextLBL.setForeground(Color.WHITE);
		changePasswordTextLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		changePasswordTextLBL.setBounds(11, 184, 99, 14);
		dataPanel.add(changePasswordTextLBL);
		
		// Campo para digitar a nova senha do usuário
		// Inativo por padrão
		// -- parent = dataPanel
		
		JPasswordField changePasswordFLD = new JPasswordField();
		changePasswordFLD.setBorder(new LineBorder(new Color(0, 0, 0)));
		changePasswordFLD.setEnabled(false);
		changePasswordFLD.setColumns(10);
		changePasswordFLD.setBounds(11, 200, 173, 20);
		dataPanel.add(changePasswordFLD);
		
		// Label com o texto "CONFIRMAR NOVA SENHA", nada demais 
		// -- parent = dataPanel
		
		JLabel confirmPasswordTextLBL = new JLabel("CONFIRMAR NOVA SENHA");
		confirmPasswordTextLBL.setForeground(Color.WHITE);
		confirmPasswordTextLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		confirmPasswordTextLBL.setBounds(11, 229, 173, 14);
		dataPanel.add(confirmPasswordTextLBL);
		
		// Campo para confirmar a nova senha do usuário 
		// Inativo por padrão
		// -- parent = dataPanel
		
		JPasswordField confirmPasswordFLD = new JPasswordField();
		confirmPasswordFLD.setBorder(new LineBorder(new Color(0, 0, 0)));
		confirmPasswordFLD.setEnabled(false);
		confirmPasswordFLD.setColumns(10);
		confirmPasswordFLD.setBounds(11, 243, 173, 20);
		dataPanel.add(confirmPasswordFLD);
		
		// Botão de salvar as alterações 
		// -- parent = frame
		
		JButton saveBTN = new JButton("Salvar Alterações");
		saveBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		saveBTN.setContentAreaFilled(false);
		saveBTN.setBounds(47, 302, 157, 23);
		saveBTN.setEnabled(false);
		getContentPane().add(saveBTN);
		saveBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String email = emailFLD.getText();
				String name = nameFLD.getText();
				String encriptedPassword = null;
				String passwordField1 = String.valueOf(changePasswordFLD.getPassword());
				String passwordField2 = String.valueOf(confirmPasswordFLD.getPassword());

				if (nameFLD.isEnabled() && emailFLD.isEnabled()) {

					// Update name

					if (!name.equals(currentUser.getName())) {
						try {
							dao.updateUserName(currentUser, name);
							currentUser.setName(name);
							ProfileWindow.this.setTitle("bet-betina v1.21 - Perfil de " + currentUser.getName());
							nameLBL.setText(currentUser.getName().concat(
									currentUser.getAccessLevel() == 0 ? " - Usuario Comum." : " - Admnistrador."));
							JOptionPane.showMessageDialog(ProfileWindow.this, "Nome atualizado com sucesso.");
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(ProfileWindow.this, "Erro ao atualizar nome.");
						}
					}

					// Update email

					if (!email.equals(currentUser.getEmail())) {
						try {
							dao.updateUserName(currentUser, email);
							currentUser.setEmail(email);
							JOptionPane.showMessageDialog(ProfileWindow.this, "Email atualizado com sucesso.");
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(ProfileWindow.this, "Erro ao atualizar email.");
						}
					}
				}

				// Update password

				if (changePasswordFLD.isEnabled() && confirmPasswordFLD.isEnabled()) {

					if (passwordField1.length() > 0 && passwordField2.length() > 0) {
						try {
							encriptedPassword = InputManipulation.generateHashedPassword(
									InputManipulation.joinPasswords(passwordField1, passwordField2));
							try {
								dao.updateUserPassword(currentUser, encriptedPassword);
								changePasswordFLD.setText(null);
								confirmPasswordFLD.setText(null);
								JOptionPane.showMessageDialog(ProfileWindow.this, "Senha atualizada com sucesso.");
							} catch (SQLException e1) {
								JOptionPane.showMessageDialog(ProfileWindow.this,
										"Não foi possível atualizar sua senha.");
								e1.printStackTrace();
							}
						} catch (PasswordsDontMatchException e1) {
							JOptionPane.showMessageDialog(ProfileWindow.this, "As senhas não conferem!");
							return;
						}
					}
				}

				// Comentário

				changePasswordFLD.setEnabled(false);
				confirmPasswordFLD.setEnabled(false);
				emailFLD.setEnabled(false);
				nameFLD.setEnabled(false);
				saveBTN.setEnabled(false);

			}
		});
		
		// Botão que permite fazer alterações nos
		// Dados (nome, email) do usuário atual
		// -- parent = dataPanel
		
		JButton editDataBTN = new JButton();
		editDataBTN.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/editBTN.png")));
		editDataBTN.setBounds(164, 50, 20, 20);
		dataPanel.add(editDataBTN);
		editDataBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				emailFLD.setEnabled(true);
				nameFLD.setEnabled(true);
				saveBTN.setEnabled(true);
			}
		});
		
		
		// Botão que permite alterar a senha do usuário 
		// -- parent = dataPanel
		
		JButton editPasswordBTN = new JButton();
		editPasswordBTN.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/editBTN.png")));
		editPasswordBTN.setBounds(164, 162, 20, 20);
		dataPanel.add(editPasswordBTN);
		editPasswordBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePasswordFLD.setEnabled(true);
				confirmPasswordFLD.setEnabled(true);
				saveBTN.setEnabled(true);
			}
		});		
		
		// Painel que exibe as x apostas mais recentes de um usuário
		// , se houver (Ainda não programado / TODO)
		// -- parent = frame

		JPanel statementPNL = new JPanel();
		statementPNL.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		statementPNL.setLayout(null);
		statementPNL.setBackground(new Color(0, 128, 128));
		statementPNL.setBounds(436, 11, 194, 280);
		getContentPane().add(statementPNL);

		// Label com o texto "EXTRATO", nada demais 
		// -- parent = statementPNL
		
		JLabel statementLBL = new JLabel("EXTRATO");
		statementLBL.setForeground(Color.WHITE);
		statementLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		statementLBL.setBounds(45, 11, 103, 28);
		statementPNL.add(statementLBL);


		// Botão de histórico de apostas do usuário (Ainda não programado / TODO)
		// -- parent = frame

		JButton userHistoryBTN = new JButton("Meu Histórico");
		userHistoryBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		userHistoryBTN.setContentAreaFilled(false);
		userHistoryBTN.setBounds(435, 302, 195, 23);
		getContentPane().add(userHistoryBTN);
		userHistoryBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		// Botão que permite ao usuário voltar
		// para a janela anterior
		// -- parent = frame
		
		JButton backBTN = new JButton("");
		backBTN.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/backBTN.png")));
		backBTN.setContentAreaFilled(false);
		backBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		backBTN.setBounds(10, 302, 30, 23);
		getContentPane().add(backBTN);
		backBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		// Botão que permite ao usuário deletar
		// O seu próprio perfil
		// -- parent = frame

		JButton deleteUserBTN = new JButton("Apagar Perfil");
		deleteUserBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		deleteUserBTN.setContentAreaFilled(false);
		deleteUserBTN.setBounds(214, 302, 212, 23);
		deleteUserBTN.setForeground(new Color(255, 0, 0));
		getContentPane().add(deleteUserBTN);
		deleteUserBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmDeleteWindow.turnOn(mainWindow, ProfileWindow.this);
				confirmDeleteWindow.setVisible(true);

			}
		});

		// Botão de exibir estatísticas
		// As estatísticas começam "ocultaas"
		// Para que o usuário não precise esperar
		// O tempo da query em sql para abrir a janela
		// -- parent = frame
		
		JButton statsBTN = new JButton("Ver Estatísticas");
		JButton surroundStatsBTN = new JButton("Ocultar Estatísticas");

		statsBTN.setBounds(18, 126, 175, 23);
		statsBTN.setContentAreaFilled(false);
		statsBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mainPanel.add(statsBTN);
		statsBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					totalBetsLBL.setText("Total de Apostas: " + dao.getTotalBets(currentUser) + ".");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(ProfileWindow.this, "Erro ao ler suas estatísticas.");
					return;
				}
				totalLosesLBL.setText("Total de Derrotas:");
				totalWinsLBL.setText("Total de Vitórias: ");
				victoryRateLBL.setText("Taxa de Vitória:");
				favoriteTeamLBL.setText("Time Favorito: ");
				mainPanel.remove(statsBTN);
				mainPanel.add(surroundStatsBTN);
				surroundStatsBTN.setVisible(true);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		surroundStatsBTN.setVisible(false);

		// Botão de ocultar estatísticas
		// -- parent = frame

		surroundStatsBTN.setBounds(18, 126, 175, 23);
		surroundStatsBTN.setContentAreaFilled(false);
		surroundStatsBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mainPanel.add(surroundStatsBTN);
		surroundStatsBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				totalBetsLBL.setText("");
				totalLosesLBL.setText("");
				totalWinsLBL.setText("");
				victoryRateLBL.setText("");
				favoriteTeamLBL.setText("");
				surroundStatsBTN.setVisible(false);
				mainPanel.add(statsBTN);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});

	}
	
	// Fim do método initialize

	public void turnOn(MainWindow mainWinndowPointer) {
		this.currentUser = mainWinndowPointer.getCurrentUser();
		this.mainWindow = mainWinndowPointer;
		setTitle("bet-betina v1.21 - Perfil de " + currentUser.getName());
		nameLBL.setText(currentUser.getName()
				.concat(currentUser.getAccessLevel() == 2 ? " - Usuario Comum." : " - Admnistrador."));
		balanceLBL.setText(("Saldo Atual: R$ " + currentUser.getBalance() + "."));
		idLBL.setText("ID:" + currentUser.getID());
		nameFLD.setText(currentUser.getName());
		emailFLD.setText(currentUser.getEmail());

	}
}