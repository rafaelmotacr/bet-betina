package org.ifba.bet.ui.team;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.team.TeamDaoPostgres;
import org.ifba.bet.util.InputManipulation;

public class CreateTeamWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	TeamDaoPostgres dao = new TeamDaoPostgres();
	TeamMainWindow testeClass;

	CreateTeamWindow() {

		super();

		setBorder(new LineBorder(new Color(0, 0, 0)));
		setTitle("Bet-Betina v1.23 - ADM: Criar Time");
		setClosable(true);
		setResizable(false);
		setMaximizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JButton backBTN = new JButton("");
		backBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		backBTN.setIcon(new ImageIcon("src/main/resources/backBTN.png"));
		backBTN.setContentAreaFilled(false);
		backBTN.setBorder(null);
		backBTN.setBounds(271, 136, 30, 30);
		getContentPane().add(backBTN);

		JTextField teamNameFLD = new JTextField();
		teamNameFLD.setText((String) null);
		teamNameFLD.setOpaque(false);
		teamNameFLD.setForeground(Color.WHITE);
		teamNameFLD.setColumns(10);
		teamNameFLD.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		teamNameFLD.setBounds(146, 48, 155, 20);
		getContentPane().add(teamNameFLD);

		JTextField teamAbbreviationFLD = new JTextField();
		teamAbbreviationFLD.setText((String) null);
		teamAbbreviationFLD.setOpaque(false);
		teamAbbreviationFLD.setForeground(Color.WHITE);
		teamAbbreviationFLD.setColumns(10);
		teamAbbreviationFLD.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		teamAbbreviationFLD.setBounds(146, 85, 155, 20);
		getContentPane().add(teamAbbreviationFLD);

		JButton createTeamBTN = new JButton("");
		createTeamBTN.setContentAreaFilled(false);
		createTeamBTN.setBounds(16, 136, 131, 30);
		createTeamBTN.setOpaque(false);
		createTeamBTN.setBorderPainted(false);
		createTeamBTN.setFocusPainted(false);
		getContentPane().add(createTeamBTN);
		createTeamBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String teamName = teamNameFLD.getText();
				String teamAbbreviation = teamAbbreviationFLD.getText();

				if (!InputManipulation.isMinTeamAbbreviationLength(teamAbbreviation)) {
					JOptionPane.showMessageDialog(CreateTeamWindow.this, "A abreviação do time é muito curta.");
					return;
				}
				if (InputManipulation.isMaxTeamAbbreviationLength(teamAbbreviation)) {
					JOptionPane.showMessageDialog(CreateTeamWindow.this, "A abreviação do time é grande demais.");
					return;
				}
				if (!InputManipulation.isMinTeamNameLength(teamName)) {
					JOptionPane.showMessageDialog(CreateTeamWindow.this, "O nome do time é muito curto.");
					return;
				}
				try {
					dao.insertTeam(teamName, teamAbbreviation);
					JOptionPane.showMessageDialog(CreateTeamWindow.this, "Time criado com sucesso.");
					testeClass.updateTeams();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(CreateTeamWindow.this, "Ocorreu um erro ao criar o time.");
					e1.printStackTrace();
				} finally {
					teamNameFLD.setText(null);
					teamAbbreviationFLD.setText(null);
					dispose();
				}
			}
		});

		JLabel bgImageLBL = new JLabel("");
		bgImageLBL.setBorder(new LineBorder(new Color(0, 0, 0)));
		bgImageLBL.setIcon(new ImageIcon("src/main/resources/createTeamBG.png"));
		bgImageLBL.setBounds(0, 0, 320, 180);
		getContentPane().add(bgImageLBL);
	}

	public void setTesteClass(TeamMainWindow testeClass) {
		this.testeClass = testeClass;
	}
}
