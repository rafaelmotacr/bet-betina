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

import dao.TeamDaoPostgres;
import model.Team;

public class ConfirmDeleteTeamPanel extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private Team team;
	private TeamDaoPostgres dao = new TeamDaoPostgres();

	ConfirmDeleteTeamPanel(){
		
		setTitle("Exclusão");
        setClosable(true);
        setBounds(0, 0, 157, 255);
        getContentPane().setLayout(null);
        
        JButton yesBTN = new JButton("Sim");
        yesBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					dao.deleteTeam(team);
					JOptionPane.showMessageDialog(ConfirmDeleteTeamPanel.this, "Time deletado com sucesso.");
					dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(ConfirmDeleteTeamPanel.this, "Não foi possível deletar o time.");
					dispose();
				}
        	}
        });
        yesBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        yesBTN.setContentAreaFilled(false);
        yesBTN.setOpaque(false);
        yesBTN.setBounds(10, 191, 56, 23);
        getContentPane().add(yesBTN);
        
        JButton noBTN = new JButton("Não");
        noBTN.setOpaque(false);
        noBTN.setContentAreaFilled(false);
        noBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        noBTN.setBounds(76, 191, 56, 23);
        getContentPane().add(noBTN);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(TesteClass.class.getResource("/resources/time.png")));
        lblNewLabel.setBounds(45, 0, 50, 46);
        getContentPane().add(lblNewLabel);
        
        JLabel confirmDeleteLBL = new JLabel("");
        confirmDeleteLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        confirmDeleteLBL.setBounds(10, 67, 121, 113);
        getContentPane().add(confirmDeleteLBL);
        confirmDeleteLBL.setText("<html>Deseja realmente apagar para \r\n<br> sempre o time " +
			 team.getName()+ "<strong> "
			 +  "</strong>?\r\n<br>(Para sempre é um tempão!)</html>");
        
        JPanel blackLine = new JPanel();
        blackLine.setForeground(Color.BLACK);
        blackLine.setBackground(Color.BLACK);
        blackLine.setBounds(11, 53, 119, 3);
        getContentPane().add(blackLine);
        setVisible(true);
	}
	
	private void setTeam(Team team){
		this.team = team;
	}

}
