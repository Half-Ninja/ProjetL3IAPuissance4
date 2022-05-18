package up.mi.al.connect.AI;

//TODO: ???
public class BoardNodeAlphaBeta extends BoardNodeMinMax {
	private int pruneValue;

	public BoardNodeAlphaBeta(int[][] board, int linkSize, boolean player1) {
		super(board, linkSize, player1);
		pruneValue = 1;
	}

	protected BoardNodeAlphaBeta(int[][] board, int linkSize, boolean player1, boolean isMin, int pruneValue) {
		super(board, linkSize, player1, isMin);
		this.pruneValue = pruneValue;
		
	}

	protected BoardNodeAlphaBeta(int[][] board, int linkSize, boolean player1, boolean isMin) {
		super(board, linkSize, player1, isMin);
		this.pruneValue = isMin?-1:1;
	}

	@Override
	public int evaluateValue() {
		int val = isWon(); // if is won, returns winner
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
				if (emptyVarCheck)
					val = evaluateValue(x, res);
				else
					val = evaluateValue(x);
				
				if((!isMin && ((pruneValue > 0 && !(val > 0)) || (val == 0 && pruneValue < 0) || (res < 0 && pruneValue > val)
						|| (val > 0 && pruneValue > val)))
						|| (isMin && ((val < 0 && !(pruneValue < 0)) || (val == 0 && pruneValue > 0) || (pruneValue > 0 && val > pruneValue)
								|| (val < 0 && val > pruneValue))))
					return val;
				if (emptyVarCheck || comparePlayValues(val, res) > 0) {
					emptyVarCheck = false;
					res = val;
				}
			}
		return !isMin ? res : res > 0 ? res + 1 : (res < 0 ? res - 1 : 0);
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

	protected int evaluateValue(int playAt, int pruneAt) {
		setChildren(playAt, createChildNode(playAt, pruneAt));
		return getChildren(playAt).evaluateValue();
	}

	/**
	 * Creates an alpha-beta child node
	 * @param playAt - the column of the corresponding play
	 * @param pruneAt - the value at which to prune
	 * @return the new node
	 */
	protected BoardNode createChildNode(int playAt, int pruneAt) {
		return new BoardNodeAlphaBeta(cloneBoardWithPlayAt(playAt), linkSize, !isPlayer1(), !isMin, pruneAt);
	}

	/**
	 * Creates an alpha-beta child node, without a prune value (prune value = 1)
	 * @param playAt - the column of the corresponding play
	 * @return the new node
	 */
	protected BoardNode createChildNode(int playAt)  {
		return new BoardNodeAlphaBeta(cloneBoardWithPlayAt(playAt), linkSize, !isPlayer1(), !isMin);
	}
}
