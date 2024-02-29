package hexaround.game.rules.path;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;
import hexaround.game.rules.pre_movement.PreMoveContext;
import hexaround.game.rules.pre_movement.PreMoveDestinationNotButterfly;

import java.util.List;

public class PathDestinationNotButterfly implements ICondition<PathContext> {
    protected ICondition<PreMoveContext> preMoveDestinationNotButterfly = new PreMoveDestinationNotButterfly();

    /**
     * Used to determine if the paths destination is not a butterfly
     *
     * @param context a PathContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the path's destination is not a creature with CreatureName BUTTERFLY,
     * otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PathContext context) {
        IBoard board = context.board();
        ICreature creature = context.creature();
        List<IPoint> path = context.path();
        IPoint fromPoint = path.get(0);
        IPoint toPoint = path.get(path.size() - 1);
        PreMoveContext preMoveContext = new PreMoveContext(board, creature, fromPoint, toPoint);

        return preMoveDestinationNotButterfly.test(preMoveContext);
    }
}
