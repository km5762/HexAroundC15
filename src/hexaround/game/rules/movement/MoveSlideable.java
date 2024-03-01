package hexaround.game.rules.movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

import java.util.ArrayList;
import java.util.List;

public class MoveSlideable implements ICondition<MoveContext> {
    protected static ICondition<MoveContext> pointEmpty = new MoveEmpty();

    /**
     * Used to determine if the given move is slideable, meaning that it a creature may be dragged to it unblocked
     *
     * @param context a MovementContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the creature can be dragged to the point,
     * otherwise "valid" will be set to false with a message describing the error.
     */
    public ValidationResult test(MoveContext context) {
        ValidationResult result = new ValidationResult(true, null);

        if (!creatureCanSlide(context)) {
            result = new ValidationResult(false, "That destination is blocked");
        }

        return result;
    }

    private boolean creatureCanSlide(MoveContext context) {
        IBoard board = context.board();
        IPoint fromPoint = context.fromPoint();
        IPoint toPoint = context.toPoint();
        List<IPoint> fromNeighboringPoints = fromPoint.getNeighboringPoints();
        List<IPoint> toNeighboringPoints = toPoint.getNeighboringPoints();

        int commonNeighborsCount = 0;

        for (IPoint fromNeighboringPoint : fromNeighboringPoints) {
            boolean commonNeighbor = toNeighboringPoints.contains(fromNeighboringPoint);
            boolean occupied = board.pointIsOccupied(fromNeighboringPoint);

            if (commonNeighbor && occupied) {
                commonNeighborsCount++;

                if (commonNeighborsCount == 2) {
                    return false;
                }
            }
        }
        return true;
    }
}
