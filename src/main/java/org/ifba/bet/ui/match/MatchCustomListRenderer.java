package org.ifba.bet.ui.match;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import org.ifba.bet.dao.team.TeamDaoPostgres;
import org.ifba.bet.model.Match;

class MatchCustomListRenderer extends JLabel implements ListCellRenderer<Match> {

	private static final long serialVersionUID = 1L;
	TeamDaoPostgres teamDao = new TeamDaoPostgres();

	public MatchCustomListRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Match> list, Match value, int index,
			boolean isSelected, boolean cellHasFocus) {
		String text = "<html>";
		index++;
		if (index < 10) {
			text = text + "0";
		}

		text = text + index + " - " + value + " - ";

		switch (value.getState()) {
		case Match.MATCH_FINISHED:
			text = text + "<span style=\"color: red;\">" + "Partida Finalizada. " + "</span>";
			break;
		case Match.MATCH_ON_GOING:
			text = text + "<span style=\"color: green;\">" + "Partida Ativa. " + "</span>";
			break;
		default:
			text = text + "<span style=\"color: red;\">" + "Estado Desconhecido. " + "</span>";
			break;
		}
		text = text + "Resultado: <u>";

		switch (value.getResult()) {
		case Match.RESULT_ON_GOING:
			text = text + "Em aberto.";
			break;
		case Match.RESULT_HOME_WIN:
			text = text + "<span style=\"color: blue;\">" + "Vitória do "
					+ teamDao.findTeamById(value.getHomeTeamId()).getName() + ".</span>";
			break;
		case Match.RESULT_AWAY_WIN:
			text = text + "<span style=\"color: blue;\">" + "Vitória do "
					+ teamDao.findTeamById(value.getAwayTeamId()).getName() + ".</span>";
			break;
		case Match.RESULT_DRAW:
			text = text + "<span style=\"color: orange;\">" + "Empate </span>";
			break;
		default:
			text = text + "<span style=\"color: red;\">" + "Mas hein????." + "</span>";
			break;
		}
		text = text + "</u></html>";

		setText(text);
		setFont(new Font("Georgia", Font.PLAIN, 16));
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setBorder(LineBorder.createBlackLineBorder());

		return this;
	}

}
