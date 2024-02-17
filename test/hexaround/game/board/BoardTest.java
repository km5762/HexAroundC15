package hexaround.game.board;

import hexaround.game.creature.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private IBoard board;
    List<ICreature> creaturesAtPoint;
    ICreature creature1;
    ICreature creature2;

    @BeforeEach
    void setUpBoard() {
        HashMap<Point, List<ICreature>> initialBoard = new HashMap<>();
        creature1 = new Creature(CreatureName.CRAB, 1, Collections.singleton(CreatureProperty.INTRUDING));
        creature2 = new Creature(CreatureName.GRASSHOPPER, 3, Collections.singleton(CreatureProperty.INTRUDING));
        creaturesAtPoint = new LinkedList<>(Arrays.asList(new ICreature[]{creature1, creature2}));
        initialBoard.put(new Point(3, 5), creaturesAtPoint);

        board = new Board(initialBoard);
    }

    @Test
    void getAllCreaturesAtEmptyPoint() {
        assertEquals(Collections.emptyList(), board.getAllCreatures(3, 6));
    }


    @Test
    void getAllCreatures() {
        assertEquals(creaturesAtPoint, board.getAllCreatures(3, 5));
    }

    @Test
    void getCreatureWithNameAtEmptyPoint() {
        Optional<ICreature> creatureWithName = board.getCreatureWithName(CreatureName.SPIDER, 3, 6);

        assertFalse(creatureWithName.isPresent());
    }

    @Test
    void getCreatureWithNameNoMatch() {
        Optional<ICreature> creatureWithName = board.getCreatureWithName(CreatureName.SPIDER, 3, 5);

        assertFalse(creatureWithName.isPresent());
    }

    @Test
    void getCreatureWithName() {
        Optional<ICreature> creatureWithName = board.getCreatureWithName(CreatureName.CRAB, 3, 5);

        assertEquals(creature1, creatureWithName.get());
    }

    @Test
    void getTopCreatureAtEmptyPoint() {
        Optional<ICreature> topCreature = board.getTopCreature(3, 6);

        assertFalse(topCreature.isPresent());
    }

    @Test
    void getTopCreature() {
        Optional<ICreature> topCreature = board.getTopCreature(3, 5);

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
        board.placeCreature(creature, 3, 5);

        assertEquals(creaturesAtPoint, board.getAllCreatures(3, 5));
    }

    @Test
    void removeCreatureAtEmptyPoint() {
        board.removeCreature(CreatureName.SPIDER, 3, 6);

        assertEquals(Collections.emptyList(), board.getAllCreatures(3, 6));
    }

    @Test
    void removeCreatureNoMatch() {
        board.removeCreature(CreatureName.SPIDER, 3, 5);

        assertEquals(creaturesAtPoint, board.getAllCreatures(3, 5));
    }

    @Test
    void removeCreature() {
        board.removeCreature(CreatureName.CRAB, 3, 5);
        List<ICreature> expectedCreatures = Arrays.asList(new ICreature[]{creature2});

        assertEquals(expectedCreatures, board.getAllCreatures(3, 5));
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
        board.moveCreature(CreatureName.BUTTERFLY, 3, 5, 4, 5);

        assertEquals(Collections.emptyList(), board.getAllCreatures(4, 5));
    }

    @Test
    void moveCreature() {
        board.moveCreature(CreatureName.GRASSHOPPER, 3, 5, 5, 6);

        List<ICreature> creaturesAtPoint1 = Arrays.asList(new ICreature[]{creature1});
        List<ICreature> creaturesAtPoint2 = Arrays.asList(new ICreature[]{creature2});

        assertEquals(creaturesAtPoint1, board.getAllCreatures(3, 5));
        assertEquals(creaturesAtPoint2, board.getAllCreatures(5, 6));
    }

    @Test
    void moveIsNotDisconnecting() {
        assertFalse(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 2, 6));
        assertFalse(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 3, 6));
        assertFalse(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 4, 5));
        assertFalse(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 4, 4));
        assertFalse(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 3, 4));
        assertFalse(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 2, 5));
    }

    @Test
    void moveIsDisconnecting() {
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 3, 7));
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 4, 6));
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 5, 5));
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 5, 4));
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 5, 3));
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 4, 3));
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 3, 3));
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 2, 4));
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 1, 5));
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 1, 6));
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 1, 7));
        assertTrue(board.moveIsDisconnecting(CreatureName.CRAB, 3, 5, 2, 7));
    }

    @Test
    void placementIsNotDisconnecting() {
        ICreature creature = new Creature(CreatureName.SPIDER, 1, Collections.singleton(CreatureProperty.INTRUDING));

        assertFalse(board.placementIsDisconnecting(creature, 2, 6));
        assertFalse(board.placementIsDisconnecting(creature, 3, 6));
        assertFalse(board.placementIsDisconnecting(creature, 4, 5));
        assertFalse(board.placementIsDisconnecting(creature, 4, 4));
        assertFalse(board.placementIsDisconnecting(creature, 3, 4));
        assertFalse(board.placementIsDisconnecting(creature, 2, 5));
    }

    @Test
    void placementIsDisconnecting() {
        ICreature creature = new Creature(CreatureName.SPIDER, 1, Collections.singleton(CreatureProperty.INTRUDING));

        assertTrue(board.placementIsDisconnecting(creature, 3, 7));
        assertTrue(board.placementIsDisconnecting(creature, 4, 6));
        assertTrue(board.placementIsDisconnecting(creature, 5, 5));
        assertTrue(board.placementIsDisconnecting(creature, 5, 4));
        assertTrue(board.placementIsDisconnecting(creature, 5, 3));
        assertTrue(board.placementIsDisconnecting(creature, 4, 3));
        assertTrue(board.placementIsDisconnecting(creature, 3, 3));
        assertTrue(board.placementIsDisconnecting(creature, 2, 4));
        assertTrue(board.placementIsDisconnecting(creature, 1, 5));
        assertTrue(board.placementIsDisconnecting(creature, 1, 6));
        assertTrue(board.placementIsDisconnecting(creature, 1, 7));
        assertTrue(board.placementIsDisconnecting(creature, 2, 7));
    }
}
