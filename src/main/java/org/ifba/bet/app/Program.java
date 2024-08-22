package org.ifba.bet.app;

import java.awt.EventQueue;

import org.ifba.bet.ui.user.MainWindow;

public class Program {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
