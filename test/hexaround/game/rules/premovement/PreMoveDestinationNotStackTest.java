package hexaround.game.rules.premovement;

import hexaround.game.board.Board;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.Creature;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.pre_movement.PreMoveContext;
import hexaround.game.rules.pre_movement.PreMoveDestinationConnected;
import hexaround.game.rules.pre_movement.PreMoveDestinationNotStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PreMoveDestinationNotStackTest {
    ICondition<PreMoveContext> preMoveDestinationNotStack = new PreMoveDestinationNotStack();
    IBoard board;
    ICreature creature;

    ICreature butterfly;

    IPoint origin;

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
        creature = new Creature(CreatureName.CRAB, null, 5, null, Collections.singleton(CreatureProperty.WALKING));
        butterfly = new Creature(CreatureName.BUTTERFLY, null, 5, null, Collections.singleton(CreatureProperty.WALKING));
        origin = new HexPoint(0, 0);
    }

    @Test
    void destinationNotStack() {
        board.placeCreature(creature, origin);
        PreMoveContext context = new PreMoveContext(board, creature, origin, new HexPoint(0, 1));
        assertTrue(preMoveDestinationNotStack.test(context).valid());
    }

    @Test
    void destinationStack() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        PreMoveContext context = new PreMoveContext(board, creature, new HexPoint(0, 1), origin);
        assertFalse(preMoveDestinationNotStack.test(context).valid());
    }
}
