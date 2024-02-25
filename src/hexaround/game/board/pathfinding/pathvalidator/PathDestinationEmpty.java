package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.movevalidator.IMoveCondition;
import hexaround.game.board.pathfinding.movevalidator.MoveEmpty;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathDestinationEmpty implements IPathCondition {
    IMoveCondition pointEmpty = new MoveEmpty();

    @Override
    public boolean test(List<IPoint> path, IBoard board, ICreature creature) {
        IPoint lastPoint = path.get(path.size() - 1);

        return pointEmpty.test(board, creature, null, lastPoint);
    }
}
