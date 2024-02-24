package hexaround.game.board.pathfinding;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.pathvalidator.IPathValidator;
import hexaround.game.board.pathfinding.pointvalidator.IPointValidator;
import hexaround.game.creature.ICreature;

import java.util.*;

public class PathFinder implements IPathFinder {
    protected IPointValidator pointValidator;
    protected IPathValidator pathValidator;

    public PathFinder(IPointValidator pointValidator, IPathValidator pathValidator) {
        this.pointValidator = pointValidator;
        this.pathValidator = pathValidator;
    }

    @Override
    public Optional<List<IPoint>> findPath(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        Queue<List<IPoint>> pathQueue = new LinkedList<>();
        List<IPoint> startPath = new ArrayList<>();
        startPath.add(fromPoint);
        pathQueue.add(startPath);

        while (!pathQueue.isEmpty()) {
            List<IPoint> currentPath = pathQueue.poll();
            IPoint lastPoint = currentPath.get(currentPath.size() - 1);
            IBoard boardSimulation = board.clone();
            boardSimulation.moveCreature(creature.getName(), fromPoint, lastPoint);

            if (pathValidator.validate(currentPath, creature, toPoint) && (lastPoint.equals(toPoint) || toPoint == null)) {
                return Optional.of(currentPath);
            }

            for (IPoint neighboringPoint : lastPoint.getNeighboringPoints()) {
                if (!currentPath.contains(neighboringPoint) && pointValidator.validate(boardSimulation, creature, lastPoint, neighboringPoint)) {
                    List<IPoint> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighboringPoint);
                    pathQueue.add(newPath);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<IPoint>> findPath(IBoard board, ICreature creature, IPoint fromPoint) {
        return findPath(board, creature, fromPoint, null);
    }
}