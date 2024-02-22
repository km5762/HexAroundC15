package hexaround.game.board.pathfinding;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.stopstrategy.IStopStrategy;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;

import java.util.*;

public class GroundPathfinding implements IPathfindingStrategy {

    @Override
    public Optional<List<IPoint>> getPath(IBoard board, ICreature creature, IPoint fromPoint, IStopStrategy stopStrategy) {
        Queue<List<IPoint>> pathQueue = new LinkedList<>();
        List<IPoint> startPath = new ArrayList<>();
        startPath.add(fromPoint);
        pathQueue.add(startPath);

        while (!pathQueue.isEmpty()) {
            List<IPoint> currentPath = pathQueue.poll();
            IPoint lastPoint = currentPath.get(currentPath.size() - 1);
            IBoard boardSimulation = board.createBoardSimulation();
            boardSimulation.moveCreature(creature.getName(), fromPoint, lastPoint);

            if (stopStrategy.shouldStop(currentPath, creature)) {
                return Optional.of(currentPath);
            }

            for (IPoint neighboringPoint : lastPoint.getNeighboringPoints()) {
                if (!currentPath.contains(neighboringPoint) && isViablePoint(boardSimulation, lastPoint, neighboringPoint, creature)) {
                    List<IPoint> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighboringPoint);
                    pathQueue.add(newPath);
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

    private List<List<IPoint>> filterDisconnectingPaths(IBoard board, ICreature creature, List<List<IPoint>> paths) {
        List<List<IPoint>> filteredPaths = new ArrayList<>();
        for (List<IPoint> path : paths) {
            if (!pathIsDisconnecting(board, creature, path)) {
                filteredPaths.add(path);
            }
        }
        return filteredPaths;
    }

    private boolean pathIsDisconnecting(IBoard board, ICreature creature, List<IPoint> path) {
        if (path.isEmpty()) {
            return false;
        }

        IPoint fromPoint = path.get(0);
        IPoint toPoint = path.get(path.size() - 1);
        return board.moveIsDisconnecting(creature.getName(), fromPoint, toPoint);
    }

    public boolean noPath(IBoard board, ICreature creature, IPoint fromPoint) {
        for (IPoint neighboringPoint : fromPoint.getNeighboringPoints()) {
            if (canMove(board, creature, fromPoint, neighboringPoint)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMove(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        return !board.moveIsDisconnecting(creature.getName(), fromPoint, toPoint)
                && ((!board.pointIsOccupied(toPoint) && board.creatureCanSlide(fromPoint, toPoint))
                || creature.hasProperty(CreatureProperty.INTRUDING));
    }
}
