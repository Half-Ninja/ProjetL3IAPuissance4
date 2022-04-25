package up.mi.al.connect.AI;

public abstract class BoardNode {
	private int width, height;
	protected int linkSize;
	private int[][] board;
	private BoardNode[] children;
	/** true if it's player 1's turn */
	private boolean player1;

	public BoardNode(int[][] board, int linkSize, boolean player1) {
		this.width = board.length;
		this.height = board[0].length;
		this.linkSize = linkSize;
		this.player1 = player1;

		this.children = new BoardNode[this.width];
		this.board = new int[this.width][this.height];

		for (int y = 0; y < board.length; y++)
			for (int x = 0; x < board[0].length; x++)
				this.board[y][x] = board[y][x];

	}

	/**
	 * evaluates the value of the node
	 * 
	 * @return the calculated value
	 */
	public abstract int evaluateValue();

	/**
	 * evaluates the value of the child node
	 * 
	 * @param playAt - the column of the play
	 * @return the calculated value
	 */
	public int evaluateValue(int playAt) {
		setChildren(playAt, createChildNode(playAt));
		return getChildren(playAt).evaluateValue();
	}

	/**
	 * Creates an object corresponding to the new child
	 * @return
	 */
	protected abstract BoardNode createChildNode(int playAt);

	/**
	 * calculate the values of the child nodes
	 * 
	 * @return
	 */
	public abstract int[] computePlayValues();
	
	/**
	 * 
	 * @return the theorical max numbers of plays (height * width)
	 */
	protected int theoricalMaxPlays() {
		return width*height;
	}

	/**
	 * 
	 * @param column - the column to check
	 * @return true if the column isn't full
	 */
	public boolean canPlayIn(int column) {
		return board[column][height - 1] == 0;
	}

	/**
	 * check if the game is won
	 * 
	 * return 0 if the game is unfinished, >0 if the player 1 won or <0 if the
	 * player 2 won
	 * 
	 * @return - the win code
	 */
	public int isWon() {
		int res = 0;
		// checks columns
		for (int[] column : board) {
			res = 0;
			for (int t : column) {
				if (t == 0) {
					res = 0;
					break;
				}
				if ((t < 0 && res >= 0) || (t > 0 && res <= 0)) {
					res = t;
				} else {
					res += t;
					if (Math.abs(res) >= linkSize)
						return res;
				}
			}
		}
		// checks rows
		for (int i = 0; i < this.height; i++) {
			res = 0;
			for (int j = 0; j < this.width; j++) {
				if ((board[j][i] <= 0 && res > 0) || (board[j][i] >= 0 && res < 0)) {
					res = board[j][i];
				} else {
					res += board[j][i];
					if (Math.abs(res) >= linkSize)
						return res;
				}
			}
		}

		// checks diagonals
		for (int i = 1; i < this.width + this.height - 1; i++) {
			res = 0;
			for (int j = Math.max(0, i - (this.width-1)); j <= Math.min(this.height - 1, i); j++) {
				if (board[i - j][j] == 0)
					res = 0;
				if ((board[i - j][j] < 0 && res >= 0) || (board[i - j][j] > 0 && res <= 0)) {
					res = board[i - j][j];
				} else {
					res += board[i - j][j];
					if (Math.abs(res) >= linkSize)
						return res;
				}
			}
		}

		for (int i = this.width -1; i > -this.height ; i--) {
			res = 0;
			for (int j = i>0?0:-i; j < Math.min(this.height, this.height - i); j++) {
					if (board[i + j][j] == 0)
						res = 0;
					if ((board[i + j][j] < 0 && res >= 0) || (board[i + j][j] > 0 && res <= 0)) {
						res = board[i + j][j];
					} else {
						res += board[i + j][j];
						if (Math.abs(res) >= linkSize)
							return res;
					}
			}
		}

		return 0;
	}

	public BoardNode getChildren(int columnPlay) {
		return children[columnPlay];
	}

	public boolean isPlayer1() {
		return player1;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLinkSize() {
		return linkSize;
	}

	protected void setChildren(int columnPlay, BoardNode node) {
		this.children[columnPlay] = node;
	}

	protected int[][] cloneBoard() {
		int[][] newBoard = new int[this.width][this.height];

		for (int y = 0; y < board.length; y++)
			for (int x = 0; x < board[0].length; x++)
				newBoard[y][x] = board[y][x];
		return newBoard;
	}

	protected int[][] cloneBoardWithPlayAt(int columnPlay) {
		int[][] res = cloneBoard();
		int i = 0;
		while (res[columnPlay][i] != 0)
			i++;
		res[columnPlay][i] = player1 ? 1 : -1;
		return res;
	}

	protected int[][] cloneBoardWithPlayAt(int columnPlay, boolean invertPlayer) {
		int[][] res = cloneBoard();
		int i = 0;
		while (res[columnPlay][i] != 0)
			i++;
		res[columnPlay][i] = !player1 ? 1 : -1;
		return res;
	}

}
