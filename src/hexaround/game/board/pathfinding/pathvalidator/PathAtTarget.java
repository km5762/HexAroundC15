package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathAtTarget implements IPathCondition {
    @Override
    public boolean test(List<IPoint> path, ICreature creature, IPoint targetPoint) {
        return path.get(path.size() - 1).equals(targetPoint);
    }
}
