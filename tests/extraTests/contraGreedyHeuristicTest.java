package extraTests;
import comp1140.ass2.core.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@Timeout(value = 1000, unit = MILLISECONDS)

public class contraGreedyHeuristicTest {
    private void testContraInequalityP1(String s1, String s2) {
        State st1 = new State(s1);
        State st2 = new State(s2);

        int s1heuristic = st1.stateEvaluate(true);
        int s2heuristic = st2.stateEvaluate(true);
        assertTrue(s1heuristic > s2heuristic, "Expect that " + st1 + " (" + s1heuristic + ") " +
                "is better than " + st2 + " (" + s2heuristic + ") ");
    }

    private void testContraEqualityP1(String s1, String s2) {
        State st1 = new State(s1);
        State st2 = new State(s2);

        int s1heuristic = st1.stateEvaluate(true);
        int s2heuristic = st2.stateEvaluate(true);
        assertEquals(s2heuristic, s1heuristic);
    }


    @Test
    public void testContraHeuristic() {

        // winning state should always win
        testContraInequalityP1("CWa7Wb1Wc1Wd1We1Wf1Wg1vb3vc6", "CWa1Wb1Wc1Wd1We1Wf1Wg1vc7vf7");
        testContraInequalityP1("CWa7Wb1Wc1Wd1We1Wf1Wg1vb3vc6", "CWa1Wb1Wc1Wd1We1Wf1Wg1vb3vc6");

        // identical states except s1 was a1 dice at a5, so since it is closer to the other end
        testContraInequalityP1("CWa5Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7", "CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");

        // checking whether it chooses the non-losing game
        testContraInequalityP1("CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7", "CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg1");

        // shows that heuristic prioritises non-loss game states with lower piece count over a lost game state
        testContraInequalityP1("CWa1Wb1va7vb7vc7vd7ve7vf7vg7", "CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg1");

        // testing whether identical game states but one with more pieces for p1 wins
        testContraInequalityP1("CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7", "CWa1Wb1Wc1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");

        // means choosing less pieces for p2 also works
        testContraInequalityP1("CWa1Wb1Wc1Wd1We1Wf1Wg1va7vc7vd7ve7vf7vg7", "CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");

        // heuristic equally prioritises when you have more dice or they have less dice given distances are same
        testContraEqualityP1("CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7", "CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");

        // Both are game over state and in both state, player 1 wins. Therefore the score should be both 5000.
        testContraEqualityP1("CCa1Db3Lc7Wd1We1Wf1Wg1vc2cf7", "CWa1Wb1Wc7Wd1We1Wf1Wg1va7vb7vc6vd7ve7vf7vg7");

        // since maximum distance from finish is higher for the first one, heuristic selects that one
        testContraInequalityP1("CCa4Ld6Gf3va7vb7vf7", "CCa4Ld5Gf3va7vb7vf7");
    }
}
