package org.ifba.bet.ui.bet;

import java.awt.Component;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.bet.BetDaoPostgres;
import org.ifba.bet.dao.bid.BidDaoPostgres;
import org.ifba.bet.model.Bet;

class BetViewhCustomListRenderer extends JLabel implements ListCellRenderer<Bet> {

	private static final long serialVersionUID = 1L;
	private BidDaoPostgres bidDao = new BidDaoPostgres();
	private BetDaoPostgres betDao = new BetDaoPostgres();

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
		text = text + index + " - ";
		switch (value.getState()) {
		case Bet.CLOSED:
			text = text + "<span style=\"color: orange;\">" + "Aposta Fechada." + "</span>"  +  "Valor Apostado: R$ " + betDao.getBetTotalValue(value.getId()) + ".";
			break;
		case Bet.OPEN:
			text = text + "<span style=\"color: green;\">" + "Aposta Em Aberto." + "</span>" +  "Valor Apostado: R$ " + betDao.getBetTotalValue(value.getId())  + ".";
			break;
		case Bet.WIN:
			text = text + "<span style=\"color: blue;\">" + "Aposta Vencida. " + "</span>" +  "Valor ganho: R$ " + betDao.getBetPayout(value.getId()) + "." ;
			break;
		case Bet.LOSE:
			text = text + "<span style=\"color: red;\">" + "Aposta Perdida. " + "</span>" +  "Valor perdido: R$ " + betDao.getBetTotalValue(value.getId()) + "." ;
			break;
		default:
			text = text + "<span style=\"color: red;\">" + "Estado Desconhecido." + "</span>"  +  "Valor: R$ ???." ;
			break;
		}

		try {
			totalBidsInBet = bidDao.getAllBids(value.getId()).size();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		text = text + " Total de lances: " + totalBidsInBet + ".</html>";

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
