package org.ifba.bet.ui.bet;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.team.TeamDaoPostgres;
import org.ifba.bet.model.Bid;
import org.ifba.bet.model.Match;
import org.ifba.bet.model.User;

public class BidWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private Match match;
	private String homeTeamName;
	private String awayTeamName;
	private BetMainWindow matchMainWindow;
	private User currentUser;

	private JRadioButton rdbtnDraw;
	private JRadioButton rdbtnAwayTeamWin;
	private JRadioButton rdbtnHomeTeamWin;
	private JLabel matchTitleLBL;
	private JTextField bidValueFLD;

	private TeamDaoPostgres teamDao = new TeamDaoPostgres();
	
	public BidWindow() {

		super();

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 357, 244);
		setTitle("Bet-Betina v1.23 - Menu de Lances");
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 498, 28);
		getContentPane().add(panel);

		matchTitleLBL = new JLabel();
		matchTitleLBL.setForeground(Color.WHITE);
		matchTitleLBL.setFont(new Font("Georgia", Font.PLAIN, 20));
		matchTitleLBL.setBounds(10, 0, 346, 28);
		panel.add(matchTitleLBL);

		ButtonGroup group = new ButtonGroup();

		JLabel txtLBL = new JLabel("Faça Seu Palpite:");
		txtLBL.setFont(new Font("Georgia", Font.PLAIN, 14));
		txtLBL.setBounds(14, 31, 333, 23);
		getContentPane().add(txtLBL);

		JLabel bidValueLBL = new JLabel("Valor Do Lance: ");
		bidValueLBL.setFont(new Font("Georgia", Font.PLAIN, 14));
		bidValueLBL.setBounds(14, 144, 284, 20);
		getContentPane().add(bidValueLBL);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(14, 56, 326, 89);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		rdbtnHomeTeamWin = new JRadioButton();
		rdbtnHomeTeamWin.setSelected(true);
		rdbtnHomeTeamWin.setOpaque(false);
		rdbtnHomeTeamWin.setBounds(6, 7, 314, 23);
		rdbtnHomeTeamWin.setFont(new Font("Georgia", Font.PLAIN, 12));
		rdbtnHomeTeamWin.setActionCommand(String.valueOf(Bid.GUESS_TO_HOME_WIN));
		panel_1.add(rdbtnHomeTeamWin);
		group.add(rdbtnHomeTeamWin);

		rdbtnAwayTeamWin = new JRadioButton();
		rdbtnAwayTeamWin.setOpaque(false);
		rdbtnAwayTeamWin.setBounds(6, 33, 314, 23);
		rdbtnAwayTeamWin.setFont(new Font("Georgia", Font.PLAIN, 12));
		rdbtnAwayTeamWin.setActionCommand(String.valueOf(Bid.GUESS_TO_AWAY_WIN));
		panel_1.add(rdbtnAwayTeamWin);
		group.add(rdbtnAwayTeamWin);

		rdbtnDraw = new JRadioButton();
		rdbtnDraw.setOpaque(false);
		rdbtnDraw.setBounds(6, 59, 314, 23);
		rdbtnDraw.setFont(new Font("Georgia", Font.PLAIN, 12));
		rdbtnDraw.setActionCommand(String.valueOf(Bid.GUESS_TO_DRAW));
		panel_1.add(rdbtnDraw);
		group.add(rdbtnDraw);

		JButton cancelBidBTN = new JButton("Cancelar Lance");
		cancelBidBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		cancelBidBTN.setBackground(new Color(255, 0, 0));
		cancelBidBTN.setForeground(new Color(255, 0, 0));
		cancelBidBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		cancelBidBTN.setContentAreaFilled(false);
		cancelBidBTN.setFont(new Font("Georgia", Font.PLAIN, 12));
		cancelBidBTN.setBounds(14, 186, 129, 23);
		getContentPane().add(cancelBidBTN);

		JButton confirmBidBTN = new JButton("Confirmar Lance");
		confirmBidBTN.setFont(new Font("Georgia", Font.PLAIN, 12));
		confirmBidBTN.setContentAreaFilled(false);
		confirmBidBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		confirmBidBTN.setBounds(211, 186, 129, 23);
		getContentPane().add(confirmBidBTN);

		bidValueFLD = new JTextField();
		bidValueFLD.setBounds(14, 162, 326, 20);
		getContentPane().add(bidValueFLD);

		confirmBidBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (bidValueFLD.getText().equals("") || bidValueFLD.getText() == null) {
					JOptionPane.showMessageDialog(BidWindow.this.getParent(), "Insira o Valor do Lance.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				double paidValue = Double.parseDouble(bidValueFLD.getText());
				int guess = Integer.parseInt(group.getSelection().getActionCommand());
				int matchId = match.getId();

				if (paidValue < 0) {
					JOptionPane.showMessageDialog(BidWindow.this.getParent(),
							"Você não pode apostar zero reais, amigão.", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (paidValue > currentUser.getBalance()) {
					JOptionPane.showMessageDialog(BidWindow.this.getParent(),
							"O valor do lance não pode \ntranspassar seu saldo atual.", "Aviso",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				bidValueFLD.setText("");
				matchMainWindow.addBid(new Bid(paidValue, guess, matchId));
				setVisible(false);
			}
		});
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
		homeTeamName = teamDao.findTeamById(match.getHomeTeamId()).getName();
		awayTeamName = teamDao.findTeamById(match.getAwayTeamId()).getName();
		update();
	}

	public void setBetMainWindow(BetMainWindow matchMainWindow) {
		this.matchMainWindow = matchMainWindow;
	}

	public User getCurrentUserr() {
		return currentUser;
	}

	public void setCurrentUser(User user) {
		this.currentUser = user;
	}

	public void update() {
		rdbtnHomeTeamWin.setText("Vitória do Time " + homeTeamName + " - ODD: " + match.getHomeTeamOdd() + "%.");
		rdbtnAwayTeamWin.setText("Vitória do Time " + awayTeamName + " - ODD: " + match.getAwayTeamOdd() + "%.");
		rdbtnDraw.setText("Empate - ODD: " + match.getDrawOdd() + "%");
		matchTitleLBL.setText(match.toString());
	}
}
