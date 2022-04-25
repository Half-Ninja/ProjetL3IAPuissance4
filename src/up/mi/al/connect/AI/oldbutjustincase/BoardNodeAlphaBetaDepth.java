package up.mi.al.connect.AI.oldbutjustincase;

import up.mi.al.connect.AI.BoardNodeAlphaBeta;

public class BoardNodeAlphaBetaDepth extends BoardNodeAlphaBeta {

	public BoardNodeAlphaBetaDepth(int[][] board, int linkSize, boolean player1) {
		super(board, linkSize, player1);
	}

	public BoardNodeAlphaBetaDepth(int[][] board, int linkSize, boolean player1, int pruneValue) {
		super(board, linkSize, player1, pruneValue);
	}

	public BoardNodeAlphaBetaDepth(int[][] board, int linkSize, boolean player1, int pruneValue, boolean isMin) {
		super(board, linkSize, player1, pruneValue, isMin);
	}

	public int evaluateValue(int depth) {
		if (depth == 0 || isWon() != 0)
			return isWon() > 0 ? depth + 1 : (isWon() < 0 ? -(depth + 1) : 0);
		int res = 0;
		boolean emptyVarCheck = true;
		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x)) {
				int val = this.evaluateValue(x, emptyVarCheck ? Integer.MIN_VALUE : res, depth - 1);
				if ((isMin && val < pruneValue) || (!isMin && val > pruneValue))
					return val;
				if (emptyVarCheck || (!isMin && res < val) || (isMin && res > val)) {
					res = val;
					emptyVarCheck = false;
				}
			}
		return res > 0 ? res - 1 : (res < 0 ? 0 : res + 1);
	}

	public int evaluateValue(int columnPlay, int pruneValue, int depth) {
		int v = isWon();
		if (v != 0)
			return v > 0 ? depth + 1 : -(depth + 1) ;
		setChildren(columnPlay,
				new BoardNodeAlphaBetaDepth(cloneBoardWithPlayAt(columnPlay), linkSize, !isPlayer1(), pruneValue, !isMin));

		return ((BoardNodeAlphaBetaDepth) getChildren(columnPlay)).evaluateValue(depth);
	}

	public int[] computePlayValues(int depth) {
		int[] vals = new int[getWidth()];
		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x))
				vals[x] = evaluateValue(x, pruneValue, depth);
			else
				vals[x] = Integer.MIN_VALUE;
		return vals;
	}

}
