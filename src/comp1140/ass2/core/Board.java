package comp1140.ass2.core;

import java.util.ArrayList;

public class Board {
    private ArrayList<Piece> pieces;
    public Board(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public ArrayList<Piece> initialiseBoard() {
        return new ArrayList<>(); // default
    }
}
