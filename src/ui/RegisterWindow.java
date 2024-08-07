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
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.UserDaoPostgres;

public class RegisterWindow {

	JFrame frame;
	private int minPasswordLength = 8;
	private int minNameLength = 4;
	private int minEmailLenght = 10;
	private JTextField nameField;
	private JPasswordField passwordField;
	private JTextField emailField;
	private MainWindow mainWindow;
	private UserDaoPostgres dao = new UserDaoPostgres();
	int xx,xy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterWindow window = new RegisterWindow();
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
	public RegisterWindow() {
		initialize();
	}
	
	public RegisterWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
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
		
		
		JButton registerBTN = new JButton("");
		registerBTN.setBounds(58, 211, 127, 41);
		frame.getContentPane().add(registerBTN);
		registerBTN.setOpaque(false);
		registerBTN.setContentAreaFilled(false);
		registerBTN.setBorderPainted(false);
		registerBTN.setFocusPainted(false);
		registerBTN.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				String name = nameField.getText();
				String email = emailField.getText() ;
				String password = passwordField.getText();
				String regex = "^[a-zA0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
				
				if(password.length() < minPasswordLength) {
					JOptionPane.showMessageDialog(frame,"Senha muito curta.");
					return;
				}
				
				if(email.equals("") || email.length() < minEmailLenght) {
					JOptionPane.showMessageDialog(frame,"Email muito curto.");
					return;
				}
				
				if(!Pattern.matches(regex, email)) {
					JOptionPane.showMessageDialog(frame,"Insira um e-mail válido!");
					return;
				}
				
				if(name.length() < minNameLength) {
					JOptionPane.showMessageDialog(frame,"O nome precisa ter ao menos 4 letras.s");
					return;
				}
				
				
				try {

					dao.insertUser(name, email, password);
					mainWindow.updateUser(dao.findUserByLoguin(email, password));
					mainWindow.updateButtons();
					JOptionPane.showMessageDialog(frame, "Usuário criado com sucesso.");
					frame.dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame,"Erro ao criar usuário.");
				}
				frame.dispose();
				mainWindow.setRegisterWindow(null);
			}
		});
		
		
		
		emailField = new JTextField();
		emailField.setBounds(13, 127, 223, 20);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(13, 167, 223, 20);
		frame.getContentPane().add(passwordField);
		
		nameField = new JTextField();
		nameField.setBounds(13, 90, 223, 20);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		
        JPanel blackLine_2 = new JPanel();
        blackLine_2.setBounds(0, 0, 250, 23);
        blackLine_2.setForeground(Color.BLACK);
        blackLine_2.setBackground(Color.BLACK);
        frame.getContentPane().add(blackLine_2);
        blackLine_2.setLayout(null);
        
        
        JLabel titleLabel = new JLabel("bet-betina v1.21 - Registro");
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setBounds(0, 5, 214, 16);
        blackLine_2.add(titleLabel);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        JLabel closeBTN = new JLabel("X");
        closeBTN.setBounds(224, 2, 14, 21);
        blackLine_2.add(closeBTN);
        closeBTN.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		frame.dispose();
        		mainWindow.setRegisterWindow(null);
        		
        	}
        });
        closeBTN.setFont(new Font("Tahoma", Font.PLAIN, 20));
        closeBTN.setForeground(new Color(255, 255, 255));
        
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(RegisterWindow.class.getResource("/resources/registerBG.png")));
		lblNewLabel.setBounds(0, 16, 250, 250);
		frame.getContentPane().add(lblNewLabel);
		

		
	}
}
