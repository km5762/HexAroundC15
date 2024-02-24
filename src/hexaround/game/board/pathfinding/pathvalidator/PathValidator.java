package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathValidator implements IPathValidator {
    protected List<IPathCondition> conditions;

    public PathValidator(List<IPathCondition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean validate(List<IPoint> path, ICreature creature, IPoint targetPoint) {
        for (IPathCondition condition : conditions) {
            if (!condition.test(path, creature, targetPoint)) {
                return false;
            }
        }
        return true;
    }
}
