package comp1140.ass2.core;

public class Location {
    private int x;
    private int y;
    String encode;

    // Create a Location based on the x and y coordinates.
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Create a Location based on the string encoding.
    public Location(String s) {
        assert s.length() == 2;
        char[] list = s.toCharArray();
        this.y = Character.getNumericValue(list[1]);
        this.x = list[0] - 'a' + 1;
        this.encode = s;
    }

    // Setter and getter methods.
    public int getX() {return x;}
    public int getY() {return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}


    // Check if two locations are equal.
    public Boolean checkEquals(Location loc) {
        return this.x == loc.getX() && this.y == loc.getY();
    }

    // Checks if two locations are adjacent.
    public boolean isAdjacent(Location other) {
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

    // Dead Code
    // is 2 locations adjacent
    public boolean isValid(Location loc) { // checks if a location is valid
        return false; // default
    } // checks if location is valid
    public int toPosition(int x, int y) {
        return 0; // default
    } // sets the coordinate to a number corresponding to the position in the array
    // Dead Code


    // Check Methods
    public static void main(String[] args) {
        Location l1 = new Location(3, 3);
        Location l2 = new Location(3, 3);
        Location l3 = new Location(0, 0);
        Location l4 = new Location("c2");
        Location l5 = new Location("b7");
        Location l6 = new Location("a1");
        Location l7 = new Location("a2");

        System.out.println(l1.equals(l2));
        System.out.println(l2.equals(l3));

        System.out.println("The x value for location l4 is : " + l4.getX() + " and y value is : " + l4.getY());
        System.out.println("The x value for location l5 is : " + l5.getX() + " and y value is : " + l5.getY());


        if (l7.isAdjacent(l6))
            System.out.println("l7 is adjacent to l6.");
        if (l4.isAdjacent(l5))
            System.out.println("l4 is adjacent to l5.");
    }
    }


