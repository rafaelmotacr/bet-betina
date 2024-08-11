package ui.user;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
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
import model.User;

import javax.swing.border.TitledBorder;

public class MainWindow {

	private JFrame frame;
    private JLabel greetingLabel;
    private User currentUser = null;
    private JButton loginBTN = new JButton("Log In");
    private JButton registerBTN = new JButton("Registrar");
    private JButton logOutBTN = new JButton("Log Out");
    private JButton profileBTN = new JButton("Perfil");
    private JButton createADMBTN = new JButton("Criar ADM");
    
    //  drag and drop xx, yy
    private int xx,xy;
    
    //  standard height and width
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
        frame.setBounds((int) (d.getWidth() / 2 - frameWidth / 2), (int) (d.getHeight() / 2 - frameHeight / 2), 
        				frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        // Label de cumprimento ao usuário

        greetingLabel = new JLabel("");
        greetingLabel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        greetingLabel.setBounds(149, 314, 342, 34);
        greetingLabel.setForeground(new Color(255, 255, 255));
        greetingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        greetingLabel.setBackground(new Color(51, 51, 51));
        frame.getContentPane().add(greetingLabel);
        	

        // Logo com a Betina
        
        JLabel logoLabel = new JLabel("");
        logoLabel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        logoLabel.setBounds(149, 64, 342, 242);
        logoLabel.setBackground(new Color(102, 0, 0));
        logoLabel.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/b2a7259e-1018-4031-aaba-4aa606a0e6a4.png")));
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
        
        // Botão de times
        
        JButton btnNewButton = new JButton("Times");
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        btnNewButton.setBounds(12, 112, 98, 26);
        panel.add(btnNewButton);
        
        // painelo
        
        Panel panel_1 = new Panel();
        panel_1.setBounds(506, 29, 124, 322);
        panel_1.setLayout(null);
        panel_1.setBackground(Color.WHITE);
        frame.getContentPane().add(panel_1);
        
        // Destaques (falta programar)
        
        JLabel titleLabel_1 = new JLabel("Destaques");
        titleLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        titleLabel_1.setBounds(1, 0, 140, 33);
        panel_1.add(titleLabel_1);
        
        // Linha preta meramente estétitca 2
        
        JPanel blackLine_1 = new JPanel();
        blackLine_1.setForeground(Color.BLACK);
        blackLine_1.setBackground(Color.BLACK);
        blackLine_1.setBounds(1, 36, 119, 3);
        panel_1.add(blackLine_1);
        
        // Destaque falso, temporário
        
        JLabel lblNewLabel_2 = new JLabel("Flamengo é derrotado");
        lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD, 8));
        lblNewLabel_2.setBounds(11, 55, 101, 16);
        panel_1.add(lblNewLabel_2);
        
        // Linha preta meramente estétitca 3
        
        JPanel blackLine_2 = new JPanel();
        blackLine_2.setBounds(0, 0, 640, 23);
        blackLine_2.setForeground(Color.BLACK);
        blackLine_2.setBackground(Color.BLACK);
        frame.getContentPane().add(blackLine_2);
        blackLine_2.setLayout(null);
        
        
        JLabel titleLBL = new JLabel("bet-betina v1.21 - Home");
        titleLBL.setForeground(new Color(255, 255, 255));
        titleLBL.setBounds(0, 2, 141, 16);
        blackLine_2.add(titleLBL);
        
        JLabel closeBTN = new JLabel("X");
        closeBTN.setForeground(new Color(255, 255, 255));
        closeBTN.setBounds(614, 3, 20, 16);
        blackLine_2.add(closeBTN);
        
        closeBTN.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		System.exit(0);
        	}
        });
        closeBTN.setFont(new Font("Tahoma", Font.PLAIN, 16));

    	frame.getContentPane().add(createADMBTN);
    	loginBTN.setForeground(new Color(255, 255, 255));
    	loginBTN.setBounds(149, 29, 98, 26);
    	frame.getContentPane().add(loginBTN);
    	loginBTN.setContentAreaFilled(false);
    	loginBTN.setFocusPainted(false);
    	
    	// Botão de log in
    	
    	loginBTN.setMnemonic('L');
    	loginBTN.addActionListener(new ActionListener() {
    	      public void actionPerformed(ActionEvent e) {
    	    	  frame.setEnabled(false);
	              LoginWindow loginWindow = new LoginWindow(MainWindow.this);
	              loginWindow.frame.setVisible(true);
    	      }
    	  });
    	loginBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
    	registerBTN.setForeground(new Color(255, 255, 255));
    	registerBTN.setBounds(393, 29, 98, 26);
    	frame.getContentPane().add(registerBTN);
    	registerBTN.setContentAreaFilled(false);
    	
        // Botão de registro
    	
        registerBTN.setMnemonic('R');
        registerBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
          
            	RegisterWindow registerWindow = new RegisterWindow(MainWindow.this);
                registerWindow.frame.setVisible(true);
                frame.setEnabled(false);   
                
            }
        });
        registerBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        profileBTN.setBounds(149, 29, 98, 26);
        frame.getContentPane().add(profileBTN);
        profileBTN.setForeground(new Color(255, 255, 255));
        profileBTN.setContentAreaFilled(false);
        
                
                // Botão de perfil
                
        profileBTN.setMnemonic('p');
            profileBTN.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

	    	ProfileWindow profileWindow = new ProfileWindow(MainWindow.this);
	    	profileWindow.frame.setVisible(true);
	    	frame.setEnabled(false);   
   
	    	}
            });
        profileBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        logOutBTN.setBounds(393, 29, 98, 26);
        frame.getContentPane().add(logOutBTN);
        logOutBTN.setForeground(new Color(255, 255, 255));
        logOutBTN.setContentAreaFilled(false);
        
        // Botão de log out
        
        logOutBTN.setMnemonic('O');
        logOutBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        logOutBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	currentUser = null;
            	JOptionPane.showMessageDialog(frame, "Log Out realizado com sucesso.");
                updateButtons();
                updateStatusLabel();
            }
        });
        
    	createADMBTN.setForeground(new Color(255, 255, 255));
    	createADMBTN.setContentAreaFilled(false);
    	createADMBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
    	createADMBTN.setBounds(271, 29, 98, 26);
        createADMBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
			
        	}
	    });
	        updateButtons();
	        updateStatusLabel();
	    }
    
    // Limite de existência das variáveis

    public void updateUser(User user) {
        this.currentUser = user;
        updateStatusLabel();
    }
    
 
 // Remove os botões de login e registro se o usuário já estiver logado
    
    public void updateButtons() {
        if (currentUser != null) {
        	
        	
        	loginBTN.setVisible(false);
        	registerBTN.setVisible(false);
        	
        	logOutBTN.setVisible(true);
        	profileBTN.setVisible(true);
        	
        	if(currentUser.getAccessLevel() == 1) {
        		createADMBTN.setVisible(true);
        	}

        }
        else {
        	
        	if(logOutBTN.isVisible()) {
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
