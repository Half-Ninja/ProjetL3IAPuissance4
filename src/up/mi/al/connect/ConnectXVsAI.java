package up.mi.al.connect;

//import java.util.ArrayList;
import java.util.Scanner;

import up.mi.al.connect.AI.AI;
import up.mi.al.connect.AI.AIMinMax;
//import up.mi.al.connect.AI.BoardNode;
//import up.mi.al.connect.AI.BoardNodeMinMax;

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
		
//		int[][] board = {{-1, 1, 0},{-1, 1, 0},{1, -1, 1}};
//		BoardNodeMinMax node = new BoardNodeMinMax(board, 3, false, true, "lol");
//		int[] values = node.computePlayValues();
//		
//		for (int v : values) {
//			System.out.print(Integer.toString(v) + " ");
//		}
//		System.out.println(calcPlay(values, node));
//		System.out.println();
//		System.out.println(node);
//		System.out.println(node.isWon());
	}
	
//	private static ArrayList<Integer> calcPlay(int[] values, BoardNode node) {
//		ArrayList<Integer> options = new ArrayList<>();
//		boolean emptyCheck = true;
//		for (int i = 0; i < values.length; i++) {
//			if (node.canPlayIn(i)) {
//				if(!emptyCheck && values[i] == values[options.get(0)])
//					options.add(i);
//					//check if either option is empty or if the value of the placement i is batter than those currently stored
//				else if (emptyCheck || (!(values[options.get(0)] > 0) && values[i] > 0) ||
//						(values[options.get(0)] < 0 && values[i] == 0) || 
//						(values[options.get(0)] < 0 && values[i] < values[options.get(0)]) || 
//						(values[i] > 0 && values[i] < values[options.get(0)])) {
//					emptyCheck = false;
//					options.clear();
//					options.add(i);
//				} 
//			}
//		}
//		return options;
//	}

}
