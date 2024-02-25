package hexaround.game.board.pathfinding.movevalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

public class MoveEmpty implements IMoveCondition {
    public boolean test(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        return !board.pointIsOccupied(toPoint);
    }
}
