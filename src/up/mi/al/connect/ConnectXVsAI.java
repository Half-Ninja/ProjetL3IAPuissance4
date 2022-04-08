package up.mi.al.connect;

import up.mi.al.connect.AI.AI;

public class ConnectXVsAI extends ConnectX {
	private AI ai;
	
	public ConnectXVsAI(int height, int width, int linkSize, AI ai, boolean CPUAsPlayerOne) {
		super(height, width, linkSize);
		this.ai = ai;
		if (CPUAsPlayerOne) {
			super.playInColumn(ai.computePlay(getBoard()));
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
		super.playInColumn(ai.computePlay(getBoard()));
	}

}
