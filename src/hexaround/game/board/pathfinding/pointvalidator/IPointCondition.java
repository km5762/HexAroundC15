package hexaround.game.board.pathfinding.pointvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

public interface IPointCondition {
    boolean test(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint, IPoint targetPoint);
}
