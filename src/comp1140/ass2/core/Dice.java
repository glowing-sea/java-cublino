package comp1140.ass2.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Dice {
    private int topNumber; // number on the top of the dice
    private int[] sides; // format: TOP, FORWARD, RIGHT, BEHIND, LEFT, BOTTOM
    private boolean isPlayer1; // the player's type
    private Position position; // the piece's position
    private char orientation;



    public Dice(int[] sides, boolean isPlayer1, Position position) {
        this.topNumber = sides[0];
        this.sides = sides;
        this.isPlayer1 = isPlayer1;
        this.position = position;
    }

    // Constructor for other fields
    /* public Dice(char x) {
        switch (Character.toLowerCase(x)) {
            case 'a' :
                this.topNumber = 1;
                this.frontNumber = 2;
                this.leftNumber = 3;
            case 'b' :
                this.topNumber = 1;
                this.frontNumber = 3;
                this.leftNumber = 5;
            case 'c' :
                this.topNumber = 1;
                this.frontNumber = 4;
                this.leftNumber = 2;
            case 'd' :
                this.topNumber = 1;
                this.frontNumber = 5;
                this.leftNumber = 4;
            case 'e' :
                this.topNumber = 2;
                this.frontNumber = 1;
                this.leftNumber = 4;
        }
    } */

    // method to change the faces of the dice
    public static int[] changeFaces(int[] initialFaces, Step step) {
        if (!step.isTip()) {
            return initialFaces;
        }
        else {
            int[] newFaces = new int[6];
            if (step.getEndPosition().getX() - step.getStartPosition().getX() == 1) {
                newFaces[0] = initialFaces[2];
                newFaces[1] = initialFaces[1];
                newFaces[2] = initialFaces[0];
                newFaces[3] = initialFaces[3];
                newFaces[4] = initialFaces[5];
                newFaces[5] = initialFaces[4];
                return newFaces;

            }
            else if (step.getEndPosition().getX() - step.getStartPosition().getX() == -1) {
                newFaces[0] = initialFaces[2];
                newFaces[1] = initialFaces[1];
                newFaces[2] = initialFaces[5];
                newFaces[3] = initialFaces[3];
                newFaces[4] = initialFaces[0];
                newFaces[5] = initialFaces[4];
                return newFaces;
            }
            else if (step.getEndPosition().getY() - step.getStartPosition().getY() == 1) {
                newFaces[0] = initialFaces[3];
                newFaces[1] = initialFaces[0];
                newFaces[2] = initialFaces[2];
                newFaces[3] = initialFaces[5];
                newFaces[4] = initialFaces[4];
                newFaces[5] = initialFaces[1];
                return newFaces;
            }
            else if (step.getEndPosition().getY() - step.getStartPosition().getY() == -1) {
                newFaces[0] = initialFaces[1];
                newFaces[1] = initialFaces[5];
                newFaces[2] = initialFaces[2];
                newFaces[3] = initialFaces[0];
                newFaces[4] = initialFaces[4];
                newFaces[5] = initialFaces[3];
                return newFaces;
            }
            else {
                return null;
            }


        }
        // [TOP, FORWARD, RIGHT, BEHIND, LEFT, BOTTOM] is the encoding

    }

    public static char getOrientation(int[] sides, boolean isPlayer1) {
        char x = 0;
        if (sides[0] == 1 && sides[1] == 2) {
            x = 'a';
        }
        else if (sides[0] == 1 && sides[1] == 3) {
            x = 'b';
        }
        else if (sides[0] == 1 && sides[1] == 4) {
            x = 'c';
        }
        else if (sides[0] == 1 && sides[1] == 5) {
            x = 'd';
        }
        else if (sides[0] == 2 && sides[1] == 1) {
            x = 'e';
        }
        else if (sides[0] == 2 && sides[1] == 3) {
            x = 'f';
        }
        else if (sides[0] == 2 && sides[1] == 4) {
            x = 'g';
        }
        else if (sides[0] == 2 && sides[1] == 6) {
            x = 'h';
        }
        else if (sides[0] == 3 && sides[1] == 1) {
            x = 'i';
        }
        else if (sides[0] == 3 && sides[1] == 2) {
            x = 'j';
        }
        else if (sides[0] == 3 && sides[1] == 5) {
            x = 'k';
        }
        else if (sides[0] == 3 && sides[1] == 6) {
            x = 'l';
        }
        else if (sides[0] == 4 && sides[1] == 1) {
            x = 'm';
        }
        else if (sides[0] == 4 && sides[1] == 2) {
            x = 'n';
        }
        else if (sides[0] == 4 && sides[1] == 5) {
            x = 'o';
        }
        else if (sides[0] == 4 && sides[1] == 6) {
            x = 'p';
        }
        else if (sides[0] == 5 && sides[1] == 1) {
            x = 'q';
        }
        else if (sides[0] == 5 && sides[1] == 3) {
            x = 'r';
        }
        else if (sides[0] == 5 && sides[1] == 4) {
            x = 's';
        }
        else if (sides[0] == 5 && sides[1] == 6) {
            x = 't';
        }
        else if (sides[0] == 6 && sides[1] == 2) {
            x = 'u';
        }
        else if (sides[0] == 6 && sides[1] == 3) {
            x = 'v';
        }
        else if (sides[0] == 6 && sides[1] == 4) {
            x = 'w';
        }
        else if (sides[0] == 6 && sides[1] == 5) {
            x = 'x';
        }
        if (isPlayer1) {
            return Character.toUpperCase(x);
        }
        else {
            return x;
        }
        


    }

    // An intuitive constructor for generating Dice objects from dice encodings
    public Dice(String encoding) {

        char orientation = encoding.charAt(0);
        char column = encoding.charAt(1);
        char row = encoding.charAt(2);

        if (Character.isUpperCase(orientation)) { // assign the player's type
            this.isPlayer1 = true;
        }

        // A translation map from the characters available to an array containing [TOP, FORWARD, RIGHT, BEHIND, LEFT, BOTTOM]
        HashMap<Character, int[]> translationTable = new HashMap<>();
        translationTable.put('a', new int[] {1,2,4,5,3,6});
        translationTable.put('b', new int[] {1,3,2,4,5,6});
        translationTable.put('c', new int[] {1,4,5,3,2,6});
        translationTable.put('d', new int[] {1,5,3,2,4,6});

        translationTable.put('e', new int[] {2,1,3,6,4,5});
        translationTable.put('f', new int[] {2,3,6,4,1,5});
        translationTable.put('g', new int[] {2,4,1,3,6,5});
        translationTable.put('h', new int[] {2,6,4,1,3,5});

        translationTable.put('i', new int[] {3,1,5,6,2,4});
        translationTable.put('j', new int[] {3,2,1,5,6,4});
        translationTable.put('k', new int[] {3,5,1,2,6,4});
        translationTable.put('l', new int[] {3,6,2,1,5,4});

        translationTable.put('m', new int[] {4,1,2,6,5,3});
        translationTable.put('n', new int[] {4,2,6,5,1,3});
        translationTable.put('o', new int[] {4,5,1,2,6,3});
        translationTable.put('p', new int[] {4,6,5,1,2,3});

        translationTable.put('q', new int[] {5,1,4,6,3,2});
        translationTable.put('r', new int[] {5,3,1,4,6,2});
        translationTable.put('s', new int[] {5,4,6,3,1,2});
        translationTable.put('t', new int[] {5,6,3,1,4,2});

        translationTable.put('u', new int[] {6,2,3,5,4,1});
        translationTable.put('v', new int[] {6,3,5,4,2,1});
        translationTable.put('w', new int[] {6,4,2,3,5,1});
        translationTable.put('x', new int[] {6,5,4,2,3,1});

        this.topNumber = translationTable.get(Character.toLowerCase(orientation))[0]; // assign top facing number
        this.sides = Arrays.copyOf(translationTable.get(Character.toLowerCase(orientation)),6); // assign side values
        this.orientation = orientation; // assign orientation value

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

        this.position = new Position(columnNum,rowNum); // assignment of location based on encoding
    }

    public boolean isPlayer1() {
        return isPlayer1;
    }

    public Position getPosition() {
        return position;
    }

    public Dice[] getAdjacentPieces(State state) {
        Dice[] def = new Dice[4];
        return def;
    } // returns the adjacent pieces as a list

    public void setTopNumber(int topNumber) {
        this.topNumber = topNumber;
    } // returns the number that is facing up on the dice

    public int getTopNumber() {
        return this.topNumber;
    }

    public ArrayList<Step> getLegalMoves(Dice p1, State b1) {
        return null;
        // TODO: need to create a way to get all legal moves for a given piece with the current game state
    }

    public int[] getSides() {
        return sides;
    }

    public void setSides(int[] sides) {
        this.sides = sides;
    }

    @Override
    public String toString() {
        StringBuilder encoding = new StringBuilder();
        encoding.append(this.orientation);
        encoding.append(this.position.getEncoding());
        return encoding.toString();
    }
}
