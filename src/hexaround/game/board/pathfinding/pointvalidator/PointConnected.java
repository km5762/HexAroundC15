package hexaround.game.board.pathfinding.pointvalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

public class PointConnected implements IPointCondition {
    public boolean test(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        return moveIsConnected(board, creature, fromPoint, toPoint);
    }

    private boolean moveIsConnected(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        IBoard boardSimulation = board.clone();
        boardSimulation.moveCreature(creature.getName(), fromPoint, toPoint);

        return boardSimulation.isConnected();
    }
}
