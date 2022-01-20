package view;

import java.awt.*;
/*import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;*/
import java.util.List;

import observers.Observer;
import utils.Coordinate;
import utils.Time;

import javax.swing.*;

import controller.MainController;
import controller.MultiController;
import model.InfoObj;
import model.Jugador;
import model.Tablero;

public class MainView extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;

	private static CardLayout cardl;
	
	private static MainController _ct;
	private MultiController _ctM;
	public static Color backColor = new Color(99, 177, 250);
	
	private static JPanel mainPanel;

	private JPanel menuPanel;
	private JPanel optionPanel;
	private JPanel multiInitPanel;
	private JPanel multiplayerPanel;
	private JPanel winnerPanel;
	private JPanel roundsPanel;
	private JPanel hostPanel;
	private JPanel enemyPanel;
	private static CronoPanel cronoPanel;

	private JPanel gamePanel;
	private TabPanel tabPanel;
	private static JLabel nombrehost;

	private static JLabel nombreenemy;

	private static JLabel rounds;

	private static JLabel pointsHost;

	private static JLabel pointsEnemy;
	private JPanel finalPanel;
	
	public MainView(MainController ct) {
		super("Connect 4!");
		_ct = ct;
		
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
      
		initFrame();
		_ct.addObserver(this);
	}
	
	void initFrame() {
		cardl = new CardLayout();
		mainPanel = new JPanel();
		
		this.setResizable(false);
		this.setSize(680, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);	
		
		ImageIcon ico = new ImageIcon(getClass().getResource("/resources/frameico.png"));
		this.setIconImage(ico.getImage());

		mainPanel.setPreferredSize(new Dimension(680, 700));
		mainPanel.setLayout(cardl);
		mainPanel.setBackground(Color.black);
		mainPanel.setVisible(true);
		
		menuGame();
		gameFrame(_ct.getTablero());
		cardl.show(mainPanel, "1");
		
		this.add(mainPanel);
		this.pack();
		this.setVisible(true);
	}
	
	void menuGame() {
		_ctM = new MultiController();
		menuPanel = new InitMenu();
		optionPanel = new OptionsMenu(_ct);
		multiInitPanel = new MultiInitMenu(_ctM);
		multiplayerPanel = new MultiView(_ctM);
		winnerPanel = new WinnerScreen(_ct);
		finalPanel = new FinalScreen(_ct);
		cronoPanel = new CronoPanel(_ct, new Time(0, 0), true);
		mainPanel.add(menuPanel, "1");
		mainPanel.add(optionPanel, "3");
		mainPanel.add(multiInitPanel,"4");
		mainPanel.add(winnerPanel, "5");
		mainPanel.add(multiplayerPanel,"6");
		mainPanel.add(finalPanel,"8");
	}
		
	void gameFrame(Tablero t) {
		gamePanel = new JPanel();
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));
		gamePanel.setBackground(backColor);
		
		enemyPanel = new JPanel();
		enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.PAGE_AXIS));
		enemyPanel.setBackground(backColor);
		
		hostPanel = new JPanel();
		hostPanel.setLayout(new BoxLayout(hostPanel, BoxLayout.PAGE_AXIS));
		hostPanel.setBackground(backColor);
		
		nombrehost = new JLabel("", JLabel.LEFT);
		nombrehost.setFont(new Font("helvetica", Font.BOLD, 25));
		nombrehost.setForeground(Color.WHITE);
		
		JPanel pointAux = new JPanel(new FlowLayout());
		pointAux.setBackground(backColor);
		pointsHost = new JLabel("", JLabel.CENTER);
		pointsHost.setAlignmentX(CENTER_ALIGNMENT);
		pointsHost.setFont(new Font("helvetica", Font.BOLD, 25));
		pointsHost.setForeground(Color.GREEN);
		pointAux.add(pointsHost);
		
		hostPanel.add(nombrehost);
		hostPanel.add(Box.createRigidArea(new Dimension(0, 0)));
		hostPanel.add(pointAux);
		
		nombreenemy = new JLabel("", JLabel.CENTER);
		nombreenemy.setFont(new Font("helvetica", Font.BOLD, 25));
		nombreenemy.setForeground(Color.WHITE);
		
		JPanel pointAux2 = new JPanel(new FlowLayout());
		pointAux2.setBackground(backColor);
		pointsEnemy = new JLabel("", JLabel.CENTER);
		pointsEnemy.setAlignmentX(CENTER_ALIGNMENT);
		pointsEnemy.setFont(new Font("helvetica", Font.BOLD, 25));
		pointsEnemy.setForeground(Color.RED);
		pointAux2.add(pointsEnemy);
		
		enemyPanel.add(nombreenemy);
		enemyPanel.add(Box.createRigidArea(new Dimension(0, 0)));
		enemyPanel.add(pointAux2);
		
		rounds = new JLabel("", JLabel.CENTER);
		rounds.setFont(new Font("helvetica", Font.BOLD, 25));
		rounds.setForeground(Color.WHITE);
		
		
		roundsPanel=new JPanel();
		roundsPanel.setPreferredSize(new Dimension(680, 70));
		roundsPanel.setMaximumSize(new Dimension(680, 100));
		roundsPanel.setLayout(new BorderLayout(10, 0));
		roundsPanel.setOpaque(false);
		roundsPanel.add(hostPanel, BorderLayout.WEST);
		roundsPanel.add(enemyPanel, BorderLayout.EAST);
		
		JPanel aux = new JPanel();
		cronoPanel.setVisible(false);
		createCronoPanel(new Time(0, 0));
		aux.setLayout(new BoxLayout(aux, BoxLayout.PAGE_AXIS));
		aux.setBackground(backColor);
		rounds.setAlignmentX(CENTER_ALIGNMENT);
		aux.add(rounds);
		aux.add(cronoPanel);
		roundsPanel.add(aux, BorderLayout.CENTER);

		tabPanel = new TabPanel(backColor, _ct);
		
		gamePanel.add(roundsPanel);
		gamePanel.add(tabPanel);
		
		this.setJMenuBar(new MenuBar(_ct));
		gamePanel.setVisible(true);
		mainPanel.add(gamePanel, "2");
	}
	
	
	private void drawBoard(Tablero t, Color c, Coordinate coor) {
		tabPanel.drawBoard(t, c, coor);
	}
	
	
	private void redraw(Tablero t) {
		tabPanel.redraw(t);
	}
	
	@Override
	public void update(Color c, Coordinate coor) {
		drawBoard(_ct.getTablero(), c, coor);
		revalidate();
	}

	@Override
	public void onWinner(InfoObj infoObj) {	
		((WinnerScreen) winnerPanel).changeImage(infoObj);
		((FinalScreen )finalPanel).changeImage();
		MainView.changeToPanel(5);
		_ct.resetGame();
		resetTablero(MainView._ct.getTablero());
		updateLabels(infoObj);
	}
	
	public void onDraw(InfoObj infoObj) {
		((WinnerScreen) winnerPanel).changeImage(infoObj);
		((FinalScreen )finalPanel).changeImage();
		MainView.changeToPanel(8);
		_ct.resetGame();
		resetTablero(MainView._ct.getTablero());
		updateLabels(infoObj);
	}

	private void resetTablero(Tablero t) {
		tabPanel.resetTablero(t);
	}

	@Override
	public void reload(InfoObj infoObj) {
		updateLabels(infoObj);
		redraw(_ct.getTablero());
	}
	
	public static JButton createButton(String caption, int x, int y, int w, int h){
		 JButton button = new JButton(caption); // se crea el boton
		 button.setBounds(x,y,w,h);
		 button.setPreferredSize(new Dimension(w, h));// se coloca y da tamano
		 return button; // se devuelve
	}
	
	public static void changeToPanel(int n) {
		cardl.show(mainPanel, Integer.valueOf(n).toString());
	}
	
	public static void updateLabels(InfoObj infoObj) {
		if (infoObj.getTotalRound() != -1) {
			rounds.setText("ROUND  "+ infoObj.getActualRound() +" OF "+ infoObj.getTotalRound());
			pointsEnemy.setText("" + infoObj.getWonEnemyRounds());
			pointsHost.setText("" + infoObj.getWonHostRounds());
		}
		if(!infoObj.getEnemyName().equals("") && !infoObj.getHostName().equals("")) {
			nombreenemy.setText(infoObj.getEnemyName());
			nombrehost.setText(infoObj.getHostName());
		}
	}
	
	public static void createCronoPanel(Time t) {
		cronoPanel.changeTime(t);
		cronoPanel.setVisible(true);
	}

	public static void startCronoPanel() {
		cronoPanel.start();
	}
	
	@Override
	public void onAITurn() {
		tabPanel.setButtonsEnabled(false);
	}

	@Override
	public void onAIFinish() {
		tabPanel.setButtonsEnabled(true);
	}

	@Override
	public void onNameUpdate(InfoObj infoObj) {
		updateLabels(infoObj);
	}

	@Override
	public void updateScoreboard(List<Jugador> list) {}
}
