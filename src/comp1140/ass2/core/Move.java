package comp1140.ass2.core;
import comp1140.ass2.Cublino;

import java.util.ArrayList;
import java.util.Collections;

public class Move {
    private ArrayList<Position> positions;

    //================================================CONSTRUCTOR=====================================================//

    // (By Haoting)
    public Move(String encoding) {

        if (!Cublino.isMoveWellFormed(encoding)) throw new IllegalArgumentException();

        ArrayList<Position> move = new ArrayList<>();
        for(int i = 0; i < encoding.length(); i+=2)
            move.add(new Position(encoding.substring(i, i + 2)));
        this.positions = move;
    }

    public Move(ArrayList<Position> move){this.positions = move;}

    // Copier (By Group)
    public Move copy(){ return new Move(this.toString()); }

    // (By Group)
    public ArrayList<Position> getPositions() {return positions;}
    public void setPositions(ArrayList<Position> positions) {this.positions = positions;}

    //=============================================NON-STATIC METHODS=================================================//

    @Override // (By Haoting)
    public String toString(){
        StringBuilder output = new StringBuilder();
        for (Position pos : positions)
            output.append(pos);
        return output.toString();
    }


    /**
     * Task 8: (By Haoting)
     * Determine whether a move (sequence of steps) is valid for a given game.
     *
     * A move is valid if it satisfies the following conditions:
     * 1. The starting position of the move contains a dice belonging to the player who's turn it is.
     * 2. All steps in the move are valid.
     * 3. The move contains at least one step.
     * 4. Only the first step may be a tipping step.
     * 5. The starting and ending positions of the moved dice are different.
     *
     * ASSUMPTIONS: the state is of the Pur variant and valid. The move is well-formed.
     */

    public Boolean isValidMovePur(State state) {

        // Condition 3
        if (positions.size() < 1) return false;

        // Condition 5
        Position start = positions.get(0);
        Position end = this.getLastPosition();
        if (start.equals(end))
            return false;

        // Condition 1
        boolean result = false;
        result = state.containPlayerDice(start,state.getPlayerTurn());
        if (!result)
            return false;

        // Condition 2 & 4
        Step checkedStep = new Step("a1a1");
        for (int i = 0; i < positions.size() - 1; i++){
            checkedStep.setStep(positions.get(i), positions.get(i + 1));
            if (!checkedStep.isValidStepPur(state, start))
                return false;
            if (i != 0 && checkedStep.isTip())
                return false;
        }
        return true;
    }


    // Add a position to the move. (Change the current Move object)
    public void moveFurther (Position destination){
        this.positions.add(destination);
    }


    // By Haoting
    // Get the last position of the move
    public Position getLastPosition (){ return this.positions.get(positions.size() - 1); }

    //=================================================STATIC METHODS=================================================//



    //======================================================TESTS=====================================================//
    public static void main(String[] args) {
        Move m1 = new Move("g6f6d6");
        Move m2 = new Move("g6f6d6a1a2a3a4a5a6a7a8");
        System.out.println(m1 + ", " + m2.toString().equals("g6f6d6a1a2a3a4a5a6a7a8"));
    }
}
