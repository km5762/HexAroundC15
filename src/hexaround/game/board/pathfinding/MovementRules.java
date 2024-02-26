package hexaround.game.board.pathfinding;

import hexaround.game.board.pathfinding.movevalidator.IMoveValidator;
import hexaround.game.board.pathfinding.pathvalidator.IPathValidator;

public record MovementRules(IMoveValidator moveValidator, IPathValidator pathValidator) {
}
