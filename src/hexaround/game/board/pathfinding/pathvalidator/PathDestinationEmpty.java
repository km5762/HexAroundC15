package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
import hexaround.game.board.pathfinding.movevalidator.MoveContext;
import hexaround.game.board.pathfinding.movevalidator.MoveEmpty;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathDestinationEmpty implements ICondition<PathContext> {
    ICondition<MoveContext> pointEmpty = new MoveEmpty();

    @Override
    public boolean test(PathContext context) {
        List<IPoint> path = context.path();
        IBoard board = context.board();
        ICreature creature = context.creature();

        IPoint lastPoint = path.get(path.size() - 1);
        MoveContext moveContext = new MoveContext(board, creature, null, null, lastPoint);

        return pointEmpty.test(moveContext);
    }
}
