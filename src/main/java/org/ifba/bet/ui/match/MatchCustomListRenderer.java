package org.ifba.bet.ui.match;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import org.ifba.bet.model.Bid;
import org.ifba.bet.model.Match;

class MatchCustomListRenderer extends JLabel implements ListCellRenderer<Match> {

	private static final long serialVersionUID = 1L;

	private ArrayList<Bid> bidArray;

	public MatchCustomListRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Match> list, Match value, int index,
			boolean isSelected, boolean cellHasFocus) {
		String text = "";
		index++;
		if (index < 10) {
			text = "0";
		}

		text = text + String.valueOf(index) + " - " + value;
		boolean hasBid = false;
		for (Bid bid : bidArray) {
			if (bid.getMatchID() == value.getId()) {
				hasBid = true;
				break;
			}
		}

		if (hasBid) {
			text = text + " - [Lance feito]";
			setForeground(Color.RED);
		}

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

	public ArrayList<Bid> getBidArray() {
		return bidArray;
	}

	public void setBidArray(ArrayList<Bid> bidArray) {
		this.bidArray = bidArray;
	}

}
