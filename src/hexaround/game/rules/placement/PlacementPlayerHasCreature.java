package hexaround.game.rules.placement;

import hexaround.game.creature.CreatureName;
import hexaround.game.player.Player;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PlacementPlayerHasCreature implements ICondition<PlacementContext> {


    /**
     * Used to determine if the player making a placement has the creature they are placing
     *
     * @param context a PlacementContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the placing player has the placed creature (their creature count
     * for that creature is at least one), otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PlacementContext context) {
        Player player = context.player();
        CreatureName creatureName = context.creatureName();
        ValidationResult result = new ValidationResult(true, null);

        if (player.getCreatureCount(creatureName) < 1) {
            result = new ValidationResult(false, "That player does not have that creature");
        }

        return result;
    }
}
