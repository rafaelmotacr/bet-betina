package ui.match;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import dao.match.MatchDaoPostgres;
import dao.user.UserDaoPostgres;
import model.Bid;
import model.Match;
import model.User;

public class MatchMainWindow extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private MatchDaoPostgres matchDao = new MatchDaoPostgres();
    @SuppressWarnings("unused")
	private UserDaoPostgres userDao = new UserDaoPostgres();
    private User currentUser = new User(50, 0, 1.578, "ademiro", "null", "senha");
    private JTextField searchFLD;
    private JButton createBetBTN;
    private CustomListRenderer CustomListRenderer;
    private DefaultListModel<Match> listModel; 
    
    private JLabel totalCostLBL;
    private JLabel totalBidsLBL;
    private JLabel userBalanceLBL;
    private JLabel betStateLBL;
    private JLabel balanceAfterBetLBL;
    
    private boolean isUserAdmin = false;
    private int betStatus = 0;
    
    private ArrayList <Bid> bidArray;
    
    public MatchMainWindow() {
    	
        super();
        bidArray = new ArrayList<Bid>();
        
        setTitle("Bet-Betina v1.23 - Menu de Partidas");
        setClosable(true);
        setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        setBounds(0, 0, 640, 360);
        getContentPane().setLayout(null);

        BidWindow bidWindow = new BidWindow();
        bidWindow.setLocation(140, 44);
        setLocation(259, 78);
        getContentPane().add(bidWindow);
        
        listModel = new DefaultListModel<>();
        JList<Match> list = new JList<>(listModel);
        
        CustomListRenderer = new ui.match.CustomListRenderer();
        list.setOpaque(false);
        list.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        list.setCellRenderer(CustomListRenderer);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        scrollPane.setBounds(164, 32, 310, 266);
        getContentPane().add(scrollPane);

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(null);
        dataPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        dataPanel.setBackground(new Color(0, 128, 128));
        dataPanel.setBounds(0, 32, 157, 303);
        getContentPane().add(dataPanel);

        JButton searchBTN = new JButton("Buscar Partida");
        searchBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        searchBTN.setForeground(new Color(255, 255, 255));
        searchBTN.setContentAreaFilled(false);
        searchBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        searchBTN.setBounds(10, 42, 137, 23);
        dataPanel.add(searchBTN);
        searchBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchFLD.getText();
                if(searchText == null || searchText.equals("") || !searchFLD.isEnabled()) {
                	return;
                }
                updateMatchs(searchText);
            }
        });
        
        searchFLD = new JTextField();
        searchFLD.setBorder(new LineBorder(new Color(0, 0, 0)));
        searchFLD.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		searchFLD.setText(null);
        		searchFLD.setEnabled(true);
        		searchFLD.requestFocus();
        	}
        });
        searchFLD.setEnabled(false);
        searchFLD.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        searchFLD.setText("Nome do time...");
        searchFLD.setBounds(10, 11, 111, 23);
        dataPanel.add(searchFLD);
        searchFLD.setColumns(10);
    
        JButton backBTN = new JButton("");
        backBTN.setIcon(new ImageIcon(MatchMainWindow.class.getResource("/resources/backBTN.png")));
        backBTN.setContentAreaFilled(false);
        backBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        backBTN.setBounds(10, 271, 30, 23);
        dataPanel.add(backBTN);
        backBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });

        JButton refreshBTN = new JButton("");
        refreshBTN.setIcon(new ImageIcon(MatchMainWindow.class.getResource("/resources/reload.png")));
        refreshBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		searchFLD.setText(null);
        		updateMatchs();
        	}
        });
        
        refreshBTN.setToolTipText("Clique aqui para limpar a busca.");
        refreshBTN.setBorderPainted(false);
        refreshBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        refreshBTN.setContentAreaFilled(false);
        refreshBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 8));
        refreshBTN.setBounds(124, 11, 23, 23);
        dataPanel.add(refreshBTN);
        
        JButton makeBidBTN = new JButton("Fazer Lance");
        makeBidBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(list.getSelectedValue() == null) {
        			JOptionPane.showMessageDialog(MatchMainWindow.this, "Selecione uma partida primeiro.");
        			return;
        		}
        		bidWindow.setMatchMainWindow(MatchMainWindow.this);
        		bidWindow.setMatch(list.getSelectedValue());
        		bidWindow.setVisible(true);
        	}
        });
        makeBidBTN.setEnabled(false);
        makeBidBTN.setForeground(Color.WHITE);
        makeBidBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        makeBidBTN.setContentAreaFilled(false);
        makeBidBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        makeBidBTN.setBounds(10, 210, 137, 23);
        dataPanel.add(makeBidBTN);
        
        JButton btnMinhasApostas = new JButton("Minhas Apostas");
        btnMinhasApostas.setForeground(Color.WHITE);
        btnMinhasApostas.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnMinhasApostas.setContentAreaFilled(false);
        btnMinhasApostas.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnMinhasApostas.setBounds(44, 271, 103, 23);
        dataPanel.add(btnMinhasApostas);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.setBounds(0, 0, 638, 28);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel OperationsTextPNL = new JLabel("OPERAÇÕES");
        OperationsTextPNL.setBounds(10, 0, 130, 28);
        panel.add(OperationsTextPNL);
        OperationsTextPNL.setForeground(new Color(255, 255, 255));
        OperationsTextPNL.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        
        JLabel lblTimesCorrespondentes = new JLabel("PARTIDAS DISPONÍVEIS");
        lblTimesCorrespondentes.setBounds(168, 0, 307, 28);
        panel.add(lblTimesCorrespondentes);
        lblTimesCorrespondentes.setForeground(new Color(255, 255, 255));
        lblTimesCorrespondentes.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel_1.setBackground(new Color(0, 128, 128));
        panel_1.setBounds(481, 32, 157, 303);
        getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblStatus = new JLabel("Status Da Aposta");
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        lblStatus.setBounds(13, 5, 130, 23);
        panel_1.add(lblStatus);
        
        JPanel blackLine_1 = new JPanel();
        blackLine_1.setForeground(Color.BLACK);
        blackLine_1.setBackground(Color.BLACK);
        blackLine_1.setBounds(2, 29, 155, 3);
        panel_1.add(blackLine_1);
        
        totalBidsLBL = new JLabel();
        totalBidsLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        totalBidsLBL.setForeground(new Color(255, 255, 255));
        totalBidsLBL.setBounds(10, 34, 133, 14);
        panel_1.add(totalBidsLBL);
        
        userBalanceLBL = new JLabel();
        userBalanceLBL.setForeground(Color.WHITE);
        userBalanceLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        userBalanceLBL.setBounds(13, 235, 137, 14);
        panel_1.add(userBalanceLBL);
        
        totalCostLBL = new JLabel();
        totalCostLBL.setForeground(Color.WHITE);
        totalCostLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        totalCostLBL.setBounds(13, 249, 133, 14);
        panel_1.add(totalCostLBL);
        
        betStateLBL = new JLabel();
        betStateLBL.setForeground(Color.WHITE);
        betStateLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        betStateLBL.setBounds(10, 53, 133, 14);
        panel_1.add(betStateLBL);
        
        balanceAfterBetLBL = new JLabel();
        balanceAfterBetLBL.setForeground(Color.WHITE);
        balanceAfterBetLBL.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        balanceAfterBetLBL.setBounds(13, 266, 137, 14);
        panel_1.add(balanceAfterBetLBL);
        
        JButton confirmBetBTN = new JButton("Confirmar Aposta");
        confirmBetBTN.setEnabled(false);
        confirmBetBTN.setBounds(324, 303, 150, 23);
        getContentPane().add(confirmBetBTN);
        confirmBetBTN.setForeground(new Color(0, 0, 0));
        confirmBetBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        confirmBetBTN.setContentAreaFilled(false);
        confirmBetBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        
        JButton cancelBetBTN = new JButton("Cancelar Aposta");
        cancelBetBTN.setEnabled(false);
        cancelBetBTN.setBounds(163, 303, 150, 23);
        getContentPane().add(cancelBetBTN);
        cancelBetBTN.setForeground(new Color(255, 0, 0));
        cancelBetBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        cancelBetBTN.setContentAreaFilled(false);
        cancelBetBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

        createBetBTN = new JButton("Iniciar Aposta");
        createBetBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        createBetBTN.setForeground(new Color(255, 255, 255));
        createBetBTN.setContentAreaFilled(false);
        createBetBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        createBetBTN.setBounds(10, 176, 137, 23);
        dataPanel.add(createBetBTN);
        
        JButton btnVerLances = new JButton("Conferir Lances");
        btnVerLances.setForeground(Color.WHITE);
        btnVerLances.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnVerLances.setEnabled(false);
        btnVerLances.setContentAreaFilled(false);
        btnVerLances.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnVerLances.setBounds(10, 244, 137, 23);
        dataPanel.add(btnVerLances);
        createBetBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		betStatus = 1;
        		updateStatus();
        		createBetBTN.setEnabled(false);
        		cancelBetBTN.setEnabled(true);
        		confirmBetBTN.setEnabled(true);
        		makeBidBTN.setEnabled(true);
        	}
        });
        
        setVisible(true);
        updateMatchs();
        updateStatus();
        CustomListRenderer.setUser(currentUser);
    }
    
    
    public void updateStatus() {
    	double totalCost = bidArray.stream().mapToDouble(Bid::getPaidValue).sum();
    	double userBalance = currentUser.getBalance();
    	double balanceAfterBet = userBalance - totalCost;
    	totalBidsLBL.setText("Lances feitos: " + bidArray.size() + ".");
    	totalCostLBL.setText("Custo Total: " + totalCost + ".");
    	userBalanceLBL.setText("Saldo Atual: R$ " + userBalance + ".");
    	balanceAfterBetLBL.setText("Saldo Restante: " + balanceAfterBet + ".");
    	if(betStatus == 0) {
    		betStateLBL.setText("Estado: Não iniciada.");
    	}else {
    		betStateLBL.setText("Estado: Em crição.");
    	}
    	
    }
    
    public void updateMatchs() {
    	ArrayList<Match> matchs = null;
    	if(isUserAdmin) {
    		try {
    			matchs = matchDao.getAllMatchs();
    		} catch (SQLException e) {
    			JOptionPane.showMessageDialog(MatchMainWindow.this, "Nenhuma partida encontrada.");
    			e.printStackTrace();
    		}
    	}else {
       		try {
    			matchs = matchDao.getActiveMatchs();
    		} catch (SQLException e) {
    			JOptionPane.showMessageDialog(MatchMainWindow.this, "Nenhuma partida encontrada.");
    			e.printStackTrace();
    		}
    	}

    	listModel.clear();
        for (Match match : matchs) {
        	listModel.addElement(match); 
        }
    }
    
    public void updateMatchs(String filter) {
    	ArrayList<Match> matchs = null;
    	if(isUserAdmin) {
    		try {
    			matchs = matchDao.getAllMatchs(filter);
    		} catch (SQLException e) {
    			JOptionPane.showMessageDialog(MatchMainWindow.this, "Nenhuma partida encontrada.");
    			e.printStackTrace();
    		}
    	}else {
       		try {
    			matchs = matchDao.getActiveMatchs(filter);
    		} catch (SQLException e) {
    			JOptionPane.showMessageDialog(MatchMainWindow.this, "Nenhuma partida encontrada.");
    			e.printStackTrace();
    		}
    	}

    	listModel.clear();
        for (Match match : matchs) {
        	listModel.addElement(match); 
        }
    }
    
    
    public void addBid(Bid bid) {
    	bidArray.add(bid);
    	updateStatus();
    }
    
   /* 
    public void setUser(User user) {
    	currentUser = user;
    	CustomListRenderer.setUser(user);
    }
    */

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
            MatchMainWindow testeClass = new MatchMainWindow   ();
            desktopPane.add(testeClass);
            
            frame.setVisible(true);
        });
    }
}
