package comp1140.ass2.core;

import comp1140.ass2.Cublino;

import java.util.ArrayList;
import java.util.HashMap;

// (By Group)
public class Position {
    private int x;
    private int y;

    //================================================CONSTRUCTOR=====================================================//

    // (By Anubhav)
    // Create a Position based on the x and y coordinates.
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // (By Anubhav & Haoting)
    // Create a Position based on the string encoding.
    public Position(String s) {

        assert Cublino.isPositionWellFormed(s) && s.length() == 2 :
                "The input position string is not well-formed.";

        // This may be simpler.
        this.x = s.charAt(0) - 96;
        this.y = s.charAt(1) - 48;
    }

    //=============================================NON-STATIC METHODS=================================================//

    // (By Haoting)
    @Override
    public String toString() {
        char x = (char) (this.x + 96);
        return "" + x + this.y;
    }

    // (By Group)
    // Setter and getter methods.
    public int getX() {return x;}
    public int getY() {return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}

    // (By Haoting)
    // Get the position of a dice in terms of order (From 1 to 49)
    public int getPositionOrder() {
        HashMap<String, Integer> POSITION_ORDER = new HashMap<>();
        Integer order = 1;
        for (char y = '1'; y <= '7'; y++){
            for (char x = 'a'; x <= 'g'; x++){
                POSITION_ORDER.put("" + x + y, order);
                order++; } }
        return POSITION_ORDER.get(this.toString());
    }


    // (By Anubhav)
    // Check if two locations are equal.
    public Boolean equals(Position pos) {
        return this.x == pos.getX() && this.y == pos.getY();
    }

    // (By Haoting)
    // Checks if two locations are adjacent.
    public boolean isAdjacent(Position other) {
        int x1 = this.x;
        int y1 = this.y;
        int x2 = other.getX();
        int y2 = other.getY();

        if (x1 == x2)
            return y1 == y2 + 1 || y2 == y1 + 1;

        if (y1 == y2)
            return x1 == x2 + 1 || x2 == x1 + 1;

        return false;
    }

    // (By Anubhav)
    public boolean isOnBoard () {
        return (this.x <= 7 && this.x >= 1) && (this.y <= 7 && this.y >= 1);
    }

    // (By Group)
    // Dead Code
    // is 2 locations adjacent
    public boolean isValid(Position loc) { // checks if a location is valid
        return false; // default
    } // checks if location is valid

    // (By Group)
    public int toPosition(int x, int y) {
        return 0; // default
    } // sets the coordinate to a number corresponding to the position in the array
    // Dead Code


    //=================================================STATIC METHODS=================================================//

    // (By Rajin)
    // Method to calculate the manhattanDistance between two locations
    public static int manhattanDistance(Position loc1, Position loc2) {
        return (Math.abs(loc1.getX() - loc2.getX()) + Math.abs(loc1.getY() - loc2.getY()));
    }

    //======================================================TESTS=====================================================//

    // (By Group)
    // Check Methods
    public static void main(String[] args) {
        Position l1 = new Position(1, 2);
        Position l2 = new Position(3, 3);
        Position l3 = new Position(0, 0);
        Position l4 = new Position("a9");
        Position l5 = new Position("b7");
        Position l6 = new Position("a1");

        Position l7 = new Position("b6");
        Position l8 = new Position("b6");

        System.out.println(l7.equals(l8));
        System.out.println(l7.equals(l8));


        System.out.println(l1.equals(l2));
        System.out.println(l2.equals(l3));

        System.out.println("The x value for location l4 is : " + l4.getX() + " and y value is : " + l4.getY());
        System.out.println("The x value for location l5 is : " + l5.getX() + " and y value is : " + l5.getY());


        if (l7.isAdjacent(l6))
            System.out.println("l7 is adjacent to l6.");
        if (l4.isAdjacent(l5))
            System.out.println("l4 is adjacent to l5.");

        System.out.println(l4.getX() + "," + l4.getY());
        System.out.println(l1 + "," + l2 + "," + l3 + "," + l4 + "," + l5 + "," + l6 +  "," + l7);
        System.out.println(l1.getPositionOrder());
    }
}


