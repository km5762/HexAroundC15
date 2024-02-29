package hexaround.game.rules.movement;

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

    public boolean creatureCanSlide(MoveContext context) {
        IPoint fromPoint = context.fromPoint();
        IPoint toPoint = context.toPoint();

        List<IPoint> fromOccupiedNeighboringPoints = getOccupiedNeighboringPoints(context, fromPoint);
        List<IPoint> toOccupiedNeighboringPoints = getOccupiedNeighboringPoints(context, toPoint);
        int commonNeighborsCount = 0;

        for (IPoint fromOccupiedNeighboringPoint : fromOccupiedNeighboringPoints) {
            if (toOccupiedNeighboringPoints.contains(fromOccupiedNeighboringPoint)) {
                commonNeighborsCount++;

                if (commonNeighborsCount == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<IPoint> getOccupiedNeighboringPoints(MoveContext context, IPoint point) {
        List<IPoint> occupiedNeighboringPoints = new ArrayList<>();

        for (IPoint neighboringPoint : point.getNeighboringPoints()) {
            MoveContext moveContext = new MoveContext(context.board(), context.creature(), context.originPoint(), context.fromPoint(), neighboringPoint);
            if (!pointEmpty.test(moveContext).valid()) {
                occupiedNeighboringPoints.add(neighboringPoint);
            }
        }

        return occupiedNeighboringPoints;
    }
}
