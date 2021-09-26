package comp1140.ass2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(value = 1000, unit = MILLISECONDS)
public class purGreedyHeuristicTest {

    private void testGreedyHeuristicInequality(String state1, String state2) {
        float state1Heuristic = Cublino.purGreedyHeuristic(state1);
        float state2Heuristic = Cublino.purGreedyHeuristic(state2);

        assertTrue(state1Heuristic > state2Heuristic, "purGreedyHeuristic: first state is " + state1Heuristic + " and the second is " + state2Heuristic);
    }

    private void testGreedyHeuristicEquality(String state1, String state2) {
        float state1Heuristic = Cublino.purGreedyHeuristic(state1);
        float state2Heuristic = Cublino.purGreedyHeuristic(state2);

        assertEquals(state1Heuristic, state2Heuristic, "purGreedyHeuristic: heuristic equality failed");
    }

    @Test
    public void testHeuristic() {
        // win state checks
        testGreedyHeuristicInequality("PWa1Wb1Wc1Wd1We1Wf1Wg1aa7ab7ac7ad7ae7af7ag7","PMa1Mb1Mc1Md1Me1Mf1Mg1aa7ab7ac7ad7ae7af7ag7");
        testGreedyHeuristicInequality("pAa1Ab1Ac1Ad1Ae1Af1Ag1va7vb7vc7vd7ve7vf7vg7", "PMa1Mb1Mc1Md1Me1Mf1Mg1aa7ab7ac7ad7ae7af7ag7");
        testGreedyHeuristicEquality("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7", "pWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");

        // edge case check: distance from end point
        testGreedyHeuristicInequality("pWa2Wb2Wc2Wd2We2Wf2Wg2va7vb7vc7vd7ve7vf7vg7","PWa2Wb2Wc2Wd2We2Wf2Wg2va7vb7vc7vd7ve7vf7vg7");
        testGreedyHeuristicInequality("pWa3Wb3Wc3Wd3We3Wf3Wg3va5vb5vc5vd5ve5vf5vg6","PWa3Wb3Wc3Wd3We3Wf3Wg3va5vb5vc5vd5ve5vf5vg6");
        testGreedyHeuristicEquality("pWa3Wb3Wc3Wd3We3Wf3Wg2va5vb5vc5vd5ve5vf5vg6", "PWa3Wb3Wc3Wd3We3Wf3Wg2va5vb5vc5vd5ve5vf5vg6");

        // edge case check: top facing number
        testGreedyHeuristicInequality("PWa1Wb7Wc7Wd7We7Wf7Wg7va6vb1vc1vd1ve1vf1vg1","pWa1Wb7Wc7Wd7We7Wf7Wg7va6vb1vc1vd1ve1vf1vg1");
        testGreedyHeuristicInequality("pWa1Wb2Wc7Wd7We7Wf7Wg7va7vb7vc1vd1ve1vf1vg1","PWa1Wb2Wc7Wd7We7Wf7Wg7va7vb7vc1vd1ve1vf1vg1");
        testGreedyHeuristicEquality("PWa1Wb4Wc7Wd7We7Wf7Wg7va7vb1vc4vd1ve1vf1vg1","pWa1Wb4Wc7Wd7We7Wf7Wg7va7vb1vc4vd1ve1vf1vg1");

        // normal case check
        testGreedyHeuristicInequality("PSc1Ge1Wg1Le2Fb3ff3Rg3Lc4qe4hg4if6va7ve7vg7", "pSc1Ge1Wg1Le2Fb3ff3Rg3Lc4qe4hg4if6va7ve7vg7");
        testGreedyHeuristicInequality("PWg1Kc2pd2Le2Ge3ff3Rg3Lc4qe4Ca5rf5ie6if6va7", "pWg1Kc2pd2Le2Ge3ff3Rg3Lc4qe4Ca5rf5ie6if6va7");
        testGreedyHeuristicInequality("Pac1Ce1Oa2pd2fd3ia4Lc4Td4Je4Og4sd5be5Jb6if6", "pac1Ce1Oa2pd2fd3ia4Lc4Td4Je4Og4sd5be5Jb6if6");
        testGreedyHeuristicInequality("Pgb1be1af1ag1Oa2pd2pf2fd3Pc4Ed4Cf5Dd6Ue6Je7", "pgb1be1af1ag1Oa2pd2pf2fd3Pc4Ed4Cf5Dd6Ue6Je7");
        testGreedyHeuristicInequality("Pgb1be1af1ag1Oa2pd2pf2fd3Pc4Ed4Cf5Dd6Ue6Je7", "pgb1be1af1ag1Oa2pd2pf2fd3Pc4Ed4Cf5Dd6Ue6Je7");
        testGreedyHeuristicEquality("pWa1Wb1Wc1Wf1Wg1Lc2Ld2id6if6va7vb7vc7ve7vg7", "PWa1Wb1Wc1Wf1Wg1Lc2Ld2id6if6va7vb7vc7ve7vg7");
    }
}