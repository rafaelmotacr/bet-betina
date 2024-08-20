package ui.team;

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
import javax.swing.border.LineBorder;

import dao.match.MatchDaoPostgres;
import dao.team.TeamDaoPostgres;
import model.Team;

public class ConfirmDeleteTeamPanel extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private Team team;
	private TeamDaoPostgres teamDao = new TeamDaoPostgres();
	private MatchDaoPostgres matchDao = new MatchDaoPostgres();
	private TeamMainWindow teamMainWindow;
	private JLabel confirmDeleteLBL;

	ConfirmDeleteTeamPanel(){
		
		setTitle("Exclusão");
        setClosable(true);
        setBounds(0, 0, 157, 255);
        getContentPane().setLayout(null);
        
        
//        Confirmação de exclusão (botão "sim")
        JButton yesBTN = new JButton("Sim");
        yesBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int teamMatches = 0;
        		try {
					teamMatches = matchDao.findTeamMatches(team).size();
				} catch (SQLException e1) {
					dispose();
					System.out.println("here");
					e1.printStackTrace();
				}
        		if(teamMatches > 0 ) {
        			JOptionPane.showMessageDialog(ConfirmDeleteTeamPanel.this.getParent(),
        					"Não é possivel deletar o time " + team.getName() +
        					" pois ele está registrado em " +
        					teamMatches +
        					" partida (s)!");
        			dispose();
        			return;
        		}

        		try {
//        			Apaga o time
        			teamDao.deleteTeam(team); 
//        			Procura todos os usuários que possuem como time favorito
//        			o time que vai ser excluído e altera para nulo, possibilitando a exclusão
        			teamDao.fixUsersFavoriteTeamAfterDelete(team);
					JOptionPane.showMessageDialog(ConfirmDeleteTeamPanel.this, "Time deletado com sucesso.");
					teamMainWindow.updateTeams();
					dispose();
				} catch (SQLException e1) {
//					Lida com exceção
					JOptionPane.showMessageDialog(ConfirmDeleteTeamPanel.this, "Não foi possível deletar o time.");
					dispose();
				}
        	}
        });
//        Design do botão
        yesBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        yesBTN.setContentAreaFilled(false);
        yesBTN.setOpaque(false);
        yesBTN.setBounds(10, 191, 56, 23);
        getContentPane().add(yesBTN);
        
        
//      Negação de exclusão (botão "não")
        JButton noBTN = new JButton("Não");
        noBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
//        		Simplesmente fecha a janela
        		dispose();
        	}
        });
//        Design do botão
        noBTN.setOpaque(false);
        noBTN.setContentAreaFilled(false);
        noBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        noBTN.setBounds(76, 191, 56, 23);
        getContentPane().add(noBTN);
        
//        PNG de time ilustrativo pra enfeitar
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(TeamMainWindow.class.getResource("/resources/time.png")));
        lblNewLabel.setBounds(45, 0, 50, 46);
        getContentPane().add(lblNewLabel);
        
        confirmDeleteLBL = new JLabel("");
        confirmDeleteLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        confirmDeleteLBL.setBounds(10, 57, 121, 123);
        getContentPane().add(confirmDeleteLBL);
        
        
//        Linha estética preta
        JPanel blackLine = new JPanel();
        blackLine.setForeground(Color.BLACK);
        blackLine.setBackground(Color.BLACK);
        blackLine.setBounds(11, 53, 119, 3);
        getContentPane().add(blackLine);
        setVisible(true);
	}
	
//	Gambiarra de código que não é gambiarra porque se funciona tá certo
	
	public void setTeam(Team team){
		this.team = team;
	}
	
	public void setTesteClass(TeamMainWindow teamMainWindow) {
		this.teamMainWindow = teamMainWindow;
	}
	
	public void turnOn() {
        confirmDeleteLBL.setText("<html>Deseja realmente apagar para \r\n<br> sempre o time "
        		+ "<strong> " + team.getName()
        		+  "</strong>?\r\n<br>(Para sempre é um tempão!)</html>");
		
	}

}
