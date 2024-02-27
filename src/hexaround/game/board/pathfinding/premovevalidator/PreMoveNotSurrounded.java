package hexaround.game.board.pathfinding.premovevalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;

public class PreMoveNotSurrounded implements ICondition<PreMoveContext> {
    public boolean test(PreMoveContext context) {
        IBoard board = context.board();
        IPoint fromPoint = context.fromPoint();

        return !board.isSurrounded(fromPoint);
    }
}
