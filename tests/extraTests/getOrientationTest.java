package extraTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;


@Timeout(value = 1000, unit = MILLISECONDS)
class getOrientationTest {
    @Test
    public void testOrientation() {
        int[] test1 = new int[] {1,2,4,6,3};
        assertEquals('A', getOrientation(test1, true));
        assertEquals('a', getOrientation(test1, false));
        int[] test2 = new int[] {1,3,2,4,6};
        assertEquals('b', getOrientation(test2, false));
        assertEquals('B', getOrientation(test2, true));
        int[] test3 = new int[] {1,4,6,3,2};
        assertEquals('c', getOrientation(test3, false));
        assertEquals('C', getOrientation(test3, true));
        int[] test4 = new int[] {1,5,3,2,4};
        assertEquals('d', getOrientation(test4,false));
        assertEquals('D', getOrientation(test4,true));
        int[] test5 = new int[] {2,1,3,5,4};
        assertEquals('e', getOrientation(test5, false));
        assertEquals('E', getOrientation(test5, true));
        int[] test6 = new int[] {2,3,5,4,1};
        assertEquals('f', getOrientation(test6, false));
        assertEquals('F', getOrientation(test6, true));
        int[] test7 = new int[] {2,4,1,3,5};
        assertEquals('g', getOrientation(test7, false));
        assertEquals('G', getOrientation(test7, true));
        int[] test8 = new int[] {2,6,4,1,3};
        assertEquals('h', getOrientation(test8, false));
        assertEquals('H', getOrientation(test8, true));
        assertEquals('i', getOrientation(new int[] {3,1,6,5,2}, false));
        assertEquals('I', getOrientation(new int[] {3,1,6,5,2}, true));
        assertEquals('j', getOrientation(new int[] {3,2,1,6,5}, false));
        assertEquals('J', getOrientation(new int[] {3,2,1,6,5}, true));
        assertEquals('k', getOrientation(new int[] {3,5,2,1,6}, false));
        assertEquals('K', getOrientation(new int[] {3,5,2,1,6}, true));
        assertEquals('l', getOrientation(new int[] {3,6,5,2,1}, false));
        assertEquals('L', getOrientation(new int[] {3,6,5,2,1}, true));
        assertEquals('m', getOrientation(new int[] {4,1,2,5,6}, false));
        assertEquals('M', getOrientation(new int[] {4,1,2,5,6}, true));
        assertEquals('n', getOrientation(new int[] {4,2,5,6,1}, false));
        assertEquals('N', getOrientation(new int[] {4,2,5,6,1}, true));
        assertEquals('o', getOrientation(new int[] {4,5,6,1,2}, false));
        assertEquals('O', getOrientation(new int[] {4,5,6,1,2}, true));
        assertEquals('p', getOrientation(new int[] {4,6,1,2,5}, false));
        assertEquals('P', getOrientation(new int[] {4,6,1,2,5}, true));
        assertEquals('q', getOrientation(new int[] {5,1,3,6,4}, false));
        assertEquals('Q', getOrientation(new int[] {5,1,3,6,4}, true));
        assertEquals('r', getOrientation(new int[] {5,3,6,4,2}, false));
        assertEquals('R', getOrientation(new int[] {5,3,6,4,2}, true));
        assertEquals('s', getOrientation(new int[] {5,4,2,3,6}, false));
        assertEquals('S', getOrientation(new int[] {5,4,2,3,6}, true));
        assertEquals('t', getOrientation(new int[] {5,6,4,2,3}, false));
        assertEquals('T', getOrientation(new int[] {5,6,4,2,3}, true));
        assertEquals('u', getOrientation(new int[] {6,2,4,5,3}, false));
        assertEquals('U', getOrientation(new int[] {6,2,4,5,3}, true));
        assertEquals('v', getOrientation(new int[] {6,3,1,4,5}, false));
        assertEquals('V', getOrientation(new int[] {6,3,1,4,5}, true));
        assertEquals('w', getOrientation(new int[] {6,4,5,3,1}, false));
        assertEquals('W', getOrientation(new int[] {6,4,5,3,1}, true));
        assertEquals('x', getOrientation(new int[] {6,5,3,1,4}, false));
        assertEquals('X', getOrientation(new int[] {6,5,3,1,4}, true));

    }

    // May no long needed
    // (By Anubhav)
    public static char getOrientation(int[] sides, boolean isPlayer1) {
        char x = 0;
        if (sides[0] == 1 && sides[1] == 2) {
            x = 'a';
        }
        else if (sides[0] == 1 && sides[1] == 3) {
            x = 'b';
        }
        else if (sides[0] == 1 && sides[1] == 4) {
            x = 'c';
        }
        else if (sides[0] == 1 && sides[1] == 5) {
            x = 'd';
        }
        else if (sides[0] == 2 && sides[1] == 1) {
            x = 'e';
        }
        else if (sides[0] == 2 && sides[1] == 3) {
            x = 'f';
        }
        else if (sides[0] == 2 && sides[1] == 4) {
            x = 'g';
        }
        else if (sides[0] == 2 && sides[1] == 6) {
            x = 'h';
        }
        else if (sides[0] == 3 && sides[1] == 1) {
            x = 'i';
        }
        else if (sides[0] == 3 && sides[1] == 2) {
            x = 'j';
        }
        else if (sides[0] == 3 && sides[1] == 5) {
            x = 'k';
        }
        else if (sides[0] == 3 && sides[1] == 6) {
            x = 'l';
        }
        else if (sides[0] == 4 && sides[1] == 1) {
            x = 'm';
        }
        else if (sides[0] == 4 && sides[1] == 2) {
            x = 'n';
        }
        else if (sides[0] == 4 && sides[1] == 5) {
            x = 'o';
        }
        else if (sides[0] == 4 && sides[1] == 6) {
            x = 'p';
        }
        else if (sides[0] == 5 && sides[1] == 1) {
            x = 'q';
        }
        else if (sides[0] == 5 && sides[1] == 3) {
            x = 'r';
        }
        else if (sides[0] == 5 && sides[1] == 4) {
            x = 's';
        }
        else if (sides[0] == 5 && sides[1] == 6) {
            x = 't';
        }
        else if (sides[0] == 6 && sides[1] == 2) {
            x = 'u';
        }
        else if (sides[0] == 6 && sides[1] == 3) {
            x = 'v';
        }
        else if (sides[0] == 6 && sides[1] == 4) {
            x = 'w';
        }
        else if (sides[0] == 6 && sides[1] == 5) {
            x = 'x';
        }
        if (isPlayer1) {
            return Character.toUpperCase(x);
        }
        else {
            return x;
        }
    }
}
