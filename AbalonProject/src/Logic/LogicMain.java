package Logic;

public class LogicMain 
{
	public static void main(String[] args) 
	{
		Game g = new Game();
		
		g.play();

	}
}

/*

	0  1  2  3  4  5  6  7  8  9 10 11 12 13 14

0	-  -  -  -  -  F  F  F  F  F  -  -  -  -  -  
1	-  -  -  -  F  W  W  W  W  W  F  -  -  -  -  
2	-  -  -  F  W  W  W  -  W  W  W  F  -  -  -  
3	-  -  F  0  0  W  -  W  -  W  0  0  F  -  -  
4	-  F  0  0  0  -  0  -  0  -  0  0  0  F  -  
5	F  0  0  0  -  0  -  0  -  0  -  0  0  0  F  
6	-  F  0  0  0  -  0  -  0  -  0  0  0  F  -  
7	-  -  F  0  0  B  -  B  -  B  0  0  F  -  -  
8	-  -  -  F  B  B  B  -  B  B  B  F  -  -  -  
9	-  -  -  -  F  B  B  B  B  B  F  -  -  -  -  
10	-  -  -  -  -  F  F  F  F  F  -  -  -  -  - 

*/

/*

-  -  -  -  -  F  F  F  F  F  -  -  -  -  -  
-  -  -  -  F  W  W  W  W  W  F  -  -  -  -  
-  -  -  F  W  W  W  -  W  W  W  F  -  -  -  
-  -  F        W  -  W  -  W        F  -  -  
-  F           -     -     -           F  -  
F           -     -     -     -           F  
-  F           -     -     -           F  -  
-  -  F        B  -  B  -  B        F  -  -  
-  -  -  F  B  B  B  -  B  B  B  F  -  -  -  
-  -  -  -  F  B  B  B  B  B  F  -  -  -  -  
-  -  -  -  -  F  F  F  F  F  -  -  -  -  -


*/