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

public class RegisterWindow {

	JFrame frame;
	private JTextField nameField;
	private JPasswordField passwordField;
	private JTextField emailField;
	private MainWindow mainWindow;
	private UserDaoPostgres dao = new UserDaoPostgres();
	private JButton backBTN;
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
				
				try {
					String nome = nameField.getText();
					String email = emailField.getText() ;
					String senha = passwordField.getText();
					dao.insertUser(nome, email, senha);
					mainWindow.updateUser(dao.findUserByLoguin(email, senha));
					mainWindow.updateButtons();
					JOptionPane.showMessageDialog(frame, "Usuário criado com sucesso.");
					frame.dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame,"Erro ao criar usuário.");
				}
				frame.dispose();
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
        titleLabel.setBounds(0, 5, 214, 16);
        blackLine_2.add(titleLabel);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        JLabel closeBTN = new JLabel("X");
        closeBTN.setBounds(224, 2, 14, 21);
        blackLine_2.add(closeBTN);
        closeBTN.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		System.exit(0);
        	}
        });
        closeBTN.setFont(new Font("Tahoma", Font.PLAIN, 20));
        closeBTN.setForeground(new Color(255, 255, 255));
		
		backBTN = new JButton("");
		backBTN.setIcon(new ImageIcon(RegisterWindow.class.getResource("/resources/back_btn_register.png")));
		backBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 5));
		backBTN.setBounds(13, 215, 40, 35);
		frame.getContentPane().add(backBTN);
		backBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
        
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(RegisterWindow.class.getResource("/resources/registro.png")));
		lblNewLabel.setBounds(0, 16, 250, 250);
		frame.getContentPane().add(lblNewLabel);
		

		
	}
}
