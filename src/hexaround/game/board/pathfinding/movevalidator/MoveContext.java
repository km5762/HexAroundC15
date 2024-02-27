package hexaround.game.board.pathfinding.movevalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

public record MoveContext(IBoard board, ICreature creature, IPoint originPoint, IPoint fromPoint, IPoint toPoint) {
}
