package hexaround.game.board.pathfinding;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.stopstrategy.IStopStrategy;
import hexaround.game.creature.ICreature;

import java.util.List;
import java.util.Optional;

public interface IPathfindingStrategy {
    public Optional<List<IPoint>> getPath(IBoard board, ICreature creature, IPoint fromPoint, IStopStrategy stopStrategy);
}
