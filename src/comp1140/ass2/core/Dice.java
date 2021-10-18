package comp1140.ass2.core;

import comp1140.ass2.Cublino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// (By Group)
public class Dice implements Comparable<Dice> {
    private final boolean isPlayer1; // the player's type
    private Position position; // the piece's position
    private char orientation;

    //=========================================== CONSTRUCTOR & PRINTER ==============================================//

    // (By Haoting & Rajin)
    // A simple constructor of a Dice.
    public Dice(boolean isPlayer1, Position position, char orientation) {
        this.isPlayer1 = isPlayer1;
        this.position = position;
        this.orientation = orientation;
    }

    // (By Haoting & Rajin)
    // A constructor of a dice based on its encoding.
    public Dice(String encoding){

        if (!(Cublino.isDiceWellFormed(encoding)) && encoding.length() == 3) throw new IllegalArgumentException();

        int colNum = encoding.charAt(1) - 96;
        int rowNum = encoding.charAt(2) - 48;
        this.orientation = encoding.charAt(0); // assign orientation value
        this.position = new Position(colNum,rowNum); // assignment of location based on encoding
        this.isPlayer1 = Character.isUpperCase(orientation); // assign the player's type
    }

    // (Written by Rajin and edited by Haoting)
    @Override
    public String toString() {
        return (isPlayer1 ? Character.toUpperCase(orientation) : Character.toLowerCase(orientation)) +
                position.toString();
    }
    // (By Haoting)
    @Override
    public int compareTo(Dice other) {
        return this.getPosition().getPositionOrder() - other.getPosition().getPositionOrder();
    }

    //========================================= SETTER & GETTER METHODS ==============================================//

    // (By Group)
    // Getter Methods.
    public boolean isPlayer1() {return isPlayer1;}
    public Position getPosition() {return position;}

    // (By Haoting & Rajin)
    // Get the number of all faces of a dice
    // Format: {TOP, FORWARD, RIGHT, BEHIND, LEFT, BOTTOM}
    public int[] getFaces (){
        return TRANSLATION_TABLE.get(Character.toLowerCase(this.orientation));
    }
    // Get the top number of a dice
    public int getTopNumber() { return (TRANSLATION_TABLE.get(Character.toLowerCase(this.orientation)))[0]; }


    // Give a position, update the position of the dice. (By Haoting)
    public void jump(Position position) {this.position = position;}

    // (Written by Anubhav and edited by Haoting)
    // Give a tip step, update the both position and direction of the dice result from the step.
    // Do nothing if a step is not a tip.
    public void tip(Step step){
        int[] initialFaces = this.getFaces();
        int[] newFaces = new int[6];

        if (step.getEndPosition().getX() - step.getStartPosition().getX() == 1) { // Tip to the right
            newFaces[0] = initialFaces[4];
            newFaces[1] = initialFaces[1];
            newFaces[2] = initialFaces[0];
            newFaces[3] = initialFaces[3];
            newFaces[4] = initialFaces[5];
            newFaces[5] = initialFaces[2];
        }
        if (step.getEndPosition().getX() - step.getStartPosition().getX() == -1) { // Tip to the left
            newFaces[0] = initialFaces[2];
            newFaces[1] = initialFaces[1];
            newFaces[2] = initialFaces[5];
            newFaces[3] = initialFaces[3];
            newFaces[4] = initialFaces[0];
            newFaces[5] = initialFaces[4];
        }
        if (step.getEndPosition().getY() - step.getStartPosition().getY() == 1) { // Tip to the forward (Player1)
            newFaces[0] = initialFaces[3];
            newFaces[1] = initialFaces[0];
            newFaces[2] = initialFaces[2];
            newFaces[3] = initialFaces[5];
            newFaces[4] = initialFaces[4];
            newFaces[5] = initialFaces[1];
        }
        if (step.getEndPosition().getY() - step.getStartPosition().getY() == -1) { // Tip to the forward (Player2)
            newFaces[0] = initialFaces[1];
            newFaces[1] = initialFaces[5];
            newFaces[2] = initialFaces[2];
            newFaces[3] = initialFaces[0];
            newFaces[4] = initialFaces[4];
            newFaces[5] = initialFaces[3];
        }
        // Get the key
        for (Map.Entry<Character, int[]> faces: TRANSLATION_TABLE.entrySet()) {
            if (Arrays.equals(newFaces, faces.getValue())){
                this.orientation =
                        isPlayer1 ? Character.toUpperCase(faces.getKey()) : Character.toLowerCase(faces.getKey());
                break;
            }
        }
    }

    //=================================================STATIC METHODS=================================================//

    // (By Haoting)
    // Give a state(board) and a position, find all the adjacent dices.
    public static ArrayList<Dice> adjacentDices (Position position, State state){
        ArrayList<Dice> adjDices = new ArrayList<>();
        for (Dice dice : state.getDices()){
            if (dice.getPosition().isAdjacent(position))
                adjDices.add(dice);
        }
        return adjDices;
    }

    // (Written by Rajin and reviewed by Hoating)
    // A translation map from the characters available to an array containing [TOP, FORWARD, RIGHT, BEHIND, LEFT, BOTTOM]
    private static final HashMap<Character, int[]> TRANSLATION_TABLE = new HashMap<>();
    static {
        TRANSLATION_TABLE.put('a', new int[]{1, 2, 4, 5, 3, 6});
        TRANSLATION_TABLE.put('b', new int[]{1, 3, 2, 4, 5, 6});
        TRANSLATION_TABLE.put('c', new int[]{1, 4, 5, 3, 2, 6});
        TRANSLATION_TABLE.put('d', new int[]{1, 5, 3, 2, 4, 6});

        TRANSLATION_TABLE.put('e', new int[]{2, 1, 3, 6, 4, 5});
        TRANSLATION_TABLE.put('f', new int[]{2, 3, 6, 4, 1, 5});
        TRANSLATION_TABLE.put('g', new int[]{2, 4, 1, 3, 6, 5});
        TRANSLATION_TABLE.put('h', new int[]{2, 6, 4, 1, 3, 5});

        TRANSLATION_TABLE.put('i', new int[]{3, 1, 5, 6, 2, 4});
        TRANSLATION_TABLE.put('j', new int[]{3, 2, 1, 5, 6, 4});
        TRANSLATION_TABLE.put('k', new int[]{3, 5, 6, 2, 1, 4}); // Corrected from {3, 5, 1, 2, 6, 4}
        TRANSLATION_TABLE.put('l', new int[]{3, 6, 2, 1, 5, 4});

        TRANSLATION_TABLE.put('m', new int[]{4, 1, 2, 6, 5, 3});
        TRANSLATION_TABLE.put('n', new int[]{4, 2, 6, 5, 1, 3});
        TRANSLATION_TABLE.put('o', new int[]{4, 5, 1, 2, 6, 3});
        TRANSLATION_TABLE.put('p', new int[]{4, 6, 5, 1, 2, 3});

        TRANSLATION_TABLE.put('q', new int[]{5, 1, 4, 6, 3, 2});
        TRANSLATION_TABLE.put('r', new int[]{5, 3, 1, 4, 6, 2});
        TRANSLATION_TABLE.put('s', new int[]{5, 4, 6, 3, 1, 2});
        TRANSLATION_TABLE.put('t', new int[]{5, 6, 3, 1, 4, 2});

        TRANSLATION_TABLE.put('u', new int[]{6, 2, 3, 5, 4, 1});
        TRANSLATION_TABLE.put('v', new int[]{6, 3, 5, 4, 2, 1});
        TRANSLATION_TABLE.put('w', new int[]{6, 4, 2, 3, 5, 1});
        TRANSLATION_TABLE.put('x', new int[]{6, 5, 4, 2, 3, 1});
    }

    //============================================== DEAD CODE =======================================================//

    // May no long needed
    // (By Anubhav)
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

    //======================================================TESTS=====================================================//

    // (By Group)
    // Check Methods
    public static void main(String[] args) {
        Dice d1 = new Dice("ac2");
        Dice d2 = new Dice("Xb7");
        Dice d3 = new Dice("ca1");
        Dice d4 = new Dice("Mb6");
        System.out.println(Arrays.toString(d1.getFaces()));
        System.out.println(d1.getTopNumber());


        State state1 = new State("pCe1Xb2pd2fd3Ge3Rg3ia4Lc4Td4qe4Gb5rf5cg5if6");
        Dice d5 = new Dice("Td4");
        System.out.println(adjacentDices(d5.getPosition(),state1));
        System.out.println(d1 + "," + d2 + "," + d3 + "," + d4);
    }
}

