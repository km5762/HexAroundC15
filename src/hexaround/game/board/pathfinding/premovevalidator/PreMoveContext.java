package hexaround.game.board.pathfinding.premovevalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;

public record PreMoveContext(IBoard board, IPoint fromPoint) {
}
