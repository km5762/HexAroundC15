package hexaround.game.rules;

import hexaround.game.rules.movement.MoveContext;
import hexaround.game.rules.path.PathContext;
import hexaround.game.rules.pre_movement.PreMoveContext;

/**
 * A set of Validators which completely describe the movement of a creature
 *
 * @param preMoveValidator a Validator accepting PreMoveContext which will be used to validate paths prior to movement
 * @param moveValidator    a Validator accepting MoveContext which will be used to validate intermediate moves on a path
 * @param pathValidator    a Validator accepting PathContext which will be used to validate paths as they are found
 */
public record MovementRules(Validator<PreMoveContext> preMoveValidator, Validator<MoveContext> moveValidator,
                            Validator<PathContext> pathValidator) {
}
