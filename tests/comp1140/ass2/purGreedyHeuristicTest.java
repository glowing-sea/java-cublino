package comp1140.ass2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static comp1140.ass2.Cublino.isStateWellFormed;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(value = 1000, unit = MILLISECONDS)
class purGreedyHeuristicTest {

    private void testGreedyHeuristic(String state1, String state2, String msg) {
        float state1Heuristic = Cublino.purGreedyHeuristic(state1);
        float state2Heuristic = Cublino.purGreedyHeuristic(state2);

        assertTrue(state1Heuristic > state2Heuristic, msg);
    }


    @Test
    public void testHeuristic(String state) {
        // win state checks
        testGreedyHeuristic("PWa1Wb1Wc1Wd1We1Wf1Wg1aa7ab7ac7ad7ae7af7ag7","PMa1Mb1Mc1Md1Me1Mf1Mg1aa7ab7ac7ad7ae7af7ag7","");
    }
}