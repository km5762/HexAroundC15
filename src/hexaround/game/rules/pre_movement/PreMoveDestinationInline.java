package hexaround.game.rules.pre_movement;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PreMoveDestinationInline implements ICondition<PreMoveContext> {
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
