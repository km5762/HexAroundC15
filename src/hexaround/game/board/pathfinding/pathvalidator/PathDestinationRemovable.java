package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
import hexaround.game.board.pathfinding.premovevalidator.PreMoveContext;
import hexaround.game.board.pathfinding.premovevalidator.PreMovePinned;
import hexaround.game.creature.ICreature;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class PathDestinationRemovable implements ICondition<PathContext> {
    @Override
    public boolean test(PathContext context) {
        IBoard board = context.board();
        List<IPoint> path = context.path();
        ICreature creature = context.creature();

        IBoard boardSimulation = board.clone();
        IPoint firstPoint = path.get(0);
        IPoint lastPoint = path.get(path.size() - 1);
        Optional<ICreature> removedCreature = boardSimulation.getTopCreature(lastPoint);

        boardSimulation.removeCreature(creature, firstPoint);
        if (removedCreature.isPresent()) {
            boardSimulation.removeCreature(removedCreature.get(), lastPoint);
        }

        return removedCreature.isEmpty() || boardSimulation.isConnected();
    }
}
