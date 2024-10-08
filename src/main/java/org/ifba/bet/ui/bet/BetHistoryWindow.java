package org.ifba.bet.ui.bet;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.bet.BetDaoPostgres;
import org.ifba.bet.dao.bid.BidDaoPostgres;
import org.ifba.bet.dao.user.UserDaoPostgres;
import org.ifba.bet.model.Bet;
import org.ifba.bet.model.Bid;
import org.ifba.bet.model.User;

public class BetHistoryWindow extends JInternalFrame {
	
	//Janela de Histórico de apostas

	private static final long serialVersionUID = 1L;
	private DefaultListModel<Bet> listModel;
	
	//Personalização da lista de bet
	private BetViewhCustomListRenderer customListRenderer = new BetViewhCustomListRenderer();
	
	// CurrentUser define o usuário atual
	// e serve para buscá-lo no banco de dados

	private User currentUser;
	private BetMainWindow betMainWindow;
	
	//objetos de acesso ao banco de dados
	private BidDaoPostgres bidDao = new BidDaoPostgres();
	private BetDaoPostgres betDao = new BetDaoPostgres();
	private UserDaoPostgres userDao = new UserDaoPostgres();
	
	private JScrollPane scrollPane;
	private JLabel noBetsFoundedLBL;
	
	//Construtor da classe
	public BetHistoryWindow() {
		
		super();

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 462, 244);
		setTitle("Bet-Betina v1.23 - Minhas Apostas");
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		getContentPane().setLayout(null);
		
		//Modelo da Lista
		listModel = new DefaultListModel<>();
		
		//Mensagem de falha ao encontrar apostas no nome do usuario
		//sempre invisivel até que seja utilizada
        noBetsFoundedLBL = new JLabel("Não foram encontradas apostas em seu nome.");
        noBetsFoundedLBL.setVisible(false);
        noBetsFoundedLBL.setBounds(81, 50, 301, 44);
        getContentPane().add(noBetsFoundedLBL);
        noBetsFoundedLBL.setFont(new Font("Georgia", Font.PLAIN, 14));
        noBetsFoundedLBL.setForeground(Color.BLACK);
        
        //Lista de Bets
		JList<Bet> list = new JList<>(listModel);
		
		list.setOpaque(false);
		list.setFont(new Font("Georgia", Font.BOLD, 16));
		list.setCellRenderer(customListRenderer);
		
		//Onde a lista de Bets está inserida
		scrollPane = new JScrollPane(list);
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		scrollPane.setBounds(1, 0, 458, 172);
		getContentPane().add(scrollPane);
		
		//Botão de modificar apostas em aberto
		JButton btnNewButton = new JButton("Modificar Aposta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bet bet = list.getSelectedValue(); //Pega a aposta selecionada pelo usuário
				if (bet == null) {
					JOptionPane.showMessageDialog(BetHistoryWindow.this, "Selecione um aposta para editar.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				switch (bet.getState()) {
				case Bet.OPEN: //Verifica se a aposta ainda está em aberto
					try {
						betMainWindow.loadBids(betDao.getAllBids(bet.getId()));//pega as informações da aposta
						betMainWindow.setForeignBetId(bet.getId());//pega o id da aposta
						setVisible(false);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				default: //Quando a aposta estiver fechada
					JOptionPane.showMessageDialog(BetHistoryWindow.this, "Não é possível editar esta aposta.",
							"Acesso Negado", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Visuais do Botão
		btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setFont(new Font("Georgia", Font.PLAIN, 14));
		btnNewButton.setBounds(1, 183, 170, 23);
		getContentPane().add(btnNewButton);
		
		//Botão de deletar a aposta
		JButton btnNewButton_1 = new JButton("Deletar Aposta");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bet bet = list.getSelectedValue(); //pega a aposta selecionada pelo usuario
				if (bet == null) { //verifica se a aposta foi selecionada
					JOptionPane.showMessageDialog(BetHistoryWindow.this, "Selecione um aposta para apagar.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(bet.getState() != Bet.OPEN) { //verifica se a aposta está aberta
					JOptionPane.showMessageDialog(BetHistoryWindow.this, "Não é Possível Apagar Uma \nAposta neste estado.", "Acesso Negado",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {

					ArrayList<Bid> bidList = new ArrayList<Bid>(); //armazena as apostas deletadas
					bidList = bidDao.getAllBids(bet.getId());
					double bidListValue = bidList.stream().mapToDouble(Bid::getPaidValue).sum(); //pega os valores apostados nas apostas deletadas
					userDao.updateUserBalance(currentUser, (currentUser.getBalance() + bidListValue)); //reembolsa os valores na conta do usuário
					betDao.deleteBet(bet.getId()); //Deleta a aposta do banco de dados
					betMainWindow.updateStatus(); //faz um update na tela de apostas
					JOptionPane.showMessageDialog(BetHistoryWindow.this, "Aposta deletada com sucesso.", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
					updateBets();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(BetHistoryWindow.this, "Ocorreu um erro ao apagar sua aposta.",
							"Erro", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		//visual do botão
		btnNewButton_1.setForeground(new Color(255, 0, 0));
		btnNewButton_1.setFont(new Font("Georgia", Font.PLAIN, 14));
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnNewButton_1.setBounds(286, 183, 173, 23);
		getContentPane().add(btnNewButton_1);
		setVisible(true);
	}
	
	//atualiza as apostas do histórico
    public void updateBets() {
        ArrayList<Bet> bets = null;

        try {
            bets = userDao.getAllBets(currentUser.getId()); //pega as apostas do usuario atual
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listModel.clear();

        if (bets == null || bets.isEmpty()) {
            noBetsFoundedLBL.setVisible(true); //Deixa a mensagem de falha ao encontrar apostas visível
        } else {
            noBetsFoundedLBL.setVisible(false); 
            for (Bet bet : bets) {
                listModel.addElement(bet);
            }
        }
    }

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void setBetMainWindow(BetMainWindow betMainWindow) {
		this.betMainWindow = betMainWindow;
	}

}
