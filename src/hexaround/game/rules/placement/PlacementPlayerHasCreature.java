package hexaround.game.rules.placement;

import hexaround.game.creature.CreatureName;
import hexaround.game.player.Player;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PlacementPlayerHasCreature implements ICondition<PlacementContext> {

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
