package up.mi.al.connect.AI;

import java.util.ArrayList;

public class AIRandom implements AI {

	@Override
	public int computePlay(int[][] board, int linkSize) {
		ArrayList<Integer> a = new ArrayList<>();
		for (int i = 0; i <board.length; i++ ) {
			if(board[i][board[i].length-1] == 0)
				a.add(i);
		}
		return a.get((int)(Math.random() * a.size()));
	}

}
