package up.mi.al.connect.AI;

public interface AI {
	/**
	 * calculate the AI's next move according to it's algorythm
	 * @param board - the board to compute
	 * @return the AI's next move
	 */
	public int computePlay(int[][] board);
}
