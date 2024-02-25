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

public class PathDestinationRemovableTest {
    IPathCondition pathDestinationRemovable = new PathDestinationRemovable();
    List<IPoint> basePath;
    IBoard board;
    ICreature creature;
    IPoint origin;

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
        creature = new Creature(CreatureName.CRAB, null, 5, null, Collections.singleton(CreatureProperty.WALKING));
        origin = new HexPoint(0, 0);
        BoardTestingUtils.placeCreatures(creature, new IPoint[]{new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)}, board);
        basePath = PathTestingUtils.constructPath(new IPoint[]{origin, new HexPoint(0, 1)});
    }

    @Test
    void pathDestinationRemovable() {
        assertTrue(pathDestinationRemovable.test(basePath, board, creature));
    }

    @Test
    void pathDestinationNotRemovable() {
        basePath.add(new HexPoint(0, 2));
        assertFalse(pathDestinationRemovable.test(basePath, board, creature));
    }
}
