package org.ifba.bet.ui.match;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.team.TeamDaoPostgres;
import org.ifba.bet.model.Bid;
import org.ifba.bet.model.Match;

public class BidWindow extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TeamDaoPostgres teamDao = new TeamDaoPostgres();
	private Match match = new Match(20, 1, 1, 1, 1.28, 1.45, 7.45);
	private String homeTeamName;
	private String awayTeamName;
	private int betId;
	private MatchMainWindow matchMainWindow;

	private JRadioButton rdbtnDraw;
	private JRadioButton rdbtnAwayTeamWin;
	private JRadioButton rdbtnHomeTeamWin;
	private JLabel matchTitleLBL;

	public BidWindow() {

		getContentPane().setBackground(new Color(255, 255, 255));
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
		matchTitleLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		matchTitleLBL.setBounds(10, 0, 346, 28);
		panel.add(matchTitleLBL);

		ButtonGroup group = new ButtonGroup();

		JLabel txtLBL = new JLabel("Faça Seu Palpite:");
		txtLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		txtLBL.setBounds(14, 31, 333, 23);
		getContentPane().add(txtLBL);

		JLabel bidValueLBL = new JLabel("Valor Do Lance: ");
		bidValueLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
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
		rdbtnHomeTeamWin.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		rdbtnHomeTeamWin.setActionCommand("1");
		panel_1.add(rdbtnHomeTeamWin);
		group.add(rdbtnHomeTeamWin);

		rdbtnAwayTeamWin = new JRadioButton();
		rdbtnAwayTeamWin.setOpaque(false);
		rdbtnAwayTeamWin.setBounds(6, 33, 314, 23);
		rdbtnAwayTeamWin.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		rdbtnAwayTeamWin.setActionCommand("2");
		panel_1.add(rdbtnAwayTeamWin);
		group.add(rdbtnAwayTeamWin);

		rdbtnDraw = new JRadioButton();
		rdbtnDraw.setOpaque(false);
		rdbtnDraw.setBounds(6, 59, 314, 23);
		rdbtnDraw.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		rdbtnDraw.setActionCommand("3");
		panel_1.add(rdbtnDraw);
		group.add(rdbtnDraw);

		JButton cancelBidBTN = new JButton("Cancelar Lance");
		cancelBidBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelBidBTN.setBackground(new Color(255, 0, 0));
		cancelBidBTN.setForeground(new Color(255, 0, 0));
		cancelBidBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		cancelBidBTN.setContentAreaFilled(false);
		cancelBidBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		cancelBidBTN.setBounds(14, 186, 129, 23);
		getContentPane().add(cancelBidBTN);

		JButton confirmBidBTN = new JButton("Confirmar Lance");
		confirmBidBTN.setEnabled(false);
		confirmBidBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		confirmBidBTN.setContentAreaFilled(false);
		confirmBidBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		confirmBidBTN.setBounds(211, 186, 129, 23);
		getContentPane().add(confirmBidBTN);

		JTextField bidValueFLD = new JTextField();
		bidValueFLD.setBounds(14, 162, 326, 20);
		getContentPane().add(bidValueFLD);
		bidValueFLD.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				confirmBidBTN.setEnabled(true);
			}
		});

		confirmBidBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// public Bid(int iD, double paidValue, int guess, int betID, int matchID
				matchMainWindow.addBid(new Bid(Double.parseDouble(bidValueFLD.getText()),
						Integer.parseInt(group.getSelection().getActionCommand()), betId, match.getId()));
				dispose();
			}
		});

		setMatch(match);
		update();
		setVisible(true);
		setSize(357, 244);
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
		try {
			homeTeamName = teamDao.findTeamById(match.getHomeTeamId()).getName();
			awayTeamName = teamDao.findTeamById(match.getAwayTeamId()).getName();
		} catch (SQLException e) {
			System.out.println("error");
		}
		update();
	}

	public MatchMainWindow getMatchMainWindow() {
		return matchMainWindow;
	}

	public void setMatchMainWindow(MatchMainWindow matchMainWindow) {
		this.matchMainWindow = matchMainWindow;
	}

	public void update() {
		rdbtnHomeTeamWin.setText("Vitória do " + homeTeamName + " - ODD: " + match.getHomeTeamOdd() + "%");
		rdbtnAwayTeamWin.setText("Vitória do " + awayTeamName + " - ODD: " + match.getAwayTeamOdd() + "%");
		rdbtnDraw.setText("Empate - ODD: " + match.getDrawOdd() + "%");
		matchTitleLBL.setText(match.toString());
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

			// Adicionar uma instância de BidWindow ao JDesktopPane
			BidWindow bidWindow = new BidWindow();
			desktopPane.add(bidWindow);

			frame.setVisible(true);
		});
	}
}
