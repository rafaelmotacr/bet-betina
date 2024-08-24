package org.ifba.bet.ui.bet;

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

import org.ifba.bet.dao.bet.BetDaoPostgres;
import org.ifba.bet.dao.bid.BidDaoPostgres;
import org.ifba.bet.dao.match.MatchDaoPostgres;
import org.ifba.bet.dao.user.UserDaoPostgres;
import org.ifba.bet.model.Bid;
import org.ifba.bet.model.Match;
import org.ifba.bet.model.User;

public class BetMainWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private MatchDaoPostgres matchDao = new MatchDaoPostgres();
	private UserDaoPostgres userDao = new UserDaoPostgres();
	private BetDaoPostgres betDao = new BetDaoPostgres();
	private BidDaoPostgres bidDao = new BidDaoPostgres();

	private User currentUser;
	private JTextField searchFLD;
	private BetMatchCustomListRenderer customListRenderer = new BetMatchCustomListRenderer();
	private DefaultListModel<Match> listModel;

	private JLabel totalCostLBL;
	private JLabel totalBidsLBL;
	private JLabel userBalanceLBL;
	private JLabel betStateLBL;
	private JLabel balanceAfterBetLBL;

	private JButton createBetBTN;
	private JButton cancelBetBTN;
	private JButton cancelBidBTN;
	private JButton updateBidBTN;
	private JButton makeBidBTN;
	private JButton historyBTN;
	private JButton confirmBetBTN;

	private int betState = 0;
	private int foreignBetId = 0;
	private double betTotalCost = 0;

	private ArrayList<Bid> bidArray;

	public BetMainWindow() {

		super();
		bidArray = new ArrayList<Bid>();

		setTitle("Bet-Betina v1.23 - Menu de Apostas");
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBounds(0, 0, 704, 396);
		setLocation(259, 78);
		getContentPane().setLayout(null);

		BidWindow bidWindow = new BidWindow();
		bidWindow.setVisible(false);
		bidWindow.setClosable(true);
		bidWindow.setLocation(140, 44);
		getContentPane().add(bidWindow);

		BetHistoryWindow betHistoryWindow = new BetHistoryWindow();
		betHistoryWindow.setVisible(false);
		betHistoryWindow.setClosable(true);
		betHistoryWindow.setLocation(140, 44);
		getContentPane().add(betHistoryWindow);

		listModel = new DefaultListModel<>();
		JList<Match> list = new JList<>(listModel);
		customListRenderer.setBidArray(bidArray);

		list.setOpaque(false);
		list.setFont(new Font("Georgia", Font.BOLD, 16));
		list.setCellRenderer(customListRenderer);

		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		scrollPane.setBounds(164, 32, 310, 299);
		getContentPane().add(scrollPane);

		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(null);
		dataPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		dataPanel.setBackground(new Color(0, 128, 128));
		dataPanel.setBounds(0, 32, 157, 336);
		getContentPane().add(dataPanel);

		JButton searchBTN = new JButton("Buscar Partida");
		searchBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		searchBTN.setForeground(new Color(255, 255, 255));
		searchBTN.setContentAreaFilled(false);
		searchBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		searchBTN.setBounds(10, 42, 137, 23);
		dataPanel.add(searchBTN);
		searchBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchText = searchFLD.getText();
				if (searchText == null || searchText.equals("") || !searchFLD.isEnabled()) {
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
		searchFLD.setFont(new Font("Georgia", Font.PLAIN, 12));
		searchFLD.setText("Nome do time...");
		searchFLD.setBounds(10, 11, 111, 23);
		dataPanel.add(searchFLD);
		searchFLD.setColumns(10);

		JButton backBTN = new JButton("");
		backBTN.setIcon(new ImageIcon("src/main/resources/backBTN.png"));
		backBTN.setContentAreaFilled(false);
		backBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		backBTN.setBounds(10, 302, 30, 23);
		dataPanel.add(backBTN);
		backBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JButton refreshBTN = new JButton("");
		refreshBTN.setIcon(new ImageIcon("src/main/resources/reload.png"));
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
		refreshBTN.setFont(new Font("Georgia", Font.PLAIN, 8));
		refreshBTN.setBounds(124, 11, 23, 23);
		dataPanel.add(refreshBTN);

		makeBidBTN = new JButton("Fazer Lance");
		makeBidBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Match match = list.getSelectedValue();
				if (match == null) {
					JOptionPane.showMessageDialog(BetMainWindow.this, "Selecione uma partida primeiro.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				for (Bid bid : bidArray) {
					if (bid.getMatchID() == match.getId()) {
						JOptionPane.showMessageDialog(BetMainWindow.this, "Você já fez um lance nesta partida.",
								"Aviso", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				bidWindow.setBetMainWindow(BetMainWindow.this);
				bidWindow.setCurrentUser(currentUser);
				bidWindow.setMatch(list.getSelectedValue());
				bidWindow.setVisible(true);
			}
		});

		makeBidBTN.setEnabled(false);
		makeBidBTN.setForeground(Color.WHITE);
		makeBidBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		makeBidBTN.setContentAreaFilled(false);
		makeBidBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		makeBidBTN.setBounds(10, 203, 137, 23);
		dataPanel.add(makeBidBTN);

		historyBTN = new JButton("Minhas Apostas");
		historyBTN.setEnabled(false);
		historyBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				betHistoryWindow.setBetMainWindow(BetMainWindow.this);
				betHistoryWindow.setCurrentUser(currentUser);
				betHistoryWindow.updateBets();
				betHistoryWindow.setVisible(true);
			}
		});
		historyBTN.setForeground(Color.WHITE);
		historyBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		historyBTN.setContentAreaFilled(false);
		historyBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		historyBTN.setBounds(44, 302, 103, 23);
		dataPanel.add(historyBTN);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(0, 0, 702, 28);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel OperationsTextPNL = new JLabel("OPERAÇÕES");
		OperationsTextPNL.setBounds(10, 0, 130, 28);
		panel.add(OperationsTextPNL);
		OperationsTextPNL.setForeground(new Color(255, 255, 255));
		OperationsTextPNL.setFont(new Font("Georgia", Font.PLAIN, 20));

		JLabel lblTimesCorrespondentes = new JLabel("PARTIDAS DISPONÍVEIS");
		lblTimesCorrespondentes.setBounds(168, 0, 307, 28);
		panel.add(lblTimesCorrespondentes);
		lblTimesCorrespondentes.setForeground(new Color(255, 255, 255));
		lblTimesCorrespondentes.setFont(new Font("Georgia", Font.PLAIN, 20));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBackground(new Color(0, 128, 128));
		panel_1.setBounds(481, 32, 221, 336);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblStatus = new JLabel("Status Da Aposta");
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblStatus.setBounds(45, 5, 130, 23);
		panel_1.add(lblStatus);

		JPanel blackLine_1 = new JPanel();
		blackLine_1.setForeground(Color.BLACK);
		blackLine_1.setBackground(Color.BLACK);
		blackLine_1.setBounds(0, 27, 221, 3);
		panel_1.add(blackLine_1);

		totalBidsLBL = new JLabel();
		totalBidsLBL.setFont(new Font("Georgia", Font.PLAIN, 12));
		totalBidsLBL.setForeground(new Color(255, 255, 255));
		totalBidsLBL.setBounds(10, 34, 201, 14);
		panel_1.add(totalBidsLBL);

		userBalanceLBL = new JLabel();
		userBalanceLBL.setForeground(Color.WHITE);
		userBalanceLBL.setFont(new Font("Georgia", Font.PLAIN, 12));
		userBalanceLBL.setBounds(10, 217, 198, 14);
		panel_1.add(userBalanceLBL);

		totalCostLBL = new JLabel();
		totalCostLBL.setForeground(Color.WHITE);
		totalCostLBL.setFont(new Font("Georgia", Font.PLAIN, 12));
		totalCostLBL.setBounds(10, 242, 198, 14);
		panel_1.add(totalCostLBL);

		betStateLBL = new JLabel("Sem Apostas Ativas.");
		betStateLBL.setForeground(Color.WHITE);
		betStateLBL.setFont(new Font("Georgia", Font.PLAIN, 12));
		betStateLBL.setBounds(10, 53, 201, 14);
		panel_1.add(betStateLBL);

		balanceAfterBetLBL = new JLabel();
		balanceAfterBetLBL.setForeground(Color.WHITE);
		balanceAfterBetLBL.setFont(new Font("Georgia", Font.PLAIN, 12));
		balanceAfterBetLBL.setBounds(10, 267, 198, 14);
		panel_1.add(balanceAfterBetLBL);

		JPanel blackLine_1_1 = new JPanel();
		blackLine_1_1.setForeground(Color.BLACK);
		blackLine_1_1.setBackground(Color.BLACK);
		blackLine_1_1.setBounds(0, 78, 221, 3);
		panel_1.add(blackLine_1_1);

		JPanel blackLine_1_1_1 = new JPanel();
		blackLine_1_1_1.setForeground(Color.BLACK);
		blackLine_1_1_1.setBackground(Color.BLACK);
		blackLine_1_1_1.setBounds(0, 203, 221, 3);
		panel_1.add(blackLine_1_1_1);

		JPanel blackLine_1_1_2 = new JPanel();
		blackLine_1_1_2.setForeground(Color.BLACK);
		blackLine_1_1_2.setBackground(Color.BLACK);
		blackLine_1_1_2.setBounds(0, 291, 221, 3);
		panel_1.add(blackLine_1_1_2);

		confirmBetBTN = new JButton("Confirmar Aposta");
		confirmBetBTN.setEnabled(false);
		confirmBetBTN.setBounds(324, 342, 150, 23);
		getContentPane().add(confirmBetBTN);
		confirmBetBTN.setForeground(new Color(0, 0, 0));
		confirmBetBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		confirmBetBTN.setContentAreaFilled(false);
		confirmBetBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		// A esta altura do campeonato, esse código tá uma bela de uma bagunça

		confirmBetBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bidArray.size() < 1) {
					JOptionPane.showMessageDialog(BetMainWindow.this, "Inclua ao menos um lance em sua aposta.",
							"Aviso", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (betState == 7) {
					for (Bid bid : bidArray) {

						// Adiciona somente os novos lances ao banco

						if (bid.getBetID() != foreignBetId) {
							try {
								bidDao.insertBid(foreignBetId, bid.getMatchID(), bid.getGuess(), bid.getPaidValue());
								userDao.updateUserBalance(currentUser, (currentUser.getBalance() - bid.getPaidValue()));
							} catch (SQLException e1) {
								JOptionPane.showMessageDialog(BetMainWindow.this,
										"Ocorreu um erro ao cadastrar\n um de seus lances.", "Aviso",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						} else {
							currentUser.setBalance(currentUser.getBalance() - bid.getPaidValue());
							try {
								bidDao.updateBid(bid);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					}
					JOptionPane.showMessageDialog(BetMainWindow.this, "Aposta Atualizada com Sucesso.",
							"Confirmação de Aposta", JOptionPane.INFORMATION_MESSAGE);
					removeAllBids();
					updateStatus();
					updateMatchs();
					return;
				}

				int betId = 0;
				try {
					betId = betDao.insertBet(currentUser.getID(), 1);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(BetMainWindow.this, "Ocorreu um erro ao criar a aposta", "Aviso",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					return;
				}
				for (Bid bid : bidArray) {
					try {
						bidDao.insertBid(betId, bid.getMatchID(), bid.getGuess(), bid.getPaidValue());
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(BetMainWindow.this,
								"Ocorreu um erro ao cadastrar\n um de seus lances.", "Aviso",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
						return;
					}
				}
				JOptionPane.showMessageDialog(BetMainWindow.this, "Aposta criada com sucesso.", "Confirmação de Aposta",
						JOptionPane.INFORMATION_MESSAGE);
				betState = 0;

				try {
					userDao.updateUserBalance(currentUser, (currentUser.getBalance() - betTotalCost));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				removeAllBids();
				updateStatus();
				updateMatchs();
			}
		});

		cancelBetBTN = new JButton("Cancelar Aposta");
		cancelBetBTN.setEnabled(false);
		cancelBetBTN.setBounds(164, 342, 150, 23);
		getContentPane().add(cancelBetBTN);
		cancelBetBTN.setForeground(new Color(255, 0, 0));
		cancelBetBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		cancelBetBTN.setContentAreaFilled(false);
		cancelBetBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		cancelBetBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (betState == 7) {
					try {
						betDao.deleteBet(foreignBetId);
						userDao.updateUserBalance(currentUser, currentUser.getBalance());
						removeAllBids();
						JOptionPane.showMessageDialog(BetMainWindow.this, "Aposta Cancelada. Saldo Devolvido.",
								"Cancelamento Bem Sucedido.", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(BetMainWindow.this, "Não foi Possível Cancelar sua Aposta.",
								"Erro.", JOptionPane.INFORMATION_MESSAGE);
						e1.printStackTrace();
					}
					betHistoryWindow.updateBets();
				}
				removeAllBids();
				updateStatus();
			}
		});

		cancelBidBTN = new JButton("Cancelar Lance");
		cancelBidBTN.setForeground(Color.WHITE);
		cancelBidBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		cancelBidBTN.setEnabled(false);
		cancelBidBTN.setContentAreaFilled(false);
		cancelBidBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		cancelBidBTN.setBounds(10, 237, 137, 23);
		dataPanel.add(cancelBidBTN);
		cancelBidBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Match match = list.getSelectedValue();
				if (match == null) {
					JOptionPane.showMessageDialog(BetMainWindow.this, "Selecione uma partida primeiro.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (betState == 7) {
					for (Bid bid : bidArray) {
						if (bid.getMatchID() == match.getId()) {
							try {
								bidDao.deleteBid(bid);
								userDao.updateUserBalance(currentUser, (currentUser.getBalance() + bid.getPaidValue()));
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							removeBid(match);
							return;
						}
					}
					JOptionPane.showMessageDialog(BetMainWindow.this, "Você não fez um lance nesta partida.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				for (Bid bid : bidArray) {
					if (bid.getMatchID() == match.getId()) {
						removeBid(match);
						return;
					}
				}
				JOptionPane.showMessageDialog(BetMainWindow.this, "Você não fez um lance nesta partida.", "Aviso",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		createBetBTN = new JButton("Iniciar Aposta");
		createBetBTN.setEnabled(false);
		createBetBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		createBetBTN.setForeground(new Color(255, 255, 255));
		createBetBTN.setContentAreaFilled(false);
		createBetBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		createBetBTN.setBounds(10, 169, 137, 23);
		dataPanel.add(createBetBTN);
		createBetBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				betState = 1;
				updateStatus();
				JOptionPane.showMessageDialog(BetMainWindow.this,
						"Selecione uma partida para fazer um lance.\n Após definir seus lances, confirme a aposta.",
						"Tutorial", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		updateBidBTN = new JButton("Modificar Lance");
		updateBidBTN.setForeground(Color.WHITE);
		updateBidBTN.setFont(new Font("Georgia", Font.PLAIN, 14));
		updateBidBTN.setEnabled(false);
		updateBidBTN.setContentAreaFilled(false);
		updateBidBTN.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		updateBidBTN.setBounds(10, 268, 137, 23);
		dataPanel.add(updateBidBTN);
		updateBidBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Match match = list.getSelectedValue();
				if (match == null) {
					JOptionPane.showMessageDialog(BetMainWindow.this, "Selecione uma partida primeiro.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (betState == 7) {
					for (Bid bid : bidArray) {
						if (bid.getMatchID() == match.getId()) {
							if (bid.getBetID() == foreignBetId) {
								try {
									bidDao.deleteBid(bid);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
							removeBid(match);
							bidWindow.setCurrentUser(currentUser);
							bidWindow.setMatch(match);
							bidWindow.setVisible(true);
							return;
						}
					}
				}
				for (Bid bid : bidArray) {
					if (bid.getMatchID() == match.getId()) {
						removeBid(match);
						bidWindow.setCurrentUser(currentUser);
						bidWindow.setMatch(match);
						bidWindow.setVisible(true);
						return;
					}
				}
				JOptionPane.showMessageDialog(BetMainWindow.this, "Você não fez um lance nesta partida.", "Aviso",
						JOptionPane.ERROR_MESSAGE);

			}
		});

		setVisible(true);
		updateButtons();
		updateMatchs();
	}

	private void updateButtons() {
		switch (betState) {
		case 0:
			createBetBTN.setEnabled(true);
			historyBTN.setEnabled(true);
			updateBidBTN.setEnabled(false);
			cancelBidBTN.setEnabled(false);
			cancelBetBTN.setEnabled(false);
			confirmBetBTN.setEnabled(false);
			makeBidBTN.setEnabled(false);
			break;
		default:
			updateBidBTN.setEnabled(true);
			cancelBidBTN.setEnabled(true);
			cancelBetBTN.setEnabled(true);
			confirmBetBTN.setEnabled(true);
			makeBidBTN.setEnabled(true);
			createBetBTN.setEnabled(false);
			historyBTN.setEnabled(false);
			break;
		}
	}

	public void updateStatus() {
		updateButtons();
		double userBalance = currentUser.getBalance();
		double balanceAfterBet = userBalance - betTotalCost;

		totalCostLBL.setText(String.format("Custo Total: R$ %.2f.", betTotalCost));
		userBalanceLBL.setText(String.format("Saldo Atual: R$ %.2f.", userBalance));
		balanceAfterBetLBL.setText(String.format("Saldo Restante: R$ %.2f.", balanceAfterBet));
		switch (betState) {
		case 0:
			betStateLBL.setText("Estado: Não iniciada.");
			totalBidsLBL.setText("");
			break;
		case 1:
			betStateLBL.setText("Estado: Em criação.");
			totalBidsLBL.setText("Lances feitos: " + bidArray.size() + ".");
			break;
		case 7:
			betStateLBL.setText("Estado: Em modificação.");
			totalBidsLBL.setText("Lances feitos: " + bidArray.size() + ".");
			break;
		default:
			betStateLBL.setText("Estado: Desconhecido.");
			break;
		}
	}

	public void updateMatchs() {
		ArrayList<Match> matchs = null;
		try {
			matchs = matchDao.getActiveMatchs();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(BetMainWindow.this, "Nenhuma partida encontrada.");
			e.printStackTrace();
		}
		listModel.clear();
		for (Match match : matchs) {
			listModel.addElement(match);
		}
	}

	public void updateMatchs(String filter) {
		ArrayList<Match> matchs = null;

		try {
			matchs = matchDao.getActiveMatchs(filter);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(BetMainWindow.this, "Nenhuma partida encontrada.");
			e.printStackTrace();
		}
		listModel.clear();
		for (Match match : matchs) {
			listModel.addElement(match);
		}
	}

	public void addBid(Bid bid) {
		betTotalCost += bid.getPaidValue();
		bidArray.add(bid);
		updateStatus();
	}

	public void removeBid(Match match) {
		for (Bid bid : bidArray) {
			if (bid.getMatchID() == match.getId()) {
				betTotalCost -= bid.getPaidValue();
				bidArray.remove(bid);
				break;
			}
		}
		updateStatus();
	}

	public void loadBids(ArrayList<Bid> arr) {
		bidArray.addAll(arr);
		betState = 7;
		betTotalCost = arr.stream().mapToDouble(Bid::getPaidValue).sum();
		currentUser.setBalance(currentUser.getBalance() + betTotalCost);
		updateMatchs();
		updateStatus();
	}

	public void removeAllBids() {
		betState = 0;
		betTotalCost = 0;
		bidArray.clear();
		updateStatus();
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void setForeignBetId(int foreignBetId) {
		this.foreignBetId = foreignBetId;
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
			BetMainWindow testeClass = new BetMainWindow();
			desktopPane.add(testeClass);

			frame.setVisible(true);
		});
	}
}
