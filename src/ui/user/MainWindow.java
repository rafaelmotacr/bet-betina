package ui.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.User;
import ui.team.TesteClass;

public class MainWindow {
	
	/* Classe concreta. Possui um JFrame que serve
	 * de "container" para todas as janelas e subjanelas
	 * da aplicação. É a janela principal da aplicação
	 * e sempre está aberta. Todas as operações do programa
	 * são feitas a partir dela.
	 * Pode ser executada a partir da classe "Program.java".
	 * Deve evitar-se executá-la diretamente.
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
		frame.setBounds((int) ((Toolkit.getDefaultToolkit()).getScreenSize().getWidth() / 2 - 768 / 2), (int) ((Toolkit.getDefaultToolkit()).getScreenSize().getHeight() / 2 - 432 / 2), 768, 432);
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

		TesteClass testeTeste = new TesteClass();
		testeTeste.setVisible(false);
		testeTeste.setLocation(64, 36);
		frame.getContentPane().add(testeTeste);
		
		RegisterUserWindow registerUserWindow = new RegisterUserWindow(MainWindow.this);
		registerUserWindow.setLocation(259, 78);
		frame.getContentPane().add(registerUserWindow);

		RegisterAdminWindow registerAdminWindow = new RegisterAdminWindow(MainWindow.this);
		registerAdminWindow.setLocation(259, 78);
		frame.getContentPane().add(registerAdminWindow);


		// Botão de log in 
		//-- parent = frame
		
		loginBTN = new JButton("Fazer Log In");
		loginBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		loginBTN.setForeground(new Color(255, 255, 255));
		loginBTN.setBounds(140, 33, 109, 26);
		loginBTN.setContentAreaFilled(false);
		loginBTN.setFocusPainted(false);
		loginBTN.setMnemonic('L');
		loginBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		loginBTN.setVisible(true);
		frame.getContentPane().add(loginBTN);
		
		// Ativa a janela de login 
		// se o usuário tentar se registrar e logar 
		// ao mesmo tempo, cancela a chamada de login
		
		loginBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (registerUserWindow.isVisible()) {
					JOptionPane.showMessageDialog(frame, "Primeiro feche a outra janela!");
					return;
				}
				loginWindow.setVisible(true);
			}
		});

		// Botão de registro de usuários
		// -- parent = frame

		registerUserBTN = new JButton("Registrar");
		registerUserBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		registerUserBTN.setForeground(new Color(255, 255, 255));
		registerUserBTN.setBounds(434, 33, 121, 26);
		registerUserBTN.setContentAreaFilled(false);
		registerUserBTN.setMnemonic('R');
		registerUserBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		registerUserBTN.setVisible(true);
		frame.getContentPane().add(registerUserBTN);
		
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
				registerUserWindow.setVisible(true);
			}
		});

		// Botão de perfil 
		// -- parent = frame

		profileBTN = new JButton("Meu Perfil");
		profileBTN.setBounds(140, 33, 109, 26);
		profileBTN.setForeground(new Color(255, 255, 255));
		profileBTN.setContentAreaFilled(false);
		profileBTN.setMnemonic('p');
		profileBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		profileBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		profileBTN.setVisible(false);
		frame.getContentPane().add(profileBTN);
		
		// Ativa a janela de perfil 
		// e tranfere para ela uma referência da janela atual
		
		profileBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				profileWindow.turnOn(MainWindow.this);
				profileWindow.setVisible(true);
			}
		});

		// Botão de log out 
		// -- parent = frame
		
		logOutBTN =  new JButton("Fazer Log Out");
		logOutBTN.setBounds(434, 33, 121, 26);
		logOutBTN.setForeground(new Color(255, 255, 255));
		logOutBTN.setContentAreaFilled(false);
		logOutBTN.setMnemonic('O');
		logOutBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		logOutBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		logOutBTN.setVisible(false);
		frame.getContentPane().add(logOutBTN);
		
		// Efetivamente desloga o usuário por 
		// definir o usuário atual como "null"
		// se a subjanela de perfil estiver aberta,
		// fecha ela
		
		logOutBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateUser(null);
				if(profileWindow.isVisible()) {
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
		createADMBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		createADMBTN.setBounds(261, 33, 161, 26);
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
				if(profileWindow.isVisible()) {
					JOptionPane.showMessageDialog(frame, "Primeiro feche a outra janela!");
					return;
				}
				registerAdminWindow.setVisible(true);
			}
		});
		
		
		// Label de cumprimento ao usuário 
		// -- parent = frame

		greetingLBL = new JLabel("Seja Bem Vindo (a), visitante!");
		greetingLBL.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		greetingLBL.setBounds(140, 386, 415, 34);
		greetingLBL.setForeground(new Color(255, 255, 255));
		greetingLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		greetingLBL.setBackground(new Color(51, 51, 51));
		frame.getContentPane().add(greetingLBL);

		// Logo com a Betina 
		// -- parent = frame

		JLabel logoLBL = new JLabel("");
		logoLBL.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		logoLBL.setBounds(140, 67, 415, 313);
		logoLBL.setBackground(new Color(102, 0, 0));
		logoLBL.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/logoMain.png")));
		frame.getContentPane().add(logoLBL);

		// Painel onde ficam os botões 
		// -- parent = frame
		
		JPanel mainPNL = new JPanel();
		mainPNL.setBorder(new LineBorder(new Color(0, 0, 0)));
		mainPNL.setBounds(10, 33, 124, 389);
		mainPNL.setBackground(Color.WHITE);
		mainPNL.setLayout(null);
		frame.getContentPane().add(mainPNL);
		
		// Label com o titulo 
		// -- parent = mainPanel

		JLabel appNameLBL = new JLabel("BET BETINA");
		appNameLBL.setBounds(2, 0, 120, 33);
		appNameLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		mainPNL.add(appNameLBL);

		// Botão de sair 
		// -- parent = mainPanel

		JButton exitBTN = new JButton("SAIR");
		exitBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		exitBTN.setBounds(12, 355, 98, 26);
		exitBTN.setContentAreaFilled(false);
		exitBTN.setMnemonic('S');
		exitBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		mainPNL.add(exitBTN);
		
		// Fecha a janela principal usando o método dispose
		
		exitBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		// Botão de apostar TODO
		// -- parent = mainPanel

		JButton betBTN = new JButton("Apostar");
		betBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		betBTN.setBounds(12, 45, 98, 26);
		betBTN.setContentAreaFilled(false);
		betBTN.setMnemonic('A');
		betBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		mainPNL.add(betBTN);
		betBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentUser == null) {
					JOptionPane.showMessageDialog(frame, "Você precisa estar logado para apostar!");
				}
			}
		});

		// Botão de ver jogos TODOS
		// -- parent = mainPanel

		JButton matchBTN = new JButton("Ver Jogos");
		matchBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		matchBTN.setBounds(12, 77, 98, 26);
		matchBTN.setContentAreaFilled(false);
		matchBTN.setMnemonic('j');
		matchBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		mainPNL.add(matchBTN);
		matchBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		mainPNL.add(matchBTN);

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
		teamBTN.setBounds(12, 112, 98, 26);
		teamBTN.setContentAreaFilled(false);
		teamBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		mainPNL.add(teamBTN);
		teamBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testeTeste.setUser(currentUser);
				testeTeste.setMainWindow(MainWindow.this);
				testeTeste.turnOn();
				testeTeste.setVisible(true);
			}
		});

		// Painel de destaques 
		// -- parent = frame

		JPanel highlitsPNL = new JPanel();
		highlitsPNL.setBorder(new LineBorder(new Color(0, 0, 0)));
		highlitsPNL.setBounds(564, 33, 194, 387);
		highlitsPNL.setBackground(Color.WHITE);
		frame.getContentPane().add(highlitsPNL);
		highlitsPNL.setLayout(null);

		// Destaques (falta programar, TODO) 
		// -- parent = highlitsPNL

		JLabel highlitsLBL = new JLabel("Destaques");
		highlitsLBL.setBounds(53, 0, 87, 33);
		highlitsLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		highlitsPNL.add(highlitsLBL);

		// Linha preta meramente estétitca 2 
		// -- parent = highlitsPNL

		JPanel blackLine_1 = new JPanel();
		blackLine_1.setBounds(6, 36, 181, 3);
		blackLine_1.setForeground(Color.BLACK);
		blackLine_1.setBackground(Color.BLACK);
		highlitsPNL.add(blackLine_1);

		// Barra preta superior 
		// -- parent = highlitsPNL

		JPanel titleBarPNL = new JPanel();
		titleBarPNL.setBounds(0, 0, 768, 25);
		titleBarPNL.setForeground(Color.BLACK);
		titleBarPNL.setBackground(Color.BLACK);
		frame.getContentPane().add(titleBarPNL);
		titleBarPNL.setLayout(null);

		// "Título" do app 
		// -- parent = titleBarPNL

		JLabel titleLBL = new JLabel("Bet-Betina v1.23 - Home");
		titleLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
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
		
		// Finaliza completamente a execução do programa
		
		closeBTN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

	}

	// Fim do método "initialize"

	public void updateUser(User user) {
		currentUser = user;
		updateButtons();
		updateGreetingLabel();
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

	// Atualiza a label de cumprimento ao usuário de acordo
	// com seu nivel de acesso
	// Admnistrador > Usuário > Visitante 
	
	private void updateGreetingLabel() {
		if (currentUser == null) {
			greetingLBL.setText("Seja Bem Vindo (a), visitante!");
		} else if (currentUser.getAccessLevel() == 1) {
			greetingLBL.setText("Bem Vindo (a) de volta, administrador (a)!");
		} else {
			greetingLBL.setText("Bem Vindo (a) de volta, " + currentUser.getName() + "!");
		}
	}
	
	// Retorna o usuário atual

	public User getCurrentUser() {
		return currentUser;
	}
	
}
