package hexaround.game.board.pathfinding.pointvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

public interface IPointValidator {
    boolean validate(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint);
}
