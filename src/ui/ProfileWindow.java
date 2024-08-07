package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import dao.UserDaoPostgres;
import model.User;
import javax.swing.JTextField;

public class ProfileWindow {

	JFrame frame;
	private MainWindow mainWindow;
	private UserDaoPostgres dao = new UserDaoPostgres();
	private User currentUser = null;
	private JTextField nameField;
	private JTextField emailField;
	private JTextField changePasswordField;
	private JPasswordField confirmPasswordField;
	private int xx,xy;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfileWindow window = new ProfileWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ProfileWindow() {
		if(this.mainWindow != null) {
			this.currentUser = mainWindow.getCurrentUser();
		}
		else {
			try {
				this.currentUser =  dao.findUserByLoguin("rafael.rafael@rafael.rafael", "rafael123");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		initialize();
	}
	
	public ProfileWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.currentUser = mainWindow.getCurrentUser();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		JButton statsBTN = new JButton("Ver Estatísticas");
		statsBTN.setContentAreaFilled(false);
		JButton surroundStatsBTN = new JButton("Ocultar Estatísticas");
		surroundStatsBTN.setContentAreaFilled(false);
		
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setBounds(100, 100, 640, 360);
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
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(295, 41, 50, 50);
		lblNewLabel.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/user.png")));
		frame.getContentPane().add(lblNewLabel);
		
		JButton deleteUserBTN = new JButton("Apagar Perfil");
		deleteUserBTN.setContentAreaFilled(false);
		deleteUserBTN.setBounds(436, 326, 194, 23);
		deleteUserBTN.setForeground(new Color(255, 0, 0));
		frame.getContentPane().add(deleteUserBTN);
    	deleteUserBTN.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	    	try {
					dao.deletUser(currentUser);
					currentUser = null;
					JOptionPane.showMessageDialog(frame,"Usuário deletado com sucesso. Fechando janela.");
                    mainWindow.updateButtons();
                    mainWindow.updateStatusLabel();
					frame.dispose();
					mainWindow.getFrame().setEnabled(true);
					mainWindow.getFrame().toFront();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    	    }
    	});
		
		
		
		JButton saveBTN = new JButton("Salvar");
		saveBTN.setContentAreaFilled(false);
		saveBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		saveBTN.setBounds(10, 326, 194, 23);
		saveBTN.setEnabled(false);
		frame.getContentPane().add(saveBTN);
		saveBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePasswordField.setEnabled(false);
				confirmPasswordField.setEnabled(false);
				emailField.setEnabled(false);
				nameField.setEnabled(false);
				saveBTN.setEnabled(false);
			}
		});

		
		JPanel panel = new JPanel();
		panel.setBounds(10, 41, 194, 274);
		panel.setBackground(new Color(0, 128, 128));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2_1 = new JLabel("DADOS");
		lblNewLabel_2_1.setBounds(41, 11, 72, 28);
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		panel.add(lblNewLabel_2_1);
		
		nameField = new JTextField();
		nameField.setEnabled(false);
		nameField.setBounds(11, 87, 173, 20);
		panel.add(nameField);
		nameField.setText(currentUser.getName());
		nameField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("NOME");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(11, 74, 46, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("EMAIL");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3_1.setBounds(11, 118, 46, 14);
		panel.add(lblNewLabel_3_1);
		
		emailField = new JTextField();
		emailField.setEnabled(false);
		emailField.setColumns(10);
		emailField.setBounds(11, 131, 173, 20);
		emailField.setText(currentUser.getEmail());
		panel.add(emailField);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("NOVA SENHA");
		lblNewLabel_3_1_1.setForeground(Color.WHITE);
		lblNewLabel_3_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3_1_1.setBounds(11, 184, 99, 14);
		panel.add(lblNewLabel_3_1_1);
		
		JLabel idLabel = new JLabel("ID:" + currentUser.getID());
		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		idLabel.setBounds(11, 50, 46, 14);
		panel.add(idLabel);
		
		changePasswordField = new JPasswordField();
		changePasswordField.setEnabled(false);
		changePasswordField.setColumns(10);
		changePasswordField.setBounds(11, 200, 173, 20);
		
		panel.add(changePasswordField);
		
		JButton editDataBTN = new JButton("");
		editDataBTN.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/editBTN.png")));
		editDataBTN.setBounds(164, 50, 20, 20);
		panel.add(editDataBTN);
		editDataBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailField.setEnabled(true);
				nameField.setEnabled(true);
				saveBTN.setEnabled(true);
			}
		});
		
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setEnabled(false);
		confirmPasswordField.setText((String) null);
		confirmPasswordField.setColumns(10);
		confirmPasswordField.setBounds(11, 243, 173, 20);
		panel.add(confirmPasswordField);
		
		JLabel lblNewLabel_3_1_1_1 = new JLabel("CONFIRMAR NOVA SENHA");
		lblNewLabel_3_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_3_1_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3_1_1_1.setBounds(11, 229, 173, 14);
		panel.add(lblNewLabel_3_1_1_1);
		
		JButton editPasswordBTN = new JButton("");
		editPasswordBTN.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/editBTN.png")));
		editPasswordBTN.setBounds(164, 162, 20, 20);
		panel.add(editPasswordBTN);
		editPasswordBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePasswordField.setEnabled(true);
				confirmPasswordField.setEnabled(true);
				saveBTN.setEnabled(true);
			}
		});

		JLabel nameLabel = new JLabel(currentUser.getName().concat(currentUser.getAccessLevel() == 2 ? " - Usuario Comum.": " - Admnistrador."));
		nameLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		nameLabel.setBounds(214, 117, 212, 14);
		frame.getContentPane().add(nameLabel);
		
		JPanel blackLine_2 = new JPanel();
		blackLine_2.setBounds(0, 0, 640, 23);
		blackLine_2.setLayout(null);
		blackLine_2.setForeground(Color.BLACK);
		blackLine_2.setBackground(Color.BLACK);
		frame.getContentPane().add(blackLine_2);
		
		/*
        Optional<String> userName = Optional.ofNullable(mainWindow.getCurrentUser().getName());
        String displayName = userName.orElse("Usuário desconhecido");
*/
  
        JLabel titleLabel = new JLabel("bet-betina v1.21 - Perfil de " + currentUser.getName());
        titleLabel.setForeground(new Color(255, 255, 255));
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleLabel.setBounds(0, 5, 606, 16);
		blackLine_2.add(titleLabel);
		
		JLabel closeBTN = new JLabel("X");
		closeBTN.setForeground(Color.WHITE);
		closeBTN.setFont(new Font("Tahoma", Font.PLAIN, 20));
		closeBTN.setBounds(616, 0, 14, 21);
		blackLine_2.add(closeBTN);
        closeBTN.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		frame.dispose();
        		mainWindow.getFrame().setEnabled(true);
        		mainWindow.getFrame().toFront();
        	}
        });
		
		JButton btnNewButton_1 = new JButton("Meu Histórico");
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(214, 326, 212, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel balanceLabel = new JLabel("Saldo Atual: R$ " + currentUser.getBalance() + ".");
		balanceLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		balanceLabel.setBounds(214, 142, 163, 14);
		frame.getContentPane().add(balanceLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(0, 128, 128));
		panel_1.setBounds(436, 41, 194, 274);
		frame.getContentPane().add(panel_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("EXTRATO");
		lblNewLabel_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_2_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblNewLabel_2_1_1.setBounds(41, 11, 143, 28);
		panel_1.add(lblNewLabel_2_1_1);
		
		JLabel tmpLabel = new JLabel("+ 15 BRL");
		tmpLabel.setForeground(new Color(255, 255, 255));
		tmpLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		tmpLabel.setBounds(12, 52, 212, 14);
		panel_1.add(tmpLabel);
		
		JLabel lblBrl = new JLabel("- 20 BRL");
		lblBrl.setForeground(new Color(255, 255, 255));
		lblBrl.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblBrl.setBounds(12, 78, 212, 14);
		panel_1.add(lblBrl);
		
		JLabel lblBrl_2 = new JLabel("- 70 BRL");
		lblBrl_2.setForeground(new Color(255, 255, 255));
		lblBrl_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblBrl_2.setBounds(12, 104, 212, 14);
		panel_1.add(lblBrl_2);
		
		JLabel totalBetsLabel = new JLabel();
		totalBetsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		totalBetsLabel.setBounds(214, 201, 163, 14);
		frame.getContentPane().add(totalBetsLabel);
		
		JLabel totalLosesLabel = new JLabel();
		totalLosesLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		totalLosesLabel.setBounds(214, 226, 163, 14);
		frame.getContentPane().add(totalLosesLabel);
		
		JLabel victoryRateLabel = new JLabel();
		victoryRateLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		victoryRateLabel.setBounds(214, 276, 163, 14);
		frame.getContentPane().add(victoryRateLabel);
		
		JLabel favoriteTeamLabel = new JLabel();
		favoriteTeamLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		favoriteTeamLabel.setBounds(214, 301, 163, 14);
		frame.getContentPane().add(favoriteTeamLabel);
		
		JLabel totalWinsLabel = new JLabel();
		totalWinsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		totalWinsLabel.setBounds(214, 251, 163, 14);
		frame.getContentPane().add(totalWinsLabel);
		
		JPanel blackLine_1 = new JPanel();
		blackLine_1.setForeground(Color.BLACK);
		blackLine_1.setBackground(Color.BLACK);
		blackLine_1.setBounds(258, 103, 119, 3);
		frame.getContentPane().add(blackLine_1);
		
		surroundStatsBTN.setBounds(214, 167, 212, 23);
		surroundStatsBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalBetsLabel.setText("");
				totalLosesLabel.setText("");
				totalWinsLabel.setText("");
				victoryRateLabel.setText("");
				favoriteTeamLabel.setText("");
				frame.remove(surroundStatsBTN);
				frame.getContentPane().add(statsBTN);
		        frame.revalidate();
		        frame.repaint();
			}
		});
		
		statsBTN.setBounds(214, 167, 212, 23);
		frame.getContentPane().add(statsBTN);
	
		statsBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					totalBetsLabel.setText("Total de Apostas: " + dao.getTotalApostas(currentUser) + ".");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				totalLosesLabel.setText("Total de Derrotas:");
				totalWinsLabel.setText("Total de Vitórias: ");
				victoryRateLabel.setText("Taxa de Vitória:");
				favoriteTeamLabel.setText("Time Favorito: ");
				
				frame.remove(statsBTN);
				frame.getContentPane().add(surroundStatsBTN);
		        frame.revalidate();
		        frame.repaint();
			}
		});
	}
}
