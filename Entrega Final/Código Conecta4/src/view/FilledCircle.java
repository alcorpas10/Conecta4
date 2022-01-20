package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class FilledCircle extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Color color;
	public static final int CIRCLE_DIM = 50;
	
	public FilledCircle(Color c) {
		this.setSize(new Dimension(60, 60));
		this.setVisible(true);
		this.setOpaque(false);
		color = c;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawCircle(g);	
	}
	
	private void drawCircle(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;
		int x = (getWidth() -CIRCLE_DIM)/2;
		int y = (getHeight() - CIRCLE_DIM)/2;
			
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);
		g2d.setColor(Color.DARK_GRAY);
		g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.drawOval(x, y, CIRCLE_DIM, CIRCLE_DIM);
		g2d.setColor(color);
		g2d.fillOval(x, y, CIRCLE_DIM, CIRCLE_DIM);
	}
}
