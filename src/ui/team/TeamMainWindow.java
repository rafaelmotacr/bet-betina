package ui.team;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.TeamDaoPostgres;
import model.Team;

public class TeamMainWindow extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    
    private TeamDaoPostgres dao = new TeamDaoPostgres(); 

    public TeamMainWindow() {
        super("Tabela de Times", true, true, true, true);
        
        // Criar um ArrayList de opções
        ArrayList<Team> options = new ArrayList<>();
        try {
            options = dao.getAllTeams();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Criar o modelo da tabela e definir as colunas
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID", "Nome"," Sigla"}, 0);
        
        // Preencher o modelo da tabela com os dados dos times
        for (Team team : options) {
            Object[] rowData = {
                team.getID(),        // Ajuste conforme o método disponível na classe Team
                team.getName(),      // Ajuste conforme o método disponível na classe Team
                team.getAbbreviation()  // Ajuste conforme o método disponível na classe Team (opcional)
            };
            tableModel.addRow(rowData);
        }
        
        // Criar a JTable com o modelo de tabela
        JTable table = new JTable(tableModel);
        
        // Adicionar a JTable dentro de um JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        
        // Configurar tamanho e visibilidade do JInternalFrame
        setSize(400, 300);
        setVisible(true);
    }
}
