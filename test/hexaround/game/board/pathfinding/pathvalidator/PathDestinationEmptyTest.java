package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.Board;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
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

public class PathDestinationEmptyTest {
    ICondition<PathContext> pathDestinationEmpty = new PathDestinationEmpty();

    List<IPoint> path;
    IBoard board;
    ICreature creature;

    IPoint origin;

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
        creature = new Creature(CreatureName.CRAB, null, 5, null, Collections.singleton(CreatureProperty.WALKING));
        origin = new HexPoint(0, 0);
        path = PathTestingUtils.constructPath(new IPoint[]{origin, new HexPoint(0, 1)});
    }

    @Test
    void pathDestinationEmpty() {
        board.placeCreature(creature, origin);
        PathContext context = new PathContext(path, board, creature);

        assertTrue(pathDestinationEmpty.test(context));
    }

    @Test
    void pathDestinationNotEmpty() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        PathContext context = new PathContext(path, board, creature);

        assertFalse(pathDestinationEmpty.test(context));
    }
}
