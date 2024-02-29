package hexaround.game.rules.pre_movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PreMoveDestinationEmpty implements ICondition<PreMoveContext> {

    /**
     * Used to determine prior to pathfinding if the destination is empty
     *
     * @param context a PreMoveContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the destination is empty,
     * otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PreMoveContext context) {
        IBoard board = context.board();
        IPoint toPoint = context.toPoint();
        ValidationResult result = new ValidationResult(true, null);

        if (toPoint != null && board.pointIsOccupied(toPoint)) {
            result = new ValidationResult(false, "That destination is occupied");
        }

        return result;
    }
}
