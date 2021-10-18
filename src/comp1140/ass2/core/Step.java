package comp1140.ass2.core;
import comp1140.ass2.Cublino;

// (By Group)
public class Step {
    private Position start;
    private Position end;

    //=========================================== CONSTRUCTOR & PRINTER ==============================================//

    // (By Haoting)
    // A simple constructor of a step.
    public Step(String step) {
        if (!Cublino.isStepWellFormed(step)) throw new IllegalArgumentException();
        this.start = new Position(step.substring(0,2));
        this.end = new Position(step.substring(2,4));
    }

    // (By Rajin)
    // A constructor of a step based on its encoding.
    public Step(Position startPosition, Position endPosition) {
        this.start = startPosition;
        this.end = endPosition;
    }

    // (By Haoting)
    @Override
    public String toString() {
        return "" + start + end;
    }


    //========================================== SETTER & GETTER METHODS =============================================//

    // Setter and getter method
    // (By Haoting)
    public void setStep(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    // (By Group)
    public Position getStartPosition() {return start;}
    public Position getEndPosition() {return end;}

    //============================================== CHECKER METHODS =================================================//

    // Check whether a step is a step (By Group)
    public boolean isTip(){
        return start.isAdjacent(end);
    }

    /**
     * Task 7 (Object Version) (By Haoting Chen):
     * Determine whether a single step of a move is valid for a given Pur game.
     *
     * A step is valid if it satisfies the following conditions:
     * 1. It represents either a tilt or a jump of a dice.
     * 2. The ending position of the step is not occupied.
     * 3. The step moves towards the opponent's end of the board or horizontally (along its current row).
     * 3. If it is a jump step, there is a dice in the position which is jumped over.
     *
     * @param start The position where the dice start a move.
     * ASSUMPTIONS: the state is of the Pur variant and valid. The step is well-formed.
     */

    public boolean isValidStepPur(State state, Position movingDicePosition) {

        // Check if the ending position is not occupied
        if (state.containDice(end, false))
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
                return state.containDice(over, false);
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
                return !over.equals(movingDicePosition) && state.containDice(over, false);
            }
            if (x1 - 2 == x2) { // Jump to the left
                over.setX(x1 - 1);
                return !over.equals(movingDicePosition) && state.containDice(over, false);
            }
        }
        return false;
    }


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
