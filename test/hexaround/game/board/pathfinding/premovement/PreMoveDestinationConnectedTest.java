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
import hexaround.game.rules.pre_movement.PreMoveDestinationConnected;
import hexaround.game.rules.pre_movement.PreMoveDestinationNotButterfly;
import hexaround.game.rules.pre_movement.PreMoveNotSurrounded;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PreMoveDestinationConnectedTest {
    ICondition<PreMoveContext> preMoveDestinationConnected = new PreMoveDestinationConnected();
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
    void destinationNotConnected() {
        board.placeCreature(creature, origin);
        board.placeCreature(butterfly, new HexPoint(0, 1));
        PreMoveContext context = new PreMoveContext(board, creature, origin, new HexPoint(0, -1));
        assertFalse(preMoveDestinationConnected.test(context).valid());
    }

    @Test
    void destinationConnected() {
        board.placeCreature(creature, origin);
        board.placeCreature(butterfly, new HexPoint(0, 1));
        PreMoveContext context = new PreMoveContext(board, creature, origin, new HexPoint(0, 2));
        assertTrue(preMoveDestinationConnected.test(context).valid());
    }
}
