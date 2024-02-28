package hexaround.game.board;

import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.CreatureFactory;
import hexaround.game.creature.CreatureFactoryTestingUtils;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardMovementTest {
    static IBoard board;
    static CreatureFactory creatureFactory1;
    static CreatureFactory creatureFactory2;
    static ICreature walkingCreature;
    static ICreature walkingIntrudingCreature;
    static ICreature walkingEffectCreature;
    static ICreature runningCreature;
    static ICreature runningIntrudingCreature;
    static ICreature runningEffectCreature;
    static ICreature flyingCreature;
    static ICreature flyingIntrudingCreature;
    static ICreature flyingEffectCreature;
    static ICreature jumpingCreature;
    static ICreature jumpingIntrudingCreature;
    static ICreature jumpingEffectCreature;
    static ICreature trappingCreature;
    static ICreature kamikazeCreature;
    static ICreature runningKamikazeCreature;

    @BeforeAll
    static void setUpCreatures() throws IOException {
        creatureFactory1 = CreatureFactoryTestingUtils.loadCreatureFactory("testConfigurations/CreatureTestConfiguration1.hgc");
        creatureFactory2 = CreatureFactoryTestingUtils.loadCreatureFactory("testConfigurations/CreatureTestConfiguration2.hgc");

        walkingCreature = creatureFactory1.makeCreature(CreatureName.BUTTERFLY, null).get();
        walkingIntrudingCreature = creatureFactory1.makeCreature(CreatureName.CRAB, null).get();
        walkingEffectCreature = creatureFactory1.makeCreature(CreatureName.DOVE, null).get();
        runningCreature = creatureFactory1.makeCreature(CreatureName.DUCK, null).get();
        runningIntrudingCreature = creatureFactory1.makeCreature(CreatureName.GRASSHOPPER, null).get();
        runningEffectCreature = creatureFactory1.makeCreature(CreatureName.HORSE, null).get();
        flyingCreature = creatureFactory1.makeCreature(CreatureName.HUMMINGBIRD, null).get();
        flyingIntrudingCreature = creatureFactory1.makeCreature(CreatureName.RABBIT, null).get();
        flyingEffectCreature = creatureFactory1.makeCreature(CreatureName.SPIDER, null).get();
        jumpingCreature = creatureFactory1.makeCreature(CreatureName.TURTLE, null).get();
        jumpingIntrudingCreature = creatureFactory2.makeCreature(CreatureName.CRAB, null).get();
        jumpingEffectCreature = creatureFactory2.makeCreature(CreatureName.DOVE, null).get();
        trappingCreature = creatureFactory2.makeCreature(CreatureName.DUCK, null).get();
        kamikazeCreature = creatureFactory2.makeCreature(CreatureName.GRASSHOPPER, null).get();
        runningKamikazeCreature = creatureFactory2.makeCreature(CreatureName.HORSE, null).get();
    }

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());

    }

    @Test
    void walkingExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertTrue(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(1, 0)));
    }

    @Test
    void walkingPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(1, 1)));
    }

    @Test
    void walkingPathOccupied() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(0, 1)));
    }

    @Test
    void walkingPathBlocked() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(1, 0)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(0, 1)));
    }

    @Test
    void walkingPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(1, 0)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(-1, 1)));
    }


    @Test
    void walkingNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 1)));
    }

    @Test
    void walkingNoMovesBlocked() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-1, 0), new HexPoint(1, 0), new HexPoint(1, -1)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0)));
    }

    @Test
    void walkingIntrudingExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(walkingIntrudingCreature, occupiedPoints, board);
        assertTrue(board.existsPath(walkingIntrudingCreature, new HexPoint(0, 0), new HexPoint(0, 3)));
    }

    @Test
    void walkingIntrudingPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingIntrudingCreature, new HexPoint(0, 0), new HexPoint(0, 4)));
    }

    @Test
    void walkingIntrudingPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingIntrudingCreature, new HexPoint(0, 0), new HexPoint(2, 0)));
    }

    @Test
    void walkingIntrudingNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingIntrudingCreature, new HexPoint(0, 1)));
    }

    @Test
    void walkingEffectExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(walkingEffectCreature, occupiedPoints, board);
        assertTrue(board.existsPath(walkingEffectCreature, new HexPoint(0, 0), new HexPoint(0, 2)));
    }

    @Test
    void walkingEffectPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingEffectCreature, new HexPoint(0, 0), new HexPoint(0, 3)));
    }

    @Test
    void walkingEffectPathBlocked() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(1, 0)};
        BoardTestingUtils.placeCreatures(walkingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingEffectCreature, new HexPoint(0, 0), new HexPoint(0, 1)));
    }

    @Test
    void walkingEffectPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(walkingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingEffectCreature, new HexPoint(0, 0), new HexPoint(1, -1)));
    }

    @Test
    void walkingEffectNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(walkingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingEffectCreature, new HexPoint(0, 1)));
    }

    @Test
    void walkingEffectNoMovesBlocked() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-1, 0), new HexPoint(1, 0), new HexPoint(1, -1)};
        BoardTestingUtils.placeCreatures(walkingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingEffectCreature, new HexPoint(0, 0)));
    }

    @Test
    void runningExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(runningCreature, occupiedPoints, board);
        assertTrue(board.existsPath(runningCreature, new HexPoint(0, 0), new HexPoint(1, 2)));
    }

    @Test
    void runningPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(runningCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningCreature, new HexPoint(0, 0), new HexPoint(0, 3)));
    }

    @Test
    void runningPathOccupied() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(runningCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningCreature, new HexPoint(0, 0), new HexPoint(0, 2)));
    }

    @Test
    void runningPathTooSmall() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(runningCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningCreature, new HexPoint(0, 0), new HexPoint(1, 1)));
    }

    @Test
    void runningPathBlocked() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3), new HexPoint(1, 3), new HexPoint(2, 2), new HexPoint(2, 1)};
        BoardTestingUtils.placeCreatures(runningCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningCreature, new HexPoint(0, 0), new HexPoint(1, 2)));
    }

    @Test
    void runningPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(1, 0)};
        BoardTestingUtils.placeCreatures(runningCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningCreature, new HexPoint(0, 0), new HexPoint(-1, 1)));
    }


    @Test
    void runningNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(runningCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningCreature, new HexPoint(0, 1)));
    }

    @Test
    void runningNoMovesBlocked() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-1, 0), new HexPoint(1, 0), new HexPoint(1, -1)};
        BoardTestingUtils.placeCreatures(runningCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningCreature, new HexPoint(0, 0)));
    }

    @Test
    void runningNoMovesNoPathLongEnough() {
        IPoint[] occupiedPoints = {new HexPoint(-2, 2), new HexPoint(-1, 2), new HexPoint(0, 2), new HexPoint(1, 1), new HexPoint(1, 0), new HexPoint(1, -1), new HexPoint(0, -1), new HexPoint(-1, 0), new HexPoint(-2, 1), new HexPoint(0, 0)};
        BoardTestingUtils.placeCreatures(runningCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningCreature, new HexPoint(0, 0)));
    }


    @Test
    void runningIntrudingExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(runningIntrudingCreature, occupiedPoints, board);
        assertTrue(board.existsPath(runningIntrudingCreature, new HexPoint(0, 0), new HexPoint(0, 3)));
    }

    @Test
    void runningIntrudingPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3), new HexPoint(0, 4)};
        BoardTestingUtils.placeCreatures(runningIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningIntrudingCreature, new HexPoint(0, 0), new HexPoint(0, 4)));
    }

    @Test
    void runningIntrudingPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(runningIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningIntrudingCreature, new HexPoint(0, 0), new HexPoint(0, 5)));
    }

    @Test
    void runningIntrudingNoMovesNoPathLongEnough() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-1, 0), new HexPoint(1, 0), new HexPoint(1, -1)};
        BoardTestingUtils.placeCreatures(runningIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningIntrudingCreature, new HexPoint(0, 0), new HexPoint(0, 1)));
    }

    @Test
    void runningIntrudingNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(runningIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningIntrudingCreature, new HexPoint(0, 1)));
    }

    @Test
    void runningEffectExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(runningEffectCreature, occupiedPoints, board);
        assertTrue(board.existsPath(runningIntrudingCreature, new HexPoint(0, 0), new HexPoint(0, 2)));
    }

    @Test
    void runningEffectPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(runningEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningEffectCreature, new HexPoint(0, 0), new HexPoint(0, 3)));
    }

    @Test
    void runningEffectPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(runningIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningIntrudingCreature, new HexPoint(0, 0), new HexPoint(0, 5)));
    }

    @Test
    void runningEffectPathTooSmall() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(runningEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningEffectCreature, new HexPoint(0, 0), new HexPoint(1, 1)));
    }

    @Test
    void runningEffectNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(runningEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(runningEffectCreature, new HexPoint(0, 1)));
    }

    @Test
    void flyingExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-2, 2), new HexPoint(-2, 3), new HexPoint(-1, 3), new HexPoint(0, 3), new HexPoint(1, 2), new HexPoint(2, 1)};
        BoardTestingUtils.placeCreatures(flyingCreature, occupiedPoints, board);
        assertTrue(board.existsPath(flyingCreature, new HexPoint(0, 0), new HexPoint(3, 0)));
    }

    @Test
    void flyingPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-2, 2), new HexPoint(-2, 3), new HexPoint(-1, 3), new HexPoint(0, 3), new HexPoint(1, 2), new HexPoint(2, 1)};
        BoardTestingUtils.placeCreatures(flyingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(flyingCreature, new HexPoint(0, 0), new HexPoint(3, 1)));
    }

    @Test
    void flyingPathOccupied() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1)};
        BoardTestingUtils.placeCreatures(flyingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(flyingCreature, new HexPoint(0, 0), new HexPoint(0, 1)));
    }

    @Test
    void flyingPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1)};
        BoardTestingUtils.placeCreatures(flyingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(flyingCreature, new HexPoint(0, 0), new HexPoint(0, -1)));
    }

    @Test
    void flyingNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(flyingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(flyingCreature, new HexPoint(0, 1)));
    }

    @Test
    void flyingIntrudingExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-2, 2), new HexPoint(-2, 3), new HexPoint(-1, 3), new HexPoint(0, 3), new HexPoint(1, 2), new HexPoint(2, 1)};
        BoardTestingUtils.placeCreatures(flyingIntrudingCreature, occupiedPoints, board);
        assertTrue(board.existsPath(flyingIntrudingCreature, new HexPoint(0, 0), new HexPoint(2, 1)));
    }

    @Test
    void flyingIntrudingPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-2, 2), new HexPoint(-2, 3), new HexPoint(-1, 3), new HexPoint(0, 3), new HexPoint(1, 2), new HexPoint(2, 1), new HexPoint(2, 2)};
        BoardTestingUtils.placeCreatures(flyingIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(flyingIntrudingCreature, new HexPoint(0, 0), new HexPoint(2, 2)));
    }

    @Test
    void flyingIntrudingPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1)};
        BoardTestingUtils.placeCreatures(flyingIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(flyingIntrudingCreature, new HexPoint(0, 0), new HexPoint(0, -1)));
    }

    @Test
    void flyingIntrudingNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(flyingIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(flyingIntrudingCreature, new HexPoint(0, 1)));
    }

    @Test
    void flyingEffectExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-2, 2), new HexPoint(-2, 3), new HexPoint(-1, 3), new HexPoint(0, 3), new HexPoint(1, 2), new HexPoint(2, 1)};
        BoardTestingUtils.placeCreatures(flyingEffectCreature, occupiedPoints, board);
        assertTrue(board.existsPath(flyingEffectCreature, new HexPoint(0, 0), new HexPoint(2, 1)));
    }

    @Test
    void flyingEffectPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-2, 2), new HexPoint(-2, 3), new HexPoint(-1, 3), new HexPoint(0, 3), new HexPoint(1, 2), new HexPoint(2, 1), new HexPoint(2, 2)};
        BoardTestingUtils.placeCreatures(flyingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(flyingEffectCreature, new HexPoint(0, 0), new HexPoint(2, 2)));
    }

    @Test
    void flyingEffectPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1)};
        BoardTestingUtils.placeCreatures(flyingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(flyingEffectCreature, new HexPoint(0, 0), new HexPoint(0, -1)));
    }

    @Test
    void flyingEffectNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(flyingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(flyingEffectCreature, new HexPoint(0, 1)));
    }

    @Test
    void jumpingExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(0, 1), new HexPoint(1, 0), new HexPoint(1, -1), new HexPoint(0, -1), new HexPoint(-1, 0), new HexPoint(-2, 0), new HexPoint(-1, -1)};
        BoardTestingUtils.placeCreatures(jumpingCreature, occupiedPoints, board);
        assertTrue(board.existsPath(jumpingCreature, new HexPoint(0, 0), new HexPoint(-3, 0)));
    }

    @Test
    void jumpingPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(0, 1), new HexPoint(1, 0), new HexPoint(1, -1), new HexPoint(0, -1), new HexPoint(-1, 0), new HexPoint(-2, 0), new HexPoint(-1, -1), new HexPoint(-3, 0)};
        BoardTestingUtils.placeCreatures(jumpingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingCreature, new HexPoint(0, 0), new HexPoint(-4, 0)));
    }

    @Test
    void jumpingPathOccupied() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1)};
        BoardTestingUtils.placeCreatures(jumpingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingCreature, new HexPoint(0, 0), new HexPoint(0, 1)));
    }

    @Test
    void jumpingPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1)};
        BoardTestingUtils.placeCreatures(jumpingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingCreature, new HexPoint(0, 0), new HexPoint(0, -1)));
    }

    @Test
    void jumpingPathNotInline() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(0, 1), new HexPoint(1, 0), new HexPoint(1, -1), new HexPoint(0, -1), new HexPoint(-1, 0), new HexPoint(-2, 0), new HexPoint(-1, -1)};
        BoardTestingUtils.placeCreatures(jumpingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingCreature, new HexPoint(0, 0), new HexPoint(1, 1)));
    }

    @Test
    void jumpingNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(jumpingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingCreature, new HexPoint(0, 1)));
    }

    @Test
    void jumpingIntrudingExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(0, 1), new HexPoint(1, 0), new HexPoint(1, -1), new HexPoint(0, -1), new HexPoint(-1, 0), new HexPoint(-2, 0), new HexPoint(-1, -1)};
        BoardTestingUtils.placeCreatures(jumpingIntrudingCreature, occupiedPoints, board);
        assertTrue(board.existsPath(jumpingIntrudingCreature, new HexPoint(0, 0), new HexPoint(-2, 0)));
    }

    @Test
    void jumpingIntrudingPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(0, 1), new HexPoint(1, 0), new HexPoint(1, -1), new HexPoint(0, -1), new HexPoint(-1, 0), new HexPoint(-2, 0), new HexPoint(-1, -1), new HexPoint(-3, 0)};
        BoardTestingUtils.placeCreatures(jumpingIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingIntrudingCreature, new HexPoint(0, 0), new HexPoint(-4, 0)));
    }

    @Test
    void jumpingIntrudingPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1)};
        BoardTestingUtils.placeCreatures(jumpingIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingIntrudingCreature, new HexPoint(0, 0), new HexPoint(0, -1)));
    }

    @Test
    void jumpingIntrudingPathNotInline() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(0, 1), new HexPoint(1, 0), new HexPoint(1, -1), new HexPoint(0, -1), new HexPoint(-1, 0), new HexPoint(-2, 0), new HexPoint(-1, -1)};
        BoardTestingUtils.placeCreatures(jumpingIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingIntrudingCreature, new HexPoint(0, 0), new HexPoint(1, 1)));
    }

    @Test
    void jumpingIntrudingNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(jumpingIntrudingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingIntrudingCreature, new HexPoint(0, 1)));
    }

    @Test
    void jumpingEffectExistsPath() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(0, 1), new HexPoint(1, 0), new HexPoint(1, -1), new HexPoint(0, -1), new HexPoint(-1, 0), new HexPoint(-2, 0), new HexPoint(-1, -1)};
        BoardTestingUtils.placeCreatures(jumpingEffectCreature, occupiedPoints, board);
        assertTrue(board.existsPath(jumpingEffectCreature, new HexPoint(0, 0), new HexPoint(-2, 0)));
    }

    @Test
    void jumpingEffectPathOutOfRange() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(0, 1), new HexPoint(1, 0), new HexPoint(1, -1), new HexPoint(0, -1), new HexPoint(-1, 0), new HexPoint(-2, 0), new HexPoint(-1, -1), new HexPoint(-3, 0)};
        BoardTestingUtils.placeCreatures(jumpingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingEffectCreature, new HexPoint(0, 0), new HexPoint(-4, 0)));
    }

    @Test
    void jumpingEffectPathNotConnected() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1)};
        BoardTestingUtils.placeCreatures(jumpingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingEffectCreature, new HexPoint(0, 0), new HexPoint(0, -1)));
    }

    @Test
    void jumpingEffectPathNotInline() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(0, 1), new HexPoint(1, 0), new HexPoint(1, -1), new HexPoint(0, -1), new HexPoint(-1, 0), new HexPoint(-2, 0), new HexPoint(-1, -1)};
        BoardTestingUtils.placeCreatures(jumpingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingEffectCreature, new HexPoint(0, 0), new HexPoint(1, 1)));
    }

    @Test
    void jumpingEffectNoMovesPinned() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2)};
        BoardTestingUtils.placeCreatures(jumpingEffectCreature, occupiedPoints, board);
        assertFalse(board.existsPath(jumpingEffectCreature, new HexPoint(0, 1)));
    }

    @Test
    void noMovesFlyingSurrounded() {
        IPoint origin = new HexPoint(0, 0);

        for (IPoint neighboringPoint : origin.getNeighboringPoints()) {
            board.placeCreature(walkingCreature, neighboringPoint);
        }

        board.placeCreature(flyingCreature, new HexPoint(0, 0));
        assertFalse(board.existsPath(flyingCreature, new HexPoint(0, 0)));
    }

    @Test
    void pathDestinationNotRemovable() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(kamikazeCreature, occupiedPoints, board);
        assertFalse(board.existsPath(kamikazeCreature, new HexPoint(0, 0), new HexPoint(0, 2)));
    }

    @Test
    void pathDestinationAStack() {
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1)};
        BoardTestingUtils.placeCreatures(walkingIntrudingCreature, occupiedPoints, board);
        board.placeCreature(walkingIntrudingCreature, new HexPoint(0, 1));
        assertFalse(board.existsPath(walkingIntrudingCreature, new HexPoint(0, 0), new HexPoint(0, 1)));
    }

    @Test
    void cantSwapButterfly() {
        board.placeCreature(walkingCreature, new HexPoint(0, 0));
        board.placeCreature(walkingEffectCreature, new HexPoint(0, 1));
        assertFalse(board.existsPath(walkingEffectCreature, new HexPoint(0, 1), new HexPoint(0, 0)));
    }
}
