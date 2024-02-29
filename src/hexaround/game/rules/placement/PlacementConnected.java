package hexaround.game.rules.placement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PlacementConnected implements ICondition<PlacementContext> {

    /**
     * Used to determine if the placement is connected to the colony
     *
     * @param context a PlacementContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the placement is connected,
     * otherwise "valid" will be set to false with a message describing the error.
     */
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
