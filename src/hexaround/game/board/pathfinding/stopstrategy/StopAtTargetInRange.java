package hexaround.game.board.pathfinding.stopstrategy;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

public class StopAtTargetInRange implements IStopStrategy {
    protected IPoint target;

    public StopAtTargetInRange(IPoint target) {
        this.target = target;
    }

    @Override
    public boolean shouldStop(List<IPoint> path, ICreature creature) {
        boolean atTarget = path.get(path.size() - 1).equals(target);
        boolean inRange = path.size() <= creature.getMaxDistance() + 1;
        return atTarget && inRange;
    }
}
