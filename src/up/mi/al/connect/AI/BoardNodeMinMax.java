package up.mi.al.connect.AI;

public class BoardNodeMinMax extends BoardNode {
	protected final boolean isMin;

	public BoardNodeMinMax(int[][] board, int linkSize, boolean player1) {
		super(board, linkSize, player1);
		isMin = false;
	}

	protected BoardNodeMinMax(int[][] board, int linkSize, boolean player1, boolean isMin) {
		super(board, linkSize, player1);
		this.isMin = isMin;

	}

	@Override
	public int evaluateValue() {
		int val = isWon(); // is won, returns winner
		if (val != 0) {
			boolean p1win = val > 0;

			boolean result = (p1win && !isPlayer1() && isMin) || (!p1win && isPlayer1() && isMin)
					|| (p1win && isPlayer1() && !isMin) || (!p1win && !isPlayer1() && !isMin);

			return result ? 1 : -1;
		}

		int res = 0;
		boolean emptyVarCheck = true;
		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x)) {
				val = evaluateValue(x);

				if (emptyVarCheck || (!isMin && ((res > 0 && !(val > 0)) || (val == 0 && res < 0) || (res < 0 && res > val)
						|| (val > 0 && res > val)))
						|| (isMin && ((val < 0 && !(res < 0)) || (val == 0 && res > 0) || (res > 0 && val > res)
								|| (val < 0 && val > res)))) {
					emptyVarCheck = false;
					res = val;
				}
			}
		return !isMin ? res : res > 0 ? res + 1 : (res < 0 ? res - 1 : 0);
	}

	@Override
	protected BoardNode createChildNode(int playAt)  {
		return new BoardNodeMinMax(cloneBoardWithPlayAt(playAt), linkSize, !isPlayer1(), !isMin);
	}

	@Override
	public int[] computePlayValues() {
		int[] res = new int[getWidth()];

		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x) /* && x==0 */) {
				res[x] = evaluateValue(x);
			} else
				res[x] = -1;
		return res;
	}

}
