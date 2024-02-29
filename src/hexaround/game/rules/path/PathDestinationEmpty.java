package hexaround.game.rules.path;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;
import hexaround.game.rules.movement.MoveContext;
import hexaround.game.rules.movement.MoveEmpty;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathDestinationEmpty implements ICondition<PathContext> {

    /**
     * Used to determine if the paths destination is empty
     *
     * @param context a PathContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the path's destination is empty,
     * otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PathContext context) {
        List<IPoint> path = context.path();
        IBoard board = context.board();
        IPoint lastPoint = path.get(path.size() - 1);
        ValidationResult result = new ValidationResult(true, null);

        if (board.pointIsOccupied(lastPoint)) {
            result = new ValidationResult(false, "The destination of this path is not empty");
        }

        return result;
    }
}
