package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.Board;
import hexaround.game.board.BoardTestingUtils;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.Creature;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathDestinationConnectedTest {
    IPathCondition pathDestinationConnected = new PathDestinationConnected();

    List<IPoint> path;
    IBoard board;
    ICreature creature;

    IPoint origin;

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
        creature = new Creature(CreatureName.CRAB, null, 5, null, Collections.singleton(CreatureProperty.FLYING));
        origin = new HexPoint(0, 0);
        BoardTestingUtils.placeCreatures(creature, new IPoint[] {origin, new HexPoint(0, 1), new HexPoint(0, 2)}, board);
    }

    @Test
    void pathDestinationConnected() {
        path = PathTestingUtils.constructPath(new IPoint[] {origin, new HexPoint(-1, 0), new HexPoint(-1, 1)});
        assertTrue(pathDestinationConnected.test(path, board, creature));
    }

    @Test
    void pathDestinationNotConnected() {
        path = PathTestingUtils.constructPath(new IPoint[] {origin, new HexPoint(-1, 0), new HexPoint(-1, 1), new HexPoint(-2, 2)});
        assertFalse(pathDestinationConnected.test(path, board, creature));
    }

    @Test
    void pathDestinationSplitsColony() {
        path = PathTestingUtils.constructPath(new IPoint[] {new HexPoint(0, 1), new HexPoint(-1, 1), new HexPoint(-1, 0)});
        assertFalse(pathDestinationConnected.test(path, board, creature));
    }
}