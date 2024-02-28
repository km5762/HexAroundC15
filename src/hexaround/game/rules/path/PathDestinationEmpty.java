package hexaround.game.rules.path;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;
import hexaround.game.rules.movement.MoveContext;
import hexaround.game.rules.movement.MoveEmpty;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathDestinationEmpty implements ICondition<PathContext> {
    ICondition<MoveContext> pointEmpty = new MoveEmpty();

    @Override
    public ValidationResult test(PathContext context) {
        List<IPoint> path = context.path();
        IBoard board = context.board();
        ICreature creature = context.creature();
        IPoint lastPoint = path.get(path.size() - 1);
        MoveContext moveContext = new MoveContext(board, creature, null, null, lastPoint);
        ValidationResult result = new ValidationResult(true, null);

        if (!pointEmpty.test(moveContext).valid()) {
            result = new ValidationResult(false, "This destination of this path is not empty");
        }

        return result;
    }
}
