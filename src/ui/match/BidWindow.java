package ui.match;

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

import dao.team.TeamDaoPostgres;
import model.Bid;
import model.Match;

public class BidWindow extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TeamDaoPostgres teamDao = new TeamDaoPostgres(); 
	private Match match = new Match (20, 1, 4,1, 1.28,1.45, 7.45);
	private String homeTeamName;
	private String awayTeamName;
	private int betId;
	private MatchMainWindow matchMainWindow = new MatchMainWindow();
	
    public BidWindow() {
    	try {
			homeTeamName = teamDao.findTeamById(match.getHomeTeamId()).getName();
			awayTeamName = teamDao.findTeamById(match.getAwayTeamId()).getName();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
        
        JLabel OperationsTextPNL = new JLabel(match.toString());
        OperationsTextPNL.setForeground(Color.WHITE);
        OperationsTextPNL.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        OperationsTextPNL.setBounds(10, 0, 346, 28);
        panel.add(OperationsTextPNL);
        
        ButtonGroup group = new ButtonGroup();
        
        JLabel txtLBL = new JLabel("Faça Seu Palpite:");
        txtLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        txtLBL.setBounds(14, 31, 333, 23);
        getContentPane().add(txtLBL);
        
        JLabel bidValueLBL = new JLabel("Valor Do Lance: ");
        bidValueLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        bidValueLBL.setBounds(14, 144, 284, 20);
        getContentPane().add(bidValueLBL);
        JButton cancelBidBTN = new JButton("Cancelar Lance");
        cancelBidBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
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
        		//public Bid(int iD, double paidValue, int guess, int betID, int matchID
        		matchMainWindow.addBid(new Bid(
        				Double.valueOf(bidValueFLD.getSelectedText()),
        				Integer.parseInt(group.getSelection().getActionCommand()),
        				betId,
        				match.getId()));
        	}
        });
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 255, 255));
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBounds(14, 56, 326, 89);
        getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JRadioButton rdbtnVitoriaDoVitoria = new JRadioButton("Vitória do " + homeTeamName + " - ODD: "+ match.getHomeTeamOdd() + "%");
        rdbtnVitoriaDoVitoria.setOpaque(false);
        rdbtnVitoriaDoVitoria.setBounds(6, 7, 314, 23);
        panel_1.add(rdbtnVitoriaDoVitoria);
        rdbtnVitoriaDoVitoria.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        group.add(rdbtnVitoriaDoVitoria);
        
        JRadioButton rdbtnVitoriaDoFlamengo = new JRadioButton("Vitória do " + awayTeamName + " - ODD: " + match.getAwayTeamOdd() + "%");
        rdbtnVitoriaDoFlamengo.setOpaque(false);
        rdbtnVitoriaDoFlamengo.setBounds(6, 33, 314, 23);
        panel_1.add(rdbtnVitoriaDoFlamengo);
        rdbtnVitoriaDoFlamengo.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        group.add(rdbtnVitoriaDoFlamengo);
        
        JRadioButton rdbtnEmpate = new JRadioButton("Empate - ODD: " + match.getDrawOdd() + "%");
        rdbtnEmpate.setOpaque(false);
        rdbtnEmpate.setBounds(6, 59, 314, 23);
        panel_1.add(rdbtnEmpate);
        rdbtnEmpate.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        group.add(rdbtnEmpate);
        
        setSize(357, 244); 
        setVisible(true); 
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
