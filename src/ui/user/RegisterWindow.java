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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import dao.UserDaoPostgres;
import exceptions.PasswordsDontMatchException;
import util.InputManipulation;

public class RegisterWindow {

	JFrame frame;
	private MainWindow mainWindow;
	private JTextField nameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private UserDaoPostgres dao = new UserDaoPostgres();
	private int xx,xy;
	
//  standard height and width
    int frameHeight = 266;
    int frameWidth = 250;
    
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
		frame.setBounds((int) (d.getWidth() / 2 - frameWidth / 2), (int) (d.getHeight() / 2 - frameHeight / 2), frameWidth, frameHeight);
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
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(14, 180, 223, 20);
        frame.getContentPane().add(confirmPasswordField);
		
        passwordField = new JPasswordField();
        passwordField.setBounds(14, 141, 223, 20);
        frame.getContentPane().add(passwordField);
		
		JButton registerBTN = new JButton("");
		registerBTN.setBounds(58, 211, 127, 41);
		frame.getContentPane().add(registerBTN);
		registerBTN.setOpaque(false);
		registerBTN.setContentAreaFilled(false);
		registerBTN.setBorderPainted(false);
		registerBTN.setFocusPainted(false);
		registerBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = nameField.getText();
				String email = emailField.getText() ;
	
				String password = null;
				String encryptedPassword = null; 
				
				try {
					password = InputManipulation.joinPasswords(String.valueOf(passwordField.getPassword()), String.valueOf(confirmPasswordField.getPassword()));
				} catch (PasswordsDontMatchException e1) {
					JOptionPane.showMessageDialog(frame,"As senhas não conferem!");
					return;
				}
				
		        
				if(!InputManipulation.isMinLengthPassword(password)) {
					JOptionPane.showMessageDialog(frame,"Senha muito curta. Use ao menos 8 digitos.");
					return;
				}
				
				if(!InputManipulation.isMinLengthEmail(email)) {
					JOptionPane.showMessageDialog(frame,"Email muito curto.");
					return;
				}
				
				if(!InputManipulation.isValidEmail(email)) {
					JOptionPane.showMessageDialog(frame,"Insira um e-mail válido.");
					return;
				}
				
				if(!InputManipulation.isValidName(name)) {
					JOptionPane.showMessageDialog(frame,"O nome precisa ter ao menos 4 letras.");
					return;
				}
				
				try {
					if(dao.findUserByEmail(email) != null) {
						JOptionPane.showMessageDialog(frame,"Email já cadastrado no banco de dados!");
						return;
					}
				} catch (SQLException e1) {
					//no coments
				}
				
				encryptedPassword = InputManipulation.generateHashedPassword(password);
				
				try {
					dao.insertUser(name, email, encryptedPassword, 2);
					mainWindow.updateUser(dao.findUserByEmail(email));
					mainWindow.updateButtons();
					JOptionPane.showMessageDialog(frame, "Usuário criado com sucesso.");
					frame.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame,"Erro ao criar usuário.");
				}
				
				frame.dispose();
				mainWindow.getFrame().setEnabled(true);
				mainWindow.getFrame().toFront();
			}
		});
		
		
		emailField = new JTextField();
		emailField.setBounds(14, 107, 223, 20);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBorder(null);
		nameField.setBounds(14, 69, 223, 20);
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
        		mainWindow.getFrame().setEnabled(true);
        		mainWindow.getFrame().toFront();
        		
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
