package legacyCode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.team.TeamDaoPostgres;
import model.Team;

public class TeamFullView {

	private JFrame frame;
	private TeamDaoPostgres dao = new TeamDaoPostgres();

	// Efeito drag and drop xx, yy
	private int xx, xy;

	// Define altura e largura padrões
	int frameHeight = 360;
	int frameWidth = 640;

	// Pega a resolução da tela
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension d = tk.getScreenSize();
	private JTextField searchField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeamFullView window = new TeamFullView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TeamFullView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				xx = e.getX();
				xy = e.getY();
			}
		});
		frame.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
				int y = arg0.getYOnScreen();
				frame.setLocation(x - xx, y - xy);

			}
		});

		frame.setUndecorated(true);
		frame.setBounds((int) (d.getWidth() / 2 - frameWidth / 2), (int) (d.getHeight() / 2 - frameHeight / 2),
				frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel blackLine_2 = new JPanel();
		blackLine_2.setLayout(null);
		blackLine_2.setForeground(Color.BLACK);
		blackLine_2.setBackground(Color.BLACK);
		blackLine_2.setBounds(0, 0, 640, 23);
		frame.getContentPane().add(blackLine_2);

		JLabel titleLBL = new JLabel("bet-betina v1.21 - Home");
		titleLBL.setForeground(Color.WHITE);
		titleLBL.setBounds(0, 2, 141, 16);
		blackLine_2.add(titleLBL);

		JLabel closeBTN = new JLabel("X");
		closeBTN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		closeBTN.setForeground(Color.WHITE);
		closeBTN.setFont(new Font("Tahoma", Font.PLAIN, 16));
		closeBTN.setBounds(614, 3, 20, 16);
		blackLine_2.add(closeBTN);

		JPanel panel = new JPanel();
		panel.setBounds(202, 34, 236, 315);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblTime = new JLabel("Time");
		lblTime.setBounds(95, 5, 57, 36);
		lblTime.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		panel.add(lblTime);

		JLabel lblFotoTime = new JLabel("");
		lblFotoTime.setBounds(100, 35, 63, 63);
		panel.add(lblFotoTime);
		lblFotoTime.setIcon(new ImageIcon(TeamFullView.class.getResource("/resources/time.png")));
		lblFotoTime.setVisible(false);

		JLabel lblNomeTime = new JLabel("");
		lblNomeTime.setVerticalAlignment(SwingConstants.TOP);
		lblNomeTime.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblNomeTime.setBounds(10, 96, 216, 102);
		panel.add(lblNomeTime);

		JLabel lbl1 = new JLabel("Resultado do último jogo");
		lbl1.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		lbl1.setBounds(38, 209, 169, 22);
		panel.add(lbl1);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 128, 128));
		panel_1.setBounds(10, 34, 182, 315);
		frame.getContentPane().add(panel_1);

		JLabel lblNewLabel = new JLabel("Sei o que pôr aqui não");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		panel_1.add(lblNewLabel);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBackground(new Color(0, 128, 128));
		panel_1_1.setBounds(448, 34, 182, 315);
		frame.getContentPane().add(panel_1_1);
		panel_1_1.setLayout(null);

		JLabel lblProx = new JLabel("   Próximos jogos");
		lblProx.setBounds(3, 5, 177, 28);
		lblProx.setForeground(Color.WHITE);
		lblProx.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblProx.setBackground(Color.WHITE);
		panel_1_1.add(lblProx);

		JLabel lblProxJogo1 = new JLabel("");
		lblProxJogo1.setForeground(Color.WHITE);
		lblProxJogo1.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		lblProxJogo1.setBackground(Color.WHITE);
		lblProxJogo1.setBounds(3, 33, 169, 35);
		panel_1_1.add(lblProxJogo1);

		JLabel lblBuscar = new JLabel("Buscar Time");
		lblBuscar.setForeground(new Color(255, 255, 255));
		lblBuscar.setBackground(new Color(255, 255, 255));
		lblBuscar.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		panel_1.add(lblBuscar);

		searchField = new JTextField();
		searchField.setColumns(14);
		panel_1.add(searchField);

		JButton btnSearch = new JButton("Buscar");

//		busca o time por nome, tem que melhorar pra poder buscar por abreviação também

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = searchField.getText();

				try {
					Team team = dao.findTeamByName(nome);
//					criar método em times para poder achar as partidas que o time está presente
//					e criar um array list com todos os jogos

					lblNomeTime.setText(team.getAbbreviation() + " - " + team.getName());
					lblFotoTime.setVisible(true);
					lblProxJogo1.setText(team.getAbbreviation() + " X " + team.getAbbreviation());
//					getNextGame(team);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame, "Time não existe no banco de dados.");
					e1.printStackTrace();
				}

			}
//			public void getNextGame(Team teamA) {
//				PreparedStatement ps = ConexaoBdSingleton
//						.getInstance()
//						.getConexao().prepareStatement("SELECT * FROM team_tb WHERE team_abbreviation = ?");

//				como eu coloco isso no código??????
//				SELECT M.MATCH_ID, T.TEAM_ABBREVIATION, T.TEAM_NAME FROM TEAM_TB AS T
//				INNER JOIN MATCH_TB AS M
//				ON(M.MATCH_HOME_TEAM = T.TEAM_ID OR M.MATCH_AWAY_TEAM = TEAM_ID)
//				WHERE MATCH_HOME_TEAM = 1 OR MATCH_AWAY_TEAM = 1

//				ps.setString(1, abreviacao);
//				
//				ResultSet rs = ps.executeQuery();
//				rs.next();
//				lblProxJogo1.setText(teamA.getAbbreviation() + " x " + teamB.getAbbreviation());
//			}
		});

		panel_1.add(btnSearch);
	}
}
