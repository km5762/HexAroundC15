package hexaround.game.rules.pre_movement;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PreMoveDestinationInline implements ICondition<PreMoveContext> {

    /**
     * Used to determine prior to pathfinding if the destination is inline
     *
     * @param context a PreMoveContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the destination is inline
     * to fromPoint, otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PreMoveContext context) {
        IPoint fromPoint = context.fromPoint();
        IPoint toPoint = context.toPoint();
        ValidationResult result = new ValidationResult(true, null);

        if (toPoint != null && !fromPoint.inlineTo(toPoint)) {
            result = new ValidationResult(false, "That destination is not inline");
        }

        return result;
    }
}
