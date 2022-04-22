package up.mi.al.connect;

import java.util.Scanner;

import up.mi.al.connect.AI.AI;
import up.mi.al.connect.AI.AIMinMax;

public class ConnectXVsAI extends ConnectX {
	private AI ai;

	public ConnectXVsAI(int height, int width, int linkSize, AI ai, boolean CPUAsPlayerOne) {
		super(height, width, linkSize);
		this.ai = ai;
		if (CPUAsPlayerOne) {
			super.playInColumn(ai.computePlay(getBoard(), this.linkSize));
		}
	}

	public ConnectXVsAI(int height, int width, int linkSize, AI ai) {
		super(height, width, linkSize);
		this.ai = ai;
	}

	private ConnectXVsAI(int height, int width, int linkSize) {
		super(height, width, linkSize);
	}

	public void playInColumn(int column) {
		super.playInColumn(column);
		if (isWon() != 0) {
			System.out.print("isWon() >>> ");
			System.out.println(isWon());
		}
		else 
			super.playInColumn(ai.computePlay(getBoard(), linkSize));
		
	}
	
	public static void main(String args[]) {
		ConnectXVsAI game = new ConnectXVsAI(4, 4, 3, new AIMinMax(), true);
		Scanner sc = new Scanner(System.in);
		
		while(game.isWon() == 0) {
			System.out.println(game);
			int play = sc.nextInt();
			game.playInColumn(play);
		}
		sc.close();
//		int[][] board = {{-1, 1, 1, 1},{0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0}};
//		BoardNodeMinMax ai = new BoardNodeMinMax(board, 3, false);
//		System.out.println();
//		System.out.println(ai.isWon());
	}

}
