package hexaround.game.rules.placement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;
import hexaround.game.player.Player;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

import java.util.Optional;

public class PlacementNextToAllyAndNotEnemy implements ICondition<PlacementContext> {

    @Override
    public ValidationResult test(PlacementContext context) {
        IBoard board = context.board();
        Player player = context.player();
        IPoint point = context.point();
        boolean nextToAlly = false;
        boolean nextToEnemy = false;
        ValidationResult result = new ValidationResult(true, null);

        for (IPoint neighboringPoint : point.getNeighboringPoints()) {
            Optional<ICreature> topCreature = board.getTopCreature(neighboringPoint);

            if (topCreature.isPresent()) {
                if (topCreature.get().getOwnerName() != player.getName()) {
                    nextToEnemy = true;
                } else {
                    nextToAlly = true;
                }
            }
        }

        if (!nextToAlly) {
            result = new ValidationResult(false, "That placement is not next to an ally creature");
        } else if (nextToEnemy) {
            result = new ValidationResult(false, "That placement is next to an enemy creature");
        }

        return result;
    }
}
