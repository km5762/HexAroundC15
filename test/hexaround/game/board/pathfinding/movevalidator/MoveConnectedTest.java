package hexaround.game.board.pathfinding.movevalidator;

import hexaround.game.board.Board;
import hexaround.game.board.BoardTestingUtils;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;
import hexaround.game.board.pathfinding.movevalidator.IMoveCondition;
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
    IMoveCondition moveConnected = new MoveConnected();
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
        assertTrue(moveConnected.test(board, creature, origin, new HexPoint(0, 1)));
    }

    @Test
    void moveConnectedOnNonEmptyBoard() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        assertTrue(moveConnected.test(board, creature, origin, new HexPoint(1, 0)));
    }

    @Test
    void moveConnectedOnStack() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        assertTrue(moveConnected.test(board, creature, origin, new HexPoint(0, 1)));
    }

    @Test
    void moveDisconnectedToDisconnectedPoint() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        assertFalse(moveConnected.test(board, creature, origin, new HexPoint(0, 3)));
    }


    @Test
    void moveSplitsColonyInTwo() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));
        board.placeCreature(creature, new HexPoint(0, -1));
        assertFalse(moveConnected.test(board, creature, origin, new HexPoint(1, 0)));
    }
}
