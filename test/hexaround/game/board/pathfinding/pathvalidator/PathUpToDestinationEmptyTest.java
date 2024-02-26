package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.Board;
import hexaround.game.board.BoardTestingUtils;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;
import hexaround.game.creature.Creature;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathUpToDestinationEmptyTest {
    IPathCondition pathUpToDestinationEmpty = new PathUpToDestinationEmpty();
    List<IPoint> path;
    IBoard board;
    ICreature creature;
    IPoint origin;

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
        creature = new Creature(CreatureName.CRAB, null, 5, null, Collections.singleton(CreatureProperty.WALKING));
        origin = new HexPoint(0, 0);
        BoardTestingUtils.placeCreatures(creature, new IPoint[]{new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)}, board);
    }

    @Test
    void pathCompletelyEmpty() {
        path = PathTestingUtils.constructPath(new IPoint[] {origin, new HexPoint(1, 0), new HexPoint(1, 1)});
        assertTrue(pathUpToDestinationEmpty.test(path, board, creature));
    }

    @Test
    void pathUpToDestinationEmpty() {
        path = PathTestingUtils.constructPath(new IPoint[] {origin, new HexPoint(1, 0), new HexPoint(1, 1), new HexPoint(0, 2)});
        assertTrue(pathUpToDestinationEmpty.test(path, board, creature));
    }

    @Test
    void pathContainsOccupiedPoints() {
        path = PathTestingUtils.constructPath(new IPoint[] {origin, new HexPoint(1, 0), new HexPoint(0, 1), new HexPoint(1, 1)});
        assertFalse(pathUpToDestinationEmpty.test(path, board, creature));
    }
}
