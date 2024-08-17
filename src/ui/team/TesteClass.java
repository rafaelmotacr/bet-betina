package ui.team;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import dao.TeamDaoPostgres;
import model.Team;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TesteClass extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private TeamDaoPostgres dao = new TeamDaoPostgres();
    private JTextField txtDigiteONome;

    public TesteClass() {
        super("Teste Class", true, true, false, false);
        
        setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        setBounds(0, 0, 640, 360);
        
        // Criar um ArrayList de opções
        ArrayList<Team> options = new ArrayList<>();
        try {
            options = dao.getAllTeams();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Criar um DefaultListModel e preenchê-lo com o ArrayList
        DefaultListModel<Team> listModel = new DefaultListModel<>();
        for (Team team : options) {
            listModel.addElement(team);
        }
        
        // Criar um JList e adicionar o DefaultListModel
        JList<Team> list = new JList<>(listModel);
        list.setOpaque(false);
        list.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); // Permitir seleção múltipla
        
        // Adicionar um ListSelectionListener para mostrar a seleção
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                java.util.List<Team> selectedTeams = list.getSelectedValuesList();
            }
        });
        getContentPane().setLayout(null);
       
        // Adicionar o JList dentro de um JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        scrollPane.setBounds(163, 38, 465, 283);
        getContentPane().add(scrollPane);
        
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(null);
        dataPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        dataPanel.setBackground(new Color(0, 128, 128));
        dataPanel.setBounds(7, 38, 150, 283);
        getContentPane().add(dataPanel);
        
        JButton btnNewButton = new JButton("Buscar");
        btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0)));
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnNewButton.setBounds(10, 42, 130, 23);
        dataPanel.add(btnNewButton);
        
        txtDigiteONome = new JTextField();
        txtDigiteONome.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		txtDigiteONome.setText(null);
        		txtDigiteONome.setEnabled(true);
        		txtDigiteONome.requestFocus();
        	}
        });
        txtDigiteONome.setEnabled(false);
        txtDigiteONome.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        txtDigiteONome.setText("Digite o nome do time...");
        txtDigiteONome.setBounds(10, 11, 130, 20);
        dataPanel.add(txtDigiteONome);
        txtDigiteONome.setColumns(10);
        
        JButton btnNewButton_1 = new JButton("Criar ");
        btnNewButton_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnNewButton_1.setForeground(new Color(255, 255, 255));
        btnNewButton_1.setContentAreaFilled(false);
        btnNewButton_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnNewButton_1.setBounds(10, 175, 130, 23);
        dataPanel.add(btnNewButton_1);
        
        JButton btnNewButton_1_1 = new JButton("Atualizar");
        btnNewButton_1_1.setForeground(Color.WHITE);
        btnNewButton_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnNewButton_1_1.setContentAreaFilled(false);
        btnNewButton_1_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnNewButton_1_1.setBounds(10, 207, 130, 23);
        dataPanel.add(btnNewButton_1_1);
        
        JButton btnNewButton_1_2 = new JButton("Deletar");
        btnNewButton_1_2.setForeground(Color.WHITE);
        btnNewButton_1_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnNewButton_1_2.setContentAreaFilled(false);
        btnNewButton_1_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnNewButton_1_2.setBounds(43, 241, 97, 23);
        dataPanel.add(btnNewButton_1_2);
        
        JButton backBTN = new JButton("");
        backBTN.setIcon(new ImageIcon(TesteClass.class.getResource("/resources/backBTN.png")));
        backBTN.setContentAreaFilled(false);
        backBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        backBTN.setBounds(10, 241, 30, 23);
        dataPanel.add(backBTN);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.setBounds(7, 5, 621, 28);
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
