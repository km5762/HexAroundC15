package hexaround.game.board.pathfinding;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.geometry.UnitVectors;
import hexaround.game.board.geometry.Vector;
import hexaround.game.board.pathfinding.stopstrategy.IStopStrategy;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class JumpingPathfinding implements IPathfindingStrategy {

    @Override
    public Optional<List<IPoint>> getPath(IBoard board, ICreature creature, IPoint fromPoint, IStopStrategy stopStrategy) {
        int scalar = 1;
        List<Vector> viableDirections = getViableDirections(board, fromPoint);

        while (scalar < creature.getMaxDistance()) {
            for (Vector viableDirection : viableDirections) {
                int straightLineNeighborX = fromPoint.getX() + viableDirection.dX() * scalar;
                int straightLineNeighborY = fromPoint.getY() + viableDirection.dY() * scalar;
                IPoint straightLinePoint = new HexPoint(straightLineNeighborX, straightLineNeighborY);

                if (board.creatureCanSlide(fromPoint))

                List<IPoint> path = new ArrayList<>();
                path.add(fromPoint);
                path.add(straightLinePoint);

                if (isViablePoint(board, fromPoint, straightLinePoint, creature) && stopStrategy.shouldStop(path, creature)){
                    return Optional.of(path);
                }
            }
        }
        return Optional.empty();
    }

    private boolean isViablePoint(IBoard board, IPoint lastPoint, IPoint neighboringPoint, ICreature creature) {
        return !board.moveIsDisconnecting(creature.getName(), lastPoint, neighboringPoint)
                && ((!board.pointIsOccupied(neighboringPoint) && board.creatureCanSlide(lastPoint, neighboringPoint))
                || creature.hasProperty(CreatureProperty.INTRUDING));
    }

    private List<Vector> getViableDirections(IBoard board, IPoint fromPoint) {
        List<Vector> viableDirections = new ArrayList<>();
        for (Vector unitVector : UnitVectors.VECTORS) {
            IPoint neighboringPoint = new HexPoint(fromPoint.getX() - unitVector.dX(), fromPoint.getY() - unitVector.dY());
            if (board.creatureCanSlide(fromPoint, neighboringPoint)) {
                viableDirections.add(unitVector);
            }
        }
    }
}
