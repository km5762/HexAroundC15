package hexaround.game.board.pathfinding.path;

import hexaround.game.board.Board;
import hexaround.game.board.BoardTestingUtils;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.creature.ICreature;
import hexaround.game.creature.Creature;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.rules.path.PathContext;
import hexaround.game.rules.path.PathUpToDestinationEmpty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathUpToDestinationEmptyTest {
    ICondition<PathContext> pathUpToDestinationEmpty = new PathUpToDestinationEmpty();
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
        PathContext context = new PathContext(path, board, creature);

        assertTrue(pathUpToDestinationEmpty.test(context).valid());
    }

    @Test
    void pathUpToDestinationEmpty() {
        path = PathTestingUtils.constructPath(new IPoint[] {origin, new HexPoint(1, 0), new HexPoint(1, 1), new HexPoint(0, 2)});
        PathContext context = new PathContext(path, board, creature);

        assertTrue(pathUpToDestinationEmpty.test(context).valid());    }

    @Test
    void pathContainsOccupiedPoints() {
        path = PathTestingUtils.constructPath(new IPoint[] {origin, new HexPoint(1, 0), new HexPoint(0, 1), new HexPoint(1, 1)});
        PathContext context = new PathContext(path, board, creature);

        assertFalse(pathUpToDestinationEmpty.test(context).valid());    }
}
