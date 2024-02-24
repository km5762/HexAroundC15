package hexaround.game.board.pathfinding.pointvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

public class PointEmpty implements IPointCondition {
    public boolean test(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        return board.getAllCreatures(toPoint).isEmpty();
    }
}
