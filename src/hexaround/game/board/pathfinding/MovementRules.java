package hexaround.game.board.pathfinding;

import hexaround.game.board.pathfinding.movevalidator.MoveContext;
import hexaround.game.board.pathfinding.pathvalidator.PathContext;

public record MovementRules(Validator<MoveContext> moveValidator, Validator<PathContext> pathValidator) {
}
