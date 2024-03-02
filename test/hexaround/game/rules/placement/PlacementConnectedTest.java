package hexaround.game.rules.placement;

import hexaround.game.board.Board;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.player.Player;
import hexaround.game.player.PlayerName;
import hexaround.game.rules.ICondition;
import hexaround.game.creature.ICreature;
import hexaround.game.creature.Creature;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.rules.movement.MoveContext;
import hexaround.game.rules.movement.MoveEmpty;
import hexaround.game.rules.placement.PlacementConnected;
import hexaround.game.rules.placement.PlacementContext;
import hexaround.game.rules.placement.PlacementEmpty;
import hexaround.game.rules.placement.PlacementNextToAllyAndNotEnemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlacementConnectedTest {
    ICondition<PlacementContext> placementConnected = new PlacementConnected();
    IBoard board;
    ICreature creature;
    IPoint origin;

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
        creature = new Creature(CreatureName.CRAB, PlayerName.RED, 5, null, Collections.singleton(CreatureProperty.WALKING));
        origin = new HexPoint(0, 0);
    }

    @Test
    void placementNotConnected() {
        board.placeCreature(creature, origin);
        PlacementContext context = new PlacementContext(board, null, CreatureName.CRAB, new HexPoint(0, 5));
        assertFalse(placementConnected.test(context).valid());
    }

    @Test
    void placementConnected() {
        board.placeCreature(creature, origin);
        PlacementContext context = new PlacementContext(board, null, CreatureName.CRAB, new HexPoint(0, 1));
        assertTrue(placementConnected.test(context).valid());
    }
}
