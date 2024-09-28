package Graphics;

import javax.swing.JFrame;

import Logic.Game;

public class GameFrame extends JFrame implements ButtonListener
{	
	private Game _game;
	private HomePagePanel _hp;
	private BoardPanel _bp;
	private RulesPagePanel _rp;
	private WhiteWinPanel _wwp;
	private BlackWinPanel _bwp;
	private Img _img;
	
	//Description- constructive action that creates the Farme
	//Input- there is no input
	//Output- there is no output
	public GameFrame() {
		super("ABALON GAME");
		
		_game = new Game();
		_hp = new HomePagePanel(_game);
		_bp = new BoardPanel(_game);
		_rp = new RulesPagePanel();
		_wwp = new WhiteWinPanel();
		_bwp = new BlackWinPanel();
		
		this.add(_hp);
		_hp.set_listener(this);
		_bp.set_listener(this);
		_rp.set_listener(this);
		_wwp.set_listener(this);
		_bwp.set_listener(this);

		this.setSize(1400, 750);
		this.setVisible(true);
		//this.setResizable(false);
	//	this.setLocationRelativeTo(null);
	}

	//Description- action that switch between the panels
	//Input- number of situation
	//Output- there is no output
	@Override
	public void switchPanels(int situ) 
	{
		// מעבר ממסך הבית ללוח המשחק לאחר הלחיצה על כפתור התחלת משחק
		if(situ == 1) // moving from home page to the game board
		{
			this.remove(_hp);
			this.add(_bp);
			repaint();
		}
		 // מעבר ממסך הבית לעמוד ההוראות לאחר לחיצה על כפתור ההוראות
		else if(situ == 2) // moving from home page to the rules page
		{
			this.remove(_hp);
			this.add(_rp);
			repaint();
		}
		// מעבר ממסך ההוראות למסך הבית בחזרה
		else if(situ == 3) // moving from rules page to the home page
		{
			this.remove(_rp);
			this.add(_hp);
			repaint();
		}
		 // מעבר מלוח המשחק אל מסך הבית בחזרה
		else if(situ == 4) // moving from the game board to the home page
		{
			this.remove(_bp);
			this.add(_hp);
			repaint();
		}
		/*else if(situ == 5)
		{
			this.remove(_hp);
			this.add(_bp);
			repaint();
		}*/
		// מעבר ממסך ניצחון של גולות לבנות למסך הבית
		else if(situ == 6) { // moving from white win panel to the home page
			this.remove(_wwp);
			this.add(_hp);
			repaint();
		}
		// מעבר ממסך ניצחון של גולות שחורות למסך הבית
		else if(situ == 7) { // moving from black win panel to the home page
			this.remove(_bwp);
			this.add(_hp);
			repaint();
		}
		// מעבר ממסך המשחק לניצחון גולות שחורות
		else if(situ == 8) { // moving from the game board to white win panel
			this.remove(_bp);
			this.add(_bwp);
			repaint();
		}
		// מעבר ממסך המשחק לניצחון גולות לבנות
		else if(situ == 9) { // moving from the game board to black win panel
			this.remove(_bp);
			this.add(_wwp);
			repaint();
		}
	}
}
