package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
import hexaround.game.board.pathfinding.movevalidator.MoveConnected;
import hexaround.game.board.pathfinding.movevalidator.MoveContext;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathDestinationConnected implements ICondition<PathContext> {
    ICondition<MoveContext> pointConnected = new MoveConnected();

    @Override
    public boolean test(PathContext context) {
        List<IPoint> path = context.path();
        IPoint firstPoint = path.get(0);
        IPoint lastPoint = path.get(path.size() - 1);

        MoveContext moveContext = new MoveContext(context.board(), context.creature(), firstPoint, lastPoint);
        return pointConnected.test(moveContext);
    }
}
