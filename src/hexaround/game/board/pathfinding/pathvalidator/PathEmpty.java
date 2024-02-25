package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.pointvalidator.IPointCondition;
import hexaround.game.board.pathfinding.pointvalidator.PointEmpty;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathEmpty implements IPathCondition {
    IPointCondition pointEmpty = new PointEmpty();

    @Override
    public boolean test(List<IPoint> path, IBoard board, ICreature creature, IPoint targetPoint) {
        for (int i = 0; i < path.size(); i++) {
            IPoint point = path.get(i);
            boolean isLastPoint = i == path.size() - 1;
            if (!pointEmpty.test(board, creature, point, point, targetPoint) && !isLastPoint) {
                return false;
            }
        }
        return true;
    }
}
