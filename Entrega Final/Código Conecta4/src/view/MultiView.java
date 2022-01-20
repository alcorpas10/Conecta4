package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import controller.MultiController;
import model.MatchMaker;
import model.Tablero;

public class MultiView extends JPanel {
    private static final long serialVersionUID = 1L;
	
	private Tablero tab;
	private MultiController _ct;
	private TextArea textArea;
	private JLabel label;
	private TabPanel tabPanel;
	private Color backColor = new Color(255, 153, 102);
	
	public MultiView(MultiController ct) {
		_ct = ct;
		_ct.setMultiView(this);
		tab = new Tablero();
		initView();
	}
	
	private void initView() {
		this.setPreferredSize(new Dimension(680, 700));
		this.setBackground(backColor);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		label = new JLabel();
		
		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setPreferredSize(new Dimension(650, 60));
		
		tabPanel = new TabPanel(backColor, _ct);
		
		this.add(label);
		this.add(textArea);
		this.add(tabPanel);
		this.setVisible(true);
	}
	
	public void setButtonsEnabled(boolean bool) {
		tabPanel.setButtonsEnabled(bool);
	}
	
	public void ponerFicha(int numPlayer, int posicion) {
		tab.colocarFicha((numPlayer == 1 ? MatchMaker.defHostColor : MatchMaker.defEnemyColor), posicion);
	}

	public void setText(String texto) {
		textArea.setText(texto);
	}
	
	public void appendText(String string) {
		textArea.append(string);
	}
	
	public void newMessage(String s) {
		JOptionPane.showMessageDialog(this, s);
	}
	
	public void setLabelText(String s) {
		label.setText(s);
	}
	
	public void resetTab() {
		tab = new Tablero();
		redraw();
	}
	
	public void redraw() {
		tabPanel.redraw(tab);
	}
}