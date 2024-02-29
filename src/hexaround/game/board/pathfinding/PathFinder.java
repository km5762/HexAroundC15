package hexaround.game.board.pathfinding;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.MovementRules;
import hexaround.game.rules.ValidationResult;
import hexaround.game.rules.Validator;
import hexaround.game.rules.movement.MoveContext;
import hexaround.game.rules.path.PathContext;
import hexaround.game.rules.pre_movement.PreMoveContext;
import hexaround.game.creature.ICreature;

import java.util.*;

public class PathFinder implements IPathFinder {
    @Override
    public ValidationResult findPath(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint, MovementRules movementRules) {
        Validator<PreMoveContext> preMoveValidator = movementRules.preMoveValidator();
        Validator<MoveContext> moveValidator = movementRules.moveValidator();
        Validator<PathContext> pathValidator = movementRules.pathValidator();
        Queue<List<IPoint>> pathQueue = new LinkedList<>();
        List<IPoint> startPath = new ArrayList<>();
        startPath.add(fromPoint);
        pathQueue.add(startPath);

        PreMoveContext preMoveContext = new PreMoveContext(board, creature, fromPoint, toPoint);
        ValidationResult preMoveValidation = preMoveValidator.validate(preMoveContext);

        if (!preMoveValidation.valid()) {
            return preMoveValidation;
        }

        while (!pathQueue.isEmpty()) {
            List<IPoint> currentPath = pathQueue.poll();
            IPoint lastPoint = currentPath.get(currentPath.size() - 1);
            IBoard boardSimulation = board.clone();
            boardSimulation.moveCreature(creature, fromPoint, lastPoint);

            PathContext pathContext = new PathContext(currentPath, board, creature);
            ValidationResult pathValidation = pathValidator.validate(pathContext);
            if (pathValidation.valid() && (toPoint == null || lastPoint.equals(toPoint)) && currentPath.size() > 1) {
                return pathValidation;
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

        return new ValidationResult(false, "No legal path exists to that point");
    }

    @Override
    public ValidationResult findPath(IBoard board, ICreature creature, IPoint fromPoint, MovementRules movementRules) {
        return findPath(board, creature, fromPoint, null, movementRules);
    }

    private boolean isValidPoint(MoveContext moveContext, List<IPoint> currentPath, Validator<MoveContext> moveValidator) {
        IPoint neighboringPoint = moveContext.toPoint();
        ICreature creature = moveContext.creature();

        boolean notVisited = !currentPath.contains(neighboringPoint);
        boolean inRange = currentPath.size() <= creature.getMaxDistance();
        boolean validMove = moveValidator.validate(moveContext).valid();

        return notVisited && inRange && validMove;
    }
}