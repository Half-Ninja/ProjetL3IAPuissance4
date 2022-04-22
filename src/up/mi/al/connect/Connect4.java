package up.mi.al.connect;

import java.util.Scanner;

public class Connect4 extends ConnectX {

	public Connect4() {
		super(6, 7, 4);
	}
	
	public static void main(String args[]) {
		Connect4 game = new Connect4();
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
