package hexaround.game.board;

import hexaround.game.creature.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    IBoard board;
    UnitVectors unitVectors = new UnitVectors();
    CreatureStack creaturesAtPoint;
    ICreature creature1;
    ICreature creature2;
    IPoint origin;
    IPoint emptyPoint = new HexPoint(5, 5);

    @BeforeEach
    void setUpBoard() {
        HashMap<IPoint, CreatureStack> initialBoard = new HashMap<>();
        creature1 = new Creature(CreatureName.CRAB, 1, Collections.singleton(CreatureProperty.INTRUDING));
        creature2 = new Creature(CreatureName.GRASSHOPPER, 3, Collections.singleton(CreatureProperty.INTRUDING));
        creaturesAtPoint = new CreatureStack();
        creaturesAtPoint.addCreature(creature1);
        creaturesAtPoint.addCreature(creature2);
        origin = new HexPoint(0, 0);
        initialBoard.put(origin, creaturesAtPoint);

        board = new Board(initialBoard);
    }

    @Test
    void getAllCreaturesAtEmptyPoint() {
        assertTrue(board.getAllCreatures(emptyPoint).isEmpty());
    }

    @Test
    void getAllCreatures() {
        assertEquals(creaturesAtPoint, board.getAllCreatures(origin));
    }

    @Test
    void getTopCreatureAtEmptyPoint() {
        Optional<ICreature> topCreature = board.getTopCreature(emptyPoint);

        assertFalse(topCreature.isPresent());
    }

    @Test
    void getTopCreature() {
        Optional<ICreature> topCreature = board.getTopCreature(origin);

        assertEquals(creature2, topCreature.get());
    }

    @Test
    void placeCreatureAtEmptyPoint() {
        ICreature creature = new Creature(CreatureName.CRAB, 1, Collections.singleton(CreatureProperty.INTRUDING));

        board.placeCreature(creature, emptyPoint);

        assertEquals(creature, board.getTopCreature(emptyPoint).get());
    }

    @Test
    void placeCreatureAtNonEmptyPoint() {
        ICreature creature = new Creature(CreatureName.SPIDER, 5, Collections.singleton(CreatureProperty.INTRUDING));
        board.placeCreature(creature, origin);

        assertEquals(creaturesAtPoint, board.getAllCreatures(origin));
    }

    @Test
    void removeCreatureAtEmptyPoint() {
        board.removeCreature(CreatureName.SPIDER, emptyPoint);

        assertTrue(board.getAllCreatures(emptyPoint).isEmpty());
    }

    @Test
    void removeCreatureNoMatch() {
        board.removeCreature(CreatureName.SPIDER, origin);

        assertEquals(creaturesAtPoint, board.getAllCreatures(origin));
    }

    @Test
    void removeCreature() {
        board.removeCreature(CreatureName.CRAB, origin);
        CreatureStack expectedCreatures = new CreatureStack().addCreature(creature2);

        assertEquals(expectedCreatures, board.getAllCreatures(origin));
    }

    @Test
    void removeCreatureLeavingPointEmpty() {
        ICreature creature = new Creature(CreatureName.CRAB, 1, Collections.singleton(CreatureProperty.INTRUDING));

        board.placeCreature(creature, emptyPoint);

        board.removeCreature(CreatureName.CRAB, emptyPoint);

        assertTrue(board.getAllCreatures(emptyPoint).isEmpty());
    }

    @Test
    void moveCreatureFromEmptyPoint() {
        IPoint toPoint = new HexPoint(4, 6);
        board.moveCreature(CreatureName.CRAB, emptyPoint, toPoint);

        assertTrue(board.getAllCreatures(toPoint).isEmpty());
    }

    @Test
    void moveCreatureNoMatch() {
        IPoint toPoint = new HexPoint(4, 6);
        board.moveCreature(CreatureName.BUTTERFLY, origin, toPoint);

        assertTrue(board.getAllCreatures(toPoint).isEmpty());
    }

    @Test
    void moveCreature() {
        IPoint toPoint = new HexPoint(1, 1);
        board.moveCreature(CreatureName.GRASSHOPPER, origin, toPoint);

        CreatureStack creaturesAtFromPoint = new CreatureStack().addCreature(creature1);
        CreatureStack creaturesAtToPoint = new CreatureStack().addCreature(creature2);

        assertEquals(creaturesAtFromPoint, board.getAllCreatures(origin));
        assertEquals(creaturesAtToPoint, board.getAllCreatures(toPoint));
    }

    @Test
    void moveIsNotDisconnecting() {
        for (IPoint neighboringPoint : origin.getNeighboringPoints()) {
            assertFalse(board.moveIsDisconnecting(CreatureName.CRAB, origin, neighboringPoint));
        }
    }

    @Test
    void movingOnEmptyBoardIsNotDisconnecting() {
        Board emptyBoard = new Board(new HashMap<>());
        emptyBoard.placeCreature(creature1, origin);

        for (IPoint neighboringPoint : origin.getNeighboringPoints()) {
            assertFalse(board.moveIsDisconnecting(CreatureName.CRAB, origin, neighboringPoint));
        }
    }

    @Test
    void moveIsDisconnecting() {
        for (IPoint scaledNeighboringPoint : getScaledNeighboringPoints(origin, 2)) {
            assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, origin, scaledNeighboringPoint));
        }
    }

    @Test
    void placementIsNotDisconnecting() {
        ICreature creature = new Creature(CreatureName.SPIDER, 1, Collections.singleton(CreatureProperty.INTRUDING));

        for (IPoint neighboringPoint : origin.getNeighboringPoints()) {
            assertFalse(board.placementIsDisconnecting(creature, neighboringPoint));
        }
    }

    @Test
    void firstPlacementIsNotDisconnecting() {
        Board emptyBoard = new Board(new HashMap<>());

        assertFalse(emptyBoard.placementIsDisconnecting(creature1, origin));
    }

    @Test
    void placementIsDisconnecting() {
        ICreature creature = new Creature(CreatureName.SPIDER, 1, Collections.singleton(CreatureProperty.INTRUDING));

        for (IPoint scaledNeighboringPoint : getScaledNeighboringPoints(origin, 2)) {
            assertTrue(board.placementIsDisconnecting(creature, scaledNeighboringPoint));
        }
    }

    @Test
    void creatureCanSlideWithNoNeighbors() {
        for (IPoint neighboringPoint : origin.getNeighboringPoints()) {
            assertTrue(board.creatureCanSlide(origin, neighboringPoint));
        }
    }

    @Test
    void creatureCanSlideWithCommonNeighbor() {
        ICreature neighbor = new Creature(CreatureName.SPIDER, 5, Collections.singleton(CreatureProperty.INTRUDING));
        board.placeCreature(neighbor, new HexPoint(0, 1));

        assertTrue(board.creatureCanSlide(origin, new HexPoint(-1, 1)));
        assertTrue(board.creatureCanSlide(origin, new HexPoint(1, 0)));
    }

    @Test
    void creatureCannotSlide() {
        ICreature blocker1 = new Creature(CreatureName.SPIDER, 5, Collections.singleton(CreatureProperty.INTRUDING));
        ICreature blocker2 = new Creature(CreatureName.SPIDER, 5, Collections.singleton(CreatureProperty.INTRUDING));
        ICreature blocker3 = new Creature(CreatureName.SPIDER, 5, Collections.singleton(CreatureProperty.INTRUDING));

        board.placeCreature(blocker1, new HexPoint(0, 1));
        board.placeCreature(blocker2, new HexPoint(-1, 0));
        board.placeCreature(blocker3, new HexPoint(1, -1));

        assertFalse(board.creatureCanSlide(new HexPoint(0, 0), new HexPoint(-1, 1)));
        assertFalse(board.creatureCanSlide(new HexPoint(0, 0), new HexPoint(1, 0)));
        assertFalse(board.creatureCanSlide(new HexPoint(0, 0), new HexPoint(0, -1)));
    }

    @Test
    void getPathsToDisconnectedPoint() {
        IBoard emptyBoard = new Board(new HashMap<>());
        IPoint[] obstaclePoints = {new HexPoint(-1, 2), new HexPoint(0, 1), new HexPoint(1, 0)};
        placeObstacleCreatures(obstaclePoints, emptyBoard);
        System.out.println(emptyBoard.findPathLengths(origin, new HexPoint(2, -2)));
    }

    @Test
    void getPathsToNeighboringPoint() {
        IBoard emptyBoard = new Board(new HashMap<>());
        IPoint[] obstaclePoints = {new HexPoint(-1, 2), new HexPoint(0, 1), new HexPoint(1, 0)};
        placeObstacleCreatures(obstaclePoints, emptyBoard);
        System.out.println(emptyBoard.findPathLengths(origin, new HexPoint(1, -1)));
    }

    @Test
    void getPathsWithMaximumStartingPoints() {
        IBoard emptyBoard = new Board(new HashMap<>());
        IPoint[] obstaclePoints = {new HexPoint(0, 2), new HexPoint(-1, 2),
                new HexPoint(-2, 2), new HexPoint(-2, 1),
                new HexPoint(-2, 0), new HexPoint(-1, -1),
                new HexPoint(0, -2), new HexPoint(1, -2),
                new HexPoint(2, -2), new HexPoint(2, 0),
                new HexPoint(1, 1)};
        placeObstacleCreatures(obstaclePoints, emptyBoard);
        emptyBoard.findPathLengths(origin, new HexPoint(1, -1));
    }

    private List<IPoint> getScaledNeighboringPoints(IPoint point, int scale) {
        List<IPoint> scaledNeighboringPoints = new ArrayList<>();

        for (Vector vector : unitVectors.getVectors()) {
            int scaledX = point.getX() + vector.dX() * scale;
            int scaledY = point.getY() + vector.dY() * scale;
            IPoint scaledNeighboringPoint = new HexPoint(scaledX, scaledY);
            scaledNeighboringPoints.add(scaledNeighboringPoint);
        }

        return scaledNeighboringPoints;
    }

    private void placeObstacleCreatures(IPoint[] obstaclePoints, IBoard board) {
        for (IPoint atPoint : obstaclePoints) {
            ICreature obstacleCreature = new Creature(CreatureName.CRAB, 1, Collections.singleton(CreatureProperty.WALKING));
            board.placeCreature(obstacleCreature, atPoint);
        }
    }
}
