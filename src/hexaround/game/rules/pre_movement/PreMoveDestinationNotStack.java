package hexaround.game.rules.pre_movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PreMoveDestinationNotStack implements ICondition<PreMoveContext> {
    @Override
    public ValidationResult test(PreMoveContext context) {
        IBoard board = context.board();
        IPoint toPoint = context.toPoint();
        ValidationResult result = new ValidationResult(true, null);

        if (toPoint != null && board.getAllCreatures(toPoint).getSize() > 1) {
            result = new ValidationResult(false, "The destination of this path has too many creatures");
        }

        return result;
    }
}
