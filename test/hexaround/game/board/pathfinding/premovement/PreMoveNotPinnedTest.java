package hexaround.game.board.pathfinding.premovement;

import hexaround.game.board.Board;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.creature.ICreature;
import hexaround.game.creature.Creature;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.rules.pre_movement.PreMoveContext;
import hexaround.game.rules.pre_movement.PreMoveNotPinned;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PreMoveNotPinnedTest {
    ICondition<PreMoveContext> preMoveNotPinned = new PreMoveNotPinned();
    IBoard board;
    ICreature creature;

    IPoint origin;

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
        creature = new Creature(CreatureName.CRAB, null, 5, null, Collections.singleton(CreatureProperty.WALKING));
        origin = new HexPoint(0, 0);
    }

    @Test
    void preMoveNotPinned() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        board.placeCreature(creature, new HexPoint(0, -1));

        PreMoveContext context = new PreMoveContext(board, creature, new HexPoint(0, 1));
        assertTrue(preMoveNotPinned.test(context).valid());
    }

    @Test
    void preMovePinned() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        board.placeCreature(creature, new HexPoint(0, -1));

        PreMoveContext context = new PreMoveContext(board, creature, origin);
        assertFalse(preMoveNotPinned.test(context).valid());
    }
}
