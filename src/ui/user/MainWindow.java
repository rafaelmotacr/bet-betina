package ui.user;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.User;

public class MainWindow {

	public JFrame frame;
	private User currentUser = null;
	private JLabel greetingLabel;
	private JButton loginBTN = null;
	private JButton registerBTN = null;
	private JButton logOutBTN = null;
	private JButton profileBTN = null;
	private JButton createADMBTN = null;

	// drag and drop xx, yy

	private int xx, xy;

	// Standard height and width

	private int frameHeight = 432;
	private int frameWidth = 768;

	// Gets the screen resolution

	private Toolkit tk = Toolkit.getDefaultToolkit();
	private Dimension d = tk.getScreenSize();
	
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// Janela principal

		frame = new JFrame();

		frame.getContentPane().setBackground(new Color(0, 128, 128));
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(0, 128, 128));
		frame.setBounds((int) (d.getWidth() / 2 - frameWidth / 2), (int) (d.getHeight() / 2 - frameHeight / 2),
				frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				xx = e.getX();
				xy = e.getY();
			}
		});
		frame.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
				int y = arg0.getYOnScreen();
				frame.setLocation(x - xx, y - xy);

			}
		});

		ProfileWindow profileWindow = new ProfileWindow();
		profileWindow.setLocation(64, 36);
		frame.getContentPane().add(profileWindow);

		JInternalFrame loginWindow = new LoginWindow(MainWindow.this);
		loginWindow.setLocation(259, 78);
		frame.getContentPane().add(loginWindow);

		JInternalFrame registerUserWindow = new RegisterUserWindow(MainWindow.this);
		registerUserWindow.setLocation(259, 78);
		frame.getContentPane().add(registerUserWindow);

		JInternalFrame registerAdminWindow = new RegisterAdminWindow(MainWindow.this);
		registerAdminWindow.setLocation(259, 78);
		frame.getContentPane().add(registerAdminWindow);

		// Label de cumprimento ao usuário

		greetingLabel = new JLabel("");
		greetingLabel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		greetingLabel.setBounds(140, 386, 415, 34);
		greetingLabel.setForeground(new Color(255, 255, 255));
		greetingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		greetingLabel.setBackground(new Color(51, 51, 51));
		frame.getContentPane().add(greetingLabel);

		// Logo com a Betina

		JLabel logoLabel = new JLabel("");
		logoLabel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		logoLabel.setBounds(140, 67, 415, 313);
		logoLabel.setBackground(new Color(102, 0, 0));
		logoLabel.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/logoTeste.png")));
		frame.getContentPane().add(logoLabel);

		// Painel em branco

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(10, 33, 124, 389);
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel);

		// Botão de sair

		JButton exitBTN = new JButton("SAIR");
		exitBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		exitBTN.setBounds(12, 355, 98, 26);
		exitBTN.setContentAreaFilled(false);
		exitBTN.setMnemonic('S');
		exitBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		exitBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.setLayout(null);
		panel.add(exitBTN);

		// Label com o titulo

		JLabel titleLabel = new JLabel("BET BETINA");
		titleLabel.setBounds(2, 0, 120, 33);
		titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		panel.add(titleLabel);

		// Botão de apostar

		JButton betBTN = new JButton("Apostar");
		betBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		betBTN.setBounds(12, 45, 98, 26);
		betBTN.setContentAreaFilled(false);
		betBTN.setMnemonic('A');
		betBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		panel.add(betBTN);
		betBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentUser == null) {

					JOptionPane.showMessageDialog(frame, "Você precisa estar logado para apostar!");

				}
			}
		});

		// Botão de ver jogos

		JButton matchBTN = new JButton("Jogos");
		matchBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		matchBTN.setBounds(12, 77, 98, 26);
		matchBTN.setContentAreaFilled(false);
		matchBTN.setMnemonic('V');
		matchBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		matchBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// frame.dispose();
			}
		});
		panel.add(matchBTN);

		// Linha preta meramente estétitca

		JPanel blackLine = new JPanel();
		blackLine.setBounds(1, 36, 119, 3);
		panel.add(blackLine);
		blackLine.setForeground(new Color(0, 0, 0));
		blackLine.setBackground(new Color(0, 0, 0));

		// Botão de times

		JButton btnNewButton = new JButton("Times");
		btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnNewButton.setBounds(12, 112, 98, 26);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		panel.add(btnNewButton);

		// painelo

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBounds(564, 33, 194, 387);
		panel_1.setBackground(Color.WHITE);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		// Destaques (falta programar)

		JLabel titleLabel_1 = new JLabel("Destaques");
		titleLabel_1.setBounds(53, 0, 87, 33);
		titleLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		panel_1.add(titleLabel_1);

		// Linha preta meramente estétitca 2

		JPanel blackLine_1 = new JPanel();
		blackLine_1.setBounds(6, 36, 181, 3);
		blackLine_1.setForeground(Color.BLACK);
		blackLine_1.setBackground(Color.BLACK);
		panel_1.add(blackLine_1);

		// Destaque falso, temporário

		JLabel lblNewLabel_2 = new JLabel("Flamengo é derrotado");
		lblNewLabel_2.setBounds(11, 55, 101, 16);
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD, 8));
		panel_1.add(lblNewLabel_2);

		// Linha preta meramente estétitca 3

		JPanel blackLine_2 = new JPanel();
		blackLine_2.setBounds(0, 0, 768, 25);
		blackLine_2.setForeground(Color.BLACK);
		blackLine_2.setBackground(Color.BLACK);
		frame.getContentPane().add(blackLine_2);
		blackLine_2.setLayout(null);

		// "Título" do app

		JLabel titleLBL = new JLabel("Bet-Betina v1.21 - Home");
		titleLBL.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		titleLBL.setForeground(new Color(255, 255, 255));
		titleLBL.setBounds(10, 4, 185, 16);
		blackLine_2.add(titleLBL);

		// "Botão" de fechar

		JLabel closeBTN = new JLabel("X");
		closeBTN.setForeground(new Color(255, 255, 255));
		closeBTN.setBounds(744, 0, 12, 23);
		closeBTN.setFont(new Font("Tahoma", Font.PLAIN, 20));
		closeBTN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		blackLine_2.add(closeBTN);

		// Botão de log in
		
		loginBTN = new JButton("Fazer Log In");
		loginBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		loginBTN.setForeground(new Color(255, 255, 255));
		loginBTN.setBounds(140, 33, 109, 26);
		loginBTN.setContentAreaFilled(false);
		loginBTN.setFocusPainted(false);
		loginBTN.setMnemonic('L');
		loginBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		frame.getContentPane().add(loginBTN);
		loginBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (registerUserWindow.isVisible()) {
					JOptionPane.showMessageDialog(frame, "Primeiro feche a outra janela!");
					return;
				}
				loginWindow.setVisible(true);
			}
		});

		// Botão de registro

		registerBTN = new JButton("Registrar");
		registerBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		registerBTN.setForeground(new Color(255, 255, 255));
		registerBTN.setBounds(434, 33, 121, 26);
		registerBTN.setContentAreaFilled(false);
		registerBTN.setMnemonic('R');
		registerBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		frame.getContentPane().add(registerBTN);
		registerBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginWindow.isVisible()) {
					JOptionPane.showMessageDialog(frame, "Primeiro feche a outra janela!");
					return;
				}
				registerUserWindow.setVisible(true);
			}
		});

		// Botão de perfil

		profileBTN = new JButton("Meu Perfil");
		profileBTN.setBounds(140, 33, 109, 26);
		profileBTN.setForeground(new Color(255, 255, 255));
		profileBTN.setContentAreaFilled(false);
		profileBTN.setMnemonic('p');
		profileBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		profileBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		frame.getContentPane().add(profileBTN);
		profileBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profileWindow.turnOn(MainWindow.this);
				profileWindow.setVisible(true);
			}
		});

		// Botão de log out
		logOutBTN =  new JButton("Fazer Log Out");
		logOutBTN.setBounds(434, 33, 121, 26);
		logOutBTN.setForeground(new Color(255, 255, 255));
		logOutBTN.setContentAreaFilled(false);
		logOutBTN.setMnemonic('O');
		logOutBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		logOutBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		frame.getContentPane().add(logOutBTN);
		logOutBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentUser = null;
				if(profileWindow.isVisible()) {
					profileWindow.dispose();
				}
				updateButtons();
				updateStatusLabel();
				JOptionPane.showMessageDialog(frame, "Log Out realizado com sucesso.");
			}
		});

		// Botão de criar admnistradores
		
		createADMBTN = new JButton("Criar Novo ADM");
		createADMBTN.setForeground(new Color(255, 255, 255));
		createADMBTN.setContentAreaFilled(false);
		createADMBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		createADMBTN.setBounds(261, 33, 161, 26);
		createADMBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		frame.getContentPane().add(createADMBTN);
		createADMBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginWindow.isVisible()) {
					JOptionPane.showMessageDialog(frame, "Primeiro feche a outra janela!");
					return;
				}
				registerAdminWindow.setVisible(true);
			}
		});

		updateButtons();
		updateStatusLabel();

	}

	// Limite de existência das variáveis

	public void updateUser(User user) {
		currentUser = user;
		updateStatusLabel();
	}

	// Remove os botões de login e registro se o usuário já estiver logado

	public void updateButtons() {
		if (currentUser != null) {

			loginBTN.setVisible(false);
			registerBTN.setVisible(false);

			logOutBTN.setVisible(true);
			profileBTN.setVisible(true);

			if (currentUser.getAccessLevel() == 1) {
				createADMBTN.setVisible(true);
			}

		} else {

			if (logOutBTN.isVisible()) {
				logOutBTN.setVisible(false);
				profileBTN.setVisible(false);
				createADMBTN.setVisible(false);
			}

			loginBTN.setVisible(true);
			registerBTN.setVisible(true);

		}
	}

	public void updateStatusLabel() {
		if (currentUser == null) {
			greetingLabel.setText("Seja Bem Vindo (a), visitante!");
		} else if (currentUser.getAccessLevel() == 1) {
			greetingLabel.setText("Bem Vindo (a) de volta, administrador (a)!");
		} else {
			greetingLabel.setText("Bem Vindo (a) de volta, " + currentUser.getName() + "!");
		}

	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
