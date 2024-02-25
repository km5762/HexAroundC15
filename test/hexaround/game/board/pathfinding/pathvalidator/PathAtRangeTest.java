package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.Board;
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

public class PathAtRangeTest {
    IPathCondition pathAtRange = new PathAtRange();

    List<IPoint> basePath;
    IBoard board;
    ICreature creature;

    IPoint origin;

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
        creature = new Creature(CreatureName.CRAB, null, 5, null, Collections.singleton(CreatureProperty.WALKING));
        origin = new HexPoint(0, 0);
        basePath = PathTestingUtils.constructPath(
                new IPoint[]{origin, new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3), new HexPoint(0, 4)});
    }

    @Test
    void pathEqualToRange() {
        board.placeCreature(creature, origin);
        basePath.add(new HexPoint(0, 5));
        assertTrue(pathAtRange.test(basePath, board, creature));
    }

    @Test
    void pathLessThanRange() {
        board.placeCreature(creature, origin);
        assertFalse(pathAtRange.test(basePath, board, creature));
    }

    @Test
    void pathGreaterThanRange() {
        board.placeCreature(creature, origin);
        basePath.add(new HexPoint(0, 5));
        basePath.add(new HexPoint(0, 6));
        assertFalse(pathAtRange.test(basePath, board, creature));
    }
}
