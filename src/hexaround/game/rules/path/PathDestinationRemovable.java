package hexaround.game.rules.path;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.ValidationResult;

import java.util.List;
import java.util.Optional;

public class PathDestinationRemovable implements ICondition<PathContext> {
    @Override
    public ValidationResult test(PathContext context) {
        IBoard board = context.board();
        List<IPoint> path = context.path();
        ICreature creature = context.creature();
        IBoard boardSimulation = board.clone();
        IPoint firstPoint = path.get(0);
        IPoint lastPoint = path.get(path.size() - 1);
        Optional<ICreature> removedCreature = boardSimulation.getTopCreature(lastPoint);
        ValidationResult result;

        boardSimulation.removeCreature(creature, firstPoint);
        if (removedCreature.isPresent()) {
            boardSimulation.removeCreature(removedCreature.get(), lastPoint);
        }

        if (removedCreature.isEmpty() || boardSimulation.isConnected()) {
            result = new ValidationResult(true, null);
        } else {
            result = new ValidationResult(false, "Removing the destination of this path would disconnect the colony.");
        }

        return result;
    }
}
