package org.ifba.bet.ui.match;

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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.match.MatchDaoPostgres;
import org.ifba.bet.model.Match;

public class MatchMainWindow extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private MatchCustomListRenderer customListRenderer = new MatchCustomListRenderer();
	private DefaultListModel<Match> listModel;
	private JTextField searchFLD;
	private MatchDaoPostgres matchDao = new MatchDaoPostgres();

	public MatchMainWindow() {

		super();

		setTitle("Bet-Betina v1.23 - ADM: Menu de Partidas");
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBounds(0, 0, 704, 396);
		setLocation(259, 78);
		getContentPane().setLayout(null);

		listModel = new DefaultListModel<>();
		JList<Match> list = new JList<>(listModel);

		list.setOpaque(false);
		list.setFont(new Font("Georgia", Font.BOLD, 16));
		list.setCellRenderer(customListRenderer);

		MatchResultWindow matchResultWindow = new MatchResultWindow();
		matchResultWindow.setLocation(172, 76);
		matchResultWindow.setVisible(false);
		
		CreateMatchWindow createMatchWindow = new CreateMatchWindow ();
		createMatchWindow.setLocation(172, 76);
		createMatchWindow.setVisible(false);
		getContentPane().add(createMatchWindow);
		getContentPane().add(matchResultWindow);

		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		scrollPane.setBounds(164, 32, 528, 336);
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
				searchFLD.setText("Nome do time...");
				searchFLD.setEnabled(false);
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

		JButton btnCriarPartida = new JButton("Criar Partida");
		btnCriarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createMatchWindow.setMatchMainWindow(MatchMainWindow.this);
				createMatchWindow.setVisible(true);
			}
		});
		btnCriarPartida.setForeground(Color.WHITE);
		btnCriarPartida.setFont(new Font("Georgia", Font.PLAIN, 14));
		btnCriarPartida.setContentAreaFilled(false);
		btnCriarPartida.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnCriarPartida.setBounds(10, 234, 137, 23);
		dataPanel.add(btnCriarPartida);

		JButton btnAtualizarPartida = new JButton("Definir Resultado");
		btnAtualizarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Match match = list.getSelectedValue();
				if (match == null) {
					JOptionPane.showMessageDialog(MatchMainWindow.this, "Selecione Uma Partida Primeiro.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(match.getState() == Match.MATCH_FINISHED) {
					JOptionPane.showMessageDialog(MatchMainWindow.this, "Esta Partida Já Está Finalizada.\n"
							+ "Você Não Pode Fazer Alterações.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				matchResultWindow.setMatchMainWindow(MatchMainWindow.this);
				matchResultWindow.setMatch(list.getSelectedValue());
				matchResultWindow.update();
				matchResultWindow.setVisible(true);

			}
		});
		btnAtualizarPartida.setForeground(Color.WHITE);
		btnAtualizarPartida.setFont(new Font("Georgia", Font.PLAIN, 14));
		btnAtualizarPartida.setContentAreaFilled(false);
		btnAtualizarPartida.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnAtualizarPartida.setBounds(10, 268, 137, 23);
		dataPanel.add(btnAtualizarPartida);

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

		JLabel lblTimesCorrespondentes = new JLabel("TODAS AS PARTIDAS");
		lblTimesCorrespondentes.setBounds(168, 0, 307, 28);
		panel.add(lblTimesCorrespondentes);
		lblTimesCorrespondentes.setForeground(new Color(255, 255, 255));
		lblTimesCorrespondentes.setFont(new Font("Georgia", Font.PLAIN, 20));

		updateMatchs();
		setVisible(true);
	}

	public void updateMatchs() {
		ArrayList<Match> matchs = null;
		try {
			matchs = matchDao.getAllMatchs();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(MatchMainWindow.this, "Nenhuma partida encontrada.");
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
			matchs = matchDao.getAllMatchs(filter);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(MatchMainWindow.this, "Nenhuma partida encontrada.");
			e.printStackTrace();
		}
		listModel.clear();
		for (Match match : matchs) {
			listModel.addElement(match);
		}
	}
}
