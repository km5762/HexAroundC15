package hexaround.game.board;

import hexaround.game.creature.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private IBoard board;
    UnitVectors unitVectors = new UnitVectors();
    List<ICreature> creaturesAtPoint;
    ICreature creature1;
    ICreature creature2;

    @BeforeEach
    void setUpBoard() {
        HashMap<Point, List<ICreature>> initialBoard = new HashMap<>();
        creature1 = new Creature(CreatureName.CRAB, 1, Collections.singleton(CreatureProperty.INTRUDING));
        creature2 = new Creature(CreatureName.GRASSHOPPER, 3, Collections.singleton(CreatureProperty.INTRUDING));
        creaturesAtPoint = new LinkedList<>(Arrays.asList(new ICreature[]{creature1, creature2}));
        initialBoard.put(new Point(0, 0), creaturesAtPoint);

        board = new Board(initialBoard);
    }

    @Test
    void getAllCreaturesAtEmptyPoint() {
        assertEquals(Collections.emptyList(), board.getAllCreatures(3, 6));
    }


    @Test
    void getAllCreatures() {
        assertEquals(creaturesAtPoint, board.getAllCreatures(0, 0));
    }

    @Test
    void getCreatureWithNameAtEmptyPoint() {
        Optional<ICreature> creatureWithName = board.getCreatureWithName(CreatureName.SPIDER, 3, 6);

        assertFalse(creatureWithName.isPresent());
    }

    @Test
    void getCreatureWithNameNoMatch() {
        Optional<ICreature> creatureWithName = board.getCreatureWithName(CreatureName.SPIDER, 0, 0);

        assertFalse(creatureWithName.isPresent());
    }

    @Test
    void getCreatureWithName() {
        Optional<ICreature> creatureWithName = board.getCreatureWithName(CreatureName.CRAB, 0, 0);

        assertEquals(creature1, creatureWithName.get());
    }

    @Test
    void getTopCreatureAtEmptyPoint() {
        Optional<ICreature> topCreature = board.getTopCreature(3, 6);

        assertFalse(topCreature.isPresent());
    }

    @Test
    void getTopCreature() {
        Optional<ICreature> topCreature = board.getTopCreature(0, 0);

        assertEquals(creature2, topCreature.get());
    }

    @Test
    void placeCreatureAtEmptyPoint() {
        ICreature creature = new Creature(CreatureName.CRAB, 1, Collections.singleton(CreatureProperty.INTRUDING));

        board.placeCreature(creature, 3, 6);

        assertEquals(creature, board.getTopCreature(3, 6).get());
    }

    @Test
    void placeCreatureAtNonEmptyPoint() {
        ICreature creature = new Creature(CreatureName.SPIDER, 5, Collections.singleton(CreatureProperty.INTRUDING));
        board.placeCreature(creature, 0, 0);

        assertEquals(creaturesAtPoint, board.getAllCreatures(0, 0));
    }

    @Test
    void removeCreatureAtEmptyPoint() {
        board.removeCreature(CreatureName.SPIDER, 3, 6);

        assertEquals(Collections.emptyList(), board.getAllCreatures(3, 6));
    }

    @Test
    void removeCreatureNoMatch() {
        board.removeCreature(CreatureName.SPIDER, 0, 0);

        assertEquals(creaturesAtPoint, board.getAllCreatures(0, 0));
    }

    @Test
    void removeCreature() {
        board.removeCreature(CreatureName.CRAB, 0, 0);
        List<ICreature> expectedCreatures = Arrays.asList(new ICreature[]{creature2});

        assertEquals(expectedCreatures, board.getAllCreatures(0, 0));
    }

    @Test
    void removeCreatureLeavingPointEmpty() {
        ICreature creature = new Creature(CreatureName.CRAB, 1, Collections.singleton(CreatureProperty.INTRUDING));

        board.placeCreature(creature, 3, 6);

        board.removeCreature(CreatureName.CRAB, 3, 6);

        assertEquals(Collections.emptyList(), board.getAllCreatures(3, 6));
    }

    @Test
    void moveCreatureFromEmptyPoint() {
        board.moveCreature(CreatureName.CRAB, 3, 6, 4, 6);

        assertEquals(Collections.emptyList(), board.getAllCreatures(4, 6));
    }

    @Test
    void moveCreatureNoMatch() {
        board.moveCreature(CreatureName.BUTTERFLY, 0, 0, 4, 5);

        assertEquals(Collections.emptyList(), board.getAllCreatures(4, 5));
    }

    @Test
    void moveCreature() {
        board.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 1, 1);

        List<ICreature> creaturesAtPoint1 = Arrays.asList(new ICreature[]{creature1});
        List<ICreature> creaturesAtPoint2 = Arrays.asList(new ICreature[]{creature2});

        assertEquals(creaturesAtPoint1, board.getAllCreatures(0, 0));
        assertEquals(creaturesAtPoint2, board.getAllCreatures(1, 1));
    }

    @Test
    void moveIsNotDisconnecting() {
        for (Vector unitVector : unitVectors.getVectors()) {
            assertFalse(board.moveIsDisconnecting(CreatureName.CRAB, 0, 0, unitVector.dX(), unitVector.dY()));
        }
    }

    @Test
    void moveIsDisconnecting() {
        for (Vector unitVector : unitVectors.getVectors()) {
            assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 0, 0, unitVector.dX() * 2, unitVector.dY() * 2));
        }
    }

    @Test
    void placementIsNotDisconnecting() {
        ICreature creature = new Creature(CreatureName.SPIDER, 1, Collections.singleton(CreatureProperty.INTRUDING));

        for (Vector unitVector : unitVectors.getVectors()) {
            assertFalse(board.placementIsDisconnecting(creature, unitVector.dX(), unitVector.dY()));
        }
    }

    @Test
    void placementIsDisconnecting() {
        ICreature creature = new Creature(CreatureName.SPIDER, 1, Collections.singleton(CreatureProperty.INTRUDING));

        for (Vector unitVector : unitVectors.getVectors()) {
            assertTrue(board.placementIsDisconnecting(creature, unitVector.dX() * 2, unitVector.dY() * 2));
        }
    }

    @Test
    void pieceCanSlide() {

    }
}
