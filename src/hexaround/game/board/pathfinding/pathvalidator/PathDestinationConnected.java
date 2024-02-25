package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.movevalidator.IMoveCondition;
import hexaround.game.board.pathfinding.movevalidator.MoveConnected;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathDestinationConnected implements IPathCondition {
    IMoveCondition pointConnected = new MoveConnected();

    @Override
    public boolean test(List<IPoint> path, IBoard board, ICreature creature) {
        IPoint firstPoint = path.get(0);
        IPoint lastPoint = path.get(path.size() - 1);

        return pointConnected.test(board, creature, firstPoint, lastPoint);
    }
}
