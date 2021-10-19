package comp1140.ass2.core;
import comp1140.ass2.Cublino;

import java.util.ArrayList;



public class Move {
    private final ArrayList<Position> positions;

    //================================== CONSTRUCTOR & PRINTER & COPIER ==============================================//

    // (By Haoting)
    public Move(String encoding) {

        if (!Cublino.isMoveWellFormed(encoding)) throw new IllegalArgumentException();

        ArrayList<Position> move = new ArrayList<>();
        for(int i = 0; i < encoding.length(); i+=2)
            move.add(new Position(encoding.substring(i, i + 2)));
        this.positions = move;
    }

    public Move(ArrayList<Position> move){this.positions = move;}

    @Override // (By Haoting)
    public String toString(){
        StringBuilder output = new StringBuilder();
        for (Position pos : positions)
            output.append(pos);
        return output.toString();
    }

    // Copier (By Group)
    public Move copy(){ return new Move(this.toString()); }


    //========================================== SETTER & GETTER METHODS =============================================//

    // (By Group)
    public ArrayList<Position> getPositions() {return positions;}

    // (By Haoting)
    // Get the last position of the move
    public Position getEnd (){ return this.positions.get(positions.size() - 1); }
    public Position getStart (){ return this.positions.get(0); }


    // Add a new position to the move. (Change the current Move object)
    public void moveFurther (Position destination){ this.positions.add(destination); }

    //============================================== CHECKER METHODS =================================================//

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
        Position end = this.getEnd();
        if (start.equals(end))
            return false;

        // Condition 1
        boolean result = false;
        result = state.containDice(start,true);
        if (!result)
            return false;

        // Condition 2 & 4
        Step checkedStep = new Step("a1a1");
        for (int i = 0; i < positions.size() - 1; i++){
            checkedStep.setStep(positions.get(i), positions.get(i + 1));
            if (!checkedStep.isValidStepPur(state))
                return false;
            if (i != 0 && checkedStep.isTip())
                return false;
        }
        return true;
    }

    // (Written by Anubhav, edited by Haoting)
    public Boolean isValidMoveContra(State state) {

        // Only one step.
        if (positions.size() != 2)
            return false;

        Position start = positions.get(0);
        Position end = positions.get(1);
        int forward = state.getPlayerTurn() ? 1 : -1;

        // Check if the starting position and the ending position are adjacent.
        if (!start.isAdjacent(end))
            return false;

        // Check if it is a valid forward move
        if (start.getX() == end.getX()) {
            if (!(start.getY() + forward == end.getY())) // Tilt forward
                return false;
        }

        // No dice in the ending position and there is a current player's dice in the starting position.
        return !state.containDice(end, false) && state.containDice(start, true);
    }


    //=================================================STATIC METHODS=================================================//



    //======================================================TESTS=====================================================//
    public static void main(String[] args) {
        Move m = new Move("a1a2"); // empty spot, so should return true
        Move m1 = new Move("g6f6d6");
        Move m2 = new Move("g6f6d6a1a2a3a4a5a6a7a7");
        Move m3 = new Move("a1a2a3"); // should return false
        Move m4 = new Move("a2a1"); // false, behind move is invalid
        Move m5 = new Move("b3b4"); // invalid because position is occupied, is valid for s3

        State s1 = new State("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
        State s2 = new State("PWa1Db3Wc1Wd1We1Wf1Wg1va7vb4vc7vd7ve7vf7vg7");
        State s3 = new State("PWa1Wb3Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");

//        System.out.println(m1 + ", " + m2.toString().equals("g6f6d6a1a2a3a4a5a6a7a7"));
        System.out.println("Move " + m + " is valid is " + m.isValidMoveContra(s1));
        System.out.println("Move " + m3 + " is valid is " + m3.isValidMoveContra(s1));
        System.out.println("Move " + m4 + " is valid is " + m4.isValidMoveContra(s1));
        System.out.println("Move " + m5 + " is valid is " + m5.isValidMoveContra(s1));
        System.out.println("Move " + m5 + " is valid is " + m5.isValidMoveContra(s2));
        System.out.println("Move " + m5 + " is valid is " + m5.isValidMoveContra(s3));
    }




}
