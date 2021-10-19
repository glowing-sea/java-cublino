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

    public Boolean isValidMoveContra(State state) {
        if (positions.size() != 2) {
            return false;
        }
        if (!(this.positions.get(0).isAdjacent(this.positions.get(1))) && (this.positions.get(0).getY() - this.positions.get(1).getY() !=1)) return false;
        // note: accounted for the fact that you can't tip backwards, so starting y cannot be one bigger than ending y
        boolean containsStartingDice = false;
        for (Dice d : state.getDices()) {
            if (d.getPosition().equals(this.positions.get(0))) {
                containsStartingDice = true;
            }
            if (d.getPosition().equals(this.positions.get(1))) return false;
            // none of the dice in the board should return the endpoint of the move location
        }
        return true && containsStartingDice; // move should contain starting dice
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
        Move m = new Move("a1a2"); // empty spot, so should return true
        Move m3 = new Move("a1a2a3"); // should return false
        Move m4 = new Move("a2a1"); // false, behind move is invalid
        State s1 = new State("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
        State s2 = new State("PWa1Db3Wc1Wd1We1Wf1Wg1va7vb4vc7vd7ve7vf7vg7");
        State s3 = new State("PWa1Wb3Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
        Move m5 = new Move("b3b4"); // invalid because position is occupied, is valid for s3
        System.out.println("Move " + m.toString() + " is valid is " + m.isValidMoveContra(s1));
        System.out.println("Move " + m3.toString() + " is valid is " + m3.isValidMoveContra(s1));
        System.out.println("Move " + m4.toString() + " is valid is " + m4.isValidMoveContra(s1));
        System.out.println("Move " + m5.toString() + " is valid is " + m5.isValidMoveContra(s2));
        System.out.println("Move " + m5.toString() + " is valid is " + m5.isValidMoveContra(s1));
        System.out.println("Move " + m5.toString() + " is valid is " + m5.isValidMoveContra(s3));
    }




}
