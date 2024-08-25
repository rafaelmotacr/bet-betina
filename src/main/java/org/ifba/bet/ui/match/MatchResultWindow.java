package org.ifba.bet.ui.match;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.bet.BetDaoPostgres;
import org.ifba.bet.dao.match.MatchDaoPostgres;
import org.ifba.bet.dao.team.TeamDaoPostgres;
import org.ifba.bet.model.Match;
import org.ifba.bet.model.User;

public class MatchResultWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private TeamDaoPostgres teamDao = new TeamDaoPostgres();
	private MatchDaoPostgres matchDao = new MatchDaoPostgres();
	private BetDaoPostgres betDao = new BetDaoPostgres();
	private Match match;
	private String homeTeamName;
	private String awayTeamName;
	private MatchMainWindow matchMainWindow;

	private User currentUser;

	private JRadioButton rdbtnDraw;
	private JRadioButton rdbtnAwayTeamWin;
	private JRadioButton rdbtnHomeTeamWin;
	private JLabel matchTitleLBL;

	public MatchResultWindow() {

		super();

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 357, 216);
		setTitle("Bet-Betina v1.23 - ADM: Resultado de Partida.");
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

		JLabel txtLBL = new JLabel("Defina o Resultado da Partida:");
		txtLBL.setFont(new Font("Georgia", Font.PLAIN, 14));
		txtLBL.setBounds(14, 31, 333, 23);
		getContentPane().add(txtLBL);

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
		rdbtnHomeTeamWin.setActionCommand("1");
		panel_1.add(rdbtnHomeTeamWin);
		group.add(rdbtnHomeTeamWin);

		rdbtnAwayTeamWin = new JRadioButton();
		rdbtnAwayTeamWin.setOpaque(false);
		rdbtnAwayTeamWin.setBounds(6, 33, 314, 23);
		rdbtnAwayTeamWin.setFont(new Font("Georgia", Font.PLAIN, 12));
		rdbtnAwayTeamWin.setActionCommand("2");
		panel_1.add(rdbtnAwayTeamWin);
		group.add(rdbtnAwayTeamWin);

		rdbtnDraw = new JRadioButton();
		rdbtnDraw.setOpaque(false);
		rdbtnDraw.setBounds(6, 59, 314, 23);
		rdbtnDraw.setFont(new Font("Georgia", Font.PLAIN, 12));
		rdbtnDraw.setActionCommand("3");
		panel_1.add(rdbtnDraw);
		group.add(rdbtnDraw);

		JButton cancelBidBTN = new JButton("Cancelar");
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
		cancelBidBTN.setBounds(14, 156, 129, 23);
		getContentPane().add(cancelBidBTN);

		JButton confirmBidBTN = new JButton("Confirmar Resultado");
		confirmBidBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = Integer.parseInt(group.getSelection().getActionCommand());
				try {
					matchDao.updateMatchResult(result, match.getId());
					matchDao.updateMatchState(0, match.getId());
					betDao.updateBetsState(0, match.getId());
					JOptionPane.showMessageDialog(MatchResultWindow.this.getParent(), "Resultado Definido com Sucesso.",
							"Info", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(MatchResultWindow.this.getParent(), "Erro ao Definir Resultado",
							"Erro", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				matchMainWindow.updateMatchs();
				setVisible(false);
			}
		});
		confirmBidBTN.setFont(new Font("Georgia", Font.PLAIN, 12));
		confirmBidBTN.setContentAreaFilled(false);
		confirmBidBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		confirmBidBTN.setBounds(216, 156, 129, 23);
		getContentPane().add(confirmBidBTN);
		setVisible(true);
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

	public void setMatchMainWindow(MatchMainWindow matchMainWindow) {
		this.matchMainWindow = matchMainWindow;
	}

	public User getCurrentUserr() {
		return currentUser;
	}

	public void setCurrentUser(User user) {
		this.currentUser = user;
	}

	public void update() {
		homeTeamName = teamDao.findTeamById(match.getHomeTeamId()).getName();
		awayTeamName = teamDao.findTeamById(match.getAwayTeamId()).getName();
		rdbtnHomeTeamWin.setText("Vitória do Time " + homeTeamName + ".");
		rdbtnAwayTeamWin.setText("Vitória do Time " + awayTeamName + ".");
		rdbtnDraw.setText("Empate.");
		matchTitleLBL.setText(match.toString());
	}

}
