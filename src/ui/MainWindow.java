package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.UserDaoPostgres;
import model.User;

public class MainWindow {

	private JFrame frame;
    private JLabel greetingLabel;
    private User currentUser = null;
    private JButton loginBTN = new JButton("Log In");
    private JButton registerBTN = new JButton("Registrar");
    private JButton logOutBTN = new JButton("Log Out");
    private JButton deleteUserBTN = new JButton("Delete Account");
	private UserDaoPostgres dao = new UserDaoPostgres();
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
        frame.getContentPane().setBackground(new Color(153, 0, 0));
        frame.setBounds(100, 100, 541, 362);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        // Label de cumprimento ao usuário

        greetingLabel = new JLabel("");
        greetingLabel.setBounds(149, 277, 364, 34);
        greetingLabel.setForeground(new Color(255, 255, 255));
        greetingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        greetingLabel.setBackground(new Color(238, 238, 238));
        frame.getContentPane().add(greetingLabel);
        	

        // Logo com a Betina
        
        JLabel logoLabel = new JLabel("");
        logoLabel.setBounds(149, 45, 364, 233);
        logoLabel.setBackground(new Color(102, 0, 0));
        logoLabel.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/mainBg.png")));
        frame.getContentPane().add(logoLabel);

        // Painel em branco
        
        Panel panel = new Panel();
        panel.setBounds(10, 10, 124, 303);
        panel.setBackground(Color.WHITE);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        
        // Botão de sair
        
        JButton exitBTN = new JButton("SAIR");
        exitBTN.setMnemonic('S');
        exitBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        exitBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        exitBTN.setBounds(12, 265, 98, 26);
        panel.add(exitBTN);

        // Label com o titulo
        
        JLabel titleLabel = new JLabel("BET BETINA");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        titleLabel.setBounds(1, 0, 140, 33);
        panel.add(titleLabel);

        
        // Botão de apostar
        
        JButton betBTN = new JButton("Apostar");
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
        matchBTN.setMnemonic('V');
        matchBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        matchBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // frame.dispose();
            }
        });
        matchBTN.setBounds(12, 121, 98, 26);
        panel.add(matchBTN);
        
        // Botão de histórico
        
        JButton historyBTN = new JButton("Histórico");
        historyBTN.setMnemonic('h');
        historyBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        historyBTN.setBounds(12, 83, 98, 26);
        panel.add(historyBTN);
        historyBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(currentUser == null) {
            	   
            	   JOptionPane.showMessageDialog(frame, "Você precisa estar logado para acessar seu histórico!");
            	   
               }
            }
        });
        
        
        // Linha preta meramente estétitca
        
        JPanel blackLine = new JPanel();
        blackLine.setBounds(1, 36, 119, 3);
        panel.add(blackLine);
        blackLine.setForeground(new Color(0, 0, 0));
        blackLine.setBackground(new Color(0, 0, 0));
        
        JButton btnNewButton = new JButton("Times");
        btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        btnNewButton.setBounds(12, 160, 98, 26);
        panel.add(btnNewButton);
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
            logOutBTN.setBounds(415, 10, 98, 26);
            logOutBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            frame.getContentPane().add(logOutBTN);
            logOutBTN.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	currentUser = null;
                	JOptionPane.showMessageDialog(frame, "Log Out realizado com sucesso.");
                	frame.remove(logOutBTN);
                	frame.remove(deleteUserBTN);
                    updateButtons();
                    updateStatusLabel();
                }
            });

            
            // Botão de deletar usuário
            
	    	deleteUserBTN.setMnemonic('d');
	    	deleteUserBTN.setBounds(149, 10, 150, 26); 
	    	deleteUserBTN.addActionListener(new ActionListener() {
	    	    public void actionPerformed(ActionEvent e) {
	    	    	try {
						dao.deletUser(currentUser);
						currentUser = null;
						JOptionPane.showMessageDialog(frame,"Usuário deletado com sucesso. Mas ainda temos os seus dados.");
	                	frame.remove(logOutBTN);
	                	frame.remove(deleteUserBTN);
	                    updateButtons();
	                    updateStatusLabel();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    	    }
	    	});
	    	deleteUserBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
	    	frame.getContentPane().add(deleteUserBTN);

        }
        else {
        	// Botão de log in
        	
	    	loginBTN.setMnemonic('L');
	        loginBTN.setBounds(149, 10, 98, 26);
	        loginBTN.addActionListener(new ActionListener() {
	              public void actionPerformed(ActionEvent e) {
	                  LoginWindow loginDialog = new LoginWindow(MainWindow.this);
	                  loginDialog.frame.setVisible(true);
	              }
	          });
	        loginBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
	        frame.getContentPane().add(loginBTN);
              
	        // Botão de registro
        	
            registerBTN.setMnemonic('R');
            registerBTN.setBounds(415, 10, 98, 26);
            registerBTN.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RegisterWindow registerDialog = new RegisterWindow(MainWindow.this);
                    registerDialog.frame.setVisible(true);
                }
            });
            registerBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            frame.getContentPane().add(registerBTN);	
        }
        
        frame.revalidate();
        frame.repaint();
    }

    private void updateStatusLabel() {
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
}
