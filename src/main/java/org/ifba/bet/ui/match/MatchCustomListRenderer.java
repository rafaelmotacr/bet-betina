package org.ifba.bet.ui.match;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import org.ifba.bet.model.Match;
import org.ifba.bet.model.User;

class CustomListRenderer extends JLabel implements ListCellRenderer<Match> {

	private static final long serialVersionUID = 1L;
	private User user;

	public CustomListRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Match> list, Match value, int index,
			boolean isSelected, boolean cellHasFocus) {
		String text = null;
		index++;
		if (user.getAccessLevel() == 1) {
			if (index < 10) {
				text = "0" + String.valueOf(index) + " - " + value
						+ (value.getState() == 1 ? " - Ativa" : " - Finalizada");

			} else {
				text = String.valueOf(index) + " - " + value + (value.getState() == 1 ? " - Ativa" : " - Finalizada");
			}
		} else {
			if (index < 10) {
				text = "0" + String.valueOf(index) + " - " + value;

			} else {
				text = String.valueOf(index) + " - " + value;
			}
		}
		setText(text);
		setFont(new Font("Comic Sans MS", Font.BOLD, 14));
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

	public void setUser(User user) {
		this.user = user;
	}
}
