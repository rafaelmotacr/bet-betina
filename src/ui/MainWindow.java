package ui;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
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
import javax.swing.SwingConstants;

import model.User;

public class MainWindow {

	private JFrame frame;
    private JLabel greetingLabel;
    private User currentUser = null;
    private RegisterWindow registerWindow = null;
    private LoginWindow loginWindow = null;
    private ProfileWindow profileWindow = null;
    private JButton loginBTN = new JButton("Log In");
    private JButton registerBTN = new JButton("Registrar");
    private JButton logOutBTN = new JButton("Log Out");
    private JButton profileBTN = new JButton("Perfil");

    
	int xx,xy;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	MainWindow window = new MainWindow();
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
    public MainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
    	
    	// Janela principal
    	
        frame = new JFrame("Bet Betina 1.21");
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
        
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(0, 128, 128));
        frame.setBounds(100, 100, 640, 360);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        // Label de cumprimento ao usuário

        greetingLabel = new JLabel("");
        greetingLabel.setBounds(149, 314, 342, 34);
        greetingLabel.setForeground(new Color(255, 255, 255));
        greetingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        greetingLabel.setBackground(new Color(238, 238, 238));
        frame.getContentPane().add(greetingLabel);
        	

        // Logo com a Betina
        
        JLabel logoLabel = new JLabel("");
        logoLabel.setBounds(149, 64, 342, 254);
        logoLabel.setBackground(new Color(102, 0, 0));
        logoLabel.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/mainBg-1.png")));
        frame.getContentPane().add(logoLabel);

        // Painel em branco
        
        Panel panel = new Panel();
        panel.setBounds(10, 29, 124, 323);
        panel.setBackground(Color.WHITE);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        
        // Botão de sair
        
        JButton exitBTN = new JButton("SAIR");
        exitBTN.setContentAreaFilled(false);
        exitBTN.setMnemonic('S');
        exitBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        exitBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        exitBTN.setBounds(12, 285, 98, 26);
        panel.add(exitBTN);

        // Label com o titulo
        
        JLabel titleLabel = new JLabel("BET BETINA");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        titleLabel.setBounds(1, 0, 140, 33);
        panel.add(titleLabel);

        
        // Botão de apostar
        
        JButton betBTN = new JButton("Apostar");
        betBTN.setContentAreaFilled(false);
        betBTN.setMnemonic('A');
        betBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        betBTN.setBounds(12, 45, 98, 26);
        panel.add(betBTN);
        betBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(currentUser == null) {
            	   
            	   JOptionPane.showMessageDialog(frame, "Você precisa estar logado para apostar!");
            	   
               }
            }
        });
        
        
        // Botão de ver jogos

        JButton matchBTN = new JButton("Jogos");
        matchBTN.setContentAreaFilled(false);
        matchBTN.setMnemonic('V');
        matchBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        matchBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // frame.dispose();
            }
        });
        matchBTN.setBounds(12, 77, 98, 26);
        panel.add(matchBTN);
        
        
        // Linha preta meramente estétitca
        
        JPanel blackLine = new JPanel();
        blackLine.setBounds(1, 36, 119, 3);
        panel.add(blackLine);
        blackLine.setForeground(new Color(0, 0, 0));
        blackLine.setBackground(new Color(0, 0, 0));
        
        JButton btnNewButton = new JButton("Times");
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        btnNewButton.setBounds(12, 112, 98, 26);
        panel.add(btnNewButton);
        
        Panel panel_1 = new Panel();
        panel_1.setBounds(506, 29, 124, 322);
        panel_1.setLayout(null);
        panel_1.setBackground(Color.WHITE);
        frame.getContentPane().add(panel_1);
        
        JLabel titleLabel_1 = new JLabel("Destaques");
        titleLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        titleLabel_1.setBounds(1, 0, 140, 33);
        panel_1.add(titleLabel_1);
        
        JPanel blackLine_1 = new JPanel();
        blackLine_1.setForeground(Color.BLACK);
        blackLine_1.setBackground(Color.BLACK);
        blackLine_1.setBounds(1, 36, 119, 3);
        panel_1.add(blackLine_1);
        
        JLabel lblNewLabel_2 = new JLabel("Flamengo é derrotado");
        lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD, 8));
        lblNewLabel_2.setBounds(11, 55, 101, 16);
        panel_1.add(lblNewLabel_2);
        
        JPanel blackLine_2 = new JPanel();
        blackLine_2.setBounds(0, 0, 640, 23);
        blackLine_2.setForeground(Color.BLACK);
        blackLine_2.setBackground(Color.BLACK);
        frame.getContentPane().add(blackLine_2);
        blackLine_2.setLayout(null);
        
        
        JLabel titleLBL = new JLabel("bet-betina v1.21 - Home");
        titleLBL.setForeground(new Color(255, 255, 255));
        titleLBL.setBounds(0, 2, 141, 16);
        titleLBL.setHorizontalAlignment(SwingConstants.LEFT);
        blackLine_2.add(titleLBL);
        
        JLabel closeBTN = new JLabel("X");
        closeBTN.setBounds(614, 3, 20, 16);
        blackLine_2.add(closeBTN);
        closeBTN.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		System.exit(0);
        	}
        });
        closeBTN.setFont(new Font("Tahoma", Font.PLAIN, 16));
        closeBTN.setForeground(new Color(255, 255, 255));
        
        
        updateButtons();
        updateStatusLabel();
    }
    
    // Limite de existência das variáveis

    public void updateUser(User user) {
        this.currentUser = user;
        updateStatusLabel();
    }
    
 
    public void updateButtons() {
        if (currentUser != null) {
        	
        	// Remove os botões de login e registro se o usuário já estiver logado
        	
        	frame.remove(loginBTN);
        	frame.remove(registerBTN);
        	
        	// Botão de log out

            logOutBTN.setMnemonic('O');
            logOutBTN.setBounds(393, 29, 98, 26);
            logOutBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            frame.getContentPane().add(logOutBTN);
            logOutBTN.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	currentUser = null;
                	JOptionPane.showMessageDialog(frame, "Log Out realizado com sucesso.");
                	frame.remove(logOutBTN);
                	frame.remove(profileBTN);
                    updateButtons();
                    updateStatusLabel();
                }
            });

            
            // Botão de perfil
            
            profileBTN.setMnemonic('p');
            profileBTN.setBounds(149, 29, 98, 26); 
            profileBTN.addActionListener(new ActionListener() {
	    	    public void actionPerformed(ActionEvent e) {
	    	    	if(profileWindow == null) {
		    	    	profileWindow = new ProfileWindow(MainWindow.this);
		    	    	profileWindow.frame.setVisible(true);
	    	    	}
	    	    }
	    	});
            profileBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
	    	frame.getContentPane().add(profileBTN);

        }
        else {
        	
        	// Botão de log in
        	
	    	loginBTN.setMnemonic('L');
	        loginBTN.setBounds(149, 29, 98, 26);
	        loginBTN.addActionListener(new ActionListener() {
	              public void actionPerformed(ActionEvent e) {
	            	  if(loginWindow == null) {
		                  loginWindow = new LoginWindow(MainWindow.this);
		                  loginWindow.frame.setVisible(true);
	            	  }
	              }
	          });
	        loginBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
	        frame.getContentPane().add(loginBTN);
              
	        // Botão de registro
        	
            registerBTN.setMnemonic('R');
            registerBTN.setBounds(393, 29, 98, 26);
            registerBTN.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	if(registerWindow == null)
	                	registerWindow = new RegisterWindow(MainWindow.this);
	                	registerWindow.frame.setVisible(true);
                }
            });
            registerBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            frame.getContentPane().add(registerBTN);	
        }
        
        frame.revalidate();
        frame.repaint();
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

	public RegisterWindow getRegisterWindow() {
		return registerWindow;
	}

	public void setRegisterWindow(RegisterWindow registerWindow) {
		this.registerWindow = registerWindow;
	}

	public LoginWindow getLoginWindow() {
		return loginWindow;
	}

	public void setLoginWindow(LoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}

	public ProfileWindow getProfileWindow() {
		return profileWindow;
	}

	public void setProfileWindow(ProfileWindow profileWindow) {
		this.profileWindow = profileWindow;
	}
	
}
