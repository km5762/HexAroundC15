package hexaround.game.board.pathfinding;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;

public interface PathfindingStrategy {
    boolean existsValidPath(IBoard board, IPoint fromPoint, IPoint toPoint);
    boolean canMove(IBoard board, IPoint fromPoint);
}
