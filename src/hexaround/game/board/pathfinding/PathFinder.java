package hexaround.game.board.pathfinding;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.pathvalidator.IPathValidator;
import hexaround.game.board.pathfinding.movevalidator.IMoveValidator;
import hexaround.game.creature.ICreature;

import java.util.*;

public class PathFinder implements IPathFinder {
    protected IMoveValidator moveValidator;
    protected IPathValidator pathValidator;

    public PathFinder(IMoveValidator moveValidator, IPathValidator pathValidator) {
        this.moveValidator = moveValidator;
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

            if (pathValidator.validate(currentPath, board, creature) && (toPoint == null || lastPoint.equals(toPoint))) {
                return Optional.of(currentPath);
            }

            for (IPoint neighboringPoint : lastPoint.getNeighboringPoints()) {
                if (isValidPoint(boardSimulation, creature, lastPoint, neighboringPoint, currentPath, moveValidator)) {
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

    private boolean isValidPoint(IBoard board, ICreature creature, IPoint lastPoint, IPoint neighboringPoint, List<IPoint> currentPath, IMoveValidator moveValidator) {
        boolean notVisited = !currentPath.contains(neighboringPoint);
        boolean inRange = currentPath.size() <= creature.getMaxDistance();
        boolean validMove = moveValidator.validate(board, creature, lastPoint, neighboringPoint);

        return notVisited && inRange && validMove;
    }
}