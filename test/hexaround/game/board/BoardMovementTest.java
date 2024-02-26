package hexaround.game.board;

import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.CreatureFactory;
import hexaround.game.creature.CreatureFactoryTestingUtils;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardMovementTest {
    IBoard board;
    CreatureFactory creatureFactory1;
    CreatureFactory creatureFactory2;
    CreatureName walkingCreatureName = CreatureName.BUTTERFLY;
    CreatureName walkingIntrudingCreatureName = CreatureName.CRAB;
    CreatureName walkingEffectCreatureName = CreatureName.DOVE;
    CreatureName runningCreatureName = CreatureName.DUCK;
    CreatureName runningIntrudingCreatureName = CreatureName.GRASSHOPPER;
    CreatureName runningEffectCreatureName = CreatureName.HORSE;
    CreatureName flyingCreatureName = CreatureName.HUMMINGBIRD;
    CreatureName flyingIntrudingCreatureName = CreatureName.RABBIT;
    CreatureName flyingEffectCreatureName = CreatureName.SPIDER;
    CreatureName jumpingCreatureName = CreatureName.TURTLE;
    CreatureName jumpingIntrudingCreatureName = CreatureName.CRAB;
    CreatureName jumpingEffectCreatureName = CreatureName.DOVE;

    @BeforeEach
    void setUp() throws IOException {
        board = new Board(new HashMap<>());
        creatureFactory1 = CreatureFactoryTestingUtils.loadCreatureFactory("testConfigurations/CreatureTestConfiguration1.hgc");
        creatureFactory2 = CreatureFactoryTestingUtils.loadCreatureFactory("testConfigurations/CreatureTestConfiguration2.hgc");
    }

    @Test
    void walkingExistsPath() {
        ICreature walkingCreature = creatureFactory1.makeCreature(walkingCreatureName, null).get();
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertTrue(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(1, 0)));
    }

    @Test
    void walkingPathOutOfRange() {
        ICreature walkingCreature = creatureFactory1.makeCreature(walkingCreatureName, null).get();
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(1, 1)));
    }

    @Test
    void walkingPathBlocked() {
        ICreature walkingCreature = creatureFactory1.makeCreature(walkingCreatureName, null).get();
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(1, 0)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(0, 1)));
    }

    @Test
    void walkingPathNotConnected() {
        ICreature walkingCreature = creatureFactory1.makeCreature(walkingCreatureName, null).get();
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(1, 0)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(-1, 1)));
    }


    @Test
    void walkingNoMovesDueToConnectivity() {
        ICreature walkingCreature = creatureFactory1.makeCreature(walkingCreatureName, null).get();
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 1)));
    }

    @Test
    void walkingNoMovesDueToBlocked() {
        ICreature walkingCreature = creatureFactory1.makeCreature(walkingCreatureName, null).get();
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-1, 0), new HexPoint(1, 0), new HexPoint(1, -1)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0)));
    }

    @Test
    void walkingIntrudingExistsPath() {
        ICreature walkingCreature = creatureFactory1.makeCreature(walkingCreatureName, null).get();
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertTrue(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(0, 1)));
    }

    @Test
    void walkingIntrudingPathOutOfRange() {
        ICreature walkingCreature = creatureFactory1.makeCreature(walkingCreatureName, null).get();
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(0, 2)));
    }

    @Test
    void walkingIntrudingPathNotConnected() {
        ICreature walkingCreature = creatureFactory1.makeCreature(walkingCreatureName, null).get();
        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0), new HexPoint(0, 2)));
    }


//    @Test
//    void walkingIntrudingCreatureExistsPath() {
//        ICreature walkingIntrudingCreature = creatureFactory1.makeCreature(walkingIntrudingCreatureName, null).get();
//        IPoint[] occupiedPoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(-1, 0), new HexPoint(1, 0), new HexPoint(1, -1)};
//        BoardTestingUtils.placeCreatures(walkingCreature, occupiedPoints, board);
//        assertFalse(board.existsPath(walkingCreature, new HexPoint(0, 0)));
//    }
}
