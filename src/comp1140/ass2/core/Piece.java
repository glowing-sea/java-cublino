package comp1140.ass2.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Piece {
    private int topNumber; // number on the top of the dice
    private int[] sides = new int[4]; // format: forward, right, behind, left
    private String encoding = ""; // dice encoding
    private boolean isPlayer1; // the player's type
    private Location position; // the piece's position

    public Piece(int topNumber,int[] sides, boolean isPlayer1, Location position) {
        this.topNumber = topNumber;
        this.sides = sides;
        this.isPlayer1 = isPlayer1;
        this.position = position;
    }

    // An intuitive constructor for generating Piece objects from dice encodings
    public Piece(String encoding) {
        this.encoding = encoding;

        char orientation = encoding.charAt(0);
        char column = encoding.charAt(1);
        char row = encoding.charAt(2);

        if (Character.isUpperCase(orientation)) { // assign the player's type
            this.isPlayer1 = true;
        }

        // A translation map from the characters available to an array containing [TOP, FORWARD, RIGHT, BEHIND, LEFT]
        HashMap<Character, int[]> translationTable = new HashMap<>();
        translationTable.put('a', new int[] {1,2,4,6,3});
        translationTable.put('b', new int[] {1,3,2,4,6});
        translationTable.put('c', new int[] {1,4,6,3,2});
        translationTable.put('d', new int[] {1,6,3,2,4});

        translationTable.put('e', new int[] {2,1,3,5,4});
        translationTable.put('f', new int[] {2,3,5,4,1});
        translationTable.put('g', new int[] {2,4,1,3,5});
        translationTable.put('h', new int[] {2,5,4,1,3});

        translationTable.put('i', new int[] {3,1,6,5,2});
        translationTable.put('j', new int[] {3,2,1,6,5});
        translationTable.put('k', new int[] {3,5,2,1,6});
        translationTable.put('l', new int[] {3,6,5,2,1});

        translationTable.put('m', new int[] {4,1,2,5,6});
        translationTable.put('n', new int[] {4,2,5,6,1});
        translationTable.put('o', new int[] {4,5,6,1,2});
        translationTable.put('p', new int[] {4,6,1,2,5});

        translationTable.put('q', new int[] {5,2,3,6,4});
        translationTable.put('r', new int[] {5,3,6,4,2});
        translationTable.put('s', new int[] {5,4,2,3,6});
        translationTable.put('t', new int[] {5,6,4,2,3});

        translationTable.put('u', new int[] {6,1,4,5,3});
        translationTable.put('v', new int[] {6,3,1,4,5});
        translationTable.put('w', new int[] {6,4,5,3,1});
        translationTable.put('x', new int[] {6,5,3,1,4});

        this.topNumber = translationTable.get(Character.toLowerCase(orientation))[0]; // assign top facing number
        this.sides = Arrays.copyOfRange(translationTable.get(Character.toLowerCase(orientation)), 1, 5); // assign side values

        int columnNum = 0;
        int rowNum = Character.getNumericValue(row);

        if (column == 'a') {
            columnNum = 1;
        } else if (column == 'b') {
            columnNum = 2;
        } else if (column == 'c') {
            columnNum = 3;
        } else if (column == 'd') {
            columnNum = 4;
        } else if (column == 'e') {
            columnNum = 5;
        } else if (column == 'f') {
            columnNum = 6;
        } else if (column == 'g') {
            columnNum = 7;
        }

        this.position = new Location(rowNum,columnNum); // assignment of location based on encoding
    }

    public boolean isPlayer1() {
        return isPlayer1;
    }

    public Location getPosition() {
        return position;
    }

    public Piece[] getAdjacentPieces(Board board) {
        Piece[] def = new Piece[4];
        return def;
    } // returns the adjacent pieces as a list
    public void setTopNumber(int topNumber) {
        this.topNumber = topNumber;
    } // returns the number that is facing up on the dice

    public int getTopNumber() {
        return this.topNumber;
    }
    public ArrayList<Move> getLegalMoves(Piece p1, Board b1) {
        ArrayList <Move> legalMoves = new ArrayList<>();
        return legalMoves;

    } // returns the set of legal moves for a piece on a board

    public int[] getSides() {
        return sides;
    }

    public void setSides(int[] sides) {
        this.sides = sides;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
