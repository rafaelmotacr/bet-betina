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
	private User user = null;
	int xx,xy;
	private JTextField nameField;
	private JTextField emailField;
	private JTextField passwordField;
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
			this.user = mainWindow.getCurrentUser();
		}
		else {
			try {
				this.user =  dao.findUserByLoguin("rafael.rafael@rafael.rafael", "rafael123");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		initialize();
	}
	
	public ProfileWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.user = mainWindow.getCurrentUser();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		lblNewLabel.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/userIco.jpg")));
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Apagar Perfil");
		btnNewButton.setBounds(254, 326, 123, 23);
		btnNewButton.setForeground(new Color(255, 0, 0));
		frame.getContentPane().add(btnNewButton);
		
		JButton saveBTN = new JButton("Salvar");
		saveBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		saveBTN.setBounds(10, 326, 123, 23);
		saveBTN.setEnabled(false);
		frame.getContentPane().add(saveBTN);
		saveBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordField.setEnabled(false);
				emailField.setEnabled(false);
				nameField.setEnabled(false);
				saveBTN.setEnabled(false);
			}
		});

		
		JPanel panel = new JPanel();
		panel.setBounds(10, 41, 123, 274);
		panel.setBackground(new Color(244, 67, 54));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2_1 = new JLabel("DADOS");
		lblNewLabel_2_1.setBounds(25, 11, 72, 28);
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		panel.add(lblNewLabel_2_1);
		
		nameField = new JTextField();
		nameField.setEnabled(false);
		nameField.setBounds(11, 87, 102, 20);
		panel.add(nameField);
		nameField.setText(user.getName());
		nameField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("NOME");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(11, 74, 46, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("EMAIL");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3_1.setBounds(10, 105, 46, 14);
		panel.add(lblNewLabel_3_1);
		
		emailField = new JTextField();
		emailField.setEnabled(false);
		emailField.setColumns(10);
		emailField.setBounds(11, 118, 102, 20);
		emailField.setText(user.getEmail());
		panel.add(emailField);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("SENHA");
		lblNewLabel_3_1_1.setForeground(Color.WHITE);
		lblNewLabel_3_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3_1_1.setBounds(11, 161, 46, 14);
		panel.add(lblNewLabel_3_1_1);
		
		JLabel idLabel = new JLabel("ID:" + user.getID());
		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		idLabel.setBounds(11, 50, 46, 14);
		panel.add(idLabel);
		
		passwordField = new JPasswordField();
		passwordField.setEnabled(false);
		passwordField.setColumns(10);
		passwordField.setBounds(11, 176, 102, 20);
		passwordField.setText(user.getPassword());
		
		panel.add(passwordField);
		
		JButton editBTN = new JButton("");
		editBTN.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/testepencil.png")));
		editBTN.setBounds(93, 50, 20, 20);
		panel.add(editBTN);
		editBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordField.setEnabled(true);
				emailField.setEnabled(true);
				nameField.setEnabled(true);
				saveBTN.setEnabled(true);
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel(user.getName().concat(user.getAccessLevel() == 2 ? " - Usuario Comum.": " - Admnistrador."));
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(214, 102, 212, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
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
  
        JLabel titleLabel = new JLabel("bet-betina v1.21 - Perfil de " + user.getName());
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
        		System.exit(0);
        	}
        });
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(507, 41, 123, 274);
		panel_1.setBackground(new Color(244, 67, 54));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("EXTRATO");
		lblNewLabel_2.setBounds(13, 5, 97, 28);
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		panel_1.add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Depositar");
		btnNewButton_1.setBounds(507, 326, 123, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel balanceLabel = new JLabel("Saldo Atual: R$ " + user.getBalance() + ".");
		balanceLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		balanceLabel.setBounds(214, 120, 163, 14);
		frame.getContentPane().add(balanceLabel);
        
	}
}
