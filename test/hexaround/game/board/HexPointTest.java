package hexaround.game.board;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HexPointTest {

    @Test
    void getNeighboringPoints() {
        IPoint point = new HexPoint(1, 0);

        List<IPoint> neighboringPoints = new ArrayList<>(Arrays.asList(
                new HexPoint(1, 1),
                new HexPoint(2, 0),
                new HexPoint(2, -1),
                new HexPoint(1, -1),
                new HexPoint(0, 0),
                new HexPoint(0, 1)
                ));

        assertIterableEquals(neighboringPoints, point.getNeighboringPoints());
    }

    @Test
    void calculateDistanceToItself() {
        IPoint fromPoint = new HexPoint(0, 0);

        assertEquals(0, fromPoint.calculateDistanceTo(fromPoint));
    }

    @Test
    void calculateDistanceTo() {
        IPoint fromPoint = new HexPoint(0, 0);
        IPoint toPoint = new HexPoint(3, 4);

        assertEquals(7, fromPoint.calculateDistanceTo(toPoint));
    }

    @Test
    void clonePoint() {
        IPoint originalPoint = new HexPoint(2, 3);
        IPoint clonedPoint = originalPoint.clone();

        assertNotSame(originalPoint, clonedPoint);

        assertEquals(originalPoint.getX(), clonedPoint.getX());
        assertEquals(originalPoint.getY(), clonedPoint.getY());
    }
}
