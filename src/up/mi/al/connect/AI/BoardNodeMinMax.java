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
			return val < 0 ? (isPlayer1() ? 1 : -1) : (isPlayer1() ? -1 : 1);
		}

		int res = 0;
		boolean emptyVarCheck = true;
		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x)) {
				if (x == 3 && !isPlayer1())
					x = 3;
				val = evaluateValue(x);
//				boolean a = val < 0;
//				boolean b = val == 0;
//				boolean c = val > 0;
//				boolean d = res < 0;
//				boolean e = res == 0;
//				boolean f = res > 0;
//				boolean g = res < val;
//				boolean h = val < res;
//				boolean i = isMin;
//				boolean result = (!i && ((c && !f) || (b&&d) || (d && a && g) || (f && g) )) ||
//							( i && ((a && !d) || (b&&f) || (f && c && h) || (d && h) ));
				// TODO : simplify more
				if (emptyVarCheck
						|| (!isMin && ((val > 0 && !(res > 0)) || (val == 0 && res < 0)
								|| (res < 0 && val < 0 && res < val) || (res > 0 && res < val)))
						|| (isMin && ((val < 0 && !(res < 0)) || (val == 0 && res > 0)
								|| (res > 0 && val > 0 && val < res) || (res < 0 && val < res)))) {
					emptyVarCheck = true;
					res = val;
				}
			}
		return res > 0 ? res + 1 : (res < 0 ? res - 1 : 0);
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(" ");
		for (int j = 0; j < getWidth(); j++) {
			res.append(" ");
		}
		res.append(" \n");
		int[][] board = cloneBoard();
		for (int i = getHeight() - 1; i >= 0; i--) {
			res.append("│");
			for (int j = 0; j < getWidth(); j++) {
				switch (board[j][i]) {
				case -1:
					res.append('o');
					break;
				case 0:
					res.append(' ');
					break;
				case 1:
					res.append('x');
					break;
				}
			}
			res.append("│\n");
		}
		res.append("└");
		for (int j = 0; j < getWidth(); j++) {
			res.append("─");
		}
		res.append("┘");

		return res.toString();
	}

	@Override
	protected BoardNode createChildNode(int playAt) {
		return new BoardNodeMinMax(cloneBoardWithPlayAt(playAt), linkSize, !isPlayer1(), !isMin);
	}

	@Override
	public int[] computePlayValues() {
		int[] res = new int[getWidth()];

		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x)) {
				res[x] = evaluateValue(x);
			} else
				res[x] = -1;
		return res;
	}

}
