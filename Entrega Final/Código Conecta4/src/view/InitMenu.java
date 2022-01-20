package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InitMenu extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	JButton bJugar, bMultijugador, bOpciones, bExit;

	public InitMenu() {
		initPanel();
	}

	void initPanel() {

		JLabel background;

		this.setLayout(null);

		ImageIcon img = new ImageIcon(getClass().getResource("/resources/backg2.png"));
		background = new JLabel("", img, JLabel.CENTER);
		background.setBounds(0, 0, 680, 700);

		JLabel label = new JLabel();
		label.setSize(680, 700);
		label.setLocation(0, 0);
		label.setBackground(Color.BLACK);
		label.setOpaque(true);

		bJugar = MainView.createButton("PLAY", 260, 450, 150, 30);
		bJugar.setBackground(Color.WHITE);
		bJugar.addActionListener(this);

		this.add(bJugar);

		bMultijugador = MainView.createButton("MULTIPLAYER", 260, 490, 150, 30);
		bMultijugador.setBackground(Color.WHITE);
		bMultijugador.addActionListener(this);

		this.add(bMultijugador);

		bOpciones = MainView.createButton("OPTIONS", 260, 530, 150, 30);
		bOpciones.setBackground(Color.WHITE);
		bOpciones.addActionListener(this);

		this.add(bOpciones);
		
		bExit = new JButton();
	    Image ima = null;
		try {
			ima = ImageIO.read(getClass().getResource("/resources/exit_button.png"));
			bExit.setIcon(new ImageIcon(ima));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bExit.setBounds(260, 600, 155, 75);
		bExit.setMargin(new Insets(0, 0, 0, 0));
		bExit.addActionListener(this);
		
		this.add(bExit);
		
		this.add(background);
		this.add(label);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bJugar)
			MainView.changeToPanel(2);
		else if(e.getSource() == bOpciones)
			MainView.changeToPanel(3);
		else if(e.getSource() == bMultijugador)
			MainView.changeToPanel(4);
		else if(e.getSource() == bExit)
			System.exit(0);
	}
}
