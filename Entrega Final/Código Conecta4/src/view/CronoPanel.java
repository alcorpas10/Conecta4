package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.MainController;
import utils.Time;

public class CronoPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainController _ctrl;
	private Timer t;
	private Time minandsec;
	private boolean chrono;
	
	private JLabel time_lab;
	
	CronoPanel(MainController ct, Time ti, boolean type) {
		_ctrl = ct;
		minandsec = ti;
		chrono = type;
		t = new Timer(1000, this);
		initPanel();
		_ctrl.setCrono(minandsec);
	}
	
	private void initPanel() {
		this.setBackground(MainView.backColor);
		time_lab = new JLabel("", JLabel.CENTER);
		
		String mins = String.format("%02d", new Integer(minandsec.getMinutes())) ;
		String secs = String.format("%02d", new Integer(minandsec.getSec()));
		
		time_lab.setText(mins + ":" + secs);
		time_lab.setFont(new Font("helvetica", Font.BOLD, 25));
		time_lab.setForeground(Color.YELLOW);
		time_lab.setAlignmentX(CENTER_ALIGNMENT);
		this.add(time_lab);
	}
	
	public void changeTime(Time t) {
		this.minandsec = t;
		String mins = String.format("%02d", new Integer(minandsec.getMinutes())) ;
		String secs = String.format("%02d", new Integer(minandsec.getSec()));
		if (minandsec.getMinutes() != 0 || minandsec.getSec() != 0)
			chrono = false;
		time_lab.setText(mins + ":" + secs);
		_ctrl.setCrono(minandsec);
	}
	
	public void start() {
		t.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)  {	
		if(!minandsec.stopped()) {
			if (!chrono)
				minandsec.diminish();
			else
				minandsec.increase();
			time_lab.setText(String.format("%02d", new Integer(minandsec.getMinutes()))  + " : " + String.format("%02d", new Integer(minandsec.getSec())));
		}
		_ctrl.checkTime();
	}
}
