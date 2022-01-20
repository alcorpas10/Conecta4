package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import controller.MainController;
import model.InfoObj;
import model.Jugador;
import observers.Observer;
import utils.Coordinate;

public class FinalScreen extends JPanel implements ActionListener, Observer {
	private static final long serialVersionUID = 1L;
	
	private JButton bReturn;
	private JPanel background;
	private JLabel playerInfo;
	private MainController _ctrl;
	private JPanel junctionsView;
	private String hostName, enemyName;
	private int wonHostRounds, wonEnemyRounds;

	FinalScreen(MainController ct) {
		_ctrl = ct;
		_ctrl.addObserver(this);
		initPanel();
	}

	void initPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		background = new JPanel();
		background.setBounds(0, 0, 680, 700);

		playerInfo = new JLabel();
		playerInfo.setBounds(20, 10, 680, 700);
		playerInfo.setBackground(Color.WHITE);
		playerInfo.setFont(new Font("helvetica", Font.BOLD, 50));
		playerInfo.setForeground(Color.BLUE);

		bReturn = new JButton("OK");
		bReturn.setBounds(260, 650, 150, 30);
		bReturn.setBackground(Color.WHITE);
		bReturn.addActionListener(this);

		if (wonEnemyRounds > wonHostRounds) {
			playerInfo.setText("<html><body>Winner: " + enemyName + "<br/>" + "Points: "
					+ wonEnemyRounds + "<br/>" + "Loser: " + hostName + "<br/>" + "Points: "
					+ wonHostRounds + "</body></html>");

		} else {
			playerInfo.setText("<html><body>Winner: " + hostName + "<br/>" + "Points: "
					+ wonHostRounds + "<br/>" + "Loser: " + enemyName + "<br/>" + "Points: "
					+ wonEnemyRounds + "</body></html>");

		}
		junctionsView = createViewPanel(new JTable(new ScoreBoardTable(_ctrl)), "Scoreboard");
		junctionsView.setBorder(BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black, 2),"Scoreboard",TitledBorder.LEFT,TitledBorder.TOP));

		this.add(junctionsView);
		background.add(playerInfo);
		this.add(background);
		this.add(bReturn);
	}

	void changeImage() {
		if (_ctrl.isDraw())
			playerInfo.setText("<html><body>Draw </body></html>");
		else {
			if (wonEnemyRounds > wonHostRounds) {
				playerInfo.setText("<html><body>Winner: " + enemyName + "<br/>" + "Points: "
						+ wonEnemyRounds + "<br/>" + "<br/>" + "Loser: " + hostName + "<br/>"
						+ "Points: " + wonHostRounds + "</body></html>");

			} else {
				playerInfo.setText("<html><body>Winner: " + hostName + "<br/>" + "Points: "
						+ wonHostRounds + "<br/>" + "<br/>" + "Loser: " + enemyName + "<br/>"
						+ "Points: " + wonEnemyRounds + "</body></html>");

			}
		}

		playerInfo.setFont(new Font("helvetica", Font.BOLD, 50));
		playerInfo.setForeground(Color.BLUE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bReturn)
			MainView.changeToPanel(1);
	}
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		p.add(new JScrollPane(c));
		return p;
	}

	@Override
	public void update(Color c, Coordinate coor) {}

	@Override
	public void reload(InfoObj infoObj) {}

	@Override
	public void onWinner(InfoObj infoObj) {
		hostName = infoObj.getHostName();
		enemyName = infoObj.getEnemyName();
		wonHostRounds = infoObj.getWonHostRounds();
		wonEnemyRounds = infoObj.getWonEnemyRounds();
	}

	@Override
	public void onDraw(InfoObj infoObj) {}

	@Override
	public void onNameUpdate(InfoObj infoObj) {}

	@Override
	public void updateScoreboard(List<Jugador> list) {}

	@Override
	public void onAITurn() {}

	@Override
	public void onAIFinish() {}
}