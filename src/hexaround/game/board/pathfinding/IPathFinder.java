package hexaround.game.board.pathfinding;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;
import java.util.Optional;

public interface IPathFinder {
    Optional<List<IPoint>> findPath(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint);
    Optional<List<IPoint>> findPath(IBoard board, ICreature creature, IPoint fromPoint);
}
