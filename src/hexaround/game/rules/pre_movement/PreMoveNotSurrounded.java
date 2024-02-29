package hexaround.game.rules.pre_movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PreMoveNotSurrounded implements ICondition<PreMoveContext> {

    /**
     * Used to determine prior to pathfinding if the creature being moved is surrounded
     *
     * @param context a PreMoveContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the creature is not surrounded,
     * otherwise "valid" will be set to false with a message describing the error.
     */
    public ValidationResult test(PreMoveContext context) {
        IBoard board = context.board();
        IPoint fromPoint = context.fromPoint();
        ValidationResult result = new ValidationResult(true, null);

        if (board.isSurrounded(fromPoint)) {
            result = new ValidationResult(false, "This creature is surrounded");
        }

        return result;
    }
}
