
package ui.user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public abstract  class RegisterWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	public JTextField nameField = null;
	public JTextField emailField = null;
	public JPasswordField confirmPasswordField = null;
	public JPasswordField passwordField = null;
	public JButton registerBTN = null;
	public JLabel backgroundLabel = null;
	
	public RegisterWindow() {

		super();
		setClosable(true);

		setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0)));
		setBounds(0, 0, 250, 275);
		getContentPane().setLayout(null);

		nameField = new JTextField();
		nameField.setForeground(new Color(255, 255, 255));
		nameField.setOpaque(false);
		nameField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		nameField.setBounds(14, 53, 212, 20);
		getContentPane().add(nameField);
		nameField.setColumns(10);

		emailField = new JTextField();
		emailField.setForeground(new Color(255, 255, 255));
		emailField.setOpaque(false);
		emailField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		emailField.setBounds(14, 89, 212, 20);
		getContentPane().add(emailField);
		emailField.setColumns(10);

		JButton backBTN = new JButton("");
		backBTN.setIcon(new ImageIcon(RegisterWindow.class.getResource("/resources/backBTN.png")));
		backBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		backBTN.setContentAreaFilled(false);
		backBTN.setBorder(null);
		backBTN.setBounds(14, 194, 30, 30);
		this.getContentPane().add(backBTN);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setForeground(new Color(255, 255, 255));
		confirmPasswordField.setOpaque(false);
		confirmPasswordField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		confirmPasswordField.setBounds(14, 163, 210, 20);
		this.getContentPane().add(confirmPasswordField);

		passwordField = new JPasswordField();
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setOpaque(false);
		passwordField.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		passwordField.setBounds(14, 126, 210, 20);
		this.getContentPane().add(passwordField);

		registerBTN = new JButton("");
		registerBTN.setBounds(46, 194, 154, 34);
		this.getContentPane().add(registerBTN);
		registerBTN.setOpaque(false);
		registerBTN.setContentAreaFilled(false);
		registerBTN.setBorderPainted(false);
		registerBTN.setFocusPainted(false);
		registerBTN.addActionListener(this::onRegisterButtonClick);
		
		backgroundLabel = new JLabel("");
		backgroundLabel.setBounds(0, 0, 250, 250);
		getContentPane().add(backgroundLabel);
	}
	
	public abstract void onRegisterButtonClick(ActionEvent e);

}
