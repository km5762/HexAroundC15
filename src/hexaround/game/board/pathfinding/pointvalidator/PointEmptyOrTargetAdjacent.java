package hexaround.game.board.pathfinding.pointvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

public class PointEmptyOrTargetAdjacent implements IPointCondition {
    IPointCondition pointEmpty = new PointEmpty();

    @Override
    public boolean test(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint, IPoint targetPoint) {
        return pointEmpty.test(board, creature, fromPoint, toPoint, targetPoint) || fromPoint.calculateDistanceTo(targetPoint) == 1;
    }
}
