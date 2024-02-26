package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
import hexaround.game.creature.ICreature;

import java.util.List;

public class PathAtRange implements ICondition<PathContext> {
    @Override
    public boolean test(PathContext context) {
        return context.path().size() == context.creature().getMaxDistance() + 1;
    }
}
