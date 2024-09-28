package Logic;

public class Motion 
{
	private int _deleteI, _deleteJ, _newI, _newJ, _mark;

	//Description- constructive action that creates a motion
	//Input- gets deleteI & deleteJ & newI & newJ & mark
	//Output- there is no output
	
	public Motion(int _deleteI, int _deleteJ, int _newI, int _newJ, int _mark) {
		super();
		this._deleteI = _deleteI;
		this._deleteJ = _deleteJ;
		this._newI = _newI;
		this._newJ = _newJ;
		this._mark = _mark;
	}

	public int get_deleteI() {
		return _deleteI;
	}

	public void set_deleteI(int _deleteI) {
		this._deleteI = _deleteI;
	}

	public int get_deleteJ() {
		return _deleteJ;
	}

	public void set_deleteJ(int _deleteJ) {
		this._deleteJ = _deleteJ;
	}

	public int get_newI() {
		return _newI;
	}

	public void set_newI(int _newI) {
		this._newI = _newI;
	}

	public int get_newJ() {
		return _newJ;
	}

	public void set_newJ(int _newJ) {
		this._newJ = _newJ;
	}

	public int get_mark() {
		return _mark;
	}

	public void set_mark(int _mark) {
		this._mark = _mark;
	}
}
