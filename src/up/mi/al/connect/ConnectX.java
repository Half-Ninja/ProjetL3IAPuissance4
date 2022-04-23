package up.mi.al.connect;

import java.util.Scanner;

/**
 * Create a connectX Board of a given height and width and a given X
 * 
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
			for (int j = 0; j < height; j++)
				board[i][j] = 0;
	}

	public boolean canPlayIn(int column) {
		return board[column][height - 1] == 0;
	}
	
	public boolean isFinished() {
		if(isWon() != 0) return true;
		for (int i = 0; i < width; i++)
			if(canPlayIn(i)) return false;
		return true;
	}

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
					if (Math.abs(res) >= linkSize )
						return res;
				}
			}
		}
		for (int i = 0; i < this.height; i++) {
			res = 0;
			for (int j = 0; j < this.width; j++) {
				if (board[j][i] == 0)
					res = 0;
				else if ((board[j][i] < 0 && res > 0) || (board[j][i] > 0 && res < 0)) {
					res = board[j][i];
				} else {
					res += board[j][i];
					if (Math.abs(res) >= linkSize)
						return res;
				}
			}
		}

		for (int i = linkSize - 1; i < this.width + (this.height - linkSize) -1; i++) {
			res = 0;
			for (int j = Math.max(0, i - (this.width - 1)); j <= Math.min(this.height-1, i); j++) {
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

		for (int i = linkSize ; i <= this.width + (this.height - linkSize) -1; i++) {
			res = 0;
			int i2 = this.width - i;
			for (int j = Math.max(0, -i2); j <= Math.min(this.height-1, i-1); j++) {
				if (board[i2 + j][j] == 0)
					res = 0;
				if ((board[i2 + j][j] < 0 && res >= 0) || (board[i2 + j][j] > 0 && res <= 0)) {
					res = board[i2 + j][j];
				} else {
					res += board[i2 + j][j];
					if (Math.abs(res) >= linkSize)
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
		board[column][h] = currentPlayer ? -1 : 1;
		currentPlayer = !currentPlayer;
	}

	public int getTokenAt(int column, int row) {
		return board[column][row];
	}

	public int[][] getBoard() {
		return board;
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(" ");
		for (int j = 0; j < width; j++) {
			res.append(Integer.toString(j));
		}
		res.append(" \n");
		for (int i = height - 1; i >= 0; i--) {
			res.append("│");
			for (int j = 0; j < width; j++) {
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
		for (int j = 0; j < width; j++) {
			res.append("─");
		}
		res.append("┘");

		return res.toString();
	}
	
	public static void main(String args[]) {
		ConnectX game = new ConnectX(4, 4, 3);
		Scanner sc = new Scanner(System.in);
		
		while(game.isWon() == 0) {
			System.out.println(game);
			int play = sc.nextInt();
			game.playInColumn(play);
		}
		sc.close();
		System.out.println();
		System.out.println(game.isWon());
		System.out.println(game);
	}
}
