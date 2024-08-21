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
    private ui.match.CustomListRenderer CustomListRenderer = new ui.match.CustomListRenderer();
    private DefaultListModel<Match> listModel; 
    public MatchMainWindow() {
    	
        super();
        
        setTitle("Bet-Betina v1.23 - Menu de Partidas");
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
        
        createTeamBTN = new JButton("Iniciar Aposta");
        createTeamBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        createTeamBTN.setForeground(new Color(255, 255, 255));
        createTeamBTN.setContentAreaFilled(false);
        createTeamBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        createTeamBTN.setBounds(10, 164, 137, 23);
        dataPanel.add(createTeamBTN);
    
        JButton backBTN = new JButton("");
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
        
        JButton btnAdicionarNovoLance = new JButton("Fazer Lance");
        btnAdicionarNovoLance.setForeground(Color.WHITE);
        btnAdicionarNovoLance.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnAdicionarNovoLance.setContentAreaFilled(false);
        btnAdicionarNovoLance.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnAdicionarNovoLance.setBounds(10, 198, 137, 23);
        dataPanel.add(btnAdicionarNovoLance);
        
        JButton btnMinhasApostas = new JButton("Editar Partida");
        btnMinhasApostas.setForeground(Color.WHITE);
        btnMinhasApostas.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnMinhasApostas.setContentAreaFilled(false);
        btnMinhasApostas.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnMinhasApostas.setBounds(44, 271, 105, 23);
        dataPanel.add(btnMinhasApostas);
        
        JButton btnCriarPartida = new JButton("Criar Partida [ADM]");
        btnCriarPartida.setForeground(Color.WHITE);
        btnCriarPartida.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnCriarPartida.setContentAreaFilled(false);
        btnCriarPartida.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnCriarPartida.setBounds(10, 232, 137, 23);
        dataPanel.add(btnCriarPartida);
        
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
        
        JLabel lblNewLabel = new JLabel("Lances Feitos: X");
        lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(10, 34, 133, 14);
        panel_1.add(lblNewLabel);
        
        JLabel lblSaldoRestante = new JLabel("Saldo Atual: X");
        lblSaldoRestante.setForeground(Color.WHITE);
        lblSaldoRestante.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblSaldoRestante.setBounds(6, 248, 133, 14);
        panel_1.add(lblSaldoRestante);
        
        JLabel lblCustoTotal = new JLabel("Custo Total:");
        lblCustoTotal.setForeground(Color.WHITE);
        lblCustoTotal.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblCustoTotal.setBounds(7, 267, 133, 14);
        panel_1.add(lblCustoTotal);
        
        JLabel lblEstadoEmAberto = new JLabel("Estado: Não iniciada.");
        lblEstadoEmAberto.setForeground(Color.WHITE);
        lblEstadoEmAberto.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblEstadoEmAberto.setBounds(10, 53, 133, 14);
        panel_1.add(lblEstadoEmAberto);
        
        JButton btnConcluirAposta = new JButton("Concluir Aposta");
        btnConcluirAposta.setBounds(324, 303, 150, 23);
        getContentPane().add(btnConcluirAposta);
        btnConcluirAposta.setForeground(new Color(0, 0, 0));
        btnConcluirAposta.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnConcluirAposta.setContentAreaFilled(false);
        btnConcluirAposta.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        
        JButton btnCancelaraposta = new JButton("Cancelar Aposta");
        btnCancelaraposta.setBounds(163, 303, 150, 23);
        getContentPane().add(btnCancelaraposta);
        btnCancelaraposta.setForeground(new Color(0, 0, 0));
        btnCancelaraposta.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnCancelaraposta.setContentAreaFilled(false);
        btnCancelaraposta.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

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
