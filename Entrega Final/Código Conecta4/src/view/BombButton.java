package view;

import java.awt.AlphaComposite;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BombButton extends JButton implements MouseListener{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float alpha = 1.0f;
	
	public BombButton() {
		super();
	    setMargin(new Insets(0, 0, 0, 0));
		setBorder(null);
		setContentAreaFilled(false);
		setVerticalAlignment(JButton.CENTER);
		
		ImageIcon ico = new ImageIcon(getClass().getResource("/resources/nuke.png"));
		Image resize = ico.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
		
		setIcon(new ImageIcon(resize));
		setEnabled(true);
    	addMouseListener(this);
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		repaint();
	}

	public void paintComponent(java.awt.Graphics g) {
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
    	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    	super.paintComponent(g2);
    }

	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		setAlpha(0.5f);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setAlpha(1.0f);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
