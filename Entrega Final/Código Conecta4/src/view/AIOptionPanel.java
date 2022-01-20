package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controller.MainController;
import utils.AILevel;

public class AIOptionPanel extends JPanel implements ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainController _ctrl;
	private JCheckBox selectAI;
	private JRadioButton easy, med, hard;
	private ButtonGroup gr;
	private JPanel butPanel;
	private final static Font defFont = new Font("Segoe UI", Font.BOLD, 14);
	private static AILevel lev = AILevel.EASY;
	private Color backColor = new Color(22, 12, 113);

	AIOptionPanel(MainController ct) {
		_ctrl = ct;
		initPanel();
	}

	void initPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		selectAI = new JCheckBox("Select AI");
		selectAI.addItemListener(this);
		selectAI.setSelected(false);
		selectAI.setBackground(backColor);
		selectAI.setFont(defFont); selectAI.setForeground(Color.WHITE);
		
		butPanel = new JPanel();
		butPanel.setLayout(new BoxLayout(butPanel, BoxLayout.PAGE_AXIS));
		butPanel.setBackground(backColor);
		
		easy = new JRadioButton("EASY"); easy.setFont(defFont); easy.setForeground(Color.GREEN);
		med = new JRadioButton("MEDIUM"); med.setFont(defFont); med.setForeground(Color.WHITE);
		hard = new JRadioButton("HARD"); hard.setFont(defFont); hard.setForeground(Color.WHITE);
		
		gr = new ButtonGroup();
		gr.add(easy); gr.add(med); gr.add(hard);
		
		easy.setSelected(true); easy.setBackground(backColor);
		med.setSelected(false); med.setBackground(backColor);
		hard.setSelected(false); hard.setBackground(backColor);
		easy.addItemListener(this);
		med.addItemListener(this);
		hard.addItemListener(this);
		
		butPanel.add(easy);
		butPanel.add(med);
		butPanel.add(hard);
		butPanel.setVisible(false);
		
		selectAI.setVisible(true);
		selectAI.setAlignmentX(CENTER_ALIGNMENT);
		this.add(selectAI);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(butPanel);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getItemSelectable() == this.selectAI) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				selectAI.setText("AI activated");
				selectAI.setForeground(Color.GREEN);
				butPanel.setVisible(true);
				_ctrl.enableAI(lev);
			}
			else if(e.getStateChange()==ItemEvent.DESELECTED) {
				selectAI.setText("AI deactivated");
				selectAI.setForeground(Color.RED);
				butPanel.setVisible(false);
				_ctrl.disableAI();
			}
		}
		else if(e.getItemSelectable() == this.easy) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				easy.setForeground(Color.GREEN);
				_ctrl.enableAI(AILevel.EASY);
			}
			else if(e.getStateChange()==ItemEvent.DESELECTED)
				easy.setForeground(Color.WHITE);
		}
		else if(e.getItemSelectable() == this.med) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				med.setForeground(Color.YELLOW);
				_ctrl.enableAI(AILevel.MEDIUM);
			}
			else if(e.getStateChange()==ItemEvent.DESELECTED)
				med.setForeground(Color.WHITE);
		}
		else if(e.getItemSelectable() == this.hard) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				hard.setForeground(Color.RED);
				_ctrl.enableAI(AILevel.HARD);
			}
			else if(e.getStateChange()==ItemEvent.DESELECTED)
				hard.setForeground(Color.WHITE);
		}
	}
}
