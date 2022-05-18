package up.mi.al.connect.AI;

public class BoardNodeAlphaBetaDepth extends BoardNodeAlphaBeta {
	private int depth;

	public BoardNodeAlphaBetaDepth(int[][] board, int linkSize, boolean player1, int depth) {
		super(board, linkSize, player1);
		this.depth = depth;
	}

	protected BoardNodeAlphaBetaDepth(int[][] board, int linkSize, boolean player1, boolean isMin, int pruneValue, int depth) {
		super(board, linkSize, player1, isMin);
		this.depth = depth;
		
	}

	protected BoardNodeAlphaBetaDepth(int[][] board, int linkSize, boolean player1, boolean isMin, int depth) {
		super(board, linkSize, player1, isMin);
		this.depth = depth;
	}
	
	public int evaluateValue() {
		if (depth<0) {
			int v = isWon();
			return v>0?1:(v<0?-1:0);
		}
		return super.evaluateValue();
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

	@Override
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
	@Override
	protected BoardNode createChildNode(int playAt, int pruneAt) {
		return new BoardNodeAlphaBetaDepth(cloneBoardWithPlayAt(playAt), linkSize, !isPlayer1(), !isMin, pruneAt, isMin?depth-1:depth);
	}

	/**
	 * Creates an alpha-beta child node, without a prune value (prune value = 1)
	 * @param playAt - the column of the corresponding play
	 * @return the new node
	 */
	@Override
	protected BoardNode createChildNode(int playAt)  {
		return new BoardNodeAlphaBetaDepth(cloneBoardWithPlayAt(playAt), linkSize, !isPlayer1(), !isMin, isMin?depth-1:depth);
	}

}
