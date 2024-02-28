package hexaround.game.rules.movement;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class MoveInline implements ICondition<MoveContext> {

    @Override
    public ValidationResult test(MoveContext context) {
        IPoint originPoint = context.originPoint();
        IPoint toPoint = context.toPoint();
        ValidationResult result = new ValidationResult(true, null);

        if (!originPoint.inlineTo(toPoint)) {
            result = new ValidationResult(false, "That move is not inline");
        }

        return result;
    }
}
