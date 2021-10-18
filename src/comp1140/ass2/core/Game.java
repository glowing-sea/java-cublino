package comp1140.ass2.core;

import java.util.Scanner;

public class Game {

    public static State initialiseBoard() {
        String initialString = "CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7";
        State initialState = new State(initialString);
        return initialState;
    }

    public static int humanVhumanPur() {
        // 1 if player1 (White) is winner, 2 if player2 (Black) 3 if draw
        State s = initialiseBoard();
        // initialises board
        State newState = s.copy(); // Make a copy of the state.
        while (s.isGameOverPur() == 0) {
            boolean isPlayer1 = s.getPlayerTurn();
            System.out.println(s.toString());
            System.out.println((isPlayer1 ? "P1 " : "P2 ") + "to play their move");
            Scanner q = new Scanner(System.in);
            String move = q.next();
            Move m = new Move(move);
            while (!m.isValidMovePur(s)) {
                System.out.println("Invalid move, please make another move");
                String m1 = q.next();
                m = new Move(m1);
            }
            newState.applyMove(m);

        }
        // error, registers all moves are invalid for some reason.
        return newState.isGameOverPur();
    }
    public static void main(String[] args) {
        System.out.println("Welcome to the game!");
        System.out.println("Choose your variant, choose 1 for Cublino Pur, choose 2 for Cublino Contra!");
        Scanner s = new Scanner(System.in);
        int variant = s.nextInt();
        while (variant!=1 && variant !=2) {
            System.out.println("Invalid input, choose either 1 or 2");
            System.out.println("Choose your variant, choose 1 for Cublino Pur, choose 2 for Cublino Contra!");
            int variant2 = s.nextInt();
            variant = variant2;

        }
        int x = humanVhumanPur();
        System.out.println(x);



    }
}
