package hexaround.game.board.pathfinding.premovevalidator;

import hexaround.game.board.Board;
import hexaround.game.board.BoardTestingUtils;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
import hexaround.game.creature.ICreature;
import hexaround.game.board.pathfinding.movevalidator.MoveConnected;
import hexaround.game.creature.Creature;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PreMovePinnedTest {
    ICondition<PreMoveContext> preMovedPinned = new PreMovePinned();
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
        assertFalse(preMovedPinned.test(context));
    }

    @Test
    void preMovePinned() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        board.placeCreature(creature, new HexPoint(0, -1));

        PreMoveContext context = new PreMoveContext(board, creature, origin);
        assertTrue(preMovedPinned.test(context));
    }
}
