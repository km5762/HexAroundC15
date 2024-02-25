package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.movevalidator.IMoveCondition;
import hexaround.game.board.pathfinding.movevalidator.MoveEmpty;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathUpToDestinationEmpty implements IPathCondition {
    IMoveCondition pointEmpty = new MoveEmpty();

    @Override
    public boolean test(List<IPoint> path, IBoard board, ICreature creature) {
        for (int i = 1; i < path.size(); i++) {
            IPoint point = path.get(i);
            boolean isLastPoint = i == path.size() - 1;
            if (!pointEmpty.test(board, creature, null, point) && !isLastPoint) {
                return false;
            }
        }
        return true;
    }
}
