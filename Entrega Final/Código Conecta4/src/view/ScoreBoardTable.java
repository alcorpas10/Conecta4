package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import controller.MainController;
import model.InfoObj;
import model.Jugador;
import observers.Observer;
import utils.Coordinate;

public class ScoreBoardTable  extends AbstractTableModel implements Observer {
	private static final long serialVersionUID = 1L;
	
	private List<Jugador> _list;
	private MainController _mc;
	private String[] columnNames= {"Id", "Points", "Time"};
	
	public ScoreBoardTable(MainController mc) {
		_mc=mc;
		_mc.addObserver(this);
		_list=new ArrayList<Jugador>();
	}
	
	//metodos de AbstractTableModel
	public String getColumnName(int col) {
		return columnNames[col]; // nombres de las columnas
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return _list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {

		case 0:
			s = _list.get(rowIndex).getId();
			break;
		case 1:
			s = _list.get(rowIndex).getPoints();
			break;
		case 2:
			s = _list.get(rowIndex).getTime();
			break;
		
		default:
			assert (false); // should be unreachable
		}
		return s;
	}

	//metodos de Observer
	@Override
	public void update(Color c, Coordinate coor) {}

	@Override
	public void reload(InfoObj infoObj) {}

	@Override
	public void onWinner(InfoObj infoObj) {}

	@Override
	public void onDraw(InfoObj infoObj) {}

	@Override
	public void onNameUpdate(InfoObj infoObj) {}

	@Override
	public void updateScoreboard(List<Jugador> list) {
		_list=list;
	}

	@Override
	public void onAITurn() {}

	@Override
	public void onAIFinish() {}
}