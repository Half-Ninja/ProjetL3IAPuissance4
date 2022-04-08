package up.mi.al.connect;

/**
 * Create a connectX Board of a given height and width and a given X
 * @author Admin
 *
 */
public class ConnectX implements ConnectGame {
	int width, height, linkSize;
	int[][] board;
	
	/**
	 * false for player1, true for player2
	 */
	boolean currentPlayer;

	public ConnectX(int height, int width, int linkSize) {
		this.height = height;
		this.width = width;
		this.linkSize = linkSize;
		
		board = new int[width][height];
		for (int i = 0; i < width; i++)
			for(int j = 0; j < width; j++)
				board[i][j] = 0;
	}
	
	public boolean canPlayIn(int column) {
		return board[column][height - 1] == 0;
	}

	public int isWon() {
		int res = 0;
		// checks columns
		for (int[] column : board) {
			for(int t : column) {
				if(t==0)
					break;
				if((t < 0 && res > 0) || (t < 0 && res > 0)) {
					res = t;
				}
				else {
					res += t;
					if(Math.abs(res) >= linkSize)
						return res;
				}
			}
		}
		
		
		return 0;
	}

	public void playInColumn(int column) {
		int h = 0;
		while (board[column][h] != 0)
			h++;
		board[column][h] = currentPlayer?-1:1;
		currentPlayer = !currentPlayer;
	}

	public int getTokenAt(int column, int row) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(" ");
		for(int j = 0; j < width; j++) {
			res.append(Integer.toString(j));
		}
		res.append(" \n");
		for(int i = height-1; i >= 0; i--) {
			res.append("│");
			for(int j = 0; j < width; j++) {
				switch (board[j][i]) {
				case -1 : res.append('o'); break;
				case  0 : res.append(' '); break;
				case  1 : res.append('x'); break;
				}
			}
			res.append("│\n");
		}
		res.append("└");
		for(int j = 0; j < width; j++) {
			res.append("─");
		}
		res.append("┘");
		
		return res.toString();
	}
	
	public static void main(String[] args) {
		ConnectGame c = new ConnectX(5,5,3);
		System.out.println(c);
		c.playInColumn(0);
		System.out.println(c);
		c.playInColumn(1);
		System.out.println(c);
		c.playInColumn(0);
		System.out.println(c);
		c.playInColumn(1);
		System.out.println(c);
		c.playInColumn(0);
		System.out.println(c);
		System.out.println(c.isWon());
	}

}
