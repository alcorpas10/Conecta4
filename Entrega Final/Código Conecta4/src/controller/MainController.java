package controller;

import observers.Observer;
import utils.AILevel;
import utils.Time;
import view.MainView;

import java.awt.Color;
import java.io.File;
import model.MatchMaker;
import model.Tablero;

public class MainController implements Controller {


	private Object memento;
	protected MatchMaker _mt;
	private boolean started;
	
	public MainController(MatchMaker mt)
	{
		_mt = mt;
		started = false;
	}
	
	public void playTurnG(int col)
	{
		if (!started) {
			MainView.startCronoPanel();
			started = true;
		}
		_mt.playTurnG(col);
	}
	
	public Tablero getTablero() //ya buscaremos una solucion menos ilegal
	{
		return _mt.getTablero();
	}
	
	public void save()
	{
		memento = _mt.createMatchMemento();
	}
	
	public void saveToFile(File f)
	{
		memento = _mt.saveToFile(f);
	}
	
	public void loadLastSave()
	{
		_mt.undoToLastSave(memento);
	}
	
	public void loadFromFile(File f)
	{
		_mt.loadFromFile(f);
	}
	
	public void setHostColor(Color c)
	{
		_mt.setHostColor(c);
	}
	
	public void setEnemyColor(Color c) 
	{
		_mt.setEnemyColor(c);
	}
	
	public void addObserver(Observer o)
	{
		_mt.addObserver(o);
	}
	
	public void removeObserver(Observer o)
	{
		_mt.removeObserver(o);
	}
	
	public void setNamesPLayers(String name1, String name2)
	{
		_mt.asignarNombres(name1, name2);
	}
	
	public void setRoundNumber(int roundNumber)
	{
		_mt.setRoundNumber(roundNumber);
	}
	
	public void resetGame()
	{
		_mt.resetGame();
	}
	
	public void reset()
	{
		started = false;
		_mt.reset();
	}
	
	public boolean isLocalWinner()
	{
		return _mt.isLocalWinner();
	}
	
	public boolean gameFinished() 
	{
		return _mt.gameFinished();
	}
	
	public void enableAI(AILevel lev)
	{
		_mt.enableAI(lev);	
	}
	
	public void disableAI()
	{
		_mt.disableAI();
	}
	
	public void setCrono(Time t)
	{
		_mt.setCrono(t);
	}
	
	public void checkTime()
	{
		_mt.checkTime();
	}
	
	public boolean isDraw()
	{
		return _mt.isDraw();
	}
}
