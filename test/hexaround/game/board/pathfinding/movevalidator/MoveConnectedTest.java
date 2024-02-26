package hexaround.game.board.pathfinding.movevalidator;

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

public class MoveConnectedTest {
    ICondition<MoveContext> moveConnected = new MoveConnected();
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
    void moveConnectedOnEmptyBoard() {
        board.placeCreature(creature, origin);
        MoveContext context = new MoveContext(board, creature, origin, new HexPoint(0, 1));
        assertTrue(moveConnected.test(context));
    }

    @Test
    void moveConnectedOnNonEmptyBoard() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        MoveContext context = new MoveContext(board, creature, origin, new HexPoint(1, 0));

        assertTrue(moveConnected.test(context));
    }

    @Test
    void moveConnectedOnStack() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        MoveContext context = new MoveContext(board, creature, origin, new HexPoint(0, 1));

        assertTrue(moveConnected.test(context));
    }

    @Test
    void moveDisconnectedToDisconnectedPoint() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        MoveContext context = new MoveContext(board, creature, origin, new HexPoint(0, 3));

        assertFalse(moveConnected.test(context));
    }


    @Test
    void moveSplitsColonyInTwo() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        board.placeCreature(creature, new HexPoint(0, -1));
        MoveContext context = new MoveContext(board, creature, origin, new HexPoint(1, 0));

        assertFalse(moveConnected.test(context));
    }
}
