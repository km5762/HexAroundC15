package hexaround.game.board.pathfinding.movement;

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
import hexaround.game.rules.movement.MoveSlideable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveSlideableTest {
    ICondition<MoveContext> moveSlideable = new MoveSlideable();
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
    void moveSlideableOnEmptyBoard() {
        board.placeCreature(creature, origin);
        MoveContext context = new MoveContext(board, creature, null, origin, new HexPoint(0, 1));

        assertTrue(moveSlideable.test(context).valid());
    }


    @Test
    void moveSlideableWithOneAdjacent() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(-1, 1));
        MoveContext context = new MoveContext(board, creature, null, origin, new HexPoint(0, 1));

        assertTrue(moveSlideable.test(context).valid());
    }

    @Test
    void moveNotSlideable() {
        board.placeCreature(creature, origin);
        board.placeCreature(creature, new HexPoint(-1, 1));
        board.placeCreature(creature, new HexPoint(1, 0));
        MoveContext context = new MoveContext(board, creature, null, origin, new HexPoint(0, 1));

        assertFalse(moveSlideable.test(context).valid());
    }
}
