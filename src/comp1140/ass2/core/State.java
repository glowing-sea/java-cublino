package comp1140.ass2.core;

import comp1140.ass2.Cublino;

import java.util.ArrayList;

public class State {
    private static final int BOARD_SIZE = 7;
    private boolean player1Turn = true; // "true" means player1's (White) turn."false" mean player2 (Black) turn.
    private ArrayList<Dice> dices = new ArrayList<>();


    //================================================CONSTRUCTOR=====================================================//

    public State(String state) {
        if (Cublino.isStateWellFormed(state)) {
            String diceEncodings = state.substring(1); // all the dice encodings are assumed to be valid

            // add all the dice pieces
            for (int i = 0; i < diceEncodings.length()-2; i+=3) {
                this.dices.add(new Dice(diceEncodings.substring(i,i+3)));
            }

            this.player1Turn = state.charAt(0) == 'P' || state.charAt(0) == 'C';
        }
    }
    //=============================================NON-STATIC METHODS=================================================//

    // Gets the piece at an X and Y coordinate
    public Dice getPieceAt(int x, int y) {
        for (Dice dice:dices) {
            if (dice.getPosition().getX() == x && dice.getPosition().getY() == y) {
                return dice;
            }
        }
        return null;
    }

    // Gets all pieces that are either white or black based on the input boolean
    public ArrayList<Dice> getPieces(boolean isWhitePieces) {
        ArrayList<Dice> dices = new ArrayList<>();

        for (Dice dice: this.dices) {
            if (isWhitePieces) {
                if (Character.isUpperCase(dice.toString().charAt(0))) {
                    dices.add(dice);
                }
            } else {
                if (!Character.isUpperCase(dice.toString().charAt(0))) {
                    dices.add(dice);
                }
            }
        }

        return dices;
    }
    public boolean getPlayerTurn() {
        return this.player1Turn;
    }

    //=================================================STATIC METHODS=================================================//
    //======================================================TESTS=====================================================//
}
