package extraTests;
import comp1140.ass2.core.Move;
import comp1140.ass2.core.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Timeout(value = 1000, unit = MILLISECONDS)
public class legalMovesContraTest {

    @Test
    // (By Haoting)
    public void testGeneratePurMove1() {
        for (int i = 0; i < states.length; i++) {
            State testState = new State(states[i]);
            Set<Move> generatedMoves = testState.legalMoves();
            Set<String> expectedMoves = new HashSet<>(Arrays.asList(moves[i]));

            // The number of legal moves generated must be the same as the example.
            assertEquals(expectedMoves.size(), generatedMoves.size());
            for (Move move : generatedMoves) {
                // All the legal moves in the example must be contained in the list of legal moves generated.
                assertTrue(expectedMoves.contains(move.toString()), "Move \"" + move + "\" for state \"" + testState + "\" has not found");
                assertTrue(move.isValidMoveContra(testState, false));
            }
        }
    }

    public static final String[] states = {
            "Cga1Se1Tb2Ld2Lg2Gc3Pa4Jb4vf5eb6ic6ed6ef6vd7"
            , "csc1ca3sf3Mb4jc4td4Qa5Cb5Oc5vf5qb6Lg6Ga7Gd7"};

    public static final String[][] moves = {
            new String[]{"a4a5","b4b5","b4c4","c3c4","c3b3","c3d3","b2b3","b2a2","b2c2","d2d3","d2c2","d2e2","e1e2","e1d1","e1f1","g2g3","g2f2"}
            , new String[]{"b6a6","b6c6","a3a2","a3b3","c4c3","c1b1","c1d1","d4d3","d4e4","f3f2","f3e3","f3g3","f5f4","f5e5","f5g5"}
    };
}