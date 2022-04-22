package up.mi.al.connect.AI;

import java.util.ArrayList;

public class AIMinMax implements AI {

	public AIMinMax() {
	}

	@Override
	public int computePlay(int[][] board, int linkSize) {
		BoardNodeMinMax MinMaxTree = new BoardNodeMinMax(board, linkSize, false);
		
		int[] vals = MinMaxTree.computeImmediateValues();
		ArrayList<Integer> winningPlays = new ArrayList<Integer>();
		winningPlays.add(0);
		for (int i = 1; i < board.length; i++) 
			if(MinMaxTree.canPlayIn(i)) {
				if(vals[winningPlays.get(0)] == vals[i]) {
					winningPlays.add(i);
				}
			
				else if(vals[winningPlays.get(0)] < vals[i]) {
					winningPlays.clear();
					winningPlays.add(i);
				}
			}	
		
		if(vals[winningPlays.get(0)] > -10) {
			System.out.println("lol");
			return winningPlays.get((int)(Math.random() * winningPlays.size()));
		}
		
		vals = MinMaxTree.computePlayValues();
		
		String val = "";
		for(int x = 0; x < vals.length; x++)
			val += Integer.toString(vals[x]) + " ";
		System.out.println(val);
		
		ArrayList<Integer> bestPlays = new ArrayList<Integer>();
		boolean emptyVarCheck = true;
		for (int x = 0; x < board.length; x++) 
			if(MinMaxTree.canPlayIn(x)) {
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
