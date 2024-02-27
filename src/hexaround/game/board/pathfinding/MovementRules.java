package hexaround.game.board.pathfinding;

import hexaround.game.board.pathfinding.movevalidator.MoveContext;
import hexaround.game.board.pathfinding.pathvalidator.PathContext;
import hexaround.game.board.pathfinding.premovevalidator.PreMoveContext;

public record MovementRules(Validator<PreMoveContext> preMoveValidator, Validator<MoveContext> moveValidator, Validator<PathContext> pathValidator) {
}
