package ui.team;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import model.Team;

class CustomListRenderer extends JLabel implements ListCellRenderer<Team> {

	private static final long serialVersionUID = 1L;

		public CustomListRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Team> list, Team value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(String.valueOf(index + 1) + " - " + value); 
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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Desenhe a linha inferior
            g.setColor(Color.GRAY); // Cor da linha
            int y = getHeight() - 1;
            g.drawLine(0, y, getWidth(), y);
        }
    }
