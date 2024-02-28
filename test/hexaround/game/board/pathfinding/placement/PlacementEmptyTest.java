package hexaround.game.board.pathfinding.placement;

import hexaround.game.board.Board;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.creature.ICreature;
import hexaround.game.creature.Creature;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.rules.movement.MoveContext;
import hexaround.game.rules.movement.MoveEmpty;
import hexaround.game.rules.placement.PlacementContext;
import hexaround.game.rules.placement.PlacementEmpty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlacementEmptyTest {
    ICondition<PlacementContext> placementEmpty = new PlacementEmpty();
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
    void placementEmpty() {
        PlacementContext context = new PlacementContext(board, null, null, origin);
        assertTrue(placementEmpty.test(context).valid());
    }

    @Test
    void placementNotEmpty() {
        board.placeCreature(creature, origin);
        PlacementContext context = new PlacementContext(board, null, null, origin);
        assertFalse(placementEmpty.test(context).valid());
    }
}
