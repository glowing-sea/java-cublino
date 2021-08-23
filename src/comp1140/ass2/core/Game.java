package comp1140.ass2.core;

public class Game {
    private Board board;

    public Game(Board board) {
        this.board = board;
    }

    public Board getB() {
        return board;
    } // returns the board

    public boolean isGameOver(Board board) {
        return false; // default
    } // checks if the game is over
}
