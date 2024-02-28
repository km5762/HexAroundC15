package hexaround.game.rules.path;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

import java.util.List;

public class PathDestinationNotStack implements ICondition<PathContext> {

    @Override
    public ValidationResult test(PathContext context) {
        IBoard board = context.board();
        List<IPoint> path = context.path();
        IPoint lastPoint = path.get(path.size() - 1);
        ValidationResult result = new ValidationResult(true, null);

        if (board.getAllCreatures(lastPoint).getSize() > 1) {
            result = new ValidationResult(false, "The destination of this path has too many creatures");
        }

        return result;
    }
}
