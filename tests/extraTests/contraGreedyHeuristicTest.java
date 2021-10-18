package extraTests;
import comp1140.ass2.Cublino;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@Timeout(value = 1000, unit = MILLISECONDS)

public class contraGreedyHeuristicTest {
    private void testContraInequalityP1(String s1, String s2) {
        float s1heuristic = Cublino.greedyContraHeuristic(s1, true);
        float s2heuristic = Cublino.greedyContraHeuristic(s2, true);
        assertTrue(s1heuristic > s2heuristic);
    }

    private void testContraEqualityP1(String s1, String s2) {
        float s1heuristic = Cublino.greedyContraHeuristic(s1, true);
        float s2heuristic = Cublino.greedyContraHeuristic(s2, true);
        assertTrue(s1heuristic == s2heuristic);
    }


    @Test
    public void testContraHeuristic() {
        testContraInequalityP1("PWa7Wb1Wc1Wd1We1Wf1Wg1vb3vc6", "PWa1Wb1Wc1Wd1We1Wf1Wg1vc7vf7");
        testContraInequalityP1("PWa7Wb1Wc1Wd1We1Wf1Wg1vb3vc6", "PWa1Wb1Wc1Wd1We1Wf1Wg1vb3vc6");
        // winning state should always win
        testContraInequalityP1("PWa5Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7", "PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
        // identical states except s1 was a1 dice at a5, so since it is closer to the other end
        testContraInequalityP1("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7", "PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg1");
        // checking whether it chooses the non-losing game
        testContraInequalityP1("PWa1Wb1va7vb7vc7vd7ve7vf7vg7", "PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg1");
        // shows that heuristic prioritises non-loss game states with lower piece count over a lost game state
        testContraInequalityP1("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7", "PWa1Wb1Wc1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
        // testing whether identical game states but one with more pieces for p1 wins
        testContraInequalityP1("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vc7vd7ve7vf7vg7", "PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
        // means choosing less pieces for p2 also works
        testContraEqualityP1("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7", "PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
        // heuristic equally prioritises when you have more dice or they have less dice given distances are same
        testContraInequalityP1("PCa1Db3Lc7Wd1We1Wf1Wg1vc2cf7", "PWa1Wb1Wc7Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
        // since first one is a more resounding victory, because p2 has less dice, heuristic chooses it
        testContraInequalityP1("PCa4Ld6Gf3va7vb7vf7", "PCa4Ld5Gf3va7vb7vf7");
        // since maximum distance from finish is higher for the first one, heuristic selects that one




    }
}
