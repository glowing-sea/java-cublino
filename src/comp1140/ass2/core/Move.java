package comp1140.ass2.core;
import comp1140.ass2.Cublino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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

    /**
     * Task 9: (By Anubhav and Haoting)
     * Given a Pur game state and a move to play, determine the state that results from that move being played.
     * If the move ends the game, the turn should be the player who would have played next had the game not ended. If
     * the move is invalid the game state should remain unchanged.
     *
     * ASSUMPTION: the state is of Pur and valid. The move is well-formed.
     */
    public static State applyMovePur(State st, Move m) {
        if(!m.isValidMovePur(st))
            return st;

        Position[] pos = m.getPositions();
        Position start = pos[0]; // Starting position of a move.
        Position end = pos[pos.length - 1]; // Ending position of a move.
        Step firstStep = new Step(start, (pos[1])); // The first step of a move.

        for (Dice dice : st.getDices()) {
            if (dice.getPosition().equals(start)) { // Find out the dice that need to be moved!
                dice.tip(firstStep); // Tip the dice if needed.
                dice.jump(end); // Update the location of the dice
            }
        }
        st.changeTurn(); // Update the turn.
        Collections.sort(st.getDices()); // Sort the list of dices.
        return st;
    }

    //======================================================TESTS=====================================================//
    public static void main(String[] args) {
        Move m1 = new Move("g6f6d6");
        Move m2 = new Move("g6f6d6a1a2a3a4a5a6a7a8");
        System.out.println(m1 + ", " + m2.toString().equals("g6f6d6a1a2a3a4a5a6a7a8"));
    }
}
