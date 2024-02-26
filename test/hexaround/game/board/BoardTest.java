package hexaround.game.board;

import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.geometry.UnitVectors;
import hexaround.game.board.geometry.Vector;
import hexaround.game.creature.*;
import hexaround.game.player.Player;
import hexaround.game.player.PlayerName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    IBoard board;
    CreatureStack creaturesAtPoint;
    ICreature creature1;
    ICreature creature2;
    ICreature nonExistentCreature;
    IPoint origin;
    IPoint emptyPoint = new HexPoint(5, 5);

    @BeforeEach
    void setUpBoard() {
        HashMap<IPoint, CreatureStack> initialBoard = new HashMap<>();
        nonExistentCreature = new Creature(null, null, 5, null, null);
        creature1 = new Creature(CreatureName.CRAB, PlayerName.RED, 1, null, Collections.singleton(CreatureProperty.INTRUDING));
        creature2 = new Creature(CreatureName.GRASSHOPPER, PlayerName.RED, 3, null, Collections.singleton(CreatureProperty.INTRUDING));
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
        ICreature creature = new Creature(CreatureName.CRAB, PlayerName.RED, 1, null, Collections.singleton(CreatureProperty.INTRUDING));

        board.placeCreature(creature, emptyPoint);

        assertEquals(creature, board.getTopCreature(emptyPoint).get());
    }

    @Test
    void placeCreatureAtNonEmptyPoint() {
        ICreature creature = new Creature(CreatureName.SPIDER, PlayerName.RED, 5, null, Collections.singleton(CreatureProperty.INTRUDING));
        board.placeCreature(creature, origin);

        assertEquals(creaturesAtPoint, board.getAllCreatures(origin));
    }

    @Test
    void removeCreatureAtEmptyPoint() {
        board.removeCreature(creature1, emptyPoint);

        assertTrue(board.getAllCreatures(emptyPoint).isEmpty());
    }

    @Test
    void removeCreature() {
        board.removeCreature(creature1, origin);
        CreatureStack expectedCreatures = new CreatureStack().addCreature(creature2);

        assertEquals(expectedCreatures, board.getAllCreatures(origin));
    }

    @Test
    void removeCreatureLeavingPointEmpty() {
        ICreature creature = new Creature(CreatureName.CRAB, PlayerName.RED, 1, null, Collections.singleton(CreatureProperty.INTRUDING));

        board.placeCreature(creature, emptyPoint);

        board.removeCreature(creature, emptyPoint);

        assertTrue(board.getAllCreatures(emptyPoint).isEmpty());
    }

    @Test
    void moveCreature() {
        IPoint toPoint = new HexPoint(1, 1);
        board.moveCreature(creature2, origin, toPoint);

        CreatureStack creaturesAtFromPoint = new CreatureStack().addCreature(creature1);
        CreatureStack creaturesAtToPoint = new CreatureStack().addCreature(creature2);

        assertEquals(creaturesAtFromPoint, board.getAllCreatures(origin));
        assertEquals(creaturesAtToPoint, board.getAllCreatures(toPoint));
    }

    @Test
    void placementIsNotDisconnecting() {
        ICreature creature = new Creature(CreatureName.SPIDER, PlayerName.RED, 1, null, Collections.singleton(CreatureProperty.INTRUDING));

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
        ICreature creature = new Creature(CreatureName.SPIDER, PlayerName.RED, 1, null, Collections.singleton(CreatureProperty.INTRUDING));

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
        ICreature neighbor = new Creature(CreatureName.SPIDER, PlayerName.RED, 5, null, Collections.singleton(CreatureProperty.INTRUDING));
        board.placeCreature(neighbor, new HexPoint(0, 1));

        assertTrue(board.creatureCanSlide(origin, new HexPoint(-1, 1)));
        assertTrue(board.creatureCanSlide(origin, new HexPoint(1, 0)));
    }

    @Test
    void creatureCannotSlide() {
        ICreature blocker1 = new Creature(CreatureName.SPIDER, PlayerName.RED, 5, null, Collections.singleton(CreatureProperty.INTRUDING));
        ICreature blocker2 = new Creature(CreatureName.SPIDER, PlayerName.RED,  5, null, Collections.singleton(CreatureProperty.INTRUDING));
        ICreature blocker3 = new Creature(CreatureName.SPIDER, PlayerName.RED, 5, null, Collections.singleton(CreatureProperty.INTRUDING));

        board.placeCreature(blocker1, new HexPoint(0, 1));
        board.placeCreature(blocker2, new HexPoint(-1, 0));
        board.placeCreature(blocker3, new HexPoint(1, -1));

        assertFalse(board.creatureCanSlide(new HexPoint(0, 0), new HexPoint(-1, 1)));
        assertFalse(board.creatureCanSlide(new HexPoint(0, 0), new HexPoint(1, 0)));
        assertFalse(board.creatureCanSlide(new HexPoint(0, 0), new HexPoint(0, -1)));
    }

    private List<IPoint> getScaledNeighboringPoints(IPoint point, int scale) {
        List<IPoint> scaledNeighboringPoints = new ArrayList<>();

        for (Vector vector : UnitVectors.VECTORS) {
            int scaledX = point.getX() + vector.dX() * scale;
            int scaledY = point.getY() + vector.dY() * scale;
            IPoint scaledNeighboringPoint = new HexPoint(scaledX, scaledY);
            scaledNeighboringPoints.add(scaledNeighboringPoint);
        }

        return scaledNeighboringPoints;
    }
}
