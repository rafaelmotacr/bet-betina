package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.UserDaoPostgres;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Font;

public class LoginWindow {

	JFrame frame;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton loginBTN;
	private UserDaoPostgres dao = new UserDaoPostgres();
	private MainWindow mainWindow;
	int xx,xy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
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
	public LoginWindow() {
        initialize();
    }
	
	public LoginWindow(MainWindow mainWindowTMP) {
		mainWindow = mainWindowTMP;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setBounds(100, 100, 250, 266);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
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
		
        JPanel blackLine_2 = new JPanel();
        blackLine_2.setBounds(0, 0, 250, 23);
        blackLine_2.setForeground(Color.BLACK);
        blackLine_2.setBackground(Color.BLACK);
        frame.getContentPane().add(blackLine_2);
        blackLine_2.setLayout(null);
        
        JLabel closeBTN = new JLabel("X");
        closeBTN.addMouseMotionListener(new MouseMotionAdapter() {
        	@Override
        	public void mouseMoved(MouseEvent e) {
        		closeBTN.setForeground(new Color(255, 0, 0));
        		
        	}
        });
        closeBTN.setFont(new Font("Tahoma", Font.PLAIN, 20));
        closeBTN.setForeground(new Color(255, 255, 255));
        closeBTN.setBounds(224, 2, 14, 21);
        blackLine_2.add(closeBTN);
        closeBTN.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		frame.dispose();
        		mainWindow.setLoginWindow(null);
        	}
        });
        
        JLabel titleLBL = new JLabel("bet-betina v1.21 - Login");
        titleLBL.setForeground(new Color(255, 255, 255));
        titleLBL.setBounds(0, 5, 141, 16);
        titleLBL.setHorizontalAlignment(SwingConstants.LEFT);
        blackLine_2.add(titleLBL);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(25, 154, 203, 20);
		frame.getContentPane().add(passwordField);
		
		emailField = new JTextField();
		emailField.setBounds(25, 111, 203, 20);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		loginBTN = new JButton("");
		loginBTN.setBounds(63, 195, 120, 29);
		frame.getContentPane().add(loginBTN);
		loginBTN.setOpaque(false);
		loginBTN.setContentAreaFilled(false);
		loginBTN.setBorderPainted(false);
		loginBTN.setFocusPainted(false);
		loginBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String email = emailField.getText();
				@SuppressWarnings("deprecation")
				String senha = String.valueOf(passwordField.getText());
				
				try {
					if(dao.login(email, senha)) {
						
					    JOptionPane.showMessageDialog(frame, "Login realizado com sucesso");
					    mainWindow.updateUser(dao.findUserByLoguin(email, senha));
					    mainWindow.updateButtons();
					    frame.dispose();
					    mainWindow.setLoginWindow(null);
					} else {
						JOptionPane.showMessageDialog(frame, "Login ou senha incorretos");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(LoginWindow.class.getResource("/resources/loginBG.png")));
		lblNewLabel.setBounds(0, 16, 250, 250);
		frame.getContentPane().add(lblNewLabel);
	}
}
