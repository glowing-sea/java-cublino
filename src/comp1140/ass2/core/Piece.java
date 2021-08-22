package comp1140.ass2.core;

import java.util.ArrayList;

public class Piece {
    private int facing;
    private boolean isPlayer1; // make a method which sets the player, and make it final
    public Piece(int facing, boolean isPlayer1) {
        this.facing = facing;
        this.isPlayer1 = isPlayer1;
    }
    public Piece[] getAdjacentPieces(Board board) {
        Piece[] def = new Piece[4];
        return def;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public int getFacing() {
        return facing;
    }
    public ArrayList<Move> getLegalMoves(Piece p1, Board b1) {
        ArrayList <Move> legalMoves = new ArrayList<>();
        return legalMoves;

    }
}
