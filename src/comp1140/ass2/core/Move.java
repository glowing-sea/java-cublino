package comp1140.ass2.core;
import comp1140.ass2.Cublino;

public class Move {
    private Position[] positions;

    //================================================CONSTRUCTOR=====================================================//

    // (By Haoting)
    public Move(String encoding) {
        assert Cublino.isMoveWellFormed(encoding);
        int length = encoding.length() / 2; // length of the move array
        Position[] move = new Position[length];
        for(int i = 0; i < length; i++)
            move[i] = new Position(encoding.substring(i * 2, i * 2 + 2));
        this.positions = move;
    }

    // (By Group)
    public Position[] getPositions() {return positions;}
    public void setPositions(Position[] positions) {this.positions = positions;}


    //=============================================NON-STATIC METHODS=================================================//

    @Override // (By Haoting)
    public String toString(){
        StringBuilder output = new StringBuilder();
        for (Position pos : positions)
            output.append(pos);
        return output.toString();
    }

    /**
     * Task 8: (By Haoring)
     *
     * A move is valid if it satisfies the following conditions:
     * 1. The starting position of the move contains a dice belonging to the player who's turn it is.
     * 2. All steps in the move are valid.
     * 3. The move contains at least one step.
     * 4. Only the first step may be a tipping step.
     * 5. The starting and ending positions of the moved dice are different.
     *
     */

    public Boolean isValidMovePur(State state) {

        // Condition 3
        if (positions.length < 1) return false;

        // Condition 5
        Position start = positions[0];
        Position end = positions[positions.length - 1];
        if (start.equals(end))
            return false;

        // Condition 1
        boolean result = false;
        result = state.containPlayerDice(start,state.getPlayerTurn());
        if (!result)
            return false;

        // Condition 2 & 4
        Step checkedStep = new Step("a1a1");
        for (int i = 0; i < positions.length - 1; i++){
            checkedStep.setStep(positions[i], positions[i + 1]);
            if (!checkedStep.isValidStepPur(state))
                return false;
            if (i != 0 && checkedStep.isTip())
                return false;
        }
        return true;
    }

    //=================================================STATIC METHODS=================================================//
    //======================================================TESTS=====================================================//
    public static void main(String[] args) {
        Move m1 = new Move("g6f6d6");
        Move m2 = new Move("g6f6d6a1a2a3a4a5a6a7a8");
        System.out.println(m1 + ", " + m2.toString().equals("g6f6d6a1a2a3a4a5a6a7a8"));
    }
}
