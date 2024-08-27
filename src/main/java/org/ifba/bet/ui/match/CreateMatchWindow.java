package org.ifba.bet.ui.match;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.match.MatchDaoPostgres;
import org.ifba.bet.dao.team.TeamDaoPostgres;
import org.ifba.bet.model.Team;

public class CreateMatchWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private TeamDaoPostgres teamDao = new TeamDaoPostgres();
	private MatchDaoPostgres matchDao = new MatchDaoPostgres();
	private JLabel matchTitleLBL;
	private MatchMainWindow matchMainWindow;
	private JComboBox<Team> awayTeamBox;
	private JComboBox<Team> homeTemBox;
	
	private ArrayList<Team> teamArray;

	public CreateMatchWindow() {

		super();

		try {
			teamArray = teamDao.getAllTeams();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 428, 239);
		setTitle("Bet-Betina v1.23 - ADM: Registro de Nova Partida.");
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 426, 28);
		getContentPane().add(panel);

		matchTitleLBL = new JLabel();
		matchTitleLBL.setText("Criando Nova Partida");
		matchTitleLBL.setForeground(Color.WHITE);
		matchTitleLBL.setFont(new Font("Georgia", Font.PLAIN, 20));
		matchTitleLBL.setBounds(22, 0, 346, 28);
		panel.add(matchTitleLBL);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(20, 97, 385, 69);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblDeterminarResultado = new JLabel();
		lblDeterminarResultado.setVerticalAlignment(SwingConstants.TOP);
		lblDeterminarResultado.setBounds(10, 11, 296, 16);
		panel_1.add(lblDeterminarResultado);
		lblDeterminarResultado.setText("NOTA¹: ODDS serão geradas aleatoriamente.");
		lblDeterminarResultado.setForeground(new Color(0, 0, 0));
		lblDeterminarResultado.setFont(new Font("Georgia", Font.PLAIN, 14));

		JButton cancelBidBTN = new JButton("Cancelar Operação");
		cancelBidBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		cancelBidBTN.setBackground(new Color(255, 0, 0));
		cancelBidBTN.setForeground(new Color(255, 0, 0));
		cancelBidBTN.setBorder(new LineBorder(new Color(0, 0, 0)));
		cancelBidBTN.setContentAreaFilled(false);
		cancelBidBTN.setFont(new Font("Georgia", Font.PLAIN, 12));
		cancelBidBTN.setBounds(20, 177, 167, 23);
		getContentPane().add(cancelBidBTN);

		awayTeamBox = new JComboBox<>(teamArray.toArray(new Team[0]));
		awayTeamBox.setFont(new Font("Georgia", Font.PLAIN, 14));
		awayTeamBox.setBounds(243, 64, 162, 22);
		getContentPane().add(awayTeamBox);

		homeTemBox = new JComboBox<>(teamArray.toArray(new Team[0]));
		homeTemBox.setFont(new Font("Georgia", Font.PLAIN, 14));
		homeTemBox.setBounds(20, 64, 162, 22);
		getContentPane().add(homeTemBox);

		JButton confirmBidBTN = new JButton("Registrar Partida");
		confirmBidBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selectedHomeTeamIndex = homeTemBox.getSelectedIndex();
				int selectedAwayTeamIndex = awayTeamBox.getSelectedIndex();

				if (selectedHomeTeamIndex == -1 || selectedAwayTeamIndex == -1) {
					JOptionPane.showMessageDialog(CreateMatchWindow.this,
							"Selecione Dois Times Para \n Criar Uma Partida.", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (selectedHomeTeamIndex == selectedAwayTeamIndex) {
					JOptionPane.showMessageDialog(CreateMatchWindow.this, "Os Times Não Podem ser Iguais.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				}

				int homeTeamid = awayTeamBox.getItemAt(selectedAwayTeamIndex).getId();
				int awayTeamid = homeTemBox.getItemAt(selectedHomeTeamIndex).getId();

				try {
					matchDao.insertMatch(homeTeamid, awayTeamid);
					JOptionPane.showMessageDialog(CreateMatchWindow.this, "Partida Criada Com Sucesso.", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					matchMainWindow.updateMatchs();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(CreateMatchWindow.this,
							"Houve Um Erro...\n Não era pra haver um erro.", "Aviso", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				setVisible(false);
			}
		});
		confirmBidBTN.setFont(new Font("Georgia", Font.PLAIN, 12));
		confirmBidBTN.setContentAreaFilled(false);
		confirmBidBTN.setBorder(new LineBorder(new Color(0, 0, 0)));
		confirmBidBTN.setBounds(243, 177, 162, 23);
		getContentPane().add(confirmBidBTN);

		JLabel lblNewLabel = new JLabel("Time de Casa:");
		lblNewLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
		lblNewLabel.setBounds(20, 39, 129, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblTimeDesafiante = new JLabel("Time Adversário:");
		lblTimeDesafiante.setFont(new Font("Georgia", Font.PLAIN, 14));
		lblTimeDesafiante.setBounds(276, 39, 129, 14);
		getContentPane().add(lblTimeDesafiante);

		JLabel lblVs = new JLabel("VS");
		lblVs.setFont(new Font("Georgia", Font.PLAIN, 25));
		lblVs.setBounds(197, 56, 31, 28);
		getContentPane().add(lblVs);
		
		setVisible(true);
	}

	public void updateTeams() {
		try {
			teamArray = teamDao.getAllTeams();
			Collections.sort(teamArray, (t1, t2) -> t1.getName().compareTo(t2.getName()));
			awayTeamBox.removeAll();
			homeTemBox.removeAll();
	        for (Team team : teamArray) {
	        	awayTeamBox.addItem(team);
	        	homeTemBox.addItem(team);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public void setMatchMainWindow(MatchMainWindow matchMainWindow) {
		this.matchMainWindow = matchMainWindow;
	}
	



}
