package hexaround.game.rules.path;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;
import hexaround.game.rules.movement.MoveContext;
import hexaround.game.rules.movement.MoveEmpty;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathUpToDestinationEmpty implements ICondition<PathContext> {
    ICondition<MoveContext> pointEmpty = new MoveEmpty();

    /**
     * Used to determine the every point up to the destination of a path is empty
     *
     * @param context a PathContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if every point up to the destination is empty,
     * otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PathContext context) {
        IBoard board = context.board();
        List<IPoint> path = context.path();
        ICreature creature = context.creature();
        ValidationResult result = new ValidationResult(true, null);

        for (int i = 1; i < path.size(); i++) {
            IPoint point = path.get(i);
            boolean isLastPoint = i == path.size() - 1;
            MoveContext moveContext = new MoveContext(board, creature, null, null, point);
            if (!pointEmpty.test(moveContext).valid() && !isLastPoint) {
                result = new ValidationResult(false, "The path up to this destination is not empty");
            }
        }
        return result;
    }
}
