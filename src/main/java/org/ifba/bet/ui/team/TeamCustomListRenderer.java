package org.ifba.bet.ui.team;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import org.ifba.bet.model.Team;
import org.ifba.bet.model.User;

class TeamCustomListRenderer extends JLabel implements ListCellRenderer<Team> {

	private static final long serialVersionUID = 1L;
	private User user;

	public TeamCustomListRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Team> list, Team value, int index, boolean isSelected,
			boolean cellHasFocus) {
		String text;
		index++;
		if (index < 10) {
			text = "0" + String.valueOf(index) + " - " + value;

		} else {
			text = String.valueOf(index) + " - " + value;
		}
		if ((user != null) && (value.getId() == user.getFavoriteTeam())) {
			text = text.concat(" | (Seu time favorito)");
		}
		setText(text);
		setFont(new Font("Georgia", Font.PLAIN, 16));
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Desenhe a linha inferior
		g.setColor(Color.GRAY); // Cor da linha
		int y = getHeight() - 1;
		g.drawLine(0, y, getWidth(), y);
	}

	public void setUser(User user) {
		this.user = user;
	}

}
