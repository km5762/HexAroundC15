package hexaround.game.rules.placement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PlacementConnected implements ICondition<PlacementContext> {

    @Override
    public ValidationResult test(PlacementContext context) {
        IBoard board = context.board();
        IPoint point = context.point();
        ValidationResult result = new ValidationResult(false, "That placement is not connected");

        if (board.isEmpty()) {
            result = new ValidationResult(true, null);
        } else {
            for (IPoint neighboringPoint : point.getNeighboringPoints()) {
                if (board.pointIsOccupied(neighboringPoint)) {
                    result = new ValidationResult(true, null);
                }
            }
        }
        return result;
    }
}
