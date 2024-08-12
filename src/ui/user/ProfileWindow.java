package ui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
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
import exceptions.PasswordsDontMatchException;
import model.User;
import util.InputManipulation;

import javax.swing.JTextField;
import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;

public class ProfileWindow {

	JFrame frame;
	private MainWindow mainWindow;
	private User currentUser = null;
	private UserDaoPostgres dao = new UserDaoPostgres();
	private JTextField nameField;
	private JTextField emailField;
	private JPasswordField changePasswordField;
	private JPasswordField confirmPasswordField;
	private JButton editDataBTN = new JButton("");
	private JButton editPasswordBTN = new JButton("");
	private JButton statsBTN = new JButton("Ver Estatísticas");
	private JButton surroundStatsBTN = new JButton("Ocultar Estatísticas");
	private int xx, xy;
	
//  Standard height and width
    int frameHeight = 360;
    int frameWidth = 640;
    
 // Gets the screen resolution
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();
    
	
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
				this.currentUser =  dao.findUserByEmail("rafael.rafael@rafael.rafael");
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
		
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setBounds((int) (d.getWidth() / 2 - frameWidth / 2), (int) (d.getHeight() / 2 - frameHeight / 2), frameWidth, frameHeight);
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
		
		JPanel blackLine_2 = new JPanel();
		blackLine_2.setBounds(0, 0, 640, 23);
		blackLine_2.setLayout(null);
		blackLine_2.setForeground(Color.BLACK);
		blackLine_2.setBackground(Color.BLACK);
		frame.getContentPane().add(blackLine_2);
  
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
        
        
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mainPanel.setBounds(214, 41, 212, 273);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
				
		// inicio da felete label
		
		
		JInternalFrame confirmDeletePanel = new JInternalFrame("Confirmação de Exclusão");
		confirmDeletePanel.setBounds(2, 0, 212, 274);
		mainPanel.add(confirmDeletePanel);
		confirmDeletePanel.setClosable(true);
		confirmDeletePanel.getContentPane().setLayout(null);
		
		JButton confirmDeleteBTN = new JButton("Sim");
		confirmDeleteBTN.setContentAreaFilled(false);
		confirmDeleteBTN.setBounds(8, 196, 90, 23);
		confirmDeletePanel.getContentPane().add(confirmDeleteBTN);
		confirmDeleteBTN.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				dao.deletUser(currentUser);
				mainWindow.updateUser(null);
				mainWindow.updateButtons();
				mainWindow.updateStatusLabel();
				JOptionPane.showMessageDialog(frame,"Usuário deletado com sucesso. Fechando janela.");
				mainWindow.getFrame().setEnabled(true);
				mainWindow.getFrame().toFront();
				frame.dispose();
					
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
		});
		
		
		JButton cancelDeleteBTN = new JButton("Esquece");
		cancelDeleteBTN.setContentAreaFilled(false);
		cancelDeleteBTN.setBounds(102, 196, 90, 23);
		confirmDeletePanel.getContentPane().add(cancelDeleteBTN);
		cancelDeleteBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setEnabled(true);
				confirmDeletePanel.dispose();
				}
		});
		
				
				
		JLabel profilePictureLabel_1 = new JLabel("");
		profilePictureLabel_1.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/user.png")));
		profilePictureLabel_1.setBounds(73, 11, 50, 50);
		confirmDeletePanel.getContentPane().add(profilePictureLabel_1);
		
		JPanel blackLine_1_1 = new JPanel();
		blackLine_1_1.setForeground(Color.BLACK);
		blackLine_1_1.setBackground(Color.BLACK);
		blackLine_1_1.setBounds(37, 72, 119, 3);
		confirmDeletePanel.getContentPane().add(blackLine_1_1);
		
		JLabel lblNewLabel = new JLabel("<html>Deseja realmente apagar para \r\n<br> sempre o ".concat(+ currentUser.getAccessLevel() == 2 ? "usuario": "admnistrador")  +"<strong> " + currentUser.getName() + "</strong>?\r\n<br>(Para sempre é um tempão!)</html>");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setBounds(8, 86, 178, 99);
		confirmDeletePanel.getContentPane().add(lblNewLabel);
		
		JLabel nameLabel = new JLabel(currentUser.getName().concat(currentUser.getAccessLevel() == 2 ? " - Usuario Comum.": " - Admnistrador."));
		nameLabel.setBounds(12, 77, 202, 14);
		mainPanel.add(nameLabel);
		nameLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));		
		
		JButton saveBTN = new JButton("Salvar Alterações");
		saveBTN.setContentAreaFilled(false);
		saveBTN.setBounds(10, 326, 194, 23);
		saveBTN.setEnabled(false);
		frame.getContentPane().add(saveBTN);
		saveBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String email = emailField.getText();
				String name = nameField.getText();
				String encriptedPassword = null;
				String passwordField1 = String.valueOf(changePasswordField.getPassword());
				String passwordField2 = String.valueOf(confirmPasswordField.getPassword());
				
				if(nameField.isEnabled() && emailField.isEnabled() ) {
					
					// Update name
					
					if(!name.equals(currentUser.getName())) {
						try {
							dao.updateUserName(currentUser, name);
							currentUser.setName(name);
							titleLabel.setText("bet-betina v1.21 - Perfil de " + currentUser.getName());
							nameLabel.setText(currentUser.getName().concat(currentUser.getAccessLevel() == 2 ? " - Usuario Comum.": " - Admnistrador."));
							JOptionPane.showMessageDialog(frame,"Nome atualizado com sucesso.");
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(frame,"Erro ao atualizar nome.");
						}
					}
					
					// Update email
					
					if(!email.equals(currentUser.getEmail())) {
						try {
							dao.updateUserName(currentUser, email);
							currentUser.setEmail(email);
							JOptionPane.showMessageDialog(frame,"Email atualizado com sucesso.");
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(frame,"Erro ao atualizar email.");
						}
					}
				}
				
				// Update password
				
				if(changePasswordField.isEnabled() && confirmPasswordField.isEnabled()) {
					
					if(passwordField1.length() > 0 && passwordField2.length() > 0){
						try {
							try {
								encriptedPassword = InputManipulation.generateHashedPassword(InputManipulation.joinPasswords(passwordField1, passwordField2));
								dao.updateUserPassword(currentUser, encriptedPassword);
								JOptionPane.showMessageDialog(frame,"Senha atualizada com sucesso.");
							} catch (SQLException e1) {
								JOptionPane.showMessageDialog(frame,"Não foi possível atualizar sua senha.");
								e1.printStackTrace();
							}
						} catch (PasswordsDontMatchException e1) {
							JOptionPane.showMessageDialog(frame,"As senhas não conferem!");
							return;
						}
					}	
				}
				
				// Comentário
				
				changePasswordField.setEnabled(false);
				confirmPasswordField.setEnabled(false);
				emailField.setEnabled(false);
				nameField.setEnabled(false);
				saveBTN.setEnabled(false);
				
			}
		});

	

		
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		dataPanel.setBounds(10, 41, 194, 274);
		dataPanel.setBackground(new Color(0, 128, 128));
		frame.getContentPane().add(dataPanel);
		dataPanel.setLayout(null);
		
		JLabel lblNewLabel_2_1 = new JLabel("DADOS");
		lblNewLabel_2_1.setBounds(61, 11, 72, 28);
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		dataPanel.add(lblNewLabel_2_1);
		
		nameField = new JTextField();
		nameField.setEnabled(false);
		nameField.setBounds(11, 87, 173, 20);
		dataPanel.add(nameField);
		nameField.setText(currentUser.getName());
		nameField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("NOME");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(11, 74, 46, 14);
		dataPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("EMAIL");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3_1.setBounds(11, 118, 46, 14);
		dataPanel.add(lblNewLabel_3_1);
		
		emailField = new JTextField();
		emailField.setEnabled(false);
		emailField.setColumns(10);
		emailField.setBounds(11, 131, 173, 20);
		emailField.setText(currentUser.getEmail());
		dataPanel.add(emailField);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("NOVA SENHA");
		lblNewLabel_3_1_1.setForeground(Color.WHITE);
		lblNewLabel_3_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3_1_1.setBounds(11, 184, 99, 14);
		dataPanel.add(lblNewLabel_3_1_1);
		
		JLabel idLabel = new JLabel("ID:" + currentUser.getID());
		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		idLabel.setBounds(11, 50, 46, 14);
		dataPanel.add(idLabel);

		editDataBTN.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/editBTN.png")));
		editDataBTN.setBounds(164, 50, 20, 20);
		dataPanel.add(editDataBTN);
		editDataBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailField.setEnabled(true);
				nameField.setEnabled(true);
				saveBTN.setEnabled(true);
			}
		});
		
		changePasswordField = new JPasswordField();
		changePasswordField.setEnabled(false);
		changePasswordField.setColumns(10);
		changePasswordField.setBounds(11, 200, 173, 20);
		dataPanel.add(changePasswordField);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setEnabled(false);
		confirmPasswordField.setText((String) null);
		confirmPasswordField.setColumns(10);
		confirmPasswordField.setBounds(11, 243, 173, 20);
		dataPanel.add(confirmPasswordField);
		
		JLabel lblNewLabel_3_1_1_1 = new JLabel("CONFIRMAR NOVA SENHA");
		lblNewLabel_3_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_3_1_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3_1_1_1.setBounds(11, 229, 173, 14);
		dataPanel.add(lblNewLabel_3_1_1_1);
		
		editPasswordBTN.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/editBTN.png")));
		editPasswordBTN.setBounds(164, 162, 20, 20);
		dataPanel.add(editPasswordBTN);
		editPasswordBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePasswordField.setEnabled(true);
				confirmPasswordField.setEnabled(true);
				saveBTN.setEnabled(true);
			}
		});
		
		JLabel lblNewLabel_2_1_2 = new JLabel("SENHA");
		lblNewLabel_2_1_2.setForeground(Color.WHITE);
		lblNewLabel_2_1_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblNewLabel_2_1_2.setBounds(61, 152, 72, 28);
		dataPanel.add(lblNewLabel_2_1_2);
		
		JButton btnNewButton_1 = new JButton("Meu Histórico");
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(434, 326, 202, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JPanel balancePanel = new JPanel();
		balancePanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		balancePanel.setLayout(null);
		balancePanel.setBackground(new Color(0, 128, 128));
		balancePanel.setBounds(436, 41, 194, 274);
		frame.getContentPane().add(balancePanel);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("EXTRATO");
		lblNewLabel_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_2_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblNewLabel_2_1_1.setBounds(45, 11, 103, 28);
		balancePanel.add(lblNewLabel_2_1_1);
		
		JLabel tmpLabel = new JLabel("+ 15 BRL");
		tmpLabel.setForeground(new Color(255, 255, 255));
		tmpLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		tmpLabel.setBounds(12, 52, 212, 14);
		balancePanel.add(tmpLabel);
		
		JLabel lblBrl = new JLabel("- 20 BRL");
		lblBrl.setForeground(new Color(255, 255, 255));
		lblBrl.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblBrl.setBounds(12, 78, 212, 14);
		balancePanel.add(lblBrl);
		
		JLabel lblBrl_2 = new JLabel("- 70 BRL");
		lblBrl_2.setForeground(new Color(255, 255, 255));
		lblBrl_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblBrl_2.setBounds(12, 104, 212, 14);
		balancePanel.add(lblBrl_2);
		
		JButton deleteUserBTN = new JButton("Apagar Perfil");
		deleteUserBTN.setContentAreaFilled(false);
		deleteUserBTN.setBounds(214, 326, 212, 23);
		deleteUserBTN.setForeground(new Color(255, 0, 0));
		frame.getContentPane().add(deleteUserBTN);
    	deleteUserBTN.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	    	confirmDeletePanel.setVisible(true);
    	    	
    	    }
    	});

				
				// limite da felete label
		
		
		JLabel balanceLabel = new JLabel("Saldo Atual: R$ " + currentUser.getBalance() + ".");
		balanceLabel.setBounds(12, 103, 163, 14);
		mainPanel.add(balanceLabel);
		balanceLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		
		JLabel profilePictureLabel = new JLabel("");
		profilePictureLabel.setBounds(81, 12, 50, 50);
		mainPanel.add(profilePictureLabel);
		profilePictureLabel.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/user.png")));
		
		JPanel blackLine_1 = new JPanel();
		blackLine_1.setBounds(46, 66, 119, 3);
		mainPanel.add(blackLine_1);
		blackLine_1.setForeground(Color.BLACK);
		blackLine_1.setBackground(Color.BLACK);
		
		JLabel totalBetsLabel = new JLabel();
		totalBetsLabel.setBounds(12, 153, 163, 14);
		mainPanel.add(totalBetsLabel);
		totalBetsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		
		JLabel totalLosesLabel = new JLabel();
		totalLosesLabel.setBounds(12, 179, 163, 14);
		mainPanel.add(totalLosesLabel);
		totalLosesLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		
		JLabel totalWinsLabel = new JLabel();
		totalWinsLabel.setBounds(12, 205, 163, 14);
		mainPanel.add(totalWinsLabel);
		totalWinsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		
		JLabel victoryRateLabel = new JLabel();
		victoryRateLabel.setBounds(12, 231, 163, 14);
		mainPanel.add(victoryRateLabel);
		victoryRateLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		
		JLabel favoriteTeamLabel = new JLabel();
		favoriteTeamLabel.setBounds(12, 252, 163, 14);
		mainPanel.add(favoriteTeamLabel);
		favoriteTeamLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		statsBTN.setBounds(18, 126, 175, 23);
		mainPanel.add(statsBTN);
		statsBTN.setContentAreaFilled(false);
		statsBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						totalBetsLabel.setText("Total de Apostas: " + dao.getTotalBets(currentUser) + ".");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					totalLosesLabel.setText("Total de Derrotas:");
					totalWinsLabel.setText("Total de Vitórias: ");
					victoryRateLabel.setText("Taxa de Vitória:");
					mainPanel.remove(statsBTN);
					mainPanel.add(surroundStatsBTN);
					mainPanel.revalidate();
					mainPanel.repaint();
				}
			});
		
		surroundStatsBTN.setBounds(18, 126, 175, 23);
		surroundStatsBTN.setContentAreaFilled(false);
		surroundStatsBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalBetsLabel.setText("");
				totalLosesLabel.setText("");
				totalWinsLabel.setText("");
				victoryRateLabel.setText("");
				favoriteTeamLabel.setText("");
				mainPanel.remove(surroundStatsBTN);
				mainPanel.add(statsBTN);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
	}
}
