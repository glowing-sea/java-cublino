package extraTests;

import comp1140.ass2.Cublino;
import comp1140.ass2.ExampleGames;
import comp1140.ass2.core.Move;
import comp1140.ass2.core.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//(By Haoting)
@Timeout(value = 1000, unit = MILLISECONDS)
class isValidMoveContraTest {

    State st1 = new State("Cga1Se1Tb2Ld2Lg2Gc3Pa4Jb4vf5eb6ic6ed6ef6vd7");
    State st2 = new State("csc1ca3sf3Mb4jc4td4Qa5Cb5Oc5vf5qb6Lg6Ga7Gd7");
    String[] m1 = {"b4b5", "b4c4","b2b3"}; // Valid Moves
    String[] m2 = {"c4c3","b6a6", "d4d3"}; // Valid Moves
    String[] m3 = {"c3c5", "d2d1", "e3d4", "b4a4"}; // Invalid Moves
    String[] m4 = {"", "b6b5", "c4a4", "d4d5", "d4d6", "d2d1", "c4b4"}; // Invalid Moves

    private Move getMove (String encode){
        return new Move(encode);
    }

    @Test
    public void validMoves() {
        for (String move : m1)
            assertTrue(getMove(move).isValidMoveContra(st1), "Move \"" + move + "\" is valid for state \"" + st1 + "\"");
        for (String move : m2)
            assertTrue(getMove(move).isValidMoveContra(st2), "Move \"" + move + "\" is valid for state \"" + st2 + "\"");
    }

    @Test
    public void invalidMoves() {
        for (String move : m3)
            assertFalse(getMove(move).isValidMoveContra(st1), "Move \"" + move + "\" is invalid for state \"" + st1 + "\"");
        for (String move : m4)
            assertFalse(getMove(move).isValidMoveContra(st2), "Move \"" + move + "\" is invalid for state \"" + st2 + "\"");
    }
}
