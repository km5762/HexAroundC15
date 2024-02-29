package hexaround.game.rules.path;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

import java.util.List;

public class PathAtRange implements ICondition<PathContext> {

    /**
     * Used to determine if the path is at the creatures max range
     *
     * @param context a PathContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the path's length is equal
     * to the creatures max range, otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PathContext context) {
        List<IPoint> path = context.path();
        ICreature creature = context.creature();
        ValidationResult result = new ValidationResult(true, null);

        if (path.size() != creature.getMaxDistance() + 1) {
            result = new ValidationResult(false, "This path is not at the creatures max range");
        }

        return result;
    }
}
