package hexaround.game.board.pathfinding.premovevalidator;

import hexaround.game.board.Board;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
import hexaround.game.creature.ICreature;
import hexaround.game.creature.Creature;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PreMoveNotTrappedTest {
    ICondition<PreMoveContext> preMoveNotTrapped = new PreMoveNotTrapped();
    IBoard board;
    ICreature creature;
    ICreature trapper;
    IPoint origin;

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
        creature = new Creature(CreatureName.CRAB, null, 5, null, Collections.singleton(CreatureProperty.WALKING));
        trapper = new Creature(CreatureName.SPIDER, null, 5, null, Collections.singleton(CreatureProperty.TRAPPING));
        origin = new HexPoint(0, 0);
    }

    @Test
    void preMoveNotTrapped() {
        board.placeCreature(creature, origin);
        PreMoveContext context = new PreMoveContext(board, creature, origin);

        assertTrue(preMoveNotTrapped.test(context));
    }

    @Test
    void preMoveAboveTrapper() {
        board.placeCreature(trapper, origin);
        board.placeCreature(creature, origin);
        PreMoveContext context = new PreMoveContext(board, creature, origin);

        assertTrue(preMoveNotTrapped.test(context));
    }

    @Test
    void preMoveTrapped() {
        board.placeCreature(creature, origin);
        board.placeCreature(trapper, origin);
        PreMoveContext context = new PreMoveContext(board, creature, origin);

        assertFalse(preMoveNotTrapped.test(context));
    }
}
