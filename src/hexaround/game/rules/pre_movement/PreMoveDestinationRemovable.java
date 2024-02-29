package hexaround.game.rules.pre_movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;
import hexaround.game.rules.movement.MoveContext;

import java.util.List;
import java.util.Optional;

public class PreMoveDestinationRemovable implements ICondition<PreMoveContext> {

    /**
     * Used to determine prior to pathfinding if removing the destination would disconnect the colony
     *
     * @param context a PreMoveContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the destination can be removed
     * without disconnecting the colony, otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PreMoveContext context) {
        IBoard board = context.board();
        ICreature creature = context.creature();
        IBoard boardSimulation = board.clone();
        IPoint fromPoint = context.fromPoint();
        IPoint toPoint = context.toPoint();
        Optional<ICreature> removedCreature = boardSimulation.getTopCreature(toPoint);
        ValidationResult result = new ValidationResult(true, null);

        if (toPoint != null) {
            boardSimulation.removeCreature(creature, fromPoint);
            if (removedCreature.isPresent()) {
                boardSimulation.removeCreature(removedCreature.get(), toPoint);
            }

            if (!boardSimulation.isConnected()) {
                result = new ValidationResult(false, "Removing the destination of this path would disconnect the colony.");
            }
        }

        return result;
    }
}
