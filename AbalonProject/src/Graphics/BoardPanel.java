package Graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Logic.Game;
import Logic.Player.type;

public class BoardPanel extends JPanel implements /*MoveBallsListener,*/ ActionListener
{
	private Square _board[][];
	private ButtonListener _listener;
	private Color _lastColor = null, co;
	private int _lastI = -1, _lastJ = -1, ih, jw;
	private Img _background, _goback, _restart, _turn, _black, _white;
	private JButton _back, _startover;
	private boolean _currentTurn = true, _start = false;
	private JTextArea _countPlayers;
	
	private Game _game;
	
	//Description- constructive action that creates the graphics game board,
					//defines each square and places players in the board, buttons and mouse listener
					//and connects between the logic game board to the graphics game board
	//Input- the logic game board
	//Output- there is no output
	
	public BoardPanel(Game game) 
	{
		super();

		_game = game;
		_countPlayers = new JTextArea("WHITE : " + _game._countW + "  BLACK : " + _game._countB);
		_countPlayers.setBounds(1225, 10, 135, 20);
		_countPlayers.setEditable(false);
		this.add(_countPlayers);
		
		_background = new Img("Images\\board.png", 0, 0, 1400, 700);
		_goback = new Img("Images\\back.png", 0, 0, 100, 50);
		//_restart = new Img("Images\\restart.jpg", 0, 50, 100, 60);
		_turn = new Img("Images\\turn.png", 0, 650, 100, 50);
		_black = new Img("Images\\black.png", 100, 650, 50, 50);
		_white = new Img("Images\\white.png", 100, 650, 50, 50);
		
		_back = new JButton();
		//_startover = new JButton();
		
		_back.setOpaque(false);
		_back.setContentAreaFilled(false);
		_back.setBorderPainted(false);
		
		setLayout(null);
		_back.setBounds(0, 0, 100, 50);
		
		this.add(_back);
		_back.addActionListener(this);
		
		/*_startover.setOpaque(false);
		_startover.setContentAreaFilled(false);
		_startover.setBorderPainted(false);
		
		setLayout(null);
		_startover.setBounds(0, 50, 100, 60);
		
		this.add(_startover);
		_startover.addActionListener(this);*/
		
		_board = new Square[11][15];
		
		for(int i = 0 ; i < 11 ; i++) {
			for(int j = 0 ; j < 15 ; j++) {
								
				//כל משבצת אחרת
				_board[i][j] = new Square(i*64, j*(93+1/3), true, null, 1);
				//_board[i][j] = new Square(i*64, j*(93+1/3), false, Color.pink, 1);

				//לא חוקי מחוץ לגבולות לוח המשחק
				if((i<=4 && j<=4 && i+j<5) || (i<=4 && j>=10 && j-i>9) ||
						(i>=6 && j<=4 && i-j>5) || (i>=6 && j>=10 && i+j>19)) {
					_board[i][j] = new Square(i*64, j*(93+1/3), true, null, -1);

				}
				if((i==2 && j==7) || (i==3 && j==6) || (i==3 && j==8) || (i==4 && j==5) 
						|| (i==4 && j==7) || (i==4 && j==9) || (i==6 && j==5) || (i==6 && j==7)
						|| (i==6 && j==9) || (i==7 && j==6) || (i==7 && j==8) || (i==8 && j==7)
						|| (i==5 && j==4) || (i==5 && j==6) || (i==5 && j==8) || (i==5 && j==10)) {
					_board[i][j] = new Square(i*64, j*(93+1/3), true, null, -1);
				}
				//מסגרת המשחק שאליה נופלים כדורים
				if(i+j==5 || j-i==9 || i-j==5 || i+j==19 || ((j>=5 && j<=9) && (i==0 || i==10))) {
					_board[i][j] = new Square(i*64, j*(93+1/3), true, null, 100);
				}				
			}
		}
		//מיקומי שחקנים
		for(int i = 5 ; i < 10 ; i++) 
		{	
			_board[1][i] = new Square(64, i*(93+1/3), false, Color.WHITE, 1);
			_board[9][i] = new Square(64*9, i*(93+1/3), false, Color.BLACK, 1);
			
			if(i!=6 && i!=8) {
				_board[3][i] = new Square(64*3, i*(93+1/3), false, Color.WHITE, 1);
				_board[7][i] = new Square(64*7, i*(93+1/3), false, Color.BLACK, 1);
			}
		}
		for(int i = 4 ; i < 11 ; i++) {
			if(i!=7) {
				_board[2][i] = new Square(64*2, i*(93+1/3), false, Color.WHITE, 1);
				_board[8][i] = new Square(64*8, i*(93+1/3), false, Color.BLACK, 1);			
			}
		}
		
		/*for(int i = 0 ; i < 11 ; i++) {
			for(int j = 0 ; j < 15 ; j++) {
				//this.add(_board[i][j]);
				_board[i][j].addListener(this); // האזנה אחד לשני
			}
		}*/
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				super.mouseClicked(e);
				
				/*if(!_start) {
					_game.play();
				}*/
				
				if(_game._alone) 
				{
					checkIfPlayer(e);
					
					if(_lastColor == null && _lastI == -1 && _lastI == -1) {
						_lastColor = co;
						_lastI = ih;
						_lastJ = jw;
						
						_game.deleteI = ih;
						_game.deleteJ = jw;
						//_game._firstClick = true;
						
						System.out.println("kkk");
					}
					else {
						_game.newI = ih;
						_game.newJ = jw;
						//_game._secondClick = true;
						
						_lastColor = null;
						_lastI = -1;
						_lastJ = -1;
						
						_game.play();
						
						if(_game._exeptions) { // if the turn was legal
							_currentTurn = !_currentTurn;	
							repaint();
						}	
						if(!_game._stilPlaying) {
							if(_game._win == type.black) {
								_listener.switchPanels(8);
							}
							else {
								_listener.switchPanels(9);
							}
						}
					}
				}
				else {
					checkIfPlayer(e);
					
					if(_lastColor == null && _lastI == -1 && _lastI == -1) {
						_lastColor = co;
						_lastI = ih;
						_lastJ = jw;
						
						_game.deleteI = ih;
						_game.deleteJ = jw;
						//_game._firstClick = true;
						
						System.out.println("kkk");
					}
					else {
						System.out.println("human");
						_game.newI = ih;
						_game.newJ = jw;
						//_game._secondClick = true;
						
						_lastColor = null;
						_lastI = -1;
						_lastJ = -1;
						
						_game.play();
						
						if(_game._exeptions) { // if the turn was legal
							_currentTurn = !_currentTurn;	
							repaint();
						}	
						if(!_game._stilPlaying) {
							if(_game._win == type.black) {
								_listener.switchPanels(8);
							}
							else {
								_listener.switchPanels(9);
							}
						}
						System.out.println("mmm");
						System.out.println("computer");

						_game.playAI();
					}
					
					///////////////////////////////// ai
				}	
			}
		});
		
		this.setLayout(new GridLayout(11,15));
		
		this.setSize(1400, 700);
		this.setVisible(true);
				
		
		
		this.repaint();
	}
	
	public ButtonListener get_listener() {
		return _listener;
	}

	public void set_listener(ButtonListener _listener) {
		this._listener = _listener;
	}
	
	
	//Description- action that checks if the pressed place is a player 
					//and in what position it is
	//Input- the press with the mouse that happen
	//Output- there is no output
	
	public void checkIfPlayer(MouseEvent e)
	{
		boolean flag = false;

		for(int i = 0 ; i < 11 && !flag ; i++) {
			for(int j = 0 ; j < 15 ; j++) {
				if(e.getX() >= _board[i][j].get_width() && e.getX() <= _board[i][j+1].get_width() &&
						e.getY() >= _board[i][j].get_high() && e.getY() <= _board[i+1][j].get_high()) {
					if(_board[i][j].get_legal() == 1) {
						flag = true;
						//move(_board[i][j].get_soliderColor(), i, j);
						co = _board[i][j].get_soliderColor();
						ih = i;
						jw = j;
					}
				}
			}
		}
	}
	
	
	//Description- action that draws the board and pictures
	//Input- painting surface
	//Output- there is no output
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(_background != null) {
			_background.drawImg(g);
		}
		if(_goback != null) {
			_goback.drawImg(g);
		}
		/*if(_restart != null) {
			_restart.drawImg(g);
		}*/
		if(_turn != null && _game._alone) {
			_turn.drawImg(g);
		}
		if(_black != null && _currentTurn && _game._alone) {
			_black.drawImg(g);
		}
		if(_white != null && !_currentTurn && _game._alone) {
			_white.drawImg(g);
		}
		
		
		for(int i = 0 ; i < 11 ; i++) {
			for(int j = 0 ; j < 15 ; j++) {
				if(_game._board[i][j].get_type() == type.empty) {
					_board[i][j].set_soliderColor(null);
					g.setColor(Color.BLACK);
					g.drawOval(_board[i][j].get_width(), _board[i][j].get_high(), 50, 50);
				}
				else if(_game._board[i][j].get_type() == type.black || _game._board[i][j].get_type() == type.white) {
					g.setColor(_game._board[i][j].get_color());
					g.fillOval(_board[i][j].get_width(), _board[i][j].get_high(), 50, 50);		
				}	
			}		
		}
		_countPlayers.setText("WHITE : " + _game._countW + "  BLACK : " + _game._countB);
		_countPlayers.setBounds(1225, 10, 135, 20);
		this.add(_countPlayers);
	}
	
	
	//Description- action that calls the frame (listener) when one button pressed and switch panels
	//Input- the action that happen
	//Output- there is no output
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==_back) {
			_listener.switchPanels(4);
		}
	}	
}

