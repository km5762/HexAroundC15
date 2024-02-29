package hexaround.game.rules.movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class MoveEmpty implements ICondition<MoveContext> {

    /**
     * Used to determine if the given move is to an empty destination
     *
     * @param context a MovementContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the move destination is empty,
     * otherwise "valid" will be set to false with a message describing the error.
     */
    public ValidationResult test(MoveContext context) {
        IBoard board = context.board();
        IPoint toPoint = context.toPoint();
        ValidationResult result = new ValidationResult(true, null);

        if (board.pointIsOccupied(toPoint)) {
            result = new ValidationResult(false, "That destination is occupied");
        }

        return result;
    }
}
