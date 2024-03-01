package hexaround.game.rules.path;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.ValidationResult;
import hexaround.game.rules.pre_movement.PreMoveContext;
import hexaround.game.rules.pre_movement.PreMoveDestinationRemovable;

import java.util.List;
import java.util.Optional;

public class PathDestinationRemovable implements ICondition<PathContext> {
    protected ICondition<PreMoveContext> preMoveDestinationRemovable = new PreMoveDestinationRemovable();

    /**
     * Used to determine if removing the paths destination would disconnect the colony
     *
     * @param context a PathContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if removing the path's destination would not
     * disconnect the colony, otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PathContext context) {
        IBoard board = context.board();
        List<IPoint> path = context.path();
        ICreature creature = context.creature();
        IPoint fromPoint = path.get(0);
        IPoint toPoint = path.get(path.size() - 1);
        PreMoveContext preMoveContext = new PreMoveContext(board, creature, fromPoint, toPoint);

        return preMoveDestinationRemovable.test(preMoveContext);
    }
}
