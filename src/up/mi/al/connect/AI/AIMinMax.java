package up.mi.al.connect.AI;

import java.util.ArrayList;

public class AIMinMax implements AI {

	private boolean player1;

	public AIMinMax(boolean player1) {
		this.player1 = player1;
	}

	@Override
	public int computePlay(int[][] board, int linkSize) {
		BoardNodeMinMax node = new BoardNodeMinMax(board, linkSize, player1);

		int[] values = node.computePlayValues();


		ArrayList<Integer> options = new ArrayList<>();
		boolean emptyCheck = true;
		for (int i = 0; i < values.length; i++) {
			if (node.canPlayIn(i)) {
				//
				if (!emptyCheck && values[i] == values[options.get(0)])
					options.add(i);
				else if (emptyCheck || node.comparePlayValues(values[i], values[options.get(0)]) > 0) {
					emptyCheck = false;
					options.clear();
					options.add(i);
				}
			}
		}

		return options.get((int) (Math.random() * options.size()));
	}

}
