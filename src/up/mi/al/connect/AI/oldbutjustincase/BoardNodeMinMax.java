package up.mi.al.connect.AI.oldbutjustincase;

public class BoardNodeMinMax extends BoardNode {
	protected boolean isMin;

	public BoardNodeMinMax(int[][] board, int linkSize, boolean player1) {
		super(board, linkSize, player1);
	}

	protected BoardNodeMinMax(int[][] board, int linkSize, boolean player1, boolean isMin) {
		super(board, linkSize, player1);
		this.isMin = isMin;
	}

	/**
	 * evaluates the value of the node
	 * 
	 * @return
	 */
	public int evaluateValue() {
		if (isWon() != 0)
			return isWon() > 0 ? theoricalMaxPlays() : -theoricalMaxPlays();
		int res = 0;
		boolean emptyVarCheck = true;
		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x)) {
				int val = evaluateValue(x);
				if (emptyVarCheck || (!isMin && res < val) || (isMin && res > val)) {
					res = val;
					emptyVarCheck = false;
				}
			}
		return res > 0 ? res - 1 : (res < 0 ? 0 : res + 1);
	}

	public int evaluateValue(int columnPlay) {
		int v = isWon();
		if (v != 0)
			return v > 0 ? 1 : -1;
		setChildren(columnPlay, new BoardNodeMinMax(cloneBoardWithPlayAt(columnPlay), linkSize, !isPlayer1(), !isMin));

		return getChildren(columnPlay).evaluateValue();
	}

	public int evaluateImmediateValue(int columnPlay) {
		setChildren(columnPlay, new BoardNodeMinMax(cloneBoardWithPlayAt(columnPlay), linkSize, !isPlayer1(), !isMin));
		if (getChildren(columnPlay).isWon() != 0) {
			return -1;
		}
		setChildren(columnPlay,
				new BoardNodeMinMax(cloneBoardWithPlayAt(columnPlay, true), linkSize, !isPlayer1(), !isMin));
		if (getChildren(columnPlay).isWon() != 0) {
			return 1;
		}
		return Integer.MIN_VALUE;
	}

	public int[] computeImmediateValues() {
		int[] vals = new int[getWidth()];
		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x))
				vals[x] = evaluateImmediateValue(x);
			else
				vals[x] = Integer.MIN_VALUE;
		return vals;
	}

	@Override
	public int[] computePlayValues() {
		int[] vals = new int[getWidth()];
		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x))
				vals[x] = evaluateValue(x);
			else
				vals[x] = Integer.MIN_VALUE;
		return vals;
	}

}
