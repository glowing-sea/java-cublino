package comp1140.ass2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;

import static comp1140.ass2.core.Piece.*;

@Timeout(value = 1000, unit = MILLISECONDS)

class getOrientationTest {

    @Test
    public void testOrientation() {
        int[] test1 = new int[] {1,2,4,6,3};
        assertEquals('a', getOrientation(test1, false));
        assertEquals('A', getOrientation(test1, true));


    }
}
