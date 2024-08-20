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
import model.Match;
import model.User;
import ui.user.MainWindow;

public class MatchMainWindow extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private MatchDaoPostgres matchDao = new MatchDaoPostgres();
    private UserDaoPostgres userDao = new UserDaoPostgres();
    private User currentUser;
    private MainWindow mainWindow;
    private JTextField searchFLD;
    private JButton createTeamBTN;
    private JButton updateTeamBTN;
    private JButton DeleteTeamBTN;
    private JButton bookmarkTeamBTN;
    private ui.match.CustomListRenderer CustomListRenderer = new ui.match.CustomListRenderer();
    private DefaultListModel<Match> listModel; 
    public MatchMainWindow() {
    	
        super();
        
        setTitle("Bet-Betina v1.23 - Menu de Times ");
        setClosable(true);
        setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        setBounds(0, 0, 640, 360);
        getContentPane().setLayout(null);

        listModel = new DefaultListModel<>();
		updateTeams();

        JList<Match> list = new JList<>(listModel);
        
        list.setOpaque(false);
        list.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        list.setCellRenderer(CustomListRenderer);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        scrollPane.setBounds(163, 32, 475, 300);
        getContentPane().add(scrollPane);

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(null);
        dataPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        dataPanel.setBackground(new Color(0, 128, 128));
        dataPanel.setBounds(0, 32, 157, 300);
        getContentPane().add(dataPanel);

        JButton searchBTN = new JButton("Buscar");
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
                	JOptionPane.showMessageDialog(MatchMainWindow.this, "Você não pode realizar uma busca vazia!");
                	return;
                }
                updateTeams();
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
        
        createTeamBTN = new JButton("Criar Time");
        createTeamBTN.setVisible(false);
        createTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        createTeamBTN.setForeground(new Color(255, 255, 255));
        createTeamBTN.setContentAreaFilled(false);
        createTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        createTeamBTN.setBounds(10, 164, 137, 23);
        dataPanel.add(createTeamBTN);

        
        updateTeamBTN = new JButton("Atualizar Time");
        updateTeamBTN.setVisible(false);
        updateTeamBTN.setForeground(Color.WHITE);
        updateTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        updateTeamBTN.setContentAreaFilled(false);
        updateTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        updateTeamBTN.setBounds(10, 198, 137, 23);
        dataPanel.add(updateTeamBTN);
        
        DeleteTeamBTN = new JButton("Deletar Time");
        DeleteTeamBTN.setVisible(false);
        DeleteTeamBTN.setForeground(Color.WHITE);
        DeleteTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        DeleteTeamBTN.setContentAreaFilled(false);
        DeleteTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        DeleteTeamBTN.setBounds(43, 266, 104, 23);
        dataPanel.add(DeleteTeamBTN);
    
        JButton backBTN = new JButton("");
        backBTN.setContentAreaFilled(false);
        backBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        backBTN.setBounds(10, 266, 30, 23);
        dataPanel.add(backBTN);
        backBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        
        bookmarkTeamBTN = new JButton("Favoritar Time");
        bookmarkTeamBTN.setVisible(false);
        bookmarkTeamBTN.setForeground(Color.WHITE);
        bookmarkTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        bookmarkTeamBTN.setContentAreaFilled(false);
        bookmarkTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        bookmarkTeamBTN.setBounds(10, 232, 137, 23);
        dataPanel.add(bookmarkTeamBTN);

        JButton refreshBTN = new JButton("");
        refreshBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		searchFLD.setText(null);
        		updateTeams();
        	}
        });
        
        refreshBTN.setToolTipText("Clique aqui para limpar a busca.");
        refreshBTN.setBorderPainted(false);
        refreshBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        refreshBTN.setContentAreaFilled(false);
        refreshBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 8));
        refreshBTN.setBounds(124, 11, 23, 23);
        dataPanel.add(refreshBTN);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(10, 76, 130, 77);
        dataPanel.add(lblNewLabel_1);
        
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
        
        JLabel lblTimesCorrespondentes = new JLabel("TIMES CORRESPONDENTES");
        lblTimesCorrespondentes.setBounds(159, 0, 307, 28);
        panel.add(lblTimesCorrespondentes);
        lblTimesCorrespondentes.setForeground(new Color(255, 255, 255));
        lblTimesCorrespondentes.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

        setVisible(true);
    }
    
    public void updateTeams( ) {
    	ArrayList<Match> matchs = null;
		try {
			matchs = matchDao.getAllMatchs();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(MatchMainWindow.this, "Nenhum time foi encontrado.");
			e.printStackTrace();
		}
    	listModel.clear();
        for (Match match : matchs) {
        	listModel.addElement(match); 
        }
    }
   /* 
    public void setUser(User user) {
    	currentUser = user;
    	CustomListRenderer.setUser(user);
    }
    */
    public void setMainWindow(MainWindow mainWindow) {
    	this.mainWindow = mainWindow;
    	
    }
    
    public void turnOn() {
    	if(currentUser == null) {
    		createTeamBTN.setVisible(false);
    		updateTeamBTN.setVisible(false);
    	    DeleteTeamBTN.setVisible(false);
    	    bookmarkTeamBTN.setVisible(false);
    		return;
    	}
    	if(currentUser.getAccessLevel() == 1) {
    		createTeamBTN.setVisible(true);
    		updateTeamBTN.setVisible(true);
    	    DeleteTeamBTN.setVisible(true);
    	}
    	bookmarkTeamBTN.setVisible(true);
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
            
            // Adicionar uma instância de TesteClass ao JDesktopPane
            MatchMainWindow testeClass = new MatchMainWindow   ();
            desktopPane.add(testeClass);
            
            frame.setVisible(true);
        });
    }
}
