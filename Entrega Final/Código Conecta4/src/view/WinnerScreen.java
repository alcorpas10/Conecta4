package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainController;
import model.InfoObj;

public class WinnerScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	JButton bReturn;
	ImageIcon img;
	JLabel background;
	JLabel playerInfo;
	MainController _ctrl;
	
	public WinnerScreen(MainController ct) {
		_ctrl = ct;
		initPanel();
	}

	void initPanel() {
		this.setLayout(null);

		if(_ctrl.isLocalWinner()) img = new ImageIcon(getClass().getResource("/resources/crown2.jpg"));
		else img = new ImageIcon(getClass().getResource("/resources/loseps.jpg"));
		
		
		background = new JLabel("", img, JLabel.CENTER);
		background.setBounds(0, 0, 680, 700);

		playerInfo = new JLabel();
		playerInfo.setBounds(20, 10, 200, 100);
		
		bReturn = new JButton("OK");
		bReturn.setBounds(260, 650, 150, 30);
		bReturn.setBackground(Color.WHITE);
		bReturn.addActionListener(this);

		background.add(playerInfo);
		background.add(bReturn);

		this.add(background);		
	}
	
	void changeImage(InfoObj infoObj) {
		if(_ctrl.isDraw()) {
			img = new ImageIcon(getClass().getResource("/resources/draw.png"));
			playerInfo.setText("<html><body>Draw </body></html>");
		}
		else {
			if(_ctrl.isLocalWinner()) img = new ImageIcon(getClass().getResource("/resources/crown2.jpg"));
			else img = new ImageIcon(getClass().getResource("/resources/loseps.jpg"));
			playerInfo.setText("<html><body>Winner: " + infoObj.getWinnerID() + "<br/>" + "Points: " + infoObj.getWinnerPoints() + "</body></html>");
		}
		
		playerInfo.setFont(new Font("helvetica", Font.BOLD, 16));
		playerInfo.setForeground(Color.WHITE);
		
		background.setIcon(img);
	}

	@Override
	public void actionPerformed(ActionEvent e)  {
		if(e.getSource() == bReturn) {
			if(_ctrl.gameFinished())
				MainView.changeToPanel(8);
			else
				MainView.changeToPanel(2);
		}
	}
}
