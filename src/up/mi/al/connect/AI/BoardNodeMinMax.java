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

	/**
	 * Evaluate two value relative to another (named res and val for coding
	 * convenience)
	 * 
	 * @param val
	 * @param res
	 * @return 0 if the values are the same, 1 is val is preferable, -1 if res is
	 *         preferable
	 */
	public int comparePlayValues(int val, int res) {
		if (val == 0)
			return 0;

//		logic gates are cool, I guess anyways:
//		boolean a = val < 0;
//		boolean b = val == 0;
//		boolean c = val > 0;
//		boolean d = res < 0;
//		boolean e = res == 0;
//		boolean f = res > 0;
//		boolean g = res < val;
//		boolean h = val < res;
//		boolean i = isMin;
//		boolean result = (!i && ((c && !f) || (b&&d) || (d && a && g) || (f && g) )) ||
//					( i && ((a && !d) || (b&&f) || (f && c && h) || (d && h) ));
		// TODO : simplify more
		return  true ? 1 : -1;
	}

}
