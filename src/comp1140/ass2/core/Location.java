package comp1140.ass2.core;

public class Location {
    private int x;
    private int y;
    public Location(int x, int y) {
        this.x = x;
        this.y = y;

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
}
