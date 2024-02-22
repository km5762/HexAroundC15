package hexaround.game.board.pathfinding.stopstrategy;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

public interface IStopStrategy {
    boolean shouldStop(List<IPoint> path, ICreature creature);
}
