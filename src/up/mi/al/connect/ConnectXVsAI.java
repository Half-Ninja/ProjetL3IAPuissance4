package up.mi.al.connect;

import java.util.Scanner;

import up.mi.al.connect.AI.AI;
import up.mi.al.connect.AI.AIMinMax;
import up.mi.al.connect.AI.BoardNodeMinMax;

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
		} else
			super.playInColumn(ai.computePlay(getBoard(), linkSize));

	}

	public static void main(String args[]) {
		int height = 3, width = 3, linksize = 3;
		boolean AIAsPlayer1 = true;
		ConnectXVsAI game = new ConnectXVsAI(height, width, linksize, new AIMinMax(AIAsPlayer1), AIAsPlayer1);
		Scanner sc = new Scanner(System.in);

		while (!game.isFinished()) {
			System.out.println(game);
			int play = sc.nextInt();
			game.playInColumn(play);
		}
		sc.close();
		if(game.isWon() != 0)
			System.out.println("player " + (game.isWon() > 0 ? "1" : "2") + " won");
		else
			System.out.println("draw");
		System.out.println(game);
		
//		int[][] board = {{1, 1, -1},{-1, 01, 01},{1, -01, -01}};
//		BoardNodeMinMax node = new BoardNodeMinMax(board, 3, false);
//		int[] values = node.computePlayValues();
//		
//		for (int v : values) {
//			System.out.print(Integer.toString(v) + " ");
//		}
//		System.out.println();
//		System.out.println(node);
//		System.out.println(node.isWon());
	}

}
