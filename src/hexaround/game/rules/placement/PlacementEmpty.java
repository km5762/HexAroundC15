package hexaround.game.rules.placement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PlacementEmpty implements ICondition<PlacementContext> {
    @Override
    public ValidationResult test(PlacementContext context) {
        IBoard board = context.board();
        IPoint point = context.point();
        ValidationResult result = new ValidationResult(true, null);

        if (board.pointIsOccupied(point)) {
            result = new ValidationResult(false, "That placement is occupied");
        }

        return result;
    }
}
