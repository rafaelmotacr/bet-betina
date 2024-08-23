package org.ifba.bet.ui.bet;

import java.awt.Component;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.bid.BidDaoPostgres;
import org.ifba.bet.model.Bet;

class BetViewhCustomListRenderer extends JLabel implements ListCellRenderer<Bet> {

	private static final long serialVersionUID = 1L;
	private BidDaoPostgres bidDao = new BidDaoPostgres();

	public BetViewhCustomListRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Bet> list, Bet value, int index, boolean isSelected,
			boolean cellHasFocus) {
		String text = "<html>";
		int totalBidsInBet = 0;
		index++;
		if (index < 10) {
			text = text + "0";
		}
		// text = text + index + " - [ID: " + value.getID() + "] ";
		text = text + index + " - ";

		switch (value.getState()) {
		case 0:
			text = text + "<span style=\"color: red;\">" + "Aposta Finalizada." + "</span>";
			break;
		case 1:
			text = text + "<span style=\"color: green;\">" + "Aposta Em Aberto." + "</span>";
			break;
		case 3:
			text = text + "Aposta Vencida.";
			break;
		case 4:
			text = text + "Aposta Perdida.";
			break;
		default:
			text = text + "<span style=\"color: red;\">" + "Estado Desconhecido." + "</span>";
			break;
		}

		try {
			totalBidsInBet = bidDao.getAllBids(value.getID()).size();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		text = text + " Total de lances: " + totalBidsInBet + "</html>";

		setText(text);
		setFont(new Font("Georgia", Font.PLAIN, 14));

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setBorder(LineBorder.createGrayLineBorder());

		return this;
	}

}
