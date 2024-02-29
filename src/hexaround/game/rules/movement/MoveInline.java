package hexaround.game.rules.movement;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class MoveInline implements ICondition<MoveContext> {

    /**
     * Used to determine if the given move is inline to the origin
     *
     * @param context a MovementContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the move will not disconnect the colony,
     * otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(MoveContext context) {
        IPoint originPoint = context.originPoint();
        IPoint toPoint = context.toPoint();
        ValidationResult result = new ValidationResult(true, null);

        if (!originPoint.inlineTo(toPoint)) {
            result = new ValidationResult(false, "That destination is not inline");
        }

        return result;
    }
}
