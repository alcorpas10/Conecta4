package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import utils.Time;

public class SelectCronoPanel extends JPanel implements ItemListener {
	private static final long serialVersionUID = 1L;
	
	private JCheckBox selectCrono;
	private JPanel timePanel;
	private JSpinner minSpin, secSpin;
	private final static Font defFont = new Font("Segoe UI", Font.BOLD, 14);
	private Color backColor = new Color(22, 12, 113);
	private boolean activated;


	SelectCronoPanel() {
		activated = false;
		initPanel();
	}

	void initPanel() 
	{
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		selectCrono = new JCheckBox("Select Timed Game");
		selectCrono.addItemListener(this);
		selectCrono.setSelected(false);
		selectCrono.setBackground(backColor);
		selectCrono.setFont(defFont); selectCrono.setForeground(Color.WHITE);
		
		timePanel = new JPanel();
		timePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		timePanel.setPreferredSize(new Dimension(200, 40));
		timePanel.setMaximumSize(new Dimension(200, 40));
		timePanel.setBackground(backColor);
		
		JLabel info = new JLabel("min");
		info.setFont(defFont); info.setForeground(Color.WHITE);
		
		SpinnerModel sm = new SpinnerNumberModel(5, 1, 59, 1);  //default value,lower bound,upper bound,increment by
		minSpin = new JSpinner(sm);
		minSpin.setFont(defFont);
		sm = new SpinnerNumberModel(0, 0, 59, 1);  
		secSpin = new JSpinner(sm);
		secSpin.setFont(defFont);
		
		timePanel.add(minSpin);
		timePanel.add(info);
		timePanel.add(secSpin);
		info = new JLabel("sec");
		info.setFont(defFont); info.setForeground(Color.WHITE);
		timePanel.add(info);

		timePanel.setVisible(false);
		
		selectCrono.setVisible(true);
		selectCrono.setAlignmentX(CENTER_ALIGNMENT);
		this.add(selectCrono);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(timePanel);
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getItemSelectable() == this.selectCrono) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				selectCrono.setText("Cronogame activated");
				selectCrono.setForeground(Color.GREEN);
				timePanel.setVisible(true);
				activated = true;
			}
			else if(e.getStateChange()==ItemEvent.DESELECTED) {
				selectCrono.setText("Cronogame deactivated");
				selectCrono.setForeground(Color.RED);
				timePanel.setVisible(false);
				activated = false;
			}
		}
	}
	
	public boolean isActivated() {
		 return activated;
	}
	
	public Time timeData() {
		return new Time((Integer)minSpin.getValue(), (Integer)secSpin.getValue());
	}

}
