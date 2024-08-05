package ui;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import dao.UserDaoPostgres;

public class LoginWindow {

	JFrame frame;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton loginBTN;
	private UserDaoPostgres dao = new UserDaoPostgres();
	private MainWindow mainWindow;
	private JButton backBTN;
	

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

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
			}});
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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\rafae\\eclipse-workspace\\betgree\\src\\img\\ico.png"));
		frame.setBounds(100, 100, 259, 286);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		backBTN = new JButton("New button");
		backBTN.setIcon(new ImageIcon(LoginWindow.class.getResource("/resources/back_btn_register.png")));
		backBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		backBTN.setBounds(22, 178, 31, 32);
		frame.getContentPane().add(backBTN);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(27, 138, 154, 20);
		frame.getContentPane().add(passwordField);
		
		loginBTN = new JButton("");
		loginBTN.setBounds(63, 178, 118, 32);
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
					} else {
						JOptionPane.showMessageDialog(frame, "Login ou senha incorretos");
					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		emailField = new JTextField();
		emailField.setBounds(27, 94, 154, 20);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(LoginWindow.class.getResource("/resources/loginBg.png")));
		lblNewLabel.setBounds(0, 0, 250, 250);
		frame.getContentPane().add(lblNewLabel);
	}
}
