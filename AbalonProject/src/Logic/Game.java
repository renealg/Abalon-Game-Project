package Logic;

import java.awt.Color;
import java.util.LinkedList;
import Logic.Player.type;

public class Game 
{
	public Player _board[][];
	private int _limitBalls = 3, _AIcountB = 14, _AIcountW = 14/*10*/;
	private boolean _moveFunc = true, _moveFuncAI = true, _justmove = false;
	private type turn = null, nextTurn;
	
	public int deleteI, deleteJ, newI, newJ, _countB = 14, _countW = 14/*10*/;
	public boolean /*_firstClick = false, _secondClick = false,*/ _exeptions = true, _winning = false,
			_stilPlaying = true, _exeptionsAI = true, _stilPlayingAI = true;
	public type _win = type.illegal, _winAI = type.illegal;
	public boolean _alone, _computer;
	//private LinkedList<Motion> _motions;
	
	
	//Description- constructive action that creates the game board,
					//defines each square and places players in the board
	//Input- there is no input
	//Output- there is no output	
 	public Game() 
	{
		_board = new Player[11][15];

		for(int i = 0 ; i < 11 ; i++) 
		{
			for(int j = 0 ; j < 15 ; j++) 
			{
				_board[i][j] = new Player(null, type.empty); // 0 
				
				// illegal place
				if((i<=4 && j<=4 && i+j<5) || (i<=4 && j>=10 && j-i>9) ||
						(i>=6 && j<=4 && i-j>5) || (i>=6 && j>=10 && i+j>19)) {
					_board[i][j] = new Player(null, type.illegal); // -1
		
				}
				if((i==2 && j==7) || (i==3 && j==6) || (i==3 && j==8) || (i==4 && j==5) 
						|| (i==4 && j==7) || (i==4 && j==9) || (i==6 && j==5) || (i==6 && j==7) 
						|| (i==6 && j==9) || (i==7 && j==6) || (i==7 && j==8) || (i==8 && j==7)
						|| (i==5 && j==4) || (i==5 && j==6) || (i==5 && j==8) || (i==5 && j==10)) {
					_board[i][j] = new Player(null, type.illegal); // -1
				}
				
				// the bounds of the board (that the balls fall to)
				if(i+j==5 || j-i==9 || i-j==5 || i+j==19 || ((j>=5 && j<=9) && (i==0 || i==10))) {
					_board[i][j] = new Player(null, type.bound); // 100
				}
			}
		}
		
		// players locations
		for(int i = 5 ; i < 10 ; i++) {
			
			_board[1][i] = new Player(Color.WHITE, type.white); // 1
			_board[9][i] = new Player(Color.BLACK, type.black); // 2
			
			if(i!=6 && i!=8) {
				_board[3][i] = new Player(Color.WHITE, type.white); // 1
				_board[7][i] = new Player(Color.BLACK, type.black); // 2
			}
		}
		for(int i = 4 ; i < 11 ; i++) {
			if(i!=7) {
				_board[2][i] = new Player(Color.WHITE, type.white); // 1
				_board[8][i] = new Player(Color.BLACK, type.black); // 2
			}
		}
		
		
		
		printBoard();
	}

	
 	//Description- action that starts the game 
 	//Input- there is no input
 	//Output- there is no output	
	public void play() 
	{
		//type nextTurn;
		
		if(turn == null) { //the black player starting
			turn = type.black;
		}
		
		/*while(_stilPlaying)
		{
			nextTurn = oneTurn(turn);
			turn = nextTurn;
		}*/
		
		nextTurn = oneTurn(turn);
		turn = nextTurn;
	}
	
	
	//Description- action that starts the game against the computer
	//Input- there is no input
	//Output- there is no output
	public void playAI()
	{
		//Player tempBoard[][] = copyBoard(_board);
		Player tempBoard[][] = new Player[11][15];
		tempBoard =	copyBoard(_board);

		int nextMotionMark = minimax(tempBoard, 3, true);
		
		//_motions = getMotions(tempBoard, true);
		//_motions = getMotions(_board, true);		
		
		Motion nextMotion = searchForMotion(getMotions(tempBoard, true), nextMotionMark);
		
		deleteI = nextMotion.get_deleteI();
		deleteJ = nextMotion.get_deleteJ();
		newI = nextMotion.get_newI();
		newJ = nextMotion.get_newJ();
		
		nextTurn = oneTurn(turn);
		turn = nextTurn;
	}
	
	//Description- action that allows the current turn to play its turn
	//Input- the type of the current player
	//Output- the type of the next turn	
	public type oneTurn(type _player)
	{
		//int deleteI, deleteJ, newI, newJ;
		String color;
		type competitor, turn = _player;
		//Scanner s = new Scanner(System.in);

		boolean success = true;
		
		if(_player == type.white) 
		{
			color = "white";
			competitor = type.black;
		}
		else {
			color = "black";
			competitor = type.white;
		}
		
		/*System.out.print("the player with the " + color + " balls, please enter i and j to delete");
		deleteI = s.nextInt();
		deleteJ = s.nextInt();*/
		
		// trying to move a ball from the other team
		if(_board[deleteI][deleteJ].get_type() == competitor) {
			System.out.print("it's impossible to move a ball of your competitor");
			_exeptions = false;
		}
		else {
			/*System.out.print("now please enter i and j to direction");
			newI = s.nextInt();
			newJ = s.nextInt();*/
	
			// אלכסון מלמטה שמאלה
			// movement from bottom to left & up
			if(deleteJ > newJ && deleteI > newI) {
				move(deleteI, deleteJ, newI, newJ, -1, -1);
			}
			// אלכסון מלמטה ימינה
			// movement from bottom to right & up
			else if(deleteJ < newJ && deleteI > newI) {
				move(deleteI, deleteJ, newI, newJ, -1, 1);
			}
			// אלכסון מלמעלה שמאלה
			// movement from top to left & down
			else if(deleteJ > newJ && deleteI < newI) {
				move(deleteI, deleteJ, newI, newJ, 1, -1);
			}
			// אלכסון מלמעלה ימינה
			// movement from top to right & down
			else {
				move(deleteI, deleteJ, newI, newJ, 1, 1);
			}
			
			if(_moveFunc) {
				if(_win != type.illegal) {
					_stilPlaying = false; 
				}
				else {
					turn = competitor;
				}
			}
			else {
				System.out.print("there was a problem... please do your turn again");
				_exeptions = false;
			}
			_moveFunc = true;
		}
		
		return turn;
	}
	
	
	//Description- action that causes the player's move to happen by calling actions that check 
					// the propriety of the move
 	//Input- the place (i,j) of the delete ball, the place (i,j) of the new ball and
				// the type of the movement in the rows & columns
	//Output- there is no output	
	public void move(int deleteI, int deleteJ, int newI, int newJ, int changeI, int changeJ)
	{
		//int save = 999, temp = 0;
		//type saveT = type.illegal, tempT = type.empty, keepT;
		//Color saveC = null, keepC = null, tempC = null;
		boolean check = true;
		
		// checking the edge cases
		check = checkExceptions(deleteI, deleteJ, newI, newJ);
		if(!check) {
			_moveFunc = false;
			return;
		}
		
		// moving one ball to empty place
		if(_board[newI][newJ].get_type() == type.empty) 
		{						
			moveOneBall(deleteI, deleteJ, newI, newJ);
			
			printBoard();
			return;
		}
				
		// checking the power balance between the black and the white balls
		check = true;//TODO sleep
		check = checkPowerBalance(deleteI, deleteJ, newI, newJ, changeI, changeJ);
		if(!check) {
			_moveFunc = false;
			return;
		}
		
		// moving only two balls (means that the place after is empty)
		if(_board[newI+changeI][newJ+changeJ].get_type() == type.empty) 
		{
			moveTwoBalls(deleteI, deleteJ, newI, newJ, changeI, changeJ);

			printBoard();
			return;
		} // :)
		
		// moving more than two balls
		else 
		{	
			check = true;
			check = moveMoreThanTwoBalls(deleteI, deleteJ, newI, newJ, changeI, changeJ);
			if(!check) {
				_moveFunc = false;
				return;
			}
						
			printBoard();
			return;
		}
	}
		
	
	//Description- action that checks if the movement that the player wants to make is legal or not
	//Input- the place (i,j) of the delete ball and the place (i,j) of the new ball
	//Output- true if the movement id legal or false if illegal
	public boolean checkExceptions(int deleteI, int deleteJ, int newI, int newJ)
	{
		boolean check = true;
		
		// move a ball in a straight line
		if(deleteI == newI || deleteJ == newJ) {
			System.out.println("Not legal move! You can't move in a straight line!");
			check = false;
		} // :)
		
		// move two separated balls
		if(!((deleteI - newI == 1 && deleteJ - newJ == 1) || (deleteI - newI == 1 && deleteJ - newJ == -1) ||
			(deleteI - newI == -1 && deleteJ - newJ == 1) || (deleteI - newI == -1 && deleteJ - newJ == -1))) {		
			System.out.println("Not legal move! You can't move two balls that are not next to each other!");
			check = false;
		} // :)
		
		// move a ball from / to ilegal place
		if(_board[deleteI][deleteJ].get_type() == type.bound || _board[deleteI][deleteJ].get_type() == type.illegal || 
				_board[newI][newJ].get_type() == type.bound || _board[newI][newJ].get_type() == type.illegal) {
			System.out.println("Not legal move! You can't move a ball from / to illegal place!");
			check = false;
		} // :)
			
		// move a ball from an empty place
		if(_board[deleteI][deleteJ].get_type() == type.empty) {
			System.out.println("hi1");

			System.out.println("Not legal move! You can't move an empty place!!");
			System.out.println(deleteI + " " + deleteJ);
			check = false;
		} // :)
		
		// move a white ball back (from bottom to top)
		if((deleteJ > newJ && deleteI > newI) || (deleteJ < newJ && deleteI > newI)) 
		{
			if(_board[deleteI][deleteJ].get_type() == type.white) {
				System.out.println("Not legal move! You can't move white balls back!");
				check = false;
			}
		}
		// move a black ball back (from top to bottom)
		else if((deleteJ > newJ && deleteI < newI) || (deleteJ < newJ && deleteI < newI))
		{
			if(_board[deleteI][deleteJ].get_type() == type.black) {
				System.out.println("Not legal move! You can't move black balls back!");
				check = false;
			}
		}
		
		_exeptions = check;
		return check;
	}
	
	
	//Description- action that checks the power balance between the players if there is a push
					// between the black and white balls in this movement
	//Input- the place (i,j) of the delete ball and the place (i,j) of the new ball and
				// the type of the movement in the rows & columns
	//Output- true if the movement id legal or false if illegal
	public boolean checkPowerBalance(int deleteI, int deleteJ, int newI, int newJ, int changeI, int changeJ)
	{
		int sumB = 0, sumW = 0, i = deleteI, j = deleteJ;
		boolean check = true;
		
		while(_board[i][j].get_type() != type.empty && _board[i][j].get_type() != type.bound 
				&& _board[i][j].get_type() != type.illegal) 
		{
			if(_board[i][j].get_type() == type.white) {
				sumW++;
			}
			else {
				sumB++;
			}
			i += changeI;
			j += changeJ;
		}
		
		// moving from bottom to top - the black balls
		if((deleteJ > newJ && deleteI > newI) || (deleteJ < newJ && deleteI > newI)) 
		{
			if(sumB <= sumW) {
				System.out.println("hi2");

				System.out.println("Not legal move! You can't move balls when the sum of balls of "
						+ "the other player (that in front of you) is the same sum of balls as yours or below!");
				check = false;
			}
			if(sumB > _limitBalls) {
				System.out.println("Not legal move! You can't move more than 3 balls from your team! ");
				check = false;
			}
		}

		// moving from top to bottom - the white balls
		else if((deleteJ > newJ && deleteI < newI) || (deleteJ < newJ && deleteI < newI))
		{
			if(sumW <= sumB) {
				System.out.println("hi22");

				System.out.println("Not legal move! You can't move balls when the sum of balls of "
						+ "the other player (that in front of you) is the same sum of balls as yours or below!");
				check = false;
			}
			if(sumW > _limitBalls) {
				System.out.println("Not legal move! You can't move more than 3 balls from your team! ");
				check = false;
			}
		}
		
		return check;
	}
	
	
	//Description- action that called in a case that one ball has to be moved to an empty place 
	//Input- the place (i,j) of the delete ball and the place (i,j) of the new ball
	//Output- there is no output
	public void moveOneBall(int deleteI, int deleteJ, int newI, int newJ)
	{
		type keepT;
		Color keepC;
		
		keepT = _board[deleteI][deleteJ].get_type();
		keepC = _board[deleteI][deleteJ].get_color();
		
		_board[newI][newJ].set_type(keepT);
		_board[newI][newJ].set_color(keepC);
		
		_board[deleteI][deleteJ].set_type(type.empty);
		_board[deleteI][deleteJ].set_color(null); 		
	}
	
	
	//Description- action that called in a case that only two balls have to be moved to an empty place 
	//Input- the place (i,j) of the delete ball and the place (i,j) of the new ball and
				// the type of the movement in the rows & columns
	//Output- there is no output
	public void moveTwoBalls(int deleteI, int deleteJ, int newI, int newJ, int changeI, int changeJ)
	{
		type saveT, keepT;
		Color saveC, keepC;
		
		saveT = _board[newI][newJ].get_type();
		saveC = _board[newI][newJ].get_color();
		keepT = _board[deleteI][deleteJ].get_type();
		keepC = _board[deleteI][deleteJ].get_color();				
		
		_board[newI][newJ].set_type(keepT);
		_board[newI][newJ].set_color(keepC);
		_board[newI+changeI][newJ+changeJ].set_type(saveT);
		_board[newI+changeI][newJ+changeJ].set_color(saveC);
		
		_board[deleteI][deleteJ].set_type(type.empty);
		_board[deleteI][deleteJ].set_color(null);
	}
	
	
	//Description- action that called in a case that more that two balls have to be moved 
	//Input- the place (i,j) of the delete ball and the place (i,j) of the new ball and
				// the type of the movement in the rows & columns
	//Output- true if the action performed the movement successfully or false if not
	public boolean moveMoreThanTwoBalls(int deleteI, int deleteJ, int newI, int newJ, int changeI, int changeJ)
	{
		int save = 999, temp = 0;
		type saveT = type.illegal, tempT = type.empty;
		Color saveC = null, tempC = null;
		boolean check = false, check1 = true;
		
		while(_board[newI+changeI][newJ+changeJ].get_type() != type.illegal &&
				_board[newI+changeI][newJ+changeJ].get_type() != type.bound && temp != 999) 
		{			
			// first move - clear the place of deletion
			if(save == 999) 
			{
				saveT = _board[newI][newJ].get_type();
				saveC = _board[newI][newJ].get_color();
				tempT = _board[deleteI][deleteJ].get_type();
				tempC = _board[deleteI][deleteJ].get_color();
					
				_board[newI][newJ].set_type(tempT); 
				_board[newI][newJ].set_color(tempC);

				_board[deleteI][deleteJ].set_type(type.empty);
				_board[deleteI][deleteJ].set_color(null);

				newI += changeI;
				newJ += changeJ;
				
				save = 0;
			}

			// not the first move - saving 2 balls every time
			else 
			{
				tempT = _board[newI][newJ].get_type();
				tempC = _board[newI][newJ].get_color();
				
				_board[newI][newJ].set_type(saveT); 
				_board[newI][newJ].set_color(saveC); 
				
				if(_board[newI+changeI][newJ+changeJ].get_type() != type.empty) {
					newI += changeI;
					newJ += changeJ;
					
					saveT=tempT;
					saveC=tempC;
				}
				else {
					_board[newI+changeI][newJ+changeJ].set_type(tempT);
					_board[newI+changeI][newJ+changeJ].set_color(tempC);
					temp = 999;
					check = true;
				}
			}
		}
		
		// case of dropping a ball to the edge
		if(_board[newI+changeI][newJ+changeJ].get_type() == type.bound ||
				_board[newI+changeI][newJ+changeJ].get_type() == type.illegal) 
		{
			check1 = DroppingBall(deleteI, deleteJ, newI, newJ, changeI, changeJ, tempT, tempC);
			if(!check1) {
				_moveFunc = false;
			}
			else {
				check = true;
			}
		}
		
		return check;
	}
	
	
	//Description- action that called in a case that a ball is supposed to fall to the edge, 
					// if it's possible it will fall and see if it's a win
	//Input- the place (i,j) of the delete ball and the place (i,j) of the new ball, the type of 
				// the movement in the rows & columns and the type & color of the ball that left to move
	//Output- true if the action performed the movement successfully or false if not
	public boolean DroppingBall(int deleteI, int deleteJ, int newI, int newJ, int changeI, int changeJ, type tempT, Color tempC)
	{
		boolean check = true;
		
		// checking if there is a try to drop one ball by one ball of the other color
		if(newI - changeI == deleteI && newJ - changeJ == deleteJ && 
				_board[newI][newJ] != _board[deleteI][deleteJ]) {
			System.out.print("Only one ball can't push one ball from the other team!");
			check = false;
		}
		
		if(_board[newI][newJ].get_type() == type.white) 
		{
			_board[newI][newJ].set_type(tempT);
			_board[newI][newJ].set_color(tempC);
			
			_countW--;
			if(_countW == 8) // at the moment that one player has 8 balls left, the other one wins
			{
				System.out.println("The player with the black balls won!");
				_win = type.black;
			}
		}
		else if(_board[newI][newJ].get_type() == type.black) 
		{
			_board[newI][newJ].set_type(tempT); 
			_board[newI][newJ].set_color(tempC);
			
			_countB--;
			if(_countB == 8) // at the moment that one player has 8 balls left, the other one wins
			{
				System.out.println("The player with the white balls won!");
				_win = type.white;
			}
		}							
		
		return check;
	}
	
	
	/////////////// AI functions ////////////////
	
	//Description- action that calculate the motion mark
	//Input- the AI board, the place (i,j) of the delete ball and the place (i,j) of the new ball 
	//Output- the motion mark
	public int motionMark(Player[][] aiBoard, int deleteI, int deleteJ, int newI, int newJ)
	{
		int mark = 0;
		
		// movement from bottom to left & up
		if(deleteJ > newJ && deleteI > newI) {
			if(newI-1 < 11 && newI-1 >=0 && newJ-1 < 15 && newJ-1 >= 0) {
				if(aiBoard[newI-1][newJ-1].get_type() == type.empty){
					mark+=10;
				}
			}		
			if(newJ < 10 && newJ > 4 && newI < 6 && newI > 2) {
				mark+=700;
			}
			boolean check = isFall(aiBoard, deleteI, deleteJ, newI, newJ, -1, -1);
			if(check){
				mark+=1000;
			}
			if(_justmove) {
				mark+=50;
			}
			check = false;
			check = escape(aiBoard, newI, newJ, deleteI, deleteJ, -1, -1);
			if(check){
				mark+=2000;
			}
		}
		// movement from bottom to right & up
		else if(deleteJ < newJ && deleteI > newI) {
			if(newI-1 < 11 && newI-1 >=0 && newJ+1 < 15 && newJ+1 >= 0) {
				if(aiBoard[newI-1][newJ+1].get_type() == type.empty) {
					mark+=10;			
				}	
			}
			if(newJ < 10 && newJ > 4 && newI < 6 && newI > 2){
				mark+=700;
			}
			boolean check = isFall(aiBoard, deleteI, deleteJ, newI, newJ, -1, 1);
			if(check){
				mark+=1000;
			}
			if(_justmove) {
				mark+=50;
			}
			check = false;
			check = escape(aiBoard, newI, newJ, deleteI, deleteJ, -1, 1);
			if(check){
				mark+=2000;
			}
		}
		// movement from top to left & down
		else if(deleteJ > newJ && deleteI < newI) {
			if(newI+1 < 11 && newI+1 >=0 && newJ-1 < 15 && newJ-1 >= 0) {
				if(aiBoard[newI+1][newJ-1].get_type() == type.empty){
					mark+=10;
				}
			}
			if(newJ < 10 && newJ > 4 && newI < 6 && newI > 2){
				mark+=700;
			}
			boolean check = isFall(aiBoard, deleteI, deleteJ, newI, newJ, 1, -1);
			if(check){
				mark+=1000;
				if(_winning){
					mark+=5000;
				}
			}
			if(_justmove) {
				mark+=50;
			}
			check = false;
			check = escape(aiBoard, newI, newJ, deleteI, deleteJ, 1, -1);
			if(check){
				mark+=2000;
			}
		}
		// movement from top to right & down
		else {
			if(newI+1 < 11 && newI+1 >=0 && newJ+1 < 15 && newJ+1 >= 0) {
				if(aiBoard[newI+1][newJ+1].get_type() == type.empty){
					mark+=10;
				}
			}////////////////////////////////////////////////
			if(newJ < 10 && newJ > 4 && newI < 6 && newI > 2){
				mark+=700;
			}
			boolean check = isFall(aiBoard, deleteI, deleteJ, newI, newJ, 1, 1);
			if(check){
				mark+=1000;
				if(_winning){
					mark+=5000;
				}
			}
			if(_justmove) {
				mark+=50;
			}
			check = false;
			check = escape(aiBoard, newI, newJ, deleteI, deleteJ, 1, 1);
			if(check){
				mark+=2000;
			}
		}
		
		if(aiBoard[newI][newJ].get_type() == type.empty){
			mark+=100;
		}
		
		return mark;
	}

	
	//Description- action that checks if there was if a ball has been dropped to the bounds
	//Input- the AI board, the place (i,j) of the delete ball and the place (i,j) of the new ball and
				// the type of the movement in the rows & columns
	//Output- true if happened or false if not
	public boolean isFall(Player[][] aiBoard, int deleteI, int deleteJ, int newI, int newJ, int changeI, int changeJ)
	{
		while(newI+changeI < 11 && newI+changeI >=0 && newJ+changeJ < 15 && newJ+changeJ >= 0 
				&& aiBoard[newI+changeI][newJ+changeJ].get_type() != type.illegal &&
					aiBoard[newI+changeI][newJ+changeJ].get_type() != type.bound &&
						aiBoard[newI+changeI][newJ+changeJ].get_type() != type.empty) {
			newI+=changeI;
			newJ+=changeJ;
		}
		if(newI+changeI < 11 && newI+changeI >=0 && newJ+changeJ < 15 && newJ+changeJ >= 0
				&& aiBoard[newI+changeI][newJ+changeJ].get_type() == type.empty) {
			_justmove = true;
		}
		if(newI+changeI < 11 && newI+changeI >=0 && newJ+changeJ < 15 && newJ+changeJ >= 0
				&& (aiBoard[newI+changeI][newJ+changeJ].get_type() == type.bound || aiBoard[newI+changeI][newJ+changeJ].get_type() == type.illegal) &&
					aiBoard[deleteI][deleteJ].get_type() != aiBoard[newI][newJ].get_type()) {
			if(aiBoard[newI][newJ].get_type() == type.black && _AIcountB-1 == 8){
				_winning = true;
			}
			else if(aiBoard[newI][newJ].get_type() == type.white && _AIcountW-1 == 8){
				_winning = true;
			}
			return true;
		}
		return false;
	}
	
	
	//Description- action that checks if there was if the movement save the player from falling to the bounds
	//Input- the AI board, the place (i,j) of the delete ball and the place (i,j) of the new ball and
				// the type of the movement in the rows & columns
	//Output- true if happened or false if not
	public boolean escape(Player[][] aiBoard, int newI, int newJ, int deleteI, int deleteJ, int changeI, int changeJ)
	{
		/*if(aiBoard[newI][newJ].get_type() != aiBoard[deleteI-changeI][deleteJ-changeJ].get_type() && 
				aiBoard[deleteI-changeI][deleteJ-changeJ].get_type() != type.illegal &&
				aiBoard[deleteI-changeI][deleteJ-changeJ].get_type() != type.empty) {
			return true;
		}*/
		if(aiBoard[deleteI][deleteJ].get_type() != aiBoard[deleteI-changeI][deleteJ-changeJ].get_type() && 
				aiBoard[deleteI-changeI][deleteJ-changeJ].get_type() != type.illegal &&
				aiBoard[deleteI-changeI][deleteJ-changeJ].get_type() != type.empty &&
				aiBoard[deleteI-changeI][deleteJ-changeJ].get_type() != type.bound) {
			return true;
		}
		return false;
	}
	

	//Description- action that make a list of the current possible movements
	//Input- the AI board and the current player (human or computer)
	//Output- linked list of motions
	public LinkedList<Motion> getMotions(Player[][] board, boolean player)
	{
		LinkedList<Motion> motions = new LinkedList<>();
		
		if(player) {
			for (int i = 0 ; i < 11 ; i++) {
				for (int j = 0; j < 15 ; j++) {
					// the computer is the white player and the white marbles can only go from top to bottom
					if (board[i][j] != null && board[i][j].get_type() == type.white 
							&& checkExceptionsAI(board, i, j, i+1, j+1))
					{
						motions.add(new Motion(i, j, i+1, j+1, motionMark(board, i, j, i+1, j+1)));
					}
					if (board[i][j] != null && board[i][j].get_type() == type.white 
							&& checkExceptionsAI(board, i, j, i+1, j-1))
					{
						motions.add(new Motion(i, j, i+1, j-1, motionMark(board, i, j, i+1, j-1)));
					}
				}
			}
		}
		else {
			for (int i = 0 ; i < 11 ; i++) {
				for (int j = 0; j < 15 ; j++) {
					// the human player is the black player and the black marbles can only go from bottom to top
					if (board[i][j] != null && board[i][j].get_type() == type.black 
							&& checkExceptionsAI(board, i, j, i-1, j-1))
					{
						motions.add(new Motion(i, j, i+1, j+1, motionMark(board, i, j, i+1, j+1)));
					}
					if (board[i][j] != null && board[i][j].get_type() == type.black 
							&& checkExceptionsAI(board, i, j, i+1, j-1))
					{
						motions.add(new Motion(i, j, i+1, j-1, motionMark(board, i, j, i-1, j+1)));
					}
				}
			}
		}
		return motions;
	}

	
	//Description- action that search for a specific motion with its mark
	//Input- linked list of motions and the motion mark
	//Output- the specific motion
	public Motion searchForMotion(LinkedList<Motion> motions, int mark)
	{
		Motion mo = null;
		//Motion mo = motions.get(0);
		//Player [][] temp; 
		for (int i=0 ; i < motions.size() ; i++)
		{
//			System.out.println( motions.get(i).get_mark() + "   " + motions.get(i).get_deleteI() + "   " + motions.get(i).get_deleteJ()
//					+ "   " + motions.get(i).get_newI() + "   " + motions.get(i).get_newJ());
			if (mo == null && motions.get(i).get_mark() == mark) {
			//if (checkExceptionsAI() && motions.get(i).get_mark() == mark) {
				mo = motions.get(i);
				//return mo;
			}
				
		}
		//_motions.addFirst(searchForMotion(_motions, mark)); 
		System.out.println(" ???? " + mark);
		return mo;
	}
	
	
	//Description- action that search for the best motion in the current board (with the highest mark)
	//Input- linked list of motions
	//Output- the best motion
	public Motion bestMotion(LinkedList<Motion> motions)
	{
		Motion mo = motions.getFirst();
		int mark = Integer.MIN_VALUE;
		for (int i=0 ; i < motions.size() ; i++)
		{
			if (motions.get(i).get_mark() > mark && checkExceptionsAI(_board, motions.get(i).get_deleteI(), 
					motions.get(i).get_deleteJ(), motions.get(i).get_newI(), motions.get(i).get_newJ())) {
				
//				System.out.println("****" + motions.get(i).get_mark() + "   " + motions.get(i).get_deleteI() + "   " + motions.get(i).get_deleteJ()
//						+ "   " + motions.get(i).get_newI() + "   " + motions.get(i).get_newJ());
				
				mark = motions.get(i).get_mark();
				mo = motions.get(i);
			}
		}
		//System.out.println(" !!!! " + mark + "  " + mo.get_deleteI() + "  " + mo.get_deleteJ()+ "   " + mo.get_newI() + "   " + mo.get_newJ());
		
		return mo;
	}

	
	//Description- recursive action that takes a few steps forward in the game to find the best move the
					// computer player can make right now to get closer to win
	//Input- the AI board, the depth and the current player (human=false or computer=true)
	//Output- the mark of the best motion 
	public int minimax(Player[][] board, int depth, Boolean maximizingPlayer)
	{
		int min, max, mark, motionMark=0;
		LinkedList<Motion> motions;
		Player temp[][] = new Player[11][15];

		if(depth == 0 || _winAI != type.illegal){
			return bestMotion(getMotions(_board, true)).get_mark();
		}
		if(maximizingPlayer){
			max=Integer.MIN_VALUE;
			motions=getMotions(board,true);
			for(int i=0 ; i<motions.size() ; i++)
			{
//				System.out.println("i : " + i + " d :" + depth);
//				System.out.println();
//				printBoard(board);

				temp = copyBoard(board);
				oneTurnAI(board, motions.get(i).get_deleteI(), motions.get(i).get_deleteJ(), 
						motions.get(i).get_newI(), motions.get(i).get_newJ(), type.white);
				mark=minimax(board, depth-1, false);
				max=Math.max(mark, max);
				//printBoard(board);

				board=copyBoard(temp);
			}
			motionMark=max;
		}
		else{
			min=Integer.MAX_VALUE;
			motions=getMotions(board,false);

			for(int i=0 ; i<motions.size() ; i++)
			{
//				System.out.println("i : " + i + " d :" + depth);
//				System.out.println();
//				printBoard(board);

				temp = copyBoard(board);
				oneTurnAI(board, motions.get(i).get_deleteI(), motions.get(i).get_deleteJ(), 
						motions.get(i).get_newI(), motions.get(i).get_newJ(), type.black);
				mark=minimax(board, depth-1, true);
				min=Math.min(mark, min);
				//printBoard(board);

				board=copyBoard(temp);

			}
			motionMark=min;
		}
		return motionMark;
	}
	
	//Description- action that copy the board that it gets
	//Input- game board
	//Output- copy board
	public Player[][] copyBoard(Player[][] board)
	{
		Player temp[][]  = new Player[11][15];
		for (int i = 0 ; i < 11 ; i++) {
			for (int j = 0 ; j < 15 ; j++) {
				temp[i][j] = new Player(board[i][j].get_color(), board[i][j].get_type());
			}
		}
		return temp;
	}

	
	
		//Description- action that allows the current turn to play its turn
		//Input- the AI board, the place (i,j) of the delete ball, the place (i,j) of the new ball and the type of the current player
		//Output- the type of the next turn	
		public type oneTurnAI(Player[][] aiBoard, int deleteI, int deleteJ, int newI, int newJ, type _player)
		{
			//int deleteI, deleteJ, newI, newJ;
			String color;
			type competitor, turn = _player;
			//Scanner s = new Scanner(System.in);

			boolean success = true;
			
			if(_player == type.white) 
			{
				color = "white";
				competitor = type.black;
			}
			else {
				color = "black";
				competitor = type.white;
			}
			
			/*System.out.print("the player with the " + color + " balls, please enter i and j to delete");
			deleteI = s.nextInt();
			deleteJ = s.nextInt();*/
			
			// trying to move a ball from the other team
			if(aiBoard[deleteI][deleteJ].get_type() == competitor) {
				System.out.print("it's impossible to move a ball of your competitor");
				_exeptionsAI = false;
			}
			else {
				/*System.out.print("now please enter i and j to direction");
				newI = s.nextInt();
				newJ = s.nextInt();*/
		
				// אלכסון מלמטה שמאלה
				// movement from bottom to left & up
				if(deleteJ > newJ && deleteI > newI) {
					moveAI(aiBoard, deleteI, deleteJ, newI, newJ, -1, -1);
				}
				// אלכסון מלמטה ימינה
				// movement from bottom to right & up
				else if(deleteJ < newJ && deleteI > newI) {
					moveAI(aiBoard, deleteI, deleteJ, newI, newJ, -1, 1);
				}
				// אלכסון מלמעלה שמאלה
				// movement from top to left & down
				else if(deleteJ > newJ && deleteI < newI) {
					moveAI(aiBoard, deleteI, deleteJ, newI, newJ, 1, -1);
				}
				// אלכסון מלמעלה ימינה
				// movement from top to right & down
				else {
					moveAI(aiBoard, deleteI, deleteJ, newI, newJ, 1, 1);
				}
				
				//////////////////////////////////////////////////////////////////////////////
				if(_moveFuncAI) {
					if(_winAI != type.illegal) {
						_stilPlayingAI = false; 
					}
					else {
						turn = competitor;
					}
				}
				else {
					System.out.print("there was a problem... please do your turn again");
					_exeptionsAI = false;
				}
				_moveFuncAI = true;
				
			}
			
			return turn;
		}
		
		
		//Description- action that causes the player's move to happen by calling actions that check 
						// the propriety of the move
	 	//Input- the AI board, the place (i,j) of the delete ball, the place (i,j) of the new ball and
					// the type of the movement in the rows & columns
		//Output- there is no output	
		public void moveAI(Player[][] aiBoard, int deleteI, int deleteJ, int newI, int newJ, int changeI, int changeJ)
		{
			int save = 999, temp = 0;
			type saveT = type.illegal, tempT = type.empty, keepT;
			Color saveC = null, keepC = null, tempC = null;
			boolean check = true;
			
			// checking the edge cases
			check = checkExceptionsAI(aiBoard, deleteI, deleteJ, newI, newJ);
			if(!check) {
				_moveFuncAI = false;
				return;
			}
			
			// moving one ball to empty place
			if(aiBoard[newI][newJ].get_type() == type.empty) 
			{						
				moveOneBallAI(aiBoard, deleteI, deleteJ, newI, newJ);
				
				//printBoard();
				return;
			}
					
			// checking the power balance between the black and the white balls
			check = true;
			check = checkPowerBalanceAI(aiBoard, deleteI, deleteJ, newI, newJ, changeI, changeJ);
			if(!check) {
				_moveFuncAI = false;
				return;
			}
			
			// moving only two balls (means that the place after is empty)
			if(aiBoard[newI+changeI][newJ+changeJ].get_type() == type.empty) 
			{
				moveTwoBallsAI(aiBoard, deleteI, deleteJ, newI, newJ, changeI, changeJ);

				//printBoard();
				return;
			} // :)
			
			// moving more than two balls
			else 
			{	
				check = true;
				check = moveMoreThanTwoBallsAI(aiBoard, deleteI, deleteJ, newI, newJ, changeI, changeJ);
				if(!check) {
					_moveFuncAI = false;
					return;
				}
							
				//printBoard();
				return;
			}
		}
			
		
		//Description- action that checks if the movement that the player wants to make is legal or not
		//Input- the AI board, the place (i,j) of the delete ball and the place (i,j) of the new ball
		//Output- true if the movement id legal or false if illegal
		public boolean checkExceptionsAI(Player[][] aiBoard, int deleteI, int deleteJ, int newI, int newJ)
		{
			boolean check = true;
			
			// move a ball in a straight line
			if(deleteI == newI || deleteJ == newJ) {
				//System.out.println("Not legal move! You can't move in a straight line!");
				check = false;
			} // :)
			
			// move two separated balls
			if(!((deleteI - newI == 1 && deleteJ - newJ == 1) || (deleteI - newI == 1 && deleteJ - newJ == -1) ||
				(deleteI - newI == -1 && deleteJ - newJ == 1) || (deleteI - newI == -1 && deleteJ - newJ == -1))) {		
				//System.out.println("Not legal move! You can't move two balls that are not next to each other!");
				check = false;
			} // :)
			
			// move a ball from / to ilegal place
			if(aiBoard[deleteI][deleteJ].get_type() == type.bound || aiBoard[deleteI][deleteJ].get_type() == type.illegal || 
					aiBoard[newI][newJ].get_type() == type.bound || aiBoard[newI][newJ].get_type() == type.illegal) {
				//System.out.println("Not legal move! You can't move a ball from / to illegal place!");
				check = false;
			} // :)
				
			// move a ball from an empty place
			if(aiBoard[deleteI][deleteJ].get_type() == type.empty) {
				//System.out.println("Not legal move! You can't move an empty place!!");
				check = false;
			} // :)
			
			// move a white ball back (from bottom to top)
			if((deleteJ > newJ && deleteI > newI) || (deleteJ < newJ && deleteI > newI)) 
			{
				if(aiBoard[deleteI][deleteJ].get_type() == type.white) {
					//System.out.println("Not legal move! You can't move white balls back!");
					check = false;
				}
			}
			// move a black ball back (from top to bottom)
			else if((deleteJ > newJ && deleteI < newI) || (deleteJ < newJ && deleteI < newI))
			{
				if(aiBoard[deleteI][deleteJ].get_type() == type.black) {
					//System.out.println("Not legal move! You can't move black balls back!");
					check = false;
				}
			}
			
			_exeptionsAI = check;
			return check;
		}
		
		
		//Description- action that checks the power balance between the players if there is a push
						// between the black and white balls in this movement
		//Input- the AI board, the place (i,j) of the delete ball and the place (i,j) of the new ball and
					// the type of the movement in the rows & columns
		//Output- true if the movement id legal or false if illegal
		public boolean checkPowerBalanceAI(Player[][] aiBoard, int deleteI, int deleteJ, int newI, int newJ, int changeI, int changeJ)
		{
			int sumB = 0, sumW = 0, i = deleteI, j = deleteJ;
			boolean check = true;
			
			while(aiBoard[i][j].get_type() != type.empty && aiBoard[i][j].get_type() != type.bound 
					&& aiBoard[i][j].get_type() != type.illegal) 
			{
				if(aiBoard[i][j].get_type() == type.white) {
					sumW++;
				}
				else {
					sumB++;
				}
				i += changeI;
				j += changeJ;
			}
			
			// moving from bottom to top - the black balls
			if((deleteJ > newJ && deleteI > newI) || (deleteJ < newJ && deleteI > newI)) 
			{
				if(sumB <= sumW) {
					//System.out.println("Not legal move! You can't move balls when the sum of balls of "
						//	+ "the other player (that in front of you) is the same sum of balls as yours or below!");
					check = false;
				}
				if(sumB > _limitBalls) {
					//System.out.println("Not legal move! You can't move more than 3 balls from your team! ");
					check = false;
				}
			}

			// moving from top to bottom - the white balls
			else if((deleteJ > newJ && deleteI < newI) || (deleteJ < newJ && deleteI < newI))
			{
				if(sumW <= sumB) {
				//	System.out.println("Not legal move! You can't move balls when the sum of balls of "
					//		+ "the other player (that in front of you) is the same sum of balls as yours or below!");
					check = false;
				}
				if(sumW > _limitBalls) {
				//	System.out.println("Not legal move! You can't move more than 3 balls from your team! ");
					check = false;
				}
			}
			
			return check;
		}
		
		
		//Description- action that called in a case that one ball has to be moved to an empty place 
		//Input- the AI board, the place (i,j) of the delete ball and the place (i,j) of the new ball
		//Output- there is no output
		public void moveOneBallAI(Player[][] aiBoard, int deleteI, int deleteJ, int newI, int newJ)
		{
			type keepT;
			Color keepC;
			
			keepT = aiBoard[deleteI][deleteJ].get_type();
			keepC = aiBoard[deleteI][deleteJ].get_color();
			
			aiBoard[newI][newJ].set_type(keepT);
			aiBoard[newI][newJ].set_color(keepC);
			
			aiBoard[deleteI][deleteJ].set_type(type.empty);
			aiBoard[deleteI][deleteJ].set_color(null); 		
		}
		
		
		//Description- action that called in a case that only two balls have to be moved to an empty place 
		//Input- the AI board, the place (i,j) of the delete ball and the place (i,j) of the new ball and
					// the type of the movement in the rows & columns
		//Output- there is no output
		public void moveTwoBallsAI(Player[][] aiBoard, int deleteI, int deleteJ, int newI, int newJ, int changeI, int changeJ)
		{
			type saveT, keepT;
			Color saveC, keepC;
			
			saveT = aiBoard[newI][newJ].get_type();
			saveC = aiBoard[newI][newJ].get_color();
			keepT = aiBoard[deleteI][deleteJ].get_type();
			keepC = aiBoard[deleteI][deleteJ].get_color();				
			
			aiBoard[newI][newJ].set_type(keepT);
			aiBoard[newI][newJ].set_color(keepC);
			aiBoard[newI+changeI][newJ+changeJ].set_type(saveT);
			aiBoard[newI+changeI][newJ+changeJ].set_color(saveC);
			
			aiBoard[deleteI][deleteJ].set_type(type.empty);
			aiBoard[deleteI][deleteJ].set_color(null);
		}
		
		
		//Description- action that called in a case that more that two balls have to be moved 
		//Input- the AI board, the place (i,j) of the delete ball and the place (i,j) of the new ball and
					// the type of the movement in the rows & columns
		//Output- true if the action performed the movement successfully or false if not
		public boolean moveMoreThanTwoBallsAI(Player[][] aiBoard, int deleteI, int deleteJ, int newI, int newJ, int changeI, int changeJ)
		{
			int save = 999, temp = 0;
			type saveT = type.illegal, tempT = type.empty;
			Color saveC = null, tempC = null;
			boolean check = false, check1 = true;
			
			while(aiBoard[newI+changeI][newJ+changeJ].get_type() != type.illegal &&
					aiBoard[newI+changeI][newJ+changeJ].get_type() != type.bound && temp != 999) 
			{			
				// first move - clear the place of deletion
				if(save == 999) 
				{
					saveT = aiBoard[newI][newJ].get_type();
					saveC = aiBoard[newI][newJ].get_color();
					tempT = aiBoard[deleteI][deleteJ].get_type();
					tempC = aiBoard[deleteI][deleteJ].get_color();
						
					aiBoard[newI][newJ].set_type(tempT); 
					aiBoard[newI][newJ].set_color(tempC);

					aiBoard[deleteI][deleteJ].set_type(type.empty);
					aiBoard[deleteI][deleteJ].set_color(null);

					newI += changeI;
					newJ += changeJ;
					
					save = 0;
				}

				// not the first move - saving 2 balls every time
				else 
				{
					tempT = aiBoard[newI][newJ].get_type();
					tempC = aiBoard[newI][newJ].get_color();
					
					aiBoard[newI][newJ].set_type(saveT); 
					aiBoard[newI][newJ].set_color(saveC); 
					
					if(aiBoard[newI+changeI][newJ+changeJ].get_type() != type.empty) {
						newI += changeI;
						newJ += changeJ;
					}
					else {
						aiBoard[newI+changeI][newJ+changeJ].set_type(tempT);
						aiBoard[newI+changeI][newJ+changeJ].set_color(tempC);
						temp = 999;
						check = true;
					}
				}
			}
			
			// case of dropping a ball to the edge
			if(aiBoard[newI+changeI][newJ+changeJ].get_type() == type.bound ||
					aiBoard[newI+changeI][newJ+changeJ].get_type() == type.illegal) 
			{
				check1 = DroppingBall(deleteI, deleteJ, newI, newJ, changeI, changeJ, tempT, tempC);
				if(!check1) {
					_moveFuncAI = false;
				}
				else {
					check = true;
				}
			}
			
			return check;
		}
		
		
		//Description- action that called in a case that a ball is supposed to fall to the edge, 
						// if it's possible it will fall and see if it's a win
		//Input- the AI board, the place (i,j) of the delete ball and the place (i,j) of the new ball, the type of 
					// the movement in the rows & columns and the type & color of the ball that left to move
		//Output- true if the action performed the movement successfully or false if not
		public boolean DroppingBallAI(Player[][] aiBoard, int deleteI, int deleteJ, int newI, int newJ, int changeI, int changeJ, type tempT, Color tempC)
		{
			boolean check = true;
			
			// checking if there is a try to drop one ball by one ball of the other color
			if(newI - changeI == deleteI && newJ - changeJ == deleteJ && 
					aiBoard[newI][newJ] != aiBoard[deleteI][deleteJ]) {
				System.out.print("Only one ball can't push one ball from the other team!");
				check = false;
			}
			
			if(aiBoard[newI][newJ].get_type() == type.white) 
			{
				aiBoard[newI][newJ].set_type(tempT);
				aiBoard[newI][newJ].set_color(tempC);
				
				_AIcountW--;
				if(_AIcountW == 8) // at the moment that one player has 8 balls left, the other one wins
				{
					System.out.println("The player with the black balls won!");
					_winAI = type.black;
				}
			}
			else if(aiBoard[newI][newJ].get_type() == type.black) 
			{
				aiBoard[newI][newJ].set_type(tempT); 
				aiBoard[newI][newJ].set_color(tempC);
				
				_AIcountB--;
				if(_AIcountB == 8) // at the moment that one player has 8 balls left, the other one wins
				{
					System.out.println("The player with the white balls won!");
					_winAI = type.white;
				}
			}							
			
			return check;
		}
		
	
	
	public void printBoard() // by color
	{
		for(int i = 0 ; i < 11 ; i++) 
		{
			for(int j = 0 ; j < 15 ; j++) 
			{
				if(_board[i][j].get_color() == Color.WHITE) {
					System.out.print("W  ");
				}
				if(_board[i][j].get_color() == Color.BLACK) {
					System.out.print("B  ");
				}
				if(_board[i][j].get_type() == type.empty) {
					System.out.print("   ");
				}
				if(_board[i][j].get_type() == type.illegal) {
					System.out.print("-  ");
				}
				if(_board[i][j].get_type() == type.bound) {
					System.out.print("F  ");
				}
			}
			System.out.println();
		}
	}
	
	public void printBoard(Player[][] board) // by color
	{
		for(int i = 0 ; i < 11 ; i++) 
		{
			for(int j = 0 ; j < 15 ; j++) 
			{
				if(board[i][j].get_color() == Color.WHITE) {
					System.out.print("W  ");
				}
				if(board[i][j].get_color() == Color.BLACK) {
					System.out.print("B  ");
				}
				if(board[i][j].get_type() == type.empty) {
					System.out.print("   ");
				}
				if(board[i][j].get_type() == type.illegal) {
					System.out.print("-  ");
				}
				if(board[i][j].get_type() == type.bound) {
					System.out.print("F  ");
				}
			}
			System.out.println();
			

		}
		
		System.out.println();
		System.out.println();
		System.out.println();
	}

	public int getCountB() {
		return _countB;
	}

	public void setCountB(int countB) {
		this._countB = countB;
	}

	public int getCountW() {
		return _countW;
	}

	public void setCountW(int countW) {
		this._countW = countW;
	}
}
