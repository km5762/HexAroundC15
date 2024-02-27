package hexaround.game.board.pathfinding;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.movevalidator.MoveContext;
import hexaround.game.board.pathfinding.pathvalidator.PathContext;
import hexaround.game.board.pathfinding.premovevalidator.PreMoveContext;
import hexaround.game.creature.ICreature;

import java.util.*;

public class PathFinder implements IPathFinder {
    @Override
    public Optional<List<IPoint>> findPath(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint, MovementRules movementRules) {
        Validator<PreMoveContext> preMoveValidator = movementRules.preMoveValidator();
        Validator<MoveContext> moveValidator = movementRules.moveValidator();
        Validator<PathContext> pathValidator = movementRules.pathValidator();
        Queue<List<IPoint>> pathQueue = new LinkedList<>();
        List<IPoint> startPath = new ArrayList<>();
        startPath.add(fromPoint);
        pathQueue.add(startPath);

        PreMoveContext preMoveContext = new PreMoveContext(board, creature, fromPoint);
        boolean preMoveValidated = preMoveValidator.validate(preMoveContext);

        while (!pathQueue.isEmpty() && preMoveValidated) {
            List<IPoint> currentPath = pathQueue.poll();
            IPoint lastPoint = currentPath.get(currentPath.size() - 1);
            IBoard boardSimulation = board.clone();
            boardSimulation.moveCreature(creature, fromPoint, lastPoint);

            PathContext pathContext = new PathContext(currentPath, board, creature);
            if (pathValidator.validate(pathContext) && (toPoint == null || lastPoint.equals(toPoint)) && currentPath.size() > 1) {
                return Optional.of(currentPath);
            }

            for (IPoint neighboringPoint : lastPoint.getNeighboringPoints()) {
                MoveContext moveContext = new MoveContext(boardSimulation, creature, fromPoint, lastPoint, neighboringPoint);
                if (isValidPoint(moveContext, currentPath, moveValidator)) {
                    List<IPoint> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighboringPoint);
                    pathQueue.add(newPath);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<IPoint>> findPath(IBoard board, ICreature creature, IPoint fromPoint, MovementRules movementRules) {
        return findPath(board, creature, fromPoint, null, movementRules);
    }

    private boolean isValidPoint(MoveContext moveContext, List<IPoint> currentPath, Validator<MoveContext> moveValidator) {
        IPoint neighboringPoint = moveContext.toPoint();
        ICreature creature = moveContext.creature();

        boolean notVisited = !currentPath.contains(neighboringPoint);
        boolean inRange = currentPath.size() <= creature.getMaxDistance();
        boolean validMove = moveValidator.validate(moveContext);

        return notVisited && inRange && validMove;
    }
}