package ui.team;

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

import dao.team.TeamDaoPostgres;
import model.Team;
import util.InputManipulation;

public class UpdateTeamWindow extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private TeamDaoPostgres dao = new TeamDaoPostgres ();
	private TeamMainWindow testeClass;
	private Team team;
	private JTextField teamNameFLD = new JTextField();
	private JTextField teamAbbreviationFLD = new JTextField();
	
	UpdateTeamWindow(){
		
		super();
		
		setBorder(new LineBorder(new Color(0, 0, 0)));
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
		
		backBTN.setIcon(new ImageIcon(CreateTeamWindow.class.getResource("/resources/backBTN.png")));
		backBTN.setContentAreaFilled(false);
		backBTN.setBorder(null);
		backBTN.setBounds(271, 136, 30, 30);
		getContentPane().add(backBTN);
		
		teamNameFLD.setOpaque(false);
		teamNameFLD.setForeground(Color.WHITE);
		teamNameFLD.setColumns(10);
		teamNameFLD.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		teamNameFLD.setBounds(146, 48, 155, 20);
		getContentPane().add(teamNameFLD);
	
		teamAbbreviationFLD.setOpaque(false);
		teamAbbreviationFLD.setForeground(Color.WHITE);
		teamAbbreviationFLD.setColumns(10);
		teamAbbreviationFLD.setBorder(new LineBorder(new Color(53, 83, 85), 1, true));
		teamAbbreviationFLD.setBounds(146, 85, 155, 20);
		getContentPane().add(teamAbbreviationFLD);
		
		JButton updateTeamBTN = new JButton("");
		updateTeamBTN.setContentAreaFilled(false);
		updateTeamBTN.setBounds(16, 136, 131, 30);
		updateTeamBTN.setOpaque(false);
		updateTeamBTN.setBorderPainted(false);
		updateTeamBTN.setFocusPainted(false);
		getContentPane().add(updateTeamBTN);
		updateTeamBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String newName = teamNameFLD.getText();
				String newAbbreviation = teamAbbreviationFLD.getText();
				
				if(!InputManipulation.isMinTeamAbbreviationLength(newAbbreviation)) {
					JOptionPane.showMessageDialog(UpdateTeamWindow.this, "A abreviação do time é muito curta.");
					return;			
				}
				if(InputManipulation.isMaxTeamAbbreviationLength(newAbbreviation)) {
					JOptionPane.showMessageDialog(UpdateTeamWindow.this, "A abreviação do time é grande demais.");
					return;
				}
				if(!InputManipulation.isMinTeamNameLength(newName)) {
					JOptionPane.showMessageDialog(UpdateTeamWindow.this, "O nome do time é muito curto.");
					return;
				}	
				
				if(!newName.equals(team.getName())) {
					try {
						dao.updateTeamName(team, newName);
						JOptionPane.showMessageDialog(UpdateTeamWindow.this, "Nome do time atualizado com sucesso.");
						testeClass.updateTeams();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(UpdateTeamWindow.this, "Não foi possível atualizar o nome do time.");
					}
				}else {
					JOptionPane.showMessageDialog(UpdateTeamWindow.this, "O novo nome não pode ser igual ao anterior.");
					return;
				}
				
				if(!newAbbreviation.equals(team.getAbbreviation())) {
					try {
						dao.updateAbbreviation(team, newAbbreviation);
						testeClass.updateTeams();
						JOptionPane.showMessageDialog(UpdateTeamWindow.this, "Abreviação do time atualizado com sucesso.");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(UpdateTeamWindow.this, "Não foi possível atualizar a abreviação do time.");
					}
				}else {
					JOptionPane.showMessageDialog(UpdateTeamWindow.this, "A nova abreviação não pode ser igual à anterior.");
					return;
				}
				dispose();
			}
		});
		
		JLabel bgImageLBL = new JLabel("");
		bgImageLBL.setIcon(new ImageIcon(UpdateTeamWindow.class.getResource("/resources/updateTeamBG.png")));
		bgImageLBL.setBounds(0, 0, 320, 180);
		getContentPane().add(bgImageLBL);
	}
	
	public void setTesteClass(TeamMainWindow testeClass) {
		this.testeClass = testeClass;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void turnOn() {
		teamNameFLD.setText(team.getName());
		teamAbbreviationFLD.setText(team.getAbbreviation());
		setTitle("Bet-Betina v1.23 - Atualizando o Time [" + team.getAbbreviation() + "]");
	}
	
}
