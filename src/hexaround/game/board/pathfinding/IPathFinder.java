package hexaround.game.board.pathfinding;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.MovementRules;
import hexaround.game.rules.ValidationResult;

import java.util.List;
import java.util.Optional;

public interface IPathFinder {
    ValidationResult findPath(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint, MovementRules movementRules);
    ValidationResult findPath(IBoard board, ICreature creature, IPoint fromPoint, MovementRules movementRules);
}
