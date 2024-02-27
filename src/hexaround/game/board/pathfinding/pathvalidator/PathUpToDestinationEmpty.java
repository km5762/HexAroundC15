package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
import hexaround.game.board.pathfinding.movevalidator.MoveContext;
import hexaround.game.board.pathfinding.movevalidator.MoveEmpty;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathUpToDestinationEmpty implements ICondition<PathContext> {
    ICondition<MoveContext> pointEmpty = new MoveEmpty();

    @Override
    public boolean test(PathContext context) {
        IBoard board = context.board();
        List<IPoint> path = context.path();
        ICreature creature = context.creature();

        for (int i = 1; i < path.size(); i++) {
            IPoint point = path.get(i);
            boolean isLastPoint = i == path.size() - 1;
            MoveContext moveContext = new MoveContext(board, creature, null, null, point);
            if (!pointEmpty.test(moveContext) && !isLastPoint) {
                return false;
            }
        }
        return true;
    }
}
