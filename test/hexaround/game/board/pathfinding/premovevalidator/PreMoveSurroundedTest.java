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

public class PreMoveSurroundedTest {
    ICondition<PreMoveContext> preMoveSurrounded = new PreMoveSurrounded();
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
    void preMoveNotSurrounded() {
        board.placeCreature(creature, origin);
        List<IPoint> neighbors = origin.getNeighboringPoints();

        for (int i = 1; i < neighbors.size(); i++) {
            board.placeCreature(creature, neighbors.get(i));
        }

        PreMoveContext context = new PreMoveContext(board, creature, origin);
        assertFalse(preMoveSurrounded.test(context));
    }

    @Test
    void preMoveSurrounded() {
        board.placeCreature(creature, origin);
        List<IPoint> neighbors = origin.getNeighboringPoints();

        for (int i = 0; i < neighbors.size(); i++) {
            board.placeCreature(creature, neighbors.get(i));
        }

        PreMoveContext context = new PreMoveContext(board, creature, origin);
        assertTrue(preMoveSurrounded.test(context));
    }
}
