package org.ifba.bet.ui.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.bet.BetDaoPostgres;
import org.ifba.bet.dao.match.MatchDaoPostgres;
import org.ifba.bet.dao.user.UserDaoPostgres;
import org.ifba.bet.model.Bet;
import org.ifba.bet.model.Match;
import org.ifba.bet.model.User;
import org.ifba.bet.ui.bet.BetMainWindow;
import org.ifba.bet.ui.team.TeamMainWindow;

public class MainWindow {

	/*
	 * Classe concreta. Possui um JFrame que serve de "container" para todas as
	 * janelas e subjanelas da aplicação. É a janela principal da aplicação e sempre
	 * está aberta. Todas as operações do programa são feitas a partir dela. Pode
	 * ser executada a partir da classe "Program.java". Deve evitar-se executá-la
	 * diretamente.
	 */

	// Janela principal do programa

	public JFrame frame;

	// CurrentUser define o usuário atual
	// e serve para buscá-lo no banco de dados

	private User currentUser;

	// Estes componentes da tela são declarados aqui para
	// se tornarem acessíveis aos métodos da classe

	private JLabel greetingLBL;
	private JButton loginBTN;
	private JButton registerUserBTN;
	private JButton logOutBTN;
	private JButton profileBTN;
	private JButton createADMBTN;

	private MatchDaoPostgres matchDao = new MatchDaoPostgres();
	private BetDaoPostgres betDao = new BetDaoPostgres();
	private UserDaoPostgres userDao = new UserDaoPostgres();

	private DefaultListModel<Match> listModel;

	// Coordenadas utilizadas para movimentação dinâmica da janela

	private int xx, xy;

	// Construtor da classe1

	public MainWindow() {
		initialize();
	}

	private void initialize() {

		// Janela principal e suas configurações

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 128, 128));
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(0, 128, 128));
		frame.setBounds((int) ((Toolkit.getDefaultToolkit()).getScreenSize().getWidth() / 2 - 768 / 2),
				(int) ((Toolkit.getDefaultToolkit()).getScreenSize().getHeight() / 2 - 432 / 2), 768, 432);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Captura a posição atual do mouse

		frame.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});

		// Movimenta o painel principal de acordo com a posição atual do mouse

		frame.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
				int y = arg0.getYOnScreen();
				frame.setLocation(x - xx, y - xy);
			}
		});

		// Todas as janelas secundárias

		ProfileWindow profileWindow = new ProfileWindow();
		profileWindow.setLocation(64, 36);
		frame.getContentPane().add(profileWindow);

		LoginWindow loginWindow = new LoginWindow(MainWindow.this);
		loginWindow.setLocation(259, 78);
		frame.getContentPane().add(loginWindow);

		RegisterCommonUserWindow registerCommonUserWindow = new RegisterCommonUserWindow(MainWindow.this);
		registerCommonUserWindow.setLocation(259, 78);
		frame.getContentPane().add(registerCommonUserWindow);

		RegisterAdminUserWindow registerAdminUserWindow = new RegisterAdminUserWindow(MainWindow.this);
		registerAdminUserWindow.setLocation(259, 78);
		frame.getContentPane().add(registerAdminUserWindow);

		TeamMainWindow teamMainWindow = new TeamMainWindow();
		teamMainWindow.setVisible(false);
		teamMainWindow.setLocation(64, 36);
		frame.getContentPane().add(teamMainWindow);

		BetMainWindow betMainWindow = new BetMainWindow();
		betMainWindow.setVisible(false);
		betMainWindow.setLocation(32, 18);
		frame.getContentPane().add(betMainWindow);

		// Botão de log out
		// -- parent = frame

		logOutBTN = new JButton("Fazer Log Out");
		logOutBTN.setBounds(140, 33, 121, 26);
		logOutBTN.setForeground(new Color(255, 255, 255));
		logOutBTN.setContentAreaFilled(false);
		logOutBTN.setMnemonic('O');
		logOutBTN.setFont(new Font("Georgia", Font.BOLD, 14));
		logOutBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		logOutBTN.setVisible(false);

		// Efetivamente desloga o usuário por
		// definir o usuário atual como "null"
		// se a subjanela de perfil estiver aberta,
		// fecha ela

		logOutBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateUser(null);
				if (profileWindow.isVisible()) {
					profileWindow.dispose();
				}
				JOptionPane.showMessageDialog(frame, "Log Out realizado com sucesso.");
			}
		});

		// Botão de criar novos admnistradores
		// só aparece caso o usuário logado
		// seja um admnistrador
		// -- parent = frame

		createADMBTN = new JButton("Criar Novo ADM");
		createADMBTN.setForeground(new Color(255, 255, 255));
		createADMBTN.setContentAreaFilled(false);
		createADMBTN.setFont(new Font("Georgia", Font.BOLD, 14));
		createADMBTN.setBounds(271, 33, 150, 26);
		createADMBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		createADMBTN.setVisible(false);
		frame.getContentPane().add(createADMBTN);

		// Ativa a janela de registro de admnistradores
		// presume que o usuário já está logado
		// no entanto, se a janela de perfil estiver aberta,
		// cancela a ação

		createADMBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (profileWindow.isVisible()) {
					JOptionPane.showMessageDialog(frame, "Primeiro feche a outra janela!");
					return;
				}
				registerAdminUserWindow.setVisible(true);
			}
		});

		// Label de cumprimento ao usuário
		// -- parent = frame

		greetingLBL = new JLabel("Seja Bem Vindo (a), Visitante!");
		greetingLBL.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		greetingLBL.setBounds(140, 386, 415, 34);
		greetingLBL.setForeground(new Color(255, 255, 255));
		greetingLBL.setFont(new Font("Georgia", Font.BOLD, 16));
		greetingLBL.setBackground(new Color(51, 51, 51));
		frame.getContentPane().add(greetingLBL);

		// Logo com a Betina
		// -- parent = frame

		JLabel logoLBL = new JLabel("");
		logoLBL.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		logoLBL.setBounds(140, 67, 415, 313);
		logoLBL.setBackground(new Color(102, 0, 0));
		logoLBL.setIcon(new ImageIcon("src/main/resources/logoMain.png"));
		frame.getContentPane().add(logoLBL);

		// Painel onde ficam os botões
		// -- parent = frame

		JPanel mainPNL = new JPanel();
		mainPNL.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		mainPNL.setBounds(10, 33, 124, 389);
		mainPNL.setBackground(Color.WHITE);
		mainPNL.setLayout(null);
		frame.getContentPane().add(mainPNL);

		// Label com o titulo
		// -- parent = mainPanel

		JLabel appNameLBL = new JLabel("BET BETINA");
		appNameLBL.setBounds(2, 0, 120, 33);
		appNameLBL.setFont(new Font("Georgia", Font.BOLD, 18));
		mainPNL.add(appNameLBL);

		// Botão de sair
		// -- parent = mainPanel

		JButton exitBTN = new JButton("SAIR");
		exitBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		exitBTN.setBounds(12, 355, 98, 26);
		exitBTN.setContentAreaFilled(false);
		exitBTN.setMnemonic('S');
		exitBTN.setFont(new Font("Georgia", Font.BOLD, 14));
		mainPNL.add(exitBTN);

		// Fecha a janela principal usando o método dispose

		exitBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		// Botão de ver jogos TODOS
		// -- parent = mainPanel

		JButton betBTN = new JButton("Apostar");
		betBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		betBTN.setBounds(12, 50, 98, 26);
		betBTN.setContentAreaFilled(false);
		betBTN.setMnemonic('A');
		betBTN.setFont(new Font("Georgia", Font.BOLD, 14));
		mainPNL.add(betBTN);
		betBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentUser == null) {
					JOptionPane.showMessageDialog(frame, "Você precisa estar logado para apostar!");
					return;
				}
				if (currentUser.getAccessLevel() == User.ADMIN) {
					JOptionPane.showMessageDialog(frame, "Admnistradores Não Podem Apostar.", "Acesso Negado",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				betMainWindow.setCurrentUser(currentUser);
				betMainWindow.setVisible(true);
			}
		});
		mainPNL.add(betBTN);

		// Linha preta meramente estétitca
		// -- parent = mainPanel

		JPanel blackLine = new JPanel();
		blackLine.setBounds(1, 36, 119, 3);
		blackLine.setForeground(new Color(0, 0, 0));
		blackLine.setBackground(new Color(0, 0, 0));
		mainPNL.add(blackLine);

		// Botão de times
		// -- parent = mainPanel

		JButton teamBTN = new JButton("Ver Times");
		teamBTN.setMnemonic('t');
		teamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		teamBTN.setBounds(12, 87, 98, 26);
		teamBTN.setContentAreaFilled(false);
		teamBTN.setFont(new Font("Georgia", Font.BOLD, 14));
		mainPNL.add(teamBTN);

		JButton matchBTN = new JButton("Ver Partidas");
		matchBTN.setMnemonic('t');
		matchBTN.setFont(new Font("Georgia", Font.BOLD, 14));
		matchBTN.setContentAreaFilled(false);
		matchBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		matchBTN.setBounds(12, 124, 100, 26);
		mainPNL.add(matchBTN);
		teamBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamMainWindow.setUser(currentUser);
				teamMainWindow.setMainWindow(MainWindow.this);
				teamMainWindow.turnOn();
				teamMainWindow.setVisible(true);
			}
		});

		// "Título" do app
		// -- parent = titleBarPNL

		JPanel titleBarPNL = new JPanel();
		titleBarPNL.setBounds(0, 0, 768, 25);
		titleBarPNL.setForeground(Color.BLACK);
		titleBarPNL.setBackground(Color.BLACK);
		frame.getContentPane().add(titleBarPNL);
		titleBarPNL.setLayout(null);

		JLabel titleLBL = new JLabel("Bet-Betina v1.23 - Home");
		titleLBL.setFont(new Font("Georgia", Font.BOLD, 14));
		titleLBL.setForeground(new Color(255, 255, 255));
		titleLBL.setBounds(10, 4, 185, 16);
		titleBarPNL.add(titleLBL);

		// "Botão" de fechar
		// -- parent = titleBarPNL

		JLabel closeBTN = new JLabel("X");
		closeBTN.setForeground(new Color(255, 255, 255));
		closeBTN.setBounds(744, 0, 12, 23);
		closeBTN.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titleBarPNL.add(closeBTN);

		// Botão de registro de usuários
		// -- parent = frame

		registerUserBTN = new JButton("Registrar");
		registerUserBTN.setBounds(431, 33, 124, 26);
		frame.getContentPane().add(registerUserBTN);
		registerUserBTN.setFont(new Font("Georgia", Font.BOLD, 14));
		registerUserBTN.setForeground(new Color(255, 255, 255));
		registerUserBTN.setContentAreaFilled(false);
		registerUserBTN.setMnemonic('R');
		registerUserBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		// Botão de perfil
		// -- parent = frame

		profileBTN = new JButton("Meu Perfil");
		profileBTN.setBounds(431, 33, 124, 26);
		frame.getContentPane().add(profileBTN);
		profileBTN.setForeground(new Color(255, 255, 255));
		profileBTN.setContentAreaFilled(false);
		profileBTN.setMnemonic('p');
		profileBTN.setFont(new Font("Georgia", Font.BOLD, 14));
		profileBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		// Botão de log in
		// -- parent = frame

		loginBTN = new JButton("Fazer Log In");
		loginBTN.setBounds(140, 33, 121, 26);
		frame.getContentPane().add(loginBTN);
		loginBTN.setFont(new Font("Georgia", Font.BOLD, 14));
		loginBTN.setForeground(new Color(255, 255, 255));
		loginBTN.setContentAreaFilled(false);
		loginBTN.setFocusPainted(false);
		loginBTN.setMnemonic('L');
		loginBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		loginBTN.setVisible(true);

		// Ativa a janela de login
		// se o usuário tentar se registrar e logar
		// ao mesmo tempo, cancela a chamada de login

		loginBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (registerCommonUserWindow.isVisible()) {
					JOptionPane.showMessageDialog(frame, "Primeiro feche a outra janela!");
					return;
				}
				loginWindow.setVisible(true);
			}
		});
		profileBTN.setVisible(false);

		// Ativa a janela de perfil
		// e tranfere para ela uma referência da janela atual

		profileBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				profileWindow.turnOn(MainWindow.this);
				profileWindow.setVisible(true);
			}
		});
		registerUserBTN.setVisible(true);

		// Ativa a janela de registro de usuários
		// se o usuário tentar registrar e logar
		// ao mesmo tempo, cancela a chamada do registro

		registerUserBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (loginWindow.isVisible()) {
					JOptionPane.showMessageDialog(frame, "Primeiro feche a outra janela!");
					return;
				}
				registerCommonUserWindow.setVisible(true);
			}
		});

		// Finaliza completamente a execução do programa

		closeBTN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		// Destaques (falta programar, TODO)
		// -- parent = highlitsPNL

		// Painel de destaques
		// -- parent = frame

		JPanel highlitsPNL = new JPanel();
		highlitsPNL.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		highlitsPNL.setBounds(564, 33, 194, 26);
		highlitsPNL.setBackground(Color.WHITE);
		frame.getContentPane().add(highlitsPNL);
		highlitsPNL.setLayout(null);

		JLabel highlitsLBL = new JLabel("Partidas Ativas");
		highlitsLBL.setBounds(27, 2, 140, 22);
		highlitsLBL.setFont(new Font("Georgia", Font.BOLD, 18));
		highlitsPNL.add(highlitsLBL);

		listModel = new DefaultListModel<>();
		JList<Match> highlitsList = new JList<>(listModel);

		highlitsList.setOpaque(false);
		highlitsList.setFont(new Font("Georgia", Font.PLAIN, 14));
		highlitsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		frame.getContentPane().add(logOutBTN);
		highlitsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int index = highlitsList.locationToIndex(e.getPoint());
				if (index != -1) {
					highlitsList.clearSelection();
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(highlitsList);
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane.setBounds(564, 67, 194, 352);
		frame.getContentPane().add(scrollPane);

		updateHighlits();

	}

	// Fim do método "initialize"

	public void updateUser(User user) {
		currentUser = user;
		updateUserBets();
		updateButtons();
		updateGreetingLabel();
	}

	private void updateUserBets() {
		if (currentUser == null) {
			return;
		}
		try {
			ArrayList<Bet> betList = betDao.getAllBets(currentUser.getID());
			if (betList == null) {
				return;
			}
			for (Bet bet : betList) {
				int betId = bet.getID();
				if (bet.getState() != 0) {
					return;
				}
				if (!betDao.isBetCompleted(betId)) {
					return;
				}

				if (betDao.isBetCorrect(betId)) {
					betDao.updateBetState(3, betId);
				} else {
					betDao.updateBetState(4, betId);
				}
				double betPayout = betDao.getBetPayout(betId);
				userDao.updateUserBalance(currentUser, (currentUser.getBalance() + betPayout));
			}
		} catch (SQLException e) {
			System.out.println("Unexpected Error");
		}
	}

	// Oculta os botões de login e registro se o usuário já estiver logado
	// e os traz de volta se necessário

	private void updateButtons() {
		if (currentUser != null) {
			// Se houver um usuário logado
			// exibe apenas os botoões de log out e perfil

			loginBTN.setVisible(false);
			registerUserBTN.setVisible(false);

			logOutBTN.setVisible(true);
			profileBTN.setVisible(true);

			// Caso este usuário seja admnistrador
			// adiciona o botão de criar novos admnistradores

			if (currentUser.getAccessLevel() == 1) {
				createADMBTN.setVisible(true);
			}
		} else {
			// Se o usuário realizou log out e os botões
			// ainda estão visíveis, os retira da tela
			// antes de adicionar os de registro e login

			if (logOutBTN.isVisible()) {
				logOutBTN.setVisible(false);
				profileBTN.setVisible(false);
				createADMBTN.setVisible(false);
			}
			loginBTN.setVisible(true);
			registerUserBTN.setVisible(true);

		}
	}

	public void updateHighlits() {
		ArrayList<Match> matchs = null;
		try {
			matchs = matchDao.getActiveMatchs();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		listModel.clear();
		for (Match match : matchs) {
			listModel.addElement(match);
		}
	}

	// Atualiza a label de cumprimento ao usuário de acordo
	// com seu nivel de acesso
	// Admnistrador > Usuário > Visitante

	private void updateGreetingLabel() {
		if (currentUser == null) {
			greetingLBL.setText("Seja Bem Vindo (a), Visitante!");
		} else if (currentUser.getAccessLevel() == 1) {
			greetingLBL.setText("Bem Vindo (a) de volta, Administrador (a)!");
		} else {
			greetingLBL.setText("Bem Vindo (a) de volta, " + currentUser.getName() + "!");
		}
	}

	// Retorna o usuário atual

	public User getCurrentUser() {
		return currentUser;
	}
}
