package up.mi.al.connect;

public interface ConnectGame {
	/**
	 * 
	 * @param column - the column to check
	 * @return - true if the column isn't full
	 */
	public boolean canPlayIn(int column);
	
	/**
	 * check if the game is won
	 * 
	 * return 0 if the game is unfinished, >0 if the player 1 won or <0 if the player 2 won
	 * @return - the win code
	 */
	public int isWon();
	
	/**
	 * 
	 * @param column - the column to play in
	 */
	public void playInColumn(int column);

	/**
	 * return 0 if the space is empty, >0 if the token is for player 1 or <0 if the token is for player 2
	 * @param column
	 * @param row
	 * @return - the token
	 */
	public int getTokenAt(int column, int row);
	
	public int[][] getBoard();
	
	public String toString();
}
