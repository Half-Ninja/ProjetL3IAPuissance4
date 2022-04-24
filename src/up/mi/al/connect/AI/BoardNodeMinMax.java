package up.mi.al.connect.AI;

public class BoardNodeMinMax extends BoardNode {
	protected final boolean isMin;
	public String id;

	public BoardNodeMinMax(int[][] board, int linkSize, boolean player1) {
		super(board, linkSize, player1);
		isMin = false;
		id = "";
	}

	/*protected*/ public BoardNodeMinMax(int[][] board, int linkSize, boolean player1, boolean isMin, String id) {
		super(board, linkSize, player1);
		this.isMin = isMin;
		this.id = id;

	}

	@Override
	public int evaluateValue() {
		int val = isWon(); // is won, returns winner
		if (val != 0) {
//			System.out.println(this);
//			System.out.println("won by player" + (val > 0 ? "1" : "2"));
//			System.out.println(val);
//			System.out.println(val < 0 ? -1 : 1);
//			System.out.println("won by player" + (val > 0 ? "1" : "2"));
			boolean p1win = val > 0;
			
			boolean result = (p1win && !isPlayer1() && isMin) || (!p1win && isPlayer1() && isMin) ||
							 (p1win && isPlayer1() && !isMin) || (!p1win && !isPlayer1() && !isMin);
			
			return result?1:-1;
		}

		int res = 0;
		boolean emptyVarCheck = true;
		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x)) {
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
				if(id.equals("2221100"))
					res = res;
				boolean result = emptyVarCheck
						|| (!isMin && ((res > 0 && !(val > 0)) || (val == 0 && res < 0)
								|| (res < 0 && res > val) || (val > 0 && res > val)))
						|| (isMin && ((val < 0 && !(res < 0)) || (val == 0 && res > 0)
								|| (res > 0 && val > res) || (val < 0 && val > res)));
				if (result) {
					emptyVarCheck = false;
					res = val;
				}
			}
//		System.out.println(this);
//		System.out.println(res > 0 ? res + 1 : (res < 0 ? res - 1 : 0));
//		System.out.println(isPlayer1()?"player1":"player2");
//		System.out.println();
		return !isMin?res : res > 0 ? res + 1 : (res < 0 ? res - 1 : 0);
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("["+id+"]");
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
		return new BoardNodeMinMax(cloneBoardWithPlayAt(playAt), linkSize, !isPlayer1(), !isMin, id+Integer.toString(playAt));
	}

	@Override
	public int[] computePlayValues() {
		int[] res = new int[getWidth()];

		for (int x = 0; x < getWidth(); x++)
			if (canPlayIn(x) /*&& x==0*/) {
//				System.out.println("**************************************");
//				System.out.print("column ");
//				System.out.println(x);
//				System.out.println("**************************************");
				res[x] = evaluateValue(x);
//				for (int i=0; i < 3; i++) {
//					System.out.println(getChildren(x).getChildren(i));
//					System.out.println(getChildren(x).getChildren(i).evaluateValue());
//				}
			} else
				res[x] = -1;
		return res;
	}

}
