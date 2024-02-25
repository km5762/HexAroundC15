package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;
import java.util.Optional;

public class PathDestinationRemovable implements IPathCondition {

    @Override
    public boolean test(List<IPoint> path, IBoard board, ICreature creature, IPoint targetPoint) {
        IBoard boardSimulation = board.clone();
        IPoint lastPoint = path.get(path.size() - 1);
        Optional<ICreature> removedCreature = boardSimulation.getTopCreature(lastPoint);

        if (removedCreature.isPresent()) {
            boardSimulation.removeCreature(removedCreature.get().getName(), lastPoint);
        }

        return removedCreature.isEmpty() || boardSimulation.isConnected();
    }
}
