package org.ifba.bet.ui.bet;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.bet.BetDaoPostgres;
import org.ifba.bet.dao.bid.BidDaoPostgres;
import org.ifba.bet.dao.user.UserDaoPostgres;
import org.ifba.bet.model.Bet;
import org.ifba.bet.model.Bid;
import org.ifba.bet.model.User;

public class BetHistoryWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private DefaultListModel<Bet> listModel;
	private BetViewhCustomListRenderer customListRenderer;

	private User currentUser;
	private BetMainWindow betMainWindow;
	private BidDaoPostgres bidDao = new BidDaoPostgres();
	private BetDaoPostgres betDao = new BetDaoPostgres();
	private UserDaoPostgres userDao = new UserDaoPostgres();

	public BetHistoryWindow() {

		super();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 357, 244);
		setTitle("Bet-Betina v1.23 - Minhas Apostas");
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		getContentPane().setLayout(null);

		listModel = new DefaultListModel<>();
		JList<Bet> list = new JList<>(listModel);

		customListRenderer = new BetViewhCustomListRenderer();

		list.setOpaque(false);
		list.setFont(new Font("Georgia", Font.BOLD, 16));
		list.setCellRenderer(customListRenderer);

		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		scrollPane.setBounds(1, 0, 353, 172);
		getContentPane().add(scrollPane);

		JButton btnNewButton = new JButton("Modificar Aposta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bet bet = list.getSelectedValue();
				if (bet == null) {
					JOptionPane.showMessageDialog(BetHistoryWindow.this, "Selecione um aposta para editar.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				switch (bet.getState()) {
				case 1:
					try {
						betMainWindow.loadBids(betDao.getAllBids(bet.getID()));
						betMainWindow.setForeignBetId(bet.getID());
						setVisible(false);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				default:
					JOptionPane.showMessageDialog(BetHistoryWindow.this, "Não é possível editar esta aposta.",
							"Acesso Negado", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setFont(new Font("Georgia", Font.PLAIN, 14));
		btnNewButton.setBounds(1, 183, 170, 23);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Deletar Aposta");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bet bet = list.getSelectedValue();
				if (bet == null) {
					JOptionPane.showMessageDialog(BetHistoryWindow.this, "Selecione um aposta para apagar.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				try {

					ArrayList<Bid> bidList = new ArrayList<Bid>();
					bidList = bidDao.getAllBids(bet.getID());
					double bidListValue = bidList.stream().mapToDouble(Bid::getPaidValue).sum();
					userDao.updateUserBalance(currentUser, (currentUser.getBalance() + bidListValue));
					betDao.deleteBet(bet.getID());
					betMainWindow.updateStatus();
					JOptionPane.showMessageDialog(BetHistoryWindow.this, "Aposta deletada com sucesso.", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
					updateBets();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(BetHistoryWindow.this, "Ocorreu um erro ao apagar sua aposta.",
							"Erro", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnNewButton_1.setForeground(new Color(255, 0, 0));
		btnNewButton_1.setFont(new Font("Georgia", Font.PLAIN, 14));
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnNewButton_1.setBounds(181, 183, 173, 23);
		getContentPane().add(btnNewButton_1);

		setVisible(true);
		// updateBets();

	}

	public void updateBets() {
		ArrayList<Bet> bets = null;
		try {
			bets = userDao.getAllBets(currentUser.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		listModel.clear();
		for (Bet bet : bets) {
			listModel.addElement(bet);
		}
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void setBetMainWindow(BetMainWindow betMainWindow) {
		this.betMainWindow = betMainWindow;
	}

	public static void main(String[] args) {
		// Garantir que a criação da GUI ocorra na Event Dispatch Thread
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Main Frame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(800, 600);

			// Adicionar um JDesktopPane ao JFrame
			JDesktopPane desktopPane = new JDesktopPane();
			frame.setContentPane(desktopPane);

			// Adicionar uma instância de TesteClass ao JDesktopPane
			BetHistoryWindow testeClass = new BetHistoryWindow();
			desktopPane.add(testeClass);

			frame.setVisible(true);
		});
	}

}