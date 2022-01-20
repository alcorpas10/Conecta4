package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.MultiController;
import launcher.ServerMain;
import view.TextPrompt.Show;

public class MultiInitMenu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JButton aceptar, cancelar;
	private JTextField IP, puerto;
	private JLabel label;
	private static Font mFont = new Font("helvetica", Font.BOLD, 16); 
	private MultiController _ctM;
	
	public MultiInitMenu(MultiController controller) {
		_ctM = controller;
		initView();
	}
	
	private void initView() {
		JLabel background;
		this.setLayout(new BorderLayout());

		this.setBackground(new Color(255, 153, 102));
		ImageIcon img = new ImageIcon(getClass().getResource("/resources/multiplayer.png"));
		background = new JLabel("", img, JLabel.CENTER);
		
		JPanel centro = new JPanel();
		centro.setBackground(new Color(255, 153, 102));
		
		JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
		panel.setBackground(new Color(255, 153, 102));
		
		JLabel aux = new JLabel("Server IP: ");
		aux.setFont(mFont);
		
		panel.add(aux);
		
		//LA CLASE TEXTPROMPT ES PRESTADA DE ROB CAMICK EN: https://tips4java.wordpress.com/2009/11/29/text-prompt/
		IP = new JTextField();
		TextPrompt placeholder = new TextPrompt("XXX.XXX.X.XXX", IP);
		placeholder.setShow(Show.FOCUS_LOST);
	    placeholder.changeAlpha(0.5f);
	    placeholder.setFont(mFont.deriveFont(Font.PLAIN));
		panel.add(IP);
		
		aux = new JLabel("Server port: ");
		aux.setFont(mFont);
		panel.add(aux);
		
		puerto = new JTextField(String.valueOf(ServerMain.PORTNUMBER));
		placeholder = new TextPrompt("1 ~ 65535", puerto);
		placeholder.setShow(Show.FOCUS_LOST);
	    placeholder.changeAlpha(0.5f);
	    placeholder.setFont(mFont.deriveFont(Font.PLAIN));
		panel.add(puerto);
		
		JPanel panelAbajo = new JPanel(new GridLayout(1, 2, 5, 5));
		panelAbajo.setBackground(new Color(255, 153, 102));
		
		aceptar = MainView.createButton("ACCEPT", 260, 450, 150, 30);
		aceptar.addActionListener(this);
		cancelar = MainView.createButton("CANCEL", 260, 490, 150, 30);
		cancelar.addActionListener(this);
		panelAbajo.add(aceptar);
		panelAbajo.add(cancelar);
		panel.add(panelAbajo);
		centro.add(panel);
		
		label = new JLabel();
		
		this.add(label, BorderLayout.PAGE_END);
		this.add(centro, BorderLayout.CENTER);
		this.add(background, BorderLayout.PAGE_START);
	}
	
    @Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == aceptar) {
			label.setText("Conectando al servidor, por favor espere...");
			cancelar.setEnabled(false);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						_ctM.iniciarSocket(Integer.parseInt(puerto.getText()), IP.getText());
						MainView.changeToPanel(6);
						label.setText("Si quieres volver a jugar pulsa de nuevo ACCEPT");
						cancelar.setEnabled(true);
					}
					catch(Exception ex) {
						label.setText("Revisa la entrada e intentalo de nuevo");
						cancelar.setEnabled(true);
					}
				}
			});
		}
		else if(e.getSource() == cancelar)
			MainView.changeToPanel(1);
	}
}
