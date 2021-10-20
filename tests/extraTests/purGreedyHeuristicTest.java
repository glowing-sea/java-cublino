package extraTests;

import comp1140.ass2.Cublino;
import comp1140.ass2.core.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(value = 1000, unit = MILLISECONDS)
public class purGreedyHeuristicTest {

    /**
     * If the AI is Player 1, the score of the state = Player 1 score - Player 2 score.
     * If the AI is Player 2, the score of the state = Player 2 score - Player 1 score.
     * i.e. the AI always want to maximise its score.
     * If the score is positive, the AI is winning the game.
     * If the score is negative, the AI is losing the game.
     */

    // Test if the first state is better then the others.
    private void testGreedyHeuristicInequality(String state1, String state2, boolean aiIsPlayer1) {
        State st1 = new State(state1);
        State st2 = new State(state2);

        int s1Heuristic = st1.stateEvaluate(aiIsPlayer1);
        int s2Heuristic = st2.stateEvaluate(aiIsPlayer1);

        assertTrue(s1Heuristic > s2Heuristic, "Expect that " + st1 + " (" + s1Heuristic + ") " +
                "is better than " + st2 + " (" + s2Heuristic + ") ");
    }

    // Test if two states have the score score.
    private void testGreedyHeuristicEquality(String state1, String state2, boolean aiIsPlayer1) {
        State s1 = new State(state1);
        State s2 = new State(state2);

        int state1Heuristic = s1.stateEvaluate(aiIsPlayer1);
        int state2Heuristic = s2.stateEvaluate(aiIsPlayer1);

        assertEquals(state1Heuristic, state2Heuristic, "purGreedyHeuristic: heuristic equality failed");
    }

    // True: check if player 1 is better. False: check if player 2 is better.
    private void testScoreCompare(String state, boolean checkPlayerOneIsBetter) {
        State s = new State(state);

        int p1Heuristic = s.stateEvaluate(true);
        int p2Heuristic = s.stateEvaluate(false);

        if (checkPlayerOneIsBetter){
            assertTrue(p1Heuristic > p2Heuristic, "Expect that in the state " + s + " Player 1 (" +
                    p1Heuristic + ") players better than Player 2 (" + p2Heuristic + ").");
        }
        else{
            assertTrue(p1Heuristic < p2Heuristic, "Expect that in the state " + s + " Player 2 (" +
                    p2Heuristic + ") players better than Player 1 (" + p1Heuristic + ").");
        }
    }


    @Test
    public void testHeuristic() {
        // win state checks
        testGreedyHeuristicInequality("PWa7Wb7Wc7Wd7We7Wf1Wg7aa1ab1ac1ad1ae1af1ag1","PWa1Wb4Wc7Wd7We7Wf7Wg7va7vb1vc4vd1ve1vf1vg1", true);
        testGreedyHeuristicInequality("PMa7Mb7Mc7Md7Me7Mf7Mg7aa1ab1ac1ad1ae1af1ag1", "pAa7Ab7Ac7Ad7Ae7Af7Ag7va1vb1vc1vd1ve1vf1vg1", true);

        // edge case check: distance from end point
        testGreedyHeuristicInequality("pWa2Wb2Wc2Wd2We3Wf2Wg2va7vb7vc7vd7ve7vf7vg7","PWa2Wb2Wc2Wd2We2Wf2Wg2va7vb7vc7vd7ve7vf7vg7", true);
        testGreedyHeuristicInequality("pWa3Wb3Wc3Wd3We3Wf3Wg3va5vb5vc5vd5ve5vf5vg5","PWa3Wb3Wc3Wd3We3Wf3Wg3va5vb5vc5vd5ve5vf5vg6", false);
        testGreedyHeuristicEquality("pWa3Wb3Wc3Wd3We3Wf3Wg2va5vb5vc5vd5ve5vf5vg6", "PWa3Wb3Wc3Wd3We3Wf3Wg2va5vb5vc5vd5ve5vf5vg6", false);

        // edge case check: top facing number
        testGreedyHeuristicInequality("PWa1Wb7Wc7Wd7We7Wf7Wg7va6vb1vc1vd1ve1vf1vg1","pWa1Wb7Wc7Wd7We7Wf7Wg7va7vb1vc1vd1ve1vf1vg1", false);
        testGreedyHeuristicInequality("pWa1Wb2Wc7Wd7We7Wf7Wg7va7vb7vc1vd1ve1vf1vg1","PWa1Wb1Wc7Wd7We7Wf7Wg7va7vb7vc1vd1ve1vf1vg1", true);
        testGreedyHeuristicEquality("PWa1Wb4Wc7Wd7We7Wf7Wg7va7vb1vc4vd1ve1vf1vg1","pWa1Wa4Wc7Wd7We7Wf7Wg7va7vb1vc4vd1ve1vf1vg1", true);

        // normal case check
        testScoreCompare("PSc1Ge1Wg1Le2Fb3ff3Rg3Lc4qe4hg4if6va7ve7vg7", false);
        testScoreCompare("PWg1Kc2pd2Le2Ge3ff3Rg3Lc4qe4Ca5rf5ie6if6va7", false);
        testScoreCompare("Pac1Ce1Oa2pd2fd3ia4Lc4Td4Je4Og4sd5be5Jb6if6", false);
        testScoreCompare("pWa2Wb2Wc2Wd2We3Wf2Wg2va7vb7vc7vd7ve7vf7vg7", true);
        testScoreCompare("Pgb1be1af1ag1Oa2pd2pf2fd3Pc4Ed4Cf5Dd6Ue6Je7", false);
    }
}