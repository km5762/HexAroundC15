package hexaround.game.rules;

import hexaround.game.rules.movement.MoveContext;
import hexaround.game.rules.path.PathContext;
import hexaround.game.rules.pre_movement.PreMoveContext;

public record MovementRules(Validator<PreMoveContext> preMoveValidator, Validator<MoveContext> moveValidator, Validator<PathContext> pathValidator) {
}
