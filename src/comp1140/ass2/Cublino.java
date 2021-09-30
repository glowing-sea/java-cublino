package comp1140.ass2;

import comp1140.ass2.core.Dice;
import comp1140.ass2.core.Position;
import comp1140.ass2.core.State;
import comp1140.ass2.core.Step;

import java.util.ArrayList;

public class Cublino {

    /**
     * Task 3:
     * Determine whether the input state is well formed or not.
     * Note: you don't have to consider whether the state is valid for this task.
     * A state is well formed if it has the following format:
     * [variant][dice]*
     * where [variant] and [dice] are replaced by the corresponding substrings below. Note that [dice]* means zero or
     * more repetitions of the [dice] substring.
     *
     * 1. [variant] The variant substring is a single character which is either an upper or lower case 'p' or 'c' - The
     * letter encodes which variant the of the rules the game is using and the case represents the turn of the current
     * player.
     *
     * 2. [dice] The dice substring contains three characters. The first character can be either an upper or lower case
     * letter in the range 'a' to 'x'. The letter encodes the orientation of the dice and the case encodes which player
     * the dice belongs two. The next two characters encode the position of the dice in the format [column][row] where
     * the column is one character in the range 'a' to 'g' and the row is one character in the range '1' to '7'.
     *
     * See the "Encoding Game State" section in the README for more details.
     *
     * @param state a string representing a game state
     * @return true if the input state is well formed, otherwise false
     */
    public static Boolean isStateWellFormed(String state) {
        // The length - 1 must be a multiple of 3.
        if ((state.length() - 1) % 3 != 0)
            return false;
        // Either p, c, P, or C.
        if (Character.toLowerCase(state.charAt(0)) != 'p' && Character.toLowerCase(state.charAt(0)) != 'c')
            return false;
        for (int i = 1; i < state.length(); i = i + 3) {
            // In the range 'a' to 'x' or in the range 'A' to 'X'
            if (Character.toLowerCase(state.charAt(i)) < 'a' || Character.toLowerCase(state.charAt(i)) > 'x')
                return false;
            // In the range 'a' to 'g'
            if (state.charAt(i + 1) < 'a' || state.charAt(i + 1) > 'g')
                return false;
            // In the range '1' to '7'
            if (state.charAt(i + 2) < '1' || state.charAt(i + 2) > '7')
                return false;
        }
        return true;
    }

    /**
     * Task 4:
     * Determine whether the input state is valid.
     * A game state is valid if it satisfies the conditions below. Note that there are some shared conditions and
     * some conditions which are specific to each variant of the game. For this task you are expected to check states
     * from both variants.
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
     * @param state a string representing a game state
     * @return true if the input state is valid, otherwise false
     */
    public static Boolean isStateValid(String state) {

        if (!isStateWellFormed(state)) {
            return false;
        }
        if (state.length() == 1) {
            return false;
        }

        // checking if the game state string is valid
        ArrayList<Position> tempLoc = new ArrayList<>();
        char[] pieces = state.toCharArray();
        int p1onOtherSide = 0;
        int p2OnOtherSide = 0;
        int noOfDiceP1 = 0;
        int noOfDiceP2 = 0;
        int increment = 0; // easy way to know which part of string I'm dealing with

        for (int i=0; i<pieces.length;i++) {
            if (increment == 1) {
                StringBuilder s = new StringBuilder();
                s.append(pieces[i + 1]).append(pieces[i + 2]);
                Position loc = new Position(s.toString());
                for (Position j : tempLoc) {
                    if (j.checkEquals(loc)) {
                        return false;
                    }
                }
                // checked if the location is already on the board by storing the previous locations
                tempLoc.add(loc);
                if (Character.isUpperCase(pieces[i])) {
                    noOfDiceP1++;
                    if (loc.getY() == 7) {
                        p1onOtherSide++;

                    }
                } else {
                    noOfDiceP2++;
                    if (loc.getY() == 1) {
                        p2OnOtherSide++;

                    }
                }
            }
            if (increment != 3) {
                increment++;
            } else {
                increment = 1;
            }
        }

        // checking dice/piece counts for variants and dice/piece counts at each end of the board
        if (Character.toLowerCase(pieces[0]) == 'p') {
            if (p1onOtherSide == 7 && p2OnOtherSide == 7) {
                return false;
            } else {
                return (noOfDiceP1 ==7 && noOfDiceP2 == 7);
            }
        } else {
             if (p1onOtherSide>=1 && p2OnOtherSide>=1) {
                 return false;
             } else {
                 return noOfDiceP2 <= 7 & noOfDiceP2 <= 7;
             }

        }
    }

    /**
     * Task 6:
     * Determine whether a state represents a finished Pur game, and if so who the winner is.
     *
     * A game of Cublino Pur is finished once one player has reached the opponent's end of the board with all seven of
     * their dice. Each player then adds the numbers facing upwards on their dice which have reached the opponent's end
     * of the board. The player with the highest total wins.
     *
     * You may assume that all states input into this method will be of the Pur variant and that the state will be
     * valid.
     *
     * @param state a Pur game state
     * @return 1 if player one has won, 2 if player two has won, 3 if the result is a draw, otherwise 0.
     */
    public static int isGameOverPur(String state) {
        char[] pieces = state.toCharArray();
        int increment = 0;
        int p1OnOtherSide = 0;
        int p2OnOtherSide = 0;
        int p1score = 0;
        int p2score = 0;
        for (int i = 0; i < pieces.length; i++) {
            if (increment == 1) {
                StringBuilder s = new StringBuilder();
                s.append(pieces[i+1]).append(pieces[i+2]);
                Position loc = new Position(s.toString());
                if (Character.isUpperCase(pieces[i])) {
                    if (loc.getY() == 7) {
                        p1OnOtherSide++;
                        p1score+= getScorePur(pieces[i]);
                    }
                } else {
                    if (loc.getY() == 1) {
                        p2OnOtherSide++;
                        p2score+= getScorePur(pieces[i]);
                    }
                }
            }
            if (increment !=3) {
                increment++;
            }
            else {
                increment = 1;
            }

        }
        if (p1OnOtherSide == 7 || p2OnOtherSide == 7) {
            if (p1score > p2score) {
                return 1;
            }
            else if (p1score < p2score) {
                return 2;
            }
            else {
                return 3;
            }
        } else {
            return 0;
        }
    }

    /* Method to take a score and get the top face on the dice
    * NOTE: this method assumes the string has valid orientation characters
    */
    public static int getScorePur(char s) {

        if (Character.toLowerCase(s) >= 'a' && Character.toLowerCase(s) <= 'd') {
            return 1;
        }
        else if (Character.toLowerCase(s) >= 'e' && Character.toLowerCase(s) <= 'h') {
            return 2;
        }
        else if (Character.toLowerCase(s) >= 'i' && Character.toLowerCase(s) <= 'l') {
            return 3;
        }
        else if (Character.toLowerCase(s) >= 'm' && Character.toLowerCase(s) <= 'p') {
            return 4;
        }
        else if (Character.toLowerCase(s) >= 'q' && Character.toLowerCase(s) <= 't') {
            return 5;
        }
        else {
            return 6;
        }
    }

    /**
     * Task 7:
     * Determine whether a single step of a move is valid for a given Pur game.
     * A step is encoded as follows: [position][position]. The [position] substring is a two character string encoding a
     * position on the board. The first position represents the starting position of the dice making the step and the
     * second position represents the ending position of the dice making the step. You may assume that the step strings
     * input into this method are well formed according to the above specification.
     *
     * A step is valid if it satisfies the following conditions:
     * 1. It represents either a tilt or a jump of a dice.
     * 2. The ending position of the step is not occupied.
     * 3. The step moves towards the opponent's end of the board or horizontally (along its current row).
     * 3. If it is a jump step, there is a dice in the position which is jumped over.
     *
     * You may assume that all states input into this method will be of the Pur variant and that the state will be
     * valid.
     *
     * @param state a Pur game state
     * @param step a string representing a single step of a move
     * @return true if the step is valid for the given state, otherwise false
     */
    public static Boolean isValidStepPur(String state, String step) {
        // Check if the ending position is not occupied
        if (state.contains(step.substring(2,4)))
            return false;

        byte x1 = (byte) step.charAt(0);
        byte y1 = (byte) step.charAt(1);
        byte x2 = (byte) step.charAt(2);
        byte y2 = (byte) step.charAt(3);
        byte forward;
        String over; // The location where the dice jump over

        if (state.charAt(0) == 'P')
            forward = 1; // For the white dice, moving one step forward means column number plus 1.
        else
            forward = -1; // For the black dice, moving one step forward means column number minus 1.

        // Check if it is a valid forward move
        if (x1 == x2) {
            if (y1 + forward == y2) // Tilt forward
                return true;
            if (y1 + forward * 2 == y2) { // Jump forward
                over = "" + step.charAt(0) + ((char) (y1 + forward));
                return state.contains(over);
            }
        }

        // Check if it is a valid horizontal move
        if (y1 == y2) {
            if (x1 + 1 == x2) // Tilt to the right
                return true;
            if (x1 - 1 == x2) // Tilt to the left
                return true;
            if (x1 + 2 == x2) { // Jump to the right
                over = "" + ((char) (x1 + 1)) + step.charAt(1);
                return (state.contains(over));
            }
            if (x1 - 2 == x2) { // Jump to the left
                over = "" + ((char) (x1 - 1)) + step.charAt(1);
                return state.contains(over);
            }
        }

        return false;
    }


    /**
     * Task 8:
     * Determine whether a move (sequence of steps) is valid for a given game.
     * A move is encoded as follows [position]*. The [position] substring encodes a position on the board. This encoding
     * lists a sequence of positions that a dice will have as it makes a move. Note that [position]* means zero or more
     * repetitions of the [position] substring. You may assume that the move strings input into this method are well
     * formed according to the above specification.
     *
     * A move is valid if it satisfies the following conditions:
     * 1. The starting position of the move contains a dice belonging to the player who's turn it is.
     * 2. All steps in the move are valid.
     * 3. The move contains at least one step.
     * 4. Only the first step may be a tipping step.
     * 5. The starting and ending positions of the moved dice are different.
     *
     * You may assume that all states input into this method will be of the Pur variant and that the state will be
     * valid.
     *
     * @param state a Pur game state
     * @param move a string representing a move
     * @return true if the move is valid for the given state, otherwise false
     */
    public static Boolean isValidMovePur(String state, String move) {
        int length = move.length();
        boolean isPlayer1 = state.charAt(0) == 'P';
        boolean check1 = false;
        String checkedStep;

        // Check 3
        if (length < 2)
            return false;

        String startPosition = move.substring(0,2);
        String endPosition = move.substring(length - 2,length);

        // Check 5
        if (startPosition.equals(endPosition))
            return false;

        // Check 1
        for (int i = 1; i < 43; i = i + 3){
            if (isPlayer1 == Character.isUpperCase(state.charAt(i)))
                if (state.substring(i + 1, i + 3).equals(startPosition)){
                    check1 = true;
                    break; } }

        if (!check1)
            return false;

       // Check 2 & 4
        for (int i = 0; i < move.length() - 2; i = i + 2){
            checkedStep = move.substring(i,i + 4);
            if (!isValidStepPur(state, checkedStep))
                return false;
            if (i != 0 && Step.isTipPlus(checkedStep))
                return false;
        }
        return true;
    }

    /**
     * Task 9:
     * Given a Pur game state and a move to play, determine the state that results from that move being played.
     * If the move ends the game, the turn should be the player who would have played next had the game not ended. If
     * the move is invalid the game state should remain unchanged.
     *
     * You may assume that all states input into this method will be of the Pur variant and that the state will be
     * valid.
     *
     * @param state a Pur game state
     * @param move a move being played
     * @return the resulting state after the move has been applied
     */
    public static String applyMovePur(String state, String move) {
        if (move.equals("")) {
            return state;
        } else {
            String startPointString = "" + move.toCharArray()[0] + move.toCharArray()[1];
            String endPointString = "" + move.toCharArray()[move.length() -2] + move.toCharArray()[move.length() -1];
            String returnString = "";
        }
        return null;
         // FIXME Task 9 (P)
    }

    /**
     * Task 11:
     * Given a valid Pur game state, return a valid move.
     * This task imposes an additional constraint that moves returned must not revisit positions previously occupied as
     * part of the move (ie. a move may not contain a jumping move followed by another jumping move back to the previous
     * position).
     *
     * You may assume that all states input into this method will be of the Pur variant and that the state will be
     * valid.
     *
     * @param state a Pur game state
     * @return a valid move for the current game state.
     */
    public static String generateMovePur(String state) {
       ArrayList<Step> move = new ArrayList<>();
       State gameState = new State(state);
       boolean isPlayer1 = Character.isUpperCase(state.charAt(0));

        // GOAL: to be greedy at each step and generate a move based on that
        for (Dice dice: gameState.getPieces(isPlayer1)) {
            // get all the possible steps for the current piece

            /* TODO: apply the steps and generate game states which can be inputted into the heuristic and the best step gets picked;
                this process continues until no possible step can be generated
            */
        }

       return null;
    }


    // Generates all possible step from a given state for a piece
    public static ArrayList<Step> generateStepPur(String state, Position pieceLocation) {
        State stateState = new State(state);
        int forwardIncrement = stateState.getPlayerTurn() ? 1 : -1; // forward direction varies from player's type
        ArrayList<Step> steps = new ArrayList<>();

        ArrayList<Position> endPositions = new ArrayList<>();

        endPositions.add(new Position(pieceLocation.getX() + 1, pieceLocation.getY()));
        endPositions.add(new Position(pieceLocation.getX() - 1, pieceLocation.getY()));
        endPositions.add(new Position(pieceLocation.getX(), pieceLocation.getY() + forwardIncrement));

        for (Position tipLocation:endPositions) {
            if (stateState.getPieceAt(tipLocation.getX(), tipLocation.getY()) != null) { // piece exists at the tip location
                Position[] potentialJumps = {
                        new Position(tipLocation.getX() + 1, tipLocation.getY()),
                        new Position(tipLocation.getX() - 1, tipLocation.getY()),
                        new Position(tipLocation.getX(), tipLocation.getY() + forwardIncrement)
                };

                for (Position potentialJump:potentialJumps) {
                    if (!potentialJump.equals(pieceLocation)) {
                        endPositions.add(potentialJump);
                    }
                }
            }
        }

        // filter and get rid of invalid steps (off board and landing on a piece) & add to steps
        for (Position endPosition:endPositions) {
            if (endPosition.isOffBoard() || stateState.getPieceAt(endPosition.getX(), endPosition.getY()) != null || endPosition.equals(pieceLocation)) {
                endPositions.remove(endPosition);
            }
            steps.add(new Step(pieceLocation, endPosition));
        }

        return steps;
    }


    // Cublino Pur Heuristic Function
    public static float purGreedyHeuristic(String state) {
        State gameState = new State(state);
        boolean isPlayer1 = Character.isUpperCase(state.charAt(0));

        int totalDistanceFromEnd = 0; // sum all differences in distances for each piece of the player
        int totalTopFaceValue = 0; // sum all top face values of each of the player's piece

        ArrayList<Dice> playerDices = new ArrayList<>(gameState.getPieces(isPlayer1));

        for (Dice dice: playerDices) {
            // adds the manhattan distance between the current dice and the end position it should achieve
            Position endPosition = new Position(dice.getPosition().getX(), isPlayer1 ? 1 : 7);
            totalDistanceFromEnd += Position.manhattanDistance(dice.getPosition(), endPosition);
            // adds the top face value
            totalTopFaceValue += dice.getTopNumber();
        }

       return  (100/((float)totalDistanceFromEnd+1)) + ((float)totalTopFaceValue/42);
    }

    // FIXME Task 13 (HD): Implement a "smart" generateMove()

    /**
     * Task 14a:
     * Determine whether a state represents a finished Contra game, and if so who the winner is.
     *
     * A player wins a game of Cublino Contra by reaching the opponent's end of the board with one of their dice.
     *
     * You may assume that all states input into this method will be of the Contra variant and that the state will be
     * valid.
     *
     * @param state a Contra game state
     * @return 1 if player one has won, 2 if player two has won, otherwise 0.
     */
    public static int isGameOverContra(String state) {
        return -1; // FIXME Task 14a (HD)
    }

    /**
     * Task 14b:
     * Given a Contra game state and a move to play, determine the state that results from that move being played.
     * If the move ends the game, the turn should be the player who would have played next had the game not ended. If
     * the move is invalid the game state should remain unchanged. See the README for what constitutes a valid Contra
     * move and the rules for updating the game state.
     *
     * You may assume that all states input into this method will be of the Contra variant and that the state will be
     * valid.
     *
     * @param state a Contra game state
     * @param move a move being played
     * @return the resulting state after the move has been applied
     */
    public static String applyMoveContra(String state, String move) {
        return null; // FIXME Task 14b (HD)
    }

    /**
     * Task 14c:
     * Given a valid Contra game state, return a valid move.
     *
     * You may assume that all states input into this method will be of the Contra variant and that the state will be
     * valid.
     *
     * @param state the Pur game state
     * @return a move for the current game state.
     */
    public static String generateMoveContra(String state) {
        return null; // FIXME Task 14c (HD)
    }

}
