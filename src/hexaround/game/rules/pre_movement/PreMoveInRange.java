package hexaround.game.rules.pre_movement;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PreMoveInRange implements ICondition<PreMoveContext> {

    /**
     * Used to determine prior to pathfinding if the destination is in the creature's range
     *
     * @param context a PreMoveContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if there is no butterfly at the destination,
     * otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PreMoveContext context) {
        ICreature creature = context.creature();
        IPoint fromPoint = context.fromPoint();
        IPoint toPoint = context.toPoint();
        ValidationResult result = new ValidationResult(true, null);

        if (toPoint != null ) {
            int distance = fromPoint.calculateDistanceTo(toPoint);

            if (distance > creature.getMaxDistance()) {
                result = new ValidationResult(false, "That destination is out of range");
            }
        }

        return result;
    }
}
