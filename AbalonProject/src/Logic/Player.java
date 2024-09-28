package Logic;

import java.awt.Color;

public class Player 
{
	private Color _color;
	public enum type {white, black, illegal, empty, bound};
	private type _type;
	// 1 - white , 2 - black , -1 - illegal place, 0 - empty and legal place, 100 - board bounds
	
	//Description- constructive action that creates a player
	//Input- gets color & type
	//Output- there is no output
	public Player(Color c, type t) {
		_color = c;
		_type = t;
	}

	public Color get_color() {
		return _color;
	}

	public void set_color(Color _color) {
		this._color = _color;
	}

	public type get_type() {
		return _type;
	}

	public void set_type(type _type) {
		this._type = _type;
	}
}
