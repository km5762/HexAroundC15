package hexaround.game.board.pathfinding.stopstrategy;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

public class StopAtPathInRange implements IStopStrategy {
    @Override
    public boolean shouldStop(List<IPoint> path, ICreature creature) {
        return path.size() >= creature.getMaxDistance() + 1;
    }
}
