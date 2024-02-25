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

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveEmptyTest {
    IMoveCondition moveEmpty = new MoveEmpty();
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
    void moveNotEmpty() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(0, 1));

        assertFalse(moveEmpty.test(board, creature, origin, new HexPoint(0, 1)));
    }

    @Test
    void moveEmpty() {
        board.placeCreature(creature, origin);

        assertTrue(moveEmpty.test(board, creature, origin, new HexPoint(0, 1)));
    }
}
