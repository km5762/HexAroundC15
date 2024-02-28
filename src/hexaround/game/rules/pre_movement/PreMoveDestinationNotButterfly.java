package hexaround.game.rules.pre_movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

import java.util.Optional;

public class PreMoveDestinationNotButterfly implements ICondition<PreMoveContext> {

    @Override
    public ValidationResult test(PreMoveContext context) {
        IBoard board = context.board();
        IPoint toPoint = context.toPoint();
        ValidationResult result = new ValidationResult(true, null);

        Optional<ICreature> destinationCreature = board.getTopCreature(toPoint);

        if (destinationCreature.isPresent()) {
            CreatureName creatureName = destinationCreature.get().getName();

            if (creatureName.equals(CreatureName.BUTTERFLY)) {
                result = new ValidationResult(false, "The target of this creatures move may not be a butterfly");
            }
        }

        return result;
    }
}
