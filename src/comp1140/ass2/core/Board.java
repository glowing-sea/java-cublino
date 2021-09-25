package comp1140.ass2.core;

import comp1140.ass2.Cublino;

import java.util.ArrayList;

public class Board {
    private static final int BOARD_SIZE = 7;

    private ArrayList<Piece> dices = new ArrayList<>();

    public Board(String state) {
        if (Cublino.isStateWellFormed(state)) {
            String diceEncodings = state.substring(1); // all the dice encodings are assumed to be valid

            // add all the dice pieces
            for (int i = 0; i < diceEncodings.length()-2; i+=3) {
                dices.add(new Piece(diceEncodings.substring(i,i+3)));
            }
        }
    }

    // Gets the piece at an X and Y coordinate
    public Piece getPieceAt(int x, int y) {
        for (Piece dice:dices) {
            if (dice.getPosition().getX() == x && dice.getPosition().getY() == y) {
                return dice;
            }
        }
        return null;
    }
}
