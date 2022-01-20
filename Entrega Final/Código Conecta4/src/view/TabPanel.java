package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Controller;
import model.Tablero;
import utils.Coordinate;

public class TabPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Controller _ct;
	private JPanel[][] circles;
	private JPanel bubblePanel;
	private JPanel selectorPanel;
	private List<JButton> buttonArray;
	
	public TabPanel(Color backColor, Controller ct) {
		_ct = ct;
		circles = new JPanel[Tablero.defNumRows][Tablero.defNumCols];
		buttonArray = new ArrayList<>();
		initView(backColor);
	}
	
	private void initView(Color backColor) {		
		this.setBackground(backColor);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setPreferredSize(new Dimension(680, 700));
		
		bubblePanel = new JPanel();
		bubblePanel.setLayout(new GridLayout(6, 7));
		bubblePanel.setPreferredSize(new Dimension(650, 500));
		initPanel();
		bubblePanel.setOpaque(false);
		
		selectorPanel = new JPanel();
		selectorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
		selectorPanel.setPreferredSize(new Dimension(650, 80));
		putSelectors(selectorPanel);
		selectorPanel.setOpaque(false);			

		this.add(bubblePanel);
		this.add(selectorPanel);
		this.setVisible(true);
	}
	
	private void putSelectors(JPanel selectorPanel) {		
		for(int i = 0; i < Tablero.defNumCols; i++) {
			int[] cols = {i};
			BombButton b = new BombButton();
			b.setVerticalAlignment(JButton.CENTER);
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {	
					
					Thread t = new Thread("Intended to make an organic game process") {
						public void run() {
							try {
								_ct.playTurnG(cols[0]);
				            }
							catch(Exception ex) {
								JOptionPane.showMessageDialog(bubblePanel, ex.getMessage(), "Error manager", JOptionPane.ERROR_MESSAGE);
								b.setEnabled(false);
							}
				            }
				        };
				        t.start();
	
				}
			});
			
			buttonArray.add(b);
			selectorPanel.add(b);
		}
	}

	private void initPanel() {
		for(int i = 0; i < Tablero.defNumRows; i++) {
			for(int j = 0; j < Tablero.defNumCols; j++) {
				circles[i][j] = new EmptyCircle();
				bubblePanel.add(circles[i][j]);
			}
		}
	}
	
	public void setButtonsEnabled(boolean bool) {
		for (JButton b : buttonArray)
			b.setEnabled(bool);
	}
	
	public void redraw(Tablero t) {
		bubblePanel.removeAll();
		for(int i = 0; i < Tablero.defNumRows; i++) {
			for(int j = 0; j < Tablero.defNumCols; j++) {
				if(t.getFicha(i, j) != null)
					circles[i][j] = new FilledCircle(t.getFicha(i, j).getColor());
				else
					circles[i][j] = new EmptyCircle();
				bubblePanel.add(circles[i][j]);
			}
		}
		bubblePanel.revalidate();
	}
	
	public void drawBoard(Tablero t, Color c, Coordinate coor) {
		bubblePanel.removeAll();
		for(int i = 0; i < Tablero.defNumRows; i++) {
			for(int j = 0; j < Tablero.defNumCols; j++) {
				if(t.getFicha(i, j) != null) {
					if(j == coor.getCol() && i == coor.getRow())
						circles[i][j] = new FilledCircle(c);
				}
				bubblePanel.add(circles[i][j]);
			}
		}
		bubblePanel.revalidate();
	}
	
	public void resetTablero(Tablero t) {
		bubblePanel.removeAll();
        for(int i = 0; i < Tablero.defNumRows; i++) {
            for(int j = 0; j < Tablero.defNumCols; j++) {
                circles[i][j] = new EmptyCircle();
                bubblePanel.add(circles[i][j]);
            }
        }
        bubblePanel.revalidate();
        selectorPanel.removeAll();
        putSelectors(selectorPanel);
        selectorPanel.revalidate();
    }
}
