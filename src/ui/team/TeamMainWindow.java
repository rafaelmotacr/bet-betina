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

import dao.team.TeamDaoPostgres;
import dao.user.UserDaoPostgres;
import model.Team;
import model.User;
import ui.user.MainWindow;

public class TeamMainWindow extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private TeamDaoPostgres teamDao = new TeamDaoPostgres();
    private UserDaoPostgres userDao = new UserDaoPostgres();
    private User currentUser;
    private MainWindow mainWindow;
    private JTextField searchFLD;
    private JButton createTeamBTN;
    private JButton updateTeamBTN;
    private JButton DeleteTeamBTN;
    private JButton bookmarkTeamBTN;
    private TeamCustomListRenderer CustomListRenderer = new TeamCustomListRenderer();
    private DefaultListModel<Team> listModel; 

    public TeamMainWindow() {
    	
        super();
        
//      Título da janela
        setTitle("Bet-Betina v1.23 - Menu de Times ");
        setClosable(true);
        setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        setBounds(0, 0, 640, 360);
        getContentPane().setLayout(null);

//		Criação de painel de update de time
		UpdateTeamWindow updateTeamWindow = new UpdateTeamWindow();
		updateTeamWindow.setBounds(159, 62, 320, 208);
		getContentPane().add(updateTeamWindow);
		updateTeamWindow.setVisible(false);
		
//		Criação de painel de time
		CreateTeamWindow createTeamWindow = new CreateTeamWindow();
		createTeamWindow.setBounds(159, 62, 320, 208);
		getContentPane().add(createTeamWindow);
		createTeamWindow.setVisible(false);
		
//		Painel de confirmação de exclusão
		ConfirmDeleteTeamPanel confirmDeleteTeamPanel = new ConfirmDeleteTeamPanel();
		confirmDeleteTeamPanel.setBounds(0, 32, 157, 254);
		getContentPane().add(confirmDeleteTeamPanel);
		confirmDeleteTeamPanel.setVisible(false);
		
//      Lista de times
        listModel = new DefaultListModel<>();
//        não entendi esse updateTeams
		updateTeams();
		
        JList<Team> list = new JList<>(listModel);
        
//        fru fru
        list.setOpaque(false);
        list.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); 
        list.setCellRenderer(CustomListRenderer);

//		Cria o painel com scroll para visualizar a lista de times
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        scrollPane.setBounds(163, 32, 475, 300);
        getContentPane().add(scrollPane);

//        Painel de onde será feita a busca dos times
        JPanel dataPanel = new JPanel();
        dataPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        dataPanel.setBackground(new Color(0, 128, 128));
        dataPanel.setBounds(0, 32, 157, 300);
        getContentPane().add(dataPanel);
        dataPanel.setLayout(null);

//        Botão de busca e suas partes estéticas
        JButton searchBTN = new JButton("Buscar");
        searchBTN.setBounds(10, 42, 137, 23);
        searchBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        searchBTN.setForeground(new Color(255, 255, 255));
        searchBTN.setContentAreaFilled(false);
        searchBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        dataPanel.add(searchBTN);
//        Ação realizada pelo botão
        searchBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchFLD.getText();
//                Verifica se a busca contém algum texto e faz o tratamento
                if(searchText == null || searchText.equals("") || !searchFLD.isEnabled()) {
//                	JOptionPane.showMessageDialog(TeamMainWindow.this, "Você não pode realizar uma busca vazia!");
//                	comentei porque geralmente os sites etc não fazem nada quando se busca string vazia, mas fica a gosto
//                	só toma cuidado pra não deixar chegar em setembro
                	return;
                }
//                realiza propriamente a busca
                updateTeams(searchText);
            }
        });
        
//        Criação do campo de busca
        searchFLD = new JTextField();
        searchFLD.setBounds(10, 11, 111, 23);
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
        dataPanel.add(searchFLD);
        searchFLD.setColumns(10);
        
        createTeamBTN = new JButton("Criar Time");
        createTeamBTN.setBounds(10, 164, 137, 23);
        createTeamBTN.setVisible(false);
        createTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        createTeamBTN.setForeground(new Color(255, 255, 255));
        createTeamBTN.setContentAreaFilled(false);
        createTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        dataPanel.add(createTeamBTN);
        createTeamBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		createTeamWindow.setVisible(true);
        		createTeamWindow.setTesteClass(TeamMainWindow.this);
        	}
        });
        
        updateTeamBTN = new JButton("Atualizar Time");
        updateTeamBTN.setBounds(10, 198, 137, 23);
        updateTeamBTN.setVisible(false);
        updateTeamBTN.setForeground(Color.WHITE);
        updateTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        updateTeamBTN.setContentAreaFilled(false);
        updateTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        dataPanel.add(updateTeamBTN);
        updateTeamBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(list.getSelectedValue() == null) {
        			JOptionPane.showMessageDialog(TeamMainWindow.this, "Selecione um time primeiro.");
        			return;
        		}
        		updateTeamWindow.setVisible(true);
        		updateTeamWindow.setTesteClass(TeamMainWindow.this);
        		updateTeamWindow.setTeam(list.getSelectedValue());
        		updateTeamWindow.turnOn();
        	}
        });
        
        DeleteTeamBTN = new JButton("Deletar Time");
        DeleteTeamBTN.setBounds(43, 266, 104, 23);
        DeleteTeamBTN.setVisible(false);
        DeleteTeamBTN.setForeground(Color.WHITE);
        DeleteTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        DeleteTeamBTN.setContentAreaFilled(false);
        DeleteTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        dataPanel.add(DeleteTeamBTN);
        DeleteTeamBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(list.getSelectedValue() == null) {
        			JOptionPane.showMessageDialog(TeamMainWindow.this, "Selecione um time primeiro.");
        			return;
        		}
        		confirmDeleteTeamPanel.setVisible(true);
        		confirmDeleteTeamPanel.setTeam(list.getSelectedValue());
        		confirmDeleteTeamPanel.setTesteClass(TeamMainWindow.this);
        		confirmDeleteTeamPanel.turnOn();
        	}
        });
        
        JButton backBTN = new JButton("");
        backBTN.setBounds(10, 266, 30, 23);
        backBTN.setIcon(new ImageIcon(TeamMainWindow.class.getResource("/resources/backBTN.png")));
        backBTN.setContentAreaFilled(false);
        backBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        dataPanel.add(backBTN);
        backBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        
        bookmarkTeamBTN = new JButton("Favoritar Time");
        bookmarkTeamBTN.setBounds(10, 232, 137, 23);
        bookmarkTeamBTN.setVisible(false);
        bookmarkTeamBTN.setForeground(Color.WHITE);
        bookmarkTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        bookmarkTeamBTN.setContentAreaFilled(false);
        bookmarkTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        dataPanel.add(bookmarkTeamBTN);
        bookmarkTeamBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					userDao.updateUserFavoriteTeam(currentUser, list.getSelectedValue());
					setUser(userDao.findUserByEmail(currentUser.getEmail()));
					mainWindow.updateUser(currentUser);
					updateTeams();
					JOptionPane.showMessageDialog(TeamMainWindow.this, "Time favoritado com sucesso!");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(TeamMainWindow.this, "Erro ao favoritar time.");
				}
        	}
        });
        
        JButton refreshBTN = new JButton("");
        refreshBTN.setBounds(124, 11, 23, 23);
        refreshBTN.setToolTipText("Clique aqui para limpar a busca.");
        refreshBTN.setBorderPainted(false);
        refreshBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        refreshBTN.setIcon(new ImageIcon(TeamMainWindow.class.getResource("/resources/reload.png")));
        refreshBTN.setContentAreaFilled(false);
        refreshBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 8));
        dataPanel.add(refreshBTN);
        refreshBTN.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		searchFLD.setText(null);
        		updateTeams();
        	}
        });
        
        JLabel teamImgLBL = new JLabel("");
        teamImgLBL.setBounds(37, 76, 84, 77);
        teamImgLBL.setIcon(new ImageIcon(TeamMainWindow.class.getResource("/resources/bolacha.png")));
        dataPanel.add(teamImgLBL);
        
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
    
    public void updateTeams(){
    	ArrayList<Team> teams = null;
		try {
			teams = teamDao.getAllTeams();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(TeamMainWindow.this, "Nenhum time foi encontrado.");
		}
    	listModel.clear();
        for (Team team : teams) {
        	listModel.addElement(team); 
        }
    }
    
    public void updateTeams(String filter) {
    	ArrayList<Team> teams = null;
		try {
			teams = teamDao.getAllTeams(filter);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(TeamMainWindow.this, "Nenhum time foi encontrado.");
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
            TeamMainWindow testeClass = new TeamMainWindow();
            desktopPane.add(testeClass);
            
            frame.setVisible(true);
        });
    }
}
