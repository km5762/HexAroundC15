package hexaround.game.rules.movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class MoveEmpty implements ICondition<MoveContext> {
    public ValidationResult test(MoveContext context) {
        IBoard board = context.board();
        IPoint toPoint = context.toPoint();
        ValidationResult result = new ValidationResult(true, null);

        if (board.pointIsOccupied(toPoint)) {
            result = new ValidationResult(false, "That point is occupied");
        }

        return result;
    }
}
