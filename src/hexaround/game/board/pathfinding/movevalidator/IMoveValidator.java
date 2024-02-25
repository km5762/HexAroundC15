package hexaround.game.board.pathfinding.movevalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

public interface IMoveValidator {
    boolean validate(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint);
}
