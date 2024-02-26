package hexaround.game.board.pathfinding.movevalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
import hexaround.game.creature.ICreature;

import java.util.ArrayList;
import java.util.List;

public class MoveSlideable implements ICondition<MoveContext> {
    protected static ICondition<MoveContext> pointEmpty = new MoveEmpty();

    public boolean test(MoveContext context) {
        return creatureCanSlide(context);
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
            MoveContext moveContext = new MoveContext(context.board(), context.creature(), context.fromPoint(), neighboringPoint);
            if (!pointEmpty.test(moveContext)) {
                occupiedNeighboringPoints.add(neighboringPoint);
            }
        }

        return occupiedNeighboringPoints;
    }
}
