package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import controller.MainController;
import model.InfoObj;
import model.MatchMaker;
import utils.Time;

public class OptionsMenu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JPanel butPanel;
	
	private JButton bAceptar;
	private JButton bColor, bNombres;
	private JButton bRondas;
	private JPanel AIpan;
	private SelectCronoPanel cronopan;


	private MainController _ctrl;

	OptionsMenu(MainController ctrl) 
	{
		_ctrl = ctrl;
		initPanel();
	}

	void initPanel() {

		butPanel = new JPanel();
		butPanel.setLayout(new BoxLayout(butPanel, BoxLayout.PAGE_AXIS));
		
		JLabel imgLabel;

		this.setLayout(new BorderLayout(0, 75));
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(680, 700));

		ImageIcon img = new ImageIcon(getClass().getResource("/resources/options.png"));
		imgLabel = new JLabel("", img, JLabel.CENTER);


		bColor = MainView.createButton("CAMBIAR COLOR FICHA", 260, 450, 200, 30);
		bNombres = MainView.createButton("CAMBIAR NOMBRES DE LOS JUGADORES", 260, 450, 200, 30);
		bAceptar = MainView.createButton("ACEPTAR", 260, 450, 200, 30);		
		bRondas = MainView.createButton("NUMERO DE RONDAS", 260, 450, 200, 30);  // BOTON PARA NUMERO DE RONDAS
		AIpan = new AIOptionPanel(_ctrl); AIpan.setBackground(Color.BLACK);
		AIpan.setBounds(260, 600, 200, 60);
		cronopan = new SelectCronoPanel(); cronopan.setBackground(Color.BLACK);
				
		bColor.setBackground(Color.WHITE);
		bColor.addActionListener(this);
		bColor.setHorizontalAlignment(JButton.CENTER);
		bColor.setAlignmentX(CENTER_ALIGNMENT);
		
		bNombres.setBackground(Color.WHITE);
		bNombres.addActionListener(this);
		bNombres.setHorizontalAlignment(JButton.CENTER);
		bNombres.setAlignmentX(CENTER_ALIGNMENT);
		
		bAceptar.setBackground(Color.WHITE);
		bAceptar.addActionListener(this);
		bAceptar.setHorizontalAlignment(JButton.CENTER);
		bAceptar.setAlignmentX(CENTER_ALIGNMENT);
		
		bRondas.setBackground(Color.WHITE);
		bRondas.addActionListener(this);
		bRondas.setHorizontalAlignment(JButton.CENTER);
		bRondas.setAlignmentX(CENTER_ALIGNMENT);
		
		butPanel.add(bRondas);
		butPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		butPanel.add(bColor);
		butPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		butPanel.add(bNombres);
		
		butPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JLabel etiquetaIA = new JLabel("IA");
		etiquetaIA.setForeground(Color.BLUE);
		etiquetaIA.setOpaque(true);
		etiquetaIA.setVisible(true);
		etiquetaIA.setAlignmentX(CENTER_ALIGNMENT);
		butPanel.add(etiquetaIA);
		butPanel.add(AIpan);
		butPanel.add(cronopan);

		butPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		butPanel.add(bAceptar);
		butPanel.setBackground(Color.BLACK);
		
		this.add(imgLabel, BorderLayout.PAGE_START);
		this.add(butPanel, BorderLayout.CENTER);
	}
	
	
	void changeColor() {
		Color c = JColorChooser.showDialog(this, "Select color", MatchMaker.defHostColor);
		if(c != null)
			_ctrl.setHostColor(c);
		 c = JColorChooser.showDialog(this, "Select color", MatchMaker.defHostColor);
		if(c != null)
			_ctrl.setEnemyColor(c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bColor)
			changeColor();
		else if(e.getSource() == bAceptar) {
			MainView.changeToPanel(1);
			if(cronopan.isActivated())
				MainView.createCronoPanel(cronopan.timeData());
			else
				MainView.createCronoPanel(new Time(0, 0));
		}
		else if (e.getSource() == bNombres)
			asignarNombres();
		else if(e.getSource() == bRondas)
			asignarRondas();
	}
	
	private void asignarNombres() {
		String nombre1 = (String) JOptionPane.showInputDialog(
				 butPanel,
				 "Escribe tu nombre (Jugador 1):",
				 "Nombre del Jugador 1",
				 JOptionPane.INFORMATION_MESSAGE,
				 null,
				 null, // opciones para seleccionar vacio
				 ""); // opcion por defecto. La dejamos vacia aunque
				 // se puede poner cualquier String
	
		if (nombre1 == null || nombre1.equals(""))
			nombre1="Host Player";
		
		String nombre2 = (String) JOptionPane.showInputDialog(
				 butPanel,
				 "Escribe tu nombre (Jugador 2):",
				 "Nombre del Jugador 2",
				 JOptionPane.INFORMATION_MESSAGE,
				 null,
				 null, // opciones para seleccionar vacio
				 ""); // opcion por defecto. La dejamos vacia aunque
				 // se puede poner cualquier String
		
		
		if (nombre2!=null && !nombre2.equals("")) {
			while(nombre2!= null && nombre2.equalsIgnoreCase(nombre1)) {
				nombre2 = (String) JOptionPane.showInputDialog(
						 butPanel,
						 "Escribe tu nombre, DIFERENTE AL DEL JUGADOR 1 (Jugador 2):",
						 "Nombre del Jugador 2",
						 JOptionPane.INFORMATION_MESSAGE,
						 null,
						 null, // opciones para seleccionar vacio
						 ""); // opcion por defecto. La dejamos vacia aunque
						 // se puede poner cualquier String
			}
			if(nombre2==null)
				nombre2= "Enemy Player";
		}
		else
			nombre2= "Enemy Player";
		InfoObj infoObj = new InfoObj(nombre1, nombre2);
		MainView.updateLabels(infoObj);
		_ctrl.setNamesPLayers(nombre1, nombre2);
	}
	
	private void asignarRondas() {
		String rondas = (String) JOptionPane.showInputDialog(
				 butPanel,
				 "Escribe numero de rondas(Numero)",
				 "Rondas",
				 JOptionPane.INFORMATION_MESSAGE,
				 null,
				 null, // opciones para seleccionar vac�o
				 "");
		_ctrl.setRoundNumber(Integer.parseInt(rondas));
		InfoObj infoObj = new InfoObj(Integer.parseInt(rondas));
		MainView.updateLabels(infoObj);
	}
}
