package extraTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import comp1140.ass2.core.Position;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Timeout(value = 1000, unit = MILLISECONDS)


public class PositionTest {

    Position l1 = new Position(3, 3);
    Position l2 = new Position(3, 3);
    Position l3 = new Position(0, 0);
    Position l4 = new Position("c2");
    Position l5 = new Position("b7");
    Position l6 = new Position("a1");
    Position l7 = new Position("a2");

    // TODO
}
