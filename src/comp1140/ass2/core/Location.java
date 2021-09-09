package comp1140.ass2.core;

public class Location {
    private int x;
    private int y;
    public Location(int x, int y) {
        this.x = x;
        this.y = y;

    }
    public Location(String s) {
        assert s.length() == 2;
        char[] list = s.toCharArray();
        this.y = Character.getNumericValue(list[1]);
        this.x = list[0] - 'a' + 1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Boolean checkEquals(Location loc) {
        if (this.x==loc.getX() && this.y==loc.getY()) {
            return true;
        }
        else {
            return false;
        }
    }


    public boolean isAdjacent(Location loc) { // checks if 2 locations are adjacent to one another
        return false; // default
    } // is 2 locations adjacent
    public boolean isValid(Location loc) { // checks if a location is valid
        return false; // default
    } // checks if location is valid
    public int toPosition(int x, int y) {
        return 0; // default
    } // sets the coordinate to a number corresponding to the position in the array

    public static void main(String[] args) {
        Location l1 = new Location(3,3);
        Location l2 = new Location(3,3);
        Location l3 = new Location(0,0);
        System.out.println(l1.equals(l2));
        System.out.println(l2.equals(l3));
        Location l4 = new Location("c2");
        System.out.println("The x value for location l4 is : " + l4.getX() + " and y value is : "+ l4.getY());
        Location l5 = new Location("b7");
        System.out.println("The x value for location l5 is : " + l5.getX() + " and y value is : "+ l5.getY());

        }
    }

