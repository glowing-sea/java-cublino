package comp1140.ass2.core;

import comp1140.ass2.Cublino;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

// (By Group)
public class State {
    private static final int BOARD_SIZE = 7;
    private boolean player1Turn; // "true" means player1's (White) turn."false" means player2 (Black) turn.
    private boolean pur; // "true" means Pur."false" mean Contra.
    private ArrayList<Dice> dices = new ArrayList<>();

    //================================================CONSTRUCTOR=====================================================//

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

//    public State(String state) {
//        if (Cublino.isStateWellFormed(state)) {
//            String diceEncodings = state.substring(1); // all the dice encodings are assumed to be valid
//
//            // add all the dice pieces
//            for (int i = 0; i < diceEncodings.length()-2; i+=3) {
//                this.dices.add(new Dice(diceEncodings.substring(i,i+3)));
//            }
//
//            this.player1Turn = state.charAt(0) == 'P' || state.charAt(0) == 'C';
//        }
//    }
//

    //=============================================NON-STATIC METHODS=================================================//

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

    // (By Rajin)
    // Gets the piece at an X and Y coordinate
    public Dice getPieceAt(int x, int y) {
        for (Dice dice:dices) {
            if (dice.getPosition().getX() == x && dice.getPosition().getY() == y) {
                return dice;
            }
        }
        return null;
    }

    // (By Rajin)
    // Gets all pieces that are either white or black based on the input boolean
    public ArrayList<Dice> getPieces(boolean isWhitePieces) {
        ArrayList<Dice> dices = new ArrayList<>();

        for (Dice dice: this.dices) {
            if (isWhitePieces) {
                if (Character.isUpperCase(dice.toString().charAt(0))) {
                    dices.add(dice);
                }
            } else {
                if (!Character.isUpperCase(dice.toString().charAt(0))) {
                    dices.add(dice);
                }
            }
        }

        return dices;
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


    // Getter and setter methods (By Group)
    public ArrayList<Dice> getDices() {return dices;}
    public void setDices(ArrayList<Dice> dices) {this.dices = dices;}
    public void setPur(boolean pur) {this.pur = pur;}
    public boolean getPlayerTurn() {return this.player1Turn;}
    public boolean isPur() {return this.pur;}

    // (By Haoting)
    public void changeTurn(){player1Turn = !this.player1Turn;}


    /**
     * Task 4: (Object version) (Written by Anubhav, edited by Haoting)
     * Determine whether the input state is valid.
     *
     * [Both Variants]
     * 1. The game state is well formed.
     * 2. No two dice occupy the same position on the board.
     *
     * [Pur]
     * 1. Each player has exactly seven dice.
     * 2. Both players do not have all seven of their dice on the opponent's end of the board (as the game would have
     * already finished before this)
     *
     * [Contra]
     * 1. Each player has no more than seven dice.
     * 2. No more than one player has a dice on the opponent's end of the board.
     *
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

    /**
     * Task 6: (Object version) (Written by Anubhav, edited by Haoting)
     * Determine whether a state represents a finished Pur game, and if so who the winner is.
     *
     * A game of Cublino Pur is finished once one player has reached the opponent's end of the board with all seven of
     * their dice. Each player then adds the numbers facing upwards on their dice which have reached the opponent's end
     * of the board. The player with the highest total wins.
     *
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

    // (By Rajin)
    // Given a dice, find available tip spots
    public ArrayList<Position> getTipPositions(Position dicePos) {
        ArrayList<Position> positions = new ArrayList<>();
        int forwardIncrement = getPlayerTurn() ? -1 : 1;
        Position leftPos = new Position(dicePos.getX() - 1, dicePos.getY());
        Position rightPos = new Position(dicePos.getX() + 1, dicePos.getY());
        Position forwardPos = new Position(dicePos.getX(), dicePos.getY() + forwardIncrement);

        if (leftPos.isOnBoard() && !containDice(leftPos)) {
            positions.add(leftPos);
        }

        if (rightPos.isOnBoard() && !containDice(rightPos)) {
            positions.add(rightPos);
        }

        if (forwardPos.isOnBoard() && !containDice(forwardPos)) {
            positions.add(forwardPos);
        }

        return positions;
    }

    // (By Rajin)
    // Given a dice, find available jump spots
    public ArrayList<Position> getJumpPositions(Position dicePos) {

        int forwardIncrement = getPlayerTurn() ? -1 : 1;
        Position leftPos = new Position(dicePos.getX() - 1, dicePos.getY());
        Position rightPos = new Position(dicePos.getX() + 1, dicePos.getY());
        Position forwardPos = new Position(dicePos.getX(), dicePos.getY() + forwardIncrement);

        ArrayList<Position> jumpEndPositions = new ArrayList<>();
        // jump only occurs when a dice exists in the adjacent spots
        // jump positions cannot end with the start position

        if (containDice(leftPos)) {
            jumpEndPositions.addAll(getTipPositions(leftPos).stream().filter(p -> !p.equals(dicePos)).collect(Collectors.toList()));
        }

        if (containDice(rightPos)) {
            jumpEndPositions.addAll(getTipPositions(rightPos).stream().filter(p -> !p.equals(dicePos)).collect(Collectors.toList()));
        }

        if (containDice(forwardPos)) {
            jumpEndPositions.addAll(getTipPositions(forwardPos).stream().filter(p -> !p.equals(dicePos)).collect(Collectors.toList()));
        }

        return jumpEndPositions;
    }

    //=================================================STATIC METHODS=================================================//

    //======================================================TESTS=====================================================//

    public static void main(String[] args) {
        State state1 = new State("Pwb1bc1sf1if2ca3ub3gc3Cb5Mb6Hf6Oa7Fb7Sc7We7");
        State state2 = new State("pMb6");
        Position pos = new Position("b6");
        System.out.println(state2.containDice(pos));
        System.out.println(state1.toString().equals("Pwb1bc1sf1if2ca3ub3gc3Cb5Mb6Hf6Oa7Fb7Sc7We7"));
        System.out.println(state1.getPlayerTurn());
        System.out.println(state2.getPlayerTurn());
    }
}
