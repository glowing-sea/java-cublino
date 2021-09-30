package extraTests;

import comp1140.ass2.Cublino;
import comp1140.ass2.ExampleGames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(value = 1000, unit = MILLISECONDS)
class isValidMovePurTestD2D {

    @Test
    // Given a valid move for a valid state, check if the function return true.
    public void testValidStepMove() {
        for (int i = 0; i < ExampleGames.FULL_PUR_GAME.length - 1; i += 2) {
            String state = ExampleGames.FULL_PUR_GAME[i][0];
            String move = ExampleGames.FULL_PUR_GAME[i][1];
            Assertions.assertTrue(Cublino.isValidMovePur(state, move), "Move \"" + move + "\" for state \"" + state + "\" is valid");
        }
    }

    // Test invalid move

    // From FULL_PUR_GAME_WITH_MOVES_STATES.
    String testStateA = "Pga1Se1Tb2Ld2Lg2Gc3Pa4Jb4vf5eb6ic6ed6ef6vd7";
    String testStateB = "psc1ca3sf3Mb4jc4td4Qa5Cb5Oc5vf5qb6Lg6Ga7Gd7";

    @Test
    // Moves that fail condition 1
    public void testCondition1() {
        String[] movesA = {"b1b3b5b7", "f1f2", "a3a5"};
        String[] movesB = {"f6f4f2", "f7f5", "c7c6"};
        for (String move : movesA)
            assertFalse(Cublino.isValidMovePur(testStateA, move), "Move \"" + move + "\" is invalid for state \"" + testStateA + "\"");
        for (String move : movesB)
            assertFalse(Cublino.isValidMovePur(testStateA, move), "Move \"" + move + "\" is invalid for state \"" + testStateB + "\"");
    }

    @Test
    // Moves that fail condition 2
    public void testCondition2() {
        String[] movesA = {"c3c5c7", "d2d1", "a1a2"};
        String[] movesB = {"f5f3f1", "a7a6", "b6b3"};
        for (String move : movesA)
            assertFalse(Cublino.isValidMovePur(testStateA, move), "Move \"" + move + "\" is invalid for state \"" + testStateA + "\"");
        for (String move : movesB)
            assertFalse(Cublino.isValidMovePur(testStateA, move), "Move \"" + move + "\" is invalid for state \"" + testStateB + "\"");
    }

    @Test
    // Moves that fail condition 3
    public void testCondition3() {
        String move = "";
        assertFalse(Cublino.isValidMovePur(testStateA, move), "Move \"" + move + "\" is invalid for state \"" + testStateA + "\"");
        assertFalse(Cublino.isValidMovePur(testStateA, move), "Move \"" + move + "\" is invalid for state \"" + testStateB + "\"");
    }

    @Test
    // Moves that fail condition 4
    public void testCondition4() {
        String[] movesA = {"e1e2e3e4e5", "g2f2e2", "c3c4c5"};
        String[] movesB = {"d4d3d2c2", "f5f4e4", "a3a2a1"};
        for (String move : movesA)
            assertFalse(Cublino.isValidMovePur(testStateA, move), "Move \"" + move + "\" is invalid for state \"" + testStateA + "\"");
        for (String move : movesB)
            assertFalse(Cublino.isValidMovePur(testStateA, move), "Move \"" + move + "\" is invalid for state \"" + testStateB + "\"");
    }

    @Test
    // Moves that fail condition 5
    public void testCondition5() {
        String[] movesA = {"b4b5b7b5b4", "b2b3b2", "g2f2g2"};
        String[] movesB = {"f5f4f2f4f5", "d4d3d4", "a3b3a3"};
        for (String move : movesA)
            assertFalse(Cublino.isValidMovePur(testStateA, move), "Move \"" + move + "\" is invalid for state \"" + testStateA + "\"");
        for (String move : movesB)
            assertFalse(Cublino.isValidMovePur(testStateA, move), "Move \"" + move + "\" is invalid for state \"" + testStateB + "\"");
    }
}