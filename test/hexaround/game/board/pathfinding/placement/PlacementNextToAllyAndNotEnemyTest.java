package hexaround.game.board.pathfinding.placement;

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

public class PlacementNextToAllyAndNotEnemyTest {
    ICondition<PlacementContext> placementNextToAllyAndNotEnemy = new PlacementNextToAllyAndNotEnemy();
    IBoard board;
    ICreature redCreature;
    ICreature blueCreature;

    Player redPlayer;

    IPoint origin;

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
        redCreature = new Creature(CreatureName.CRAB, PlayerName.RED, 5, null, Collections.singleton(CreatureProperty.WALKING));
        blueCreature = new Creature(CreatureName.CRAB, PlayerName.BLUE, 5, null, Collections.singleton(CreatureProperty.WALKING));
        origin = new HexPoint(0, 0);
        Map<CreatureName, Integer> creatureCounts = new HashMap<>();
        creatureCounts.put(CreatureName.CRAB, 1000);
        redPlayer = new Player(PlayerName.RED, creatureCounts);
    }

    @Test
    void placementNotNextToAny() {
        board.placeCreature(redCreature, origin);
        PlacementContext context = new PlacementContext(board, redPlayer, CreatureName.CRAB, new HexPoint(0, 5));
        assertFalse(placementNextToAllyAndNotEnemy.test(context).valid());
    }

    @Test
    void placementNextToAlly() {
        board.placeCreature(redCreature, new HexPoint(-1, 1));
        PlacementContext context = new PlacementContext(board, redPlayer, CreatureName.CRAB, new HexPoint(0, 0));
        assertTrue(placementNextToAllyAndNotEnemy.test(context).valid());
    }

    @Test
    void placementNextToEnemy() {
        board.placeCreature(blueCreature, new HexPoint(-1, 1));
        PlacementContext context = new PlacementContext(board, redPlayer, CreatureName.CRAB, new HexPoint(0, 0));
        assertFalse(placementNextToAllyAndNotEnemy.test(context).valid());
    }

    @Test
    void placementNextToEnemyAndAlly() {
        board.placeCreature(blueCreature, new HexPoint(-1, 1));
        board.placeCreature(redCreature, new HexPoint(1, 0));
        PlacementContext context = new PlacementContext(board, redPlayer, CreatureName.CRAB, new HexPoint(0, 0));
        assertFalse(placementNextToAllyAndNotEnemy.test(context).valid());
    }
}
