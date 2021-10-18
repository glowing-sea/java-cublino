package comp1140.ass2.core;

import comp1140.ass2.Cublino;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.lang.Cloneable;

// (By Group)
public class State {
    private static final int BOARD_SIZE = 7;
    private final boolean pur; // "true" means Pur."false" mean Contra.
    private boolean player1Turn; // "true" means player1's (White) turn."false" means player2 (Black) turn.
    private ArrayList<Dice> dices = new ArrayList<>();

    //======================================= CONSTRUCTOR & PRINTER ==================================================//

    // (Created by Rajin, edited by Haoting)
    public State(String state) {
        assert Cublino.isStateWellFormed(state) : "The input state string is not well-formed.";

        this.player1Turn = state.charAt(0) == 'P' || state.charAt(0) == 'C';
        this.pur = state.charAt(0) == 'P' || state.charAt(0) == 'p';

        // add all the dice pieces
        for (int i = 1; i < state.length(); i += 3) {
            this.dices.add(new Dice(state.substring(i, i + 3)));
        }
    }

    public State(boolean pur, boolean player1Turn, ArrayList<Dice> dices){
        this.pur = pur;
        this.player1Turn = player1Turn;
        this.dices = dices;
    }

    // (By Haoting)
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        if(this.pur)
            output.append( player1Turn ? 'P' : 'p');
        else
            output.append( player1Turn ? 'C' : 'c');
        for (Dice dice : dices)
            output.append(dice);
        return output.toString();
    }


    //============================================ CHECKER METHODS ===================================================//

    /**
     * Task 4: (Object version) (Written by Anubhav, edited by Haoting)
     * Determine whether the input state is valid.
     * ASSUMPTIONS: the state is well-formed.
     */
    public Boolean isStateValid(){

        LinkedList<Position> tempPos = new LinkedList<>();
        int p1onOtherSide = 0; // The number of player1's dices that reaches player2's end.
        int p2OnOtherSide = 0; // The number of player2's dices that reaches player1's end.
        int noOfDiceP1 = 0; // The number of player1's dices.
        int noOfDiceP2 = 0; // The number of player2's dices.

        for (Dice dice : this.getDices()){
            Position pos = dice.getPosition();

            // Checked if the location is already on the board by storing the previous locations
            for (Position j : tempPos){
                if (j.equals(pos))
                    return false;}
            tempPos.add(pos);

            if (dice.isPlayer1()) {
                noOfDiceP1++; // Count the number of player1's dices.
                if (pos.getY() == 7)
                    p1onOtherSide++; // Count the number of player1's dices that have reached the end
            }
            else {
                noOfDiceP2++; // Count the number of player2's dices.
                if (pos.getY() == 1)
                    p2OnOtherSide++; // Count the number of player1's dices that have reached the end.
            }
        }

        // checking dice/piece counts for variants and dice/piece counts at each end of the board
        if (this.isPur()) {
            if (p1onOtherSide == 7 && p2OnOtherSide == 7)
                return false;
            else
                return (noOfDiceP1 ==7 && noOfDiceP2 == 7);
        }
        else {
            if (p1onOtherSide>=1 && p2OnOtherSide>=1)
                return false;
            else
                return noOfDiceP2 <= 7 & noOfDiceP2 <= 7;
        }
    }


    // (By Haoting Chen)
    // Given a position, check if there is a dice at that position.
    public boolean containDice (Position position){
        for(Dice dice : this.getDices())
            if (position.equals(dice.getPosition()))
                return true;
        return false;
    }

    // (By Haoting Chen)
    // Given a position and a player name, check if there is a player's dice at that position.
    public boolean containPlayerDice (Position position, Boolean isPlayer1){
        for(Dice dice : this.getDices())
            if (position.equals(dice.getPosition()) && dice.isPlayer1() == isPlayer1)
                return true;
        return false;
    }

    // Take a Pur or Contra state, check if it is over.
    public boolean isOver () {
        return false; // FIXME
    }

    /**
     * Task 6: (Object version) (Written by Anubhav, edited by Haoting)
     * Determine whether a state represents a finished Pur game, and if so who the winner is.
     * ASSUMPTIONS: the state is of the Pur variant and valid.
     */
    public int isGameOverPur() {
        int p1OnOtherSide = 0; // The number of player1's dice at the opponent's end.
        int p2OnOtherSide = 0; // The number of player2's dice at the opponent's end.
        int p1score = 0; // Player1's score.
        int p2score = 0; // Player2's score.

        // Count the score of each player and the number of dices that reach the end.
        for (Dice dice : this.getDices()){
            if (dice.isPlayer1()) {
                if (dice.getPosition().getY() == 7) {
                    p1OnOtherSide++;
                    p1score+= dice.getTopNumber();
                }
            } else {
                if (dice.getPosition().getY() == 1) {
                    p2OnOtherSide++;
                    p2score+= dice.getTopNumber();
                }
            }
        }
        // Generate the result
        if (p1OnOtherSide == 7 || p2OnOtherSide == 7) {
            if (p1score > p2score)
                return 1;
            if (p1score < p2score)
                return 2;
            else
                return 3;}
        else
            return 0;
    }

    //======================================= SETTER & GETTER METHODS ================================================//

    // Gets all the dices in a state (By Group)
    public ArrayList<Dice> getDices() {return dices;}

    // (Written by Rajin, edited by Haoting)
    // Gets all dices belonging to the current player. (Player1 = white = true, vice versa.)
    public ArrayList<Dice> getCurrentPlayerDices() {
        ArrayList<Dice> dices = new ArrayList<>();
        for (Dice dice: this.dices) {
            if (dice.isPlayer1() == this.getPlayerTurn())
                dices.add(dice);}
        return dices;
    }

    // (By Group)
    public void setDices(ArrayList<Dice> dices) {this.dices = dices;}
    public void setPlayer1Turn(boolean isPlayer1Turn) {this.player1Turn = isPlayer1Turn;}
    public boolean getPlayerTurn() {return this.player1Turn;}
    public boolean isPur() {return this.pur;}
    public void changeTurn(){player1Turn = !this.player1Turn;}


    //============================================== LEGAL MOVES =====================================================//

    // Take a Pur or Contra state, return a list of legal moves of the state. (By Group)
    public ArrayList<Move> legalMoves () {
        return this.pur ? legalMovesPur() : legalMovesContra(); // FIXME: legal move for Contra needed.
    }

    // (By Rajin & Haoting)
    // Give a valid Pur state, generate a list of legal moves.
    public ArrayList<Move> legalMovesPur (){
        Set<Move> legalMoves = new HashSet<>(); // A list of all the legal moves.
        ArrayList<Dice> playerDices = this.getCurrentPlayerDices(); // All the dice belonging to the current player.
        boolean[] visited = new boolean[49];

        // Starting from each dice, search the board for legal moves.
        for (Dice dice : playerDices){
            Position start = dice.getPosition();
            Move soFar = new Move("");
            soFar.moveFurther(start); // Reset the starting move;

            for (Position destination : start.getAdjacentPositions()) {
                propagateMovePur(this, legalMoves, visited, start, destination, soFar); }

            for (Position destination : dice.getPosition().getJumpPositions()) {
                propagateMovePur(this, legalMoves, visited, start, destination, soFar); }
        }
        return new ArrayList<>(legalMoves);
    }

    /**
     * Recursive function for finding all the legal moves from a starting position. (By Rajin & Haoting)
     *
     * Reference: lecture code J14.Boggle.java
     *
     * What does the function work intuitively ?
     * Assume that you already have a legal move (a1a2) and some potential positions (a3, a5) to move further, you want
     * to know if the new moves (a1a2a3, a1a2a5) are still valid. If so, add them to the legal moves list.
     *
     * @param state The state of the game (invariance)
     * @param legalMoves The set of words found so far (variance)
     * @param visited An array indicating for each position whether the dice has visited it in this search (variance)
     * @param destination  The potential position to move further (variance)
     * @param start  The start position of the dice of the move (invariance)
     * @param soFar  The valid move so far in this particular search (variance)
     */
    // Give a valid Pur state and a position, return all the legal moves from that position
    public static void propagateMovePur
    (State state, Set<Move> legalMoves, boolean[] visited, Position start, Position destination, Move soFar){

        visited[destination.getPositionOrder()] = true; // Record that the position has been visited.
        Step further = new Step (soFar.getLastPosition(), destination); // A new potential step.
        Move candidate = new Move(soFar.toString()); // Clone the move so far.
        candidate.moveFurther(destination); // A new potential move.

        // Since we assume that the move so far is valid, we only need to check if the step from the last position of
        // the move so far to the destination is valid. If so, the candidate move must be valid too.
        if (further.isValidStepPur(state, start)) {
            legalMoves.add(candidate); // found a legal move!

            for (Position newDestination : destination.getJumpPositions()){
                if (!visited[newDestination.getPositionOrder()])
                    propagateMovePur(state, legalMoves, visited, start, newDestination, candidate);
            }
        } // If candidate is not valid, no point to look further.
        visited[destination.getPositionOrder()] = false; // Reset in order for the function to be used again.
    }

    public ArrayList<Move> legalMovesContra (){
        return new ArrayList<>();
    }


    //============================================ HEURISTIC METHODS==================================================//

    // Take a Pur or Contra state, return the heuristic score of the state.
    public int stateEvaluate () {
        return this.pur ? stateEvaluatePur () : stateEvaluateContra(); // FIXME: heuristic function for Contra needed.
    }


    // (Written by Rajin & edited by Haoting)
    // Cublino Pur Heuristic Function, the output score is the score of Player 1 - the score of Player 2
    // Therefore, Player1 will try maximising the score and Player2 will try minimising the score.
    public int stateEvaluatePur () {

        // A player score is calculated by the sum of the vertical distance from the starting point of all dices of the
        // player plus, the 100 * sum of the top values of all dices of the player that reaches the end.

        int p1Score = 0;
        int p2Score = 0;

        for (Dice dice: this.dices) {

            if(dice.isPlayer1()){
                Position startPosition = new Position(dice.getPosition().getX(), 1);
                p1Score += Position.manhattanDistance(dice.getPosition(), startPosition);
                if (dice.getPosition().getY() == 7) // The dice has reached the end.
                    p1Score += 100 * dice.getTopNumber();
            }
            else{
                Position startPosition = new Position(dice.getPosition().getX(), 7);
                p2Score += Position.manhattanDistance(dice.getPosition(), startPosition);
                if (dice.getPosition().getY() == 1) // The dice has reached the end.
                    p2Score += 100 * dice.getTopNumber();
            }
        }
        return p1Score - p2Score;
    }


    // Cublino Contra Heuristic Function
    public int stateEvaluateContra () {
        return 0; // FIXME
    }

    //===============================================DEAD CODE========================================================//



    // (Written Rajin, edited by Haoting)
    // Given a state, return a list of available jump steps
    public ArrayList<Step> generateAllJumpPur() {
        ArrayList<Step> output = new ArrayList<>();

        // Get a list of all the dices of this player
        for (Dice dice:getCurrentPlayerDices()) {
            Position start = dice.getPosition(); // The position of the dice
            for (Position end : start.getJumpPositions()) { // Get all the positions 2 units away from the dice.
                Step jump = new Step(start, end);
                // If the step from the position of the dice to the destination is valid, add it to the output list.
                if (jump.isValidStepPur(this, start))
                    output.add(jump);
            }
        }
        return output;
    }

    // (Written by Rajin, edited by Haoting)
    // Given a Dice, return a list of position that the dice can be moved in a step.
    public ArrayList<Step> getLegalStepPur(Dice dice) {
        Position start = dice.getPosition(); // The start position of the dice, named "start".
        ArrayList<Step> output = new ArrayList<>();

        // If the dice the user click does not belong to the current player, return nothing.
        if (this.player1Turn != dice.isPlayer1()) return output;

        for(Position end : start.getJumpPositions()){ // Get all the positions 2 units away from the dice, named "end".
            Step possibleStep = new Step (start,end);
            if(possibleStep.isValidStepPur(this,start)) // If the step from "start" to "end" is valid, add it.
                output.add(possibleStep);
        }

        for(Position end : start.getAdjacentPositions()){ // Get all the positions 1 unit away from the dice, named "end".
            Step possibleStep = new Step (start,end);
            if(possibleStep.isValidStepPur(this,start)) // If the step from "start" to "end" is valid, add it.
                output.add(possibleStep);
        }
        return output;
    }

    //=================================================STATIC METHODS=================================================//


    //======================================================TESTS=====================================================//


    public static void main(String[] args) {
        State state1 = new State("Pwb1bc1sf1if2ca3ub3gc3Cb5Mb6Hf6Oa7Fb7Sc7We7");
        State state2 = new State("pMb6");
        State state3 = new State("pWb1Wc1Wd1We1Wf1Wg1La2va7vb7vc7vd7ve7vf7vg7");
        Position pos = new Position("b6");
        System.out.println(state2.containDice(pos));
        System.out.println(state1.toString().equals("Pwb1bc1sf1if2ca3ub3gc3Cb5Mb6Hf6Oa7Fb7Sc7We7"));
        System.out.println(state1.getPlayerTurn());
        System.out.println(state2.getPlayerTurn());
        System.out.println(state3.stateEvaluate());
    }
}

