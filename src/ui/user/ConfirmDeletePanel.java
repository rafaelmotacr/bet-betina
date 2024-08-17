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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import dao.UserDaoPostgres;
import model.User;

public class ConfirmDeletePanel extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	// Conexão com banco de dados
	
	private UserDaoPostgres dao = new UserDaoPostgres();
	
	// JLabel declarada aqui para er acessível
	// aos métodos da classe
	
	private JLabel confirmationTextLabel = new JLabel();
	
	// Ponteiro para o usuário a ser deletado
	
	private User currentUser = null;
	
	// Ponteiro para a janela de perfil 
	// para casos onde seja necessário
	// fechá-la
	
	private ProfileWindow profileWindow = null;
	
	// Ponteiro para a janela principal
	// para que seja possível deslogar
	// o usuário quando seu perfil 
	// for apagado
	
	private MainWindow mainWindow = null;

	public ConfirmDeletePanel() {

		super();

		setTitle("Confirmação de Exclusão");
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBounds(0, 0, 212, 280);
		setFocusable(false);
		setClosable(true);
		getContentPane().setLayout(null);

		JButton confirmDeleteBTN = new JButton("Sim");
		confirmDeleteBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		confirmDeleteBTN.setContentAreaFilled(false);
		confirmDeleteBTN.setBounds(8, 196, 90, 23);
		getContentPane().add(confirmDeleteBTN);
		confirmDeleteBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dao.deletUser(currentUser);
					mainWindow.updateUser(null);
					JOptionPane.showMessageDialog(ConfirmDeletePanel.this, "Usuário(a) deletado(a) com sucesso. Fechando janela.");
					dispose();
					profileWindow.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(ConfirmDeletePanel.this, "Não foi possível deletar o seu usuário.");
					dispose();
				}
			}
		});

		JButton cancelDeleteBTN = new JButton("Esquece");
		cancelDeleteBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		cancelDeleteBTN.setContentAreaFilled(false);
		cancelDeleteBTN.setBounds(102, 196, 90, 23);
		getContentPane().add(cancelDeleteBTN);
		cancelDeleteBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JLabel profilePictureLabel_1 = new JLabel("");
		profilePictureLabel_1.setIcon(new ImageIcon(ConfirmDeletePanel.class.getResource("/resources/user.png")));
		profilePictureLabel_1.setBounds(73, 11, 50, 50);
		getContentPane().add(profilePictureLabel_1);

		JPanel blackLine_1_1 = new JPanel();
		blackLine_1_1.setForeground(Color.BLACK);
		blackLine_1_1.setBackground(Color.BLACK);
		blackLine_1_1.setBounds(37, 72, 119, 3);
		getContentPane().add(blackLine_1_1);

		confirmationTextLabel.setVerticalAlignment(SwingConstants.TOP);
		confirmationTextLabel.setBounds(8, 86, 178, 99);
		confirmationTextLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		getContentPane().add(confirmationTextLabel);
	}

	public void turnOn(MainWindow mainWindow, ProfileWindow profileWindow) {
		this.profileWindow = profileWindow;
		this.mainWindow = mainWindow;
		currentUser = mainWindow.getCurrentUser();
		confirmationTextLabel.setText("<html>Deseja realmente apagar para \r\n<br> sempre o "
				.concat(currentUser.getAccessLevel() == 0 ? "usuario(a)" : "admnistrador") + "<strong> "
				+ currentUser.getName() + "</strong>?\r\n<br>(Para sempre é um tempão!)</html>");
	}
}
