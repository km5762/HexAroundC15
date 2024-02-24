package hexaround.game.board.pathfinding.pointvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.pathvalidator.IPathCondition;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PointValidator implements IPointValidator {
    protected List<IPointCondition> conditions;

    public PointValidator(List<IPointCondition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean validate(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        for (IPointCondition condition : conditions) {
            if (!condition.test(board, creature, fromPoint, toPoint)) {
                return false;
            }
        }
        return true;
    }
}
