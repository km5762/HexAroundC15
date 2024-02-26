package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;
import java.util.Optional;

public class PathDestinationRemovable implements IPathCondition {

    @Override
    public boolean test(List<IPoint> path, IBoard board, ICreature creature) {
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
