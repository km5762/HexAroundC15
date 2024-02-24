package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathInRange implements IPathCondition {
    @Override
    public boolean test(List<IPoint> path, ICreature creature, IPoint targetPoint) {
        return path.size() <= creature.getMaxDistance() + 1;
    }
}
