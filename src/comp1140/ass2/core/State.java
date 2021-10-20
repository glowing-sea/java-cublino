package comp1140.ass2.core;

import comp1140.ass2.Cublino;
import java.util.*;

// (By Group)
public class State {
    private final boolean pur; // "true" means Pur."false" mean Contra.
    private final ArrayList<Dice> dices = new ArrayList<>();
    private boolean player1Turn; // "true" means player1's (White) turn."false" means player2 (Black) turn.

    //================================== CONSTRUCTOR & PRINTER & COPIER ==============================================//

    // Constructor (Created by Rajin, edited by Haoting)
    public State(String state) {

        if (!Cublino.isStateWellFormed(state)) throw new IllegalArgumentException();

        this.player1Turn = state.charAt(0) == 'P' || state.charAt(0) == 'C';
        this.pur = state.charAt(0) == 'P' || state.charAt(0) == 'p';

        // add all the dice pieces
        for (int i = 1; i < state.length(); i += 3) {
            this.dices.add(new Dice(state.substring(i, i + 3)));
        }
    }

    // Printer (By Haoting)
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

    // Copier (By Group)
    public State copy(){ return new State(this.toString()); }

    //============================================ CHECKER METHODS ===================================================//

    public boolean isPur() {return this.pur;} // Check if it is a Pur state.

    /**
     * Task 4: (Written by Anubhav, edited by Haoting)
     * Determine whether a Pur or Contra state is valid.
     * ASSUMPTIONS: the state is well-formed.
     */
    public boolean isStateValid(){

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

    /**
     * (By Haoting Chen)
     * Check if the state contains a dice at a given position.
     * Extra Condition enable: check if there is a dice at that position and the dice belongs to the current player.
     */
    //
    public boolean containDice (Position position, boolean extraCondition){
        if (extraCondition){
            for(Dice dice : this.getDices())
                if (position.equals(dice.getPosition()) && dice.isPlayer1() == this.player1Turn)
                    return true;
        }
        else{
            for(Dice dice : this.getDices())
                if (position.equals(dice.getPosition()))
                    return true;
        }
        return false;
    }

    /**
     * Task 6 & Task 14a (Written by Anubhav, edited by Haoting)
     * Determine whether a state represents a finished Pur or Contra game, and if so who the winner is.
     * ASSUMPTIONS: the state is of the Pur variant and valid.
     */
    public int isGameOver() {

        // Check if a Contra game is over.
        if (!this.pur){
            for (Dice dice : this.getDices()){
                if (dice.isPlayer1()) {
                    if (dice.getPosition().getY() == 7)
                        return 1; }
                else {
                    if (dice.getPosition().getY() == 1)
                        return 2; } }
            return 0;
        }

        // Check if a Pur game is over.
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


    //============================================= SETTER METHODS ===================================================//

    // (By Group)
    public void setTurn(boolean isPlayer1Turn) {this.player1Turn = isPlayer1Turn;} // Given a player, set the turn.
    public void changeTurn(){player1Turn = !this.player1Turn;} // Change the turn.


    /**
     * Task 9 & 14(b) (By Anubhav and Haoting)
     * Given a Pur or Contra game state and a move, update the state from the move.
     * If the move ends the game, the turn should be the player who would have played next had the game not ended.
     * If the move is invalid the, return an EXCEPTION (SLIGHTLY DIFFERENT FROM THE ORIGINAL TASKS).
     *
     * ASSUMPTION: the state is valid and the move is well-formed.
     */
    public void applyMove(Move m) {

        if(this.pur){
            if (!m.isValidMovePur(this)) throw new IllegalArgumentException(); }
        else{
            if (!m.isValidMoveContra(this, false)) throw new IllegalArgumentException(); }

        // Move a dice
        ArrayList<Position> pos = m.getPositions();
        Position start = m.getStart(); // Starting position of a move.
        Position end = m.getEnd(); // Ending position of a move.
        Step firstStep = new Step(start, (pos.get(1))); // The first step of a move.

        for (Dice dice : this.getDices()) { // Find the dice that need to be moved.
            if (dice.getPosition().equals(start)) {
                if(firstStep.isTip())
                    dice.tip(firstStep); // Tip the dice if needed.
                dice.jump(end); // Update the location of the dice.
            }
        }

        // Solve a battle (By Anubhav)
        if (!this.pur){
            ArrayList<Dice> removedDice = new ArrayList<>();
            for (Dice d : this.getDices()) {
                for (Dice enemy : d.getEnemies(this)) {
                    switch (battle(d, enemy, this)){
                        case 1 -> removedDice.add(enemy);
                        case 2 -> removedDice.add(d);
                    }
                }
            }
            for (Dice d : removedDice) {
                this.getDices().remove(d);
            }
        }

        this.changeTurn(); // Update the turn.
        Collections.sort(this.getDices()); // Sort the list of dices.
    }


    // (By Anubhav and Haoting)
    // Solve a battle. Return 1 if d1 win, 2 if d2 win, 0 if it is a draw.
    private static int battle(Dice d1, Dice d2, State state){
        ArrayList<Dice> d1Soldiers = d2.getEnemies(state); // the dices adjacent to d2 and having the same colours as d1.
        ArrayList<Dice> d2Soldiers = d1.getEnemies(state); // the dices adjacent to d1 and having the same colours as d2.

        int d1Power = 0;
        int d2Power = 0;

        for (Dice d1Soldier : d1Soldiers){ d1Power += d1Soldier.getTopNumber(); }
        for (Dice d2Soldier : d2Soldiers){ d2Power += d2Soldier.getTopNumber(); }

        if (d1Power > d2Power) return 1;
        if (d1Power < d2Power) return 2;

        return 0;
    }

    //======================================== GENERAL GETTER METHODS ================================================//

    // Getter Methods (By Group)
    public ArrayList<Dice> getDices() {return dices;} // Get a list of all dices.
    public boolean getPlayerTurn() {return this.player1Turn;} // Get the current turn of the state.

    // (Written by Rajin, edited by Haoting)
    // Gets a list of all dices belonging to the current player.
    public ArrayList<Dice> getCurrentPlayerDices() {
        ArrayList<Dice> dices = new ArrayList<>();
        for (Dice dice: this.dices) {
            if (dice.isPlayer1() == this.getPlayerTurn())
                dices.add(dice);}
        return dices;
    }

    //============================================== LEGAL MOVES =====================================================//

    // Get a set of legal moves of the Pur or Contra state. (By Group)
    public Set<Move> legalMoves () {
        return this.pur ? legalMovesPur() : legalMovesContra();
    }

    // (By Rajin & Haoting)
    // Give a valid Pur state, generate a list of legal moves.
    private Set<Move> legalMovesPur (){
        Set<Move> legalMoves = new HashSet<>(); // A set storing all the legal moves.
        ArrayList<Dice> playerDices = this.getCurrentPlayerDices(); // All the dice belonging to the current player.
        boolean[] visited = new boolean[49];

        // Starting from each dice, search the board for legal moves.
        for (Dice dice : playerDices){
            Position start = dice.getPosition();
            Move soFar = new Move("");
            soFar.moveFurther(start); // Move start from the current position of the dice.

            for (Position destination : start.getAdjacentPositions()) {
                propagateMovePur(this, legalMoves, visited, dice, destination, soFar);
                dice.jump(start); // reset the position of the dice after a move propagation.
            }

            for (Position destination : dice.getPosition().getJumpPositions()) {
                propagateMovePur(this, legalMoves, visited, dice, destination, soFar);
                dice.jump(start); // reset the position of the dice after a move propagation.
            }
        }
        return legalMoves;
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
     * @param dice  The dice that is being move (variance)
     * @param destination  The potential position to move further (variance)
     * @param soFar  The valid move so far in this particular search (variance)
     */
    // Give a valid Pur state and a position, return all the legal moves from that position
    private static void propagateMovePur
    (State state, Set<Move> legalMoves, boolean[] visited, Dice dice, Position destination, Move soFar){

        Position start = soFar.getEnd(); // The position where the dice start from.
        dice.jump(start); // Update the position of the dice.
        visited[start.getPositionOrder()] = true; // Record that the position has been visited.

        Step further = new Step (soFar.getEnd(), destination); // A new potential step.
        Move candidate = soFar.copy(); // Copy the move so far.
        candidate.moveFurther(destination); // A new potential move.

        // Since we assume that the move so far is valid, we only need to check if the step from the last position of
        // the move so far to the destination is valid. If so, the candidate move must be valid too.
        if (further.isValidStepPur(state)) {
            legalMoves.add(candidate); // found a legal move!

            for (Position newDestination : destination.getJumpPositions()){
                if (!visited[newDestination.getPositionOrder()])
                    propagateMovePur(state, legalMoves, visited, dice, newDestination, candidate);
            }
        } // If candidate is not valid, no point to look further.
        visited[start.getPositionOrder()] = false; // Reset in order for the function to be used again.
    }

    // (Written by Anubhav, reviewed by Haoting)
    private Set<Move> legalMovesContra (){
        Set<Move> legalMoves = new HashSet<>();
        for (Dice d : this.getDices()) {
            Position pos = d.getPosition();
            if (d.isPlayer1() == this.getPlayerTurn()) {
                for (Position p : pos.getAdjacentPositions()) {
                    Move potentialMove = new Move(d.getPosition().toString());
                    potentialMove.moveFurther(p);
                    if (potentialMove.isValidMoveContra(this, true))
                        legalMoves.add(potentialMove);
                }
            }
        }
        return legalMoves;
    }

    // (Written Rajin, edited by Haoting)
    // Get a list of all the legal steps in for a given dice in a Pur state. (used for UI only)
    public ArrayList<Step> legalJumpsPur() {
        ArrayList<Step> output = new ArrayList<>();

        // Get a list of all the dices of this player
        for (Dice dice:getCurrentPlayerDices()) {
            Position start = dice.getPosition(); // The position of the dice
            for (Position end : start.getJumpPositions()) { // Get all the positions 2 units away from the dice.
                Step jump = new Step(start, end);
                // If the step from the position of the dice to the destination is valid, add it to the output list.
                if (jump.isValidStepPur(this))
                    output.add(jump);
            }
        }
        return output;
    }

    // (Written by Rajin, edited by Haoting)
    // Return all the legal steps in in a state. (used for UI only)
    public ArrayList<Step> legalStepsPur(Dice dice) {
        Position start = dice.getPosition(); // The start position of the dice, named "start".
        ArrayList<Step> output = new ArrayList<>();

        // If the dice the user click does not belong to the current player, return nothing.
        if (this.player1Turn != dice.isPlayer1()) return output;

        for(Position end : start.getJumpPositions()){ // Get all the positions 2 units away from the dice, named "end".
            Step possibleStep = new Step (start,end);
            if(possibleStep.isValidStepPur(this)) // If the step from "start" to "end" is valid, add it.
                output.add(possibleStep);
        }

        for(Position end : start.getAdjacentPositions()){ // Get all the positions 1 unit away from the dice, named "end".
            Step possibleStep = new Step (start,end);
            if(possibleStep.isValidStepPur(this)) // If the step from "start" to "end" is valid, add it.
                output.add(possibleStep);
        }
        return output;
    }

    //============================================ HEURISTIC METHODS =================================================//

    /**
     * Evaluate a Pur or Contra state.
     *
     * @param currentPlayer The role of the AI.
     * If the AI is Player 1, the score of the state = Player 1 score - Player 2 score.
     * If the AI is Player 2, the score of the state = Player 2 score - Player 1 score.
     * The score = AI’s score – its opponent’s score.
     * If the score is positive, the AI is winning the game.
     * If the score is negative, the AI is losing the game.
     */

    public int stateEvaluate (boolean currentPlayer) {
        return this.pur ? stateEvaluatePur (currentPlayer) : stateEvaluateContra(currentPlayer);
    }

    /**
     * Cublino Pur Heuristic Function (Written by Rajin & edited by Haoting)
     *
     * The final score = the current player score - the opponent score.
     * A player's score = sum of the scores of all their dice score.
     * A dice score = the vertical distance from the dice to the starting point +
     * 100 * the top values if the dice reaches the end.
     *
     */
    private int stateEvaluatePur (boolean currentPlayer) {

        int p1Score = 0;
        int p2Score = 0;

        for (Dice dice: this.dices) {

            if(dice.isPlayer1()){ // The dice belongs to the current player
                Position startPosition = new Position(dice.getPosition().getX(), 1);
                p1Score += Position.manhattanDistance(dice.getPosition(), startPosition);
                if (dice.getPosition().getY() == 7) // The dice has reached the end.
                    p1Score += 100 * dice.getTopNumber();
            }
            else{ // The dice belongs to the opponent.
                Position startPosition = new Position(dice.getPosition().getX(), 7);
                p2Score += Position.manhattanDistance(dice.getPosition(), startPosition);
                if (dice.getPosition().getY() == 1) // The dice has reached the end.
                    p2Score += 100 * dice.getTopNumber();
            }
        }
        // If the current player is player 1, the result is p1Score (current player) - p2Score (opponent)
        // If the current player is player 2, the result is p2Score (current player) - p1Score (opponent)
        return currentPlayer ? p1Score - p2Score : p2Score - p1Score;
    }


    /**
     * Cublino Contra Heuristic Function (Written by Anubhav, reviewed by Haoting)
     *
     * The final score = the current player score - the opponent score.
     * A player's score = the number of dices + (the distance from the starting position to the farthest dice) ^ 2
     *
     */
    private int stateEvaluateContra (boolean currentPlayer) {

        int p1Score;
        int p2Score;

        // Makes sure won games are prioritised
        switch(this.isGameOver()){
            // Player 1 win, the current player is Player 1, then return 5000.
            case 1 -> {return currentPlayer ? 5000 : -5000;}
            // Player 2 win, the current player is Player 2, then return 5000.
            case 2 -> {return currentPlayer ? -5000 : 5000;}
        }

        int p1Farthest = 0; // The vertical distance between the starting point and the farthest dice of player 1.
        int p2Farthest = 0; // The vertical distance between the starting point and the farthest dice of player 2.
        int p1DiceNumber = 0;
        int p2DiceNumber = 0;

        for (Dice d : this.getDices()) {
            if (d.isPlayer1()) {
                p1DiceNumber++;
                if (d.getPosition().getY() - 1 > p1Farthest)
                    p1Farthest = d.getPosition().getY() - 1; // Update the maximum distance.
            }
            else{
                p2DiceNumber++;
                if (7 - d.getPosition().getY() > p2Farthest)
                    p2Farthest = 7 - d.getPosition().getY(); // Update the maximum distance.
            }
        }

        p1Score = p1DiceNumber + p1Farthest * p1Farthest;
        p2Score = p2DiceNumber + p2Farthest * p2Farthest;

        // If the current player is player 1, the result is p1Score (current player) - p2Score (opponent)
        // If the current player is player 2, the result is p2Score (current player) - p1Score (opponent)
        return currentPlayer ? p1Score - p2Score : p2Score - p1Score;
    }


    //================================================== TEST ========================================================//


    public static void main(String[] args) {
        State state1 = new State("Pwb1bc1sf1if2ca3ub3gc3Cb5Mb6Hf6Oa7Fb7Sc7We7");
        State state2 = new State("pMb6");
        State state3 = new State("pWb1Wc1Wd1We1Wf1Wg1La2va7vb7vc7vd7ve7vf7vg7");
        Position pos = new Position("b6");
        System.out.println(state2.containDice(pos, false));
        System.out.println(state1.toString().equals("Pwb1bc1sf1if2ca3ub3gc3Cb5Mb6Hf6Oa7Fb7Sc7We7"));
        System.out.println(state1.getPlayerTurn());
        System.out.println(state2.getPlayerTurn());
        System.out.println(state3.stateEvaluate(true));

        Dice d1 = new Dice("ic4");
        Dice d2 = new Dice("uc5");
        Dice d3 = new Dice("Ud5");
        State s4 = new State("Cic4uc5Ud5");
        System.out.println("the battle result is " + battle(d1,d2,s4));
        System.out.println("the battle result is " + battle(d2,d3,s4));
        System.out.println();

        // legalMovesContra test
        State st1 = new State("Cga1Se1Tb2Ld2Lg2Gc3Pa4Jb4vf5eb6ic6ed6ef6vd7");
        State st2 = new State("csc1ca3sf3Mb4jc4td4Qa5Cb5Oc5vf5qb6Lg6Ga7Gd7");
        State st3 = new State("CWa1Wb1Wc7Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
        System.out.println("The legal moves for st1 is " + st1.legalMoves());
        System.out.println("The legal moves for st2 is " + st1.legalMoves());
        System.out.println(st3.isGameOver());
        System.out.println(state1.isGameOver());
    }
}

