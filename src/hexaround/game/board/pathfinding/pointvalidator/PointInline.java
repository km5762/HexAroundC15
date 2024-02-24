package hexaround.game.board.pathfinding.pointvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

public class PointInline implements IPointCondition {

    @Override
    public boolean test(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        return fromPoint.inlineTo(toPoint);
    }
}
