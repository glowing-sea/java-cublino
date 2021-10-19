package comp1140.ass2;

import comp1140.ass2.core.*;

import java.util.*;

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
    public static Boolean isMoveWellFormed(String move) {
        if(move.length() % 2 != 0)
            return false;

        for(int i = 0; i < move.length(); i += 2){
            if (!isPositionWellFormed(move.substring(i, i + 2)))
                return false;}
        return true;
    }
    public static Boolean isStepWellFormed(String move) {
        return move.length() == 4 && isPositionWellFormed(move.substring(0,2)) &&
                isPositionWellFormed(move.substring(2,4));
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
    // (By Anubhav)
    // For marking only, please use the inner method isStateValid in State class.
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
    // For marking only, please use the inner method isGameOverPur in State class.
    public static int isGameOverPur(String state) {
        State st = new State(state);
        return st.isGameOver();
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
    // (By Haoting)
    // For marking only. Please use the inner method isValidStepPur in Step Class.
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
    // For marking only. Please use the inner method isValidMovePur in Move Class.
    public static Boolean isValidMovePur(String state, String move) {
        Move m = new Move(move);
        State st = new State(state);
        return m.isValidMovePur(st);
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
    // (By Anubhav and Haoting)
    // For marking only. Please use the method applyMovePur in Move Class.
    public static String applyMovePur(String state, String move) {
        if (!isValidMovePur(state, move)) return state;
        Move m = new Move(move);
        State st = new State(state);
        st.applyMove(m);
        return st.toString();
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
    // For marking only. Please use the method bestMovePur in Cublino Class.
    public static String generateMovePur(String state) {
        State gameState = new State(state);
        return bestMovePur(gameState, 1).toString();
    }

    /**
     * Give a state and a difficulty value n, find the best move.
     * If n = 1, implement greedy AI. If n = 2 implement Minimax AI.
     *
     * How does the function work intuitively?
     *
     * The function generate a list of legal moves. For each move, it applies the move and gets a new state resulting
     * from the move. It then evaluates the new state and save the move and the new state together in a pair called
     * MovePlus. Finally, the function choose the move which will lead to the new state with the highest score.
     */
    // (By Rajin & Haoting)
    public static Move bestMovePur(State state, int difficulty){
        if (difficulty != 1 && difficulty != 2) throw new IllegalArgumentException();
        boolean useMinimax = difficulty == 2;

        ArrayList<MovePlus> legalMoves = new ArrayList<>(); // A list of legal moves.

        for (Move move : state.legalMoves()){
            MovePlus st = new MovePlus(move, state, useMinimax);
            legalMoves.add(st);
        }
        return Collections.max(legalMoves).getMOVE(); // Find the move leading to the state with the highest score.
    }

    // (By Haoting)
    // A pair that store a move and the score of the new state result from the move.
    private static class MovePlus implements Comparable<MovePlus>{
        private final Move MOVE; // A move
        private final int SCORE; // The score of the new state result from the move.

        public MovePlus(Move move, State parent, boolean useMinimax) {
            this.MOVE = move;
            boolean currentPlayer = parent.getPlayerTurn(); // The current player who is going to make a move decision.
            State child = parent.copy(); // Make a child by copying the parent
            child.applyMove(move); // Apply the move to the child

            if (useMinimax){
                GameTree tree = new GameTree(child, 3); // Build a tree from the child.
                this.SCORE = tree.miniMaxAB(-9999, 9999, currentPlayer); } // Evaluate the tree.
            else
                this.SCORE = child.stateEvaluate(currentPlayer); // Evaluate the child state directly.
        }
        @Override
        public int compareTo(MovePlus other) {
            return this.SCORE - other.SCORE; // The best move is the one with the highest score.
        }
        public Move getMOVE() { return MOVE; }
    }


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
    // (By Anubhav)
    // For marking only, please use the inner method isGameOverPur in State class.
    public static int isGameOverContra(String state) {
        State st = new State(state);
        return st.isGameOver();
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
        State st = new State(state);
        Move m = new Move(move);
        st.applyMove(m);
        return st.toString();
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
        State st = new State(state);
        ArrayList<String> moves = new ArrayList<>();
        for (Dice d : st.getDices()) {
            Position pos = d.getPosition();
            if (d.isPlayer1() == st.getPlayerTurn()) {
                for (Position p : pos.getAdjacentPositions()) {
                    if (! st.containDice(p, false)) {
                        moves.add(pos + p.toString());

                    }
                }
            }
        }
        return moves.get(0); // FIXME Task 14c (HD)
    }


    public static float greedyContraHeuristic(String state, boolean isPlayer1) {
        // implements a greedy contra heuristic
        State s = new State(state);
        int gameOverFactor = 0; // makes sure won games are prioritised
        int yourDiceNumber = 0;
        int opponentDiceNumber = 0;
        int closestDistanceToFinish = 0;
        ArrayList<Integer> distancesFromEndPos = new ArrayList<>(); // because there are maximum 7 in the board for each player

        if (Cublino.isGameOverContra(state) == 1 && isPlayer1 || Cublino.isGameOverContra(state) == 2 && !isPlayer1) {
            gameOverFactor = 15; // this ensures the heuristic will choose a winning game state
        }
        else if (Cublino.isGameOverContra(state) == 1 && !isPlayer1 || Cublino.isGameOverContra(state) == 2 && isPlayer1) {
            gameOverFactor = -15; // worst possible result
        }
        for (Dice d : s.getDices()) {
            if (d.isPlayer1() == isPlayer1) {
                if (isPlayer1) {
                    distancesFromEndPos.add(7 - d.getPosition().getY());
                }
                else {
                    distancesFromEndPos.add(d.getPosition().getY() - 1);
                }
                yourDiceNumber++;

                }
            else {
                opponentDiceNumber++;
            }
            }

        closestDistanceToFinish = Collections.min(distancesFromEndPos);

        return ((float) (yourDiceNumber - opponentDiceNumber)/2) - closestDistanceToFinish + gameOverFactor;
        // because I want to maximise my dice, miminise their dice, and minimise the closest distance

    }


    public static void main(String[] args) {
        System.out.println(generateMovePur("PGf1Kc2pd2Le2Ge3ff3Rg3Lc4qe4Ca5ce5rf5if6va7"));


        State s1 = new State("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
        State s2 = new State("Psc1ma2if2ca3gc3we3Qc4td4Qa5Cb5Oc5Hf6Wb7We7");
        State s3 = new State("PWa1Wb1Wc1Wd4We1Wf1Wg1va7vb7vc7vd7ve4vf7vg7");
        Move m = new Move("f6e6");

        System.out.println(s3.legalMoves());

        System.out.println(s2.legalMoves());
        System.out.println(bestMovePur(s2,1));
        System.out.println(bestMovePur(s2,2));
        System.out.println();
        System.out.println(s3.legalMoves());

    }
}
