package up.mi.al;

import java.util.Scanner;

import up.mi.al.connect.Connect4VsAI;
import up.mi.al.connect.AI.AIRandom;
import up.mi.al.connect.AI.AIAlphaBeta;
import up.mi.al.connect.AI.AIAlphaBetaDepth;


public class App {
  
  private static String menuPrincipal = "1 - facile\n" + "2 - moyen\n" + "3 - difficile";

    public static void main(String args[]) {

      int choixNiveau = 0;
          Scanner sc = new Scanner(System.in);
      do{
        System.out.println(menuPrincipal);
        choixNiveau = sc.nextInt();
        switch(choixNiveau){
          case 1:
            System.out.println("\n facile");
            IAEasy();
            break;
          case 2:
            System.out.println("\n moyen");
            IAMedium();
            break;
          case 3:
            System.out.println("\n difficile");
            IAHard();
            break;
          default:
            System.out.println("Entrée non reconnue\n");
        }
      }while(choixNiveau != 3);
          sc.close();
    }

    private static void IAEasy(){

        boolean AIAsPlayer1 = false;
        Connect4VsAI game = new Connect4VsAI(new AIRandom(), AIAsPlayer1);
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
            System.out.println("d1raw");
        System.out.println(game);
    }

    private static void IAMedium(){

        int depth = 5;
        boolean AIAsPlayer1 = false;
		Connect4VsAI game = new Connect4VsAI(new AIAlphaBetaDepth(AIAsPlayer1, depth), AIAsPlayer1);
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
    }

    private static void IAHard(){

    boolean AIAsPlayer1 = false;
		Connect4VsAI game = new Connect4VsAI(new AIAlphaBeta(AIAsPlayer1), AIAsPlayer1);
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
	    
    }


}
