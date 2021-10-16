package comp1140.ass2.core;

import java.util.Collections;
import java.util.LinkedList;

public class GameTree {
    private final State parent;
    private final LinkedList<GameTree> children = new LinkedList<>();

    //================================================CONSTRUCTOR=====================================================//

    // (By Haoting)
    // Given a depth (>=1), generate a game tree from a game state.
    // Helper functions required: applyMove, isOver, legalMoves.
    public GameTree(State state, int depth) {
        // If the required depth is reached or the state is over, stop generating.
        if (depth == 1 || state.isOver())
            this.parent = state;
        else {
            this.parent = state;
            LinkedList<GameTree> children = new LinkedList<>();

            // If there there is no legal move, the turn is skipped.
            // The only child state will be same as the parent state except for the turn.
            if (state.legalMoves().isEmpty()) {
                state.changeTurn();
                this.children.add(new GameTree(state, depth - 1));
            }
            // If there are any legal moves, the child states are generated after every legal move of the parent state
            else {
                for (Move move : state.legalMoves()) {
                    this.children.add(new GameTree(move.applyMove(state), depth - 1));
                }
            }
        }
    }

    //=============================================NON-STATIC METHODS=================================================//

    /**
     * Given a game tree, return the score of the tree. (By Haoting)
     *
     * @param isMax whether the node of the tree (or subtree) is Max's.
     * @return the score of the input state
     */

    public int miniMax(boolean isMax) {
        // If the function reach a leaf, call the heuristic function to evaluate the leaf.
        if (children.isEmpty())
            return parent.stateEvaluate();
        else {
            LinkedList<Integer> childrenScores = new LinkedList<>();
            if (isMax){
                for (GameTree child : children){
                    childrenScores.add(child.miniMax(false));}
                return Collections.max(childrenScores);
            }
            else{
                for (GameTree child : children){
                    childrenScores.add(child.miniMax(true));}
                return Collections.min(childrenScores);
            }
        }
    }

    /**
     * Given a game tree, return the score of the tree. (By Haoting)
     *
     * @param isMax whether the node of the tree (or subtree) is Max's.
     * @param alpha initialised to be 9999.
     * @param beta initialised to be -9999.
     * @return the score of the input state
     */

    public int miniMaxAB(boolean isMax, int alpha, int beta) {
        // If the function reach a leaf, call the heuristic function to evaluate the leaf.
        if (children.isEmpty())
            return parent.stateEvaluate();
        else {
            int valueSoFar; // The maximum score so far.
            int childScore; // The score of a subtree.

            if (isMax){
                valueSoFar = -9999;
                for (GameTree child : children){
                    childScore = child.miniMaxAB(false, alpha, beta);
                    valueSoFar = Math.max(childScore, valueSoFar); // Update the score so far.
                    alpha = Math.max(alpha, valueSoFar); // Update the alpha value.
                    if (alpha >= beta)
                        break; // No point to continue.
                }
            }
            else{
                valueSoFar = 9999;
                for (GameTree child : children){
                    childScore = child.miniMaxAB(true, alpha, beta);
                    valueSoFar = Math.min(childScore, valueSoFar); // Update the score so far.
                    beta = Math.min(beta, valueSoFar); // Update the beta value.
                    if (beta <= alpha)
                        break; // No point to continue.
                }
            }
            return valueSoFar;
        }
    }
}
