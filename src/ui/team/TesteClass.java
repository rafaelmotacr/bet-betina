package ui.team;

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

import dao.TeamDaoPostgres;
import dao.UserDaoPostgres;
import model.Team;
import model.User;
import ui.user.MainWindow;

public class TesteClass extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private TeamDaoPostgres teamdao = new TeamDaoPostgres();
    private UserDaoPostgres userDao = new UserDaoPostgres();
    private User currentUser;
    private MainWindow mainWindow;
    private JTextField searchFLD;
    CustomListRenderer CustomListRenderer = new CustomListRenderer();
    private DefaultListModel<Team> listModel; 

    public TesteClass() {
    	
        super();
        
        setTitle("Bet-Betina v1.23 - Menu de Times ");
        setClosable(true);

        setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        setBounds(0, 0, 640, 360);
        getContentPane().setLayout(null);

        listModel = new DefaultListModel<>();
		updateTeams();

        JList<Team> list = new JList<>(listModel);
        
        list.setOpaque(false);
        list.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); 
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
        searchBTN.setBorder(new LineBorder(new Color(0, 0, 0)));
        searchBTN.setForeground(new Color(255, 255, 255));
        searchBTN.setContentAreaFilled(false);
        searchBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        searchBTN.setBounds(10, 42, 130, 23);
        dataPanel.add(searchBTN);

        searchBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchFLD.getText();
                if(searchText == null || searchText.equals("") || !searchFLD.isEnabled()) {
                	JOptionPane.showMessageDialog(TesteClass.this, "Você não pode realizar uma busca vazia!");
                	return;
                }
                updateTeams(searchText);
            }
        });

        
        searchFLD = new JTextField();
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
        searchFLD.setBounds(10, 11, 97, 23);
        dataPanel.add(searchFLD);
        searchFLD.setColumns(10);
        
        JButton createTeamBTN = new JButton("Criar ");
        createTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        createTeamBTN.setForeground(new Color(255, 255, 255));
        createTeamBTN.setContentAreaFilled(false);
        createTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        createTeamBTN.setBounds(10, 164, 130, 23);
        dataPanel.add(createTeamBTN);
        
        JButton updateTeamBTN = new JButton("Atualizar");
        updateTeamBTN.setForeground(Color.WHITE);
        updateTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        updateTeamBTN.setContentAreaFilled(false);
        updateTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        updateTeamBTN.setBounds(10, 198, 130, 23);
        dataPanel.add(updateTeamBTN);
        
        JButton DeleteTeamBTN = new JButton("Deletar");
        DeleteTeamBTN.setForeground(Color.WHITE);
        DeleteTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        DeleteTeamBTN.setContentAreaFilled(false);
        DeleteTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        DeleteTeamBTN.setBounds(43, 266, 97, 23);
        dataPanel.add(DeleteTeamBTN);
        DeleteTeamBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int escolha = JOptionPane.showConfirmDialog(TesteClass.this,
        				"Deseja realmente excluir este time?", "Atenção", JOptionPane.YES_NO_CANCEL_OPTION);
        		System.out.println("Sua escolha: " + escolha);
        	}
        });
        
        JButton backBTN = new JButton("");
        backBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        backBTN.setIcon(new ImageIcon(TesteClass.class.getResource("/resources/backBTN.png")));
        backBTN.setContentAreaFilled(false);
        backBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        backBTN.setBounds(10, 266, 30, 23);
        dataPanel.add(backBTN);
        
        JButton bookmarkTeamBTN = new JButton("Favoritar");
        bookmarkTeamBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					userDao.updateUserFavoriteTeam(currentUser, list.getSelectedValue());
					setUser(userDao.findUserByEmail(currentUser.getEmail()));
					mainWindow.updateUser(currentUser);
					updateTeams();
					JOptionPane.showMessageDialog(TesteClass.this, "Time favoritado com sucesso!");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(TesteClass.this, "Erro ao favoritar time.");
				}
        	}
        });
        bookmarkTeamBTN.setForeground(Color.WHITE);
        bookmarkTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        bookmarkTeamBTN.setContentAreaFilled(false);
        bookmarkTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        bookmarkTeamBTN.setBounds(10, 232, 130, 23);
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
        refreshBTN.setIcon(new ImageIcon(TesteClass.class.getResource("/resources/reload.png")));
        refreshBTN.setContentAreaFilled(false);
        refreshBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 8));
        refreshBTN.setBounds(117, 11, 23, 23);
        dataPanel.add(refreshBTN);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(TesteClass.class.getResource("/resources/bolacha.png")));
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
    
    private void updateTeams(){
    	ArrayList<Team> teams = null;
		try {
			teams = teamdao.getAllTeams();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(TesteClass.this, "Nenhum time foi encontrado.");
		}
    	listModel.clear();
        for (Team team : teams) {
        	listModel.addElement(team); 
        }
    }
    
    private void updateTeams(String filter) {
    	ArrayList<Team> teams = null;
		try {
			teams = teamdao.getAllTeams(filter);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(TesteClass.this, "Nenhum time foi encontrado.");
		}
    	listModel.clear();
        for (Team team : teams) {
        	listModel.addElement(team); 
        }
    }
    
    public void setUser(User user) {
    	currentUser = user;
    	CustomListRenderer.setUser(user);
    }
    
    public void setMainWindow(MainWindow mainWindow) {
    	this.mainWindow = mainWindow;
    	
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
            TesteClass testeClass = new TesteClass();
            desktopPane.add(testeClass);
            
            frame.setVisible(true);
        });
    }
}
