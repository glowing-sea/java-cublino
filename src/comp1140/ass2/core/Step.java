package comp1140.ass2.core;

// (By Group)
public class Step {
    private Position start;
    private Position end;

    //================================================CONSTRUCTOR=====================================================//

    // (By Haoting)
    // A simple constructor of a step.
    public Step(String step) {
        this.start = new Position(step.substring(0,2));
        this.end = new Position(step.substring(2,4));
    }

    // (By Rajin)
    // A constructor of a step based on its encoding.
    public Step(Position startPosition, Position endPosition) {
        this.start = startPosition;
        this.end = endPosition;
    }

    //=============================================NON-STATIC METHODS=================================================//

    // (By Haoting)
    @Override
    public String toString() {
        return "" + start + end;
    }

    // Setter and getter method

    // (By Haoting)
    public void setStep(String step) {
        this.start = new Position(step.substring(0,2));
        this.end = new Position(step.substring(2,4));
    }

    // (By Haoting)
    public void setStep(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    // (By Group)
    public Position getStartPosition() {return start;}
    public Position getEndPosition() {return end;}



    /**
     * Task 7 (Object Version) (By Haoting Chen):
     *
     * The function takes a step and a valid state, check if the step is valid.
     *
     * A step is valid if it satisfies the following conditions:
     * 1. It represents either a tilt or a jump of a dice.
     * 2. The ending position of the step is not occupied.
     * 3. The step moves towards the opponent's end of the board or horizontally (along its current row).
     * 3. If it is a jump step, there is a dice in the position which is jumped over.
     *
     */

    public boolean isValidStepPur(State state) {

        // Check if the ending position is not occupied
        if (state.containDice(end))
            return false;

        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = end.getX();
        int y2 = end.getY();
        // For the white dice, moving one step forward means column number plus 1, vice versa.
        int forward = state.getPlayerTurn() ? 1 : -1;
        Position over = new Position(x1,y1); // The position where the dice jump over


        // Check if it is a valid forward move
        if (x1 == x2) {
            if (y1 + forward == y2) // Tilt forward
                return true;
            if (y1 + forward * 2 == y2) { // Jump forward
                over.setY(y1 + forward);
                return state.containDice(over);
            }
        }
        // Check if it is a valid horizontal move
        if (y1 == y2) {
            if (x1 + 1 == x2) // Tilt to the right
                return true;
            if (x1 - 1 == x2) // Tilt to the left
                return true;
            if (x1 + 2 == x2) { // Jump to the right
                over.setX(x1 + 1);
                return state.containDice(over);
            }
            if (x1 - 2 == x2) { // Jump to the left
                over.setX(x1 - 1);
                return state.containDice(over);
            }
        }
        return false;
    }

    public boolean isTip(){
        return start.isAdjacent(end);
    }

    //=================================================STATIC METHODS=================================================//

    //======================================================TESTS=====================================================//

    // (By Group)
    // Check Methods
    public static void main(String[] args) {
        Step s1 = new Step("a1a3");
        Step s2 = new Step("c5c7");
        Step s3 = new Step("g7g6");
        Step s4 = new Step("a1a3");
        System.out.println(s1 + "," + s2 + "," + s3 + "," + s4);
    }
}
