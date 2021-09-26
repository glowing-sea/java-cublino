package comp1140.ass2.core;

import comp1140.ass2.Cublino;

import java.util.ArrayList;

public class Board {
    private static final int BOARD_SIZE = 7;
    private int playerTurn = 1; // value of means the current player's turn is player 1 (White) or 2 means player 2 (Black)

    private ArrayList<Piece> dices = new ArrayList<>();

    public Board(String state) {
        if (Cublino.isStateWellFormed(state)) {
            String diceEncodings = state.substring(1); // all the dice encodings are assumed to be valid

            // add all the dice pieces
            for (int i = 0; i < diceEncodings.length()-2; i+=3) {
                this.dices.add(new Piece(diceEncodings.substring(i,i+3)));
            }

            if (state.charAt(0) == 'P' || state.charAt(0) == 'C') {
                this.playerTurn = 1;
            } else {
                this.playerTurn = 2;
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

    // Gets all pieces that are either white or black based on the input boolean
    public ArrayList<Piece> getPieces(boolean isWhitePieces) {
        ArrayList<Piece> pieces = new ArrayList<>();

        for (Piece dice:dices) {
            if (isWhitePieces) {
                if (Character.isUpperCase(dice.getEncoding().charAt(0))) {
                    pieces.add(dice);
                }
            } else {
                if (!Character.isUpperCase(dice.getEncoding().charAt(0))) {
                    pieces.add(dice);
                }
            }
        }

        return pieces;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }
}
