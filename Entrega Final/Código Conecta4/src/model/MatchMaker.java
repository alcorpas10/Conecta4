package model;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import observers.*;
import utils.AILevel;
import utils.Coordinate;
import utils.Parser;
import utils.PlayerFactory;
import utils.PlayerType;
import utils.Time;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MatchMaker implements Observable<Observer> {

	public static final Color defHostColor = Color.RED, defEnemyColor = Color.YELLOW;

	public static final String defHostId = "Host Player", defEnemyId = "Enemy Player";
	public static final String defHostColorName = "R", defEnemyColorName = "Y";

	public static final int defRoundsNumber = 5;

	private int wonHostRounds, wonEnemyRounds;

	private int round, rondactual;

	private static Tablero tab;
	public static Jugador host, enemy;
	private static CronoGame crono;

	private List<Observer> obs;
	private static int nextTurn;

	private boolean AIChosen;
	private boolean draw;
	
	private ScoreBoard Hboard;
	private List<Jugador> _list;

	public MatchMaker() {
		tab = new Tablero();
		host = PlayerFactory.createHJugador(defHostId, defHostColor, defHostColorName); // los nombres y colores se configuran luego
		enemy = PlayerFactory.createHJugador(defEnemyId, defEnemyColor, defEnemyColorName);
		crono = new CronoGame(new Time(0, 0)); //default
		wonEnemyRounds = 0;
		wonHostRounds = 0;
		nextTurn = 0;
		obs = new ArrayList<Observer>();
		round = defRoundsNumber;
		rondactual = 1;
		AIChosen = false;
		draw = false;
		Hboard = new ScoreBoard();
		_list=new ArrayList<>();
	}

	public void playTurnG(int col) {
		Color color = Color.BLACK;
		Jugador win = null;
		int fila;
		
		Hboard.loadFromFileMemento();
		
		if(nextTurn==0) {
			InfoObj infoObj = new InfoObj(host.getId(), enemy.getId(), rondactual, round, wonHostRounds, wonEnemyRounds, -1 , "");
			for (Observer o : obs)
				o.reload(infoObj);
		}
		
		if (!finished() && !gameFinished()) {
			if (nextTurn % 2 == 0) {
				fila = host.playTurn(tab, col);
				win = tab.chequearGanador(host);
				color = host.getColor();
				for (Observer o : obs)
					o.update(color, new Coordinate(fila, col));

			} else {
				if (AIEnabled()) {
					for (Observer o : obs)
						o.onAITurn();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				fila = enemy.playTurn(tab, col);
				
				color = enemy.getColor();
				col = enemy.getNextCol();
				
				for (Observer o : obs) {
					o.update(color, new Coordinate(fila, col));
					o.onAIFinish();
				}
				
				win = tab.chequearGanador(enemy);
				try {
					if(win!=null &&AIEnabled())
						Thread.sleep(1500);	//Para ver con que ficha gana la IA
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (win != null) {
				
				setWinner(win, crono.getSeconds());
				nextTurn = 0;
				rondactual++;
				win.addPoints();
				InfoObj infoObj = new InfoObj(host.getId(), enemy.getId(), rondactual, round, wonHostRounds, wonEnemyRounds, win.getPoints(), win.getId());

				for (Observer o : obs)
					o.onWinner(infoObj);
				Hboard.addPlayer(win);
				Hboard.createScoreMemento();
			}
			else if(tab.fullBoard()) {
				draw = true;
			}
			else {
				nextTurn++;
			}
			if(AIEnabled() && nextTurn%2 !=0) {
				this.playTurnG(col);
			}
		}
		
		_list=Hboard.getList();
		for (Observer o : obs)
			o.updateScoreboard(_list);
	}

	public boolean isDraw() {
		return draw;
	}
	
	public void checkTime() {
		if(crono.finishedTime()) {
			if(!hostWins() && !enemyWins()) {
				draw = true;
			}
			else draw = false;
		}
		
		if(draw) {
			InfoObj infoObj = new InfoObj(host.getId(), enemy.getId(), rondactual, round, wonHostRounds, wonEnemyRounds, -1 , "");

			for(Observer o: obs) {
				o.onDraw(infoObj);
			}
			draw = false;
		}
	}

	public static boolean hostWins() {
		return tab.chequearGanador(host) != null;
	}
	
	public static boolean enemyWins() {
		return tab.chequearGanador(enemy) != null;
	}
	
	public void setCrono(Time t) {
		crono.changeTime(t);
		crono.toggleCrono(true);
	}

	public void resetGame() {
		tab = new Tablero();
		nextTurn = 0;
		draw = false;
		crono.restart();
	}
	
	public void reset() {
		tab = new Tablero();
		rondactual=0;
		wonHostRounds=0;
		wonEnemyRounds=0;
		draw = false;

		InfoObj infoObj = new InfoObj(host.getId(), enemy.getId(), rondactual, round, wonHostRounds, wonEnemyRounds, -1, "");

		for (Observer o : obs)
			o.reload(infoObj);
	}
	
	public void updateNameLabels() {
		InfoObj infoObj = new InfoObj(host.getId(), enemy.getId(), rondactual, round, wonHostRounds, wonEnemyRounds, -1, "");

		for(Observer o : obs)
			o.onNameUpdate(infoObj);
	}

	static boolean finished() {
		return tab.fullBoard() || tab.connectFour();
	}

	void setWinner(Jugador j, int i) {
		if (j.getId().equals(host.getId())) {
			host.setWinner(true);
			enemy.setWinner(false);
			host.setTime(i);
			wonHostRounds++;
		} else {
			host.setWinner(false);
			enemy.setWinner(true);
			enemy.setTime(i);
			wonEnemyRounds++;
		}
	}

	public int getWonHostRounds() {
		return wonHostRounds;
	}

	public int getWonEnemyRounds() {
		return wonEnemyRounds;
	}

	public boolean isLocalWinner() {
		return host.isWinner();
	}

	public void asignarNombres(String name1, String name2) { // modifica los nombres del host y el del enemy

		host.setId(name1);
		enemy.setId(name2);
		InfoObj infoObj = new InfoObj(host.getId(), enemy.getId(), rondactual, round, wonHostRounds, wonEnemyRounds, -1,"");

		for(Observer o: obs)
			o.onNameUpdate(infoObj);
	}


	public void enableAI(AILevel lev) {
		AIChosen = true;
		enemy = PlayerFactory.createAIJugador(lev);
		updateNameLabels();
	}

	public void disableAI() {
		AIChosen = false;
		enemy = PlayerFactory.createHJugador(defEnemyId, defEnemyColor, defEnemyColorName);
	}

	public boolean AIEnabled() {
		return AIChosen;
	}

	/** MEMENTO **/
	public MatchMemento createMatchMemento() {
		MatchMemento mm = new MatchMemento(tab, host, enemy, nextTurn);
		mm.saveOnFile(new File("saveFile1.json"));
		return mm;
	}

	public MatchMemento saveToFile(File f) {
		MatchMemento mm = new MatchMemento(tab, host, enemy, nextTurn);
		mm.saveOnFile(f);

		return mm;
	}

	public void undoToLastSave(Object mem) {
		MatchMemento mm = (MatchMemento) mem;
		tab = new Tablero(mm.t);
		host = new Jugador(mm.host);
		enemy = mm.enemy.isAI() ? new Jugador(mm.enemy, PlayerType.CPU): new Jugador(mm.enemy, PlayerType.jugador);
		nextTurn = mm.nextTurn;

		InfoObj infoObj = new InfoObj(host.getId(), enemy.getId(), rondactual, round, wonHostRounds, wonEnemyRounds, -1 ,"");

		for (Observer o : obs)
			o.reload(infoObj);
	}

	public void loadFromFile(File f) {
		FileInputStream in;
		try {
			in = new FileInputStream(f);
			JSONObject jo = new JSONObject(new JSONTokener(in));
			JSONObject mainobj = new JSONObject();

			mainobj = jo.getJSONObject("game");
			loadDataFromJSON(mainobj);
			InfoObj infoObj = new InfoObj(host.getId(), enemy.getId(), rondactual, round, wonHostRounds, wonEnemyRounds, -1, "");
			
			for (Observer o : obs)
				o.reload(infoObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void loadDataFromJSON(JSONObject js) {
		nextTurn = js.getInt("next_turn");
		host = new Jugador(Parser.parsePlayer(js.getJSONObject("host")), PlayerType.jugador);
		
		Jugador loaded_enemy = Parser.parsePlayer(js.getJSONObject("enemy"));
		enemy = loaded_enemy.isAI() ? new Jugador(loaded_enemy, PlayerType.CPU) : new Jugador(loaded_enemy, PlayerType.jugador);
		tab = new Tablero(Parser.parseBoard(js.getJSONArray("board")));

	}

	/** 				**/

	private class MatchMemento {

		private Tablero t;
		private Jugador host;
		private Jugador enemy;
		private int nextTurn; // 1 host, 0 enemy

		public MatchMemento(Tablero t, Jugador host, Jugador enemy, int nextTurn) {
			this.t = new Tablero(t);
			this.host = new Jugador(host,PlayerType.jugador);
			this.enemy = enemy.isAI() ? new Jugador(enemy, PlayerType.CPU): new Jugador(enemy,PlayerType.jugador);
			this.nextTurn = nextTurn;
		}

		public void saveOnFile(File f) {
			try {
				FileOutputStream fos = new FileOutputStream(f);
				JSONObject game = new JSONObject();
				JSONObject inner = new JSONObject();

				JSONArray matrix = new JSONArray();
				for (int i = 0; i < Tablero.defNumRows; i++) {
					JSONArray row = new JSONArray();
					for (int j = 0; j < Tablero.defNumCols; j++) {
						String color;
						if (t.getFicha(i, j) == null) {
							color = Integer.toString(Color.WHITE.getRGB());
						} else {
							color = Integer.toString(t.getFicha(i, j).getColor().getRGB());
						}
						row.put(color);
					}
					matrix.put(row);
				}

				inner.put("board", matrix);

				JSONObject player1 = new JSONObject();
				player1.put("id", host.getId());
				player1.put("ai", host.isAI());
				player1.put("color_piece", Integer.toString(host.getColor().getRGB()));
				player1.put("points", host.getPoints());
				inner.put("host", player1);

				player1 = new JSONObject();
				player1.put("id", enemy.getId());
				player1.put("ai", enemy.isAI());
				player1.put("color_piece", Integer.toString(enemy.getColor().getRGB()));
				player1.put("points", enemy.getPoints());
				inner.put("enemy", player1);

				inner.put("next_turn", nextTurn);
				game.put("game", inner);

				PrintStream p = new PrintStream(fos);
				String res = game.toString(3);
				p.print(res);
				p.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addObserver(Observer o) {
		obs.add(o);
		InfoObj infoObj = new InfoObj(host.getId(), enemy.getId(), rondactual, round, wonHostRounds, wonEnemyRounds, -1, "");
		o.reload(infoObj);
	}

	@Override
	public void removeObserver(Observer o) {
		obs.remove(o);
	}

	public Tablero getTablero() {
		return tab;
	}

	public void setHostColor(Color c) {
		host.setColor(c);
	}
	
	public void setEnemyColor(Color c) {
		enemy.setColor(c);
	}

	public void setRoundNumber(int roundNumber) {
		if(roundNumber>0)
			this.round = roundNumber;
		else
			this.round = MatchMaker.defRoundsNumber;
	}

	public boolean gameFinished() {
		if (rondactual > round || (wonHostRounds > (round / 2)) || (wonEnemyRounds > (round / 2)) || crono.finishedTime()) {
			rondactual = 0;
			wonHostRounds = 0;
			wonEnemyRounds = 0;
			
			InfoObj infoObj = new InfoObj(host.getId(), enemy.getId(), rondactual, round, wonHostRounds, wonEnemyRounds,0, "");

			for (Observer o : obs)
				o.reload(infoObj);
			return true;
		} 
		else
			return false;
	}

	//Metodos de testing
	public int getActualRound() {
		return this.rondactual;
	}

	public int getTotalRounds() {
		return this.round;
	}

	public String getHostName() {
		return host.getId();
	}

	public String getEnemyName() {
		return enemy.getId();
	}

	public int getHostPoints() {
		return host.getPoints();
	}

	public int getEnemyPoints() {
		return enemy.getPoints();
	}
}
