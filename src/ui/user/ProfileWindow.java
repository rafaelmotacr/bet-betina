package ui.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import dao.UserDaoPostgres;
import exceptions.PasswordsDontMatchException;
import model.User;
import util.InputManipulation;

public class ProfileWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private MainWindow mainWindow = null;
	private User currentUser = null;
	private UserDaoPostgres dao = new UserDaoPostgres();
	private JTextField nameField;
	private JTextField emailField;
	private JPasswordField changePasswordField;
	private JPasswordField confirmPasswordField;
	private JButton editDataBTN = new JButton("");
	private JButton editPasswordBTN = new JButton("");
	private JLabel nameLabel = new JLabel();
	private JLabel idLabel = new JLabel();
	private JLabel balanceLabel = new JLabel();

	public ProfileWindow() {

		super();
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		setClosable(true);
		setBounds(0, 0, 640, 360);
		getContentPane().setLayout(null);

		ConfirmDeletePanel confirmDeleteWindow = new ConfirmDeletePanel();
		confirmDeleteWindow.setBounds(214, 11, 212, 280);
		getContentPane().add(confirmDeleteWindow);

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mainPanel.setBounds(214, 11, 212, 280);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		nameLabel.setBounds(12, 77, 202, 14);
		mainPanel.add(nameLabel);
		nameLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		JButton saveBTN = new JButton("Salvar Alterações");
		saveBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		saveBTN.setContentAreaFilled(false);
		saveBTN.setBounds(47, 302, 157, 23);
		saveBTN.setEnabled(false);
		getContentPane().add(saveBTN);
		saveBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String email = emailField.getText();
				String name = nameField.getText();
				String encriptedPassword = null;
				String passwordField1 = String.valueOf(changePasswordField.getPassword());
				String passwordField2 = String.valueOf(confirmPasswordField.getPassword());

				if (nameField.isEnabled() && emailField.isEnabled()) {

					// Update name

					if (!name.equals(currentUser.getName())) {
						try {
							dao.updateUserName(currentUser, name);
							currentUser.setName(name);
							ProfileWindow.this.setTitle("bet-betina v1.21 - Perfil de " + currentUser.getName());
							nameLabel.setText(currentUser.getName().concat(
									currentUser.getAccessLevel() == 0 ? " - Usuario Comum." : " - Admnistrador."));
							JOptionPane.showMessageDialog(ProfileWindow.this, "Nome atualizado com sucesso.");
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(ProfileWindow.this, "Erro ao atualizar nome.");
						}
					}

					// Update email

					if (!email.equals(currentUser.getEmail())) {
						try {
							dao.updateUserName(currentUser, email);
							currentUser.setEmail(email);
							JOptionPane.showMessageDialog(ProfileWindow.this, "Email atualizado com sucesso.");
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(ProfileWindow.this, "Erro ao atualizar email.");
						}
					}
				}

				// Update password

				if (changePasswordField.isEnabled() && confirmPasswordField.isEnabled()) {

					if (passwordField1.length() > 0 && passwordField2.length() > 0) {
						try {
							try {
								encriptedPassword = InputManipulation.generateHashedPassword(
										InputManipulation.joinPasswords(passwordField1, passwordField2));
								dao.updateUserPassword(currentUser, encriptedPassword);
								changePasswordField.setText(null);
								confirmPasswordField.setText(null);
								JOptionPane.showMessageDialog(ProfileWindow.this, "Senha atualizada com sucesso.");
							} catch (SQLException e1) {
								JOptionPane.showMessageDialog(ProfileWindow.this,
										"Não foi possível atualizar sua senha.");
								e1.printStackTrace();
							}
						} catch (PasswordsDontMatchException e1) {
							JOptionPane.showMessageDialog(ProfileWindow.this, "As senhas não conferem!");
							return;
						}
					}
				}

				// Comentário

				changePasswordField.setEnabled(false);
				confirmPasswordField.setEnabled(false);
				emailField.setEnabled(false);
				nameField.setEnabled(false);
				saveBTN.setEnabled(false);

			}
		});

		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		dataPanel.setBounds(10, 11, 194, 280);
		dataPanel.setBackground(new Color(0, 128, 128));
		getContentPane().add(dataPanel);
		dataPanel.setLayout(null);

		JLabel lblNewLabel_2_1 = new JLabel("DADOS");
		lblNewLabel_2_1.setBounds(61, 11, 72, 28);
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		dataPanel.add(lblNewLabel_2_1);

		nameField = new JTextField();
		nameField.setBorder(new LineBorder(new Color(0, 0, 0)));
		nameField.setEnabled(false);
		nameField.setBounds(11, 87, 173, 20);
		dataPanel.add(nameField);
		nameField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("NOME");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(11, 74, 46, 14);
		dataPanel.add(lblNewLabel_3);

		JLabel lblNewLabel_3_1 = new JLabel("EMAIL");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3_1.setBounds(11, 118, 46, 14);
		dataPanel.add(lblNewLabel_3_1);

		emailField = new JTextField();
		emailField.setBorder(new LineBorder(new Color(0, 0, 0)));
		emailField.setEnabled(false);
		emailField.setColumns(10);
		emailField.setBounds(11, 131, 173, 20);
		dataPanel.add(emailField);

		JLabel lblNewLabel_3_1_1 = new JLabel("NOVA SENHA");
		lblNewLabel_3_1_1.setForeground(Color.WHITE);
		lblNewLabel_3_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3_1_1.setBounds(11, 184, 99, 14);
		dataPanel.add(lblNewLabel_3_1_1);

		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		idLabel.setBounds(11, 50, 46, 14);
		dataPanel.add(idLabel);

		editDataBTN.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/editBTN.png")));
		editDataBTN.setBounds(164, 50, 20, 20);
		dataPanel.add(editDataBTN);
		editDataBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailField.setEnabled(true);
				nameField.setEnabled(true);
				saveBTN.setEnabled(true);
			}
		});

		changePasswordField = new JPasswordField();
		changePasswordField.setBorder(new LineBorder(new Color(0, 0, 0)));
		changePasswordField.setEnabled(false);
		changePasswordField.setColumns(10);
		changePasswordField.setBounds(11, 200, 173, 20);
		dataPanel.add(changePasswordField);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBorder(new LineBorder(new Color(0, 0, 0)));
		confirmPasswordField.setEnabled(false);
		confirmPasswordField.setText((String) null);
		confirmPasswordField.setColumns(10);
		confirmPasswordField.setBounds(11, 243, 173, 20);
		dataPanel.add(confirmPasswordField);

		JLabel lblNewLabel_3_1_1_1 = new JLabel("CONFIRMAR NOVA SENHA");
		lblNewLabel_3_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_3_1_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3_1_1_1.setBounds(11, 229, 173, 14);
		dataPanel.add(lblNewLabel_3_1_1_1);

		editPasswordBTN.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/editBTN.png")));
		editPasswordBTN.setBounds(164, 162, 20, 20);
		dataPanel.add(editPasswordBTN);
		editPasswordBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePasswordField.setEnabled(true);
				confirmPasswordField.setEnabled(true);
				saveBTN.setEnabled(true);
			}
		});

		JLabel lblNewLabel_2_1_2 = new JLabel("SENHA");
		lblNewLabel_2_1_2.setForeground(Color.WHITE);
		lblNewLabel_2_1_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblNewLabel_2_1_2.setBounds(61, 152, 72, 28);
		dataPanel.add(lblNewLabel_2_1_2);

		JButton btnNewButton_1 = new JButton("Meu Histórico");
		btnNewButton_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(435, 302, 195, 23);
		getContentPane().add(btnNewButton_1);

		JPanel balancePanel = new JPanel();
		balancePanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		balancePanel.setLayout(null);
		balancePanel.setBackground(new Color(0, 128, 128));
		balancePanel.setBounds(436, 11, 194, 280);
		getContentPane().add(balancePanel);

		JLabel lblNewLabel_2_1_1 = new JLabel("EXTRATO");
		lblNewLabel_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_2_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblNewLabel_2_1_1.setBounds(45, 11, 103, 28);
		balancePanel.add(lblNewLabel_2_1_1);

		JLabel tmpLabel = new JLabel("+ 15 BRL");
		tmpLabel.setForeground(new Color(255, 255, 255));
		tmpLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		tmpLabel.setBounds(12, 52, 212, 14);
		balancePanel.add(tmpLabel);

		JLabel lblBrl = new JLabel("- 20 BRL");
		lblBrl.setForeground(new Color(255, 255, 255));
		lblBrl.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblBrl.setBounds(12, 78, 212, 14);
		balancePanel.add(lblBrl);

		JLabel lblBrl_2 = new JLabel("- 70 BRL");
		lblBrl_2.setForeground(new Color(255, 255, 255));
		lblBrl_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblBrl_2.setBounds(12, 104, 212, 14);
		balancePanel.add(lblBrl_2);

		// inicio da felete label

		JButton backBTN = new JButton("");
		backBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// limite da felete label

		JButton deleteUserBTN = new JButton("Apagar Perfil");
		deleteUserBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		deleteUserBTN.setContentAreaFilled(false);
		deleteUserBTN.setBounds(214, 302, 212, 23);
		deleteUserBTN.setForeground(new Color(255, 0, 0));
		getContentPane().add(deleteUserBTN);

		deleteUserBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmDeleteWindow.turnOn(mainWindow, ProfileWindow.this);
				confirmDeleteWindow.setVisible(true);

			}
		});

		balanceLabel.setBounds(12, 103, 163, 14);
		mainPanel.add(balanceLabel);
		balanceLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		JLabel profilePictureLabel = new JLabel("");
		profilePictureLabel.setBounds(81, 12, 50, 50);
		mainPanel.add(profilePictureLabel);
		profilePictureLabel.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/user.png")));

		JPanel blackLine_1 = new JPanel();
		blackLine_1.setBounds(46, 66, 119, 3);
		mainPanel.add(blackLine_1);
		blackLine_1.setForeground(Color.BLACK);
		blackLine_1.setBackground(Color.BLACK);

		JLabel totalBetsLabel = new JLabel();
		totalBetsLabel.setBounds(12, 153, 163, 14);
		mainPanel.add(totalBetsLabel);
		totalBetsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		JLabel totalLosesLabel = new JLabel();
		totalLosesLabel.setBounds(12, 179, 163, 14);
		mainPanel.add(totalLosesLabel);
		totalLosesLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		JLabel totalWinsLabel = new JLabel();
		totalWinsLabel.setBounds(12, 205, 163, 14);
		mainPanel.add(totalWinsLabel);
		totalWinsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		JLabel victoryRateLabel = new JLabel();
		victoryRateLabel.setBounds(12, 231, 163, 14);
		mainPanel.add(victoryRateLabel);
		victoryRateLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		JLabel favoriteTeamLabel = new JLabel();
		favoriteTeamLabel.setBounds(12, 257, 163, 14);
		mainPanel.add(favoriteTeamLabel);
		favoriteTeamLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		// Botão de voltar

		backBTN.setIcon(new ImageIcon(ProfileWindow.class.getResource("/resources/backBTN.png")));
		backBTN.setContentAreaFilled(false);
		backBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		backBTN.setBounds(10, 302, 30, 23);
		getContentPane().add(backBTN);

		// Botão de mostrar estatísticas
		
		JButton statsBTN = new JButton("Ver Estatísticas");
		JButton surroundStatsBTN = new JButton("Ocultar Estatísticas");

		statsBTN.setBounds(18, 126, 175, 23);
		statsBTN.setContentAreaFilled(false);
		statsBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mainPanel.add(statsBTN);
		statsBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					totalBetsLabel.setText("Total de Apostas: " + dao.getTotalBets(currentUser) + ".");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(ProfileWindow.this, "Erro ao ler suas estatísticas.");
					return;
				}
				totalLosesLabel.setText("Total de Derrotas:");
				totalWinsLabel.setText("Total de Vitórias: ");
				victoryRateLabel.setText("Taxa de Vitória:");
				favoriteTeamLabel.setText("Time Favorito: ");
				mainPanel.remove(statsBTN);
				mainPanel.add(surroundStatsBTN);
				surroundStatsBTN.setVisible(true);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		surroundStatsBTN.setVisible(false);

		// Botão de ocultar estatísticas

		surroundStatsBTN.setBounds(18, 126, 175, 23);
		surroundStatsBTN.setContentAreaFilled(false);
		surroundStatsBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mainPanel.add(surroundStatsBTN);
		surroundStatsBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalBetsLabel.setText("");
				totalLosesLabel.setText("");
				totalWinsLabel.setText("");
				victoryRateLabel.setText("");
				favoriteTeamLabel.setText("");
				surroundStatsBTN.setVisible(false);
				mainPanel.add(statsBTN);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});

	}

	public void turnOn(MainWindow mainWinndowPointer) {
		this.currentUser = mainWinndowPointer.getCurrentUser();
		this.mainWindow = mainWinndowPointer;
		setTitle("bet-betina v1.21 - Perfil de " + currentUser.getName());
		nameLabel.setText(currentUser.getName()
				.concat(currentUser.getAccessLevel() == 2 ? " - Usuario Comum." : " - Admnistrador."));
		balanceLabel.setText(("Saldo Atual: R$ " + currentUser.getBalance() + "."));
		idLabel.setText("ID:" + currentUser.getID());
		nameField.setText(currentUser.getName());
		emailField.setText(currentUser.getEmail());

	}
}