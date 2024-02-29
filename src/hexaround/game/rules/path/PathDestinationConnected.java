package hexaround.game.rules.path;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;
import hexaround.game.rules.movement.MoveConnected;
import hexaround.game.rules.movement.MoveContext;

import java.util.List;

public class PathDestinationConnected implements ICondition<PathContext> {
    ICondition<MoveContext> pointConnected = new MoveConnected();


    /**
     * Used to determine if the paths destination is connected.
     *
     * @param context a PathContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the path's destination is connected,
     * otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PathContext context) {
        List<IPoint> path = context.path();
        IPoint firstPoint = path.get(0);
        IPoint lastPoint = path.get(path.size() - 1);
        IBoard board = context.board();
        ICreature creature = context.creature();
        MoveContext moveContext = new MoveContext(board, creature, null, firstPoint, lastPoint);
        ValidationResult result = new ValidationResult(true, null);

        if (!pointConnected.test(moveContext).valid()) {
            result = new ValidationResult(false, "The destination of this path is not connected");
        }

        return result;
    }
}
