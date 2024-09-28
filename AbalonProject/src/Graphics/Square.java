package Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Square // משבצת
{
	private int _width; // w = j
	private int _high; // h = i
	private boolean _isEmpty;
	private Color _soliderColor;
	private int _legal;
	private LinkedList <MoveBallsListener> _listeners;
	
	//Description- constructive action that creates a square
	//Input- gets high, width, if empty, color, if legal
	//Output- there is no output
	public Square(int high, int width, boolean empty, Color sc, int legal) {
		super();
		
		_width = width;
		_high = high;
		_isEmpty = empty;
		_soliderColor = sc;
		_legal = legal;
		_listeners = new LinkedList <MoveBallsListener>();
	}
	
	//Description- action that add to the list of listeners a listener
	//Input- listener in type of MoveBallsListener
	//Output- there is no output
	public void addListener(MoveBallsListener mbl) {
		_listeners.add(mbl);
	}
	
	public int get_width() {
		return _width;
	}

	public void set_width(int _width) {
		this._width = _width;
	}

	public int get_high() {
		return _high;
	}

	public void set_high(int _high) {
		this._high = _high;
	}

	public boolean is_isEmpty() {
		return _isEmpty;
	}

	public void set_isEmpty(boolean _isEmpty) {
		this._isEmpty = _isEmpty;
	}

	public Color get_soliderColor() {
		return _soliderColor;
	}

	public void set_soliderColor(Color _soliderColor) {
		this._soliderColor = _soliderColor;
	}

	public int get_legal() {
		return _legal;
	}

	public void set_legal(int _legal) {
		this._legal = _legal;
	}
}
