package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import utils.DescendentOrder;
import utils.Parser;
import utils.SortedArrayList;

public class ScoreBoard {

	private List<Jugador> _players;
	private Map<String, Jugador> mapaP;
	private int sizeScore;
	
	public ScoreBoard() {
		_players = new SortedArrayList<Jugador>(new DescendentOrder());
		mapaP = new HashMap<String, Jugador>();
		sizeScore = _players.size();
	}
	
	public void addPlayer(Jugador j) {
		if(mapaP.containsKey(j.getId())) {
			Jugador oldJ= mapaP.get(j.getId());
			if(oldJ.getPoints()<j.getPoints()) {
				_players.remove(oldJ);
				mapaP.remove(j.getId());
				_players.add(j);
				mapaP.put(j.getId(), j);
			}
			else if(oldJ.getPoints()==j.getPoints() &&oldJ.getTime()>=j.getTime()) {
				_players.remove(oldJ);
				mapaP.remove(j.getId());
				_players.add(j);
				mapaP.put(j.getId(), j);
			}
			
		}
		else if(_players.size()<=10)
			_players.add(j);
		else {
			Jugador jF = _players.get(10);
			if(jF.getPoints()<=j.getPoints() && jF.getTime()<=j.getTime()) {
				_players.remove(jF);
				mapaP.remove(jF.getId());
				_players.add(j);
				mapaP.put(j.getId(), j);
			}
		}
	}
	
	public ScoreBoardMemento createScoreMemento() {
		ScoreBoardMemento mm = new ScoreBoardMemento(this._players, this.mapaP, this.sizeScore);
		mm.saveOnFile(new File("saveHighScore.json"));

		return mm;
	}

	public ScoreBoardMemento saveToFile(File f) {
		ScoreBoardMemento mm = new ScoreBoardMemento(this._players, this.mapaP, this.sizeScore);
		mm.saveOnFile(f);
		return mm;
	}
	
	public void loadFromFileMemento() {
		_players.clear();
		mapaP.clear();
		ScoreBoardMemento mm = new ScoreBoardMemento(this._players, this.mapaP, this.sizeScore);
		try {
		JSONObject jo = mm.loadFromFile(new File("saveHighScore.json"));
		
		JSONObject mainobj = new JSONObject();
		mainobj = jo.getJSONObject("HighScores");
		loadDataFromJSON(mainobj);
		} catch (FileNotFoundException e) {
			mm = new ScoreBoardMemento(this._players, this.mapaP, this.sizeScore);
			mm.saveOnFile(new File("saveHighScore.json"));
		}
		
	}
	private void loadDataFromJSON(JSONObject js) {
		JSONArray i = js.getJSONArray("board");
		
		for(int j = 0; j < (int)i.length(); j++) {
			JSONObject o= i.getJSONObject(j);
			Jugador player = Parser.parsePlayerScoreBoard(o);
			_players.add(player);
			mapaP.put(player.getId(), player);
		}
	}
	public List<Jugador> getList(){
		return Collections.unmodifiableList(this._players);
	}
	
	private class ScoreBoardMemento {
		
		private List<Jugador> _players;
		private Map<String, Jugador> mapaP;
		private int sizeScore;
		public ScoreBoardMemento(List<Jugador> _players, Map<String, Jugador> mapaP,  int sizeScore ) {
			this._players=_players;
			this.mapaP=mapaP;
			this.sizeScore=sizeScore;
		}
		
		public void saveOnFile(File f) {
			try {
				FileOutputStream fos = new FileOutputStream(f);
				JSONObject score = new JSONObject();
				JSONObject inner = new JSONObject();
				sizeScore = _players == null ? 0 : _players.size();
				
				JSONArray row = new JSONArray();
				for (int i = 0; i < sizeScore; i++) {
					Jugador sc = _players.get(i);
					String namePlayer = sc.getId();;
					int points = sc.getPoints();
					int time = sc.getTime();
					JSONObject player = new JSONObject();
					player.put("id", namePlayer);
					player.put("points", points);
					player.put("time", time);
					player.put("ai", false);
					row.put(player);
				}
				
				inner.put("board", row);
				score.put("HighScores", inner);

				PrintStream p = new PrintStream(fos);
				String res = score.toString(3);
				p.print(res);
				p.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		public JSONObject loadFromFile(File f) throws FileNotFoundException {
			FileInputStream in;
			JSONObject jo=null;
			
				in = new FileInputStream(f);
				jo = new JSONObject(new JSONTokener(in));
			return jo;
		}

		
	}
}
