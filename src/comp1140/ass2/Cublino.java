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
    // (By Haoting)
    public static Boolean isStateWellFormed(String state) {
        // The length - 1 must be a multiple of 3.
        if ((state.length() - 1) % 3 != 0)
            return false;
        // Either p, c, P, or C.
        if (Character.toLowerCase(state.charAt(0)) != 'p' && Character.toLowerCase(state.charAt(0)) != 'c')
            return false;

        for (int i = 1; i < state.length(); i = i + 3) {
            if (!isDiceWellFormed(state.substring(i, i + 3)))
                return false;
        }
        return true;
    }

    // Assume the input length of the string is 2 (By Haoting)
    public static Boolean isPositionWellFormed(String s) {
        return (s.charAt(0) >= 'a' && s.charAt(0) <= 'g') &&
                (s.charAt(1) >= '1' && s.charAt(1) <= '7');
    }
    // Assume the input length of the string is 3 (By Haoting)
    public static Boolean isDiceWellFormed(String dice) {
        return isPositionWellFormed(dice.substring(1)) &&
                ((Character.toLowerCase(dice.charAt(0)) >= 'a' && Character.toLowerCase(dice.charAt(0)) <= 'x'));
    }

    /**
     * Task 4 (Object Version) (Written by Haoting Chen):
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
    // (By Anubhav)
    public static Boolean isStateValid(String state){
        if (!isStateWellFormed(state))
            return false;
        State st = new State(state);
        return st.isStateValid();
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
    // (By Anubhav)
    public static int isGameOverPur(String state) {
        State st = new State(state);
        return st.isGameOverPur();
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
    // (By Haoting) (Only for assessment) (Recommend to use the inner method in Step Class)
    public static Boolean isValidStepPur(String state, String step) {
        Step step1 = new Step(step);
        State state1 = new State(state);
        return step1.isValidStepPur(state1);
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
    // (By Haoting)
    public static Boolean isValidMovePur(String state, String move) {

        // Condition 3
        int length = move.length() / 2; // length of the move1 array
        if (length < 1) return false;

        // Convert the move string into an array of positions.
        Position[] move1 = new Position[length];
        State state1 = new State(state);
        for(int i = 0; i < length; i++)
            move1[i] = new Position(move.substring(i * 2, i * 2 + 2));

        // Condition 5
        Position start = move1[0];
        Position end = move1[length - 1];
        if (start.equals(end))
            return false;

        // Condition 1
        boolean result = false;
        result = state1.containPlayerDice(start,state1.getPlayerTurn());
        if (!result)
            return false;

        // Condition 2 & 4
        Step checkedStep = new Step("a1a1");
        for (int i = 0; i < length - 1; i++){
            checkedStep.setStep(move1[i], move1[i + 1]);
            if (!checkedStep.isValidStepPur(state1))
                return false;
            if (i != 0 && checkedStep.isTip())
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
    // (By Anubhav)
    public static String applyMovePur(String state, String move) {
        if (move.equals("")) {
            return state;
        } else {
            String startPointString = "" + move.toCharArray()[0] + move.toCharArray()[1];
            String endPointString = "" + move.toCharArray()[move.length() -2] + move.toCharArray()[move.length() -1];
            String returnString = "";
            int x = (int) (0.5*(state.length()));
            String p1String = state.substring(1,(int)(0.5*state.length()) + 1);
            String p2String = state.substring((int) ((0.5*state.length()) + 1));
            char[] stateChars = state.toCharArray();
            int increment = 0;
            for (int i = 0; i<stateChars.length;i++) {
                if (increment == 1) {
                    String s = "" + stateChars[i+1] + stateChars[i+2];
                    if (s.equals(startPointString)) {
                        Dice d = new Dice("" + stateChars[i] + stateChars[i+1] + stateChars[i+2]);
                        char newOrientation = d.getOrientation(d.changeFaces(d.getFaces(), new Step(startPointString + move.toCharArray()[2] + move.toCharArray()[3])), Character.isUpperCase(stateChars[i]));
                        String newString = "" + newOrientation + endPointString;
                        if (Character.isUpperCase(stateChars[i])) {
                            return Character.toLowerCase(stateChars[0]) + p1String.replaceAll("" + stateChars[i] + stateChars[i+1] + stateChars[i+2], "") + newString + p2String;
                        } else {
                            return Character.toUpperCase(stateChars[0]) + p1String + newString + p2String.replaceAll("" + stateChars[i] + stateChars[i+1] + stateChars[i+2], "");
                        }
                    }
                }
                if (increment !=3) {
                    increment++;
                } else {
                    increment = 1;
                }
            }
        }
        // have to sort the string later
        return state;
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
    // (By Rajin)
    public static String generateMovePur(String state) {
        // 1. Generate valid steps (first may or may not be a tip, rest are jumps)
        return null;
    }


    public static ArrayList<Step> generateStepPur(String state) {
        State gameState = new State(state);
        ArrayList<Step> possibleSteps = new ArrayList<>();

        // get each dice for this player
        for (Dice dice:gameState.getPieces(gameState.getPlayerTurn())) {
            // 1. generate possible tips
            for (Position tipPos:gameState.getTipPositions(dice.getPosition())) {
                possibleSteps.add(new Step(dice.getPosition(), tipPos));
            }
            // 2. generate possible jumps
            for (Position jumpPos: gameState.getJumpPositions(dice.getPosition())) {
                possibleSteps.add(new Step(dice.getPosition(), jumpPos));
            }
        }
        return possibleSteps;
    }


    // (By Rajin)
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
        char[] stateChars = state.toCharArray();
        int increment = 0;
        for (int i=0;i<stateChars.length;i++) {
            if (increment == 1) {
                Position p = new Position("" + stateChars[i+1] + stateChars[i+2]);
                if (Character.isUpperCase(stateChars[i])) {
                    if (p.getY() == 7) {
                        return 1;
                    }
                }
                else {
                    if (p.getY() == 1) {
                        return 2;
                    }
                }
            }
            if (increment != 3) {
                increment++;
            }
            else {
                increment = 1;
            }
        }
        return 0; // FIXME Task 14a (HD)
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

    public static void main(String[] args) {
        System.out.println(generateStepPur("Pca1gc1fd1ae1af1vg1hg2Ga3Cd5Pe6Tf6Je7Ef7Qg7"));
    }

}
