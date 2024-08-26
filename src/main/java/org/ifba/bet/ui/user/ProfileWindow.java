package org.ifba.bet.ui.user;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.ifba.bet.dao.bet.BetDaoPostgres;
import org.ifba.bet.dao.user.UserDaoPostgres;
import org.ifba.bet.exceptions.PasswordsDontMatchException;
import org.ifba.bet.model.Bet;
import org.ifba.bet.model.User;
import org.ifba.bet.util.InputManipulation;

public class ProfileWindow extends JInternalFrame {

	/*
	 * Classe concreta que herda de JInternalFrame e permite a visualização,
	 * atulização e exclusão do perfil de um usuário "x". Depende de uma MainWindow
	 * para ser instanciada corretamente e ter bom funcionamento. Não pode ser
	 * "executada" isoladamente.
	 */

	// Atributo obrigarório para classes que herdam de JInternalFrame

	private static final long serialVersionUID = 1L;

	// CurrentUser define o usuário atual
	// E serve para buscá-lo no banco de dados

	private User currentUser;

	// Estes componentes da tela são declarados aqui para
	// se tornarem acessíveis aos métodos da classe
	
	private JPanel statementPNL;
	private JTextField nameFLD;
	private JTextField emailFLD;
	private JLabel nameLBL;
	private JLabel idLBL;
	private JLabel balanceLBL;
	private Point initialClick;

	// Conexão com o banco de dados

	private UserDaoPostgres userDao = new UserDaoPostgres();
	private BetDaoPostgres betDao = new BetDaoPostgres();

	// Ponteiro/referência para a janela principal
	// para que seja possível receber e atualizar o usuário
	// atual, de acordo com o fluxo de execução do programa

	private MainWindow mainWindow;

	// Construtor da classe, onde seu design é elaborado

	public ProfileWindow() {

		super();

		// Configurações da janela de perfil

		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setClosable(true);
		setBounds(0, 0, 704, 396);
		getContentPane().setLayout(null);
		
		JLabel qrCodeLBL = new JLabel("EU não deveria estar aparecendo");
		qrCodeLBL.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		qrCodeLBL.setVisible(false);
		qrCodeLBL.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		qrCodeLBL.setOpaque(true);
		qrCodeLBL.setBounds(191, 24, 320, 320);
		
		qrCodeLBL.setIcon(new ImageIcon("src/main/resources/qrCode.png"));
		getContentPane().add(qrCodeLBL);
		qrCodeLBL.addMouseListener(new MouseAdapter() {
            

            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                qrCodeLBL.getComponentAt(initialClick); // Ensures label captures the initial click point
            }
        });

		qrCodeLBL.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Calculate the new position of the label
                int x = e.getX() + qrCodeLBL.getX() - initialClick.x;
                int y = e.getY() + qrCodeLBL.getY() - initialClick.y;

                // Set the new position of the label
                qrCodeLBL.setLocation(x, y);
            }
        });
	
		// Painel principal, o do meio
		// -- parent = this

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mainPanel.setBounds(241, 11, 220, 312);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		// InternalFrame de delete
		// -- parent = this

		ConfirmDeleteUserPanel confirmDeleteWindow = new ConfirmDeleteUserPanel();
		mainPanel.add(confirmDeleteWindow);

		// Label onde o nome do usuário é exibido
		// -- parent = mainPanel

		nameLBL = new JLabel();
		nameLBL.setBounds(12, 77, 202, 14);
		mainPanel.add(nameLBL);
		nameLBL.setFont(new Font("Georgia", Font.PLAIN, 12));

		// Label onde o saldo atual do usuário é exibido
		// -- parent = mainPanel

		balanceLBL = new JLabel();
		balanceLBL.setBounds(12, 103, 163, 14);
		mainPanel.add(balanceLBL);
		balanceLBL.setFont(new Font("Georgia", Font.PLAIN, 12));

		// Label que exibe uma imagem de perfil genérica para o usuário
		// -- parent = mainPanel

		JLabel profilePictureLBL = new JLabel("");
		profilePictureLBL.setBounds(81, 12, 50, 50);
		mainPanel.add(profilePictureLBL);
		profilePictureLBL.setIcon(new ImageIcon("src/main/resources/user.png"));

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
		totalBetsLBL.setFont(new Font("Georgia", Font.PLAIN, 12));

		// Label que exibe o total de derrotas de um usuário
		// -- parent = dataPanel

		JLabel totalLosesLBL = new JLabel();
		totalLosesLBL.setBounds(12, 179, 163, 14);
		mainPanel.add(totalLosesLBL);
		totalLosesLBL.setFont(new Font("Georgia", Font.PLAIN, 12));

		// Label que exibe o total de triunfos de um usuário
		// -- parent = dataPanel

		JLabel totalWinsLBL = new JLabel();
		totalWinsLBL.setBounds(12, 205, 163, 14);
		mainPanel.add(totalWinsLBL);
		totalWinsLBL.setFont(new Font("Georgia", Font.PLAIN, 12));

		// Label que exibe a taxa de acerto de um user
		// -- parent = dataPanel

		JLabel victoryRateLBL = new JLabel();
		victoryRateLBL.setBounds(12, 231, 163, 14);
		mainPanel.add(victoryRateLBL);
		victoryRateLBL.setFont(new Font("Georgia", Font.PLAIN, 12));

		// Label que exibe o time favorito de um usuário
		// baseado no time que ele mais apostou ao longo do tempo
		// -- parent = dataPanel

		JLabel favoriteTeamLBL = new JLabel();
		favoriteTeamLBL.setBounds(12, 257, 163, 14);
		mainPanel.add(favoriteTeamLBL);
		favoriteTeamLBL.setFont(new Font("Georgia", Font.PLAIN, 12));

		// Painel que contém os dados do usuário
		// -- parent = this

		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		dataPanel.setBounds(10, 11, 220, 312);
		dataPanel.setBackground(new Color(0, 128, 128));
		dataPanel.setLayout(null);
		getContentPane().add(dataPanel);

		// Label com o texto "Dados", nada demais
		// -- parent = dataPanel

		JLabel dataTextPNL = new JLabel("DADOS");
		dataTextPNL.setBounds(76, 11, 72, 28);
		dataTextPNL.setForeground(Color.WHITE);
		dataTextPNL.setFont(new Font("Georgia", Font.PLAIN, 20));
		dataPanel.add(dataTextPNL);

		// Label que exibe o id do usuário atual
		// -- parent = dataPanel

		idLBL = new JLabel();
		idLBL.setForeground(Color.WHITE);
		idLBL.setFont(new Font("Georgia", Font.PLAIN, 12));
		idLBL.setBounds(11, 50, 46, 14);
		dataPanel.add(idLBL);

		// Label com o texto "Nome", nada demais
		// -- parent = dataPanel

		JLabel nameTextPNL = new JLabel("NOME");
		nameTextPNL.setForeground(new Color(255, 255, 255));
		nameTextPNL.setFont(new Font("Georgia", Font.PLAIN, 12));
		nameTextPNL.setBounds(11, 74, 46, 14);
		dataPanel.add(nameTextPNL);

		// Campo para alterar nome do usuário
		// inativo por padrão
		// -- parent = dataPanel

		nameFLD = new JTextField();
		nameFLD.setBorder(new LineBorder(new Color(0, 0, 0)));
		nameFLD.setEnabled(false);
		nameFLD.setBounds(11, 87, 199, 20);
		dataPanel.add(nameFLD);

		// Label com o texto "Nome", nada demais
		// -- parent = dataPanel

		JLabel emailTextPNL = new JLabel("EMAIL");
		emailTextPNL.setForeground(Color.WHITE);
		emailTextPNL.setFont(new Font("Georgia", Font.PLAIN, 12));
		emailTextPNL.setBounds(11, 118, 46, 14);
		dataPanel.add(emailTextPNL);

		// Campo para alterar email do usuário
		// inativo por padrão
		// -- parent = dataPanel

		emailFLD = new JTextField();
		emailFLD.setBorder(new LineBorder(new Color(0, 0, 0)));
		emailFLD.setEnabled(false);
		emailFLD.setColumns(10);
		emailFLD.setBounds(11, 131, 199, 20);
		dataPanel.add(emailFLD);

		// Label com o texto "SENHA", nada demais
		// indica visualmente a utilidade dos campos abaixo dela
		// -- parent = dataPanel

		JLabel passwordTextLBL = new JLabel("SENHA");
		passwordTextLBL.setForeground(Color.WHITE);
		passwordTextLBL.setFont(new Font("Georgia", Font.PLAIN, 20));
		passwordTextLBL.setBounds(74, 186, 72, 28);
		dataPanel.add(passwordTextLBL);

		// Label com o texto "NOVA SENHA", nada demais
		// -- parent = dataPanel

		JLabel changePasswordTextLBL = new JLabel("NOVA SENHA");
		changePasswordTextLBL.setForeground(Color.WHITE);
		changePasswordTextLBL.setFont(new Font("Georgia", Font.PLAIN, 12));
		changePasswordTextLBL.setBounds(11, 217, 99, 14);
		dataPanel.add(changePasswordTextLBL);

		// Campo para digitar a nova senha do usuário
		// inativo por padrão
		// -- parent = dataPanel

		JPasswordField changePasswordFLD = new JPasswordField();
		changePasswordFLD.setBorder(new LineBorder(new Color(0, 0, 0)));
		changePasswordFLD.setEnabled(false);
		changePasswordFLD.setColumns(10);
		changePasswordFLD.setBounds(11, 231, 199, 20);
		dataPanel.add(changePasswordFLD);

		// Label com o texto "CONFIRMAR NOVA SENHA", nada demais
		// -- parent = dataPanel

		JLabel confirmPasswordTextLBL = new JLabel("CONFIRMAR NOVA SENHA");
		confirmPasswordTextLBL.setForeground(Color.WHITE);
		confirmPasswordTextLBL.setFont(new Font("Georgia", Font.PLAIN, 12));
		confirmPasswordTextLBL.setBounds(11, 253, 173, 14);
		dataPanel.add(confirmPasswordTextLBL);

		// Campo para confirmar a nova senha do usuário
		// inativo por padrão
		// -- parent = dataPanel

		JPasswordField confirmPasswordFLD = new JPasswordField();
		confirmPasswordFLD.setBorder(new LineBorder(new Color(0, 0, 0)));
		confirmPasswordFLD.setEnabled(false);
		confirmPasswordFLD.setColumns(10);
		confirmPasswordFLD.setBounds(11, 268, 199, 20);
		dataPanel.add(confirmPasswordFLD);

		// Botão de salvar as alterações
		// -- parent = this

		JButton saveBTN = new JButton("Salvar Alterações");
		saveBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		saveBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		saveBTN.setContentAreaFilled(false);
		saveBTN.setBounds(44, 334, 186, 23);
		saveBTN.setEnabled(false);
		getContentPane().add(saveBTN);

		// Salva os dados editados pelo usuário de acordo com
		// os campos ativos e se de fato os dados foram
		// alterados

		saveBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = emailFLD.getText();
				String name = nameFLD.getText();
				String password = null;
				String encriptedPassword = null;
				boolean userChanged = false;
				if (nameFLD.isEnabled() && emailFLD.isEnabled()) {

					// Se o nome digitado no campo de alterar nome for
					// diferente do nome atual do usuário,
					// tenta atualizar o nome no banco de dados

					if (!name.equals(currentUser.getName())) {
						if (!InputManipulation.isValidName(name)) {
							JOptionPane.showMessageDialog(ProfileWindow.this, "O nome precisa ter ao menos 4 letras.");
							return;
						}
						try {
							userDao.updateUserName(currentUser, name);
							update();
							JOptionPane.showMessageDialog(ProfileWindow.this, "Nome atualizado com sucesso.");
							userChanged = true;
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(ProfileWindow.this, "Erro ao atualizar nome.");
						}
					}

					// Se o email digitado no campo de alterar email for
					// diferente do email atual do usuário,
					// tenta atualizar o email no banco de dados

					if (!email.equals(currentUser.getEmail())) {

						if (!InputManipulation.isMinLengthEmail(email)) {
							JOptionPane.showMessageDialog(ProfileWindow.this, "Email muito curto.");
							return;
						}
						if (!InputManipulation.isValidEmail(email)) {
							JOptionPane.showMessageDialog(ProfileWindow.this, "Insira um e-mail válido.");
							return;
						}
						if (userDao.findUserByEmail(email) != null) {
							JOptionPane.showMessageDialog(ProfileWindow.this,
									"Email já cadastrado no banco de dados!");
							return;
						}


						// Tenta atualizar o email

						try {
							userDao.updateUserEmail(currentUser, email);
							JOptionPane.showMessageDialog(ProfileWindow.this, "Email atualizado com sucesso.");
							userChanged = true;
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(ProfileWindow.this, "Erro ao atualizar email.");
						}
					}
				}

				// Se os campos de alterar e confirmar senha
				// estiverem ativos, tenta mesclar as senhas
				// e testar se a senha resultante
				// é válida. Se for, tenta atualizar
				// a senha atual no banco de dados

				if (changePasswordFLD.isEnabled() && confirmPasswordFLD.isEnabled()) {
					try {
						password = InputManipulation.joinPasswords(String.valueOf(changePasswordFLD.getPassword()),
								String.valueOf(confirmPasswordFLD.getPassword()));
					} catch (PasswordsDontMatchException e1) {
						JOptionPane.showMessageDialog(ProfileWindow.this, "As senhas não conferem!");
						return;
					}
					if (!InputManipulation.isMinLengthPassword(password)) {
						JOptionPane.showMessageDialog(ProfileWindow.this, "Senha muito curta. Use ao menos 8 digitos.");
						return;
					}
					encriptedPassword = InputManipulation.generateHashedPassword(password);
					try {
						// Atualiza a senha no banco de dados
						userDao.updateUserPassword(currentUser, encriptedPassword);
						// Reseta os campos de senha e confirmação de senha
						changePasswordFLD.setText(null);
						confirmPasswordFLD.setText(null);
						JOptionPane.showMessageDialog(ProfileWindow.this, "Senha atualizada com sucesso.");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(ProfileWindow.this, "Não foi possível atualizar sua senha.");
					}
				}
				if (userChanged) {
					currentUser = userDao.findUserByEmail(currentUser.getEmail());

				}
				changePasswordFLD.setEnabled(false);
				confirmPasswordFLD.setEnabled(false);
				emailFLD.setEnabled(false);
				nameFLD.setEnabled(false);
				saveBTN.setEnabled(false);
			}
		});

		// Botão que permite fazer alterações nos
		// dados (nome, email) do usuário atual
		// -- parent = dataPanel

		JButton editDataBTN = new JButton();
		editDataBTN.setIcon(new ImageIcon("src/main/resources/editBTN.png"));
		editDataBTN.setBounds(190, 50, 20, 20);
		dataPanel.add(editDataBTN);

		// Ativa os campos de email e nome para
		// que o usuário possa os alterar

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
		editPasswordBTN.setIcon(new ImageIcon("src/main/resources/editBTN.png"));
		editPasswordBTN.setBounds(190, 194, 20, 20);
		dataPanel.add(editPasswordBTN);

		// Ativa os campos de alterar e confirmar
		// senha para que que o usuário possa os alterar

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
		// -- parent = this

		statementPNL = new JPanel();
		statementPNL.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		statementPNL.setBackground(new Color(0, 128, 128));
		statementPNL.setBounds(473, 11, 220, 312);
		getContentPane().add(statementPNL);
		statementPNL.setLayout(null);

		// Botão de histórico de apostas do usuário (Ainda não programado / TODO)
		// -- parent = this

		JButton userHistoryBTN = new JButton("Fazer Depósito");
		userHistoryBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		userHistoryBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		userHistoryBTN.setContentAreaFilled(false);
		userHistoryBTN.setBounds(473, 334, 220, 23);
		getContentPane().add(userHistoryBTN);
		userHistoryBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(qrCodeLBL.isVisible()) {
					qrCodeLBL.setVisible(false);
					return;
				}
				qrCodeLBL.setVisible(true);
				try {
					userDao.updateUserBalance(currentUser, (currentUser.getBalance() + 100));
					JOptionPane.showMessageDialog(ProfileWindow.this, "Saldo aumentado em R$ 100.",
							"Info",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(ProfileWindow.this, "Erro ao te dar dinheiro. Tente depois.",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				update();
			}
		});

		// Botão que permite ao usuário deletar
		// o seu próprio perfil
		// -- parent = this

		JButton deleteUserBTN = new JButton("Apagar Perfil");
		deleteUserBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		deleteUserBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		deleteUserBTN.setContentAreaFilled(false);
		deleteUserBTN.setBounds(241, 334, 220, 23);
		deleteUserBTN.setForeground(new Color(255, 0, 0));
		getContentPane().add(deleteUserBTN);

		// Abre uma nova janela para que o usuário
		// decida se quer ou não excluir seu perfil

		deleteUserBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmDeleteWindow.turnOn(mainWindow, ProfileWindow.this);
				confirmDeleteWindow.setVisible(true);

			}
		});

		// Botão de exibir estatísticas
		// as estatísticas começam "ocultaas"
		// para que o usuário não precise esperar
		// o tempo da query em sql para abrir a janela
		// -- parent = this

		JButton statsBTN = new JButton("Ver Estatísticas");
		statsBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		JButton surroundStatsBTN = new JButton("Ocultar Estatísticas");

		statsBTN.setBounds(18, 126, 175, 23);
		statsBTN.setContentAreaFilled(false);
		statsBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mainPanel.add(statsBTN);
		statsBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int currentUserId = currentUser.getId();
				int wins = userDao.getTotalWinnedBets(currentUserId);
				int loses = userDao.getTotalLosedBets(currentUserId);
				int totalBets = userDao.getTotalBets(currentUserId);
				double victoryRate;
				try {
					victoryRate = (wins * 100) / totalBets ;
				}catch(ArithmeticException arithmeticException ){
					victoryRate = 0;
				}
				totalBetsLBL.setText("Total de Apostas: " + totalBets + ".");
				try {
					favoriteTeamLBL.setText("Time Favorito: " + userDao.getFavoriteTeam(currentUser) + ".");
				} catch (SQLException e1) {
					favoriteTeamLBL.setText("Time Favorito: Nenhum.");
				}
				totalLosesLBL.setText("Total de Derrotas: " + loses + ".");
				totalWinsLBL.setText("Total de Vitórias: " + wins  + ".");
				victoryRateLBL.setText("Taxa de Vitória: " + victoryRate + "%.");
				statsBTN.setVisible(false);
				surroundStatsBTN.setVisible(true);
			}
		});
		

		// Botão de ocultar estatísticas
		// -- parent = this

		surroundStatsBTN.setBounds(18, 126, 175, 23);
		surroundStatsBTN.setContentAreaFilled(false);
		surroundStatsBTN.setVisible(false);
		surroundStatsBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
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
				statsBTN.setVisible(true);
				mainPanel.add(statsBTN);
			}
		});

		// Botão que permite ao usuário voltar
		// para a janela anterior
		// -- parent = this

		JButton backBTN = new JButton("");
		backBTN.setIcon(new ImageIcon("src/main/resources/backBTN.png"));
		backBTN.setContentAreaFilled(false);
		backBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		backBTN.setBounds(10, 334, 30, 23);
		getContentPane().add(backBTN);

		// Fecha a janela de perfil e
		// efetivamente retorna para a
		// janela anterior

		backBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (surroundStatsBTN.isVisible()) {
					surroundStatsBTN.doClick();
				}
				dispose();
			}
		});

		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				if (surroundStatsBTN.isVisible()) {
					surroundStatsBTN.doClick();
				}
			}
		});
		updateStatement();
		// Fim do constructor

	}

	// Personaliza a janela de acordo com o usuário recebido
	// e define a referência para a janela principal
	// indispensável para o funcionamento adequado da classe

	public void update(){
		setTitle("Bet-Betina v1.23 - Perfil de " + currentUser.getName());
		nameLBL.setText(currentUser.getName()
				.concat(currentUser.getAccessLevel() == User.REGULAR_USER ? " - Usuario Comum." : " - Admnistrador."));
		balanceLBL.setText(("Saldo Atual: R$ " + currentUser.getBalance() + "."));
		idLBL.setText("ID:" + currentUser.getId());
		nameFLD.setText(currentUser.getName());
		emailFLD.setText(currentUser.getEmail());
		updateStatement();
	}
	
	public void updateStatement() {
		if (currentUser == null) {
			return;
		}
		int initialPosX = 10;
		int initialPosY = 45;
		int foundedBets = 0 ;
		statementPNL.removeAll();
		statementPNL.revalidate();
		statementPNL.repaint();
		JLabel statementLBL = new JLabel("EXTRATO");
		statementLBL.setBounds(61, 11, 103, 28);
		statementLBL.setForeground(Color.WHITE);
		statementLBL.setFont(new Font("Georgia", Font.PLAIN, 20));
		statementPNL.add(statementLBL);
		try {
			ArrayList<Bet> betList = betDao.getAllBets(currentUser.getId());
			if (betList == null) {
				return;
			}
			Collections.reverse(betList);
			for (Bet bet : betList) {
				int betId = bet.getId();
				if (bet.getState() == Bet.CLOSED || bet.getState() == Bet.OPEN) {
					continue;
				}
				if(foundedBets == 10) {
					break;
				}
				JLabel lblR = new JLabel();
				lblR.setForeground(Color.WHITE);
				lblR.setFont(new Font("Georgia", Font.PLAIN, 14));
				statementPNL.add(lblR);
				lblR.setBounds(initialPosX, initialPosY, 200, 25);
				if(bet.getState() == Bet.WIN) {
					lblR.setText("+ RS " + betDao.getBetPayout(betId) + " em Aposta Ganha");
				}
				else {
					lblR.setText("- RS " + betDao.getBetTotalValue(betId) + " em Aposta Perdida");
				}
				initialPosY += 25;
				foundedBets++;
			}
		} catch (SQLException e) {
			System.out.println("Unexpected Error");
		}
		if(foundedBets == 0) {
			JLabel lblR = new JLabel("Sem Apostas Para Exibir.");
			lblR.setForeground(Color.WHITE);
			lblR.setFont(new Font("Georgia", Font.PLAIN, 14));
			statementPNL.add(lblR);
			lblR.setBounds(initialPosX, initialPosY, 200, 25);
		}
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
}