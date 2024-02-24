package hexaround.game.board.pathfinding;

import hexaround.game.board.Board;
import hexaround.game.board.BoardTestingUtils;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.pathvalidator.*;
import hexaround.game.board.pathfinding.pointvalidator.*;
import hexaround.game.creature.Creature;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;
import hexaround.game.player.PlayerName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroundPathfindingTest {
    IBoard board;
    IPathFinder pathFinder;

    IPathValidator pathValidator;

    IPointValidator pointValidator;

    ICreature defaultCreature  = new Creature(CreatureName.CRAB, PlayerName.RED, 5, Arrays.asList(new CreatureProperty[] {CreatureProperty.WALKING}));


    @BeforeEach
    void setUpBoard() {
        board = new Board(new HashMap<>());
    }

    @Test
    void doesExistPathToInRangePoint() {
        pathValidator = new PathValidator(Arrays.asList(new IPathCondition[] {new PathInRange(), new PathAtTarget()}));
        pointValidator = new PointValidator(Arrays.asList(new IPointCondition[] {new PointEmpty(), new PointConnected(), new PointSlideable()}));
        IPoint[] obstaclePoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3), new HexPoint(-1 ,2), new HexPoint(1, 1), new HexPoint(1, 2), new HexPoint(1, 3)};
        BoardTestingUtils.placeCreatures(defaultCreature, obstaclePoints, board);
        Optional<ICreature> creature = board.getTopCreature(new HexPoint(0, 0));
        pathFinder = new PathFinder(pointValidator, pathValidator);
        Optional<List<IPoint>> path = pathFinder.findPath(board, creature.get(), new HexPoint(0, 0), new HexPoint(0, 4));

        System.out.println(path);
    }

//    @Test
//    void doesNotExistPathToDisconnectedPoint() {
//        IPoint[] obstaclePoints = {new HexPoint(0, 0), new HexPoint(-1, 1), new HexPoint(1, -1)};
//        BoardTestingUtils.placeCreatures(defaultCreature, obstaclePoints, board);
//        Optional<ICreature> creature = board.getTopCreature(new HexPoint(0, 0));
//        assertTrue(pathFindingStrategy.getPaths(board, creature.get(), new HexPoint(-1, 1), new HexPoint(-1, 2)).isEmpty());
//        assertTrue(pathFindingStrategy.getPaths(board, creature.get(), new HexPoint(-1, 1), new HexPoint(-2, 2)).isEmpty());
//        assertTrue(pathFindingStrategy.getPaths(board, creature.get(), new HexPoint(-1, 1), new HexPoint(-2, 1)).isEmpty());
//        assertTrue(pathFindingStrategy.getPaths(board, creature.get(), new HexPoint(-1, 1), new HexPoint(1, 1)).isEmpty());
//
//    }
//
//    @Test
//    void doesNotExistPathToOutOfRangePoint() {
//        IPoint[] obstaclePoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3), new HexPoint(0, 4)};
//        BoardTestingUtils.placeCreatures(defaultCreature, obstaclePoints, board);
//        Optional<ICreature> creature = board.getTopCreature(new HexPoint(0, 0));
//        assertFalse(pathFindingStrategy.existsPath(board, creature.get(), new HexPoint(0, 0), new HexPoint(0, 5)));
//        assertFalse(pathFindingStrategy.existsPath(board, creature.get(), new HexPoint(0, 0), new HexPoint(1, 5)));
//        assertFalse(pathFindingStrategy.existsPath(board, creature.get(), new HexPoint(0, 0), new HexPoint(-1, 6)));
//    }
//
//    @Test
//    void doesExistPathToInRangePoint() {
//        IPoint[] obstaclePoints = {new HexPoint(0, 0), new HexPoint(0, 1), new HexPoint(0, 2), new HexPoint(0, 3), new HexPoint(0, 4)};
//        BoardTestingUtils.placeCreatures(defaultCreature, obstaclePoints, board);
//        Optional<ICreature> creature = board.getTopCreature(new HexPoint(0, 0));
//        assertTrue(pathFindingStrategy.existsPath(board, creature.get(), new HexPoint(0, 0), new HexPoint(1, 4)));
//        assertTrue(pathFindingStrategy.existsPath(board, creature.get(), new HexPoint(0, 0), new HexPoint(1, 3)));
//        assertTrue(pathFindingStrategy.existsPath(board, creature.get(), new HexPoint(0, 0), new HexPoint(1, 2)));
//        assertTrue(pathFindingStrategy.existsPath(board, creature.get(), new HexPoint(0, 0), new HexPoint(1, 1)));
//        assertTrue(pathFindingStrategy.existsPath(board, creature.get(), new HexPoint(0, 0), new HexPoint(1, 0)));
//    }
}
