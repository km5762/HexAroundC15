package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.pointvalidator.IPointCondition;
import hexaround.game.board.pathfinding.pointvalidator.PointConnected;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathConnected implements IPathCondition {
    IPointCondition pointConnected = new PointConnected();

    @Override
    public boolean test(List<IPoint> path, IBoard board, ICreature creature, IPoint targetPoint) {
        IPoint firstPoint = path.get(0);
        IPoint lastPoint = path.get(path.size() - 1);

        return pointConnected.test(board, creature, firstPoint, lastPoint, targetPoint);
    }
}
