package hexaround.game.board.pathfinding.movevalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.ArrayList;
import java.util.List;

public class MoveSlideable implements IMoveCondition {
    protected static IMoveCondition pointEmpty = new MoveEmpty();

    public boolean test(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        return creatureCanSlide(board, creature, fromPoint, toPoint);
    }

    public boolean creatureCanSlide(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        List<IPoint> fromOccupiedNeighboringPoints = getOccupiedNeighboringPoints(board, creature, fromPoint);
        List<IPoint> toOccupiedNeighboringPoints = getOccupiedNeighboringPoints(board, creature, toPoint);
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

    public List<IPoint> getOccupiedNeighboringPoints(IBoard board, ICreature creature, IPoint lastPoint) {
        List<IPoint> occupiedNeighboringPoints = new ArrayList<>();

        for (IPoint neighboringPoint : lastPoint.getNeighboringPoints()) {
            if (!pointEmpty.test(board, creature, lastPoint, neighboringPoint)) {
                occupiedNeighboringPoints.add(neighboringPoint);
            }
        }

        return occupiedNeighboringPoints;
    }
}
