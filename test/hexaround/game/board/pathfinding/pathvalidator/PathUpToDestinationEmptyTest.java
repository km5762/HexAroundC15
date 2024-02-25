package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.Board;
import hexaround.game.board.BoardTestingUtils;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathUpToDestinationEmptyTest {
    IPathCondition pathUpToDestinationEmpty = new PathUpToDestinationEmpty();
    IBoard board;

    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
    }

//    @Test
//    void pathUpToDestinationNotEmpty() {
//        IPoint[] points = {new HexPoint(0,0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
//        List<IPoint> path = new LinkedList<>(Arrays.asList( new IPoint[] {new HexPoint(0, 0), new HexPoint(0, 1)}));
//        BoardTestingUtils.placeCreatures(points, board);
//        assertTrue(pathDestinationRemovable.test(path, board, board.getTopCreature(new HexPoint(0, 0)).get()));
//    }
//
//    @Test
//    void pathDestinationNotRemovable() {
//        IPoint[] points = {new HexPoint(0,0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3)};
//        List<IPoint> path = new LinkedList<>(Arrays.asList( new IPoint[] {new HexPoint(0, 0), new HexPoint(0, 2)}));
//        BoardTestingUtils.placeCreatures(points, board);
//        assertFalse(pathDestinationRemovable.test(path, board, board.getTopCreature(new HexPoint(0, 0)).get()));
//    }
}
