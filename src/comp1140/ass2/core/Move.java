package comp1140.ass2.core;

public class Move {
    private Location startPoint;
    private Location endPoint;

    public Move(Location startPoint) {
        this.startPoint = startPoint;
    }

    public Location getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Location startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Location endPoint) {
        this.endPoint = endPoint;
    }

    public Location getEndPoint() {
        return endPoint;
    }

    public boolean isMoveValid(Move move) {
        return false; // default
    }

    public void implementMove(Move move) {

    }
}
