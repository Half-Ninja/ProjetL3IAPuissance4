package up.mi.al.connect;

import java.util.ArrayList;

import up.mi.al.connect.AI.AI;
import up.mi.al.connect.AI.AIAlphaBeta;

public class AIAlphaBetaDepth extends AIAlphaBeta implements AI {
	
	private int depth;

	public AIAlphaBetaDepth(int depth) {
		this.depth = depth;
	}
	
	@Override
	public int computePlay(int[][] board, int linkSize) {
		BoardNodeAlphaBetaDepth abTree = new BoardNodeAlphaBetaDepth(board, linkSize, false);
		
		int[] vals = abTree.computeImmediateValues();
		ArrayList<Integer> winningPlays = new ArrayList<Integer>();
		winningPlays.add(0);
		for (int i = 1; i < board.length; i++) 
			if(abTree.canPlayIn(i)) {
				if(vals[winningPlays.get(0)] == vals[i]) {
					winningPlays.add(i);
				}
			
				else if(vals[winningPlays.get(0)] < vals[i]) {
					winningPlays.clear();
					winningPlays.add(i);
				}
			}	
		
		if(vals[winningPlays.get(0)] > -10) {
			return winningPlays.get((int)(Math.random() * winningPlays.size()));
		}
		
		vals = abTree.computePlayValues(depth);
		
		String val = "";
		for(int x = 0; x < vals.length; x++)
			val += Integer.toString(vals[x]) + " ";
		System.out.println(val);
		
		ArrayList<Integer> bestPlays = new ArrayList<Integer>();
		boolean emptyVarCheck = true;
		for (int x = 0; x < board.length; x++) 
			if(abTree.canPlayIn(x)) {
				if(emptyVarCheck || vals[bestPlays.get(0)] == vals[x]) {
					bestPlays.add(x);
					emptyVarCheck = false;
				}
			
				else if(vals[bestPlays.get(0)] < vals[x]) {
					bestPlays.clear();
					bestPlays.add(x);
				}
			}
		System.out.println(bestPlays);
		int r = (int)(Math.random() * bestPlays.size());
		System.out.print("r = ");
		System.out.println(r);
		return bestPlays.get(r);
	}

}
