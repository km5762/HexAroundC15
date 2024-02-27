package hexaround.game.board.pathfinding.premovevalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

public record PreMoveContext(IBoard board, ICreature creature, IPoint fromPoint) {
}
