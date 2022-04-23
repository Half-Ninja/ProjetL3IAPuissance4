package up.mi.al.connect.AI;

public class BoardNodeAlphaBeta extends BoardNodeMinMax {
	protected int pruneValue;

	public BoardNodeAlphaBeta(int[][] board, int linkSize, boolean player1) {
		super(board, linkSize, player1);
		this.pruneValue = Integer.MIN_VALUE;
	}

	public BoardNodeAlphaBeta(int[][] board, int linkSize, boolean player1, int pruneValue) {
		super(board, linkSize, player1);
		this.pruneValue = pruneValue;
	}

	public BoardNodeAlphaBeta(int[][] board, int linkSize, boolean player1, int pruneValue, boolean isMin) {
		super(board, linkSize, player1, isMin);
		this.pruneValue = pruneValue;
	}

	public int evaluateValue() {
		if (isWon() != 0)
			return isWon() > 0 ? theoricalMaxPlays() : -theoricalMaxPlays();
		int res = 0;
		boolean emptyVarCheck = true;
		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x)) {
				int val = this.evaluateValue(x, emptyVarCheck ? Integer.MIN_VALUE : res);
				if ((isMin && val < pruneValue) || (!isMin && val > pruneValue))
					return val;
				if (emptyVarCheck || (!isMin && res < val) || (isMin && res > val)) {
					res = val;
					emptyVarCheck = false;
				}
			}
		if (res == 0) {
			System.out.println(this);
		}
		return res > 0 ? res - 1 : (res < 0 ? res + 1 : 0);
	}

	public int evaluateValue(int columnPlay, int pruneValue) {
		int v = isWon();
		if (v != 0)
			return v > 0 ? theoricalMaxPlays() : -theoricalMaxPlays();
		setChildren(columnPlay,
				new BoardNodeAlphaBeta(cloneBoardWithPlayAt(columnPlay), linkSize, !isPlayer1(), pruneValue, !isMin));

		return ((BoardNodeAlphaBeta) getChildren(columnPlay)).evaluateValue();
	}

	@Override
	public int[] computePlayValues() {
		int[] vals = new int[getWidth()];
		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x))
				vals[x] = evaluateValue(x, pruneValue);
			else
				vals[x] = Integer.MIN_VALUE;
		return vals;
	}

}
