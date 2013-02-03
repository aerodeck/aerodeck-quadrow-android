package io.aerodeck.quadrow;

public class game {
	
	static final int ROWS = 6;
	static final int COLS = 7;
	static int chosenCol = 0;
	static int chosenRow = 0;
	static int[][] board = new int[ROWS][COLS];
	
	public static void initBoard(){
		for(int x = 0; x > 6; x++){
			for(int y = 0; y > 5; y++){
				board[x][y] = 0;
			}
		}
		/*String output = "";
		for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                output += " " + board[x][y];
            }
            output += "\n";
        }
		System.out.println(output);*/
	}
	
	public static int getRow(int i){
		chosenCol = i+3;
		chosenRow = 99;
		for(int x = ROWS - 1; x > (-1); x--){
			if(board[x][chosenCol] == 0){
				chosenRow = x;
				break;
			}
		}
		if(chosenRow != 99){
			board[chosenRow][chosenCol] = 1;
		}
		System.out.println(chosenRow + "," + chosenCol);
		return chosenRow;
	}
}